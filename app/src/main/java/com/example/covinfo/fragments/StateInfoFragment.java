package com.example.covinfo.fragments;

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
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.covinfo.R;
import com.example.covinfo.enums.CaseType;
import com.example.covinfo.enums.RegionType;
import com.example.covinfo.interfaces.ActivityFragmentInterface;
import com.example.covinfo.ui.CaseCardView;
import com.example.covinfo.ui.GraphView;
import com.example.covinfo.ui.StatsListView;
import com.example.covinfo.viewmodels.MainViewModel;

import java.util.ArrayList;
import java.util.Arrays;

public class StateInfoFragment extends Fragment implements ActivityFragmentInterface {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_state_info, container, false);
    }

    private MainViewModel mainViewModel;
    private NavController navController;

    private TextView tvStateName;
    private CaseCardView ccvConfirmed, ccvActive, ccvRecovered, ccvDeceased;
    private StatsListView statsListView;
    private GraphView graphView;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViewsAndAttachListeners(view);
        initializeViewModelAndNavController();
    }

    @Override
    public void findViewsAndAttachListeners(View view) {
        tvStateName = view.findViewById(R.id.tvStateInfoName);
        ccvConfirmed = new CaseCardView(requireActivity(), view.findViewById(R.id.stateInfoConfirmedCard), CaseType.TOTAL_CONFIRMED, "Confirmed");
        ccvActive = new CaseCardView(requireActivity(), view.findViewById(R.id.stateInfoActiveCard), CaseType.TOTAL_ACTIVE, "Active");
        ccvRecovered = new CaseCardView(requireActivity(), view.findViewById(R.id.stateInfoRecoveredCard), CaseType.TOTAL_RECOVERED, "Recovered");
        ccvDeceased = new CaseCardView(requireActivity(), view.findViewById(R.id.stateInfoDeceasedCard), CaseType.TOTAL_DECEASED, "Deceased");

        ArrayList<CaseType> caseTypeList = new ArrayList<>(Arrays.asList(CaseType.values()));

        statsListView = new StatsListView((AppCompatActivity) requireActivity(), view.findViewById(R.id.stateInfoDistrictList), caseTypeList, "District-Wise Data", "District", RegionType.DISTRICT,
                (code, name) -> {
                    mainViewModel.setCurrentDistrictName(name);
                    navController.navigate(R.id.action_stateInfoFragment_to_districtInfoFragment);
                }
        );

        graphView = new GraphView((AppCompatActivity) requireActivity(), view.findViewById(R.id.stateInfoGraph), caseTypeList, "Cases over Time");

        getLifecycle().addObserver((LifecycleEventObserver) (source, event) -> {
            if(event == Lifecycle.Event.ON_START)
                graphView.setMenu();
        });
    }

    @Override
    public void initializeViewModelAndNavController() {
        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        navController = Navigation.findNavController(requireView());

        mainViewModel.getCurrentStateName().observe(getViewLifecycleOwner(), name -> tvStateName.setText(name));

        mainViewModel.getCurrentStateStats().observe(getViewLifecycleOwner(), covidStatsData -> {
            ccvConfirmed.SetDetails(covidStatsData.getTotalConfirmed(), covidStatsData.getDailyConfirmed(), true);
            ccvActive.SetDetails(covidStatsData.getTotalActive(), covidStatsData.getDailyActive(), true);
            ccvRecovered.SetDetails(covidStatsData.getTotalRecovered(), covidStatsData.getDailyRecovered(), true);
            ccvDeceased.SetDetails(covidStatsData.getTotalDeceased(), covidStatsData.getDailyDeceased(), true);
        });

        mainViewModel.getCurrentStateTimeSeriesData().observe(getViewLifecycleOwner(), covidStatsData -> graphView.setGraphData(covidStatsData));
        mainViewModel.getDistrictDataList().observe(getViewLifecycleOwner(), covidStatsData -> statsListView.setDetails(covidStatsData, false));
    }
}