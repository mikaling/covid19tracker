package experiments.waweruu.c19tn.ui.metrics;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import javax.inject.Inject;

import experiments.waweruu.c19tn.App;
import experiments.waweruu.c19tn.R;
import experiments.waweruu.c19tn.databinding.FragmentMetricsBinding;
import experiments.waweruu.c19tn.db.entity.CountryDataEntity;
import experiments.waweruu.c19tn.di.AppComponent;
import experiments.waweruu.c19tn.util.Util;
import experiments.waweruu.c19tn.viewmodel.MetricsViewModel;

public class MetricsFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private static final String TAG = "MetricsFragment";

    private FragmentMetricsBinding binding;
    private MetricsViewModel viewModel;

    @Inject public ViewModelProvider.Factory factory;

    public MetricsFragment() {}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AppComponent appComponent = ((App) requireActivity().getApplication()).getAppComponent();
        appComponent.inject(this);

        viewModel = new ViewModelProvider(this, factory).get(MetricsViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentMetricsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(
                requireContext(), R.array.statistics_array, android.R.layout.simple_spinner_item
        );
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        binding.statSelector.setAdapter(arrayAdapter);
        binding.statSelector.setOnItemSelectedListener(this);

        binding.recyclerview.setLayoutManager(new LinearLayoutManager(requireContext()));

        binding.statSelector.setSelection(1);

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String choice = (String) adapterView.getItemAtPosition(i);
        int groupId = Util.getGroupFromChoice(choice);
        String dbChoice = Util.getColumnFromChoice(choice);
        int titleId = Util.getTitleIdFromChoice(choice);
        viewModel.getMetrics(dbChoice).observe(getViewLifecycleOwner(), countryDataEntities -> {
            MetricsAdapter metricsAdapter = new MetricsAdapter(countryDataEntities, groupId);
            metricsAdapter.notifyDataSetChanged();
            binding.recyclerview.setAdapter(metricsAdapter);
            binding.statTitle.setText(titleId);
            binding.shimmerMetrics.setVisibility(View.INVISIBLE);
            binding.contentMetrics.setVisibility(View.VISIBLE);
        });

        viewModel.getStatusReport().observe(getViewLifecycleOwner(), statusReport -> {
            switch (statusReport.getStatus()) {
                case SUCCESS:
                    Log.d(TAG, "Status report success: " + statusReport.getMessage());
                    break;

                case ERROR:
                    Log.d(TAG, "Status report error: " + statusReport.getMessage());
                    showErrorSnackbar();
                    break;
            }
        });
    }

    private void showErrorSnackbar() {
        Snackbar.make(binding.getRoot(),
                "Error loading data. Please check your internet connection",
                BaseTransientBottomBar.LENGTH_INDEFINITE)
                .setAction("Retry", view -> {
                    viewModel.refreshCountryData();
                })
                .setAnchorView(R.id.bottom_navigation)
                .show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
