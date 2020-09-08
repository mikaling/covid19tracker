package experiments.waweruu.c19tn.remote.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GlobalStatisticsModel {

    @SerializedName("NewConfirmed")
    @Expose
    private int newConfirmed;

    @SerializedName("TotalConfirmed")
    @Expose
    private int totalConfirmed;

    @SerializedName("NewDeaths")
    @Expose
    private int newDeaths;

    @SerializedName("TotalDeaths")
    @Expose
    private int totalDeaths;

    @SerializedName("NewRecovered")
    @Expose
    private int newRecovered;

    @SerializedName("TotalRecovered")
    @Expose
    private int totalRecovered;

    public GlobalStatisticsModel(int newConfirmed, int totalConfirmed, int newDeaths, int totalDeaths, int newRecovered, int totalRecovered) {
        this.newConfirmed = newConfirmed;
        this.totalConfirmed = totalConfirmed;
        this.newDeaths = newDeaths;
        this.totalDeaths = totalDeaths;
        this.newRecovered = newRecovered;
        this.totalRecovered = totalRecovered;
    }

    public int getNewConfirmed() {
        return newConfirmed;
    }

    public int getTotalConfirmed() {
        return totalConfirmed;
    }

    public int getNewDeaths() {
        return newDeaths;
    }

    public int getTotalDeaths() {
        return totalDeaths;
    }

    public int getNewRecovered() {
        return newRecovered;
    }

    public int getTotalRecovered() {
        return totalRecovered;
    }
}
