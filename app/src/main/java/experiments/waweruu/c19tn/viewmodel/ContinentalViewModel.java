package experiments.waweruu.c19tn.viewmodel;

import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import javax.inject.Inject;

import experiments.waweruu.c19tn.local.entity.ContinentCountryDataEntity;
import experiments.waweruu.c19tn.local.entity.ContinentTotalsEntity;
import experiments.waweruu.c19tn.local.model.CountryInfoModel;
import experiments.waweruu.c19tn.local.model.CountryModel;
import experiments.waweruu.c19tn.repository.ContinentalRepository;
import experiments.waweruu.c19tn.ui.continental.SingleContinentFragment;
import experiments.waweruu.c19tn.util.StatusReport;

public class ContinentalViewModel extends ViewModel {

    private ContinentalRepository continentalRepository;

    @Inject
    public ContinentalViewModel(ContinentalRepository continentalRepository) {
        this.continentalRepository = continentalRepository;
    }

    public LiveData<ContinentTotalsEntity> getContinentTotals(String continent) {
        return continentalRepository.getContinentTotals(continent);
    }

    public LiveData<List<ContinentCountryDataEntity>> getContinentCountryData(String continent) {
        return continentalRepository.getContinentCountryData(continent);
    }

    public LiveData<List<CountryModel>> getCountryModelList(String continent) {
        return continentalRepository.getCountryModelList(continent);
    }

    public MutableLiveData<StatusReport> getStatusReport() {
        return continentalRepository.getStatusReport();
    }

    public void refreshContinentalData(String continent) {
        continentalRepository.refreshContinentalData(continent);
    }
}
