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
import experiments.waweruu.c19tn.db.entity.CountrySlugEntity;
import experiments.waweruu.c19tn.remote.model.CountrySlugModel;
import experiments.waweruu.c19tn.remote.response.CountrySlugResponse;
import experiments.waweruu.c19tn.remote.retrofit.ApiService;
import experiments.waweruu.c19tn.util.Status;
import experiments.waweruu.c19tn.util.StatusReport;
import experiments.waweruu.c19tn.util.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Singleton
public class CountrySlugRepository {

    private static final String TAG = "CountrySlugRepo";

    private AppExecutors appExecutors;
    private ApiService apiService;
    private CountrySlugDao countrySlugDao;

    private MutableLiveData<StatusReport> statusReport = new MutableLiveData<>();

    @Inject
    public CountrySlugRepository(AppExecutors appExecutors, ApiService apiService,
                                 CountrySlugDao countrySlugDao) {
        this.appExecutors = appExecutors;
        this.apiService = apiService;
        this.countrySlugDao = countrySlugDao;
    }

    public void insert(List<CountrySlugEntity> entityList) {
        appExecutors.getDiskIO().execute(() -> countrySlugDao.insert(entityList));
    }

    public void deleteAll() {
        appExecutors.getDiskIO().execute(() -> countrySlugDao.deleteAll());
    }

    public MutableLiveData<StatusReport> getStatusReport() {
        return this.statusReport;
    }

    public void refreshCountrySlugs() {
        appExecutors.getDiskIO().execute(() -> {
            if(!countrySlugDao.doesRowExist(Util.getFormattedTime())) {
                Log.d(TAG, "refreshCountrySlugs: Data stale. Fetching from network.");
                fetchFromNetwork();
            } else {
                Log.d(TAG, "refreshCountrySlugs: Data fresh");
                appExecutors.getMainThread().execute(() -> statusReport.setValue(
                        StatusReport.makeStatusReport(Status.SUCCESS, "Data in db.")
                ));
            }
        });
    }

    public LiveData<List<String>> getCountryNames() {
        refreshCountrySlugs();
        return countrySlugDao.getCountryNames();
    }

    private void fetchFromNetwork() {
        appExecutors.getNetworkIO().execute(() -> {
            Log.d(TAG, "fetchFromNetwork: Fetching from network");
            Call<CountrySlugResponse> call = apiService.getCountrySlugs();
            call.enqueue(new Callback<CountrySlugResponse>() {
                @Override
                public void onResponse(Call<CountrySlugResponse> call, Response<CountrySlugResponse> response) {
                    assert response.body() != null;
                    if(response.body().getStatus().equals(Util.SUCCESS_MESSAGE)) {
                        Log.d(TAG, "onResponse: " + response.body().getStatus());
                        int dateRetrieved = Util.getFormattedTime();
                        List<CountrySlugEntity> entityList = new ArrayList<>();
                        for(CountrySlugModel m : response.body().getWrap().getModelList()) {
                            entityList.add(new CountrySlugEntity(dateRetrieved, m.getCountry(),
                                    m.getCountryCode(), m.getSlug()));
                        }

                        deleteAll();
                        insert(entityList);

                        appExecutors.getMainThread().execute(() -> statusReport.setValue(
                                StatusReport.makeStatusReport(Status.SUCCESS, "Data in db.")
                        ));
                    }
                }

                @Override
                public void onFailure(Call<CountrySlugResponse> call, Throwable t) {
                    Log.d(TAG, "onFailure: Error" + t.getMessage());
                    appExecutors.getMainThread().execute(() -> statusReport.setValue(
                            StatusReport.makeStatusReport(Status.ERROR, t.getMessage())
                    ));
                }
            });
        });
    }
}
