package com.example.covinfo.classes.about;

import org.json.JSONException;
import org.json.JSONObject;

public class AboutApp {

    private static final String KEY_APP_PLAY_STORE_LINK = "play_store_link";
    private static final String KEY_ABOUT_APP = "about_content";

    private String appPlayStoreLink;
    private String aboutAppContent;

    public AboutApp(){

    }

    public AboutApp(JSONObject jsonObject){
        if(jsonObject!=null){
            try {
                appPlayStoreLink = jsonObject.getString(KEY_APP_PLAY_STORE_LINK);
                aboutAppContent = jsonObject.getString(KEY_ABOUT_APP);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public String getAppPlayStoreLink(){
        return appPlayStoreLink;
    }

    public String getAboutAppContent(){
        return aboutAppContent;
    }

}
