package com.example.covinfo.fragments.world;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.covinfo.R;
import com.example.covinfo.adapters.stats.NewsListAdapter;
import com.example.covinfo.classes.stats.News;
import com.example.covinfo.enums.CaseType;
import com.example.covinfo.enums.RegionType;
import com.example.covinfo.enums.TaskType;
import com.example.covinfo.manager.ActivityApiManager;
import com.example.covinfo.manager.ActivityViewManager;
import com.example.covinfo.views.CaseCardView;
import com.example.covinfo.viewmodels.MainViewModel;

import java.util.ArrayList;

public class WorldHomeFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_world_home, container, false);
    }

    private CaseCardView ccvConfirmed, ccvDeceased;
    private RecyclerView rcvNewsList;

    private MainViewModel mainViewModel;
    private NavController navController;
    private ActivityViewManager navAndViewManager;
    private ActivityApiManager apiManager;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViewsAndAttachListeners(view);
        initializeViewModelAndManagers();
        updateActivityViews();
        addViewModelObservers();
        addApiTasks();
    }

    private void findViewsAndAttachListeners(View view) {
        ccvConfirmed = new CaseCardView(
                requireActivity(), view.findViewById(R.id.caseCardWorldHomeConfirmed),
                CaseType.TOTAL_CONFIRMED, "Confirmed"
        );
        ccvDeceased = new CaseCardView(
                requireActivity(), view.findViewById(R.id.caseCardWorldHomeDeceased),
                CaseType.TOTAL_DECEASED, "Deceased"
        );

        Button btnViewWorldInfo = view.findViewById(R.id.btnViewWorldInfo);
        Button btnViewHeadlines = view.findViewById(R.id.btnViewAllWorldNews);
        ImageButton btnViewDoctorAdvice = view.findViewById(R.id.layoutWorldHomeDoctorSuggestion).findViewById(R.id.btnViewDoctorAdvice);
        ImageButton btnMeetDevelopers = view.findViewById(R.id.layoutWorldHomeMeetDevelopers).findViewById(R.id.btnMeetDevelopers);

        btnViewWorldInfo.setOnClickListener(v -> navController.navigate(R.id.action_worldHomeFragment_to_worldInfoFragment));
        btnViewHeadlines.setOnClickListener(v -> navController.navigate(R.id.action_worldHomeFragment_to_newsListFragment));
        btnViewDoctorAdvice.setOnClickListener(v -> navController.navigate(R.id.action_worldHomeFragment_to_adviceHomeFragment));
        btnMeetDevelopers.setOnClickListener(v -> navController.navigate(R.id.action_worldHomeFragment_to_meetDeveloperFragment));

        rcvNewsList = view.findViewById(R.id.rcvWorldHomeNews);
    }

    private void initializeViewModelAndManagers() {
        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        navController = Navigation.findNavController(requireView());

        AppCompatActivity activity = (AppCompatActivity) requireActivity();

        navAndViewManager = ActivityViewManager.getInstance(activity);
        apiManager = ActivityApiManager.getInstance(activity);
    }

    private void updateActivityViews() {
        navAndViewManager.updateAppBarVisibility(View.VISIBLE, View.GONE);
        navAndViewManager.updateRegionIconImage(RegionType.GLOBAL);
    }

    private void addViewModelObservers() {
        mainViewModel
                .getCurrentRegionType()
                .observe(getViewLifecycleOwner(), regionType -> {
                    if (regionType == RegionType.INDIA) {
                        navController.navigate(R.id.action_worldHomeFragment_to_indiaHomeFragment);
                    }
                });

        mainViewModel
                .getNewsList()
                .observe(getViewLifecycleOwner(), newsArrayList -> {
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireActivity());
                    linearLayoutManager.setOrientation(RecyclerView.VERTICAL);

                    ArrayList<News> newsList = new ArrayList<>();
                    for (int i = 0; i < newsArrayList.size() && i < 2; i++) {
                        newsList.add(newsArrayList.get(i));
                    }
                    NewsListAdapter newsListAdapter = new NewsListAdapter(newsList, position -> {
                        mainViewModel.setCurrentNews(position);
                        navController.navigate(R.id.action_worldHomeFragment_to_newsFragment);
                    });

                    rcvNewsList.setLayoutManager(linearLayoutManager);
                    rcvNewsList.setAdapter(newsListAdapter);
                });

        mainViewModel
                .getGlobalOverallStats()
                .observe(getViewLifecycleOwner(), covidStatsData -> {
                    ccvConfirmed.SetDetails(
                            covidStatsData.getTotalConfirmed(), covidStatsData.getDailyConfirmed(), true
                    );
                    ccvDeceased.SetDetails(
                            covidStatsData.getTotalDeceased(), covidStatsData.getDailyDeceased(), true
                    );
                });
    }

    private void addApiTasks() {
        apiManager.addApiTask(TaskType.WORLD_NEWS, null, null);
        apiManager.addApiTask(TaskType.GLOBAL_DATA, null, null);
    }
}