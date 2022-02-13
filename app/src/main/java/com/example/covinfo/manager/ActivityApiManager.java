package com.example.covinfo.manager;

import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.covinfo.R;
import com.example.covinfo.api.ApiSingleton;
import com.example.covinfo.classes.AboutApp;
import com.example.covinfo.classes.CovidStats;
import com.example.covinfo.enums.TaskType;
import com.example.covinfo.fragments.util.ProgressDialogFragment;
import com.example.covinfo.utils.JSONParser;
import com.example.covinfo.viewmodels.MainViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class ActivityApiManager {

    private static final HashMap<AppCompatActivity, ActivityApiManager> apiManagerHashMap = new HashMap<>();

    public static ActivityApiManager getInstance(AppCompatActivity activity) {
        if (!apiManagerHashMap.containsKey(activity)) {
            ActivityApiManager apiManager = new ActivityApiManager(activity);
            apiManagerHashMap.put(activity, apiManager);
        }
        return apiManagerHashMap.get(activity);
    }

    private static final String PROGRESS_FRAGMENT_TAG = "ProgressDialogFragment";

    private final AppCompatActivity activity;

    private final ApiSingleton apiSingleton;
    private final MainViewModel mainViewModel;
    private final JSONParser jsonParser;

    private final ArrayList<String> activeNetworkTasks;
    private final ProgressDialogFragment progressDialogFragment;

    private ActivityApiManager(AppCompatActivity activity) {
        this.activity = activity;

        apiSingleton = ApiSingleton.getInstance(activity);
        mainViewModel = new ViewModelProvider(activity).get(MainViewModel.class);
        jsonParser = new JSONParser();

        activeNetworkTasks = new ArrayList<>();
        progressDialogFragment = ProgressDialogFragment.newInstance();
    }

    private synchronized void addNetworkTask(String uid) {
        if (activeNetworkTasks.size() == 0) {
            progressDialogFragment.show(activity.getSupportFragmentManager(), PROGRESS_FRAGMENT_TAG);
        }
        activeNetworkTasks.add(uid);
    }

    private synchronized void removeNetworkTask(String uid) {
        activeNetworkTasks.remove(uid);
        if (activeNetworkTasks.size() == 0) {
            progressDialogFragment.dismiss();
        }
    }

    public void addApiTask(TaskType taskType, String code, String name) {
        String url = getApiUrl(taskType, code, name);

        String uid = UUID.randomUUID().toString();
        addNetworkTask(uid);

        apiSingleton.sendGetRequest(url, (success, result) -> {
            if (success) {
                onTaskSuccess(taskType, result);
            } else {
                onTaskFailure(taskType, result);
            }

            removeNetworkTask(uid);
        });
    }

    private void onTaskSuccess(TaskType taskType, String response) {
        if(taskType == TaskType.DEVELOPER_LIST){
            mainViewModel.setDeveloperList(jsonParser.parseJSONToDeveloperList(response));
        } else if (taskType == TaskType.ABOUT_APP){
            mainViewModel.setAboutApp(jsonParser.parseJSONToAboutApp(response));
        }
        else if (taskType == TaskType.WORLD_NEWS || taskType == TaskType.INDIA_NEWS) {
            mainViewModel.setNewsList(jsonParser.parseJSONToNewsList(response));
        } else if (taskType == TaskType.REGION_INFO_LIST) {
            mainViewModel.setRegionInfoList(jsonParser.parseJSONToInfoList(response));
        } else {
            updateViewModelWithStats(taskType, jsonParser.parseJSONToStats(response));
        }
    }

    private void onTaskFailure(TaskType taskType, String error) {
        if(error!=null)
            Log.d(taskType.name(), error);

        if(taskType == TaskType.DEVELOPER_LIST){
            mainViewModel.setDeveloperList(new ArrayList<>());
        } else if (taskType == TaskType.ABOUT_APP){
            mainViewModel.setAboutApp(new AboutApp());
        }
        else if (taskType == TaskType.WORLD_NEWS || taskType == TaskType.INDIA_NEWS) {
            mainViewModel.setNewsList(new ArrayList<>());
        } else if (taskType == TaskType.REGION_INFO_LIST) {
            mainViewModel.setRegionInfoList(new ArrayList<>());
        } else {
            updateViewModelWithStats(taskType, new ArrayList<>());
        }
    }

    private String getApiUrl(TaskType taskType, String code, String name) {
        String root_url = activity.getString(R.string.backend_url);
        switch (taskType) {
            case DEVELOPER_LIST:
                return root_url + "about/developers/";
            case ABOUT_APP:
                return root_url + "about/app/";
            case WORLD_NEWS:
                return root_url + "world/news/";
            case GLOBAL_DATA:
                return root_url + "world/global/";
            case REGION_INFO_LIST:
                return root_url + "world/region/info/";
            case REGION_DATA_LIST:
                return root_url + "world/region/data/";
            case REGION_DATA:
                return root_url + "world/region/data/" + code + "/";
            case REGION_COUNTRY_DATA_LIST:
                return root_url + "world/region/data/" + code + "/countries/";
            case COUNTRY_DATA_LIST:
                return root_url + "world/country/data/";
            case COUNTRY_DATA:
                return root_url + "world/country/data/" + code + "/";
            case COUNTRY_DATA_TIME_SERIES:
                return root_url + "world/country/data/" + code + "/timeseries/";
            case INDIA_NEWS:
                return root_url + "india/news/";
            case INDIA_DATA:
                return root_url + "india/";
            case INDIA_DATA_TIME_SERIES:
                return root_url + "india/timeseries/";
            case STATE_DATA_LIST:
                return root_url + "state/data/";
            case STATE_DATA:
                return root_url + "state/data/" + code + "/";
            case STATE_DATA_TIME_SERIES:
                return root_url + "state/data/" + code + "/timeseries/";
            case DISTRICT_DATA_LIST:
                return root_url + "state/data/" + code + "/districts/";
            case DISTRICT_DATA:
                return root_url + "state/data/" + code + "/districts/" + name + "/";
            case DISTRICT_DATA_TIME_SERIES:
                return root_url + "state/data/" + code + "/districts/" + name + "/timeseries/";
        }
        return root_url;
    }

    private void updateViewModelWithStats(TaskType taskType, ArrayList<CovidStats> statsList) {
        CovidStats statsData;
        if (statsList.size() == 0) {
            statsData = new CovidStats();
        } else {
            statsData = statsList.get(0);
        }

        switch (taskType) {
            case GLOBAL_DATA:
                mainViewModel.setGlobalOverallStats(statsData);
                break;
            case REGION_DATA_LIST:
                mainViewModel.setWhoRegionDataList(statsList);
                break;
            case REGION_DATA:
                mainViewModel.setCurrentWhoRegionStats(statsData);
                break;
            case REGION_COUNTRY_DATA_LIST:
                mainViewModel.setWhoRegionCountryDataList(statsList);
                break;
            case COUNTRY_DATA_LIST:
                mainViewModel.setCountryDataList(statsList);
                break;
            case COUNTRY_DATA:
                mainViewModel.setCurrentCountryStats(statsData);
                break;
            case COUNTRY_DATA_TIME_SERIES:
                mainViewModel.setCountryTimeSeriesData(statsList);
                break;
            case INDIA_DATA:
                mainViewModel.setIndiaOverallStats(statsData);
                break;
            case INDIA_DATA_TIME_SERIES:
                mainViewModel.setIndiaTimeSeriesData(statsList);
                break;
            case STATE_DATA_LIST:
                mainViewModel.setStateDataList(statsList);
                break;
            case STATE_DATA:
                mainViewModel.setCurrentStateStats(statsData);
                break;
            case STATE_DATA_TIME_SERIES:
                mainViewModel.setCurrentStateTimeSeriesData(statsList);
                break;
            case DISTRICT_DATA_LIST:
                mainViewModel.setDistrictDataList(statsList);
                break;
            case DISTRICT_DATA:
                mainViewModel.setCurrentDistrictStats(statsData);
                break;
            case DISTRICT_DATA_TIME_SERIES:
                mainViewModel.setCurrentDistrictTimeSeriesData(statsList);
                break;
        }
    }

}
