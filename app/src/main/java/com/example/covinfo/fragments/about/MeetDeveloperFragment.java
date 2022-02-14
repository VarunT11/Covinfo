package com.example.covinfo.fragments.about;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.covinfo.R;
import com.example.covinfo.adapters.about.DeveloperListAdapter;
import com.example.covinfo.adapters.about.SourceListAdapter;
import com.example.covinfo.enums.TaskType;
import com.example.covinfo.manager.ActivityApiManager;
import com.example.covinfo.manager.ActivityViewManager;
import com.example.covinfo.viewmodels.MainViewModel;

public class MeetDeveloperFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_meet_developer, container, false);
    }

    private TextView tvAboutContent;
    private RecyclerView rcvDeveloperList, rcvSourceList;
    private Button btnShareApp;

    private MainViewModel mainViewModel;
    private ActivityViewManager viewManager;
    private ActivityApiManager apiManager;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViewAndAttachListeners(view);
        initializeViewModelAndManagers();
        addViewModelObservers();
        updateActivityViews();
        addApiTasks();
    }

    private void findViewAndAttachListeners(View view) {
        tvAboutContent = view.findViewById(R.id.tvAboutContent);
        rcvDeveloperList = view.findViewById(R.id.rcvDeveloperList);
        rcvSourceList = view.findViewById(R.id.rcvDataSourceList);
        btnShareApp = view.findViewById(R.id.btnShareApp);
    }

    private void initializeViewModelAndManagers() {
        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);

        AppCompatActivity activity = (AppCompatActivity) requireActivity();

        viewManager = ActivityViewManager.getInstance(activity);
        apiManager = ActivityApiManager.getInstance(activity);
    }

    private void addViewModelObservers() {
        mainViewModel
                .getAboutApp()
                .observe(getViewLifecycleOwner(), aboutApp -> {
                    tvAboutContent.setText(aboutApp.getAboutAppContent());
                    btnShareApp.setOnClickListener(v -> {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(aboutApp.getAppPlayStoreLink()));
                        requireActivity().startActivity(intent);
                    });
                });

        mainViewModel
                .getDeveloperList()
                .observe(getViewLifecycleOwner(), developers -> {
                    LinearLayoutManager layoutManager = new LinearLayoutManager(requireActivity());
                    layoutManager.setOrientation(RecyclerView.VERTICAL);

                    DeveloperListAdapter listAdapter = new DeveloperListAdapter(requireActivity(), developers);

                    rcvDeveloperList.setLayoutManager(layoutManager);
                    rcvDeveloperList.setAdapter(listAdapter);
                });

        mainViewModel
                .getDataSourceList()
                .observe(getViewLifecycleOwner(), dataSources -> {
                    LinearLayoutManager layoutManager = new LinearLayoutManager(requireActivity());
                    layoutManager.setOrientation(RecyclerView.VERTICAL);

                    SourceListAdapter listAdapter = new SourceListAdapter(requireActivity(), dataSources);

                    rcvSourceList.setLayoutManager(layoutManager);
                    rcvSourceList.setAdapter(listAdapter);
                });
    }

    private void updateActivityViews() {
        viewManager.setHeadingText("About");
        viewManager.updateAppBarVisibility(View.GONE, View.VISIBLE);
    }

    private void addApiTasks() {
        apiManager.addApiTask(TaskType.ABOUT_APP, null, null);
        apiManager.addApiTask(TaskType.DEVELOPER_LIST, null, null);
        apiManager.addApiTask(TaskType.DATA_SOURCE_LIST, null, null);
    }
}