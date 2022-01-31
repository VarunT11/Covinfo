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
    private long publishedTime;
    private String content;

    public News(String sourceName, String authors, String title, String description, String newsUrl, String newsImageUrl, long publishedTime, String content) {
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

                String publishedTimeString = jsonObject.getString("published_time");
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    Instant instant = Instant.parse(publishedTimeString);
                    publishedTime = instant.toEpochMilli();
                } else {

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNewsUrl() {
        return newsUrl;
    }

    public void setNewsUrl(String newsUrl) {
        this.newsUrl = newsUrl;
    }

    public String getNewsImageUrl() {
        return newsImageUrl;
    }

    public void setNewsImageUrl(String newsImageUrl) {
        this.newsImageUrl = newsImageUrl;
    }

    public long getPublishedTime() {
        return publishedTime;
    }

    public void setPublishedTime(long publishedTime) {
        this.publishedTime = publishedTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
