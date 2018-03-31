package com.inge.nathan.monopolycalculator.UI;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.media.Image;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.MenuBuilder;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.inge.nathan.monopolycalculator.MonopolyObjects.MonopolyGame;
import com.inge.nathan.monopolycalculator.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    // Private UI
    private EditText playerOneEdit;
    private EditText playerTwoEdit;
    private EditText playerThreeEdit;
    private EditText playerFourEdit;
    private Button nextButton;
    private Toolbar customToolbar;
    private ImageButton resetButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI
        playerOneEdit = findViewById(R.id.p1_name_edit);
        playerTwoEdit = findViewById(R.id.p2_name_edit);
        playerThreeEdit = findViewById(R.id.p3_name_edit);
        playerFourEdit = findViewById(R.id.p4_name_edit);
        nextButton = findViewById(R.id.next_button);
        resetButton = findViewById(R.id.reset_button);
        customToolbar = findViewById(R.id.custom_home_toolbar);
        setSupportActionBar(customToolbar);
        customToolbar.setOverflowIcon(ContextCompat.getDrawable(this, R.drawable.toolbar_menu));

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verifyPlayers();
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playerOneEdit.setText(null);
                playerTwoEdit.setText(null);
                playerThreeEdit.setText(null);
                playerFourEdit.setText(null);
            }
        });

    }

    @SuppressLint("RestrictedApi")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_options, menu);

        if(menu instanceof MenuBuilder){
            MenuBuilder m = (MenuBuilder) menu;
            m.setOptionalIconsVisible(true);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.about_menu_item:
                Intent i = new Intent(this, AboutActivity.class);
                startActivity(i);
                return true;
            case R.id.rules_menu_item:
                Intent j = new Intent(this, RulesActivity.class);
                startActivity(j);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void verifyPlayers() {
        ArrayList<String> playerNames = new ArrayList<>();

        String p1Name = playerOneEdit.getText().toString().trim();
        String p2Name = playerTwoEdit.getText().toString().trim();
        String p3Name = playerThreeEdit.getText().toString().trim();
        String p4Name = playerFourEdit.getText().toString().trim();

        if (!p1Name.isEmpty()) { playerNames.add(p1Name); }
        if (!p2Name.isEmpty()) { playerNames.add(p2Name); }
        if (!p3Name.isEmpty()) { playerNames.add(p3Name); }
        if (!p4Name.isEmpty()) { playerNames.add(p4Name); }


        if(playerNames.size() < 2) {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Invalid Player Names")
                .setMessage(("Please enter at least two player names."))
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .show();

        } else if(containsDuplicates(playerNames)) {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Invalid Player Names")
                .setMessage(("Player names must be unique."))
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .show();

        } else {
            // Create new game
            MonopolyGame.setupNewGame(playerNames);

            // Go to standings activity
            Intent i = new Intent(this, StandingsActivity.class);
            startActivity(i);
        }
    }

    private boolean containsDuplicates(ArrayList<String> playerNames) {
        Set<String> set = new HashSet<>(playerNames);

        return set.size() < playerNames.size();
    }
}
