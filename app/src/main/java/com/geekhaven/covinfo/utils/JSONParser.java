package com.geekhaven.covinfo.utils;

import com.geekhaven.covinfo.classes.about.AboutApp;
import com.geekhaven.covinfo.classes.about.DataSource;
import com.geekhaven.covinfo.classes.stats.CovidStats;
import com.geekhaven.covinfo.classes.about.Developer;
import com.geekhaven.covinfo.classes.stats.News;
import com.geekhaven.covinfo.classes.stats.RegionInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JSONParser {

    public JSONParser() {

    }

    public AboutApp parseJSONToAboutApp(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            return new AboutApp(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
            return new AboutApp();
        }
    }

    public ArrayList<Developer> parseJSONToDeveloperList(String response) {
        ArrayList<Developer> developerList = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(response);
            for (int i = 0; i < jsonArray.length(); i++)
                developerList.add(new Developer(jsonArray.getJSONObject(i)));
        } catch (JSONException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
        return developerList;
    }

    public ArrayList<DataSource> parseJSONToDataSourceList(String response) {
        ArrayList<DataSource> dataSources = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(response);
            for (int i = 0; i < jsonArray.length(); i++)
                dataSources.add(new DataSource(jsonArray.getJSONObject(i)));
        } catch (JSONException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
        return dataSources;
    }

    public ArrayList<News> parseJSONToNewsList(String response) {
        ArrayList<News> newsList = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(response);
            for (int i = 0; i < jsonArray.length(); i++)
                newsList.add(new News(jsonArray.getJSONObject(i)));
        } catch (JSONException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
        return newsList;
    }

    public ArrayList<RegionInfo> parseJSONToInfoList(String response) {
        ArrayList<RegionInfo> infoList = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(response);
            for (int i = 0; i < jsonArray.length(); i++)
                infoList.add(new RegionInfo(jsonArray.getJSONObject(i)));
        } catch (JSONException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
        return infoList;
    }

    public ArrayList<CovidStats> parseJSONToStats(String response) {
        ArrayList<CovidStats> statsData = new ArrayList<>();
        try {
            if (response.charAt(0) == '[') {
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
            new ArrayList<>();
        }
        return statsData;
    }

}
