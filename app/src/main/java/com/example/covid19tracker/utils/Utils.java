package com.example.covid19tracker.utils;

import android.content.Context;
import android.widget.Toast;

public class Utils {

    /* This class contains utility methods to reduce repeating code
     * e.g. showing a Toast message */

    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
