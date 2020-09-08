package experiments.waweruu.c19tn.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import javax.inject.Inject;
import javax.inject.Singleton;

import experiments.waweruu.c19tn.AppExecutors;
import experiments.waweruu.c19tn.local.dao.GlobalStatisticsDao;
import experiments.waweruu.c19tn.local.entity.GlobalStatisticsEntity;
import experiments.waweruu.c19tn.remote.model.GlobalStatisticsModel;
import experiments.waweruu.c19tn.remote.response.GlobalStatisticsResponse;
import experiments.waweruu.c19tn.remote.retrofit.ApiService;
import experiments.waweruu.c19tn.util.Status;
import experiments.waweruu.c19tn.util.StatusReport;
import experiments.waweruu.c19tn.util.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Singleton
public class GlobalStatisticsRepository {

    private static final String TAG = "GlobalStatsRepo";

    private AppExecutors appExecutors;
    private ApiService apiService;
    private GlobalStatisticsDao globalStatisticsDao;

    public MutableLiveData<StatusReport> globalStatisticsStatusReport;

    @Inject
    public GlobalStatisticsRepository(AppExecutors appExecutors, ApiService apiService,
                                      GlobalStatisticsDao globalStatisticsDao) {
        this.appExecutors = appExecutors;
        this.apiService = apiService;
        this.globalStatisticsDao = globalStatisticsDao;
        this.globalStatisticsStatusReport = new MutableLiveData<>();
    }

    public void refreshGlobalStatistics() {
        globalStatisticsStatusReport.postValue(StatusReport.makeStatusReport(
                Status.LOADING, "Data check"
        ));
        //Check if global statistics entry is older or fresh
        Log.d(TAG, "refreshGlobalStatistics: called");

//        LiveData<GlobalStatisticsEntity> retrieved_entity = globalStatisticsDao
//                .getGlobalStatistics(currentDate);
//        AtomicBoolean doesRowExist = new AtomicBoolean(false);
        appExecutors.getDiskIO().execute(() -> {
            int currentDate = Util.getFormattedTime();
            Log.d(TAG, "Current date: " + currentDate);
            if(!globalStatisticsDao.doesRowExist(currentDate)) {
                //Fetch from network
                Log.d(TAG, "Entity empty. doesRowExist: false");
                fetchFromNetwork();
            } else {
                //Fetch from db
                Log.d(TAG, "Entity not empty. doesRowExist: true");
                fetchFromDatabase();
            }
        });
    }

    private void fetchFromDatabase() {
        appExecutors.getMainThread().execute(() ->
                globalStatisticsStatusReport.postValue(StatusReport.makeStatusReport(
                        Status.SUCCESS, "Data loaded from database")));
    }

    private void fetchFromNetwork() {
        appExecutors.getNetworkIO().execute(() -> {
            Log.d(TAG, "refreshGlobalStatistics: fetching from network");
            Call<GlobalStatisticsResponse> call = apiService.getGlobalStatistics();
            call.enqueue(new Callback<GlobalStatisticsResponse>() {
                @Override
                public void onResponse(Call<GlobalStatisticsResponse> call, Response<GlobalStatisticsResponse> response) {
                    assert response.body() != null;
                    if(response.body().getStatus().equals(Util.SUCCESS_MESSAGE)) {
                        //Request successful

                        Log.d(TAG, "onResponse: " + response.body().getStatus());
                        int retrievalDate = Util.getFormattedTime();
                        Log.d(TAG, "Retrieval date: " + retrievalDate);
                        GlobalStatisticsModel model = response.body().getGlobalStatisticsWrap()
                                .getGlobalStatisticsModel();
                        Log.d(TAG, "Total Confirmed: " + model.getTotalConfirmed());
                        GlobalStatisticsEntity entity_two = new GlobalStatisticsEntity(
                                retrievalDate, model.getNewConfirmed(), model.getNewDeaths(),
                                model.getNewRecovered(), model.getTotalConfirmed(),
                                model.getTotalDeaths(), model.getTotalRecovered()
                        );

                        // Clear database table
                        deleteAll();

                        //Insert fetched data into database
                        insert(entity_two);

                        globalStatisticsStatusReport.postValue(
                            StatusReport.makeStatusReport(Status.SUCCESS, "Data load success")
                        );
                    }
                }

                @Override
                public void onFailure(Call<GlobalStatisticsResponse> call, Throwable t) {
                    //TODO: find out how to handle errors
                    Log.d(TAG, "onFailure: Call error" + t.getMessage());
                    globalStatisticsStatusReport.postValue(StatusReport.makeStatusReport(
                            Status.ERROR, t.getMessage()
                    ));
                }
            });
        });
    }

    public MutableLiveData<StatusReport> getGlobalStatisticsStatusReport() {
        return this.globalStatisticsStatusReport;
    }

    public LiveData<GlobalStatisticsEntity> getGlobalStatistics() {
        refreshGlobalStatistics();
        return globalStatisticsDao.getGlobalStatistics(Util.getFormattedTime());
    }

    public void insert(GlobalStatisticsEntity globalStatisticsEntity) {
        appExecutors.getDiskIO().execute(() -> globalStatisticsDao.insert(globalStatisticsEntity));
    }

    public void deleteAll() {
        appExecutors.getDiskIO().execute(() -> globalStatisticsDao.deleteAll());
    }

}
