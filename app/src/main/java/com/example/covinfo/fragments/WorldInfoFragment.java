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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.covinfo.R;
import com.example.covinfo.adapters.RegionItemAdapter;
import com.example.covinfo.classes.CovidStats;
import com.example.covinfo.enums.CaseType;
import com.example.covinfo.enums.RegionType;
import com.example.covinfo.interfaces.ActivityFragmentInterface;
import com.example.covinfo.ui.StatsListView;
import com.example.covinfo.viewmodels.MainViewModel;

import java.util.ArrayList;
import java.util.Arrays;

public class WorldInfoFragment extends Fragment implements ActivityFragmentInterface {

    private MainViewModel mainViewModel;
    private NavController navController;

    private RecyclerView rcvRegionList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_world_info, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViewsAndAttachListeners(view);
        initializeViewModelAndNavController();
    }

    @Override
    public void findViewsAndAttachListeners(View view) {
        rcvRegionList = view.findViewById(R.id.rcvRegionList);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireActivity());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        rcvRegionList.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void initializeViewModelAndNavController() {
        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        navController = Navigation.findNavController(requireView());

        mainViewModel.getCountryDataList().observe(getViewLifecycleOwner(), covidStats -> {

        });

        mainViewModel.getWhoRegionDataList().observe(getViewLifecycleOwner(), covidStats -> {
            RegionItemAdapter itemAdapter = new RegionItemAdapter(covidStats, (regionCode, regionName) -> {
                mainViewModel.setCurrentWhoRegionCode(regionCode);
                mainViewModel.setCurrentWhoRegionName(regionName);
                navController.navigate(R.id.action_worldInfoFragment_to_regionInfoFragment);
            });
            rcvRegionList.setAdapter(itemAdapter);
        });
    }
}