package com.example.covinfo.classes;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DistrictStats extends StateStats {

    private String districtName;

    public DistrictStats(Date dateOfStat, int totalConfirmedCases, int totalDeceasedCases, int totalRecoveredCases, String stateCode, String stateName, int currentActiveCases, String districtName) {
        super(dateOfStat, totalConfirmedCases, totalDeceasedCases, totalRecoveredCases, stateCode, stateName, currentActiveCases);
        this.districtName = districtName;
    }

    public DistrictStats(String stateCode, String stateName, JSONObject jsonObject){
        if(jsonObject!=null){
            try {
                setStateCode(stateCode);
                setStateName(stateName);
                setDistrictName(jsonObject.getString("name"));
                setTotalConfirmedCases(jsonObject.getInt("confirmed"));
                setTotalRecoveredCases(jsonObject.getInt("recovered"));
                setTotalDeceasedCases(jsonObject.getInt("deceased"));
                setCurrentActiveCases(jsonObject.getInt("active"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public DistrictStats(String stateCode, String stateName, String districtName, JSONObject jsonObject){
        if(jsonObject!=null){
            try {
                setStateCode(stateCode);
                setStateName(stateName);
                setDistrictName(districtName);
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

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }
}
