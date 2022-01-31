package com.example.covinfo.fragments;

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

import com.example.covinfo.R;
import com.example.covinfo.adapters.NewsListAdapter;
import com.example.covinfo.classes.News;
import com.example.covinfo.interfaces.ActivityFragmentInterface;
import com.example.covinfo.viewmodels.MainViewModel;

import java.util.ArrayList;


public class NewsListFragment extends Fragment implements ActivityFragmentInterface {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news_list, container, false);
    }

    private MainViewModel mainViewModel;
    private NavController navController;
    private RecyclerView rcvNewsList;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViewsAndAttachListeners(view);
        initializeViewModelAndNavController();
    }

    @Override
    public void findViewsAndAttachListeners(View view) {
        rcvNewsList = view.findViewById(R.id.rcvNewsList);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireActivity());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        rcvNewsList.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void initializeViewModelAndNavController() {
        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        navController = Navigation.findNavController(requireView());

        mainViewModel.getNewsList().observe(getViewLifecycleOwner(), newsArrayList -> {
            NewsListAdapter newsListAdapter = new NewsListAdapter(newsArrayList);
            rcvNewsList.setAdapter(newsListAdapter);
        });
    }
}