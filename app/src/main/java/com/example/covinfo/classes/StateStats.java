package com.example.covinfo.classes;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class StateStats extends IndiaStats {

    private String stateCode;
    private String stateName;

    private int currentActiveCases;

    public StateStats(){

    }

    public StateStats(Date dateOfStat, int totalConfirmedCases, int totalDeceasedCases, int totalRecoveredCases, String stateCode, String stateName, int currentActiveCases) {
        super(dateOfStat, totalConfirmedCases, totalDeceasedCases, totalRecoveredCases);
        this.stateCode = stateCode;
        this.stateName = stateName;
        this.currentActiveCases = currentActiveCases;
    }

    public StateStats(JSONObject jsonObject){
        super(null);
        if(jsonObject!=null){
            try {
                setStateCode(jsonObject.getString("code"));
                setStateName(jsonObject.getString("name"));
                setTotalConfirmedCases(jsonObject.getInt("confirmed"));
                setTotalRecoveredCases(jsonObject.getInt("recovered"));
                setTotalDeceasedCases(jsonObject.getInt("deceased"));
                setCurrentActiveCases(jsonObject.getInt("active"));

                String dateOfStat = jsonObject.getString("last_updated").substring(0,10);
                setDateOfStat(new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(dateOfStat));
            } catch (JSONException | ParseException e) {
                e.printStackTrace();
            }
        }
    }

    public StateStats(String code, String name, JSONObject jsonObject){
        if(jsonObject!=null){
            try {
                setStateCode(code);
                setStateName(name);
                setTotalConfirmedCases(jsonObject.getInt("confirmed"));
                setTotalRecoveredCases(jsonObject.getInt("recovered"));
                setTotalDeceasedCases(jsonObject.getInt("deceased"));
                setCurrentActiveCases(jsonObject.getInt("active"));
                setDateOfStat(new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(jsonObject.getString("date")));
            } catch (JSONException | ParseException e) {
                e.printStackTrace();
            }
        }
    }

    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public int getCurrentActiveCases() {
        return currentActiveCases;
    }

    public void setCurrentActiveCases(int currentActiveCases) {
        this.currentActiveCases = currentActiveCases;
    }
}
