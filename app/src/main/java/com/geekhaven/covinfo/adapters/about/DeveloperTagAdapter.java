package com.geekhaven.covinfo.adapters.about;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.geekhaven.covinfo.R;

import java.util.ArrayList;

public class DeveloperTagAdapter extends RecyclerView.Adapter<DeveloperTagAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvTag;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTag = itemView.findViewById(R.id.tvTagName);
        }
    }

    private final ArrayList<String> tagList;

    public DeveloperTagAdapter(ArrayList<String> tagList){
        this.tagList = tagList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.developer_tag_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvTag.setText(tagList.get(position));
    }

    @Override
    public int getItemCount() {
        return tagList.size();
    }

}
