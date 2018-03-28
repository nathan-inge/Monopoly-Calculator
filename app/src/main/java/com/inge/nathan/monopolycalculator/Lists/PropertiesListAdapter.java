package com.inge.nathan.monopolycalculator.Lists;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.inge.nathan.monopolycalculator.MonopolyPlayer;
import com.inge.nathan.monopolycalculator.MonopolyProperty;
import com.inge.nathan.monopolycalculator.R;

import java.util.ArrayList;

public class PropertiesListAdapter extends ArrayAdapter<MonopolyProperty> {

    public ArrayList<MonopolyProperty> selectedProperties;

    public PropertiesListAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
        selectedProperties = new ArrayList<>();
    }

    public PropertiesListAdapter(Context context, int resource, ArrayList<MonopolyProperty> items, ArrayList<MonopolyProperty> ownedProperties) {
        super(context, resource, items);
        selectedProperties = new ArrayList<>();
        selectedProperties.addAll(ownedProperties);
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

            TextView valueView = propertiesView.findViewById(R.id.property_value_view);
            valueView.setText(MonopolyPlayer.formatMoney(property.getTotalValue()));

            CheckBox ownedCheck = propertiesView.findViewById(R.id.owned_check);

            // Render already owned properties
//            if (selectedProperties.contains(property)) {
//                valueView.setVisibility(View.VISIBLE);
//                ownedCheck.setChecked(true);
//            } else {
//                valueView.setVisibility(View.INVISIBLE);
//                ownedCheck.setChecked(false);
//            }

            ownedCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked && !selectedProperties.contains(property)) {
                        selectedProperties.add(property);
                        valueView.setVisibility(View.VISIBLE);
                    } else if (!isChecked && selectedProperties.contains(property)) {
                        selectedProperties.remove(property);
                        valueView.setVisibility(View.INVISIBLE);
                    }
                }
            });
        }

        return propertiesView;
    }
}
