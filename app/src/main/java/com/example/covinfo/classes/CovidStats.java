package com.example.covinfo.classes;

import org.json.JSONObject;

import java.util.Date;

public class CovidStats {

    private Date dateOfStat;
    private int totalConfirmedCases;
    private int dailyConfirmedCases;
    private int totalDeceasedCases;
    private int dailyDeceasedCases;

    public CovidStats(){

    }

    public CovidStats(Date dateOfStat, int totalConfirmedCases, int dailyConfirmedCases, int totalDeceasedCases, int dailyDeceasedCases) {
        this.dateOfStat = dateOfStat;
        this.totalConfirmedCases = totalConfirmedCases;
        this.dailyConfirmedCases = dailyConfirmedCases;
        this.totalDeceasedCases = totalDeceasedCases;
        this.dailyDeceasedCases = dailyDeceasedCases;
    }

    public CovidStats(Date dateOfStat, int totalConfirmedCases, int totalDeceasedCases) {
        this.dateOfStat = dateOfStat;
        this.totalConfirmedCases = totalConfirmedCases;
        this.totalDeceasedCases = totalDeceasedCases;
    }

    public CovidStats(JSONObject jsonObject){
        if(jsonObject!=null){

        }
    }

    public Date getDateOfStat() {
        return dateOfStat;
    }

    public void setDateOfStat(Date dateOfStat) {
        this.dateOfStat = dateOfStat;
    }

    public int getTotalConfirmedCases() {
        return totalConfirmedCases;
    }

    public void setTotalConfirmedCases(int totalConfirmedCases) {
        this.totalConfirmedCases = totalConfirmedCases;
    }

    public int getDailyConfirmedCases() {
        return dailyConfirmedCases;
    }

    public void setDailyConfirmedCases(int dailyConfirmedCases) {
        this.dailyConfirmedCases = dailyConfirmedCases;
    }

    public int getTotalDeceasedCases() {
        return totalDeceasedCases;
    }

    public void setTotalDeceasedCases(int totalDeceasedCases) {
        this.totalDeceasedCases = totalDeceasedCases;
    }

    public int getDailyDeceasedCases() {
        return dailyDeceasedCases;
    }

    public void setDailyDeceasedCases(int dailyDeceasedCases) {
        this.dailyDeceasedCases = dailyDeceasedCases;
    }
}
