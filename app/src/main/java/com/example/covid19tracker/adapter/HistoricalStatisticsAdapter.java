package com.example.covid19tracker.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.covid19tracker.databinding.HistoricalDataItemBinding;
import com.example.covid19tracker.model.BarDataModel;

import java.util.List;

public class HistoricalStatisticsAdapter extends RecyclerView.Adapter<HistoricalStatisticsAdapter.HistoricalStatisticsViewHolder>
{
    private Context context;
    private List<BarDataModel> listOfHistoricalStatistics;

    public HistoricalStatisticsAdapter(Context context, List<BarDataModel> listOfHistoricalStatistics)
    {
        this.context = context;
        this.listOfHistoricalStatistics = listOfHistoricalStatistics;
    }

    @NonNull
    @Override
    public HistoricalStatisticsAdapter.HistoricalStatisticsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        /* Android 3.6.* provides view binding which removes the need to use the findViewById()
         * method to instantiate view objects */
        HistoricalDataItemBinding itemBinding = HistoricalDataItemBinding
                .inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new HistoricalStatisticsViewHolder(itemBinding);
    }

    @Override
    public void
    onBindViewHolder(@NonNull HistoricalStatisticsAdapter.HistoricalStatisticsViewHolder holder, int position) {
        BarDataModel statisticsModel = listOfHistoricalStatistics.get(position);

        holder.historicalItemBinding.countryName.setText(statisticsModel.getCountry());
        holder.historicalItemBinding.confirmedCases.setText(String.valueOf(statisticsModel.getTotalConfirmed()));
        holder.historicalItemBinding.deaths.setText(String.valueOf(statisticsModel.getTotalDeaths()));
        holder.historicalItemBinding.recoveries.setText(String.valueOf(statisticsModel.getTotalRecovered()));
//        holder.historicalItemBinding.activeCases.setText(String.valueOf(statisticsModel.getConfirmedActiveCases()));
        holder.historicalItemBinding.reportDate.setText(statisticsModel.getDate());
    }

    @Override
    public int getItemCount() {
        return listOfHistoricalStatistics.size();
    }

    class HistoricalStatisticsViewHolder extends RecyclerView.ViewHolder {

        /* Here you can see why view binding is better than data binding in this case. Instead of
         * declaring the TextViews we have in the view and then initializing them in the
         * constructor, we only declare an instance of our view's binding class that we instantiate
         * and use to access the views within in onBindViewHolder()*/

        private HistoricalDataItemBinding historicalItemBinding;

        HistoricalStatisticsViewHolder(@NonNull HistoricalDataItemBinding itemBinding) {
            super(itemBinding.getRoot());
            historicalItemBinding = itemBinding;
        }
    }
}
