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
import com.example.covid19tracker.network.RetrofitClientInstance;
import com.example.covid19tracker.network.TestApi;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WorldFragment extends Fragment {


    private static final String TAG = "WorldFragment";
    private WebView webView;
    private List<CountryDataModel> countriesResponse;
    private List<CountryInfoModel> countriesList = new ArrayList<>();
    private List<String> listOfCountries = new ArrayList();
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
            Log.i(TAG, "Countries list: " + Arrays.deepToString(countriesList.toArray()));
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
        rootView=view.findViewById(R.id.content);
        viewGlobal = view.findViewById(R.id.view_global);
        shimmerFrameLayout = view.findViewById(R.id.shimmer_global_container);
        shimmerFrameLayout.startShimmer();

        /**/
        service = RetrofitClientInstance
                .getRetrofitInstance().create(TestApi.class);

        /**/

        webView = view.findViewById(R.id.map_webview);
        getWorldData();
        getGlobalData();

    }

    private void getWorldData(){

        Call<List<CountryDataModel>> countryDataCall = service
                .getCountryData();

        countryDataCall.enqueue(new Callback<List<CountryDataModel>>() {
            @Override
            public void onResponse(Call<List<CountryDataModel>> call,
                                   Response<List<CountryDataModel>> response) {

                Log.i(TAG, "Response received from API call");

                countriesResponse = response.body();

                for (int i = 0; i < countriesResponse.size(); i++) {
                    CountryInfoModel countryInfoModel = new CountryInfoModel(
                            countriesResponse.get(i).getCountryDataName(),
                            countriesResponse.get(i).getCountryDataTotalConfirmedCases()
                    );
                    countriesList.add(countryInfoModel);
                }

                Gson countriesObject = new Gson();
                countries = countriesObject.toJson(countriesList);

                loadWebView();
            }

            @Override
            public void onFailure(Call<List<CountryDataModel>> call, Throwable t) {
//                showErrorSnackBar();
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

        Call<GlobalStatisticsModel> call=service.getGlobalStatistics();
        call.enqueue(new Callback<GlobalStatisticsModel>() {
            @Override
            public void onResponse(Call<GlobalStatisticsModel> call, Response<GlobalStatisticsModel> response) {
                if(response.code()==200){
                    GlobalStatisticsModel globalStatisticsModel=response.body();
                    assert globalStatisticsModel!=null;

                    int confirmed_cases = globalStatisticsModel.getTotalConfirmedCases();
                    int total_recoveries = globalStatisticsModel.getTotalRecoveries();
                    int total_deaths = globalStatisticsModel.getTotalDeaths();
                    int new_confirmed = globalStatisticsModel.getNewConfirmedCases();
                    int new_recoveries = globalStatisticsModel.getNewRecoveries();
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
            public void onFailure(Call<GlobalStatisticsModel> call, Throwable t) {
                Log.i("GLOBAL ERROR",t.getMessage());
                shimmerFrameLayout.stopShimmer();
//                showErrorSnackBar();
            }
        });
    }

    private void showErrorSnackBar() {

        final Snackbar snackbar = Snackbar
                .make(rootView, "Error Loading Data", Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction("Retry", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shimmerFrameLayout.startShimmer();
                getGlobalData();
                getWorldData();
                snackbar.dismiss();
            }
        });
        snackbar.show();
    }

    //TODO:Improve on the shimmer effect, add onPause and onResume implementations
    //TODO:Work on the web view layout
}
