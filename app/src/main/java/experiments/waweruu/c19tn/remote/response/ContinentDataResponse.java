package experiments.waweruu.c19tn.remote.response;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import experiments.waweruu.c19tn.remote.wrap.ContinentDataWrap;

public class ContinentDataResponse {

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("results")
    @Expose
    private int results;

    @SerializedName("continent")
    @Expose
    private String continent;

    @SerializedName("data")
    @Expose
    private ContinentDataWrap continentDataWrap;

    public String getStatus() {
        return status;
    }

    public int getResults() {
        return results;
    }

    public ContinentDataWrap getContinentDataWrap() {
        return continentDataWrap;
    }

    public String getContinent() {
        return continent;
    }
}
