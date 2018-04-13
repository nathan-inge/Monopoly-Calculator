package com.inge.nathan.monopolycalculator.UI;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.media.Image;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.widget.CardView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.inge.nathan.monopolycalculator.Lists.GameListAdapter;
import com.inge.nathan.monopolycalculator.Lists.NonScrollListView;
import com.inge.nathan.monopolycalculator.MonopolyObjects.MonopolyGame;
import com.inge.nathan.monopolycalculator.R;
import com.inge.nathan.monopolycalculator.Utilities.MCExceptions.NoSavedGamesException;
import com.inge.nathan.monopolycalculator.Utilities.MCFileManager;
import com.inge.nathan.monopolycalculator.Utilities.MCPreferencesManager;

import static com.inge.nathan.monopolycalculator.Utilities.MonopolyConstants.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    // Private UI
    private EditText playerOneEdit;
    private EditText playerTwoEdit;
    private EditText playerThreeEdit;
    private EditText playerFourEdit;
    private EditText playerFiveEdit;
    private EditText playerSixEdit;
    private EditText playerSevenEdit;
    private EditText playerEightEdit;
    private Button nextButton;
    private Button addPlayerButton;
    private Toolbar customToolbar;
    private ImageButton resetButton;
    private NonScrollListView savedGamesList;

    // Private instance vars
    private boolean hasProVersion;
    private int numPlayerEdits = 4;

    private GameListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI
        playerOneEdit = findViewById(R.id.p1_name_edit);
        playerTwoEdit = findViewById(R.id.p2_name_edit);
        playerThreeEdit = findViewById(R.id.p3_name_edit);
        playerFourEdit = findViewById(R.id.p4_name_edit);
        playerFiveEdit = findViewById(R.id.p5_name_edit);
        playerSixEdit = findViewById(R.id.p6_name_edit);
        playerSevenEdit = findViewById(R.id.p7_name_edit);
        playerEightEdit = findViewById(R.id.p8_name_edit);
        addPlayerButton = findViewById(R.id.add_player_button);
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

        hasProVersion = MCPreferencesManager.getProStatus(getApplicationContext());

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playerOneEdit.setText(null);
                playerTwoEdit.setText(null);
                playerThreeEdit.setText(null);
                playerFourEdit.setText(null);
                playerFiveEdit.setText(null);
                playerSixEdit.setText(null);
                playerSevenEdit.setText(null);
                playerEightEdit.setText(null);

                if(hasProVersion) { resetExtraPlayers(); }
            }
        });

        if(hasProVersion) {
            CardView adCardView = findViewById(R.id.banner_ad_main);
            adCardView.setVisibility(View.GONE);

            setUpSavedGameList();
            setUpClearAllButton();
            setUpAddPlayerButton();

        } else {
            // Remove saved game card and set up ads
            CardView savedGamesCardView = findViewById(R.id.saved_games_card);
            savedGamesCardView.setVisibility(View.GONE);
            MobileAds.initialize(this, "ca-app-pub-1213633519254149~9428094547");
            AdView adView = findViewById(R.id.adViewMain);
            AdRequest adRequest = new AdRequest.Builder().build();
            adView.loadAd(adRequest);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if((resultCode == GAME_SAVED)) {
            if(adapter != null) {
                updateSavedGameList();
            }
        }
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

            case R.id.pro_menu_item:
                Intent k = new Intent(this, GoProActivity.class);
                startActivity(k);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void updateSavedGameList() {
        Handler mainHandler = new Handler(Looper.getMainLooper());
        Runnable myRunnable = new Runnable() {
            @Override
            public void run() {
                adapter.notifyDataSetChanged();
            } // This is your code
        };
        mainHandler.post(myRunnable);
    }

    private void verifyPlayers() {
        ArrayList<String> playerNames = new ArrayList<>();

        String p1Name = playerOneEdit.getText().toString().trim();
        String p2Name = playerTwoEdit.getText().toString().trim();
        String p3Name = playerThreeEdit.getText().toString().trim();
        String p4Name = playerFourEdit.getText().toString().trim();
        String p5Name = playerFiveEdit.getText().toString().trim();
        String p6Name = playerSixEdit.getText().toString().trim();
        String p7Name = playerSevenEdit.getText().toString().trim();
        String p8Name = playerEightEdit.getText().toString().trim();

        if (!p1Name.isEmpty()) { playerNames.add(p1Name); }
        if (!p2Name.isEmpty()) { playerNames.add(p2Name); }
        if (!p3Name.isEmpty()) { playerNames.add(p3Name); }
        if (!p4Name.isEmpty()) { playerNames.add(p4Name); }
        if (!p5Name.isEmpty()) { playerNames.add(p5Name); }
        if (!p6Name.isEmpty()) { playerNames.add(p6Name); }
        if (!p7Name.isEmpty()) { playerNames.add(p7Name); }
        if (!p8Name.isEmpty()) { playerNames.add(p8Name); }


        if(playerNames.size() < 2) {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Invalid Player Names")
                .setMessage(("Please enter at least two player names."))
                .setPositiveButton("OK", (dialog, which) -> {
                    // do nothing
                })
                .show();

        } else if(containsDuplicates(playerNames)) {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Invalid Player Names")
                .setMessage(("Player names must be unique."))
                .setPositiveButton("OK", (dialog, which) -> {
                    // do nothing
                })
                .show();

        } else {
            // Create new game
            MonopolyGame.setupNewGame(playerNames);

            // Go to standings activity
            Intent i = new Intent(this, StandingsActivity.class);
            startActivityForResult(i, REQUEST_DISPLAY_STANDINGS);
        }
    }

    private void setUpSavedGameList() {

        ArrayList<MonopolyGame> savedGames = new ArrayList<>();

        try {
            savedGames = MonopolyGame.getSavedGames(getApplicationContext());

        } catch (IOException e) {
            e.printStackTrace();
            showSavedGamesError();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            showSavedGamesError();

        } finally {
            // Set up list adapter
            savedGamesList = findViewById(R.id.saved_games_list);
            adapter = new GameListAdapter(
                this,
                R.layout.list_row_saved_game,
                savedGames);
            savedGamesList.setAdapter(adapter);
        }
    }

    private boolean containsDuplicates(ArrayList<String> playerNames) {
        Set<String> set = new HashSet<>(playerNames);

        return set.size() < playerNames.size();
    }

    private void showSavedGamesError() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Error Loading Saved Games")
            .setMessage(("Please try again."))
            .setPositiveButton("OK", (dialog, which) -> {
                // do nothing
            })
            .show();
    }

    private void setUpClearAllButton() {
        ImageButton clearButton = findViewById(R.id.clear_saved_games_button);

        clearButton.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Confirm Clear")
                .setMessage(("Are you sure you want to clear all saved games? \nThis action cannot be undone."))
                .setPositiveButton("Clear", (dialog, which) -> {
                    MCFileManager.deleteSavedGames(this);
                    ((ArrayAdapter) savedGamesList.getAdapter()).clear();
                    Toast.makeText(this, "All Ga mes Deleted!", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Cancel", (dialog, which) -> {
                    // do nothing
                })
                .show();
        });
    }

    private void setUpAddPlayerButton() {
        addPlayerButton.setVisibility(View.VISIBLE);

        addPlayerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (numPlayerEdits) {
                    case 4:
                        playerFiveEdit.setVisibility(View.VISIBLE);
                        numPlayerEdits = 5;
                        break;
                    case 5:
                        playerSixEdit.setVisibility(View.VISIBLE);
                        numPlayerEdits = 6;
                        break;
                    case 6:
                        playerSevenEdit.setVisibility(View.VISIBLE);
                        numPlayerEdits = 7;
                        break;
                    case 7:
                        playerEightEdit.setVisibility(View.VISIBLE);
                        numPlayerEdits = 8;
                        addPlayerButton.setVisibility(View.GONE);
                        break;
                    default:
                        break;
                }
            }
        });
    }

    private void resetExtraPlayers() {
        addPlayerButton.setVisibility(View.VISIBLE);

        playerFiveEdit.setVisibility(View.GONE);
        playerSixEdit.setVisibility(View.GONE);
        playerSevenEdit.setVisibility(View.GONE);
        playerEightEdit.setVisibility(View.GONE);

        numPlayerEdits = 4;
    }
}
