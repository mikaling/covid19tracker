package experiments.waweruu.c19tn.util;

import android.app.Service;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class Util {

    public static final String[] EA_COUNTRIES = {"Burundi", "Kenya", "Rwanda",
            "Tanzania, United Republic of", "Uganda"};

    public static final String SUCCESS_MESSAGE = "success";

    public static final String BASE_URL = "https://api.covid-updates.live/";

    public static final String CONTINENT_AFRICA = "Africa";
    public static final String CONTINENT_ASIA = "Asia";
    public static final String CONTINENT_EUROPE = "Europe";
    public static final String CONTINENT_NORTH_AMERICA = "NorthAmerica";
    public static final String CONTINENT_SOUTH_AMERICA = "SouthAmerica";
    public static final String CONTINENT_OCEANIA = "Oceania";

    public static final String COLUMN_AFRICA = "AFRICA";
    public static final String COLUMN_ASIA = "ASIA";
    public static final String COLUMN_EUROPE = "EUROPE";
    public static final String COLUMN_NORTH_AMERICA = "NORTH AMERICA";
    public static final String COLUMN_SOUTH_AMERICA = "SOUTH AMERICA";
    public static final String COLUMN_OCEANIA = "OCEANIA";

    public static final String AFRICA_REGION_CODE = "002";
    public static final String EUROPE_REGION_CODE = "150";
    public static final String ASIA_REGION_CODE = "142";
    public static final String NORTH_AMERICA_REGION_CODE = "021";
    public static final String SOUTH_AMERICA_REGION_CODE = "005";
    public static final String OCEANIA_REGION_CODE = "009";

    /* Formats used in Strings shown in views */
    public static final String CONFIRMED_FORMAT = "%,d confirmed";
    public static final String RECOVERED_FORMAT = "%,d recovered (%.2f%% recovery rate)";
    public static final String DEATHS_FORMAT = "%,d dead (%.2f%% death rate)";
    public static final String SEPARATOR_FORMAT = "%,d";

    public static String getFormattedString(int number) {
        return String.format(Locale.ENGLISH, SEPARATOR_FORMAT, number);
    }

    public static String getContinentFromCode(String continentCode) {
        String continent = null;
        switch (continentCode) {
            case Util.AFRICA_REGION_CODE:
                continent = Util.CONTINENT_AFRICA;
                break;

            case Util.EUROPE_REGION_CODE:
                continent = Util.CONTINENT_EUROPE;
                break;

            case Util.ASIA_REGION_CODE:
                continent = Util.CONTINENT_ASIA;
                break;

            case Util.NORTH_AMERICA_REGION_CODE:
                continent = Util.CONTINENT_NORTH_AMERICA;
                break;

            case Util.OCEANIA_REGION_CODE:
                continent = Util.CONTINENT_OCEANIA;
                break;

            case Util.SOUTH_AMERICA_REGION_CODE:
                continent = Util.CONTINENT_SOUTH_AMERICA;
                break;
        }
        return continent;
    }

    public static String getContinentColumn(String continent) {
        String databaseContinent = null;
        switch (continent) {
            case Util.CONTINENT_AFRICA:
                databaseContinent = Util.COLUMN_AFRICA;
                break;

            case Util.CONTINENT_ASIA:
                databaseContinent = Util.COLUMN_ASIA;
                break;

            case Util.CONTINENT_EUROPE:
                databaseContinent = Util.COLUMN_EUROPE;
                break;

            case Util.CONTINENT_NORTH_AMERICA:
                databaseContinent = Util.COLUMN_NORTH_AMERICA;
                break;

            case Util.CONTINENT_OCEANIA:
                databaseContinent = Util.COLUMN_OCEANIA;
                break;

            case Util.CONTINENT_SOUTH_AMERICA:
                databaseContinent = Util.COLUMN_SOUTH_AMERICA;
                break;
        }
        return databaseContinent;
    }


    public static int getFormattedTime() {
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd", Locale.ENGLISH);
        String time = dateFormat.format(date);
        return Integer.parseInt(time);
    }

    public static boolean doesNetworkConnectionExist(Context context) {
        boolean isConnectedWifi = false;
        boolean isConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    isConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    isConnectedMobile = true;
        }
        return isConnectedWifi || isConnectedMobile;
    }
}
