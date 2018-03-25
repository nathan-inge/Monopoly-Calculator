package com.inge.nathan.monopolycalculator.UI;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.inge.nathan.monopolycalculator.MonopolyGame;
import com.inge.nathan.monopolycalculator.R;
import com.inge.nathan.monopolycalculator.Utilities.Constants;
import com.inge.nathan.monopolycalculator.Utilities.StandingsListAdapter;

public class StandingsActivity extends AppCompatActivity {

    // Private UI
    private ListView playerStandingsList;

    private MonopolyGame currentGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_standings);

        // Initialize UI
        playerStandingsList = findViewById(R.id.standings_list);

        // Get current game
        currentGame = MonopolyGame.getInstance();

        // Set up list adapter
        StandingsListAdapter adapter = new StandingsListAdapter(
            this,
            R.layout.list_row_standings,
            currentGame.getPlayers());

        playerStandingsList.setAdapter(adapter);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if((requestCode == Constants.REQUEST_EDIT_PLAYER) && (resultCode == Constants.PLAYER_EDITTED)) {
            // Refresh info
            finish();
            overridePendingTransition(0, 0);
            startActivity(getIntent());
            overridePendingTransition(0,0);
        }
    }

}
