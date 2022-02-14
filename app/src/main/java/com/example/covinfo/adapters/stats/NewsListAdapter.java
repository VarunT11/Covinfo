package com.example.covinfo.adapters.stats;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.covinfo.R;
import com.example.covinfo.classes.stats.News;
import com.example.covinfo.utils.DateTimeUtil;

import java.util.ArrayList;

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

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tvNewsItemTitle);
            description = itemView.findViewById(R.id.tvNewsItemDescription);
            source = itemView.findViewById(R.id.tvNewsItemSource);
            timePublished = itemView.findViewById(R.id.tvNewsItemTimePublished);
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
        holder.timePublished.setText(DateTimeUtil.getDisplayTime(newsItem.getPublishedTime()));

        holder.itemView.setOnClickListener(v -> adapterInterface.onItemClickListener(position));
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

}
