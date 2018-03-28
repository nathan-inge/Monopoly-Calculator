package com.inge.nathan.monopolycalculator.Utilities;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class MoneyTextWatcher implements TextWatcher {
    private final EditText editTextWeakReference;

    public MoneyTextWatcher(EditText editText) {
        editTextWeakReference = editText;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    private String current = "";
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if(!s.toString().equals(current)){
            editTextWeakReference.removeTextChangedListener(this);

            String cleanString = s.toString().replaceAll("[$+,+.+]", "");

            long parsed;

            try {
                parsed = Long.parseLong(cleanString);
            } catch (NumberFormatException e) {
                parsed = 0;
            }
            DecimalFormat currencyFormat = new DecimalFormat("'$',###.##");
            String formatted = currencyFormat.format(parsed);

            current = formatted;
            editTextWeakReference.setText(formatted);
            editTextWeakReference.setSelection(formatted.length());

            editTextWeakReference.addTextChangedListener(this);
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}