package experiments.waweruu.c19tn.remote.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HistoricalStatisticsModel {

    @SerializedName("Country")
    @Expose
    private String country;

    @SerializedName("Confirmed")
    @Expose
    private int confirmed;

    @SerializedName("Deaths")
    @Expose
    private int deaths;

    @SerializedName("Recovered")
    @Expose
    private int recovered;

    @SerializedName("Active")
    @Expose
    private int active;

    @SerializedName("Date")
    @Expose
    private String date;

    public String getCountry() {
        return country;
    }

    public int getConfirmed() {
        return confirmed;
    }

    public int getDeaths() {
        return deaths;
    }

    public int getRecovered() {
        return recovered;
    }

    public int getActive() {
        return active;
    }

    public String getDate() {
        return date;
    }
}
