package com.example.covinfo.fragments;

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

import com.example.covinfo.R;
import com.example.covinfo.enums.CaseType;
import com.example.covinfo.enums.RegionType;
import com.example.covinfo.interfaces.ActivityFragmentInterface;
import com.example.covinfo.ui.GraphView;
import com.example.covinfo.ui.StatsListView;
import com.example.covinfo.viewmodels.MainViewModel;

import java.util.ArrayList;
import java.util.Arrays;

public class IndiaInfoFragment extends Fragment implements ActivityFragmentInterface {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_india_info, container, false);
    }

    private MainViewModel mainViewModel;
    private NavController navController;

    private GraphView graphView;
    private StatsListView stateListView;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViewsAndAttachListeners(view);
        initializeViewModelAndNavController();
    }

    @Override
    public void findViewsAndAttachListeners(View view) {
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

        graphView = new GraphView((AppCompatActivity) requireActivity(), view.findViewById(R.id.indiaInfoGraphIndia), graphCaseTypeList, "Across India");

        getLifecycle().addObserver((LifecycleEventObserver) (source, event) -> {
            if (event == Lifecycle.Event.ON_START) {
                graphView.setMenu();
            }
        });
    }

    @Override
    public void initializeViewModelAndNavController() {
        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        navController = Navigation.findNavController(requireView());

        mainViewModel.getIndiaTimeSeriesData().observe(getViewLifecycleOwner(), covidStatsData -> graphView.setGraphData(covidStatsData));
        mainViewModel.getStateDataList().observe(getViewLifecycleOwner(), covidStatsData -> stateListView.setDetails(covidStatsData, false));
    }
}