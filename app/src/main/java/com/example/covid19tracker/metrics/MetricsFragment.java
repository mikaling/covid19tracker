package com.example.covid19tracker.metrics;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.example.covid19tracker.R;
import com.example.covid19tracker.adapter.CountryDataAdapter;
import com.example.covid19tracker.databinding.FragmentMetricsBinding;
import com.example.covid19tracker.databinding.ViewMetricsBinding;
import com.example.covid19tracker.model.CountryDataModel;
import com.example.covid19tracker.network.Covid19ApiAlt;
import com.example.covid19tracker.network.RetrofitClientInstance;
import com.example.covid19tracker.response.CountryDataResponse;
import com.example.covid19tracker.utils.Utils;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Retrofit;

public class MetricsFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private final String TAG = "MetricsFragment";
    private FragmentMetricsBinding binding;
    private ViewMetricsBinding metricsBinding;
    private ArrayList<CountryDataModel> confirmedList = new ArrayList<>();
    private ArrayList<CountryDataModel> recoveredList = new ArrayList<>();
    private ArrayList<CountryDataModel> deathsList = new ArrayList<>();
    private Context context;

    public MetricsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMetricsBinding.inflate(inflater, container, false);
        metricsBinding = binding.viewContainerMetrics;
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.shimmerMetricsContainer.startShimmer();
        fetchData();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        context = null;
    }

    private void fetchData() {
        Retrofit retrofit = RetrofitClientInstance.getAlternativeRetrofitInstance();
        Covid19ApiAlt serviceAlt = retrofit.create(Covid19ApiAlt.class);

        //Make a collection of all requests you need to call at once
        List<Observable<?>> requests = new ArrayList<>();
        requests.add(serviceAlt.getTopCountryData(getString(R.string.top_ten_confirmed)));
        requests.add(serviceAlt.getTopCountryData(getString(R.string.top_ten_recovered)));
        requests.add(serviceAlt.getTopCountryData(getString(R.string.top_ten_deaths)));

        //Zip all requests with the Function, which will receive all the results
        Observable.zip(
                requests,
                objects -> {
                    //objects is an array of combined results of completed requests
                    for(int i = 0; i < objects.length; i++) {
                        switch (i) {
                            case 0:
                                //Confirmed object
                                CountryDataResponse confirmedResponse =
                                        (CountryDataResponse) objects[i];
                                if(confirmedResponse.getStatus().equals(Utils.RESPONSE_SUCCESS)) {
                                    confirmedList = confirmedResponse.getCountryDataWrap()
                                            .getCountryDataModelList();
                                } else {
                                    //Status of response from API is fail
                                    //TODO: information logged and possibly sent to base
                                }
                                break;

                            case 1:
                                //Recovered object
                                // Log.i(TAG, "Confirmed: " + confirmedList.toString());
                                CountryDataResponse recoveredResponse =
                                        (CountryDataResponse) objects[i];
                                if(recoveredResponse.getStatus().equals(Utils.RESPONSE_SUCCESS)) {
                                    recoveredList = recoveredResponse.getCountryDataWrap()
                                            .getCountryDataModelList();
                                } else {
                                    //Status of response from API is fail
                                    //TODO: information logged and possibly sent to base
                                }
                                break;

                            case 2:
                                //Deaths object
                                CountryDataResponse deathsResponse =
                                        (CountryDataResponse) objects[i];
                                if(deathsResponse.getStatus().equals(Utils.RESPONSE_SUCCESS)) {
                                    deathsList = deathsResponse.getCountryDataWrap()
                                            .getCountryDataModelList();
                                } else {
                                    //Status of response from API is fail
                                    //TODO: information logged and possibly sent to base
                                }
                                break;

                            default:
                                break;
                        }
                    }
                    return new Object();
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                /* After all requests have been performed, the next observer will receive
                 * the Object returned from Function */
                .subscribe(
                        //Will be triggered if all requests will end successfully
                        o -> {
                            Log.i(TAG, "All requests successful");
                            //Call method that populates view with data
                            populateView();
                        },
                        throwable -> {
                            Log.i(TAG, throwable.getMessage(), throwable);
                            binding.shimmerMetricsContainer.stopShimmer();
                            showErrorSnackbar();
                            //TODO: implement error handling on user side
                        }
                );
    }

    private void populateView() {
        //Setting up spinner
        assert context != null;
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(
                context, R.array.statistics_array, android.R.layout.simple_spinner_item
        );
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        metricsBinding.statSelector.setAdapter(arrayAdapter);
        metricsBinding.statSelector.setOnItemSelectedListener(this);

        //Setting up recycler view
        metricsBinding.recyclerview.setLayoutManager(new LinearLayoutManager(context));

        metricsBinding.statSelector.setSelection(1);

        showView();
    }

    private void showView() {
        binding.shimmerMetricsContainer.stopShimmer();
        binding.shimmerMetricsContainer.setVisibility(View.INVISIBLE);
        metricsBinding.statSelector.setVisibility(View.VISIBLE);
        metricsBinding.statTitle.setVisibility(View.VISIBLE);
        metricsBinding.recyclerview.setVisibility(View.VISIBLE);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String choice = (String) parent.getItemAtPosition(position);
        CountryDataAdapter dataAdapter;
        switch (choice) {
            case "Confirmed":
                dataAdapter = new CountryDataAdapter(context, confirmedList, Utils.CONFIRMED_GROUP_ID);
                dataAdapter.notifyDataSetChanged();
                metricsBinding.statTitle.setText(R.string.title_confirmed);
                metricsBinding.recyclerview.setAdapter(dataAdapter);
                break;
            case "Recovered":
                dataAdapter = new CountryDataAdapter(context, recoveredList, Utils.RECOVERED_GROUP_ID);
                dataAdapter.notifyDataSetChanged();
                metricsBinding.statTitle.setText(R.string.title_recovered);
                metricsBinding.recyclerview.setAdapter(dataAdapter);
                break;
            case "Deaths":
                dataAdapter = new CountryDataAdapter(context, deathsList, Utils.DEATHS_GROUP_ID);
                dataAdapter.notifyDataSetChanged();
                metricsBinding.statTitle.setText(R.string.title_deaths);
                metricsBinding.recyclerview.setAdapter(dataAdapter);
                break;
            default:
                break;
        }
    }

    private void showErrorSnackbar() {
        final Snackbar snackbar = Snackbar.make(metricsBinding.getRoot(), "Error loading data",
                Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction("Retry", v -> {
            binding.shimmerMetricsContainer.startShimmer();
            fetchData();
            snackbar.dismiss();
        });
        snackbar.show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        //TODO: what to do on nothing selected
    }
}
