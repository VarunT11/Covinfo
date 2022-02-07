package com.example.covinfo.adapters;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.covinfo.R;
import com.example.covinfo.classes.News;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.ViewHolder> {

    public interface NewsItemAdapterInterface{
        void onItemClickListener(int position);
    }

    private final ArrayList<News> newsList;
    private final NewsItemAdapterInterface adapterInterface;

    public NewsListAdapter(ArrayList<News> newsList, NewsItemAdapterInterface adapterInterface) {
        this.newsList = newsList;
        this.adapterInterface = adapterInterface;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, description, source, timePublished;
        ImageView imgNews;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tvNewsItemTitle);
            description = itemView.findViewById(R.id.tvNewsItemDescription);
            source = itemView.findViewById(R.id.tvNewsItemSource);
            timePublished = itemView.findViewById(R.id.tvNewsItemTimePublished);
            imgNews = itemView.findViewById(R.id.imgNewsItem);
        }
    }

    @NonNull
    @Override
    public NewsListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsListAdapter.ViewHolder holder, int position) {
        News newsItem = newsList.get(position);
        holder.title.setText(newsItem.getTitle());
        holder.description.setText(newsItem.getDescription());
        holder.source.setText(newsItem.getSourceName());
        holder.timePublished.setText(getDisplayTime(newsItem.getPublishedTime()));

        holder.itemView.setOnClickListener(v -> adapterInterface.onItemClickListener(position));
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    private String getDisplayTime(String timeStr){
        String isoDatePattern = "yyyy-MM-dd'T'HH:mm:ss'Z'";
        SimpleDateFormat dateFormat = new SimpleDateFormat(isoDatePattern, Locale.ENGLISH);
        try {
            Date dateOfNews = dateFormat.parse(timeStr);
            if(dateOfNews!=null){
                long timeDiff = (new Date().getTime() - dateOfNews.getTime())/(1000*60);
                if(timeDiff <= 60){
                    return timeDiff + " mins ago";
                }
                timeDiff = timeDiff/60;
                if(timeDiff <= 24){
                    return timeDiff + " hours ago";
                }
                timeDiff = timeDiff/24;
                if (timeDiff == 1)
                    return timeDiff + " day ago";
                return  timeDiff + " days ago";
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return timeStr;
    }

}
