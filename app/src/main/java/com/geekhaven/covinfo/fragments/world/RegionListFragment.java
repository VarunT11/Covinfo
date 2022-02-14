package com.geekhaven.covinfo.fragments.world;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.geekhaven.covinfo.R;
import com.geekhaven.covinfo.adapters.stats.RegionItemAdapter;
import com.geekhaven.covinfo.viewmodels.MainViewModel;

public class RegionListFragment extends Fragment {

    public RegionListFragment() {
    }

    public static RegionListFragment newInstance() {
        return new RegionListFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_region_list, container, false);
    }

    private RecyclerView rcvRegionInfoList;

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
        rcvRegionInfoList = view.findViewById(R.id.rcvRegionList);
    }

    private void initializeViewModelAndManagers() {
        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        navController = Navigation.findNavController(requireActivity(), R.id.navHostMain);
    }

    private void addViewModelObservers() {
        mainViewModel
                .getRegionInfoList()
                .observe(getViewLifecycleOwner(), regionList -> {
                    LinearLayoutManager layoutManager = new LinearLayoutManager(requireActivity());
                    layoutManager.setOrientation(RecyclerView.VERTICAL);

                    RegionItemAdapter itemAdapter = new RegionItemAdapter(regionList, (regionCode, regionName) -> {
                        mainViewModel.setCurrentWhoRegionName(regionName);
                        mainViewModel.setCurrentWhoRegionCode(regionCode);
                        navController.navigate(R.id.action_worldInfoFragment_to_regionInfoFragment);
                    });

                    rcvRegionInfoList.setLayoutManager(layoutManager);
                    rcvRegionInfoList.setAdapter(itemAdapter);
                });
    }
}