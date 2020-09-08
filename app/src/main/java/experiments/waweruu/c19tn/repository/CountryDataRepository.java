package experiments.waweruu.c19tn.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import experiments.waweruu.c19tn.AppExecutors;
import experiments.waweruu.c19tn.local.dao.CountryDataDao;
import experiments.waweruu.c19tn.local.entity.CountryDataEntity;
import experiments.waweruu.c19tn.local.model.CountryInfoModel;
import experiments.waweruu.c19tn.remote.model.CountryDataModel;
import experiments.waweruu.c19tn.remote.response.CountryDataResponse;
import experiments.waweruu.c19tn.remote.retrofit.ApiService;
import experiments.waweruu.c19tn.util.Status;
import experiments.waweruu.c19tn.util.StatusReport;
import experiments.waweruu.c19tn.util.Util;
import io.reactivex.rxjava3.core.Observable;

@Singleton
public class CountryDataRepository {

    private static final String TAG = "GlobalMapRepo";

    private AppExecutors appExecutors;
    private ApiService apiService;
    private CountryDataDao countryDataDao;

    private MutableLiveData<StatusReport> countryDataStatus = new MutableLiveData<>();

    @Inject
    public CountryDataRepository(AppExecutors appExecutors, ApiService apiService,
                                 CountryDataDao countryDataDao) {
        this.appExecutors = appExecutors;
        this.apiService = apiService;
        this.countryDataDao = countryDataDao;
    }

    public void insert(List<CountryDataEntity> entityList) {
        appExecutors.getDiskIO().execute(() -> countryDataDao.insert(entityList));
    }

    public void deleteAll() {
        appExecutors.getDiskIO().execute(() -> countryDataDao.deleteAll());
    }

    public LiveData<List<CountryDataEntity>> getCountryData() {
        countryDataStatus.setValue(StatusReport.makeStatusReport(Status.LOADING,
                "Loading data"));
        refreshCountryData();
        return countryDataDao.getCountryData(Util.getFormattedTime());
    }

    public LiveData<List<CountryInfoModel>> getCountryInfo() {
        refreshCountryData();
        return countryDataDao.getCountryInfo(Util.getFormattedTime());
    }

    public MutableLiveData<StatusReport> getStatusReport() {
        return this.countryDataStatus;
    }

    private void refreshCountryData() {

        appExecutors.getDiskIO().execute(() -> {
            // Check if latest data is present in database
            if(!countryDataDao.doesRowExist(Util.getFormattedTime())) {
                Log.d(TAG, "refreshCountryData: Data stale. Fetching from network.");
                fetchFromNetwork();
            } else {
                Log.d(TAG, "refreshCountryData: Data fresh. Load from database");
                appExecutors.getMainThread().execute(() -> {
                    countryDataStatus.setValue(StatusReport.makeStatusReport(
                            Status.SUCCESS, "Data in database"
                    ));
                });
            }
        });
    }

    private void fetchFromNetwork() {
        appExecutors.getNetworkIO().execute(() -> {

            List<Observable<?>> requests = new ArrayList<>();

            //Setting up to get at most 25 results per page
            for(int a = 0; a < 8; a++) {
                requests.add(apiService.getCountryData(25, a + 1));
            }

            Observable.zip(
                    requests,
                    objects -> {
                        List<CountryDataModel> modelList = new ArrayList<>();
                        for (Object object: objects) {
                            CountryDataResponse response = (CountryDataResponse) object;
                            if(response.getStatus().equals(Util.SUCCESS_MESSAGE)) {
                                modelList.addAll(response.getCountryDataWrap()
                                        .getCountryDataModelList());
                            } else {
                                //TODO: error
                                appExecutors.getMainThread().execute(() -> {
                                    countryDataStatus.setValue(StatusReport.makeStatusReport(
                                            Status.ERROR, "An error has occurred"
                                    ));
                                });
                            }
                        }
                        return modelList;
                    })
                    .subscribe(
                            o -> {
                                List<CountryDataEntity> entityList = new ArrayList<>();
                                int dateRetrieved = Util.getFormattedTime();
                                for (CountryDataModel m: o) {
                                    entityList.add(new CountryDataEntity(
                                            m.getCountry(), m.getTotalConfirmed(),
                                            m.getTotalDeaths(), m.getTotalRecovered(),
                                            m.getRecoveryRate(), m.getDeathRate(), dateRetrieved
                                    ));
                                }
                                deleteAll();
                                insert(entityList);

                                appExecutors.getMainThread().execute(() -> {
                                    countryDataStatus.setValue(StatusReport.makeStatusReport(
                                            Status.SUCCESS, "Data has been inserted"
                                    ));
                                });
                            },
                            throwable -> {
                                //TODO: error
                                appExecutors.getMainThread().execute(() -> {
                                    countryDataStatus.setValue(StatusReport.makeStatusReport(
                                            Status.ERROR, throwable.getMessage()
                                    ));
                                });
                            }
                    );
        });
    }
}
