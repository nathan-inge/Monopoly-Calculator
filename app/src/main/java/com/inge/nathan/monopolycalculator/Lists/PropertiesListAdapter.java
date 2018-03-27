package com.inge.nathan.monopolycalculator.Lists;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.inge.nathan.monopolycalculator.MonopolyPlayer;
import com.inge.nathan.monopolycalculator.MonopolyProperty;
import com.inge.nathan.monopolycalculator.R;
import com.inge.nathan.monopolycalculator.UI.EditPlayerActivity;

import java.util.ArrayList;

public class PropertiesListAdapter extends ArrayAdapter<MonopolyProperty> {

    public PropertiesListAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public PropertiesListAdapter(Context context, int resource, ArrayList<MonopolyProperty> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(final int position, final View convertView, @NonNull ViewGroup parent) {

        View propertiesView = convertView;

        if (propertiesView == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            propertiesView = vi.inflate(R.layout.list_row_properties, null);
        }

        MonopolyProperty property = getItem(position);

        if (property != null) {
            TextView nameView = propertiesView.findViewById(R.id.property_name_view);
            nameView.setText(property.getName());

            CardView cardView = propertiesView.findViewById(R.id.property_card_view);
            cardView.setCardBackgroundColor(ContextCompat.getColor(getContext(), property.getColor()));
        }

        return propertiesView;
    }
}
