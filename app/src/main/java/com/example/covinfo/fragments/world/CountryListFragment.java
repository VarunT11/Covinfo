package com.example.covinfo.fragments.world;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.covinfo.R;
import com.example.covinfo.enums.CaseType;
import com.example.covinfo.enums.RegionType;
import com.example.covinfo.views.StatsListView;
import com.example.covinfo.viewmodels.MainViewModel;

import java.util.ArrayList;
import java.util.Arrays;

public class CountryListFragment extends Fragment {

    public CountryListFragment() {
    }

    public static CountryListFragment newInstance() {
        return new CountryListFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_country_list, container, false);
    }

    private StatsListView statsListView;

    private MainViewModel mainViewModel;
    private NavController navController;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViewsAndAttachListeners(view);
        initializeViewModelAndManagers();
        addViewModelObservers();
    }

    private void findViewsAndAttachListeners(View view) {
        ArrayList<CaseType> caseTypes = new ArrayList<>(
                Arrays.asList(
                        CaseType.TOTAL_CONFIRMED,
                        CaseType.TOTAL_DECEASED
                )
        );

        statsListView = new StatsListView(
                (AppCompatActivity) requireActivity(), view.findViewById(R.id.countryListLayout),
                RegionType.COUNTRY, caseTypes, "", "Country",
                (countryCode, countryName) -> {
                    mainViewModel.setCurrentCountryCode(countryCode);
                    mainViewModel.setCurrentCountryName(countryName);
                    navController.navigate(R.id.action_worldInfoFragment_to_countryInfoFragment);
                }
        );
    }

    private void initializeViewModelAndManagers() {
        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        navController = Navigation.findNavController(requireActivity(), R.id.navHostMain);
    }

    private void addViewModelObservers() {
        mainViewModel
                .getCountryDataList()
                .observe(getViewLifecycleOwner(), covidStats ->
                        statsListView.setDetails(covidStats, true)
                );
    }
}