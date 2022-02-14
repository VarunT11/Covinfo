package com.geekhaven.covinfo.views;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.geekhaven.covinfo.R;
import com.geekhaven.covinfo.adapters.stats.CountryItemAdapter;
import com.geekhaven.covinfo.adapters.stats.ListItemAdapter;
import com.geekhaven.covinfo.classes.stats.CovidStats;
import com.geekhaven.covinfo.enums.CaseType;
import com.geekhaven.covinfo.enums.RegionType;

import java.util.ArrayList;

public class StatsListView {

    private final AppCompatActivity activity;
    private final View rootView;
    private final RegionType regionType;
    private final ArrayList<CaseType> caseTypeList;

    private EditText etNameSearch;
    private RecyclerView rcvList;

    private final String statsTitle, nameLabel;
    private boolean showActive, showConfirmed, showRecovered, showDeceased, showDailyCases;
    private ArrayList<CovidStats> statsList;

    private ListItemAdapter.ListItemClickInterface itemClickInterface;
    private CountryItemAdapter.ItemAdapterInterface countryAdapterInterface;

    private BottomSortSheetView bottomSortSheetView;

    public StatsListView(AppCompatActivity activity, View rootView, ArrayList<CaseType> caseTypeList, String statsTitle, String nameLabel, RegionType regionType, ListItemAdapter.ListItemClickInterface itemClickInterface) {
        this.activity = activity;
        this.rootView = rootView;
        this.caseTypeList = caseTypeList;
        this.statsTitle = statsTitle;
        this.nameLabel = nameLabel;
        this.regionType = regionType;
        this.itemClickInterface = itemClickInterface;
        initializeView();
    }

    public StatsListView(AppCompatActivity activity, View rootView, RegionType regionType, ArrayList<CaseType> caseTypeList, String statsTitle, String nameLabel, CountryItemAdapter.ItemAdapterInterface countryAdapterInterface) {
        this.activity = activity;
        this.rootView = rootView;
        this.regionType = regionType;
        this.caseTypeList = caseTypeList;
        this.statsTitle = statsTitle;
        this.nameLabel = nameLabel;
        this.countryAdapterInterface = countryAdapterInterface;
        initializeView();
    }

