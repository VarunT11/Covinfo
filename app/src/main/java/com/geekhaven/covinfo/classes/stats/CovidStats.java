package com.geekhaven.covinfo.classes.stats;

import com.geekhaven.covinfo.enums.RegionType;

import org.json.JSONException;
import org.json.JSONObject;

public class CovidStats {

    private static final String KEY_REGION_TYPE = "region_type";
    private static final String KEY_REGION_CODE_WHO = "region_code_who";
    private static final String KEY_REGION_NAME_WHO = "region_name_who";
    private static final String KEY_COUNTRY_CODE = "country_code";
    private static final String KEY_COUNTRY_NAME = "country_name";
    private static final String KEY_STATE_CODE = "state_code";
    private static final String KEY_STATE_NAME = "state_name";
    private static final String KEY_DISTRICT_NAME = "district_name";
    private static final String KEY_DATE_OF_STAT = "date_of_stat";
    private static final String KEY_TOTAL_CONFIRMED = "total_confirmed";
    private static final String KEY_DAILY_CONFIRMED = "daily_confirmed";
    private static final String KEY_TOTAL_RECOVERED = "total_recovered";
    private static final String KEY_DAILY_RECOVERED = "daily_recovered";
    private static final String KEY_TOTAL_DECEASED = "total_deceased";
    private static final String KEY_DAILY_DECEASED = "daily_deceased";
    private static final String KEY_TOTAL_ACTIVE = "total_active";
    private static final String KEY_DAILY_ACTIVE = "daily_active";

    private RegionType regionType;

    private String regionCodeWHO;
    private String regionNameWHO;
    private String countryCode;
    private String countryName;
    private String stateCode;
    private String stateName;
    private String districtName;

    private String dateOfStat;

    private int totalConfirmed;
    private int dailyConfirmed;
    private int totalRecovered;
    private int dailyRecovered;
    private int totalDeceased;
    private int dailyDeceased;
    private int totalActive;
    private int dailyActive;

    public CovidStats(){

    }

    public CovidStats(JSONObject jsonObject) {
        if(jsonObject==null)
            return;
        try {
            if(jsonObject.has(KEY_REGION_TYPE))
                regionType = RegionType.valueOf(jsonObject.getString(KEY_REGION_TYPE));
            if (jsonObject.has(KEY_REGION_CODE_WHO))
                regionCodeWHO = jsonObject.getString(KEY_REGION_CODE_WHO);
            if (jsonObject.has(KEY_REGION_NAME_WHO))
                regionNameWHO = jsonObject.getString(KEY_REGION_NAME_WHO);
            if (jsonObject.has(KEY_COUNTRY_CODE))
                countryCode = jsonObject.getString(KEY_COUNTRY_CODE);
            if (jsonObject.has(KEY_COUNTRY_NAME))
                countryName = jsonObject.getString(KEY_COUNTRY_NAME);
            if (jsonObject.has(KEY_STATE_CODE))
                stateCode = jsonObject.getString(KEY_STATE_CODE);
            if (jsonObject.has(KEY_STATE_NAME))
                stateName = jsonObject.getString(KEY_STATE_NAME);
            if (jsonObject.has(KEY_DISTRICT_NAME))
                districtName = jsonObject.getString(KEY_DISTRICT_NAME);

            if (jsonObject.has(KEY_DATE_OF_STAT))
                dateOfStat = jsonObject.getString(KEY_DATE_OF_STAT);

            if(jsonObject.has(KEY_TOTAL_CONFIRMED))
                totalConfirmed = jsonObject.getInt(KEY_TOTAL_CONFIRMED);
            if(jsonObject.has(KEY_DAILY_CONFIRMED))
                dailyConfirmed = jsonObject.getInt(KEY_DAILY_CONFIRMED);
            if(jsonObject.has(KEY_TOTAL_RECOVERED))
                totalRecovered = jsonObject.getInt(KEY_TOTAL_RECOVERED);
            if(jsonObject.has(KEY_DAILY_RECOVERED))
                dailyRecovered = jsonObject.getInt(KEY_DAILY_RECOVERED);
            if(jsonObject.has(KEY_TOTAL_DECEASED))
                totalDeceased = jsonObject.getInt(KEY_TOTAL_DECEASED);
            if(jsonObject.has(KEY_DAILY_DECEASED))
                dailyDeceased = jsonObject.getInt(KEY_DAILY_DECEASED);
            if(jsonObject.has(KEY_TOTAL_ACTIVE))
                totalActive = jsonObject.getInt(KEY_TOTAL_ACTIVE);
            if(jsonObject.has(KEY_DAILY_ACTIVE))
                dailyActive = jsonObject.getInt(KEY_DAILY_ACTIVE);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public RegionType getRegionType() {
        return regionType;
    }

    public String getRegionCodeWHO() {
        return regionCodeWHO;
    }

    public String getRegionNameWHO() {
        return regionNameWHO;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public String getCountryName() {
        return countryName;
    }

    public String getStateCode() {
        return stateCode;
    }

    public String getStateName() {
        return stateName;
    }

    public String getDistrictName() {
        return districtName;
    }

    public String getDateOfStat() {
        return dateOfStat;
    }

    public int getTotalConfirmed() {
        return totalConfirmed;
    }

    public int getDailyConfirmed() {
        return dailyConfirmed;
    }

    public int getTotalRecovered() {
        return totalRecovered;
    }

    public int getDailyRecovered() {
        return dailyRecovered;
    }

    public int getTotalDeceased() {
        return totalDeceased;
    }

    public int getDailyDeceased() {
        return dailyDeceased;
    }

    public int getTotalActive() {
        return totalActive;
    }

    public int getDailyActive() {
        return dailyActive;
    }
}
