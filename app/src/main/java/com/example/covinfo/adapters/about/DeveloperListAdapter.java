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
import androidx.appcompat.content.res.AppCompatResources;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.covinfo.R;
import com.example.covinfo.classes.about.Developer;
import com.google.android.material.imageview.ShapeableImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DeveloperListAdapter extends RecyclerView.Adapter<DeveloperListAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder {
        View view1, view2;
        TextView tvName1, tvName2;
        RecyclerView rcvTag1, rcvTag2;
        ImageButton btnLinkedIn1, btnLinkedIn2, btnBehance1, btnBehance2, btnGitHub1, btnGitHub2, btnMail1, btnMail2;
        ShapeableImageView imgDeveloper1, imgDeveloper2;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            view1 = itemView.findViewById(R.id.developerItemLayout1);
            view2 = itemView.findViewById(R.id.developerItemLayout2);

            tvName1 = itemView.findViewById(R.id.tvName1);
            tvName2 = itemView.findViewById(R.id.tvName2);

            rcvTag1 = itemView.findViewById(R.id.rcvTagList1);
            rcvTag2 = itemView.findViewById(R.id.rcvTagList2);

            btnLinkedIn1 = itemView.findViewById(R.id.btnLinkedIn1);
            btnLinkedIn2 = itemView.findViewById(R.id.btnLinkedIn2);

            btnBehance1 = itemView.findViewById(R.id.btnBehance1);
            btnBehance2 = itemView.findViewById(R.id.btnBehance2);

            btnGitHub1 = itemView.findViewById(R.id.btnGitHub1);
            btnGitHub2 = itemView.findViewById(R.id.btnGitHub2);

            btnMail1 = itemView.findViewById(R.id.btnMail1);
            btnMail2 = itemView.findViewById(R.id.btnMail2);

            imgDeveloper1 = itemView.findViewById(R.id.imgDeveloper1);
            imgDeveloper2 = itemView.findViewById(R.id.imgDeveloper2);
        }
    }

    private final Context context;
    private final ArrayList<Developer> developerList;

    public DeveloperListAdapter(Context context, ArrayList<Developer> developerList) {
        this.context = context;
        this.developerList = developerList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.developer_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Developer developer = developerList.get(position);

        if (position % 2 == 0) {
            holder.view1.setVisibility(View.VISIBLE);
            holder.view2.setVisibility(View.GONE);

            holder.tvName1.setText(developer.getName());

            LinearLayoutManager layoutManager = new LinearLayoutManager(context);
            layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            holder.rcvTag1.setLayoutManager(layoutManager);

            DeveloperTagAdapter tagAdapter = new DeveloperTagAdapter(developer.getTags());
            holder.rcvTag1.setAdapter(tagAdapter);

            setupSocialBtn(holder.btnLinkedIn1, developer.getLinkedInId());
            setupSocialBtn(holder.btnBehance1, developer.getBehanceId());
            setupSocialBtn(holder.btnGitHub1, developer.getGitHubId());
            setupSocialBtn(holder.btnMail1, developer.getMailId());

            if (developer.getImageUrl().equals("")) {
                holder.imgDeveloper1.setImageDrawable(AppCompatResources.getDrawable(context, R.drawable.ic_developer));
            } else {
                Picasso
                        .get()
                        .load(Uri.parse(developer.getImageUrl()))
                        .into(holder.imgDeveloper1);
            }
        } else {
            holder.view1.setVisibility(View.GONE);
            holder.view2.setVisibility(View.VISIBLE);

            holder.tvName2.setText(developer.getName());

            LinearLayoutManager layoutManager = new LinearLayoutManager(context);
            layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            holder.rcvTag2.setLayoutManager(layoutManager);

            DeveloperTagAdapter tagAdapter = new DeveloperTagAdapter(developer.getTags());
            holder.rcvTag2.setAdapter(tagAdapter);

            setupSocialBtn(holder.btnLinkedIn2, developer.getLinkedInId());
            setupSocialBtn(holder.btnBehance2, developer.getBehanceId());
            setupSocialBtn(holder.btnGitHub2, developer.getGitHubId());
            setupSocialBtn(holder.btnMail2, developer.getMailId());

            if (developer.getImageUrl().equals("")) {
                holder.imgDeveloper2.setImageDrawable(AppCompatResources.getDrawable(context, R.drawable.ic_developer));
            } else {
                Picasso
                        .get()
                        .load(Uri.parse(developer.getImageUrl()))
                        .into(holder.imgDeveloper2);
            }
        }
    }

    @Override
    public int getItemCount() {
        return developerList.size();
    }

    private void setupSocialBtn(ImageButton btnSocial, String url) {
        if (url.equals("")) {
            btnSocial.setVisibility(View.GONE);
        } else {
            btnSocial.setVisibility(View.VISIBLE);
            btnSocial.setOnClickListener(v -> {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                context.startActivity(intent);
            });
        }
    }

}
