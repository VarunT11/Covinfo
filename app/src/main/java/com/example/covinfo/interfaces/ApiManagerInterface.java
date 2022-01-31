package com.example.covinfo.interfaces;

import com.example.covinfo.classes.DistrictStats;
import com.example.covinfo.classes.IndiaStats;
import com.example.covinfo.classes.News;
import com.example.covinfo.classes.StateStats;

import java.util.ArrayList;

public interface ApiManagerInterface {

    void onNewsFetchComplete(boolean success, ArrayList<News> newsList, String error);

    void onIndiaStatsFetch(boolean success, IndiaStats indiaStats, String error);

    void onIndiaTimeSeriesStatsFetch(boolean success, ArrayList<IndiaStats> indiaTimeSeriesStats, String error);

    void onStatesDataListFetch(boolean success, ArrayList<StateStats> stateStatsList, String error);

    void onStateDataFetch(boolean success, StateStats stateStats, String error);

    void onStateTimeSeriesDataFetch(boolean success, ArrayList<StateStats> stateTimeSeriesStats, String error);

    void onDistrictsListFetch(boolean success, ArrayList<DistrictStats> districtsDataList, String error);

    void onDistrictDataFetch(boolean success, DistrictStats districtStats, String error);

    void onDistrictTimeSeriesDataFetch(boolean success, ArrayList<DistrictStats> districtTimeSeriesStats, String error);

}
