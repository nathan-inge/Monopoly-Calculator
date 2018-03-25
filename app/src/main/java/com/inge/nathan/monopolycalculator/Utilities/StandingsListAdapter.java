package com.inge.nathan.monopolycalculator.Utilities;


import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.inge.nathan.monopolycalculator.MonopolyPlayer;
import com.inge.nathan.monopolycalculator.R;
import com.inge.nathan.monopolycalculator.UI.EditPlayerActivity;
import com.inge.nathan.monopolycalculator.UI.StandingsActivity;

import java.util.ArrayList;

public class StandingsListAdapter extends ArrayAdapter<MonopolyPlayer> {

    public StandingsListAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public StandingsListAdapter(Context context, int resource, ArrayList<MonopolyPlayer> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(final int position, final View convertView, @NonNull ViewGroup parent) {

        View rankingsView = convertView;

        if (rankingsView == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            rankingsView = vi.inflate(R.layout.list_row_standings, null);
        }

        MonopolyPlayer player = getItem(position);

        if (player != null) {
            TextView nameView = rankingsView.findViewById(R.id.name_view);
            TextView rankingView = rankingsView.findViewById(R.id.ranking_view);
            TextView totalView = rankingsView.findViewById(R.id.total_view);
            Button editButton = rankingsView.findViewById(R.id.edit_player_button);

            if (nameView != null) nameView.setText(player.getName());

            if (rankingView != null) rankingView.setText("1st");

            if (totalView != null) totalView.setText("$1000");

            if (editButton != null) {
                editButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(getContext(), EditPlayerActivity.class);
                        i.putExtra("playerIndex", position);
                        getContext().startActivity(i);
                    }
                });
            }
        }

        return rankingsView;
    }

}
