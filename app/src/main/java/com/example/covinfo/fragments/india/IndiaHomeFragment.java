package com.example.covinfo.fragments.india;

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
import com.example.covinfo.adapters.NewsListAdapter;
import com.example.covinfo.classes.News;
import com.example.covinfo.enums.CaseType;
import com.example.covinfo.enums.RegionType;
import com.example.covinfo.enums.TaskType;
import com.example.covinfo.manager.ActivityApiManager;
import com.example.covinfo.manager.ActivityViewManager;
import com.example.covinfo.views.CaseCardView;
import com.example.covinfo.viewmodels.MainViewModel;

import java.util.ArrayList;

public class IndiaHomeFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_india_home, container, false);
    }

    private CaseCardView ccvConfirmed, ccvActive, ccvRecovered, ccvDeceased;
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
                requireActivity(), view.findViewById(R.id.caseCardIndiaHomeConfirmed),
                CaseType.TOTAL_CONFIRMED, "Confirmed"
        );

        ccvActive = new CaseCardView(
                requireActivity(), view.findViewById(R.id.caseCardIndiaHomeActive),
                CaseType.TOTAL_ACTIVE, "Active"
        );

        ccvRecovered = new CaseCardView(
                requireActivity(), view.findViewById(R.id.caseCardIndiaHomeRecovered),
                CaseType.TOTAL_RECOVERED, "Recovered"
        );

        ccvDeceased = new CaseCardView(
                requireActivity(), view.findViewById(R.id.caseCardIndiaHomeDeceased),
                CaseType.TOTAL_DECEASED, "Deceased"
        );

        Button btnViewIndiaInfo = view.findViewById(R.id.btnViewIndiaInfo);
        Button btnViewHeadlines = view.findViewById(R.id.btnViewAllNews);
        ImageButton btnViewDoctorAdvice = view.findViewById(R.id.layoutIndiaHomeDoctorSuggestion).findViewById(R.id.btnViewDoctorAdvice);
        ImageButton btnMeetDevelopers = view.findViewById(R.id.layoutIndiaHomeMeetDevelopers).findViewById(R.id.btnMeetDevelopers);

        btnViewIndiaInfo.setOnClickListener(v -> navController.navigate(R.id.action_indiaHomeFragment_to_indiaInfoFragment));
        btnViewHeadlines.setOnClickListener(v -> navController.navigate(R.id.action_indiaHomeFragment_to_newsListFragment));
        btnViewDoctorAdvice.setOnClickListener(v -> navController.navigate(R.id.action_indiaHomeFragment_to_adviceHomeFragment));
        btnMeetDevelopers.setOnClickListener(v -> navController.navigate(R.id.action_indiaHomeFragment_to_meetDeveloperFragment));

        rcvNewsList = view.findViewById(R.id.rcvIndiaHomeNews);
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
        navAndViewManager.updateRegionIconImage(RegionType.INDIA);
    }

    private void addViewModelObservers() {
        mainViewModel
                .getCurrentRegionType()
                .observe(getViewLifecycleOwner(), regionType -> {
                    if (regionType == RegionType.GLOBAL) {
                        navController.navigate(R.id.action_indiaHomeFragment_to_worldHomeFragment);
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
                        navController.navigate(R.id.action_indiaHomeFragment_to_newsFragment);
                    });

                    rcvNewsList.setLayoutManager(linearLayoutManager);
                    rcvNewsList.setAdapter(newsListAdapter);
                });

        mainViewModel
                .getIndiaOverallStats()
                .observe(getViewLifecycleOwner(), covidStatsData -> {
                    ccvConfirmed.SetDetails(
                            covidStatsData.getTotalConfirmed(), covidStatsData.getDailyConfirmed(), true
                    );
                    ccvRecovered.SetDetails(
                            covidStatsData.getTotalRecovered(), covidStatsData.getDailyRecovered(), true
                    );
                    ccvDeceased.SetDetails(
                            covidStatsData.getTotalDeceased(), covidStatsData.getDailyDeceased(), true
                    );
                    ccvActive.SetDetails(
                            covidStatsData.getTotalActive(), covidStatsData.getDailyActive(), true
                    );
                });
    }

    private void addApiTasks() {
        apiManager.addApiTask(TaskType.INDIA_NEWS, null, null);
        apiManager.addApiTask(TaskType.INDIA_DATA, null, null);
    }
}