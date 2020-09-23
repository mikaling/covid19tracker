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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;

import experiments.waweruu.c19tn.App;
import experiments.waweruu.c19tn.R;
import experiments.waweruu.c19tn.databinding.FragmentTrendsBinding;
import experiments.waweruu.c19tn.db.model.CountryInfoModel;
import experiments.waweruu.c19tn.di.AppComponent;
import experiments.waweruu.c19tn.util.MyValueFormatter;
import experiments.waweruu.c19tn.viewmodel.TrendsViewModel;

public class TrendsFragment extends Fragment {

    private static final String ARG_PARAM = "country";

    private String country;

    private FragmentTrendsBinding binding;
    private TrendsViewModel viewModel;
    private static final String TAG = "TrendsFragment";

    @Inject
    ViewModelProvider.Factory factory;

    public TrendsFragment() {}

    public static TrendsFragment newInstance(String country) {
        TrendsFragment fragment = new TrendsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM, country);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        assert getArguments() != null;
        country = getArguments().getString(ARG_PARAM);

        AppComponent appComponent = ((App) requireActivity().getApplication()).getAppComponent();
        appComponent.inject(this);

        viewModel = new ViewModelProvider(this, factory).get(TrendsViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentTrendsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel.getHistoricalData(country).observe(getViewLifecycleOwner(), countryInfoModels -> {
            if(countryInfoModels != null && countryInfoModels.size() > 0) {
                drawLineChart(countryInfoModels);
                Log.d(TAG, String.valueOf(countryInfoModels.size()));
            }
        });

        viewModel.getStatusReport().observe(getViewLifecycleOwner(), statusReport -> {
            switch (statusReport.getStatus()) {
                case SUCCESS:
                    Log.d(TAG, "Status report: Data loading success.");
                    break;

                case ERROR:
                    Log.d(TAG, "Status report. Error." + statusReport.getMessage());
                    showErrorSnackbar();
            }
        });
    }

    private void showErrorSnackbar() {
        Snackbar.make(binding.getRoot(), "Error loading data. Check your internet connection.",
                BaseTransientBottomBar.LENGTH_INDEFINITE)
                .setAction("RETRY", view -> {
                    viewModel.refreshHistoricalData(country);
                })
                .show();
    }

    private void drawLineChart(List<CountryInfoModel> list) {
        List<Entry> confirmedEntries = new ArrayList<>(), deathsEntries = new ArrayList<>(),
                recoveredEntries = new ArrayList<>();
        for(CountryInfoModel m : list) {
            confirmedEntries.add(new Entry(list.indexOf(m), m.getTotalConfirmed()));
            deathsEntries.add(new Entry(list.indexOf(m), m.getTotalDeaths()));
            recoveredEntries.add(new Entry(list.indexOf(m), m.getTotalRecovered()));
        }

        // Creating the dataset fo each line in the graph together with its Label/Legend Value
        LineDataSet confirmedSet = new LineDataSet(confirmedEntries, "Confirmed");
        LineDataSet deathsSet = new LineDataSet(deathsEntries, "Deaths");
        LineDataSet recoveredSet = new LineDataSet(recoveredEntries, "Recovered");

        // Combines the datasets created above into one that will be used to feed the line chart with data
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(confirmedSet);
        dataSets.add(deathsSet);
        dataSets.add(recoveredSet);

        // Setting properties of the deaths line in the line chart
        deathsSet.setLineWidth(1.5f);
        deathsSet.setColor(Color.parseColor("#180638"));
        deathsSet.setDrawCircles(false);
        deathsSet.setDrawCircleHole(false);
        deathsSet.setValueTextSize(10);
        deathsSet.setValueTextColor(Color.BLACK);

        // Setting properties of the confirmed line in the line chart
        confirmedSet.setLineWidth(1.5f);
        confirmedSet.setColor(Color.parseColor("#FF0000"));
        confirmedSet.setDrawCircles(false);
        confirmedSet.setDrawCircleHole(false);
        confirmedSet.setValueTextSize(10);
        confirmedSet.setValueTextColor(Color.BLACK);

        // Setting properties of the recovered line in the line chart
        recoveredSet.setLineWidth(1.5f);
        recoveredSet.setColor(Color.parseColor("#F1F50A"));
        recoveredSet.setDrawCircles(false);
        recoveredSet.setDrawCircleHole(false);
        recoveredSet.setValueTextSize(10);
        recoveredSet.setValueTextColor(Color.BLACK);

        // Get the legend object from the line chart
        Legend legend = binding.lineChart.getLegend();

        // Set properties of the legend
        legend.setEnabled(true);
        legend.setTextColor(Color.BLACK);
        legend.setTextSize(10);
        legend.setForm(Legend.LegendForm.LINE);
        legend.setFormSize(20);
        legend.setXEntrySpace(10);
        legend.setFormToTextSpace(10);

        // Getting the axes objects from the line charts
        XAxis xAxis = binding.lineChart.getXAxis();
        YAxis yAxisLeft = binding.lineChart.getAxisLeft();
        YAxis yAxisRight = binding.lineChart.getAxisRight();

        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        yAxisLeft.setDrawLabels(true);
        yAxisLeft.setAxisMinimum(0);
        xAxis.setDrawGridLines(false);
        yAxisLeft.setDrawGridLines(false);
        yAxisLeft.setAxisLineColor(Color.BLACK);
        yAxisRight.setEnabled(false);

        // Manually setting the description found at the bottom of the chart
        Description description = new Description();
        description.setText("");
        description.setTextColor(Color.BLACK);
        description.setTextSize(10);
        binding.lineChart.setDescription(description);

        // Setting background colour for the chart
        binding.lineChart.setBackgroundColor(Color.parseColor("#4cada2"));

        // Setting the message to be shown if no data is available in the chart
        binding.lineChart.setNoDataText("No Data Available at the moment");
        binding.lineChart.setNoDataTextColor(Color.RED);

        // Setting the data that the Line Chart will use through a LineData  object
        LineData data = new LineData(dataSets);
        data.setValueFormatter(new MyValueFormatter());
        binding.lineChart.setData(data);
        binding.lineChart.invalidate();
        binding.lineChartTitle.setText(
                String.format(Locale.ENGLISH, "Trends of COVID-19 in %s", country)
        );
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
