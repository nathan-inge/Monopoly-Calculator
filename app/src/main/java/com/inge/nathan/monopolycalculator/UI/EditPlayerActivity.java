package com.inge.nathan.monopolycalculator.UI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.inge.nathan.monopolycalculator.MonopolyGame;
import com.inge.nathan.monopolycalculator.MonopolyPlayer;
import com.inge.nathan.monopolycalculator.R;
import com.inge.nathan.monopolycalculator.Utilities.MonopolyConstants;
import com.inge.nathan.monopolycalculator.Utilities.MoneyTextWatcher;

public class EditPlayerActivity extends AppCompatActivity {

    private MonopolyPlayer player;

    // Private UI
    private EditText cashEdit;
    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_player);

        // Initialize UI
        cashEdit = findViewById(R.id.cash_edit);
        cashEdit.addTextChangedListener(new MoneyTextWatcher(cashEdit));
        saveButton = findViewById(R.id.save_button);

        // Get player
        Intent i = getIntent();
        int pos = i.getIntExtra("playerIndex", 0);
        player = MonopolyGame.getCurrentGame().getPlayers().get(pos);

        // Set up UI with player info
        TextView title = findViewById(R.id.activity_title);
        title.setText(player.getName());
        cashEdit.setHint(MonopolyPlayer.formatMoney(player.getCashValue()));

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyEdits();
            }
        });
    }

    private void verifyEdits() {
        String cashInput = cashEdit.getText().toString();

        if (cashInput.isEmpty()) {
            finish();
        } else {
            String cleanString = cashInput.replaceAll("[$+,+.+]", "");
            player.setCashValue(Long.parseLong(cleanString));
            setResult(MonopolyConstants.PLAYER_EDITTED);
            finish();
        }

    }
}
