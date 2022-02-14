package com.geekhaven.covinfo.adapters.stats;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.covinfo.R;
import com.geekhaven.covinfo.classes.stats.CovidStats;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.Locale;

public class CountryItemAdapter extends RecyclerView.Adapter<CountryItemAdapter.ViewHolder> {

    public interface ItemAdapterInterface {
        void onItemClickListener(String countryCode, String countryName);
    }

    private final ArrayList<CovidStats> statsList;
    private final ItemAdapterInterface adapterInterface;

    public CountryItemAdapter(ArrayList<CovidStats> statsList, ItemAdapterInterface adapterInterface) {
        this.statsList = statsList;
        this.adapterInterface = adapterInterface;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        MaterialCardView mcvItem;
        TextView tvName, tvTotalConfirmed, tvTotalDeceased, tvDailyConfirmed, tvDailyDeceased;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mcvItem = itemView.findViewById(R.id.mcvCountryListItem);
            tvName = itemView.findViewById(R.id.tvCountryListName);
            tvTotalConfirmed = itemView.findViewById(R.id.tvCountryListTotalConfirmed);
            tvTotalDeceased = itemView.findViewById(R.id.tvCountryListTotalDeceased);
            tvDailyConfirmed = itemView.findViewById(R.id.tvCountryListDailyConfirmed);
            tvDailyDeceased = itemView.findViewById(R.id.tvCountryListDailyDeceased);
        }
    }

    @NonNull
    @Override
    public CountryItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.country_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CountryItemAdapter.ViewHolder holder, int position) {
        CovidStats stats = statsList.get(position);

        holder.tvName.setText(stats.getCountryName());
        holder.tvTotalConfirmed.setText(String.format(Locale.ENGLISH, "%, d", stats.getTotalConfirmed()));
        holder.tvTotalDeceased.setText(String.format(Locale.ENGLISH, "%, d", stats.getTotalDeceased()));
        holder.tvDailyConfirmed.setText(String.format(Locale.ENGLISH, "(+%, d)", stats.getDailyConfirmed()));
        holder.tvDailyDeceased.setText(String.format(Locale.ENGLISH, "(+%, d)", stats.getDailyDeceased()));

        if (position % 2 == 0) {
            holder.mcvItem.setCardBackgroundColor(holder.itemView.getContext().getColor(R.color.color_primary_light));
        } else {
            holder.mcvItem.setCardBackgroundColor(holder.itemView.getContext().getColor(R.color.white));
        }

        holder.itemView.setOnClickListener(v -> adapterInterface.onItemClickListener(stats.getCountryCode(), stats.getCountryName()));
    }

    @Override
    public int getItemCount() {
        return statsList.size();
    }
}
