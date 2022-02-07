package com.example.covinfo.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.covinfo.R;
import com.example.covinfo.enums.CaseType;
import com.example.covinfo.interfaces.ActivityFragmentInterface;
import com.example.covinfo.ui.CaseCardView;
import com.example.covinfo.ui.GraphView;
import com.example.covinfo.viewmodels.MainViewModel;

import java.util.ArrayList;
import java.util.Arrays;

public class CountryInfoFragment extends Fragment implements ActivityFragmentInterface {

    private MainViewModel mainViewModel;
    private NavController navController;

    private CaseCardView ccvConfirmed, ccvDeceased;
    private GraphView graphView;
    private TextView tvCountryInfoName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_country_info, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViewsAndAttachListeners(view);
        initializeViewModelAndNavController();
    }

    @Override
    public void findViewsAndAttachListeners(View view) {
        tvCountryInfoName = view.findViewById(R.id.tvCountryInfoName);
        ccvConfirmed = new CaseCardView(requireActivity(), view.findViewById(R.id.caseCardCountryInfoConfirmed), CaseType.TOTAL_CONFIRMED, "Confirmed");
        ccvDeceased = new CaseCardView(requireActivity(), view.findViewById(R.id.caseCardCountryInfoDeceased), CaseType.TOTAL_DECEASED, "Deceased");

        ArrayList<CaseType> caseTypes = new ArrayList<>(Arrays.asList(CaseType.TOTAL_CONFIRMED, CaseType.DAILY_CONFIRMED, CaseType.TOTAL_DECEASED, CaseType.DAILY_DECEASED));

        graphView = new GraphView(
                (AppCompatActivity) requireActivity(),
                view.findViewById(R.id.graphCountryInfo),
                caseTypes,
                "Cases over Time"
        );
    }

    @Override
    public void initializeViewModelAndNavController() {
        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        navController = Navigation.findNavController(requireView());

        mainViewModel.getCurrentCountryName().observe(getViewLifecycleOwner(), s -> tvCountryInfoName.setText(s));

        mainViewModel.getCurrentCountryStats().observe(getViewLifecycleOwner(), covidStatsData -> {
            ccvConfirmed.SetDetails(covidStatsData.getTotalConfirmed(), covidStatsData.getDailyConfirmed(), true);
            ccvDeceased.SetDetails(covidStatsData.getTotalDeceased(), covidStatsData.getDailyDeceased(), true);
        });

        mainViewModel.getCountryTimeSeriesData().observe(getViewLifecycleOwner(), covidStats -> {
            graphView.setGraphData(covidStats);
        });
    }
}