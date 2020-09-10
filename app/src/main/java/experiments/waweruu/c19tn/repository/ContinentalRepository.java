package experiments.waweruu.c19tn.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import experiments.waweruu.c19tn.AppExecutors;
import experiments.waweruu.c19tn.db.dao.ContinentCountryDataDao;
import experiments.waweruu.c19tn.db.dao.ContinentTotalsDao;
import experiments.waweruu.c19tn.db.entity.ContinentCountryDataEntity;
import experiments.waweruu.c19tn.db.entity.ContinentTotalsEntity;
import experiments.waweruu.c19tn.db.model.CountryModel;
import experiments.waweruu.c19tn.remote.model.ContinentTotalsModel;
import experiments.waweruu.c19tn.remote.model.CountryDataModel;
import experiments.waweruu.c19tn.remote.response.ContinentDataResponse;
import experiments.waweruu.c19tn.remote.retrofit.ApiService;
import experiments.waweruu.c19tn.util.Status;
import experiments.waweruu.c19tn.util.StatusReport;
import experiments.waweruu.c19tn.util.Util;
import io.reactivex.rxjava3.core.Observable;

public class ContinentalRepository {

    private static final String TAG = "ConCouDataRepo";

    private AppExecutors appExecutors;
    private ApiService apiService;
    private ContinentCountryDataDao continentCountryDataDao;
    private ContinentTotalsDao continentTotalsDao;

    private MutableLiveData<StatusReport> statusReport = new MutableLiveData<>();

    @Inject
    public ContinentalRepository(AppExecutors appExecutors, ApiService apiService,
                                 ContinentCountryDataDao continentCountryDataDao,
                                 ContinentTotalsDao continentTotalsDao) {
        this.appExecutors = appExecutors;
        this.apiService = apiService;
        this.continentCountryDataDao = continentCountryDataDao;
        this.continentTotalsDao = continentTotalsDao;
    }

    public void insertContinentCountryData(List<ContinentCountryDataEntity> entityList) {
        appExecutors.getDiskIO().execute(() -> continentCountryDataDao.insert(entityList));
    }

    public void insertContinentTotals(ContinentTotalsEntity entity) {
        appExecutors.getDiskIO().execute(() -> continentTotalsDao.insert(entity));
    }

    public void deleteCountryDataContinent(String continent) {
        appExecutors.getDiskIO().execute(() -> continentCountryDataDao.deleteContinent(continent));
    }

    public void deleteContinentTotals(String continent) {
        appExecutors.getDiskIO().execute(() -> continentTotalsDao.deleteContinent(continent));
    }

    public LiveData<List<ContinentCountryDataEntity>> getContinentCountryData(String continent) {
        refreshContinentalData(continent);
        return continentCountryDataDao.getContinentCountryData(Util.getFormattedTime(),
                Util.getContinentColumn(continent));
    }

    public LiveData<List<CountryModel>> getCountryModelList(String continent) {
        refreshContinentalData(continent);
        return continentCountryDataDao.getCountryModelList(Util.getFormattedTime(),
                Util.getContinentColumn(continent));
    }

    public LiveData<ContinentTotalsEntity> getContinentTotals(String continent) {
//        refreshContinentalData(continent);
        return continentTotalsDao.getContinentTotals(Util.getContinentColumn(continent));
    }

    public MutableLiveData<StatusReport> getStatusReport() {
        return this.statusReport;
    }

    public void refreshContinentalData(String continent) {
        String databaseContinent = Util.getContinentColumn(continent);
        appExecutors.getDiskIO().execute(() -> {
            if(!continentCountryDataDao.doesRowExist(Util.getFormattedTime(), databaseContinent) ||
                !continentTotalsDao.doesRowExist(Util.getFormattedTime(), databaseContinent)) {
                Log.d(TAG, "refreshContinentCountryData: Data stale. Fetching from network");
                fetchFromNetwork(continent);
            } else {
                Log.d(TAG, "refreshContinentCountryData: Data fresh.");
                appExecutors.getMainThread().execute(() -> statusReport.setValue(StatusReport.makeStatusReport(
                        Status.SUCCESS, "Data in database"
                )));
            }
        });
    }

