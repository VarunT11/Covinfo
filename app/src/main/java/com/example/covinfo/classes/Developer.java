package com.example.covinfo.classes;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

public class Developer {

    private String name;
    private ArrayList<String> tags;
    private String linkedInId, gitHubId, behanceId, mailId;
    private String imageUrl;

    public Developer(JSONObject jsonObject){
        if(jsonObject!=null){
            try {
                name = jsonObject.getString("name");

                String tagList = jsonObject.getString("tags");
                String[] tagArray = tagList.split(";");
                tags = new ArrayList<>(Arrays.asList(tagArray));

                linkedInId = jsonObject.getString("linkedin");
                if(linkedInId.equals("NULL"))
                    linkedInId = "";

                behanceId = jsonObject.getString("behance");
                if(behanceId.equals("NULL"))
                    behanceId = "";

                gitHubId = jsonObject.getString("github");
                if(gitHubId.equals("NULL"))
                    gitHubId = "";

                mailId = jsonObject.getString("mail");
                if(mailId.equals("NULL"))
                    mailId = "";

                imageUrl = jsonObject.getString("image_url");
                if(imageUrl.equals("NULL"))
                    imageUrl = "";

            } catch (JSONException e){
                e.printStackTrace();
            }
        }
    }

    public String getName() {
        return name;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public String getLinkedInId() {
        return linkedInId;
    }

    public String getGitHubId() {
        return gitHubId;
    }

    public String getBehanceId() {
        return behanceId;
    }

    public String getMailId() {
        return mailId;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
