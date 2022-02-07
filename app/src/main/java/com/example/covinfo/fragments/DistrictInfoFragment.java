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

import com.example.covinfo.R;
import com.example.covinfo.enums.CaseType;
import com.example.covinfo.interfaces.ActivityFragmentInterface;
import com.example.covinfo.ui.CaseCardView;
import com.example.covinfo.ui.GraphView;
import com.example.covinfo.viewmodels.MainViewModel;

import java.util.ArrayList;
import java.util.Arrays;

public class DistrictInfoFragment extends Fragment implements ActivityFragmentInterface {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_district_info, container, false);
    }

    private TextView tvStateName, tvDistrictName;
    private CaseCardView ccvConfirmed, ccvActive, ccvRecovered, ccvDeceased;
    private GraphView graphView;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViewsAndAttachListeners(view);
        initializeViewModelAndNavController();
    }

    @Override
    public void findViewsAndAttachListeners(View view) {
        tvStateName = view.findViewById(R.id.tvDistrictInfoStateName);
        tvDistrictName = view.findViewById(R.id.tvDistrictInfoName);

        ccvConfirmed = new CaseCardView(requireActivity(), view.findViewById(R.id.districtInfoConfirmedCard), CaseType.TOTAL_CONFIRMED, "Confirmed");
        ccvActive = new CaseCardView(requireActivity(), view.findViewById(R.id.districtInfoActiveCard), CaseType.TOTAL_ACTIVE, "Active");
        ccvRecovered = new CaseCardView(requireActivity(), view.findViewById(R.id.districtInfoRecoveredCard), CaseType.TOTAL_RECOVERED, "Recovered");
        ccvDeceased = new CaseCardView(requireActivity(), view.findViewById(R.id.districtInfoDeceasedCard), CaseType.TOTAL_DECEASED, "Deceased");

        ArrayList<CaseType> caseTypeList = new ArrayList<>(Arrays.asList(CaseType.values()));
        graphView = new GraphView((AppCompatActivity) requireActivity(), view.findViewById(R.id.districtInfoGraph), caseTypeList, "Cases over time");

        getLifecycle().addObserver((LifecycleEventObserver) (source, event) -> {
            if(event == Lifecycle.Event.ON_START)
                graphView.setMenu();
        });
    }

    @Override
    public void initializeViewModelAndNavController() {
        MainViewModel mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);

        mainViewModel.getCurrentDistrictName().observe(getViewLifecycleOwner(), name -> tvDistrictName.setText(name));
        mainViewModel.getCurrentStateName().observe(getViewLifecycleOwner(), name -> tvStateName.setText(name));

        mainViewModel.getCurrentDistrictStats().observe(getViewLifecycleOwner(), covidStatsData -> {
            ccvConfirmed.SetDetails(covidStatsData.getTotalConfirmed(), covidStatsData.getDailyConfirmed(), true);
            ccvRecovered.SetDetails(covidStatsData.getTotalRecovered(), covidStatsData.getDailyRecovered(), true);
            ccvDeceased.SetDetails(covidStatsData.getTotalDeceased(), covidStatsData.getDailyDeceased(), true);
            ccvActive.SetDetails(covidStatsData.getTotalActive(), covidStatsData.getDailyActive(), true);
        });

        mainViewModel.getCurrentDistrictTimeSeriesData().observe(getViewLifecycleOwner(), covidStatsData -> graphView.setGraphData(covidStatsData));
    }
}