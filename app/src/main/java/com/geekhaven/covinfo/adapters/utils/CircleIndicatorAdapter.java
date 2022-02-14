package com.geekhaven.covinfo.adapters.utils;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.recyclerview.widget.RecyclerView;

import com.geekhaven.covinfo.R;

public class CircleIndicatorAdapter extends RecyclerView.Adapter<CircleIndicatorAdapter.ViewHolder> {

    public interface AdapterInterface {
        void onItemClickListener(int position);
    }

    private final int LIST_SIZE;
    private final AppCompatActivity activity;
    private final int currentItem;
    private final AdapterInterface adapterInterface;

    public CircleIndicatorAdapter(AppCompatActivity activity, int size, int currentItem, AdapterInterface adapterInterface) {
        LIST_SIZE = size;
        this.activity = activity;
        this.currentItem = currentItem;
        this.adapterInterface = adapterInterface;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgCircle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgCircle = itemView.findViewById(R.id.imgDotItem);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.circle_indicator_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        for (int i = 0; i < LIST_SIZE; i++) {
            @DrawableRes int drawableId;
            int diff = Math.abs(position - currentItem);
            switch (diff) {
                case 0:
                    drawableId = R.drawable.dot_selected;
                    break;
                case 1:
                    drawableId = R.drawable.dot_unselected_large;
                    break;
                case 2:
                    drawableId = R.drawable.dot_unselected_medium;
                    break;
                case 3:
                    drawableId = R.drawable.dot_unselected_regular;
                    break;
                default:
                    drawableId = R.drawable.dot_unselected_small;
                    break;
            }
            holder.imgCircle.setImageDrawable(AppCompatResources.getDrawable(activity, drawableId));
            holder.imgCircle.setOnClickListener(v -> adapterInterface.onItemClickListener(position));
        }
    }

    @Override
    public int getItemCount() {
        return LIST_SIZE;
    }

}
