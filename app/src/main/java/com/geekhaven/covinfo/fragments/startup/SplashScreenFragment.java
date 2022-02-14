package com.geekhaven.covinfo.fragments.startup;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
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
import com.geekhaven.covinfo.manager.ActivityViewManager;
import com.geekhaven.covinfo.enums.RegionType;
import com.geekhaven.covinfo.viewmodels.MainViewModel;

@SuppressLint("CustomSplashScreen")
public class SplashScreenFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_splash_screen, container, false);
    }

    private MainViewModel mainViewModel;
    private NavController navController;
    private ActivityViewManager navigationAndViewManager;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        navController = Navigation.findNavController(requireView());

        requireActivity().getLifecycle().addObserver((LifecycleEventObserver) (source, event) -> {
            if (event == Lifecycle.Event.ON_CREATE){
                navigationAndViewManager = ActivityViewManager.getInstance((AppCompatActivity) requireActivity());
                navigationAndViewManager.updateAppBarVisibility(View.GONE, View.GONE);
            }
        });

        new Handler().postDelayed(() -> mainViewModel.getCurrentRegionType().observe(getViewLifecycleOwner(), regionType -> {
            if (regionType == RegionType.INDIA) {
                navController.navigate(R.id.action_splashScreenFragment_to_indiaHomeFragment);
            } else {
                navController.navigate(R.id.action_splashScreenFragment_to_worldHomeFragment);
            }
        }), 1500);
    }
}