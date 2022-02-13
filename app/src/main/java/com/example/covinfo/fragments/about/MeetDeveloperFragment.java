package com.example.covinfo.fragments.about;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.covinfo.R;
import com.example.covinfo.adapters.DeveloperListAdapter;
import com.example.covinfo.enums.TaskType;
import com.example.covinfo.manager.ActivityApiManager;
import com.example.covinfo.manager.ActivityViewManager;
import com.example.covinfo.viewmodels.MainViewModel;

public class MeetDeveloperFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_meet_developer, container, false);
    }

    private RecyclerView rcvDeveloperList;
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
        rcvDeveloperList = view.findViewById(R.id.rcvDeveloperList);
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
                .getDeveloperList()
                .observe(getViewLifecycleOwner(), developers -> {
                    LinearLayoutManager layoutManager = new LinearLayoutManager(requireActivity());
                    layoutManager.setOrientation(RecyclerView.VERTICAL);

                    DeveloperListAdapter listAdapter = new DeveloperListAdapter(requireActivity(), developers);

                    rcvDeveloperList.setLayoutManager(layoutManager);
                    rcvDeveloperList.setAdapter(listAdapter);
                });

        mainViewModel
                .getAboutApp()
                .observe(getViewLifecycleOwner(), aboutApp ->
                        btnShareApp.setOnClickListener(v -> {
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(aboutApp.getAppLink()));
                            requireActivity().startActivity(intent);
                        }));
    }

    private void updateActivityViews() {
        viewManager.setHeadingText("About");
        viewManager.updateAppBarVisibility(View.GONE, View.VISIBLE);
    }

    private void addApiTasks() {
        apiManager.addApiTask(TaskType.DEVELOPER_LIST, null, null);
        apiManager.addApiTask(TaskType.ABOUT_APP, null, null);
    }
}