package com.example.covinfo.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.covinfo.classes.AboutApp;
import com.example.covinfo.classes.CovidStats;
import com.example.covinfo.classes.Developer;
import com.example.covinfo.classes.News;
import com.example.covinfo.classes.RegionInfo;
import com.example.covinfo.enums.RegionType;

import java.util.ArrayList;

public class MainViewModel extends ViewModel {

    private final MutableLiveData<ArrayList<Developer>> developerList;
    private final MutableLiveData<AboutApp> aboutApp;

    private final MutableLiveData<ArrayList<News>> newsList;
    private final MutableLiveData<News> currentNews;

    private final MutableLiveData<RegionType> currentRegionType;

    private final MutableLiveData<String>
            currentWhoRegionCode,
            currentWhoRegionName,
            currentCountryCode,
            currentCountryName,
            currentStateCode,
            currentStateName,
            currentDistrictName;

    private final MutableLiveData<ArrayList<RegionInfo>> regionInfoList;

    private final MutableLiveData<CovidStats>
            globalOverallStats,
            currentWhoRegionStats,
            currentCountryStats,
            indiaOverallStats,
            currentStateStats,
            currentDistrictStats;

    private final MutableLiveData<ArrayList<CovidStats>>
            whoRegionDataList,
            whoRegionCountryDataList,
            countryDataList,
            countryTimeSeriesData,
            indiaTimeSeriesData,
            stateDataList,
            currentStateTimeSeriesData,
            districtDataList,
            currentDistrictTimeSeriesData;

    public MainViewModel() {
        developerList = new MutableLiveData<>();
        aboutApp = new MutableLiveData<>();

        newsList = new MutableLiveData<>();
        currentNews = new MutableLiveData<>();

        currentRegionType = new MutableLiveData<>();

        currentWhoRegionCode = new MutableLiveData<>();
        currentWhoRegionName = new MutableLiveData<>();
        currentCountryCode = new MutableLiveData<>();
        currentCountryName = new MutableLiveData<>();
        currentStateCode = new MutableLiveData<>();
        currentStateName = new MutableLiveData<>();
        currentDistrictName = new MutableLiveData<>();

        regionInfoList = new MutableLiveData<>();

        globalOverallStats = new MutableLiveData<>();
        currentWhoRegionStats = new MutableLiveData<>();
        currentCountryStats = new MutableLiveData<>();
        indiaOverallStats = new MutableLiveData<>();
        currentStateStats = new MutableLiveData<>();
        currentDistrictStats = new MutableLiveData<>();

        whoRegionDataList = new MutableLiveData<>();
        whoRegionCountryDataList = new MutableLiveData<>();
        countryDataList = new MutableLiveData<>();
        countryTimeSeriesData = new MutableLiveData<>();
        indiaTimeSeriesData = new MutableLiveData<>();
        stateDataList = new MutableLiveData<>();
        currentStateTimeSeriesData = new MutableLiveData<>();
        districtDataList = new MutableLiveData<>();
        currentDistrictTimeSeriesData = new MutableLiveData<>();
    }

    public void setDeveloperList(ArrayList<Developer> developers){
        developerList.setValue(developers);
    }

    public LiveData<ArrayList<Developer>> getDeveloperList(){
        return developerList;
    }

    public void setAboutApp(AboutApp about){
        aboutApp.setValue(about);
    }

    public LiveData<AboutApp> getAboutApp(){
        return aboutApp;
    }

    public void setNewsList(ArrayList<News> newsArrayList) {
        newsList.setValue(newsArrayList);
    }

    public LiveData<ArrayList<News>> getNewsList() {
        return newsList;
    }

    public void setCurrentNews(int position) {
        ArrayList<News> list = newsList.getValue();
        if (list != null && list.size() > position)
            setCurrentNews(newsList.getValue().get(position));
    }

    public void setCurrentNews(News news) {
        currentNews.setValue(news);
    }

    public LiveData<News> getCurrentNews() {
        return currentNews;
    }

    public void setCurrentRegionType(RegionType regionType) {
        currentRegionType.setValue(regionType);
    }

    public LiveData<RegionType> getCurrentRegionType() {
        return currentRegionType;
    }

    public void setCurrentWhoRegionCode(String regionCode) {
        currentWhoRegionCode.setValue(regionCode);
    }

    public LiveData<String> getCurrentWhoRegionCode() {
        return currentWhoRegionCode;
    }

    public void setCurrentWhoRegionName(String regionName) {
        currentWhoRegionName.setValue(regionName);
    }

    public LiveData<String> getCurrentWhoRegionName() {
        return currentWhoRegionName;
    }

    public void setCurrentCountryCode(String countryCode) {
        currentCountryCode.setValue(countryCode);
    }

    public LiveData<String> getCurrentCountryCode() {
        return currentCountryCode;
    }

