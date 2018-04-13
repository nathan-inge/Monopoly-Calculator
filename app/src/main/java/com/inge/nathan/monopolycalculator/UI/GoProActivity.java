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
import android.support.design.widget.CheckableImageButton;
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
import java.util.Objects;

public class GoProActivity extends AppCompatActivity {

    private IabHelper mHelper;
    private IInAppBillingService mService;
    private ServiceConnection mServiceConn;

    private ProgressDialog loadingDialog;
    private Button buyProButton;
    private TextView hasProText;
    private TextView redeemView;
    private Button redeemButton;

    private String skuPro;
    private boolean hasProVersion;

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_go_pro);

        context = this;

        TextView activityTitle = findViewById(R.id.end_activity_title);
        activityTitle.setText("Go Pro");

        ImageButton backButton = findViewById(R.id.end_back_button);
        backButton.setOnClickListener(v -> finish());

        buyProButton = findViewById(R.id.buy_pro_button);
        hasProText = findViewById(R.id.has_pro_view);

        redeemView = findViewById(R.id.redeem_pro_view);
        redeemButton = findViewById(R.id.redeem_pro_button);

        hasProVersion = MCPreferencesManager.getProStatus(getApplicationContext());
        if (!hasProVersion) {
            buyProButton.setVisibility(View.VISIBLE);
            buyProButton.setOnClickListener(v -> buyPro());

            redeemView.setVisibility(View.VISIBLE);
            redeemButton.setVisibility(View.VISIBLE);

            redeemButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    redeemPurchase();
                }
            });

        } else {
            hasProText.setVisibility(View.VISIBLE);
            return;
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
                showError();
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
                setupLayout(true);
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

    private void getProduct() {
        new Thread(() -> {
            // Insert some method call here.
            ArrayList<String> skuList = new ArrayList<String>();
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

                        this.runOnUiThread(new Runnable() {
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

    private void showError() {
        loadingDialog.dismiss();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Error Loading Pro Details")
            .setMessage(("Please check your internet connection and try again."))
            .setPositiveButton("OK", (dialog, which) -> finish())
            .show();
    }

    private void buyPro() {
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

    private void redeemPurchase() {

        loadingDialog = ProgressDialog.show(this, "",
            "Connecting to Google Play", true);
        loadingDialog.show();

        Bundle ownedItems = null;

        try {
            ownedItems = mService.getPurchases(3,
                getPackageName(), "inapp", null);
        } catch (RemoteException e) {
            e.printStackTrace();
            showError();
        }

        int response = ownedItems.getInt("RESPONSE_CODE");
        int BILLING_RESPONSE_RESULT_OK = 0;
        if (response == BILLING_RESPONSE_RESULT_OK) {
            boolean hasPro = false;

            ArrayList<String> ownedSkus =
                ownedItems.getStringArrayList("INAPP_PURCHASE_ITEM_LIST");

            for(String sku : ownedSkus) {
                if(sku == skuPro) {
                    hasPro = true;
                }
            }

            boolean finalHasPro = hasPro;
            this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(finalHasPro) {
                        MCPreferencesManager.upgradeToPro(context);
                        Toast.makeText(getApplicationContext(), "Successfully redeemed PRO upgrade!", Toast.LENGTH_SHORT).show();
                        setupLayout(true);

                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setTitle("Upgrade not found!")
                            .setMessage(("According to Google Play Store, you have not purchased the PRO upgrade already. Please upgrade to PRO by tapping the upgrade button."))
                            .setPositiveButton("OK", (dialog, which) -> {
                                // Close dialog
                            })
                            .show();
                    }
                }
            });

        } else {
            showError();
        }

        loadingDialog.dismiss();

    }

    private void setupLayout(boolean hasPro) {
        if (!hasProVersion) {
            hasProText.setVisibility(View.GONE);

            buyProButton.setVisibility(View.VISIBLE);
            redeemView.setVisibility(View.VISIBLE);
            redeemButton.setVisibility(View.VISIBLE);

        } else {
            hasProText.setVisibility(View.VISIBLE);

            buyProButton.setVisibility(View.GONE);
            redeemView.setVisibility(View.GONE);
            redeemButton.setVisibility(View.GONE);

        }
    }
}
