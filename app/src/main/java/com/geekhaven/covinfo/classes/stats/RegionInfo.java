package com.geekhaven.covinfo.classes.stats;

import org.json.JSONException;
import org.json.JSONObject;

public class RegionInfo {

    private static final String KEY_REGION_CODE = "region_code_who";
    private static final String KEY_REGION_NAME = "region_name_who";

    private String code;
    private String name;

    public RegionInfo(JSONObject jsonObject){
        if(jsonObject!=null){
            try {
                code = jsonObject.getString(KEY_REGION_CODE);
                name = jsonObject.getString(KEY_REGION_NAME);
            } catch (JSONException e){
                e.printStackTrace();
            }
        }
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
