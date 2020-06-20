package com.example.covid19tracker.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.covid19tracker.databinding.CountryDataItemBinding;
import com.example.covid19tracker.model.CountryDataModel;

import java.util.List;

public class CountryDataAdapter extends RecyclerView.Adapter<CountryDataAdapter.CountryDataViewHolder> {

    private Context context;
    private List<CountryDataModel> countryDataModelList;

    public CountryDataAdapter(Context context, List<CountryDataModel> countryDataModelList) {
        this.context = context;
        this.countryDataModelList = countryDataModelList;
    }

    @NonNull
    @Override
    public CountryDataAdapter.CountryDataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        /* Android 3.6.* provides view binding which removes the need to use
         * the findViewById()
         * method to instantiate view objects */
        CountryDataItemBinding itemBinding = CountryDataItemBinding
                .inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new CountryDataViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull CountryDataAdapter.CountryDataViewHolder holder, int position) {
        CountryDataModel countryDataModel = countryDataModelList.get(position);

        holder.itemBinding.countryName.setText(countryDataModel.getCountryDataName());
        holder.itemBinding.confirmedCases.setText(countryDataModel.getCountryDataTotalConfirmedCases());
        holder.itemBinding.deaths.setText(countryDataModel.getCountryDataTotalDeaths());
        holder.itemBinding.recoveries.setText(countryDataModel.getCountryDataTotalRecoveries());
        holder.itemBinding.reportDate.setText(countryDataModel.getCountryDataReportDate());
    }

    @Override
    public int getItemCount() {
        return countryDataModelList.size();
    }

    class CountryDataViewHolder extends RecyclerView.ViewHolder {

        /* Here you can see why view binding is better than data binding
         * in this case. Instead of declaring the TextViews we have in the view
         * and then initializing them in the constructor, we only declare an
         * instance of our view's binding class that we instantiate
         * and use to access the views within in onBindViewHolder()*/

        private CountryDataItemBinding itemBinding;

        CountryDataViewHolder(@NonNull CountryDataItemBinding itemBinding) {
            super(itemBinding.getRoot());
            this.itemBinding = itemBinding;
        }
    }
}
