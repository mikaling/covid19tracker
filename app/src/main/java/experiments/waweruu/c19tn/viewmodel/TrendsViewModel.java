package experiments.waweruu.c19tn.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import javax.inject.Inject;

import experiments.waweruu.c19tn.db.model.CountryInfoModel;
import experiments.waweruu.c19tn.repository.HistoricalStatisticsRepository;
import experiments.waweruu.c19tn.util.StatusReport;

public class TrendsViewModel extends ViewModel {

    private static final String TAG = "TrendsViewModel";

    private HistoricalStatisticsRepository repository;

    @Inject
    public TrendsViewModel(HistoricalStatisticsRepository repository) {
        this.repository = repository;
    }

    public LiveData<List<CountryInfoModel>> getHistoricalData(String country) {
        return repository.getHistoricalData(country);
    }

    public LiveData<StatusReport> getStatusReport() {
        return repository.getStatusReport();
    }

    public void refreshHistoricalData(String country) {
        repository.refreshHistoricalStatistics(country);
    }
}
