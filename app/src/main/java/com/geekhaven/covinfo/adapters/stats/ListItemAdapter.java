package com.geekhaven.covinfo.adapters.stats;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.covinfo.R;
import com.geekhaven.covinfo.classes.stats.CovidStats;
import com.geekhaven.covinfo.enums.RegionType;

import java.util.ArrayList;
import java.util.Locale;

public class ListItemAdapter extends RecyclerView.Adapter<ListItemAdapter.ViewHolder> {

    public interface ListItemClickInterface{
        void onItemClickListener(String code, String name);
    }

    private final boolean showDailyCases, showRecoveredCases, showActiveCases, showConfirmedCases, showDeceasedCases;
    private final ArrayList<CovidStats> statsList;
    private final RegionType regionType;
    private final ListItemClickInterface itemClickInterface;

    public ListItemAdapter(boolean showDailyCases, boolean showConfirmedCases, boolean showDeceasedCases, boolean showRecoveredCases, boolean showActiveCases, ArrayList<CovidStats> statsList, RegionType regionType, ListItemClickInterface itemClickInterface) {
        this.showDailyCases = showDailyCases;
        this.showConfirmedCases = showConfirmedCases;
        this.showDeceasedCases = showDeceasedCases;
        this.showRecoveredCases = showRecoveredCases;
        this.showActiveCases = showActiveCases;
        this.statsList = statsList;
        this.regionType = regionType;
        this.itemClickInterface = itemClickInterface;
    }

    public static class ViewHolder extends NewsListAdapter.ViewHolder {
        TextView tvName, tvBlank, tvBlankDaily, tvConfirmed, tvConfirmedDaily, tvActive, tvActiveDaily, tvRecovered, tvRecoveredDaily, tvDeceased, tvDeceasedDaily;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_item_name);
            tvBlank = itemView.findViewById(R.id.tv_item_blank);
            tvBlankDaily = itemView.findViewById(R.id.tv_item_blank_daily);
            tvConfirmed = itemView.findViewById(R.id.tv_item_confirmed);
            tvConfirmedDaily = itemView.findViewById(R.id.tv_item_confirmed_daily);
            tvActive = itemView.findViewById(R.id.tv_item_active);
            tvActiveDaily = itemView.findViewById(R.id.tv_item_active_daily);
            tvRecovered = itemView.findViewById(R.id.tv_item_recovered);
            tvRecoveredDaily = itemView.findViewById(R.id.tv_item_recovered_daily);
            tvDeceased = itemView.findViewById(R.id.tv_item_deceased);
            tvDeceasedDaily = itemView.findViewById(R.id.tv_item_deceased_daily);
        }
    }

    @NonNull
    @Override
    public ListItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ListItemAdapter.ViewHolder holder, int position) {
        CovidStats stats = statsList.get(position);
        String displayName = "";
        switch (regionType) {
            case STATE:
                displayName = stats.getStateName();
                break;
            case DISTRICT:
                displayName = stats.getDistrictName();
                break;
            case COUNTRY:
                displayName = stats.getCountryName();
                break;
            case WHO_REGION:
                displayName = stats.getRegionNameWHO();
                break;
        }
        holder.tvName.setText(displayName);

        int dailyConfirmed = 0, dailyRecovered = 0, dailyDeceased = 0, dailyActive = 0;

        if (showDailyCases) {
            dailyConfirmed = stats.getDailyConfirmed();
            dailyRecovered = stats.getDailyRecovered();
            dailyDeceased = stats.getDailyDeceased();
            holder.tvBlankDaily.setVisibility(View.VISIBLE);
        } else {
            holder.tvBlankDaily.setVisibility(View.GONE);
        }

        updateTvData(holder.tvConfirmed, holder.tvConfirmedDaily, showConfirmedCases, stats.getTotalConfirmed(), dailyConfirmed);
        updateTvData(holder.tvActive, holder.tvActiveDaily, showActiveCases, stats.getTotalActive(), dailyActive);
        updateTvData(holder.tvRecovered, holder.tvRecoveredDaily, showRecoveredCases, stats.getTotalRecovered(), dailyRecovered);
        updateTvData(holder.tvDeceased, holder.tvDeceasedDaily, showDeceasedCases, stats.getTotalDeceased(), dailyDeceased);

        holder.tvName.setOnClickListener(v -> {
            String name="", code="";
            switch (regionType){
                case STATE:{
                    name = stats.getStateName();
                    code = stats.getStateCode();
                    break;
                }
                case DISTRICT:{
                    name = stats.getDistrictName();
                    break;
                }
            }
            itemClickInterface.onItemClickListener(code, name);
        });
    }

    @Override
    public int getItemCount() {
        return statsList.size();
    }

    private void updateTvData(TextView tvCases, TextView tvDaily, boolean isVisible, int data, int dailyData) {
        if (isVisible) {
            tvCases.setVisibility(View.VISIBLE);
            tvCases.setText(String.format(Locale.ROOT, "%, d", data));
            if (showDailyCases) {
                tvDaily.setVisibility(View.VISIBLE);
                String format = "(+%, d)";
                if(dailyData<0){
                    dailyData = Math.abs(dailyData);
                    format = "(-%, d)";
                }
                tvDaily.setText(String.format(Locale.ROOT, format, dailyData));
            } else {
                tvDaily.setVisibility(View.GONE);
            }
        } else {
            tvCases.setVisibility(View.GONE);
            tvDaily.setVisibility(View.GONE);
        }
    }
}
