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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.covid19tracker.model.CountryDataModel;
import com.example.covid19tracker.model.GlobalStatisticsModel;
import com.example.covid19tracker.network.RetrofitClientInstance;


import com.example.covid19tracker.R;
import com.example.covid19tracker.network.TestApi;
import com.google.android.material.snackbar.Snackbar;
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
    TextView textTotalConfirmed, textTotalRecovered,textTotalDeaths,textNewConfirmed,textNewRecoveries,textNewDeaths;
    TestApi service;
    View rootView;
    LinearLayout globalData;
    ProgressBar progressBar;


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
        textNewConfirmed=view.findViewById(R.id.text_new_confirmed_cases);
        textNewDeaths=view.findViewById(R.id.text_new_deaths);
        textNewRecoveries=view.findViewById(R.id.text_new_recovered);
        globalData=(LinearLayout) view.findViewById(R.id.global_data);
        rootView=view.findViewById(R.id.content);
        progressBar=view.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        globalData.setVisibility(View.INVISIBLE);

        /**/
        service = RetrofitClientInstance
                .getRetrofitInstance().create(TestApi.class);

        /**/

        webView = view.findViewById(R.id.map_webview);
        getWorldData();
        getGlobalData();

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
    public void getWorldData(){
        Call<List<CountryDataModel>> countryDataCall = service
                .getCountryData();

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

            }

            @Override
            public void onFailure(Call<List<CountryDataModel>> call, Throwable t) {
              showErrorSnackBar();
                Log.e(TAG, t.getMessage(), t);
            }
        });


    }

    public void getGlobalData() {

        Call<GlobalStatisticsModel> call=service.getGlobalStatistics();
        call.enqueue(new Callback<GlobalStatisticsModel>() {
            @Override
            public void onResponse(Call<GlobalStatisticsModel> call, Response<GlobalStatisticsModel> response) {
                if(response.code()==200){
                    progressBar.setVisibility(View.INVISIBLE);
                    globalData.setVisibility(View.VISIBLE);
                    GlobalStatisticsModel globalStatisticsModel=response.body();
                    assert globalStatisticsModel!=null;

                    int confirmed_cases=globalStatisticsModel.getTotalConfirmedCases();
                    int total_recoveries=globalStatisticsModel.getTotalRecoveries();
                    int total_deaths=globalStatisticsModel.getTotalDeaths();
                    int new_confirmed=globalStatisticsModel.getNewConfirmedCases();
                    int new_recoveries=globalStatisticsModel.getNewRecoveries();
                    int new_deaths=globalStatisticsModel.getNewDeaths();
                    textTotalConfirmed.setText(Integer.toString(confirmed_cases));
                    textTotalDeaths.setText(Integer.toString(total_deaths));
                    textTotalRecovered.setText(Integer.toString(total_recoveries));
                    textNewConfirmed.setText(new_confirmed+" added");
                    textNewRecoveries.setText(new_recoveries+ " added");
                    textNewDeaths.setText(new_deaths+" added");

                }
            }

            @Override
            public void onFailure(Call<GlobalStatisticsModel> call, Throwable t) {
                Log.i("GLOBAL ERROR",t.getMessage());
                progressBar.setVisibility(View.INVISIBLE);
                showErrorSnackBar();
            }
        });
    }

    private void showErrorSnackBar() {

//        View rootView = findViewById(android.R.id.content);
        final Snackbar snackbar = Snackbar
                .make(rootView, "Error Loading Data", Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction("Retry", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                getGlobalData();
                getWorldData();
                snackbar.dismiss();
            }
        });
        snackbar.show();
    }

}
