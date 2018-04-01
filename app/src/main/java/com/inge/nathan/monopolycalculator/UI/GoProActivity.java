package com.inge.nathan.monopolycalculator.UI;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.inge.nathan.monopolycalculator.R;

public class GoProActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_go_pro);

        TextView activityTitle = findViewById(R.id.end_activity_title);
        activityTitle.setText("Go Pro");
    }
}
