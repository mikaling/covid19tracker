package com.example.covid19tracker.local;

import android.content.Context;
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
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;

import com.example.covid19tracker.R;
import com.example.covid19tracker.databinding.FragmentLocalBinding;
import com.example.covid19tracker.model.CountrySlugModel;
import com.example.covid19tracker.network.RetrofitClientInstance;
import com.example.covid19tracker.network.TestApi;
import com.example.covid19tracker.response.CountrySlugResponse;
import com.example.covid19tracker.utils.Utils;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LocalFragment extends Fragment implements View.OnClickListener
{
    private FragmentLocalBinding binding;
    private static final String TAG = "LocalFragment";
    private List<CountrySlugModel> modelList;
    private Context context;
    private String slug, country;

    public LocalFragment()
    {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLocalBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.trendsButton.setOnClickListener(this);
        binding.comparisonButton.setOnClickListener(this);

        TestApi service = RetrofitClientInstance.getRetrofitInstance().create(TestApi.class);
        Call<CountrySlugResponse> call = service.getCountrySlugs();
        call.enqueue(new Callback<CountrySlugResponse>() {
            @Override
            public void onResponse(Call<CountrySlugResponse> call, Response<CountrySlugResponse> response) {
                if(response.body().getStatus().equals(Utils.RESPONSE_SUCCESS)) {
                    //Query has been a success
                    Log.i(TAG, "Response received from API - success");
                    modelList = response.body().getWrap().getModelList();
                    populateSpinner();
                } else {
                    //Query has failed
                    Log.i(TAG, "Response received from API - fail");
                }
            }

            @Override
            public void onFailure(Call<CountrySlugResponse> call, Throwable t) {
                Log.e(TAG, t.getMessage(), t);
            }
        });
    }

    private void populateSpinner() {
        List<String> spinnerArray = new ArrayList<>();
        int default_position = 0;
        int china_position = 0;
//        modelList.removeIf(model -> model.getCountry().equals("China"));
        for(int i = 0; i < modelList.size(); i++) {
            if(modelList.get(i).getCountry().equals("China"))
                china_position = i;
        }
        modelList.remove(china_position);
        for(int j = 0; j< modelList.size(); j++ ) {
            spinnerArray.add(modelList.get(j).getCountry());
            if (modelList.get(j).getCountry().equals("Kenya"))
                default_position = j;
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context,
                android.R.layout.simple_spinner_item, spinnerArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.countrySelector.setTitle("Select a country");
        binding.countrySelector.setAdapter(adapter);
        binding.countrySelector.setSelection(default_position);
        binding.countrySelector.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                slug = modelList.get(position).getSlug();
                country = modelList.get(position).getCountry();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.trends_button:
                NavDirections trendsAction =
                        LocalFragmentDirections.actionLocalFragmentToLineChartFragment2(
                                slug, country
                        );
                NavHostFragment.findNavController(this).navigate(trendsAction);
                break;

            case R.id.comparison_button:
                NavDirections comparisonAction = LocalFragmentDirections
                        .actionLocalFragmentToBarChartFragment();
                NavHostFragment.findNavController(this).navigate(comparisonAction);
                break;

            default:
                break;
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.context = null;
    }
}
