package experiments.waweruu.c19tn.ui.metrics;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;

import java.util.List;
import java.util.Locale;

import experiments.waweruu.c19tn.databinding.ItemStatisticBinding;
import experiments.waweruu.c19tn.db.entity.CountryDataEntity;
import experiments.waweruu.c19tn.util.Util;

public class MetricsAdapter extends RecyclerView.Adapter<MetricsAdapter.MetricsViewHolder> {

    private List<CountryDataEntity> entityList;
    private int groupId;

    public MetricsAdapter(List<CountryDataEntity> entityList, int groupId) {
        this.entityList = entityList;
        this.groupId = groupId;
    }

    @NonNull
    @Override
    public MetricsAdapter.MetricsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        /* Android 3.6.* provides view binding which removes the need to use
         * the findViewById()
         * method to instantiate view objects */
        ItemStatisticBinding binding = ItemStatisticBinding
                .inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new MetricsViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MetricsAdapter.MetricsViewHolder holder, int position) {

        CountryDataEntity entity = entityList.get(position);
        holder.binding.cardTitle.setText(entity.getCountry());
        MaterialCardView materialCardView  = holder.binding.statisticItem;

        switch (groupId)
        {
            case Util.CONFIRMED_GROUP_ID:
                materialCardView.setCardBackgroundColor(Color.parseColor("#ff0505"));
                holder.binding.cardDescriptionOne.setText(
                        String.format(Locale.ENGLISH, Util.CONFIRMED_FORMAT,
                                entity.getTotalConfirmed()));

                holder.binding.cardDescriptionTwo.setText(
                        String.format(Locale.ENGLISH, Util.RECOVERED_FORMAT,
                                entity.getTotalRecovered(), entity.getRecoveryRate())
                );

                holder.binding.cardDescriptionThree.setText(
                        String.format(Locale.ENGLISH, Util.DEATHS_FORMAT, entity.getTotalDeaths(),
                                entity.getDeathRate())
                );
                break;

            case Util.RECOVERED_GROUP_ID:
                materialCardView.setCardBackgroundColor(Color.parseColor("#03045E"));
                holder.binding.cardDescriptionOne.setText(
                        String.format(Locale.ENGLISH, Util.RECOVERED_FORMAT,
                                entity.getTotalRecovered(), entity.getRecoveryRate())
                );

                holder.binding.cardDescriptionTwo.setText(
                        String.format(Locale.ENGLISH, Util.DEATHS_FORMAT,
                                entity.getTotalDeaths(), entity.getDeathRate())
                );

                holder.binding.cardDescriptionThree.setText(
                        String.format(Locale.ENGLISH, Util.CONFIRMED_FORMAT,
                                entity.getTotalConfirmed())
                );
                break;

            case Util.DEATHS_GROUP_ID:
                materialCardView.setCardBackgroundColor(Color.parseColor("#540804"));
                holder.binding.cardDescriptionOne.setText(
                        String.format(Locale.ENGLISH, Util.DEATHS_FORMAT,
                                entity.getTotalDeaths(), entity.getDeathRate())
                );

                holder.binding.cardDescriptionTwo.setText(
                        String.format(Locale.ENGLISH, Util.RECOVERED_FORMAT,
                                entity.getTotalRecovered(), entity.getRecoveryRate())
                );

                holder.binding.cardDescriptionThree.setText(
                        String.format(Locale.ENGLISH, Util.CONFIRMED_FORMAT,
                                entity.getTotalConfirmed())
                );
                break;

            default:
                break;
        }
    }

    @Override
    public int getItemCount() {
        return entityList.size();
    }

    class MetricsViewHolder extends RecyclerView.ViewHolder {

        /* Here you can see why view binding is better than data binding
         * in this case. Instead of declaring the TextViews we have in the view
         * and then initializing them in the constructor, we only declare an
         * instance of our view's binding class that we instantiate
         * and use to access the views within in onBindViewHolder()*/


        private ItemStatisticBinding binding;

        MetricsViewHolder(ItemStatisticBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
