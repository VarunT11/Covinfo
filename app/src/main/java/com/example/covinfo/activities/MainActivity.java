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
import com.example.covinfo.classes.DistrictStats;
import com.example.covinfo.classes.IndiaStats;
import com.example.covinfo.classes.News;
import com.example.covinfo.classes.StateStats;
import com.example.covinfo.enums.TimeSeriesType;
import com.example.covinfo.interfaces.ActivityFragmentInterface;
import com.example.covinfo.interfaces.ApiManagerInterface;
import com.example.covinfo.viewmodels.MainViewModel;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ActivityFragmentInterface {

    private TextView tvMainHeading;

    private MainViewModel mainViewModel;
    private NavController navController;

    private ActivityApiManager apiManager;

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

        apiManager = new ActivityApiManager(this, apiManagerInterface);

        navController.addOnDestinationChangedListener((navController, navDestination, bundle) -> {
            if (navDestination.getId() == R.id.indiaHomeFragment) {

                tvMainHeading.setText("Covinfo");

                apiManager.getOverallStatsIndia();
                apiManager.getNewsHeadlinesIndia();

            } else if (navDestination.getId() == R.id.newsListFragment) {

                tvMainHeading.setText("News");

            } else if (navDestination.getId() == R.id.indiaInfoFragment) {

                tvMainHeading.setText("Covid Updates");

                apiManager.getTimeSeriesDataIndia(TimeSeriesType.MONTH);
                apiManager.getStatesDataList();

            } else if (navDestination.getId() == R.id.stateInfoFragment) {

                tvMainHeading.setText("Covid Updates");

                String stateCode = mainViewModel.getCurrentStateCode().getValue();

                apiManager.getStateTimeSeriesData(TimeSeriesType.MONTH, stateCode);
                apiManager.getDistrictsDataList(stateCode);

            } else if (navDestination.getId() == R.id.districtInfoFragment) {

                tvMainHeading.setText("Covid Updates");

                String stateCode = mainViewModel.getCurrentStateCode().getValue();
                String districtName = mainViewModel.getCurrentDistrictName().getValue();

                apiManager.getDistrictData(stateCode, districtName);
                apiManager.getDistrictTimeSeriesData(TimeSeriesType.MONTH, stateCode, districtName);

            }
        });
    }

    private void showErrorMessage(String error) {
        Toast.makeText(this, "Error in Loading Data", Toast.LENGTH_SHORT).show();
        Log.d("API Data Fetch Error", error);
    }

    private final ApiManagerInterface apiManagerInterface = new ApiManagerInterface() {
        @Override
        public void onNewsFetchComplete(boolean success, ArrayList<News> newsList, String error) {
            if (success) {
                mainViewModel.setNewsList(newsList);
            } else {
                showErrorMessage(error);
            }
        }

        @Override
        public void onIndiaStatsFetch(boolean success, IndiaStats indiaStats, String error) {
            if (success) {
                mainViewModel.setIndiaOverallStats(indiaStats);
            } else {
                showErrorMessage(error);
            }
        }

        @Override
        public void onIndiaTimeSeriesStatsFetch(boolean success, ArrayList<IndiaStats> indiaTimeSeriesStats, String error) {
            if (success) {
                mainViewModel.setIndiaTimeSeriesData(indiaTimeSeriesStats);
            } else {
                showErrorMessage(error);
            }
        }

        @Override
        public void onStatesDataListFetch(boolean success, ArrayList<StateStats> stateStatsList, String error) {
            if (success) {
                mainViewModel.setStateListData(stateStatsList);
            } else {
                showErrorMessage(error);
            }
        }

        @Override
        public void onStateDataFetch(boolean success, StateStats stateStats, String error) {
            if (success) {
                mainViewModel.setCurrentStateStats(stateStats);
            } else {
                showErrorMessage(error);
            }
        }

        @Override
        public void onStateTimeSeriesDataFetch(boolean success, ArrayList<StateStats> stateTimeSeriesStats, String error) {
            if (success) {
                mainViewModel.setCurrentStateTimeSeriesData(stateTimeSeriesStats);
            } else {
                showErrorMessage(error);
            }
        }

        @Override
        public void onDistrictsListFetch(boolean success, ArrayList<DistrictStats> districtsDataList, String error) {
            if (success) {
                mainViewModel.setDistrictListData(districtsDataList);
            } else {
                showErrorMessage(error);
            }
        }

        @Override
        public void onDistrictDataFetch(boolean success, DistrictStats districtStats, String error) {
            if (success) {
                mainViewModel.setCurrentDistrictStats(districtStats);
            } else {
                showErrorMessage(error);
            }
        }

        @Override
        public void onDistrictTimeSeriesDataFetch(boolean success, ArrayList<DistrictStats> districtTimeSeriesStats, String error) {
            if (success) {
                mainViewModel.setCurrentDistrictTimeSeriesData(districtTimeSeriesStats);
            } else {
                showErrorMessage(error);
            }
        }
    };
}