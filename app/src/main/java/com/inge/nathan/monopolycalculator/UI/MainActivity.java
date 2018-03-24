package com.inge.nathan.monopolycalculator.UI;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.inge.nathan.monopolycalculator.MonopolyGame;
import com.inge.nathan.monopolycalculator.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // Private UI
    private EditText playerOneEdit;
    private EditText playerTwoEdit;
    private EditText playerThreeEdit;
    private EditText playerFourEdit;
    private Button nextButton;

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

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verifyPlayers();
            }
        });
    }

    private void verifyPlayers() {
        ArrayList<String> playerNames = new ArrayList<>();

        String p1Name = playerOneEdit.getText().toString();
        String p2Name = playerTwoEdit.getText().toString();
        String p3Name = playerThreeEdit.getText().toString();
        String p4Name = playerFourEdit.getText().toString();

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

        } else {
            // Create new game
            createGame(playerNames);

        }
    }

    /**
     *
     * @param playerNames ArrayList<String> of player names
     */
    private void createGame(ArrayList<String> playerNames) {
        MonopolyGame game = MonopolyGame.getInstance();
        game.setupNewGame(playerNames);
    }
}
