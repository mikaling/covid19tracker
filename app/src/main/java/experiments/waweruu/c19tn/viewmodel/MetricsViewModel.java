package experiments.waweruu.c19tn.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import javax.inject.Inject;

import experiments.waweruu.c19tn.db.entity.CountryDataEntity;
import experiments.waweruu.c19tn.repository.CountryDataRepository;
import experiments.waweruu.c19tn.util.StatusReport;

public class MetricsViewModel extends ViewModel {

    private CountryDataRepository countryDataRepository;

    @Inject
    public MetricsViewModel(CountryDataRepository countryDataRepository) {
        this.countryDataRepository = countryDataRepository;
    }

    public LiveData<List<CountryDataEntity>> getMetrics(String column) {
        return countryDataRepository.getMetrics(column);
    }

    public LiveData<StatusReport> getStatusReport() {
        return countryDataRepository.getStatusReport();
    }

    public void refreshCountryData() {
        countryDataRepository.refreshCountryData();
    }
}
