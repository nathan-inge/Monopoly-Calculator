package com.inge.nathan.monopolycalculator.UI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.inge.nathan.monopolycalculator.MonopolyGame;
import com.inge.nathan.monopolycalculator.MonopolyPlayer;
import com.inge.nathan.monopolycalculator.R;

public class EditPlayerActivity extends AppCompatActivity {

    private MonopolyPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_player);

        // Get player
        Intent i = getIntent();
        int pos = i.getIntExtra("playerIndex", 0);
        player = MonopolyGame.getInstance().getPlayers().get(pos);

        // Set up UI with player info
        TextView title = findViewById(R.id.activity_title);
        title.setText(player.getName());

    }
}
