package com.example.covinfo.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateTimeUtil {

    public static String getDisplayTime(String timeStr){
        String isoDatePattern = "yyyy-MM-dd'T'HH:mm:ss'Z'";
        SimpleDateFormat dateFormat = new SimpleDateFormat(isoDatePattern, Locale.ENGLISH);
        try {
            Date dateOfNews = dateFormat.parse(timeStr);
            if (dateOfNews != null) {
                long timeDiff = (new Date().getTime() - dateOfNews.getTime()) / (1000 * 60);
                if (timeDiff <= 60) {
                    return timeDiff + " mins ago";
                }
                timeDiff = timeDiff / 60;
                if (timeDiff <= 24) {
                    return timeDiff + " hours ago";
                }
                timeDiff = timeDiff / 24;
                if (timeDiff == 1)
                    return timeDiff + " day ago";
                return timeDiff + " days ago";
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return timeStr;
    }

}
