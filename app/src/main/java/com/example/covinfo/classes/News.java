package com.example.covinfo.classes;

import org.json.JSONException;
import org.json.JSONObject;

import java.time.Instant;

public class News {

    private String sourceName;
    private String authors;
    private String title;
    private String description;
    private String newsUrl;
    private String newsImageUrl;
    private String publishedTime;
    private String content;

    public News(String sourceName, String authors, String title, String description, String newsUrl, String newsImageUrl, String publishedTime, String content) {
        this.sourceName = sourceName;
        this.authors = authors;
        this.title = title;
        this.description = description;
        this.newsUrl = newsUrl;
        this.newsImageUrl = newsImageUrl;
        this.publishedTime = publishedTime;
        this.content = content;
    }

    public News(JSONObject jsonObject) {
        if (jsonObject != null) {
            try {
                sourceName = jsonObject.getString("source_name");
                authors = jsonObject.getString("authors");
                title = jsonObject.getString("title");
                description = jsonObject.getString("description");
                newsUrl = jsonObject.getString("news_url");
                newsImageUrl = jsonObject.getString("news_image_url");
                content = jsonObject.getString("content");
                publishedTime = jsonObject.getString("published_time");
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
