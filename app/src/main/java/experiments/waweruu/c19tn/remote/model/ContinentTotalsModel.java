package experiments.waweruu.c19tn.remote.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ContinentTotalsModel {
    @SerializedName("totalConfirmed")
    @Expose
    private int totalConfirmed;

    @SerializedName("totalRecoveries")
    @Expose
    private int totalRecoveries;

    @SerializedName("totalDeaths")
    @Expose
    private int totalDeaths;

    public int getTotalConfirmed() {
        return totalConfirmed;
    }

    public int getTotalRecoveries() {
        return totalRecoveries;
    }

    public int getTotalDeaths() {
        return totalDeaths;
    }
}
