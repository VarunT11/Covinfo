package com.geekhaven.covinfo.fragments.india;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.ViewModelProvider;

import com.example.covinfo.R;
import com.geekhaven.covinfo.manager.ActivityViewManager;
import com.geekhaven.covinfo.manager.ActivityApiManager;
import com.geekhaven.covinfo.classes.stats.CovidStats;
import com.geekhaven.covinfo.enums.CaseType;
import com.geekhaven.covinfo.enums.TaskType;
import com.geekhaven.covinfo.views.CaseCardView;
import com.geekhaven.covinfo.views.GraphView;
import com.geekhaven.covinfo.viewmodels.MainViewModel;

import java.util.ArrayList;
import java.util.Arrays;

public class DistrictInfoFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_district_info, container, false);
    }

    private TextView tvStateName, tvDistrictName;
    private CaseCardView ccvConfirmed, ccvActive, ccvRecovered, ccvDeceased;
    private GraphView graphView;

    private MainViewModel mainViewModel;
    private ActivityApiManager apiManager;
    private ActivityViewManager navAndViewManager;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        findViewsAndAttachListeners(view);
        addLifeCycleListeners();
        initializeViewModelAndManagers();
        updateActivityViews();
        addViewModelObservers();
        addApiTasks();
    }

    private void findViewsAndAttachListeners(View view) {
        tvStateName = view.findViewById(R.id.tvDistrictInfoStateName);
        tvDistrictName = view.findViewById(R.id.tvDistrictInfoName);

        ccvConfirmed = new CaseCardView(
                requireActivity(), view.findViewById(R.id.districtInfoConfirmedCard),
                CaseType.TOTAL_CONFIRMED, "Confirmed"
        );
        ccvActive = new CaseCardView(
                requireActivity(), view.findViewById(R.id.districtInfoActiveCard),
                CaseType.TOTAL_ACTIVE, "Active"
        );
        ccvRecovered = new CaseCardView(
                requireActivity(), view.findViewById(R.id.districtInfoRecoveredCard),
                CaseType.TOTAL_RECOVERED, "Recovered"
        );
        ccvDeceased = new CaseCardView(
                requireActivity(), view.findViewById(R.id.districtInfoDeceasedCard),
                CaseType.TOTAL_DECEASED, "Deceased"
        );

        ArrayList<CaseType> caseTypeList = new ArrayList<>(Arrays.asList(CaseType.values()));

        graphView = new GraphView(
                (AppCompatActivity) requireActivity(), view.findViewById(R.id.districtInfoGraph),
                caseTypeList, "Cases over time"
        );
    }

    private void addLifeCycleListeners() {
        getLifecycle().addObserver((LifecycleEventObserver) (source, event) -> {
            if (event == Lifecycle.Event.ON_START)
                graphView.setMenu();
        });
    }

    private void initializeViewModelAndManagers() {
        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);

        AppCompatActivity activity = (AppCompatActivity) requireActivity();

        apiManager = ActivityApiManager.getInstance(activity);
        navAndViewManager = ActivityViewManager.getInstance(activity);
    }

    private void updateActivityViews() {
        navAndViewManager.setHeadingText("District Wise Updates");
        navAndViewManager.updateAppBarVisibility(View.GONE, View.VISIBLE);
    }

    private void addViewModelObservers() {
        mainViewModel
                .getCurrentDistrictName()
                .observe(getViewLifecycleOwner(), name ->
                        tvDistrictName.setText(name)
                );

        mainViewModel
                .getCurrentStateName()
                .observe(getViewLifecycleOwner(), name ->
                        tvStateName.setText(name)
                );

        mainViewModel
                .getCurrentDistrictStats()
                .observe(getViewLifecycleOwner(), covidStatsData -> {

                    CovidStats statsData = covidStatsData;
                    boolean showDaily = true;
                    if (statsData.getDistrictName() == null) {
                        ArrayList<CovidStats> districtDataList = mainViewModel.getDistrictDataList().getValue();
                        if (districtDataList != null) {
                            for (CovidStats districtData : districtDataList) {
                                if (districtData.getDistrictName().equals(mainViewModel.getCurrentDistrictName().getValue())) {
                                    statsData = districtData;
                                    showDaily = false;
                                }
                            }
                        }
                    }

                    ccvConfirmed.SetDetails(
                            statsData.getTotalConfirmed(), statsData.getDailyConfirmed(), showDaily
                    );
                    ccvRecovered.SetDetails(
                            statsData.getTotalRecovered(), statsData.getDailyRecovered(), showDaily
                    );
                    ccvDeceased.SetDetails(
                            statsData.getTotalDeceased(), statsData.getDailyDeceased(), showDaily
                    );
                    ccvActive.SetDetails(
                            statsData.getTotalActive(), statsData.getDailyActive(), showDaily
                    );
                });

        mainViewModel
                .getCurrentDistrictTimeSeriesData()
                .observe(getViewLifecycleOwner(), covidStatsData ->
                        graphView.setGraphData(covidStatsData)
                );
    }

    private void addApiTasks() {
        mainViewModel
                .getCurrentDistrictName()
                .observe(getViewLifecycleOwner(), name -> {
                    String stateCode = mainViewModel.getCurrentStateCode().getValue();
                    apiManager.addApiTask(TaskType.DISTRICT_DATA, stateCode, name);
                    apiManager.addApiTask(TaskType.DISTRICT_DATA_TIME_SERIES, stateCode, name);
                });
    }
}