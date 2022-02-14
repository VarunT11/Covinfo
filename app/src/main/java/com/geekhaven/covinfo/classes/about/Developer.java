package com.geekhaven.covinfo.classes.about;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

public class Developer {

    private static final String KEY_NAME = "name";
    private static final String KEY_TAGS = "tags";
    private static final String KEY_LINKEDIN = "linkedin";
    private static final String KEY_BEHANCE = "behance";
    private static final String KEY_GITHUB = "github";
    private static final String KEY_MAIL = "mail";
    private static final String KEY_IMAGE_URL = "image_url";

    private static final String NULL_CONTENT = "NULL";

    private String name;
    private ArrayList<String> tags;
    private String linkedInId, gitHubId, behanceId, mailId;
    private String imageUrl;

    public Developer(JSONObject jsonObject){
        if(jsonObject!=null){
            try {
                name = jsonObject.getString(KEY_NAME);

                String tagList = jsonObject.getString(KEY_TAGS);
                String[] tagArray = tagList.split(";");
                tags = new ArrayList<>(Arrays.asList(tagArray));

                linkedInId = jsonObject.getString(KEY_LINKEDIN);
                if(linkedInId.equals(NULL_CONTENT))
                    linkedInId = "";

                behanceId = jsonObject.getString(KEY_BEHANCE);
                if(behanceId.equals(NULL_CONTENT))
                    behanceId = "";

                gitHubId = jsonObject.getString(KEY_GITHUB);
                if(gitHubId.equals(NULL_CONTENT))
                    gitHubId = "";

                mailId = jsonObject.getString(KEY_MAIL);
                if(mailId.equals(NULL_CONTENT))
                    mailId = "";

                imageUrl = jsonObject.getString(KEY_IMAGE_URL);
                if(imageUrl.equals(NULL_CONTENT))
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
