package com.example.covid19tracker.global;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.covid19tracker.R;
import com.example.covid19tracker.model.CountryDataModel;
import com.example.covid19tracker.model.CountryInfoModel;
import com.example.covid19tracker.model.GlobalStatisticsModel;
import com.example.covid19tracker.network.Covid19ApiAlt;
import com.example.covid19tracker.network.RetrofitClientInstance;
import com.example.covid19tracker.network.TestApi;
import com.example.covid19tracker.response.CountryDataResponse;
import com.example.covid19tracker.response.GlobalStatisticsResponse;
import com.example.covid19tracker.utils.Utils;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class WorldFragment extends Fragment {


    private static final String TAG = "WorldFragment";
    private WebView webView;
    private List<CountryDataModel> countryDataList = new ArrayList<>();
    private List<CountryInfoModel> countryInfoList = new ArrayList<>();
    private GlobalStatisticsModel globalStatistics;
    private String countries;
    private TextView textTotalConfirmed, textTotalRecovered,textTotalDeaths,textNewConfirmed,
            textNewRecoveries,textNewDeaths;
    private TestApi service;
    private View rootView;
    private ShimmerFrameLayout shimmerFrameLayout;
    private RelativeLayout viewGlobal;

    public WorldFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_world, container, false);
    }

    public class WebAppInterface {
        Context mContext;

        WebAppInterface (Context context) {
            mContext = context;
        }

        @JavascriptInterface
        public String getArrayGeoChartData() {
            Log.i(TAG, "Countries list: " + Arrays.deepToString(countryInfoList.toArray()));
            return countries;
        }

    }

    private void showView() {
        shimmerFrameLayout.stopShimmer();
        shimmerFrameLayout.setVisibility(View.INVISIBLE);
        viewGlobal.setVisibility(View.VISIBLE);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        textTotalConfirmed=view.findViewById(R.id.text_confirmed_cases);
        textTotalRecovered=view.findViewById(R.id.text_recovered_cases);
        textTotalDeaths=view.findViewById(R.id.text_total_deaths);
        textNewConfirmed=view.findViewById(R.id.text_new_confirmed_cases);
        textNewDeaths=view.findViewById(R.id.text_new_deaths);
        textNewRecoveries=view.findViewById(R.id.text_new_recovered);
//        rootView=view.findViewById(R.id.content);
        viewGlobal = view.findViewById(R.id.view_global);
        shimmerFrameLayout = view.findViewById(R.id.shimmer_global_container);
        shimmerFrameLayout.startShimmer();

        /**/
        service = RetrofitClientInstance
                .getRetrofitInstance().create(TestApi.class);


        webView = view.findViewById(R.id.map_webview);
        //getWorldData();
        //getGlobalData();
        fetchData();
    }

    private void fetchData() {
        Retrofit retrofit = RetrofitClientInstance.getAlternativeRetrofitInstance();
        Covid19ApiAlt serviceAlt = retrofit.create(Covid19ApiAlt.class);

        List<Observable<?>> requests = new ArrayList<>();
        requests.add(serviceAlt.getCountryData(100, 1));
        requests.add(serviceAlt.getCountryData(100, 2));
        requests.add(serviceAlt.getGlobalStatistics());

        Observable.zip(
                requests,
                objects -> {

                    for(int i = 0; i < objects.length - 1; i++) {
                        //Accessing the CountryDataResponse Objects
                        CountryDataResponse dataResponse = (CountryDataResponse) objects[i];
                        if (dataResponse.getStatus().equals(Utils.RESPONSE_SUCCESS)) {
                            countryDataList.addAll(dataResponse
                                    .getCountryDataWrap().getCountryDataModelList());
                        } else {
                            //Status of response from API is fail
                            //TODO: information logged and possibly sent to base
                        }
                    }

                    //Accessing last object (GlobalStatisticsResponse)
                    GlobalStatisticsResponse statisticsResponse =
                            (GlobalStatisticsResponse) objects[objects.length - 1];
                    if(statisticsResponse.getStatus().equals(Utils.RESPONSE_SUCCESS)) {
                        globalStatistics = statisticsResponse.getGlobalStatisticsWrap()
                                .getGlobalStatisticsModel();
                    } else {
                        //Status of response from API is fail
                        //TODO: information logged and possibly sent to base
                    }

                    return new Object();
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                        o -> {
                            Log.i(TAG, "All requests successful");
                            populateViews();
                        },
                        throwable -> {
                            Log.i(TAG, throwable.getMessage(), throwable);
                            showErrorSnackBar();
                            //TODO: implement error handling on user side
                        }
                );
    }

    private void populateViews() {
        //Populating geocharts
        for (int i = 0; i < countryDataList.size(); i++) {
            CountryInfoModel countryInfoModel = new CountryInfoModel(
                    countryDataList.get(i).getCountry(),
                    countryDataList.get(i).getTotalConfirmed(),
                    countryDataList.get(i).getTotalDeaths(),
                    countryDataList.get(i).getTotalRecovered()
            );
            countryInfoList.add(countryInfoModel);
        }

        Gson countriesObject = new Gson();
        countries = countriesObject.toJson(countryInfoList);
        Log.i(TAG, "CountryData: " + countries);
        loadWebView();

        //Populating statistics views
        textTotalConfirmed.setText(
                String.format(Locale.ENGLISH, Utils.SEPARATOR_FORMAT,
                        globalStatistics.getTotalConfirmed())
        );
        textTotalRecovered.setText(
                String.format(Locale.ENGLISH, Utils.SEPARATOR_FORMAT,
                        globalStatistics.getTotalRecovered())
        );
        textTotalDeaths.setText(
                String.format(Locale.ENGLISH, Utils.SEPARATOR_FORMAT,
                        globalStatistics.getTotalDeaths())
        );
        textNewConfirmed.setText(
                String.format(Locale.ENGLISH, Utils.SEPARATOR_FORMAT,
                        globalStatistics.getNewConfirmed())
        );
        textNewRecoveries.setText(
                String.format(Locale.ENGLISH, Utils.SEPARATOR_FORMAT,
                globalStatistics.getNewRecovered())
        );
        textNewDeaths.setText(
                String.format(Locale.ENGLISH, Utils.SEPARATOR_FORMAT,
                globalStatistics.getNewDeaths())
        );
    }

    private void getWorldData(){

        Call<CountryDataResponse> countryDataCall = service.getCountryData(200, 1);

        countryDataCall.enqueue(new Callback<CountryDataResponse>() {
            @Override
            public void onResponse(Call<CountryDataResponse> call,
                                   Response<CountryDataResponse> response) {
                Log.i(TAG, "Response received from API call");
                if(response.body().getStatus().equals(Utils.RESPONSE_SUCCESS)) {
                    countryDataList = response.body().getCountryDataWrap().getCountryDataModelList();
                    for (int i = 0; i < countryDataList.size(); i++) {
                        CountryInfoModel countryInfoModel = new CountryInfoModel(
                                countryDataList.get(i).getCountry(),
                                countryDataList.get(i).getTotalConfirmed(),
                                countryDataList.get(i).getTotalDeaths(),
                                countryDataList.get(i).getTotalRecovered()
                        );
                        countryInfoList.add(countryInfoModel);
                    }

                    Gson countriesObject = new Gson();
                    countries = countriesObject.toJson(countryInfoList);
                    Log.i(TAG, "CountryData: " + countries);
                    loadWebView();
                }
            }

            @Override
            public void onFailure(Call<CountryDataResponse> call, Throwable t) {
                showErrorSnackBar();
                Log.e(TAG, t.getMessage(), t);
            }
        });

    }

    private void loadWebView() {
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);
        webView.addJavascriptInterface(new WebAppInterface(getContext()), "Android");

        // Auto zoom out webView
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);

        webView.loadUrl("file:///android_asset/index.html");

        showView();
    }



    private void getGlobalData() {

        Call<GlobalStatisticsResponse> globalStatisticsResponseCall = service.getGlobalStatistics();
        globalStatisticsResponseCall.enqueue(new Callback<GlobalStatisticsResponse>() {
            @Override
            public void onResponse(Call<GlobalStatisticsResponse> call, Response<GlobalStatisticsResponse> response) {
                if(response.body().getStatus().equals(Utils.RESPONSE_SUCCESS)){
                    GlobalStatisticsModel globalStatisticsModel = response
                            .body().getGlobalStatisticsWrap().getGlobalStatisticsModel();
                    assert globalStatisticsModel!=null;

                    int confirmed_cases = globalStatisticsModel.getTotalConfirmed();
                    int total_recoveries = globalStatisticsModel.getTotalRecovered();
                    int total_deaths = globalStatisticsModel.getTotalDeaths();
                    int new_confirmed = globalStatisticsModel.getNewConfirmed();
                    int new_recoveries = globalStatisticsModel.getNewRecovered();
                    int new_deaths = globalStatisticsModel.getNewDeaths();

                    DecimalFormat decimalFormat = new DecimalFormat("#,###");

                    textTotalConfirmed.setText(decimalFormat.format(confirmed_cases));
                    textTotalDeaths.setText(decimalFormat.format(total_deaths));
                    textTotalRecovered.setText(decimalFormat.format(total_recoveries));
                    textNewConfirmed.setText(decimalFormat.format(new_confirmed));
                    textNewRecoveries.setText(decimalFormat.format(new_recoveries));
                    textNewDeaths.setText(decimalFormat.format(new_deaths));

                }
            }

            @Override
            public void onFailure(Call<GlobalStatisticsResponse> call, Throwable t) {
                Log.i("GLOBAL ERROR",t.getMessage());
                shimmerFrameLayout.stopShimmer();
                showErrorSnackBar();
            }
        });
    }

    private void showErrorSnackBar() {

        final Snackbar snackbar = Snackbar
                .make(viewGlobal, "Error Loading Data", Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction("Retry", v -> {
            shimmerFrameLayout.startShimmer();
//            getGlobalData();
//            getWorldData();
            fetchData();
            snackbar.dismiss();
        });
        snackbar.show();
    }

    //TODO:Improve on the shimmer effect, add onPause and onResume implementations
    //TODO:Work on the web view layout
}
