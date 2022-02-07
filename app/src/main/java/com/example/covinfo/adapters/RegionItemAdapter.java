package com.example.covinfo.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.covinfo.R;
import com.example.covinfo.classes.CovidStats;

import java.util.ArrayList;
import java.util.Locale;

public class RegionItemAdapter extends RecyclerView.Adapter<RegionItemAdapter.ViewHolder> {

    public interface RegionItemAdapterInterface{
        void onItemClickListener(String regionCode, String regionName);
    }

    private final ArrayList<CovidStats> regionStatsList;
    private final RegionItemAdapterInterface itemAdapterInterface;

    public RegionItemAdapter(ArrayList<CovidStats> regionStatsList, RegionItemAdapterInterface itemAdapterInterface) {
        this.regionStatsList = regionStatsList;
        this.itemAdapterInterface = itemAdapterInterface;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvCode, tvName, tvTotalCases, tvDailyCases;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvCode = itemView.findViewById(R.id.tvRegionItemCode);
            tvName = itemView.findViewById(R.id.tvRegionItemName);
            tvTotalCases = itemView.findViewById(R.id.tvRegionItemTotalCases);
            tvDailyCases = itemView.findViewById(R.id.tvRegionItemDailyCases);
        }
    }

    @NonNull
    @Override
    public RegionItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.region_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RegionItemAdapter.ViewHolder holder, int position) {
        CovidStats stats = regionStatsList.get(position);
        holder.tvCode.setText(stats.getRegionCodeWHO());
        holder.tvName.setText(stats.getRegionNameWHO());
        holder.tvTotalCases.setText(String.format(Locale.ENGLISH, "%, d", stats.getTotalConfirmed()));
        holder.tvDailyCases.setText(String.format(Locale.ENGLISH, "(+%, d)", stats.getDailyConfirmed()));
        holder.itemView.setOnClickListener(v -> itemAdapterInterface.onItemClickListener(stats.getRegionCodeWHO(), stats.getRegionNameWHO()));
    }

    @Override
    public int getItemCount() {
        return regionStatsList.size();
    }
}
