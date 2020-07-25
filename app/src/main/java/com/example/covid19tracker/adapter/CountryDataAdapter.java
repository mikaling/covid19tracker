package com.example.covid19tracker.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.covid19tracker.databinding.StatisticItemBinding;
import com.example.covid19tracker.model.CountryDataModel;
import com.example.covid19tracker.utils.Utils;
import com.google.android.material.card.MaterialCardView;

import java.util.List;
import java.util.Locale;

public class CountryDataAdapter extends RecyclerView.Adapter<CountryDataAdapter.CountryDataViewHolder> {

    private Context context;
    private List<CountryDataModel> countryDataModelList;
    private int groupId;

    public CountryDataAdapter(Context context, List<CountryDataModel> countryDataModelList, int groupId)
    {
        this.context = context;
        this.countryDataModelList = countryDataModelList;
        this.groupId = groupId;
    }

    @NonNull
    @Override
    public CountryDataAdapter.CountryDataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        /* Android 3.6.* provides view binding which removes the need to use
         * the findViewById()
         * method to instantiate view objects */
//        CountryDataItemBinding itemBinding = CountryDataItemBinding
//                .inflate(LayoutInflater.from(parent.getContext()), parent, false);
//        return new CountryDataViewHolder(itemBinding);
        StatisticItemBinding binding = StatisticItemBinding
                .inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new CountryDataViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CountryDataAdapter.CountryDataViewHolder holder, int position)
    {
//        CountryDataModel countryDataModel = countryDataModelList.get(position);

//        holder.itemBinding.countryName.setText(countryDataModel.getCountryDataName());
//        holder.itemBinding.confirmedCases.setText(countryDataModel.getCountryDataTotalConfirmedCases());
//        holder.itemBinding.deaths.setText(countryDataModel.getCountryDataTotalDeaths());
//        holder.itemBinding.recoveries.setText(countryDataModel.getCountryDataTotalRecoveries());
//        holder.itemBinding.reportDate.setText(countryDataModel.getCountryDataReportDate());

        CountryDataModel model = countryDataModelList.get(position);
        holder.binding.cardTitle.setText(model.getCountry());
        MaterialCardView materialCardView  = holder.binding.statisticItem;

        switch (groupId)
        {
            case Utils.CONFIRMED_GROUP_ID:
                materialCardView.setCardBackgroundColor(Color.parseColor("#ff0505"));
//                holder.binding.cardTitle.setBackgroundColor(Color.parseColor("#FF5252"));
                holder.binding.cardDescriptionOne.setText(
                        String.format(Locale.ENGLISH, Utils.CONFIRMED_FORMAT,
                                model.getTotalConfirmed()));
//                holder.binding.cardDescriptionOne.setBackgroundColor(Color.parseColor("#FF5252"));

                holder.binding.cardDescriptionTwo.setText(
                        String.format(Locale.ENGLISH, Utils.RECOVERED_FORMAT,
                                model.getTotalRecovered(), model.getRecoveryRate())
                );
//                holder.binding.cardDescriptionTwo.setBackgroundColor(Color.parseColor("#FF3838"));

                holder.binding.cardDescriptionThree.setText(
                        String.format(Locale.ENGLISH, Utils.DEATHS_FORMAT, model.getTotalDeaths(),
                                model.getDeathRate())
                );
//                holder.binding.cardDescriptionThree.setBackgroundColor(Color.parseColor("#ff0505"));
                break;

            case Utils.RECOVERED_GROUP_ID:
                materialCardView.setCardBackgroundColor(Color.parseColor("#03045E"));
//                holder.binding.cardTitle.setBackgroundColor(Color.parseColor("#90E0EF"));
                holder.binding.cardDescriptionOne.setText(
                        String.format(Locale.ENGLISH, Utils.RECOVERED_FORMAT,
                                model.getTotalRecovered(), model.getRecoveryRate())
                );
//                holder.binding.cardDescriptionOne.setBackgroundColor(Color.parseColor("#90E0EF"));

                holder.binding.cardDescriptionTwo.setText(
                        String.format(Locale.ENGLISH, Utils.DEATHS_FORMAT,
                                model.getTotalDeaths(), model.getDeathRate())
                );
//                holder.binding.cardDescriptionTwo.setBackgroundColor(Color.parseColor("#00B4D8"));

                holder.binding.cardDescriptionThree.setText(
                        String.format(Locale.ENGLISH, Utils.CONFIRMED_FORMAT,
                                model.getTotalConfirmed())
                );
//                holder.binding.cardDescriptionThree.setBackgroundColor(Color.parseColor("#03045E"));
                break;

            case Utils.DEATHS_GROUP_ID:
                materialCardView.setCardBackgroundColor(Color.parseColor("#540804"));
//                holder.binding.cardTitle.setBackgroundColor(Color.parseColor("#EA8C55"));
                holder.binding.cardDescriptionOne.setText(
                        String.format(Locale.ENGLISH, Utils.DEATHS_FORMAT,
                                model.getTotalDeaths(), model.getDeathRate())
                );
//                holder.binding.cardDescriptionOne.setBackgroundColor(Color.parseColor("#EA8C55"));

                holder.binding.cardDescriptionTwo.setText(
                        String.format(Locale.ENGLISH, Utils.RECOVERED_FORMAT,
                                model.getTotalRecovered(), model.getRecoveryRate())
                );
//                holder.binding.cardDescriptionTwo.setBackgroundColor(Color.parseColor("#AD2E24"));

                holder.binding.cardDescriptionThree.setText(
                        String.format(Locale.ENGLISH, Utils.CONFIRMED_FORMAT,
                                model.getTotalConfirmed())
                );
//                holder.binding.cardDescriptionThree.setBackgroundColor(Color.parseColor("#540804"));
                break;

            default:
                break;
        }
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

//        private CountryDataItemBinding itemBinding;
//
//        CountryDataViewHolder(@NonNull CountryDataItemBinding itemBinding) {
//            super(itemBinding.getRoot());
//            this.itemBinding = itemBinding;
//        }

        private StatisticItemBinding binding;

        CountryDataViewHolder(StatisticItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
