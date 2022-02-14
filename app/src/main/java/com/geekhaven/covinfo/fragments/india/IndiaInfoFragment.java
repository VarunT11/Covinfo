package com.geekhaven.covinfo.fragments.india;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.geekhaven.covinfo.R;
import com.geekhaven.covinfo.manager.ActivityViewManager;
import com.geekhaven.covinfo.manager.ActivityApiManager;
import com.geekhaven.covinfo.enums.CaseType;
import com.geekhaven.covinfo.enums.RegionType;
import com.geekhaven.covinfo.enums.TaskType;
import com.geekhaven.covinfo.views.GraphView;
import com.geekhaven.covinfo.views.StatsListView;
import com.geekhaven.covinfo.viewmodels.MainViewModel;

import java.util.ArrayList;
import java.util.Arrays;

public class IndiaInfoFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_india_info, container, false);
    }

    private GraphView graphView;
    private StatsListView stateListView;

    private MainViewModel mainViewModel;
    private NavController navController;
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
        ArrayList<CaseType> stateCaseTypeList = new ArrayList<>(Arrays.asList(
                CaseType.TOTAL_ACTIVE,
                CaseType.TOTAL_CONFIRMED,
                CaseType.TOTAL_RECOVERED,
                CaseType.TOTAL_DECEASED)
        );

        ArrayList<CaseType> graphCaseTypeList = new ArrayList<>(Arrays.asList(CaseType.values()));

        stateListView = new StatsListView(
                (AppCompatActivity) requireActivity(),
                view.findViewById(R.id.indiaInfoStateList),
                stateCaseTypeList, "State-Wise Data",
                "State/UT",
                RegionType.STATE,
                (code, name) -> {
                    mainViewModel.setCurrentStateCode(code);
                    mainViewModel.setCurrentStateName(name);
                    navController.navigate(R.id.action_indiaInfoFragment_to_stateInfoFragment);
                }
        );

        graphView = new GraphView(
                (AppCompatActivity) requireActivity(), view.findViewById(R.id.indiaInfoGraphIndia),
                graphCaseTypeList, "Across India"
        );
    }

    private void addLifeCycleListeners() {
        getLifecycle().addObserver((LifecycleEventObserver) (source, event) -> {
            if (event == Lifecycle.Event.ON_START) {
                graphView.setMenu();
            }
        });
    }

    private void initializeViewModelAndManagers() {
        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        navController = Navigation.findNavController(requireView());

        AppCompatActivity activity = (AppCompatActivity) requireActivity();

        apiManager = ActivityApiManager.getInstance(activity);
        navAndViewManager = ActivityViewManager.getInstance(activity);
    }

    private void updateActivityViews() {
        navAndViewManager.updateAppBarVisibility(View.GONE, View.VISIBLE);
        navAndViewManager.setHeadingText("Nation Wise Updates");
    }

    private void addViewModelObservers() {
        mainViewModel
                .getIndiaTimeSeriesData()
                .observe(getViewLifecycleOwner(), covidStatsData ->
                        graphView.setGraphData(covidStatsData)
                );

        mainViewModel
                .getStateDataList()
                .observe(getViewLifecycleOwner(), covidStatsData ->
                        stateListView.setDetails(covidStatsData, false)
                );
    }

    private void addApiTasks() {
        if (mainViewModel.getIndiaTimeSeriesData().getValue() == null) {
            apiManager.addApiTask(TaskType.INDIA_DATA_TIME_SERIES, null, null);
            apiManager.addApiTask(TaskType.STATE_DATA_LIST, null, null);
        }

    }
}