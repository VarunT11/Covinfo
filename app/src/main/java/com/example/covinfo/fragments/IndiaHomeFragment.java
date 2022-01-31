package com.example.covinfo.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

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
import com.example.covinfo.interfaces.ActivityFragmentInterface;
import com.example.covinfo.viewmodels.MainViewModel;

import java.util.ArrayList;
import java.util.Locale;

public class IndiaHomeFragment extends Fragment implements ActivityFragmentInterface {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_india_home, container, false);
    }

    private TextView tvTotalConfirmed, tvTotalRecovered, tvTotalDeceased,
            tvDailyConfirmed, tvDailyRecovered, tvDailyDeceased;
    private Button btnViewIndiaInfo, btnViewHeadlines;
    private ImageButton btnViewDoctorAdvice, btnMeetDevelopers;
    private RecyclerView rcvNewsList;

    private MainViewModel mainViewModel;
    private NavController navController;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViewsAndAttachListeners(view);
        initializeViewModelAndNavController();
    }

    @Override
    public void findViewsAndAttachListeners(View view) {
        tvTotalConfirmed = view.findViewById(R.id.tvConfirmedInfoCardTotalCases);
        tvTotalRecovered = view.findViewById(R.id.tvRecoveredInfoCardTotalCases);
        tvTotalDeceased = view.findViewById(R.id.tvDeceasedInfoCardTotalCases);
        tvDailyConfirmed = view.findViewById(R.id.tvConfirmedInfoCardDailyCases);
        tvDailyRecovered = view.findViewById(R.id.tvRecoveredInfoCardDailyCases);
        tvDailyDeceased = view.findViewById(R.id.tvDeceasedInfoCardDailyCases);

        btnViewIndiaInfo = view.findViewById(R.id.btnViewIndiaInfo);
        btnViewIndiaInfo.setOnClickListener(v ->
                navController.navigate(R.id.action_indiaHomeFragment_to_indiaInfoFragment)
        );

        btnViewHeadlines = view.findViewById(R.id.btnViewAllNews);
        btnViewHeadlines.setOnClickListener(v ->
                navController.navigate(R.id.action_indiaHomeFragment_to_newsListFragment)
        );

        btnViewDoctorAdvice = view.findViewById(R.id.btnViewDoctorAdvice);
        btnViewDoctorAdvice.setOnClickListener(v -> {
            startActivity(new Intent(requireActivity(), CovidAdviceActivity.class));
        });

        btnMeetDevelopers = view.findViewById(R.id.btnMeetDevelopers);
        btnMeetDevelopers.setOnClickListener(v -> {

        });

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
            NewsListAdapter newsListAdapter = new NewsListAdapter(newsList);
            rcvNewsList.setAdapter(newsListAdapter);
        });

        mainViewModel.getIndiaStats().observe(getViewLifecycleOwner(), indiaStats -> {
            tvTotalConfirmed.setText(
                    String.format(Locale.ENGLISH, "%d", indiaStats.getTotalConfirmedCases())
            );
            tvTotalRecovered.setText(
                    String.format(Locale.ENGLISH, "%d", indiaStats.getTotalRecoveredCases())
            );
            tvTotalDeceased.setText(
                    String.format(Locale.ENGLISH, "%d", indiaStats.getTotalDeceasedCases())
            );
            tvDailyConfirmed.setText(
                    String.format(Locale.ENGLISH, "(+%d)", indiaStats.getDailyConfirmedCases())
            );
            tvDailyRecovered.setText(
                    String.format(Locale.ENGLISH, "(+%d)", indiaStats.getDailyRecoveredCases())
            );
            tvDailyDeceased.setText(
                    String.format(Locale.ENGLISH, "(+%d)", indiaStats.getDailyDeceasedCases())
            );
        });
    }
}