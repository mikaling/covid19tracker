package experiments.waweruu.c19tn.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import experiments.waweruu.c19tn.AppExecutors;
import experiments.waweruu.c19tn.db.dao.CountrySlugDao;
import experiments.waweruu.c19tn.db.dao.HistoricalStatisticsDao;
import experiments.waweruu.c19tn.db.entity.HistoricalStatisticsEntity;
import experiments.waweruu.c19tn.db.model.CountryInfoModel;
import experiments.waweruu.c19tn.remote.model.HistoricalStatisticsModel;
import experiments.waweruu.c19tn.remote.response.HistoricalStatisticsResponse;
import experiments.waweruu.c19tn.remote.retrofit.ApiService;
import experiments.waweruu.c19tn.util.Status;
import experiments.waweruu.c19tn.util.StatusReport;
import experiments.waweruu.c19tn.util.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Singleton
public class HistoricalStatisticsRepository {

    private static final String TAG = "HistoricalStatsRepo";

    private AppExecutors appExecutors;
    private ApiService apiService;
    private HistoricalStatisticsDao historicalStatisticsDao;
    private CountrySlugDao countrySlugDao;

    private MutableLiveData<StatusReport> statusReport = new MutableLiveData<>();

    @Inject
    public HistoricalStatisticsRepository(AppExecutors appExecutors, ApiService apiService,
                                          HistoricalStatisticsDao historicalStatisticsDao,
                                          CountrySlugDao countrySlugDao) {
        this.appExecutors = appExecutors;
        this.apiService = apiService;
        this.historicalStatisticsDao = historicalStatisticsDao;
        this.countrySlugDao = countrySlugDao;
    }

    public String getCountrySlug(String country) {
        return countrySlugDao.getCountrySlug(country);
    }

    public void refreshHistoricalStatistics(String country) {
        appExecutors.getDiskIO().execute(() -> {
            if(!historicalStatisticsDao.doesRowExist(Util.getFormattedTime(), country)) {
                //Fetch from network
                Log.d(TAG, "refreshHistoricalStatistics: Data stale. Fetching from network");
                String slug = getCountrySlug(country);
                fetchFromNetwork(country, slug);
            } else {
                Log.d(TAG, "refreshHistoricalStatistics: Data fresh");
            }
        });
    }

    private void insert(List<HistoricalStatisticsEntity> entityList) {
        appExecutors.getDiskIO().execute(() -> historicalStatisticsDao.insert(entityList));
    }

    private void deleteCountry(String country) {
        appExecutors.getDiskIO().execute(() -> historicalStatisticsDao.deleteCountry(country));
    }

    private void fetchFromNetwork(String country, String slug) {
        appExecutors.getNetworkIO().execute(() -> {
            Call<HistoricalStatisticsResponse> call = apiService
                    .getCountryHistoricalData(slug);
            call.enqueue(new Callback<HistoricalStatisticsResponse>() {
                @Override
                public void onResponse(Call<HistoricalStatisticsResponse> call, Response<HistoricalStatisticsResponse> response) {
                    assert response.body() != null;
                    if(response.body().getStatus().equals(Util.SUCCESS_MESSAGE)) {
                        Log.d(TAG, "onResponse: Success");
                        Log.d(TAG, "onResponse: modelList size " + response.body()
                                .getHistoricalStatisticsWrap().getHistoricalStatisticsModelList()
                                .size());
                        List<HistoricalStatisticsEntity> entityList = new ArrayList<>();
                        int dateRetrieved = Util.getFormattedTime();
                        for(HistoricalStatisticsModel m : response.body()
                                .getHistoricalStatisticsWrap().getHistoricalStatisticsModelList()) {
                            entityList.add(new HistoricalStatisticsEntity(dateRetrieved,
                                    m.getCountry(), m.getConfirmed(), m.getDeaths(),
                                    m.getRecovered(), m.getDate()));
                        }

                        deleteCountry(country);
                        insert(entityList);

                        Log.d(TAG, "onResponse: Success. Data in db.");
                        appExecutors.getMainThread().execute(() -> statusReport
                                .setValue(StatusReport.makeStatusReport(Status.SUCCESS, "Data inserted in db.")));
                    }
                }

                @Override
                public void onFailure(Call<HistoricalStatisticsResponse> call, Throwable t) {
                    Log.d(TAG, "onFailure: Error" + t.getMessage());
                    appExecutors.getMainThread().execute(() -> statusReport
                            .setValue(StatusReport.makeStatusReport(Status.ERROR, t.getMessage())));
                }
            });
        });

    }

    public LiveData<List<CountryInfoModel>> getHistoricalData(String country) {
        refreshHistoricalStatistics(country);
        return historicalStatisticsDao.getHistoricalData(Util.getFormattedTime(), country);
    }

    public LiveData<StatusReport> getStatusReport() {
        return this.statusReport;
    }


}
