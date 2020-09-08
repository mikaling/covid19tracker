package experiments.waweruu.c19tn.remote.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CountryDataModel {

    /* SerializedName is an annotation that gives the name of the variable in the response from the
     * API. Expose atm I have no idea what it does, but the jsonschema2pojo website includes it,
     * so ¯\_(ツ)_/¯ */

    /* Sample representation of this class in JSON
     * {
     * "country":"Afghanistan",
     * "totalConfirmed":18969,
     * "totalDeaths":309,
     * "totalRecovered":1762,
     * "date":"2020-06-06",
     * "recoveryRate":61.48,
     * "deathRate":2.82
     * } */

    @SerializedName("country")
    @Expose
    private String country;

    @SerializedName("totalConfirmed")
    @Expose
    private int totalConfirmed;

    @SerializedName("totalDeaths")
    @Expose
    private int totalDeaths;

    @SerializedName("totalRecovered")
    @Expose
    private int totalRecovered;

    @SerializedName("recoveryRate")
    @Expose
    private float recoveryRate;

    @SerializedName("deathRate")
    @Expose
    private float deathRate;

    @SerializedName("date")
    @Expose
    private String date;

    public String getCountry() {
        return country;
    }

    public int getTotalConfirmed() {
        return totalConfirmed;
    }

    public int getTotalDeaths() {
        return totalDeaths;
    }

    public int getTotalRecovered() {
        return totalRecovered;
    }

    public float getRecoveryRate() {
        return recoveryRate;
    }

    public float getDeathRate() {
        return deathRate;
    }

    public String getDate() {
        return date;
    }
}
