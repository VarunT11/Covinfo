package com.example.covinfo.classes;

import org.json.JSONException;
import org.json.JSONObject;

public class RegionInfo {

    private String code;
    private String name;

    public RegionInfo(JSONObject jsonObject){
        if(jsonObject!=null){
            try {
                code = jsonObject.getString("region_code_who");
                name = jsonObject.getString("region_name_who");
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
