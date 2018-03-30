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
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.inge.nathan.monopolycalculator.MonopolyObjects.MonopolyPlayer;
import com.inge.nathan.monopolycalculator.MonopolyObjects.MonopolyProperty;
import com.inge.nathan.monopolycalculator.R;
import com.inge.nathan.monopolycalculator.Utilities.MonopolyConstants;

import java.util.ArrayList;

public class PropertiesListAdapter extends ArrayAdapter<MonopolyProperty> {

    public ArrayList<MonopolyProperty> selectedProperties;

    public PropertiesListAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
        selectedProperties = new ArrayList<>();
    }

    public PropertiesListAdapter(Context context, int resource, ArrayList<MonopolyProperty> items) {
        super(context, resource, items);
        selectedProperties = new ArrayList<>();
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
            valueView.setVisibility(View.INVISIBLE);

            CheckBox ownedCheck = propertiesView.findViewById(R.id.owned_check);

            // Property Detail UI
            RelativeLayout propertyDetailView = propertiesView.findViewById(R.id.property_detail_view);
            propertyDetailView.setVisibility(View.INVISIBLE);

            TextView numHouses = propertyDetailView.findViewById(R.id.house_count);
            ImageButton subHouse = propertyDetailView.findViewById(R.id.minus_house_button);
            ImageButton addHouse = propertyDetailView.findViewById(R.id.house_add_button);
            CheckBox hotelCheck = propertyDetailView.findViewById(R.id.hotel_check);
            CheckBox mortgagedCheck = propertyDetailView.findViewById(R.id.mortgaged_check);


            if (property.isOwned()) {
                ownedCheck.setChecked(true);
                valueView.setVisibility(View.VISIBLE);

                propertyDetailView.setVisibility(View.VISIBLE);

                updateDetails(property, numHouses, hotelCheck, mortgagedCheck, valueView);

                if (!selectedProperties.contains(property)) {
                    selectedProperties.add(property);
                }
            }

            if(property.getId() >= MonopolyConstants.READING_RR) {
                propertyDetailView.setVisibility(View.GONE);
            }

            ownedCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked && !selectedProperties.contains(property)) {
                        selectedProperties.add(property);
                        valueView.setVisibility(View.VISIBLE);

                        if(property.getId() < MonopolyConstants.READING_RR) {
                            propertyDetailView.setVisibility(View.VISIBLE);
                        }

                    } else if (!isChecked && selectedProperties.contains(property)) {
                        selectedProperties.remove(property);
                        valueView.setVisibility(View.INVISIBLE);

                        if(property.getId() < MonopolyConstants.READING_RR) {
                            propertyDetailView.setVisibility(View.INVISIBLE);
                        }
                    }
                }
            });

            hotelCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    property.setHasHotel(isChecked);
                    updateDetails(property, numHouses, hotelCheck, mortgagedCheck, valueView);
                }
            });

            mortgagedCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    property.setIsMortgaged(isChecked);
                    updateDetails(property, numHouses, hotelCheck, mortgagedCheck, valueView);
                }
            });

            subHouse.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int currentNumHouses = property.getNumHouses();
                    if(currentNumHouses > 0) {
                        property.setNumHouses(currentNumHouses - 1);
                        updateDetails(property, numHouses, hotelCheck, mortgagedCheck, valueView);
                    }
                }
            });

            addHouse.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int currentNumHouses = property.getNumHouses();
                    if(currentNumHouses < 4) {
                        property.setNumHouses(currentNumHouses + 1);
                        updateDetails(property, numHouses, hotelCheck, mortgagedCheck, valueView);
                    }
                }
            });
        }

        return propertiesView;
    }

    @Override
    public int getViewTypeCount() {

        return getCount();
    }

    @Override
    public int getItemViewType(int position) {

        return position;
    }

    private void updateDetails(MonopolyProperty property,
                               TextView numHouses,
                               CheckBox hotelCheck,
                               CheckBox mortgagedCheck,
                               TextView valueView) {

        numHouses.setText(String.valueOf(property.getNumHouses()));
        hotelCheck.setChecked(property.hasHotel());
        mortgagedCheck.setChecked(property.isMortgaged());
        valueView.setText(MonopolyPlayer.formatMoney(property.getTotalValue()));

    }
}
