package com.geekhaven.covinfo.fragments.news;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.covinfo.R;
import com.geekhaven.covinfo.manager.ActivityViewManager;
import com.geekhaven.covinfo.utils.DateTimeUtil;
import com.geekhaven.covinfo.viewmodels.MainViewModel;
import com.squareup.picasso.Picasso;

public class NewsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_news, container, false);
    }

    private TextView tvTitle, tvPublishedTime, tvAuthorLabel, tvAuthors, tvContent;
    private ImageView imgNews;
    private Button btnOpenArticle;

    private MainViewModel mainViewModel;
    private ActivityViewManager viewManager;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViewsAndAttachListeners(view);
        initializeViewModelAndManagers();
        addViewModelObservers();
        updateActivityViews();
    }

    private void findViewsAndAttachListeners(View view) {
        tvTitle = view.findViewById(R.id.tvNewsTitle);
        tvPublishedTime = view.findViewById(R.id.tvNewsPublishedTime);
        tvAuthorLabel = view.findViewById(R.id.tvNewsAuthorLabel);
        tvAuthors = view.findViewById(R.id.tvNewsAuthor);
        tvContent = view.findViewById(R.id.tvContentNews);
        imgNews = view.findViewById(R.id.imgNews);
        btnOpenArticle = view.findViewById(R.id.btnViewFullArticle);
    }

    private void initializeViewModelAndManagers() {
        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        viewManager = ActivityViewManager.getInstance((AppCompatActivity) requireActivity());
    }

    private void addViewModelObservers() {
        mainViewModel
                .getCurrentNews()
                .observe(getViewLifecycleOwner(), news -> {
                    tvTitle.setText(news.getTitle());
                    tvPublishedTime.setText(DateTimeUtil.getDisplayTime(news.getPublishedTime()));

                    tvContent.setText(news.getDescription());

                    if (news.getAuthors().equals("")) {
                        tvAuthors.setVisibility(View.GONE);
                        tvAuthorLabel.setVisibility(View.GONE);
                    } else {
                        tvAuthors.setVisibility(View.VISIBLE);
                        tvAuthorLabel.setVisibility(View.VISIBLE);
                        tvAuthors.setText(news.getAuthors());
                    }

                    Picasso.get().load(Uri.parse(news.getNewsImageUrl())).into(imgNews);

                    btnOpenArticle.setOnClickListener(v -> {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(news.getNewsUrl()));
                        startActivity(intent);
                    });
                });
    }

    private void updateActivityViews(){
        viewManager.updateAppBarVisibility(View.GONE, View.VISIBLE);
        viewManager.setHeadingText("News");
    }
}