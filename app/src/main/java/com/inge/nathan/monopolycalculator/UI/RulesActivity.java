package com.inge.nathan.monopolycalculator.UI;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.inge.nathan.monopolycalculator.R;

public class RulesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rules);

        TextView activityTitle = findViewById(R.id.end_activity_title);
        activityTitle.setText("Rules");

        ImageButton backButton = findViewById(R.id.end_back_button);
        backButton.setOnClickListener(v -> finish());
    }
}
