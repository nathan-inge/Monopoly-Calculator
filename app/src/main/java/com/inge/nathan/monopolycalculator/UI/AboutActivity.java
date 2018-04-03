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
import com.inge.nathan.monopolycalculator.Utilities.MCPreferencesManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AboutActivity extends AppCompatActivity {

    private TextView versionText;
    private TextView activityTitle;
    private ImageButton backButton;
    private Button sendFeedbackButton;

    private boolean hasProVersion;

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

        hasProVersion = MCPreferencesManager.getProStatus(getApplicationContext());

        try {
            PackageInfo pInfo = this.getPackageManager().getPackageInfo(getPackageName(), 0);
            String proText = (hasProVersion) ? "PRO" : "standard";
            String version = "Version: " + pInfo.versionName + " " + proText;
            versionText.setText(version);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
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
