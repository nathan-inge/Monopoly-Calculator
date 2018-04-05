package com.inge.nathan.monopolycalculator.Utilities.MCExceptions;

import android.content.Context;
import android.widget.Toast;

/**
 * Exception thrown when there is error getting saved files
 */

public class GetSavedGamesException extends Exception {
    private Exception originalExeption;

    public GetSavedGamesException(Exception originalException) {
        this.originalExeption = originalException;
    }

//    public static void showToast(Context context) {
//        Toast.makeText(context,"Reference to current game lost.",Toast.LENGTH_SHORT).show();
//    }
}
