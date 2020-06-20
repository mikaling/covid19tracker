package com.example.covid19tracker.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.covid19tracker.R;
import com.example.covid19tracker.adapter.HistoricalStatisticsAdapter;
import com.example.covid19tracker.databinding.ActivityTestBinding;
import com.example.covid19tracker.model.BarDataModel;
import com.example.covid19tracker.model.HistoricalStatisticsModel;
import com.example.covid19tracker.network.RetrofitClientInstance;
import com.example.covid19tracker.network.TestApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.covid19tracker.utils.Utils.showToast;

public class TestActivity extends AppCompatActivity {

    public static final String TAG = "TestActivity";
    private ActivityTestBinding testBinding;
    private HistoricalStatisticsAdapter historicalAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        /* Android 3.6.* provides view binding which removes the need to use the findViewById()
         * method to instantiate view objects */
        testBinding = ActivityTestBinding.inflate(getLayoutInflater());
        View view = testBinding.getRoot();
        setContentView(view);

        /**/
        TestApi service = RetrofitClientInstance.getRetrofitInstance().create(TestApi.class);

        /**/
        Call<List<BarDataModel>> historicalCall = service.getBarData();

        /* enqueue() is asynchronous. It sends the request and notifies the app with a callback
         * when the a response is made. As the request is asynchronous, Retrofit handles it on
         * a background thread so that the main UI thread isn't blocked.
         * The parameter passed in enqueue() is a Callback instance. Within the instance,
         * the methods onResponse() and onFailure() must be overridden as these are the methods
         * enqueue() uses to notify the app of the request's result */
        historicalCall.enqueue(new Callback<List<BarDataModel>>() {
            @Override
            public void onResponse(Call<List<BarDataModel>> call, Response<List<BarDataModel>> response) {
                /* A response has been received */
                populateRecycler(response.body());
            }

            @Override
            public void onFailure(Call<List<BarDataModel>> call, Throwable t) {
                /* There has been no response made
                 * Possible reasons could be connection timeout, the response has a different
                 * structure to what has been defined as the expected response */
                showToast(TestActivity.this, getString(R.string.retrofit_on_failure_message));
                Log.e(TAG, t.getMessage(), t);
            }
        });
    }

    private void populateRecycler(List<BarDataModel> dataList) {
        if(dataList != null)
        {
            historicalAdapter = new HistoricalStatisticsAdapter(this, dataList);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
            testBinding.recyclerHistoricalData.setLayoutManager(layoutManager);
            testBinding.recyclerHistoricalData.setAdapter(historicalAdapter);
        } else {
            Log.i(TAG, "List<HistoricalStatisticsModel> empty");
            showToast(this, getString(R.string.retrofit_on_failure_message));
        }
    }
}
