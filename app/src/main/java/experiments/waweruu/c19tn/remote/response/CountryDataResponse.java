package experiments.waweruu.c19tn.remote.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import experiments.waweruu.c19tn.remote.wrap.CountryDataWrap;

public class CountryDataResponse {

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("page")
    @Expose
    private int pageNumber;

    @SerializedName("results")
    @Expose
    private int results;

    @SerializedName("data")
    @Expose
    private CountryDataWrap countryDataWrap;

    public String getStatus() {
        return status;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public CountryDataWrap getCountryDataWrap() {
        return countryDataWrap;
    }
}
