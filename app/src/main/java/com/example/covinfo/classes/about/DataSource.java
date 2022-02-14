package com.example.covinfo.classes.about;

import org.json.JSONException;
import org.json.JSONObject;

public class DataSource {

    private static final String KEY_SOURCE_NAME = "source_name";
    private static final String KEY_SOURCE_DESCRIPTION = "source_description";
    private static final String KEY_SOURCE_URL = "source_url";

    private String sourceName;
    private String sourceDescription;
    private String sourceUrl;

    public DataSource(JSONObject jsonObject){
        if(jsonObject!=null){
            try {
                sourceName = jsonObject.getString(KEY_SOURCE_NAME);
                sourceDescription = jsonObject.getString(KEY_SOURCE_DESCRIPTION);
                sourceUrl = jsonObject.getString(KEY_SOURCE_URL);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public String getSourceName() {
        return sourceName;
    }

    public String getSourceDescription() {
        return sourceDescription;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }
}