    public void setCurrentCountryName(String countryName) {
        currentCountryName.setValue(countryName);
    }

    public LiveData<String> getCurrentCountryName() {
        return currentCountryName;
    }

    public void setCurrentStateCode(String stateCode) {
        currentStateCode.setValue(stateCode);
    }

    public LiveData<String> getCurrentStateCode() {
        return currentStateCode;
    }

    public void setCurrentStateName(String stateName) {
        currentStateName.setValue(stateName);
    }

    public LiveData<String> getCurrentStateName() {
        return currentStateName;
    }

    public void setCurrentDistrictName(String name) {
        currentDistrictName.setValue(name);
    }

    public LiveData<String> getCurrentDistrictName() {
        return currentDistrictName;
    }

    public void setRegionInfoList(ArrayList<RegionInfo> infoList) {
        regionInfoList.setValue(infoList);
    }

    public LiveData<ArrayList<RegionInfo>> getRegionInfoList() {
        return regionInfoList;
    }

    public void setGlobalOverallStats(CovidStats overallStats) {
        globalOverallStats.setValue(overallStats);
    }

    public LiveData<CovidStats> getGlobalOverallStats() {
        return globalOverallStats;
    }

    public void setCurrentWhoRegionStats(CovidStats regionStats) {
        currentWhoRegionStats.setValue(regionStats);
    }

    public LiveData<CovidStats> getCurrentWhoRegionStats() {
        return currentWhoRegionStats;
    }

    public void setCurrentCountryStats(CovidStats countryStats) {
        currentCountryStats.setValue(countryStats);
    }

    public LiveData<CovidStats> getCurrentCountryStats() {
        return currentCountryStats;
    }

    public void setIndiaOverallStats(CovidStats overallStats) {
        indiaOverallStats.setValue(overallStats);
    }

    public LiveData<CovidStats> getIndiaOverallStats() {
        return indiaOverallStats;
    }

    public void setCurrentStateStats(CovidStats stateStats) {
        currentStateStats.setValue(stateStats);
    }

    public LiveData<CovidStats> getCurrentStateStats() {
        return currentStateStats;
    }

    public void setCurrentDistrictStats(CovidStats districtStats) {
        currentDistrictStats.setValue(districtStats);
    }

    public LiveData<CovidStats> getCurrentDistrictStats() {
        return currentDistrictStats;
    }

    public void setWhoRegionDataList(ArrayList<CovidStats> regionDataList) {
        whoRegionDataList.setValue(regionDataList);
    }

    public void setWhoRegionCountryDataList(ArrayList<CovidStats> countryDataList) {
        whoRegionCountryDataList.setValue(countryDataList);
    }

    public LiveData<ArrayList<CovidStats>> getWhoRegionCountryDataList() {
        return whoRegionCountryDataList;
    }

    public void setCountryDataList(ArrayList<CovidStats> countryList) {
        countryDataList.setValue(countryList);
    }

    public LiveData<ArrayList<CovidStats>> getCountryDataList() {
        return countryDataList;
    }

    public void setCountryTimeSeriesData(ArrayList<CovidStats> timeSeriesData) {
        countryTimeSeriesData.setValue(timeSeriesData);
    }

    public LiveData<ArrayList<CovidStats>> getCountryTimeSeriesData() {
        return countryTimeSeriesData;
    }

    public void setIndiaTimeSeriesData(ArrayList<CovidStats> timeSeriesData) {
        indiaTimeSeriesData.setValue(timeSeriesData);
    }

    public LiveData<ArrayList<CovidStats>> getIndiaTimeSeriesData() {
        return indiaTimeSeriesData;
    }

    public void setStateDataList(ArrayList<CovidStats> dataList) {
        stateDataList.setValue(dataList);
    }

    public LiveData<ArrayList<CovidStats>> getStateDataList() {
        return stateDataList;
    }

    public void setCurrentStateTimeSeriesData(ArrayList<CovidStats> timeSeriesData) {
        currentStateTimeSeriesData.setValue(timeSeriesData);
    }

    public LiveData<ArrayList<CovidStats>> getCurrentStateTimeSeriesData() {
        return currentStateTimeSeriesData;
    }

    public void setDistrictDataList(ArrayList<CovidStats> districtList) {
        districtDataList.setValue(districtList);
    }

    public LiveData<ArrayList<CovidStats>> getDistrictDataList() {
        return districtDataList;
    }

    public void setCurrentDistrictTimeSeriesData(ArrayList<CovidStats> timeSeriesData) {
        currentDistrictTimeSeriesData.setValue(timeSeriesData);
    }

    public LiveData<ArrayList<CovidStats>> getCurrentDistrictTimeSeriesData() {
        return currentDistrictTimeSeriesData;
    }
}
