package com.example.covinfo.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.covinfo.R;
import com.example.covinfo.activities.CovidAdviceActivity;
import com.example.covinfo.adapters.NewsListAdapter;
import com.example.covinfo.classes.News;
import com.example.covinfo.enums.CaseType;
import com.example.covinfo.interfaces.ActivityFragmentInterface;
import com.example.covinfo.ui.CaseCardView;
import com.example.covinfo.viewmodels.MainViewModel;

import java.util.ArrayList;

public class WorldHomeFragment extends Fragment implements ActivityFragmentInterface {

    private MainViewModel mainViewModel;
    private NavController navController;

    private Button btnViewWorldInfo, btnViewHeadlines;
    private ImageButton btnViewDoctorAdvice, btnMeetDevelopers;
    private RecyclerView rcvNewsList;

    private CaseCardView ccvConfirmed, ccvDeceased;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_world_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViewsAndAttachListeners(view);
        initializeViewModelAndNavController();
    }

    @Override
    public void findViewsAndAttachListeners(View view) {
        ccvConfirmed = new CaseCardView(requireActivity(), view.findViewById(R.id.caseCardWorldHomeConfirmed), CaseType.TOTAL_CONFIRMED, "Confirmed");
        ccvDeceased = new CaseCardView(requireActivity(), view.findViewById(R.id.caseCardWorldHomeDeceased), CaseType.TOTAL_DECEASED, "Deceased");

        btnViewWorldInfo = view.findViewById(R.id.btnViewWorldInfo);
        btnViewWorldInfo.setOnClickListener(v -> navController.navigate(R.id.action_worldHomeFragment_to_worldInfoFragment));

        btnViewHeadlines = view.findViewById(R.id.btnViewAllWorldNews);
        btnViewHeadlines.setOnClickListener(v -> navController.navigate(R.id.action_worldHomeFragment_to_newsListFragment));

        btnViewDoctorAdvice = view.findViewById(R.id.layoutWorldHomeDoctorSuggestion).findViewById(R.id.btnViewDoctorAdvice);
        btnViewDoctorAdvice.setOnClickListener(v -> startActivity(new Intent(requireActivity(), CovidAdviceActivity.class)));

        btnMeetDevelopers = view.findViewById(R.id.layoutWorldHomeMeetDevelopers).findViewById(R.id.btnMeetDevelopers);
        btnMeetDevelopers.setOnClickListener(v -> {
            navController.navigate(R.id.action_worldHomeFragment_to_indiaHomeFragment);
        });

        rcvNewsList = view.findViewById(R.id.rcvWorldHomeNews);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireActivity());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        rcvNewsList.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void initializeViewModelAndNavController() {
        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        navController = Navigation.findNavController(requireView());

        mainViewModel.getNewsList().observe(getViewLifecycleOwner(), newsArrayList -> {
            ArrayList<News> newsList = new ArrayList<>();
            for (int i = 0; i < newsArrayList.size() && i < 2; i++) {
                newsList.add(newsArrayList.get(i));
            }
            NewsListAdapter newsListAdapter = new NewsListAdapter(newsList);
            rcvNewsList.setAdapter(newsListAdapter);
        });

        mainViewModel.getGlobalOverallStats().observe(getViewLifecycleOwner(), covidStatsData -> {
            ccvConfirmed.SetDetails(covidStatsData.getTotalConfirmed(), covidStatsData.getDailyConfirmed(), true);
            ccvDeceased.SetDetails(covidStatsData.getTotalDeceased(), covidStatsData.getDailyDeceased(), true);
        });
    }
}