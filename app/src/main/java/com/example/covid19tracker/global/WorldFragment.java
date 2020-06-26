package com.example.covid19tracker.global;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.covid19tracker.model.CountryDataModel;
import com.example.covid19tracker.model.GlobalStatisticsModel;
import com.example.covid19tracker.network.RetrofitClientInstance;


import com.example.covid19tracker.R;
import com.example.covid19tracker.network.TestApi;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.covid19tracker.utils.Utils.showToast;

public class WorldFragment extends Fragment {


    private static final String TAG = "WorldFragment";
    private WebView webView;
    private List<CountryDataModel> countriesResponse;
    private List<List<String>> countriesList = new ArrayList<List<String>>();
    private List<String> countryInfo = new ArrayList<>();
    private String countries;
    TextView textTotalConfirmed, textTotalRecovered,textTotalDeaths;
    TestApi service;
    LinearLayout globalData;
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

        public WebAppInterface (Context context) {
            mContext = context;
        }

        @JavascriptInterface
        public String getArrayGeoChartData() {
            Log.i(TAG, "Countries list: " + Arrays.deepToString(countriesList.toArray()));
            return countries;
        }

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        textTotalConfirmed=view.findViewById(R.id.text_confirmed_cases);
        textTotalRecovered=view.findViewById(R.id.text_recovered_cases);
        textTotalDeaths=view.findViewById(R.id.text_total_deaths);
        globalData=(LinearLayout) view.findViewById(R.id.global_data);

        /**/
        service = RetrofitClientInstance
                .getRetrofitInstance().create(TestApi.class);

        /**/
        Call<List<CountryDataModel>> countryDataCall = service
                .getCountryData();

        /* enqueue() is asynchronous. It sends the request and notifies the app with a callback
         * when the a response is made. As the request is asynchronous, Retrofit handles it on
         * a background thread so that the main UI thread isn't blocked.
         * The parameter passed in enqueue() is a Callback instance. Within the instance,
         * the methods onResponse() and onFailure() must be overridden as these are the methods
         * enqueue() uses to notify the app of the request's result */
        countryDataCall.enqueue(new Callback<List<CountryDataModel>>() {
            @Override
            public void onResponse(Call<List<CountryDataModel>> call,
                                   Response<List<CountryDataModel>> response) {

                countriesResponse = response.body();

                for (int i = 0; i < countriesResponse.size(); i++) {
                    countryInfo = new ArrayList<>();
                    countryInfo.add("'" + countriesResponse.get(i).getCountryDataName() + "'");
                    countryInfo.add(countriesResponse.get(i).getCountryDataTotalConfirmedCases());
                    countriesList.add(countryInfo);
                }

                Gson countriesObject = new Gson();

                countries = countriesObject.toJson(countriesList);
                System.out.println(countries);
                System.out.println(countriesObject);
                System.out.println("Countries List: " + Arrays.deepToString(countriesList.toArray()));
                showToast(getActivity(), "Response received");
                Log.i(TAG, "Response received");

                loadWebView();
                getGlobalStatistics();
            }

            @Override
            public void onFailure(Call<List<CountryDataModel>> call, Throwable t) {
                showToast(getActivity(),
                        getString(R.string.retrofit_on_failure_message));
                Log.e(TAG, t.getMessage(), t);
            }
        });

        webView = view.findViewById(R.id.map_webview);


    }

    public void loadWebView() {
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);
        webView.addJavascriptInterface(new WebAppInterface(getContext()), "Android");

        // Auto zoom out webView
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);

        webView.loadUrl("file:///android_asset/index.html");
    }

    void getGlobalStatistics() {

        Call<GlobalStatisticsModel> call=service.getGlobalStatistics();
        call.enqueue(new Callback<GlobalStatisticsModel>() {
            @Override
            public void onResponse(Call<GlobalStatisticsModel> call, Response<GlobalStatisticsModel> response) {
                if(response.code()==200){
                    GlobalStatisticsModel globalStatisticsModel=response.body();
                    assert globalStatisticsModel!=null;

                    int confirmed_cases=globalStatisticsModel.getTotalConfirmedCases();
                    int total_recoveries=globalStatisticsModel.getTotalRecoveries();
                    int total_deaths=globalStatisticsModel.getTotalDeaths();
                    textTotalConfirmed.setText(Integer.toString(confirmed_cases));
                    textTotalDeaths.setText(Integer.toString(total_deaths));
                    textTotalRecovered.setText(Integer.toString(total_recoveries));

                }
            }

            @Override
            public void onFailure(Call<GlobalStatisticsModel> call, Throwable t) {
                Log.i("GLOBAL ERROR",t.getMessage());
            }
        });
    }

}
