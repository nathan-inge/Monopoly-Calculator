package com.inge.nathan.monopolycalculator.UI;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.inge.nathan.monopolycalculator.MonopolyObjects.MonopolyGame;
import com.inge.nathan.monopolycalculator.R;
import com.inge.nathan.monopolycalculator.Utilities.MCPreferencesManager;
import com.inge.nathan.monopolycalculator.Utilities.MonopolyConstants;
import com.inge.nathan.monopolycalculator.Utilities.MCExceptions.NoCurrentGameException;
import com.inge.nathan.monopolycalculator.Lists.StandingsListAdapter;

import java.io.IOException;

public class StandingsActivity extends AppCompatActivity {

    // Private UI
    private ListView playerStandingsList;
    private Toolbar customToolbar;
    private ImageButton backButton;

    private MonopolyGame currentGame;
    private boolean hasProVersion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_standings);

        // Initialize UI
        playerStandingsList = findViewById(R.id.standings_list);
        customToolbar = findViewById(R.id.custom_standings_toolbar);
        setSupportActionBar(customToolbar);
        customToolbar.setOverflowIcon(ContextCompat.getDrawable(this, R.drawable.toolbar_menu));
        backButton = findViewById(R.id.back_button);

        backButton.setOnClickListener(v -> finish());

        // Get current game
        try {
            currentGame = MonopolyGame.getCurrentGame();
        } catch(NoCurrentGameException ex) {
            NoCurrentGameException.showToast(getApplicationContext());
            finish();
        }

        TextView titleView = findViewById(R.id.activity_title);
        String title = (currentGame.getName() != null) ? currentGame.getName() : "Monopoly Calculator";
        titleView.setText(title);

        currentGame.sortStandings();

        // Set up list adapter
        StandingsListAdapter adapter = new StandingsListAdapter(
            this,
            R.layout.list_row_standings,
            currentGame.getPlayers());

        playerStandingsList.setAdapter(adapter);

        hasProVersion = MCPreferencesManager.getProStatus(getApplicationContext());

        if(hasProVersion) {
            LinearLayout adLinearLayout = findViewById(R.id.banner_ad_standings);
            adLinearLayout.setVisibility(View.GONE);

            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) playerStandingsList.getLayoutParams();
            params.bottomMargin = 0;
        } else {
            MobileAds.initialize(this, "ca-app-pub-1213633519254149~9428094547");
            AdView adView = findViewById(R.id.adViewStandings);
            AdRequest adRequest = new AdRequest.Builder().build();
            adView.loadAd(adRequest);
        }
    }

    @SuppressLint("RestrictedApi")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_standings_options, menu);

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

            case R.id.restart_menu_item:
                restart();
                return true;

            case R.id.rules_menu_item:
                Intent j = new Intent(this, RulesActivity.class);
                startActivity(j);
                return true;

            case R.id.pro_menu_item:
                Intent k = new Intent(this, GoProActivity.class);
                startActivity(k);
                return true;

            case R.id.save_game_menu_item:
                saveGame();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if((requestCode == MonopolyConstants.REQUEST_EDIT_PLAYER) && (resultCode == MonopolyConstants.PLAYER_EDITTED)) {
            // Refresh info
            finish();
            overridePendingTransition(0, 0);
            startActivity(getIntent());
            overridePendingTransition(0,0);
        }
    }

    private void restart() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Restart calculations?")
            .setMessage(("All entered information for this game will be lost."))
            .setPositiveButton("Restart", (dialog, which) -> {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            })
            .setNegativeButton("Cancel", (dialog, which) -> {
                // do nothing
            })
            .show();
    }

    private void saveGame() {
        if(hasProVersion) {
            final EditText input = new EditText(this);
            input.setHint("Game name");
            input.setFilters( new InputFilter[] { new InputFilter.LengthFilter(15) } );

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Save as...")
                .setView(input)
                .setPositiveButton("Save", (dialog, which) -> {
                    if(input.getText().toString().trim().isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Name cannot be empty!", Toast.LENGTH_SHORT).show();
                    } else {
                        performSave(input.getText().toString());
                    }
                })
                .setNegativeButton("Cancel", (dialog, which) -> {
                    // do nothing
                })
                .show();

        } else {
            Toast.makeText(
                getApplicationContext(),
                "Upgrade to PRO to save games!",
                Toast.LENGTH_SHORT).show();
        }
    }

    private void performSave(String gameName) {
        try {
            MonopolyGame.saveCurrentGame(getApplicationContext(), gameName);
            Toast.makeText(getApplicationContext(), "Game Saved!" , Toast.LENGTH_SHORT).show();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            showSavedGamesError();

        } catch (IOException e) {
            e.printStackTrace();
            showSavedGamesError();
        }
    }

    private void showSavedGamesError() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Error Saving Game")
            .setMessage(("Please try again."))
            .setPositiveButton("OK", (dialog, which) -> {
                // do nothing
            })
            .show();
    }

}
