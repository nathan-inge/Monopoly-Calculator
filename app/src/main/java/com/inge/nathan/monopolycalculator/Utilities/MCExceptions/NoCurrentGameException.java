package com.inge.nathan.monopolycalculator.Utilities.MCExceptions;

import android.widget.Toast;
import android.content.Context;

/**
 * Exception thrown when there is not current game initialized
 */

public class NoCurrentGameException extends Exception {

    public static void showToast(Context context) {
        Toast.makeText(context,"Reference to current game lost.",Toast.LENGTH_SHORT).show();
    }
}
