package com.geekhaven.covinfo.fragments.world;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.geekhaven.covinfo.R;
import com.geekhaven.covinfo.enums.TaskType;
import com.geekhaven.covinfo.manager.ActivityApiManager;
import com.geekhaven.covinfo.manager.ActivityViewManager;
import com.geekhaven.covinfo.viewmodels.MainViewModel;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class WorldInfoFragment extends Fragment {

    private static class InfoSlidePageAdapter extends FragmentStateAdapter {
        public InfoSlidePageAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            if (position == 0)
                return RegionListFragment.newInstance();
            else
                return CountryListFragment.newInstance();
        }

        @Override
        public int getItemCount() {
            return 2;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_world_info, container, false);
    }

    private MainViewModel mainViewModel;
    private ActivityViewManager viewManager;
    private ActivityApiManager apiManager;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        findViewsAndAttachListeners(view);
        initializeViewModelsAndManagers();
        updateActivityViews();
        addApiTasks();
    }

    private void findViewsAndAttachListeners(View view){
        ViewPager2 viewPager = view.findViewById(R.id.viewPagerWorldInfo);
        TabLayout tabLayout = view.findViewById(R.id.tabLayoutWorldInfo);

        InfoSlidePageAdapter pageAdapter = new InfoSlidePageAdapter(requireActivity());
        viewPager.setAdapter(pageAdapter);

        new TabLayoutMediator(tabLayout, viewPager, true, (tab, position) -> {
            if(position == 0){
                tab.setText("Region-Wise");
            } else {
                tab.setText("Country-Wise");
            }
        }).attach();
    }

    private void initializeViewModelsAndManagers(){
        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);

        AppCompatActivity activity = (AppCompatActivity) requireActivity();

        viewManager = ActivityViewManager.getInstance(activity);
        apiManager = ActivityApiManager.getInstance(activity);
    }

    private void updateActivityViews(){
        viewManager.updateAppBarVisibility(View.GONE, View.VISIBLE);
        viewManager.setHeadingText("Global Updates");
    }

    private void addApiTasks(){
        if (mainViewModel.getRegionInfoList().getValue() == null){
            apiManager.addApiTask(TaskType.REGION_INFO_LIST, null, null);
            apiManager.addApiTask(TaskType.COUNTRY_DATA_LIST, null, null);
        }
    }
}