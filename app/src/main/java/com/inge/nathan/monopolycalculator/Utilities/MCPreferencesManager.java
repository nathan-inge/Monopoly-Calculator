package com.inge.nathan.monopolycalculator.Utilities;


import android.content.Context;
import android.content.SharedPreferences;

import com.inge.nathan.monopolycalculator.R;

public final class MCPreferencesManager {

    private MCPreferencesManager() { }

    public static void upgradeToPro(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
            context.getResources().getString(R.string.pro_pref_key),
            Context.MODE_PRIVATE);

        SharedPreferences.Editor edit = sharedPreferences.edit();

        edit.putBoolean(context.getResources().getString(R.string.pro_bool), true);
        edit.apply();
    }

    public static boolean getProStatus(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
            context.getResources().getString(R.string.pro_pref_key),
            Context.MODE_PRIVATE);

        return sharedPreferences.getBoolean(
            context.getResources().getString(R.string.pro_bool), false);
    }
}
