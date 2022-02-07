package com.example.covinfo.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.covinfo.R;
import com.example.covinfo.activities.CovidAdviceActivity;
import com.example.covinfo.adapters.NewsListAdapter;
import com.example.covinfo.classes.News;
import com.example.covinfo.enums.CaseType;
import com.example.covinfo.interfaces.ActivityFragmentInterface;
import com.example.covinfo.ui.CaseCardView;
import com.example.covinfo.viewmodels.MainViewModel;

import java.util.ArrayList;

public class IndiaHomeFragment extends Fragment implements ActivityFragmentInterface {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_india_home, container, false);
    }

    private Button btnViewIndiaInfo, btnViewHeadlines;
    private ImageButton btnViewDoctorAdvice, btnMeetDevelopers;
    private RecyclerView rcvNewsList;

    private MainViewModel mainViewModel;
    private NavController navController;

    private CaseCardView ccvConfirmed, ccvActive, ccvRecovered, ccvDeceased;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViewsAndAttachListeners(view);
        initializeViewModelAndNavController();
    }

    @Override
    public void findViewsAndAttachListeners(View view) {
        ccvConfirmed = new CaseCardView(requireActivity(), view.findViewById(R.id.caseCardIndiaHomeConfirmed), CaseType.TOTAL_CONFIRMED, "Confirmed");
        ccvActive = new CaseCardView(requireActivity(), view.findViewById(R.id.caseCardIndiaHomeActive), CaseType.TOTAL_ACTIVE, "Active");
        ccvRecovered = new CaseCardView(requireActivity(), view.findViewById(R.id.caseCardIndiaHomeRecovered), CaseType.TOTAL_RECOVERED, "Recovered");
        ccvDeceased = new CaseCardView(requireActivity(), view.findViewById(R.id.caseCardIndiaHomeDeceased), CaseType.TOTAL_DECEASED, "Deceased");

        btnViewIndiaInfo = view.findViewById(R.id.btnViewIndiaInfo);
        btnViewIndiaInfo.setOnClickListener(v -> navController.navigate(R.id.action_indiaHomeFragment_to_indiaInfoFragment));

        btnViewHeadlines = view.findViewById(R.id.btnViewAllNews);
        btnViewHeadlines.setOnClickListener(v -> navController.navigate(R.id.action_indiaHomeFragment_to_newsListFragment));

        btnViewDoctorAdvice = view.findViewById(R.id.layoutIndiaHomeDoctorSuggestion).findViewById(R.id.btnViewDoctorAdvice);
        btnViewDoctorAdvice.setOnClickListener(v -> startActivity(new Intent(requireActivity(), CovidAdviceActivity.class)));

        btnMeetDevelopers = view.findViewById(R.id.layoutIndiaHomeMeetDevelopers).findViewById(R.id.btnMeetDevelopers);
        btnMeetDevelopers.setOnClickListener(v -> navController.navigate(R.id.action_indiaHomeFragment_to_meetDeveloperFragment));

        rcvNewsList = view.findViewById(R.id.rcvIndiaHomeNews);

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
            NewsListAdapter newsListAdapter = new NewsListAdapter(newsList, position -> {
                mainViewModel.setCurrentNews(position);
                navController.navigate(R.id.action_indiaHomeFragment_to_newsFragment);
            });
            rcvNewsList.setAdapter(newsListAdapter);
        });

        mainViewModel.getIndiaOverallStats().observe(getViewLifecycleOwner(), covidStatsData -> {
            ccvConfirmed.SetDetails(covidStatsData.getTotalConfirmed(), covidStatsData.getDailyConfirmed(), true);
            ccvRecovered.SetDetails(covidStatsData.getTotalRecovered(), covidStatsData.getDailyRecovered(), true);
            ccvDeceased.SetDetails(covidStatsData.getTotalDeceased(), covidStatsData.getDailyDeceased(), true);
            ccvActive.SetDetails(covidStatsData.getTotalActive(), covidStatsData.getDailyActive(), true);
        });
    }
}