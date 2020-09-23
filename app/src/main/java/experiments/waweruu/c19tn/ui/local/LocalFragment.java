package experiments.waweruu.c19tn.ui.local;

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
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import javax.inject.Inject;

import experiments.waweruu.c19tn.App;
import experiments.waweruu.c19tn.R;
import experiments.waweruu.c19tn.databinding.FragmentLocalBinding;
import experiments.waweruu.c19tn.di.AppComponent;
import experiments.waweruu.c19tn.viewmodel.LocalViewModel;

public class LocalFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "LocalFragment";

    private FragmentLocalBinding binding;
    private LocalViewModel viewModel;
    private String countryName;

    @Inject public ViewModelProvider.Factory factory;

    public LocalFragment() {}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AppComponent appComponent = ((App) requireActivity().getApplication()).getAppComponent();
        appComponent.inject(this);

        viewModel = new ViewModelProvider(this, factory).get(LocalViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentLocalBinding.inflate(inflater, container, false);
        binding.trendsButton.setVisibility(View.INVISIBLE);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.trendsButton.setOnClickListener(this);
        binding.comparisonButton.setOnClickListener(this);

        viewModel.getCountryNames().observe(getViewLifecycleOwner(), strings -> {
            if(strings != null) {
                strings.remove("China");
                int default_pos = strings.indexOf("Kenya");

                ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(),
                        android.R.layout.simple_spinner_item, strings);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                binding.countrySelector.setTitle("Select a country");
                binding.countrySelector.setAdapter(adapter);
                binding.countrySelector.setSelection(default_pos);
                binding.countrySelector.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view1, int i, long l) {
                        countryName = strings.get(i);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
                binding.trendsButton.setVisibility(View.VISIBLE);
            }
        });

        viewModel.getStatusReport().observe(getViewLifecycleOwner(), statusReport -> {
            switch (statusReport.getStatus()) {
                case ERROR:
                    showErrorSnackbar();
                    break;

                case SUCCESS:
                    Log.d(TAG, "Data fetch successful");
            }
        });
    }

    private void showErrorSnackbar() {
        Snackbar.make(binding.getRoot(), "Error loading data. Check your internet connection.",
                BaseTransientBottomBar.LENGTH_INDEFINITE)
                .setAction("RETRY", this)
                .show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.trends_button:
                if(countryName != null) {
                    NavDirections trendsAction = LocalFragmentDirections
                            .actionLocalFragmentToTrendsFragment(countryName);
                    NavHostFragment.findNavController(this).navigate(trendsAction);
                } else {
                    Snackbar.make(binding.getRoot(), "Select a country first",
                            BaseTransientBottomBar.LENGTH_SHORT)
                            .show();
                }
                break;
            case R.id.comparison_button:
                NavDirections comparisonAction = LocalFragmentDirections
                        .actionLocalFragmentToComparisonFragment();
                NavHostFragment.findNavController(this).navigate(comparisonAction);
                break;
        }

        if(view.equals(binding.getRoot())) {
            viewModel.refreshCountrySlugs();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