    private void initializeView() {
        TextView tvHeading = rootView.findViewById(R.id.tvStatsListTitle);
        TextView tvListNameLabel = rootView.findViewById(R.id.tvStatsListName);
        TextView tvListActiveLabel = rootView.findViewById(R.id.tvStatsListActive);
        TextView tvListConfirmedLabel = rootView.findViewById(R.id.tvStatsListConfirmed);
        TextView tvListRecoveredLabel = rootView.findViewById(R.id.tvStatsListRecovered);
        TextView tvListDeceasedLabel = rootView.findViewById(R.id.tvStatsListDeceased);

        ImageButton btnSearch = rootView.findViewById(R.id.btnNameSearch);
        ImageButton btnSort = rootView.findViewById(R.id.btnDataSort);

        etNameSearch = rootView.findViewById(R.id.etSearchName);
        rcvList = rootView.findViewById(R.id.rcvStatsList);

        showActive = false;
        showConfirmed = false;
        showRecovered = false;
        showDeceased = false;

        for (CaseType caseType : caseTypeList) {
            switch (caseType) {
                case DAILY_CONFIRMED:
                case TOTAL_CONFIRMED:
                    showConfirmed = true;
                    break;
                case TOTAL_RECOVERED:
                case DAILY_RECOVERED:
                    showRecovered = true;
                    break;
                case TOTAL_DECEASED:
                case DAILY_DECEASED:
                    showDeceased = true;
                    break;
                case TOTAL_ACTIVE:
                case DAILY_ACTIVE:
                    showActive = true;
                    break;
            }

            bottomSortSheetView = new BottomSortSheetView("BSS " + regionType.name(), caseTypeList, this::updateListSort);

            if(statsTitle.equals("")){
                tvHeading.setVisibility(View.GONE);
            } else {
                tvHeading.setVisibility(View.VISIBLE);
                tvHeading.setText(statsTitle);
            }

            tvListNameLabel.setText(nameLabel);

            etNameSearch.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    updateListSearch(s.toString());
                }
            });

            btnSearch.setOnClickListener(v -> updateListSearch(etNameSearch.getText().toString()));

            btnSort.setOnClickListener(v -> bottomSortSheetView.show(activity.getSupportFragmentManager(), bottomSortSheetView.getTAG()));

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
            linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
            rcvList.setLayoutManager(linearLayoutManager);
        }

        updateLabelVisibility(tvListActiveLabel, showActive);
        updateLabelVisibility(tvListConfirmedLabel, showConfirmed);
        updateLabelVisibility(tvListRecoveredLabel, showRecovered);
        updateLabelVisibility(tvListDeceasedLabel, showDeceased);

        statsList = new ArrayList<>();
    }

    private void updateLabelVisibility(TextView tvView, boolean isVisible) {
        if (isVisible)
            tvView.setVisibility(View.VISIBLE);
        else
            tvView.setVisibility(View.GONE);
    }

    public void setDetails(ArrayList<CovidStats> statsList, boolean showDailyCases) {
        this.statsList = statsList;
        this.showDailyCases = showDailyCases;
        updateListSort(true, null, false);
    }

    private void updateListSearch(String name) {
        name = name.toLowerCase();
        ArrayList<CovidStats> displayList = new ArrayList<>();
        for (int i = 0; i < statsList.size(); i++) {
            switch (regionType) {
                case STATE: {
                    String stateCode = statsList.get(i).getStateCode().toLowerCase();
                    String stateName = statsList.get(i).getStateName().toLowerCase();
                    if (stateCode.startsWith(name) || stateName.startsWith(name))
                        displayList.add(statsList.get(i));
                    break;
                }
                case DISTRICT: {
                    String districtName = statsList.get(i).getDistrictName().toLowerCase();
                    if (districtName.startsWith(name))
                        displayList.add(statsList.get(i));
                    break;
                }
                case COUNTRY:{
                    String countryCode = statsList.get(i).getCountryCode().toLowerCase();
                    String countryName = statsList.get(i).getCountryName().toLowerCase();
                    if (countryCode.startsWith(name) || countryName.startsWith(name))
                        displayList.add(statsList.get(i));
                    break;
                }
                case WHO_REGION:{
                    String regionCode = statsList.get(i).getRegionCodeWHO().toLowerCase();
                    String regionName = statsList.get(i).getRegionNameWHO().toLowerCase();
                    if (regionCode.startsWith(name) || regionName.startsWith(name))
                        displayList.add(statsList.get(i));
                    break;
                }
            }
        }

        if(regionType == RegionType.COUNTRY){
            CountryItemAdapter itemAdapter = new CountryItemAdapter(displayList, countryAdapterInterface);
            rcvList.setAdapter(itemAdapter);

        } else {
            ListItemAdapter listItemAdapter = new ListItemAdapter(showDailyCases, showConfirmed, showDeceased, showRecovered, showActive, displayList, regionType, itemClickInterface);
            rcvList.setAdapter(listItemAdapter);
        }
    }

    private void updateListSort(boolean byName, CaseType currentCaseType, boolean reverse){
        statsList.sort((o1, o2) -> {
            if(byName){
                String name1 = "", name2="";
                switch (regionType){
                    case DISTRICT:{
                        name1 = o1.getDistrictName();
                        name2 = o2.getDistrictName();
                        break;
                    }
                    case STATE:{
                        name1 = o1.getStateName();
                        name2 = o2.getStateName();
                        break;
                    }
                    case COUNTRY:{
                        name1 = o1.getCountryName();
                        name2 = o2.getCountryName();
                        break;
                    }
                    case WHO_REGION:{
                        name1 = o1.getRegionNameWHO();
                        name2 = o2.getRegionNameWHO();
                        break;
                    }
                }
                if(reverse){
                    return name2.compareTo(name1);
                } else {
                    return name1.compareTo(name2);
                }
            } else {
                int val1 = 0, val2 = 0;
                switch (currentCaseType){
                    case TOTAL_CONFIRMED:{
                        val1 = o1.getTotalConfirmed();
                        val2 = o2.getTotalConfirmed();
                        break;
                    }
                    case TOTAL_RECOVERED:{
                        val1 = o1.getTotalRecovered();
                        val2 = o2.getTotalRecovered();
                        break;
                    }
                    case TOTAL_DECEASED:{
                        val1 = o1.getTotalDeceased();
                        val2 = o2.getTotalDeceased();
                        break;
                    }
                    case TOTAL_ACTIVE:{
                        val1 = o1.getTotalActive();
                        val2 = o2.getTotalActive();
                        break;
                    }
                }
                if(reverse){
                    if(val1>=val2){
                        return -1;
                    } else {
                        return 1;
                    }
                } else {
                    if(val1>=val2){
                        return 1;
                    } else {
                        return -1;
                    }
                }
            }
        });
        updateListSearch(etNameSearch.getText().toString());
    }
}
