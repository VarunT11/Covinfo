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

import com.example.covinfo.R;
import com.example.covinfo.classes.stats.CovidStats;
import com.example.covinfo.enums.CaseType;
import com.example.covinfo.enums.TaskType;
import com.example.covinfo.manager.ActivityApiManager;
import com.example.covinfo.manager.ActivityViewManager;
import com.example.covinfo.views.CaseCardView;
import com.example.covinfo.views.GraphView;
import com.example.covinfo.viewmodels.MainViewModel;

import java.util.ArrayList;
import java.util.Arrays;

public class CountryInfoFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_country_info, container, false);
    }

    private TextView tvCountryInfoName;
    private CaseCardView ccvConfirmed, ccvDeceased;
    private GraphView graphView;

    private MainViewModel mainViewModel;
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
                        CaseType.TOTAL_CONFIRMED,
                        CaseType.DAILY_CONFIRMED,
                        CaseType.TOTAL_DECEASED,
                        CaseType.DAILY_DECEASED
                )
        );

        tvCountryInfoName = view.findViewById(R.id.tvCountryInfoName);

        ccvConfirmed = new CaseCardView(
                requireActivity(), view.findViewById(R.id.caseCardCountryInfoConfirmed),
                CaseType.TOTAL_CONFIRMED, "Confirmed"
        );
        ccvDeceased = new CaseCardView(
                requireActivity(), view.findViewById(R.id.caseCardCountryInfoDeceased),
                CaseType.TOTAL_DECEASED, "Deceased"
        );

        graphView = new GraphView(
                (AppCompatActivity) requireActivity(), view.findViewById(R.id.graphCountryInfo),
                caseTypes, "Cases over Time"
        );
    }

    private void initializeViewModelAndManagers() {
        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);

        AppCompatActivity activity = (AppCompatActivity) requireActivity();

        viewManager = ActivityViewManager.getInstance(activity);
        apiManager = ActivityApiManager.getInstance(activity);
    }

    private void addViewModelObservers() {
        mainViewModel
                .getCurrentCountryName()
                .observe(getViewLifecycleOwner(), countryName ->
                        tvCountryInfoName.setText(countryName)
                );

        mainViewModel
                .getCurrentCountryStats()
                .observe(getViewLifecycleOwner(), covidStatsData -> {
                    ccvConfirmed.SetDetails(
                            covidStatsData.getTotalConfirmed(), covidStatsData.getDailyConfirmed(), true
                    );
                    ccvDeceased.SetDetails(
                            covidStatsData.getTotalDeceased(), covidStatsData.getDailyDeceased(), true
                    );
                });

        mainViewModel
                .getCountryTimeSeriesData()
                .observe(getViewLifecycleOwner(), covidStats ->
                        graphView.setGraphData(covidStats)
                );
    }

    private void updateActivityViews() {
        viewManager.updateAppBarVisibility(View.GONE, View.VISIBLE);
        viewManager.setHeadingText("Country Wise Updates");
    }

    private void addApiTasks() {
        mainViewModel
                .getCurrentCountryCode()
                .observe(getViewLifecycleOwner(), countryCode -> {
                    CovidStats regionStats = mainViewModel.getCurrentCountryStats().getValue();
                    if (regionStats == null || !regionStats.getCountryCode().equals(countryCode)){
                        apiManager.addApiTask(TaskType.COUNTRY_DATA, countryCode, null);
                        apiManager.addApiTask(TaskType.COUNTRY_DATA_TIME_SERIES, countryCode, null);
                    }
                });
    }
}