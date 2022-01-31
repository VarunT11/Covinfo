package com.example.covinfo.classes;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class IndiaStats extends CovidStats {

    private int totalRecoveredCases;
    private int dailyRecoveredCases;

    public IndiaStats(Date dateOfStat, int totalConfirmedCases, int dailyConfirmedCases, int totalDeceasedCases, int dailyDeceasedCases, int totalRecoveredCases, int dailyRecoveredCases) {
        super(dateOfStat, totalConfirmedCases, dailyConfirmedCases, totalDeceasedCases, dailyDeceasedCases);
        this.totalRecoveredCases = totalRecoveredCases;
        this.dailyRecoveredCases = dailyRecoveredCases;
    }

    public IndiaStats(Date dateOfStat, int totalConfirmedCases, int totalDeceasedCases, int totalRecoveredCases) {
        super(dateOfStat, totalConfirmedCases, totalDeceasedCases);
        this.totalRecoveredCases = totalRecoveredCases;
    }

    public IndiaStats(){
        super();
    }

    public IndiaStats(JSONObject jsonObject) {
        super(null);
        if(jsonObject!=null){
            try {
                Date date = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(jsonObject.getString("date"));
                setTotalConfirmedCases(jsonObject.getInt("total_confirmed"));
                setTotalRecoveredCases(jsonObject.getInt("total_recovered"));
                setTotalDeceasedCases(jsonObject.getInt("total_deceased"));
                setDailyConfirmedCases(jsonObject.getInt("daily_confirmed"));
                setDailyRecoveredCases(jsonObject.getInt("daily_recovered"));
                setDailyDeceasedCases(jsonObject.getInt("daily_deceased"));
            } catch (JSONException | ParseException e) {
                e.printStackTrace();
            }
        }
    }

    public int getTotalRecoveredCases() {
        return totalRecoveredCases;
    }

    public void setTotalRecoveredCases(int totalRecoveredCases) {
        this.totalRecoveredCases = totalRecoveredCases;
    }

    public int getDailyRecoveredCases() {
        return dailyRecoveredCases;
    }

    public void setDailyRecoveredCases(int dailyRecoveredCases) {
        this.dailyRecoveredCases = dailyRecoveredCases;
    }
}
