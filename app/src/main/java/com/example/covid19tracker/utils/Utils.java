package com.example.covid19tracker.utils;

import android.content.Context;
import android.widget.Toast;

public class Utils {

    /* This class contains utility methods to reduce repeating code
     * e.g. showing a Toast message
     * It also contains variables accessed by multiple classes within the
     * android module */

    /* shows a Toast message */
    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    /* Status field in API response */
    public static final String RESPONSE_SUCCESS = "success";

    /* Group IDs to classify the responses from the endpoint aliases */
    public static final int CONFIRMED_GROUP_ID = 100;
    public static final int RECOVERED_GROUP_ID = 101;
    public static final int DEATHS_GROUP_ID = 102;

    /* Formats used in Strings shown in views */
    public static final String CONFIRMED_FORMAT = "%,d confirmed";
    public static final String RECOVERED_FORMAT = "%,d recovered (%.2f%% recovery rate)";
    public static final String DEATHS_FORMAT = "%,d dead (%.2f%% death rate)";
    public static final String SEPARATOR_FORMAT = "%,d";

    /* API URLs */
    public static final String API_BASE_URL = "https://api.covid19api.com/";
    public static final String API_BASE_URL_V2 =
            "https://api.covid-updates.live/";
}
