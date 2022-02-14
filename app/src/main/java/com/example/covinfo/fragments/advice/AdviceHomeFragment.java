package com.example.covinfo.fragments.advice;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.example.covinfo.R;
import com.example.covinfo.adapters.utils.CircleIndicatorAdapter;
import com.example.covinfo.manager.ActivityViewManager;

public class AdviceHomeFragment extends Fragment {

    private static class ScreenSlidePagerAdapter extends FragmentStateAdapter {
        public ScreenSlidePagerAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            return CovidAdviceFragment.newInstance(position);
        }

        @Override
        public int getItemCount() {
            return NUM_ADVICES;
        }
    }

    private static final int NUM_ADVICES = 10;
    private RecyclerView rcvIndicatorView;
    private ViewPager2 viewPager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_advice_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        updateActivityView();
        setupViewPager(view);
    }

    private void updateActivityView(){
        ActivityViewManager viewManager = ActivityViewManager.getInstance((AppCompatActivity) requireActivity());
        viewManager.updateAppBarVisibility(View.GONE, View.VISIBLE);
        viewManager.setHeadingText("COVID Advices");
    }

    private void setupViewPager(View view){
        viewPager = view.findViewById(R.id.viewPagerAdvice);
        rcvIndicatorView = view.findViewById(R.id.rcvCircleIndicator);

        FragmentStateAdapter pagerAdapter = new ScreenSlidePagerAdapter(requireActivity());
        viewPager.setAdapter(pagerAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(requireActivity());
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        rcvIndicatorView.setLayoutManager(layoutManager);

        updateIndicatorPosition(0);

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                updateIndicatorPosition(position);
            }
        });
    }

    private void updateIndicatorPosition(int position) {
        CircleIndicatorAdapter adapter = new CircleIndicatorAdapter(
                (AppCompatActivity) requireActivity(),
                NUM_ADVICES,
                position,
                newPosition -> viewPager.setCurrentItem(newPosition, true)
        );
        rcvIndicatorView.setAdapter(adapter);
    }
}