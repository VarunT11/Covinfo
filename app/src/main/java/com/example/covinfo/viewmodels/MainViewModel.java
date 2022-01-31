package com.example.covinfo.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.covinfo.classes.DistrictStats;
import com.example.covinfo.classes.IndiaStats;
import com.example.covinfo.classes.News;
import com.example.covinfo.classes.StateStats;

import java.time.chrono.MinguoChronology;
import java.util.ArrayList;
import java.util.concurrent.RecursiveTask;

public class MainViewModel extends ViewModel {

    private final MutableLiveData<ArrayList<News>> newsList;
    private final MutableLiveData<News> currentNews;
    private final MutableLiveData<IndiaStats> indiaOverallStats;
    private final MutableLiveData<ArrayList<IndiaStats>> indiaTimeSeriesData;
    private final MutableLiveData<ArrayList<StateStats>> stateListData;
    private final MutableLiveData<String> currentStateCode;
    private final MutableLiveData<StateStats> currentStateStats;
    private final MutableLiveData<ArrayList<StateStats>> currentStateTimeSeriesData;
    private final MutableLiveData<ArrayList<DistrictStats>> districtListData;
    private final MutableLiveData<String> currentDistrictName;
    private final MutableLiveData<DistrictStats> currentDistrictStats;
    private final MutableLiveData<ArrayList<DistrictStats>> currentDistrictTimeSeriesData;

    public MainViewModel() {
        newsList = new MutableLiveData<>();
        currentNews = new MutableLiveData<>();
        indiaOverallStats = new MutableLiveData<>();
        indiaTimeSeriesData = new MutableLiveData<>();
        stateListData = new MutableLiveData<>();
        currentStateCode = new MutableLiveData<>();
        currentStateStats = new MutableLiveData<>();
        currentStateTimeSeriesData = new MutableLiveData<>();
        districtListData = new MutableLiveData<>();
        currentDistrictName = new MutableLiveData<>();
        currentDistrictStats = new MutableLiveData<>();
        currentDistrictTimeSeriesData = new MutableLiveData<>();
    }

    public void setNewsList(ArrayList<News> newsArrayList) {
        newsList.setValue(newsArrayList);
    }

    public LiveData<ArrayList<News>> getNewsList() {
        return newsList;
    }

    public void setCurrentNews(News news) {
        currentNews.setValue(news);
    }

    public LiveData<News> getCurrentNews() {
        return currentNews;
    }

    public void setIndiaOverallStats(IndiaStats indiaStats) {
        indiaOverallStats.setValue(indiaStats);
    }

    public LiveData<IndiaStats> getIndiaStats() {
        return indiaOverallStats;
    }

    public void setIndiaTimeSeriesData(ArrayList<IndiaStats> timeSeriesData) {
        indiaTimeSeriesData.setValue(timeSeriesData);
    }

    public LiveData<ArrayList<IndiaStats>> getIndiaTimeSeriesData() {
        return indiaTimeSeriesData;
    }

    public void setStateListData(ArrayList<StateStats> stateStats) {
        stateListData.setValue(stateStats);
    }

    public LiveData<ArrayList<StateStats>> getStateStatsList() {
        return stateListData;
    }

    public void setCurrentStateCode(String stateCode) {
        currentStateCode.setValue(stateCode);
    }

    public LiveData<String> getCurrentStateCode() {
        return currentStateCode;
    }

    public void setCurrentStateStats(StateStats stateStats) {
        currentStateStats.setValue(stateStats);
    }

    public LiveData<StateStats> getCurrentStateStats() {
        return currentStateStats;
    }

    public void setCurrentStateTimeSeriesData(ArrayList<StateStats> stateTimeSeriesData) {
        currentStateTimeSeriesData.setValue(stateTimeSeriesData);
    }

    public LiveData<ArrayList<StateStats>> getCurrentStateTimeSeriesData() {
        return currentStateTimeSeriesData;
    }

    public void setDistrictListData(ArrayList<DistrictStats> districtStats) {
        districtListData.setValue(districtStats);
    }

    public LiveData<ArrayList<DistrictStats>> getDistrictStatsList() {
        return districtListData;
    }

    public void setCurrentDistrictName(String name) {
        currentDistrictName.setValue(name);
    }

    public LiveData<String> getCurrentDistrictName() {
        return currentDistrictName;
    }

    public void setCurrentDistrictStats(DistrictStats districtStats) {
        currentDistrictStats.setValue(districtStats);
    }

    public LiveData<DistrictStats> getCurrentDistrictStats() {
        return currentDistrictStats;
    }

    public void setCurrentDistrictTimeSeriesData(ArrayList<DistrictStats> districtTimeSeriesData) {
        currentDistrictTimeSeriesData.setValue(districtTimeSeriesData);
    }

    public LiveData<ArrayList<DistrictStats>> getCurrentDistrictTimeSeriesData() {
        return currentDistrictTimeSeriesData;
    }

}
