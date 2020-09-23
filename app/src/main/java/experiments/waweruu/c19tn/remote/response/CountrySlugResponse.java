package experiments.waweruu.c19tn.remote.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import experiments.waweruu.c19tn.remote.wrap.CountrySlugWrap;

public class CountrySlugResponse {
    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("results")
    @Expose
    private int results;

    @SerializedName("data")
    @Expose
    private CountrySlugWrap wrap;

    public String getStatus() {
        return status;
    }

    public int getResults() {
        return results;
    }

    public CountrySlugWrap getWrap() {
        return wrap;
    }
}
