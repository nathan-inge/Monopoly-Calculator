package com.inge.nathan.monopolycalculator.Utilities;


import android.content.ClipData;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.inge.nathan.monopolycalculator.MonopolyPlayer;
import com.inge.nathan.monopolycalculator.R;

import java.util.ArrayList;

public class StandingsListAdapter extends ArrayAdapter<MonopolyPlayer> {

    public StandingsListAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public StandingsListAdapter(Context context, int resource, ArrayList<MonopolyPlayer> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.list_row_standings, null);
        }

        MonopolyPlayer player = getItem(position);

        if (player != null) {
            TextView name = v.findViewById(R.id.name_view);
            TextView ranking = v.findViewById(R.id.ranking_view);
            TextView total = v.findViewById(R.id.total_view);

            if (name != null) {
                name.setText(player.getName());
            }

            if (ranking != null) {
                ranking.setText("1st");
            }

            if (total != null) {
                total.setText("$1000");
            }
        }

        return v;
    }

}
