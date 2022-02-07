package com.example.covinfo.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.covinfo.R;
import com.example.covinfo.api.ActivityApiManager;
import com.example.covinfo.classes.CovidStats;
import com.example.covinfo.classes.News;
import com.example.covinfo.enums.TaskType;
import com.example.covinfo.interfaces.ActivityFragmentInterface;
import com.example.covinfo.viewmodels.MainViewModel;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ActivityFragmentInterface {

    private TextView tvMainHeading;

    private MainViewModel mainViewModel;
    private NavController navController;
    private ActivityApiManager apiManager;

    private ActivityApiManager.ApiManagerInterface apiManagerInterface = new ActivityApiManager.ApiManagerInterface() {
        @Override
        public void onNewsFetchComplete(TaskType taskType, boolean success, ArrayList<News> newsList, String error) {
            if (success) {
                mainViewModel.setNewsList(newsList);
            } else {
                showErrorMessage(taskType + " " + error);
            }
        }

        @Override
        public void onStatsDataFetchComplete(TaskType taskType, boolean success, ArrayList<CovidStats> covidStatsData, String error) {
            if (success) {
                updateViewModelFromTaskResult(taskType, covidStatsData);
            } else {
                showErrorMessage(taskType + " " + error);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewsAndAttachListeners(null);
        initializeViewModelAndNavController();
    }

    @Override
    public void findViewsAndAttachListeners(View view) {
        tvMainHeading = findViewById(R.id.tvMainTitle);
    }

    @Override
    public void initializeViewModelAndNavController() {
        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        navController = Navigation.findNavController(this, R.id.navHostMain);

        apiManager = ActivityApiManager.initializeApiManager(this, apiManagerInterface);

        navController.addOnDestinationChangedListener((navController, navDestination, bundle) -> {
            if (navDestination.getId() == R.id.indiaHomeFragment) {
                tvMainHeading.setText("Covinfo");

                apiManager.addApiTask(TaskType.INDIA_DATA, null, null, null, null);
                apiManager.addApiTask(TaskType.INDIA_NEWS, null, null, null, null);
            } else if (navDestination.getId() == R.id.newsListFragment) {
                tvMainHeading.setText("News");
            } else if (navDestination.getId() == R.id.indiaInfoFragment) {
                tvMainHeading.setText("Covid Updates");

                apiManager.addApiTask(TaskType.INDIA_DATA_TIME_SERIES, null, null, null, null);
                apiManager.addApiTask(TaskType.STATE_DATA_LIST, null, null, null, null);
            } else if (navDestination.getId() == R.id.stateInfoFragment) {
                tvMainHeading.setText("Covid Updates");

                String stateCode = mainViewModel.getCurrentStateCode().getValue();

                apiManager.addApiTask(TaskType.STATE_DATA, null, null, stateCode, null);
                apiManager.addApiTask(TaskType.STATE_DATA_TIME_SERIES, null, null, stateCode, null);
                apiManager.addApiTask(TaskType.DISTRICT_DATA_LIST, null, null, stateCode, null);
            } else if (navDestination.getId() == R.id.districtInfoFragment) {
                tvMainHeading.setText("Covid Updates");

                String stateCode = mainViewModel.getCurrentStateCode().getValue();
                String districtName = mainViewModel.getCurrentDistrictName().getValue();

                apiManager.addApiTask(TaskType.DISTRICT_DATA, null, null, stateCode, districtName);
                apiManager.addApiTask(TaskType.DISTRICT_DATA_TIME_SERIES, null, null, stateCode, districtName);
            } else if (navDestination.getId() == R.id.worldHomeFragment){
                apiManager.addApiTask(TaskType.GLOBAL_DATA, null, null, null, null);
                apiManager.addApiTask(TaskType.WORLD_NEWS, null, null, null, null);
            } else if (navDestination.getId() == R.id.worldInfoFragment){
                apiManager.addApiTask(TaskType.REGION_DATA_LIST, null, null, null, null);
            } else if (navDestination.getId() == R.id.regionInfoFragment){
                String regionCode = mainViewModel.getCurrentWhoRegionCode().getValue();

                apiManager.addApiTask(TaskType.REGION_DATA, regionCode, null, null, null);
                apiManager.addApiTask(TaskType.REGION_COUNTRY_DATA_LIST, regionCode, null, null, null);
            } else if (navDestination.getId() == R.id.countryInfoFragment){
                String countryCode = mainViewModel.getCurrentCountryCode().getValue();

                apiManager.addApiTask(TaskType.COUNTRY_DATA, null, countryCode, null, null);
                apiManager.addApiTask(TaskType.COUNTRY_DATA_TIME_SERIES, null, countryCode, null, null);
            }
        });
    }

    private void showErrorMessage(String error) {
        if (error != null) {
            Toast.makeText(this, "Error in Loading Data", Toast.LENGTH_SHORT).show();
            Log.d("API Data Fetch Error", error);
        }
    }

    private void updateViewModelFromTaskResult(TaskType taskType, ArrayList<CovidStats> statsData) {
        switch (taskType) {
            case GLOBAL_DATA:
                mainViewModel.setGlobalOverallStats(statsData.get(0));
                break;
            case REGION_DATA_LIST:
                mainViewModel.setWhoRegionDataList(statsData);
                break;
            case REGION_DATA:
                mainViewModel.setCurrentWhoRegionStats(statsData.get(0));
                break;
            case REGION_COUNTRY_DATA_LIST:
                mainViewModel.setWhoRegionCountryDataList(statsData);
                break;
            case COUNTRY_DATA_LIST:
                mainViewModel.setCountryDataList(statsData);
                break;
            case COUNTRY_DATA:
                mainViewModel.setCurrentCountryStats(statsData.get(0));
                break;
            case COUNTRY_DATA_TIME_SERIES:
                mainViewModel.setCountryTimeSeriesData(statsData);
                break;
            case INDIA_DATA:
                mainViewModel.setIndiaOverallStats(statsData.get(0));
                break;
            case INDIA_DATA_TIME_SERIES:
                mainViewModel.setIndiaTimeSeriesData(statsData);
                break;
            case STATE_DATA_LIST:
                mainViewModel.setStateDataList(statsData);
                break;
            case STATE_DATA:
                mainViewModel.setCurrentStateStats(statsData.get(0));
                break;
            case STATE_DATA_TIME_SERIES:
                mainViewModel.setCurrentStateTimeSeriesData(statsData);
                break;
            case DISTRICT_DATA_LIST:
                mainViewModel.setDistrictDataList(statsData);
                break;
            case DISTRICT_DATA:
                mainViewModel.setCurrentDistrictStats(statsData.get(0));
                break;
            case DISTRICT_DATA_TIME_SERIES:
                mainViewModel.setCurrentDistrictTimeSeriesData(statsData);
                break;
        }
    }
}