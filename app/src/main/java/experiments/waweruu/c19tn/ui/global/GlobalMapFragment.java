package experiments.waweruu.c19tn.ui.global;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.List;

import javax.inject.Inject;

import experiments.waweruu.c19tn.App;
import experiments.waweruu.c19tn.AppExecutors;
import experiments.waweruu.c19tn.databinding.FragmentGlobalMapBinding;
import experiments.waweruu.c19tn.di.AppComponent;
import experiments.waweruu.c19tn.util.Util;
import experiments.waweruu.c19tn.viewmodel.GlobalViewModel;

public class GlobalMapFragment extends Fragment {

    private static final String TAG = "GlobalMapFrag";

    private FragmentGlobalMapBinding binding;
//    private GlobalMapViewModel viewModel;
    private GlobalViewModel viewModel;
    private String countries;

    @Inject public ViewModelProvider.Factory factory;
    @Inject public AppExecutors appExecutors;

    public GlobalMapFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AppComponent appComponent = ((App) requireActivity().getApplication()).getAppComponent();
        appComponent.inject(this);

//        viewModel = new ViewModelProvider(this, factory)
//                .get(GlobalMapViewModel.class);
        viewModel = new ViewModelProvider(this, factory).get(GlobalViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentGlobalMapBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel.getCountryInfo().observe(getViewLifecycleOwner(), countryInfoModels -> {
            Gson countriesObject = new Gson();
            countries = countriesObject.toJson(countryInfoModels);
            loadWebview();
        });

        viewModel.getCountryDataStatusReport().observe(getViewLifecycleOwner(), statusReport -> {
            switch (statusReport.getStatus()) {
                case LOADING:
                    Toast.makeText(requireContext(), "Map Loading", Toast.LENGTH_SHORT).show();
                    break;

                case SUCCESS:
//                    Toast.makeText(requireContext(), "Map Success", Toast.LENGTH_SHORT).show();
                    connectionCheck();
                    break;

                case ERROR:
                    //Toast.makeText(requireContext(), "Map Error", Toast.LENGTH_LONG).show();
                    viewModel.showErrorSnackbar(binding.getRoot());
                    break;
            }
        });
    }

    private void connectionCheck() {
        if(!Util.doesNetworkConnectionExist(requireContext())) {
            viewModel.showErrorSnackbar(binding.getRoot());
            loadWebview();
        }
//        if(Util.doesNetworkConnectionExist(requireContext())) {
//            binding.progressBarGlobalMap.setVisibility(View.GONE);
//            binding.mapWebview.setVisibility(View.VISIBLE);
//        } else {
//            viewModel.showErrorSnackbar(binding.getRoot());
//        }
    }

    @SuppressLint({"SetJavaScriptEnabled", "AddJavascriptInterface"})
    private void loadWebview() {
        binding.mapWebview.getSettings().setJavaScriptEnabled(true);
        binding.mapWebview.getSettings().setBuiltInZoomControls(true);
        binding.mapWebview.getSettings().setDisplayZoomControls(false);
        binding.mapWebview.addJavascriptInterface(new WebAppInterface(), "Android");

        binding.mapWebview.getSettings().setLoadWithOverviewMode(true);
        binding.mapWebview.getSettings().setUseWideViewPort(true);

        binding.mapWebview.loadUrl("file:///android_asset/global_geochart.html");
    }

    class WebAppInterface {

        WebAppInterface() {}

        @JavascriptInterface
        public String getArrayGeoChartData() {
            return countries;
        }

        @JavascriptInterface
        public void notifyChartDrawn() {
            appExecutors.getMainThread().execute(() -> {
//                Toast.makeText(requireContext(), "Charts drawn", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "notifyChartDrawn: Charts drawn");
                if(binding != null) {
                    binding.progressBarGlobalMap.setVisibility(View.GONE);
                    binding.mapWebview.setVisibility(View.VISIBLE);
                }
            });
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}