    private void fetchFromNetwork(String continent) {
        appExecutors.getNetworkIO().execute(() -> {

            List<Observable<?>> requests = new ArrayList<>();

            switch(continent) {
                case Util.CONTINENT_AFRICA:
                case Util.CONTINENT_EUROPE:
                    for(int a = 0; a < 3; a++) {
                        requests.add(apiService.getContinentData(continent, 25, a + 1));
                    }
                    break;

                case Util.CONTINENT_ASIA:
                    for(int a = 0; a < 2; a++) {
                        requests.add(apiService.getContinentData(continent, 25, a + 1));
                    }
                    break;

                case Util.CONTINENT_NORTH_AMERICA:
                case Util.CONTINENT_OCEANIA:
                case Util.CONTINENT_SOUTH_AMERICA:
                    requests.add(apiService.getContinentData(continent, 25, 1));
                    break;
            }

            Observable.zip(
                    requests,
                    objects -> {
                        List<CountryDataModel> modelList = new ArrayList<>();

                        //Getting CountryDataModel objects
                        for(Object o : objects) {
                            ContinentDataResponse response = (ContinentDataResponse) o;
                            if(response.getStatus().equals(Util.SUCCESS_MESSAGE)) {
                                Log.d(TAG, "fetchFromNetwork: Success");
                                modelList.addAll(response.getContinentDataWrap()
                                        .getCountryDataModelList());
                            } else {
                                //Error on request
                                appExecutors.getMainThread().execute(() -> {
                                    Log.d(TAG, "fetchFromNetwork: Request error");
                                    statusReport.setValue(StatusReport.makeStatusReport(
                                            Status.ERROR, "An error has occurred."
                                    ));
                                });
                            }
                        }

                        int dateRetrieved = Util.getFormattedTime();

                        //Insert ContinentTotals into database
                        ContinentDataResponse response = (ContinentDataResponse) objects[0];
                        String iContinent = response.getContinent();
                        ContinentTotalsModel t = response.getContinentDataWrap()
                                .getContinentTotalsModel();
                        ContinentTotalsEntity e = new ContinentTotalsEntity(dateRetrieved,
                                iContinent, t.getTotalConfirmed(), t.getTotalRecoveries(),
                                t.getTotalDeaths());
                        appExecutors.getDiskIO().execute(() -> {
                            deleteContinentTotals(iContinent);
                            insertContinentTotals(e);
                        });



                        //Insert CountryData into database
                        List<ContinentCountryDataEntity> entityList = new ArrayList<>();
                        for (CountryDataModel m : modelList) {
                            entityList.add(new ContinentCountryDataEntity(
                                    m.getCountry(), iContinent, dateRetrieved, m.getTotalConfirmed(),
                                    m.getTotalDeaths(), m.getTotalRecovered(), m.getRecoveryRate(),
                                    m.getDeathRate()
                            ));
                        }
                        appExecutors.getDiskIO().execute(() -> {
                                    deleteCountryDataContinent(iContinent);
                                    insertContinentCountryData(entityList);
                                });


                        return new Object();
                    })
                    .subscribe(
                            o -> appExecutors.getMainThread().execute(() -> {
                                Log.d(TAG, "fetchFromNetwork: Data has been inserted");
                                statusReport.setValue(StatusReport.makeStatusReport(
                                        Status.SUCCESS, "Data has been inserted"
                                ));
                            }),
                            throwable -> appExecutors.getMainThread().execute(() -> {
                                Log.d(TAG, "fetchFromNetwork: Error " + throwable.getMessage());
                                statusReport.setValue(StatusReport.makeStatusReport(
                                        Status.ERROR, throwable.getMessage()
                                ));
                            })
                    );
        });
    }
}
