package com.example.covinfo.adapters.about;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.covinfo.R;
import com.example.covinfo.classes.about.DataSource;

import java.util.ArrayList;

public class SourceListAdapter extends RecyclerView.Adapter<SourceListAdapter.ViewHolder>{

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvDescription;
        ImageButton btnOpenLink;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvSourceItemName);
            tvDescription = itemView.findViewById(R.id.tvSourceItemDescription);
            btnOpenLink = itemView.findViewById(R.id.btnSourceItemOpenLink);
        }
    }

    private final Context context;
    private final ArrayList<DataSource> dataSourceList;

    public SourceListAdapter(Context context, ArrayList<DataSource> dataSourceList){
        this.context = context;
        this.dataSourceList = dataSourceList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.data_source_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DataSource dataSource = dataSourceList.get(position);
        holder.tvName.setText(dataSource.getSourceName());
        holder.tvDescription.setText(dataSource.getSourceDescription());
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(dataSource.getSourceUrl()));
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return dataSourceList.size();
    }

}
