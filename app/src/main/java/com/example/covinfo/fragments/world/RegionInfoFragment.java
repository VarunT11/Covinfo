package com.example.covinfo.fragments.world;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.covinfo.R;
import com.example.covinfo.classes.stats.CovidStats;
import com.example.covinfo.enums.CaseType;
import com.example.covinfo.enums.RegionType;
import com.example.covinfo.enums.TaskType;
import com.example.covinfo.manager.ActivityApiManager;
import com.example.covinfo.manager.ActivityViewManager;
import com.example.covinfo.views.CaseCardView;
import com.example.covinfo.views.StatsListView;
import com.example.covinfo.viewmodels.MainViewModel;

import java.util.ArrayList;
import java.util.Arrays;

public class RegionInfoFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_region_info, container, false);
    }

    private TextView tvRegionName;
    private CaseCardView ccvConfirmed, ccvDeceased;
    private StatsListView countryListView;

    private MainViewModel mainViewModel;
    private NavController navController;
    private ActivityViewManager viewManager;
    private ActivityApiManager apiManager;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViewsAndAttachListeners(view);
        initializeViewModelAndManagers();
        addViewModelObservers();
        updateActivityViews();
        addApiTasks();
    }

    private void findViewsAndAttachListeners(View view) {
        ArrayList<CaseType> caseTypes = new ArrayList<>(
                Arrays.asList(
                        CaseType.TOTAL_CONFIRMED, CaseType.TOTAL_DECEASED
                )
        );

        tvRegionName = view.findViewById(R.id.tvRegionInfoName);

        ccvConfirmed = new CaseCardView(
                requireActivity(), view.findViewById(R.id.caseCardRegionInfoConfirmed),
                CaseType.TOTAL_CONFIRMED, "Confirmed"
        );
        ccvDeceased = new CaseCardView(
                requireActivity(), view.findViewById(R.id.caseCardRegionInfoDeceased),
                CaseType.TOTAL_DECEASED, "Deceased"
        );

        countryListView = new StatsListView(
                (AppCompatActivity) requireActivity(), view.findViewById(R.id.regionInfoCountryListView),
                RegionType.COUNTRY, caseTypes, "Country-Wise Data", "Country",
                (countryCode, countryName) -> {
                    mainViewModel.setCurrentCountryCode(countryCode);
                    mainViewModel.setCurrentCountryName(countryName);
                    navController.navigate(R.id.action_regionInfoFragment_to_countryInfoFragment);
                }
        );
    }

    private void initializeViewModelAndManagers() {
        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        navController = Navigation.findNavController(requireView());

        AppCompatActivity activity = (AppCompatActivity) requireActivity();

        viewManager = ActivityViewManager.getInstance(activity);
        apiManager = ActivityApiManager.getInstance(activity);
    }

    private void addViewModelObservers() {
        mainViewModel
                .getCurrentWhoRegionName()
                .observe(getViewLifecycleOwner(), regionName ->
                        tvRegionName.setText(regionName)
                );

        mainViewModel
                .getCurrentWhoRegionStats()
                .observe(getViewLifecycleOwner(), covidStatsData -> {
                    ccvConfirmed.SetDetails(
                            covidStatsData.getTotalConfirmed(), covidStatsData.getDailyConfirmed(), true
                    );
                    ccvDeceased.SetDetails(
                            covidStatsData.getTotalDeceased(), covidStatsData.getDailyDeceased(), true
                    );
                });

        mainViewModel
                .getWhoRegionCountryDataList()
                .observe(getViewLifecycleOwner(), covidStats ->
                        countryListView.setDetails(covidStats, true)
                );
    }

    private void updateActivityViews() {
        viewManager.updateAppBarVisibility(View.GONE, View.VISIBLE);
        viewManager.setHeadingText("Region Wise Updates");
    }

    private void addApiTasks() {
        mainViewModel
                .getCurrentWhoRegionCode()
                .observe(getViewLifecycleOwner(), regionCode -> {
                    CovidStats regionStats = mainViewModel.getCurrentWhoRegionStats().getValue();
                    if (regionStats == null || !regionStats.getRegionCodeWHO().equals(regionCode)){
                        apiManager.addApiTask(TaskType.REGION_DATA, regionCode, null);
                        apiManager.addApiTask(TaskType.REGION_COUNTRY_DATA_LIST, regionCode, null);
                    }
                });
    }
}