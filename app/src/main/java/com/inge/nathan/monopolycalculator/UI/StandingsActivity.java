package com.inge.nathan.monopolycalculator.UI;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.inge.nathan.monopolycalculator.MonopolyObjects.MonopolyGame;
import com.inge.nathan.monopolycalculator.R;
import com.inge.nathan.monopolycalculator.Utilities.MonopolyConstants;
import com.inge.nathan.monopolycalculator.Utilities.NoCurrentGameException;
import com.inge.nathan.monopolycalculator.Lists.StandingsListAdapter;

public class StandingsActivity extends AppCompatActivity {

    // Private UI
    private ListView playerStandingsList;
    private Toolbar customToolbar;

    private MonopolyGame currentGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_standings);

        // Initialize UI
        playerStandingsList = findViewById(R.id.standings_list);
        customToolbar = findViewById(R.id.custom_home_toolbar);
        setSupportActionBar(customToolbar);
        customToolbar.setOverflowIcon(ContextCompat.getDrawable(this, R.drawable.toolbar_menu));

        // Get current game
        try {
            currentGame = MonopolyGame.getCurrentGame();
        } catch(NoCurrentGameException ex) {
            NoCurrentGameException.showToast(getApplicationContext());
            finish();
        }

        currentGame.sortStandings();

        // Set up list adapter
        StandingsListAdapter adapter = new StandingsListAdapter(
            this,
            R.layout.list_row_standings,
            currentGame.getPlayers());

        playerStandingsList.setAdapter(adapter);
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
                Toast.makeText(this, "Developed by Nathan Inge", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.restart_menu_item:
                restart();
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

    public void restart() {

    }

}
