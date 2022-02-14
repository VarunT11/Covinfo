package com.geekhaven.covinfo.classes.stats;

import org.json.JSONException;
import org.json.JSONObject;

public class News {

    private static final String KEY_SOURCE_NAME = "source_name";
    private static final String KEY_AUTHORS = "authors";
    private static final String KEY_TITLE = "title";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_NEWS_URL = "news_url";
    private static final String KEY_NEWS_IMAGE_URL = "news_image_url";
    private static final String KEY_CONTENT = "content";
    private static final String KEy_PUBLISHED_TIME = "published_time";

    private String sourceName;
    private String authors;
    private String title;
    private String description;
    private String newsUrl;
    private String newsImageUrl;
    private String publishedTime;
    private String content;

    public News(JSONObject jsonObject) {
        if (jsonObject != null) {
            try {
                sourceName = jsonObject.getString(KEY_SOURCE_NAME);
                authors = jsonObject.getString(KEY_AUTHORS);
                title = jsonObject.getString(KEY_TITLE);
                description = jsonObject.getString(KEY_DESCRIPTION);
                newsUrl = jsonObject.getString(KEY_NEWS_URL);
                newsImageUrl = jsonObject.getString(KEY_NEWS_IMAGE_URL);
                content = jsonObject.getString(KEY_CONTENT);
                publishedTime = jsonObject.getString(KEy_PUBLISHED_TIME);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public String getSourceName() {
        return sourceName;
    }

    public String getAuthors() {
        return authors;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getNewsUrl() {
        return newsUrl;
    }

    public String getNewsImageUrl() {
        return newsImageUrl;
    }

    public String getPublishedTime() {
        return publishedTime;
    }

    public String getContent() {
        return content;
    }
}
