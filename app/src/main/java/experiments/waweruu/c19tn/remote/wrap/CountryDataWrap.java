package experiments.waweruu.c19tn.remote.wrap;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import experiments.waweruu.c19tn.remote.model.CountryDataModel;

public class CountryDataWrap {

    @SerializedName("countries")
    @Expose
    private ArrayList<CountryDataModel> countryDataModelList;

    public ArrayList<CountryDataModel> getCountryDataModelList() {
        return countryDataModelList;
    }
}
