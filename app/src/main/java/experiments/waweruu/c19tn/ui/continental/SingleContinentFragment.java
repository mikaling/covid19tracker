package experiments.waweruu.c19tn.ui.continental;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import javax.inject.Inject;

import experiments.waweruu.c19tn.App;
import experiments.waweruu.c19tn.AppExecutors;
import experiments.waweruu.c19tn.R;
import experiments.waweruu.c19tn.databinding.FragmentSingleContinentBinding;
import experiments.waweruu.c19tn.di.AppComponent;
import experiments.waweruu.c19tn.util.StatusReport;
import experiments.waweruu.c19tn.util.Util;
import experiments.waweruu.c19tn.viewmodel.ContinentalViewModel;

import static experiments.waweruu.c19tn.util.Util.getFormattedString;

public class SingleContinentFragment extends Fragment {

    private static final String ARG_PARAM = "continentCode";
    private static final String TAG = "SingleContinentFrag";

    private String mCountryCode;
    private String countries;
    private FragmentSingleContinentBinding binding;
    private ContinentalViewModel viewModel;

    @Inject public ViewModelProvider.Factory factory;
    @Inject public AppExecutors appExecutors;

    public SingleContinentFragment() {}

    public static SingleContinentFragment newInstance(String continentCode) {
        SingleContinentFragment fragment = new SingleContinentFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM, continentCode);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null)
            mCountryCode = getArguments().getString(ARG_PARAM);

        AppComponent appComponent = ((App) requireActivity().getApplication()).getAppComponent();
        appComponent.inject(this);

        viewModel = new ViewModelProvider(this, factory).get(ContinentalViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSingleContinentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.shimmerContinentContainer.startShimmer();

        viewModel.getCountryModelList(Util.getContinentFromCode(mCountryCode))
                .observe(getViewLifecycleOwner(), countryModelList -> {
                    Gson countriesObject = new Gson();
                    countries = countriesObject.toJson(countryModelList);
                    loadWebView();
                });

        viewModel.getContinentTotals(Util.getContinentFromCode(mCountryCode))
                .observe(getViewLifecycleOwner(), entity -> {
                    if(entity != null) {
                        binding.textContConfirmedCases.setText(getFormattedString(entity.getTotalConfirmed()));
                        binding.textContDeaths.setText(getFormattedString(entity.getTotalDeaths()));
                        binding.textContRecoveredCases.setText(getFormattedString(entity.getTotalRecoveries()));
                    }
                });

        viewModel.getStatusReport().observe(getViewLifecycleOwner(), new Observer<StatusReport>() {
            @Override
            public void onChanged(StatusReport statusReport) {
                switch (statusReport.getStatus()) {
                    case SUCCESS:
                        Log.d(TAG, "onChanged: Success. Data loaded.");
                        if(!Util.doesNetworkConnectionExist(requireContext())) {
                            showErrorSnackbar(binding.getRoot(), Util.getContinentFromCode(mCountryCode));
                        }
                        break;

                    case LOADING:
                        Log.d(TAG, "onChanged: Loading. Data being loaded");
                        break;

                    case ERROR:
                        Log.d(TAG, "onChanged: Error: " + statusReport.getMessage());
                        showErrorSnackbar(binding.getRoot(),
                                Util.getContinentFromCode(mCountryCode));
                        binding.shimmerContinentContainer.stopShimmer();
                        break;
                }
            }
        });
    }

    @SuppressLint({"SetJavaScriptEnabled", "AddJavascriptInterface"})
    private void loadWebView() {
        binding.continentWebview.getSettings().setJavaScriptEnabled(true);
        binding.continentWebview.getSettings().setBuiltInZoomControls(true);
        binding.continentWebview.getSettings().setDisplayZoomControls(false);
        binding.continentWebview.addJavascriptInterface(new WebAppInterface(), "Android");
        binding.continentWebview.getSettings().setLoadWithOverviewMode(true);
        binding.continentWebview.getSettings().setUseWideViewPort(true);


        binding.continentWebview.loadUrl("file:///android_asset/continents.html");
    }

    private void showErrorSnackbar(View view, String continent) {
        Snackbar.make(view, "Error loading data. Check your internet connection.",
                BaseTransientBottomBar.LENGTH_INDEFINITE)
                .setAction("Retry", view1 -> {
                    viewModel.refreshContinentalData(continent);
                    loadWebView();
                })
                .show();
    }

    class WebAppInterface {

        public WebAppInterface() {}

        @JavascriptInterface
        public String getContinentCode() {
            return mCountryCode;
        }

        @JavascriptInterface
        public void notifyChartDrawn() {
            appExecutors.getMainThread().execute(() -> {
                binding.shimmerContinentContainer.stopShimmer();
                binding.shimmerContinentContainer.setVisibility(View.GONE);
                binding.continentRelativeLayout.setVisibility(View.VISIBLE);
            });
        }

        @JavascriptInterface
        public String getContinentArrayGeoChartData() {
            return countries;
        }
    }
}
