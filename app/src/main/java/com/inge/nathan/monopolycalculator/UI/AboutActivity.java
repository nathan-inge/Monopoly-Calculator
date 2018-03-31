package com.inge.nathan.monopolycalculator.UI;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.inge.nathan.monopolycalculator.R;

public class AboutActivity extends AppCompatActivity {

    private TextView versionText;
    private TextView activityTitle;
    private ImageButton backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        versionText = findViewById(R.id.version_view);
        activityTitle = findViewById(R.id.end_activity_title);
        activityTitle.setText("About");

        backButton = findViewById(R.id.end_back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        try {
            PackageInfo pInfo = this.getPackageManager().getPackageInfo(getPackageName(), 0);
            String version = "Version: " + pInfo.versionName + " (free)";
            versionText.setText(version);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }
}
