package com.example.covinfo.utils;

import com.example.covinfo.classes.AboutApp;
import com.example.covinfo.classes.CovidStats;
import com.example.covinfo.classes.Developer;
import com.example.covinfo.classes.News;
import com.example.covinfo.classes.RegionInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JSONParser {

    public JSONParser() {

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

    public AboutApp parseJSONToAboutApp(String response){
        try {
            JSONObject jsonObject = new JSONObject(response);
            return new AboutApp(jsonObject);
        } catch (JSONException e){
            e.printStackTrace();
            return new AboutApp();
        }
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
