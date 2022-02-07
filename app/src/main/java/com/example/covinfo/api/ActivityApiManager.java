package com.example.covinfo.api;

import android.app.ProgressDialog;

import androidx.appcompat.app.AppCompatActivity;

import com.example.covinfo.R;
import com.example.covinfo.classes.CovidStats;
import com.example.covinfo.classes.News;
import com.example.covinfo.enums.TaskType;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class ActivityApiManager {

    public interface ApiManagerInterface {

        void onNewsFetchComplete(TaskType taskType, boolean success, ArrayList<News> newsList, String error);

        void onStatsDataFetchComplete(TaskType taskType, boolean success, ArrayList<CovidStats> covidStatsData, String error);

    }

    private static final HashMap<AppCompatActivity, ActivityApiManager> apiManagerHashMap = new HashMap<>();

    public static ActivityApiManager initializeApiManager(AppCompatActivity activity, ApiManagerInterface apiManagerInterface) {
        apiManagerHashMap.remove(activity);
        ActivityApiManager apiManager = new ActivityApiManager(activity, apiManagerInterface);
        apiManagerHashMap.put(activity, apiManager);
        return apiManager;
    }

    public static ActivityApiManager getInstance(AppCompatActivity activity) {
        if (!apiManagerHashMap.containsKey(activity)) {
            return null;
        }
        return apiManagerHashMap.get(activity);
    }

    private AppCompatActivity activity;
    private ApiManagerInterface apiManagerInterface;
    private ApiSingleton apiSingleton;

    private final String root_url;

    private ActivityApiManager(AppCompatActivity activity, ApiManagerInterface apiManagerInterface) {
        this.activity = activity;
        this.apiManagerInterface = apiManagerInterface;

        root_url = activity.getString(R.string.backend_url);
        apiSingleton = ApiSingleton.getInstance(activity);
        activeNetworkTasks = new ArrayList<>();

        progressDialog = new ProgressDialog(activity);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Updating Data");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
    }

    private ArrayList<String> activeNetworkTasks;
    private ProgressDialog progressDialog;

    private synchronized void addNetworkTask(String uid) {
        if (activeNetworkTasks.size() == 0) {
            progressDialog.show();
        }
        activeNetworkTasks.add(uid);
    }

    private synchronized void removeNetworkTask(String uid) {
        activeNetworkTasks.remove(uid);
        if (activeNetworkTasks.size() == 0) {
            progressDialog.dismiss();
        }
    }

    public void addApiTask(TaskType taskType, String regionCode, String countryCode, String stateCode, String districtName){
        String url = getApiUrl(taskType, regionCode, countryCode, stateCode, districtName);

        String uid = UUID.randomUUID().toString();
        addNetworkTask(uid);

        apiSingleton.sendGetRequest(url, (success, result) -> {
            if(success){
                onTaskSuccess(taskType, result);
            } else {
                onTaskFailure(taskType, result);
            }

            removeNetworkTask(uid);
        });
    }

    private String getApiUrl(TaskType taskType, String regionCode, String countryCode, String stateCode, String districtName){
        switch (taskType){
            case WORLD_NEWS:
                return root_url + "world/news/";
            case GLOBAL_DATA:
                return root_url + "world/global/";
            case REGION_DATA_LIST:
                return root_url + "world/region/data/";
            case REGION_DATA:
                return root_url + "world/region/data/"+regionCode+"/";
            case REGION_COUNTRY_DATA_LIST:
                return root_url + "world/region/data/"+regionCode+"/countries/";
            case COUNTRY_DATA_LIST:
                return root_url + "world/country/data/";
            case COUNTRY_DATA:
                return root_url + "world/country/data/"+countryCode+"/";
            case COUNTRY_DATA_TIME_SERIES:
                return root_url + "world/country/data/"+countryCode+"/timeseries/";
            case INDIA_NEWS:
                return root_url + "india/news/";
            case INDIA_DATA:
                return root_url + "india/";
            case INDIA_DATA_TIME_SERIES:
                return root_url + "india/timeseries/";
            case STATE_DATA_LIST:
                return root_url + "state/data/";
            case STATE_DATA:
                return root_url + "state/data/"+stateCode+"/";
            case STATE_DATA_TIME_SERIES:
                return root_url + "state/data/"+stateCode+"/timeseries/";
            case DISTRICT_DATA_LIST:
                return root_url + "state/data/"+stateCode+"/districts/";
            case DISTRICT_DATA:
                return root_url + "state/data/"+stateCode+"/districts/"+districtName+"/";
            case DISTRICT_DATA_TIME_SERIES:
                return root_url + "state/data/"+stateCode+"/districts/"+districtName+"/timeseries/";
        }
        return root_url;
    }

    private void onTaskSuccess(TaskType taskType, String response){
        switch (taskType){
            case WORLD_NEWS:
            case INDIA_NEWS:
                apiManagerInterface.onNewsFetchComplete(taskType, true, parseJsonToNews(response), null);
                break;

            case GLOBAL_DATA:
            case REGION_DATA_LIST:
            case REGION_DATA:
            case REGION_COUNTRY_DATA_LIST:
            case COUNTRY_DATA_LIST:
            case COUNTRY_DATA:
            case COUNTRY_DATA_TIME_SERIES:
            case INDIA_DATA:
            case INDIA_DATA_TIME_SERIES:
            case STATE_DATA_LIST:
            case STATE_DATA:
            case STATE_DATA_TIME_SERIES:
            case DISTRICT_DATA_LIST:
            case DISTRICT_DATA:
            case DISTRICT_DATA_TIME_SERIES:
                apiManagerInterface.onStatsDataFetchComplete(taskType, true, parseJsonToStats(response), null);
                break;
        }
    }

    private void onTaskFailure(TaskType taskType, String error){
        if (taskType == TaskType.INDIA_NEWS || taskType == TaskType.WORLD_NEWS){
            apiManagerInterface.onNewsFetchComplete(taskType, false, null, error);
        } else {
            apiManagerInterface.onStatsDataFetchComplete(taskType, false, null, error);
        }
    }

    private ArrayList<News> parseJsonToNews(String response){
        ArrayList<News> newsList = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(response);
            for(int i=0;i<jsonArray.length();i++)
                newsList.add(new News(jsonArray.getJSONObject(i)));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return newsList;
    }

    private ArrayList<CovidStats> parseJsonToStats(String response){
        ArrayList<CovidStats> statsData = new ArrayList<>();
            try {
                if(response.charAt(0) == '[') {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        statsData.add(new CovidStats(jsonArray.getJSONObject(i)));
                    }
                } else {
                    JSONObject jsonObject = new JSONObject(response);
                    statsData.add(new CovidStats(jsonObject));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        return statsData;
    }

}
