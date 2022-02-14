package com.geekhaven.covinfo.fragments.news;

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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.covinfo.R;
import com.geekhaven.covinfo.adapters.stats.NewsListAdapter;
import com.geekhaven.covinfo.manager.ActivityViewManager;
import com.geekhaven.covinfo.viewmodels.MainViewModel;

public class NewsListFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_news_list, container, false);
    }

    private RecyclerView rcvNewsList;

    private MainViewModel mainViewModel;
    private NavController navController;
    private ActivityViewManager viewManager;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        findViewsAndAttachListeners(view);
        initializeViewModelAndManagers();
        updateActivityViews();
        addViewModelObservers();
    }

    private void findViewsAndAttachListeners(View view) {
        rcvNewsList = view.findViewById(R.id.rcvNewsList);
    }

    private void initializeViewModelAndManagers() {
        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        navController = Navigation.findNavController(requireView());
        viewManager = ActivityViewManager.getInstance((AppCompatActivity) requireActivity());
    }

    private void updateActivityViews() {
        viewManager.updateAppBarVisibility(View.GONE, View.VISIBLE);
        viewManager.setHeadingText("News List");
    }

    private void addViewModelObservers() {
        mainViewModel
                .getNewsList()
                .observe(getViewLifecycleOwner(), newsArrayList -> {
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireActivity());
                    linearLayoutManager.setOrientation(RecyclerView.VERTICAL);

                    NewsListAdapter newsListAdapter = new NewsListAdapter(newsArrayList, position -> {
                        mainViewModel.setCurrentNews(position);
                        navController.navigate(R.id.action_newsListFragment_to_newsFragment);
                    });

                    rcvNewsList.setLayoutManager(linearLayoutManager);
                    rcvNewsList.setAdapter(newsListAdapter);
                });
    }
}