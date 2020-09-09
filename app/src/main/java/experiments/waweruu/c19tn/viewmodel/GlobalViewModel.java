package experiments.waweruu.c19tn.viewmodel;

import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import javax.inject.Inject;

import experiments.waweruu.c19tn.R;
import experiments.waweruu.c19tn.db.entity.CountryDataEntity;
import experiments.waweruu.c19tn.db.entity.GlobalStatisticsEntity;
import experiments.waweruu.c19tn.db.model.CountryInfoModel;
import experiments.waweruu.c19tn.repository.CountryDataRepository;
import experiments.waweruu.c19tn.repository.GlobalStatisticsRepository;
import experiments.waweruu.c19tn.util.StatusReport;

public class GlobalViewModel extends ViewModel {

    private static final String TAG = "GlobalVM";

    private GlobalStatisticsRepository globalStatisticsRepository;
    private CountryDataRepository countryDataRepository;
    private LiveData<List<CountryInfoModel>> countryInfoList;

    @Inject
    public GlobalViewModel(GlobalStatisticsRepository globalStatisticsRepository, CountryDataRepository countryDataRepository) {
        this.globalStatisticsRepository = globalStatisticsRepository;
        this.countryDataRepository = countryDataRepository;
//        this.countryInfoList = countryDataRepository.getCountryInfo();
    }

    public LiveData<GlobalStatisticsEntity> getGlobalStatistics() {
        return  globalStatisticsRepository.getGlobalStatistics();
    }

    public MutableLiveData<StatusReport> getGlobalStatisticsStatusReport() {
        return globalStatisticsRepository.getGlobalStatisticsStatusReport();
    }

    public LiveData<List<CountryDataEntity>> getCountryData() {
        return countryDataRepository.getCountryData();
    }

    public LiveData<List<CountryInfoModel>> getCountryInfo() {
        return countryDataRepository.getCountryInfo();
//        return this.countryInfoList;
    }

    public MutableLiveData<StatusReport> getCountryDataStatusReport() {
        return countryDataRepository.getStatusReport();
    }

    public void showErrorSnackbar(View view) {
        //TODO: move this method to individual fragments or set to work on ui/main thread
        Snackbar.make(view, "Error loading data. Check your internet connection.",
                BaseTransientBottomBar.LENGTH_INDEFINITE)
                .setAction("Retry", view1 -> {
                    getCountryInfo();
                    getGlobalStatistics();
                })
                .setAnchorView(R.id.bottom_navigation)
                .show();
    }
}
