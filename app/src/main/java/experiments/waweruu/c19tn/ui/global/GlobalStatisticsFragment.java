package experiments.waweruu.c19tn.ui.global;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.Locale;
import java.util.Objects;

import javax.inject.Inject;

import experiments.waweruu.c19tn.App;
import experiments.waweruu.c19tn.AppExecutors;
import experiments.waweruu.c19tn.databinding.FragmentGlobalStatisticsBinding;
import experiments.waweruu.c19tn.databinding.ViewGlobalStatisticsBinding;
import experiments.waweruu.c19tn.di.AppComponent;
import experiments.waweruu.c19tn.local.entity.GlobalStatisticsEntity;
import experiments.waweruu.c19tn.util.StatusReport;
import experiments.waweruu.c19tn.util.Util;
import experiments.waweruu.c19tn.viewmodel.GlobalViewModel;

import static experiments.waweruu.c19tn.util.Util.getFormattedString;

public class GlobalStatisticsFragment extends Fragment {

    private static final String TAG = "GlobalStatsFrag";

    private FragmentGlobalStatisticsBinding binding;
    private ViewGlobalStatisticsBinding statisticsBinding;
//    public GlobalStatisticsViewModel viewModel;
    private GlobalViewModel viewModel;
    @Inject public ViewModelProvider.Factory factory;
    @Inject public AppExecutors appExecutors;

    public GlobalStatisticsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AppComponent appComponent = ((App) requireActivity().getApplication()).getAppComponent();
        appComponent.inject(this);

        // Create a ViewModel the first time the system calls onCreate()
        // Recreated activities receive the same instance created by the first fragment
//        viewModel = new ViewModelProvider(this, factory)
//                .get(GlobalStatisticsViewModel.class);
        viewModel = new ViewModelProvider(this, factory).get(GlobalViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentGlobalStatisticsBinding.inflate(inflater, container, false);
        statisticsBinding = binding.viewGlobalStatistics;
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.shimmerGlobalStatistics.startShimmer();

        viewModel.getGlobalStatistics().observe(getViewLifecycleOwner(), this::updateUi);

        viewModel.getGlobalStatisticsStatusReport().observe(getViewLifecycleOwner(), new Observer<StatusReport>() {
            @Override
            public void onChanged(StatusReport statusReport) {
                switch (statusReport.getStatus()) {
                    case SUCCESS:
                        //Toast.makeText(requireContext(), "Success", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "onChanged: Success");
                        binding.shimmerGlobalStatistics.stopShimmer();
                        binding.shimmerGlobalStatistics.setVisibility(View.INVISIBLE);
                        statisticsBinding.getRoot().setVisibility(View.VISIBLE);
                        break;

                    case ERROR:
                        //Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show();
                        //Log.d(TAG, "onChanged: Error");
                        binding.shimmerGlobalStatistics.stopShimmer();
                        viewModel.showErrorSnackbar(binding.getRoot());
                        break;

                    case LOADING:
                        //Toast.makeText(requireContext(), "Loading", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "onChanged: Loading");
                        break;
                }
            }
        });
    }

    public void updateUi(GlobalStatisticsEntity globalStatistics) {
        if (globalStatistics != null) {
            statisticsBinding.textConfirmedCases.setText(getFormattedString(globalStatistics.getTotalConfirmed()));
            statisticsBinding.textTotalDeaths.setText(getFormattedString(globalStatistics.getTotalDeaths()));
            statisticsBinding.textRecoveredCases.setText(getFormattedString(globalStatistics.getTotalRecovered()));
            statisticsBinding.textNewConfirmedCases.setText(getFormattedString(globalStatistics.getNewConfirmed()));
            statisticsBinding.textNewDeaths.setText(getFormattedString(globalStatistics.getNewDeaths()));
            statisticsBinding.textNewRecovered.setText(getFormattedString(globalStatistics.getNewRecovered()));
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        statisticsBinding = null;
    }
}