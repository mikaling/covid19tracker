package experiments.waweruu.c19tn.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import javax.inject.Inject;

import experiments.waweruu.c19tn.repository.CountrySlugRepository;
import experiments.waweruu.c19tn.util.Status;
import experiments.waweruu.c19tn.util.StatusReport;

public class LocalViewModel extends ViewModel {

    private static final String TAG = "LocalViewModel";

    private CountrySlugRepository countrySlugRepository;

    @Inject
    public LocalViewModel(CountrySlugRepository countrySlugRepository) {
        this.countrySlugRepository = countrySlugRepository;
    }

    public void refreshCountrySlugs() {
        countrySlugRepository.refreshCountrySlugs();
    }

    public LiveData<List<String>> getCountryNames() {
        return countrySlugRepository.getCountryNames();
    }

    public MutableLiveData<StatusReport> getStatusReport() {
        return countrySlugRepository.getStatusReport();
    }
}
