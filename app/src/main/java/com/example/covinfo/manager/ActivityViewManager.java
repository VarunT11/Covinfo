package com.example.covinfo.manager;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.covinfo.R;
import com.example.covinfo.enums.RegionType;
import com.example.covinfo.viewmodels.MainViewModel;

import java.util.HashMap;

public class ActivityViewManager {

    private static final HashMap<AppCompatActivity, ActivityViewManager> managerHashMap = new HashMap<>();

    public static ActivityViewManager getInstance(AppCompatActivity activity){
        if(!managerHashMap.containsKey(activity)){
            managerHashMap.put(activity, new ActivityViewManager(activity));
        }
        return managerHashMap.get(activity);
    }

    private AppCompatActivity activity;

    private View appBarHomeView, appBarFragmentView;
    private TextView tvFragmentHeading;
    private ImageView iconRegionMenu;

    private MainViewModel mainViewModel;
    private NavController navController;

    public ActivityViewManager(AppCompatActivity activity){
        this.activity = activity;
        findViewsAndAttachListeners();
    }

    private void findViewsAndAttachListeners(){
        appBarHomeView = activity.findViewById(R.id.appBarHomeLayout);
        appBarFragmentView = activity.findViewById(R.id.appBarFragmentLayout);
        tvFragmentHeading = activity.findViewById(R.id.tvFragmentHeading);
        iconRegionMenu = activity.findViewById(R.id.imgHomeMenuIcon);

        ImageButton btnBack = activity.findViewById(R.id.btnBackActivity);
        btnBack.setOnClickListener(v -> activity.onBackPressed());

        mainViewModel = new ViewModelProvider(activity).get(MainViewModel.class);
        navController = Navigation.findNavController(activity, R.id.navHostMain);
    }

    public void updateAppBarVisibility(int homeVisible, int fragmentVisible){
        appBarHomeView.setVisibility(homeVisible);
        appBarFragmentView.setVisibility(fragmentVisible);
    }

    public void setHeadingText(String headingText){
        tvFragmentHeading.setText(headingText);
    }

    public void updateRegionIconImage(RegionType regionType){
        @DrawableRes int iconId;
        if (regionType == RegionType.INDIA){
            iconId = R.drawable.ic_india;
        } else {
            iconId = R.drawable.ic_globe;
        }
        iconRegionMenu.setImageDrawable(AppCompatResources.getDrawable(activity, iconId));
    }

}
