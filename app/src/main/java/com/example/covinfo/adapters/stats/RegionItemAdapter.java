package com.example.covinfo.adapters.stats;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.covinfo.R;
import com.example.covinfo.classes.stats.RegionInfo;

import java.util.ArrayList;
import java.util.Locale;

public class RegionItemAdapter extends RecyclerView.Adapter<RegionItemAdapter.ViewHolder> {

    public interface RegionItemAdapterInterface {
        void onItemClickListener(String regionCode, String regionName);
    }

    private final ArrayList<RegionInfo> regionStatsList;
    private final RegionItemAdapterInterface itemAdapterInterface;

    public RegionItemAdapter(ArrayList<RegionInfo> regionStatsList, RegionItemAdapterInterface itemAdapterInterface) {
        this.regionStatsList = regionStatsList;
        this.itemAdapterInterface = itemAdapterInterface;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvCode, tvName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvCode = itemView.findViewById(R.id.tvRegionItemCode);
            tvName = itemView.findViewById(R.id.tvRegionItemName);
        }
    }

    @NonNull
    @Override
    public RegionItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.region_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RegionItemAdapter.ViewHolder holder, int position) {
        RegionInfo info = regionStatsList.get(position);
        holder.tvCode.setText(info.getCode());
        holder.tvName.setText(String.format(Locale.ENGLISH, "(%s)", info.getName()));
        holder.itemView.setOnClickListener(v -> itemAdapterInterface.onItemClickListener(info.getCode(), info.getName()));
    }

    @Override
    public int getItemCount() {
        return regionStatsList.size();
    }
}
