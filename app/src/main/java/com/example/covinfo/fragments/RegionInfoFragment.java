package com.example.covinfo.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.covinfo.R;
import com.example.covinfo.adapters.CountryItemAdapter;
import com.example.covinfo.enums.CaseType;
import com.example.covinfo.enums.RegionType;
import com.example.covinfo.interfaces.ActivityFragmentInterface;
import com.example.covinfo.ui.CaseCardView;
import com.example.covinfo.ui.StatsListView;
import com.example.covinfo.viewmodels.MainViewModel;

import java.util.ArrayList;
import java.util.Arrays;

public class RegionInfoFragment extends Fragment implements ActivityFragmentInterface {

    private MainViewModel mainViewModel;
    private NavController navController;

    private TextView tvRegionName;
    private CaseCardView ccvConfirmed, ccvDeceased;

    private StatsListView countryListView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_region_info, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViewsAndAttachListeners(view);
        initializeViewModelAndNavController();
    }

    @Override
    public void findViewsAndAttachListeners(View view) {
        tvRegionName = view.findViewById(R.id.tvRegionInfoName);
        ccvConfirmed = new CaseCardView(requireActivity(), view.findViewById(R.id.caseCardRegionInfoConfirmed), CaseType.TOTAL_CONFIRMED, "Confirmed");
        ccvDeceased = new CaseCardView(requireActivity(), view.findViewById(R.id.caseCardRegionInfoDeceased), CaseType.TOTAL_DECEASED, "Deceased");

        ArrayList<CaseType> caseTypes = new ArrayList<>(Arrays.asList(CaseType.TOTAL_CONFIRMED, CaseType.TOTAL_DECEASED));

        countryListView = new StatsListView(
                (AppCompatActivity) requireActivity(),
                view.findViewById(R.id.regionInfoCountryListView),
                RegionType.COUNTRY,
                caseTypes,
                "Country-Wise Data",
                "Country",
                (countryCode, countryName) -> {
                    mainViewModel.setCurrentCountryCode(countryCode);
                    mainViewModel.setCurrentCountryName(countryName);
                    navController.navigate(R.id.action_regionInfoFragment_to_countryInfoFragment);
                }
        );
    }

    @Override
    public void initializeViewModelAndNavController() {
        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        navController = Navigation.findNavController(requireView());

        mainViewModel.getCurrentWhoRegionName().observe(getViewLifecycleOwner(), s -> tvRegionName.setText(s));

        mainViewModel.getCurrentWhoRegionStats().observe(getViewLifecycleOwner(), covidStatsData -> {
            ccvConfirmed.SetDetails(covidStatsData.getTotalConfirmed(), covidStatsData.getDailyConfirmed(), true);
            ccvDeceased.SetDetails(covidStatsData.getTotalDeceased(), covidStatsData.getDailyDeceased(), true);
        });

        mainViewModel.getWhoRegionCountryDataList().observe(getViewLifecycleOwner(), covidStats -> {
            countryListView.setDetails(covidStats, true);
        });
    }
}