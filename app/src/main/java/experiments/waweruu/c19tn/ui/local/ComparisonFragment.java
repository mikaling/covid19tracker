package experiments.waweruu.c19tn.ui.local;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import experiments.waweruu.c19tn.App;
import experiments.waweruu.c19tn.databinding.FragmentComparisonBinding;
import experiments.waweruu.c19tn.db.model.CountryInfoModel;
import experiments.waweruu.c19tn.di.AppComponent;
import experiments.waweruu.c19tn.viewmodel.ComparisonViewModel;

public class ComparisonFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "ComparisonFragment";

    private FragmentComparisonBinding binding;
    private ComparisonViewModel viewModel;

    @Inject public ViewModelProvider.Factory factory;

    public ComparisonFragment() {}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AppComponent appComponent = ((App) requireActivity().getApplication()).getAppComponent();
        appComponent.inject(this);

        viewModel = new ViewModelProvider(this, factory).get(ComparisonViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentComparisonBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel.getCountryInfo().observe(getViewLifecycleOwner(), countryInfoModels -> {
            if(countryInfoModels != null) {
                drawBarChart(countryInfoModels);
            }
        });

        viewModel.getStatusReport().observe(getViewLifecycleOwner(), statusReport -> {
            switch (statusReport.getStatus()) {
                case SUCCESS:
                    Log.d(TAG, "statusReport: success " + statusReport.getMessage());
                    break;

                case ERROR:
                    Log.d(TAG, "statusReport: error " + statusReport.getMessage());
                    showErrorSnackBar();
                    break;

                case LOADING:
                    Log.d(TAG, "statusReport: loading " + statusReport.getMessage());
                    break;
            }
        });
    }

    private void showErrorSnackBar() {
        Snackbar.make(binding.getRoot(), "Error loading data.",
                BaseTransientBottomBar.LENGTH_INDEFINITE)
                .setAction("RETRY", this)
                .show();
    }

    @Override
    public void onClick(View view) {
        if(view.equals(binding.getRoot())) {
            viewModel.refreshCountryData();
        }
    }

    private void drawBarChart(List<CountryInfoModel> l) {
        float barSpace = 0f;
        float groupSpace = 0.1f;

        List<BarEntry> confirmedSet = new ArrayList<>(), deathsSet = new ArrayList<>(), recoveredSet = new ArrayList<>();

        for(CountryInfoModel m : l) {
            confirmedSet.add(new BarEntry(l.indexOf(m), m.getTotalConfirmed()));
            deathsSet.add(new BarEntry(l.indexOf(m), m.getTotalDeaths()));
            recoveredSet.add(new BarEntry(l.indexOf(m), m.getTotalRecovered()));
        }

        BarDataSet confirmedBarSet = new BarDataSet(confirmedSet, "Confirmed Cases");
        confirmedBarSet.setColor(Color.parseColor("#FF0000"));
        BarDataSet deathsBarSet = new BarDataSet(deathsSet, "Deaths");
        deathsBarSet.setColor(Color.parseColor("#330B5C"));
        BarDataSet recoveredBarSet = new BarDataSet(recoveredSet, "Recoveries");
        recoveredBarSet.setColor(Color.parseColor("#F1F50A"));

        XAxis xAxis = binding.barChart.getXAxis();
        YAxis yAxisLeft = binding.barChart.getAxisLeft();
        YAxis yAxisRight = binding.barChart.getAxisRight();

        String[] barChartLabels = {"Burundi", "Kenya", "Rwanda", "Tanzania", "Uganda"};

        xAxis.setValueFormatter(new IndexAxisValueFormatter(barChartLabels));
        xAxis.setCenterAxisLabels(true);
        xAxis.setGranularity(1);
        xAxis.setGranularityEnabled(true);
        xAxis.setDrawGridLines(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        yAxisLeft.setDrawLabels(true);
        yAxisLeft.setAxisMinimum(0);
        yAxisLeft.setDrawGridLines(false);
        yAxisLeft.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);

        yAxisRight.setEnabled(false);

        Description description = new Description();
        description.setText("");
        binding.barChart.setDescription(description);

        binding.barChart.setBackgroundColor(Color.parseColor("#CAF0F8"));

        BarData barData = new BarData(confirmedBarSet, deathsBarSet, recoveredBarSet);
        barData.setBarWidth(0.3f);

        xAxis.setAxisMinimum(-barData.getBarWidth() / 2);
        xAxis.setAxisMaximum(5 - barData.getBarWidth() / 2);

        binding.barChart.setFitBars(true);
        binding.barChart.setData(barData);
        binding.barChart.setDragEnabled(true);
        binding.barChart.setVisibleXRangeMaximum(3);
        binding.barChart.groupBars(0, groupSpace, barSpace);

        binding.barChart.invalidate();
    }
}
