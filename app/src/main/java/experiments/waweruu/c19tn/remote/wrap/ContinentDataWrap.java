package experiments.waweruu.c19tn.remote.wrap;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import experiments.waweruu.c19tn.remote.model.ContinentTotalsModel;
import experiments.waweruu.c19tn.remote.model.CountryDataModel;

public class ContinentDataWrap {

    @SerializedName("countries")
    @Expose
    private List<CountryDataModel> countryDataModelList;

    @SerializedName("totals")
    @Expose
    private ContinentTotalsModel continentTotalsModel;

    public List<CountryDataModel> getCountryDataModelList() {
        return countryDataModelList;
    }

    public ContinentTotalsModel getContinentTotalsModel() {
        return continentTotalsModel;
    }
}
