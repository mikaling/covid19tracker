package com.example.covid19tracker.continents;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.covid19tracker.R;
import com.example.covid19tracker.model.ContinentTotalsModel;
import com.example.covid19tracker.model.CountryDataModel;
import com.example.covid19tracker.model.CountryModel;
import com.example.covid19tracker.network.RetrofitClientInstance;
import com.example.covid19tracker.network.TestApi;
import com.example.covid19tracker.response.ContinentDataResponse;
import com.example.covid19tracker.utils.Utils;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SingleContinentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SingleContinentFragment extends Fragment {
    private static final String ARG_PARAM1 = "continentCode";
    private static final String TAG = "SingleContinentFragment";

    private List<CountryDataModel> countriesResponse;
    private ContinentTotalsModel continentTotalsModel;
    private List<CountryModel> countriesList = new ArrayList<>();
    private TestApi service;
    private String countries;
    private String continentName;


    private String mCountryCode;

    private WebView webView;
    private RelativeLayout continentRelativeLayout;
    private ShimmerFrameLayout shimmerFrameLayout;

    private TextView continentCases, continentRecoveries, continentDeaths;

    public SingleContinentFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param continentCode Parameter 1.
     * @return A new instance of fragment SingleContinentFragment.
     */
    public static SingleContinentFragment newInstance(String continentCode) {
        SingleContinentFragment fragment = new SingleContinentFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, continentCode);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mCountryCode = getArguments().getString(ARG_PARAM1);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_single_continent, container, false);
        webView = view.findViewById(R.id.continent_webview);
        continentRelativeLayout = view.findViewById(R.id.continent_relative_layout);

        continentCases = view.findViewById(R.id.text_cont_confirmed_cases);
        continentRecoveries = view.findViewById(R.id.text_cont_recovered_cases);
        continentDeaths = view.findViewById(R.id.text_cont_deaths);
        return view;
    }

    public class WebAppInterface {
        Context mContext;

        public WebAppInterface(Context context) {
            mContext = context;
        }

        @JavascriptInterface
        public String getContinentCode() {
            return mCountryCode;
        }

        @JavascriptInterface
        public String getContinentArrayGeoChartData() {
            return countries;
        }

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        shimmerFrameLayout = view.findViewById(R.id.shimmer_continent_container);
        shimmerFrameLayout.startShimmer();
        Log.i(TAG, "Code: " + mCountryCode);

        service = RetrofitClientInstance
                .getRetrofitInstance().create(TestApi.class);

        if (mCountryCode.equals("002")) {
            continentName = "Africa";
        } else if (mCountryCode.equals("150")) {
            continentName = "Europe";
        } else if (mCountryCode.equals("142")) {
            continentName = "Asia";
        } else if (mCountryCode.equals("021")) {
            continentName = "NorthAmerica";
        } else if (mCountryCode.equals("005")) {
            continentName = "SouthAmerica";
        } else if (mCountryCode.equals("009")) {
            continentName = "Oceania";
        }

        getWorldData();

    }

    public void loadWebView() {
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);
        webView.addJavascriptInterface(new WebAppInterface(getContext()), "Android");
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);


        webView.loadUrl("file:///android_asset/continents.html");
        continentRelativeLayout.setVisibility(View.VISIBLE);
    }

    private void getWorldData() {

        Call<ContinentDataResponse> continentDataResponseCall = service
                .getContinentData(continentName);
        continentDataResponseCall.enqueue(new Callback<ContinentDataResponse>() {
            @Override
            public void onResponse(Call<ContinentDataResponse> call, Response<ContinentDataResponse> response) {
                if (response.body().getStatus().equals(Utils.RESPONSE_SUCCESS)) {
                    Log.i(TAG, "Response received from API call");

                    //Edit: Changed wrap to Continent data wrap
                    countriesResponse = response.body().getContinentDataWrap().getCountryDataModelList();
                    continentTotalsModel = response.body().getContinentDataWrap().getContinentTotalsModel();
                    //Also added the ContinentTotalsModel object that you can access with the method
                    //response.body().getContinentDataWrap().getContinentTotalsModel()
                    //The object contains totalConfirmed, totalRecoveries, totalDeaths for a
                    //continent

                    for (int i = 0; i < countriesResponse.size(); i++) {
                        CountryModel countryModel = new CountryModel(
                                countriesResponse.get(i).getTotalConfirmed(),
                                countriesResponse.get(i).getCountry()

                        );
                        countriesList.add(countryModel);
                    }

                    Gson countriesObject = new Gson();
                    countries = countriesObject.toJson(countriesList);
                    shimmerFrameLayout.stopShimmer();
                    shimmerFrameLayout.setVisibility(View.INVISIBLE);
                    continentRelativeLayout.setVisibility(View.VISIBLE);
                    loadWebView();

                    continentCases.setText(
                            String.format(Locale.ENGLISH, Utils.SEPARATOR_FORMAT,
                                    continentTotalsModel.getTotalConfirmed())
                    );

                    continentRecoveries.setText(
                            String.format(Locale.ENGLISH, Utils.SEPARATOR_FORMAT,
                                    continentTotalsModel.getTotalRecoveries())
                    );

                    continentDeaths.setText(
                            String.format(Locale.ENGLISH, Utils.SEPARATOR_FORMAT,
                                    continentTotalsModel.getTotalDeaths())
                    );
                }
            }

            @Override
            public void onFailure(Call<ContinentDataResponse> call, Throwable t) {
                showErrorSnackBar();
            }
        });


    }

    private void showErrorSnackBar() {

        final Snackbar snackbar = Snackbar
                .make(continentRelativeLayout, "Error Loading Data", Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction("Retry", v -> {
            shimmerFrameLayout.startShimmer();
//            getGlobalData();
            getWorldData();
//            fetchData();
            snackbar.dismiss();
        });
        snackbar.show();
    }

}
