package experiments.waweruu.c19tn.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import javax.inject.Inject;

import experiments.waweruu.c19tn.db.model.CountryInfoModel;
import experiments.waweruu.c19tn.repository.CountryDataRepository;
import experiments.waweruu.c19tn.util.StatusReport;

public class ComparisonViewModel extends ViewModel {

    private static final String TAG = "ComparisonViewModel";

    private CountryDataRepository countryDataRepository;

    @Inject
    public ComparisonViewModel(CountryDataRepository countryDataRepository) {
        this.countryDataRepository = countryDataRepository;
    }

    public LiveData<List<CountryInfoModel>> getCountryInfo() {
        return countryDataRepository.getComparisonCountryInfo();
    }

    public void refreshCountryData() {
        countryDataRepository.refreshCountryData();
    }

    public LiveData<StatusReport> getStatusReport() {
        return countryDataRepository.getStatusReport();
    }


}
