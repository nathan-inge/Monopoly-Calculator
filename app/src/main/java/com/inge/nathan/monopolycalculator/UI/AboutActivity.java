package com.inge.nathan.monopolycalculator.UI;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.media.Image;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.vending.billing.IInAppBillingService;
import com.inge.nathan.monopolycalculator.InAppBillingUtil.IabHelper;
import com.inge.nathan.monopolycalculator.InAppBillingUtil.IabResult;
import com.inge.nathan.monopolycalculator.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AboutActivity extends AppCompatActivity {

    private TextView versionText;
    private TextView activityTitle;
    private ImageButton backButton;
    private Button sendFeedbackButton;

    private IabHelper mHelper;

    private IInAppBillingService mService;
    private ServiceConnection mServiceConn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        versionText = findViewById(R.id.version_view);
        activityTitle = findViewById(R.id.end_activity_title);
        sendFeedbackButton = findViewById(R.id.send_feedback_button);
        sendFeedbackButton.setOnClickListener(v -> sendFeedbackEmail());
        activityTitle.setText("About");

        backButton = findViewById(R.id.end_back_button);
        backButton.setOnClickListener(v -> finish());

        try {
            PackageInfo pInfo = this.getPackageManager().getPackageInfo(getPackageName(), 0);
            String version = "Version: " + pInfo.versionName + " (free)";
            versionText.setText(version);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                // Insert some method call here.
                ArrayList<String> skuList = new ArrayList<String> ();
                skuList.add("pro_mc_version");
                Bundle querySkus = new Bundle();
                querySkus.putStringArrayList("ITEM_ID_LIST", skuList);

                Bundle skuDetails = null;
                try {
                    skuDetails = mService.getSkuDetails(3,
                        getPackageName(), "inapp", querySkus);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }

                int BILLING_RESPONSE_RESULT_OK = 0;
                int response = skuDetails.getInt("RESPONSE_CODE");
                if (response == BILLING_RESPONSE_RESULT_OK) {
                    ArrayList<String> responseList
                        = skuDetails.getStringArrayList("DETAILS_LIST");

                    for (String thisResponse : responseList) {
                        try {
                            JSONObject object = new JSONObject(thisResponse);
                            String sku = object.getString("productId");
                            String price = object.getString("price");


                            Handler mainHandler = new Handler(Looper.getMainLooper());
                            mainHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    versionText.setText(price);
                                }
                            });

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }

            }
        });

        mServiceConn = new ServiceConnection() {
            @Override
            public void onServiceDisconnected(ComponentName name) {
                mService = null;
            }

            @Override
            public void onServiceConnected(ComponentName name,
                                           IBinder service) {
                mService = IInAppBillingService.Stub.asInterface(service);
                t.start();
            }
        };

        Intent serviceIntent =
            new Intent("com.android.vending.billing.InAppBillingService.BIND");
        serviceIntent.setPackage("com.android.vending");
        bindService(serviceIntent, mServiceConn, Context.BIND_AUTO_CREATE);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mService != null) {
            unbindService(mServiceConn);
        }
    }

    private void sendFeedbackEmail() {
        Intent i = new Intent(Intent.ACTION_SENDTO);
        i.setData(Uri.parse("mailto:"));
        i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"nbi.software.hello@gmail.com"});
        i.putExtra(Intent.EXTRA_SUBJECT, "Monopoly Calculator Feedback");

        try {
            startActivityForResult(
                Intent.createChooser(i, "Send feedback..."),
                10
            );

        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(
                getApplicationContext(),
                "There are no email clients installed.",
                Toast.LENGTH_SHORT
            ).show();
        }

    }
}
