package com.example.covinfo.classes;

import org.json.JSONException;
import org.json.JSONObject;

public class AboutApp {

    private String appLink;

    public AboutApp(){

    }

    public AboutApp(JSONObject jsonObject){
        if(jsonObject!=null){
            try {
                appLink = jsonObject.getString("app_link");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public String getAppLink(){
        return appLink;
    }

}
