package com.inge.nathan.monopolycalculator.UI;

import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.vending.billing.IInAppBillingService;
import com.inge.nathan.monopolycalculator.InAppBillingUtil.IabHelper;
import com.inge.nathan.monopolycalculator.R;
import com.inge.nathan.monopolycalculator.Utilities.MCPreferencesManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class GoProActivity extends AppCompatActivity {

    private IabHelper mHelper;
    private IInAppBillingService mService;
    private ServiceConnection mServiceConn;

    private ProgressDialog loadingDialog;
    private Button buyProButton;
    private TextView hasProText;

    private String skuPro;
    private boolean hasProVersion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_go_pro);

        TextView activityTitle = findViewById(R.id.end_activity_title);
        activityTitle.setText("Go Pro");

        ImageButton backButton = findViewById(R.id.end_back_button);
        backButton.setOnClickListener(v -> finish());

        buyProButton = findViewById(R.id.buy_pro_button);
        hasProText = findViewById(R.id.has_pro_view);

        hasProVersion = MCPreferencesManager.getProStatus(getApplicationContext());
        if(!hasProVersion) {
            buyProButton.setVisibility(View.VISIBLE);
            buyProButton.setOnClickListener(v -> buyPro());
        } else {
            hasProText.setVisibility(View.VISIBLE);
        }

        loadingDialog = ProgressDialog.show(this, "",
            "Loading Pro Version Details...", true);

        mServiceConn = new ServiceConnection() {
            @Override
            public void onServiceDisconnected(ComponentName name) {
                mService = null;
                loadingDialog.dismiss();
            }

            @Override
            public void onServiceConnected(ComponentName name,
                                           IBinder service) {
                mService = IInAppBillingService.Stub.asInterface(service);
                getProduct();
            }

            @Override
            public void onBindingDied(ComponentName name) {
                loadingDialog.dismiss();
            }
        };

        Intent serviceIntent =
            new Intent("com.android.vending.billing.InAppBillingService.BIND");
        serviceIntent.setPackage("com.android.vending");
        bindService(serviceIntent, mServiceConn, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1001) {

            if (resultCode == RESULT_OK) {
                Toast.makeText(this, "Thank you for your purchase!", Toast.LENGTH_SHORT).show();

                MCPreferencesManager.upgradeToPro(getApplicationContext());
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mService != null) {
            unbindService(mServiceConn);
        }
    }

    public void getProduct() {
        new Thread(() -> {
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
                showError();
            }

            int BILLING_RESPONSE_RESULT_OK = 0;
            int response = skuDetails.getInt("RESPONSE_CODE");
            if (response == BILLING_RESPONSE_RESULT_OK) {
                ArrayList<String> responseList
                    = skuDetails.getStringArrayList("DETAILS_LIST");

                for (String thisResponse : responseList) {
                    try {
                        JSONObject object = new JSONObject(thisResponse);
                        skuPro = object.getString("productId");
                        String price = object.getString("price");


                        Handler mainHandler = new Handler(Looper.getMainLooper());
                        mainHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                loadingDialog.dismiss();
                                buyProButton.setText("Upgrade - " + price);
                            }
                        });

                    } catch (JSONException e) {
                        e.printStackTrace();
                        showError();
                    }
                }
            }

        }).start();
    }

    public void showError() {
        loadingDialog.dismiss();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Error Loading Pro Details")
            .setMessage(("Please check your internet connection and try again."))
            .setPositiveButton("OK", (dialog, which) -> finish())
            .show();
    }

    public void buyPro() {
        try {
            Bundle buyIntentBundle = mService.getBuyIntent(3, getPackageName(),
                skuPro, "inapp", "monopolyCalculatorProUpgrade");

            PendingIntent pendingIntent = buyIntentBundle.getParcelable("BUY_INTENT");

            int REQUEST_CODE = 1001;
            if (pendingIntent != null) {
                startIntentSenderForResult(pendingIntent.getIntentSender(),
                    REQUEST_CODE, new Intent(), 0, 0,
                    0);
            }

        } catch (RemoteException e) {
            e.printStackTrace();
            showError();
        } catch (IntentSender.SendIntentException e) {
            e.printStackTrace();
            showError();
        }
    }
}
