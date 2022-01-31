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

import java.util.ArrayList;

public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.ViewHolder> {

    ArrayList<News> newsList;

    public NewsListAdapter(ArrayList<News> newsList) {
        this.newsList = newsList;
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_list_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsListAdapter.ViewHolder holder, int position) {
        News newsItem = newsList.get(position);
        holder.title.setText(newsItem.getTitle());
        holder.description.setText(newsItem.getDescription());
        holder.source.setText(newsItem.getSourceName());
        Picasso.get().load(Uri.parse(newsItem.getNewsImageUrl())).into(holder.imgNews);
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

}
