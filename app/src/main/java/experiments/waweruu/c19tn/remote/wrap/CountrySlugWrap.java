package experiments.waweruu.c19tn.remote.wrap;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import experiments.waweruu.c19tn.remote.model.CountrySlugModel;

public class CountrySlugWrap {

    @SerializedName("countries")
    @Expose
    private List<CountrySlugModel> modelList;

    public List<CountrySlugModel> getModelList() {
        return modelList;
    }
}
