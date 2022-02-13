package com.example.covinfo.views;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.example.covinfo.R;
import com.example.covinfo.enums.CaseType;
import com.google.android.material.card.MaterialCardView;

import java.util.Locale;

public class CaseCardView {

    private final View rootView;

    private final CaseType caseType;
    private final String label;
    private int totalCases, dailyCases;
    private boolean isDailyVisible;
    private final Context context;

    private TextView tvTotalCases;
    private TextView tvDailyCases;

    private int textColorId=0, bgColorId=0;

    public CaseCardView(Context context, View rootView, CaseType caseType, String label) {
        this.context = context;
        this.rootView = rootView;
        this.caseType = caseType;
        this.label = label;
        initializeView();
    }

    public void SetDetails(int totalCases, int dailyCases, boolean isDailyVisible) {
        this.totalCases = totalCases;
        this.dailyCases = dailyCases;
        this.isDailyVisible = isDailyVisible;
        updateDetails();
    }

    private void initializeView() {
        TextView tvLabel = rootView.findViewById(R.id.tvCaseCardLabel);
        tvTotalCases = rootView.findViewById(R.id.tvCaseCardTotal);
        tvDailyCases = rootView.findViewById(R.id.tvCaseCardDaily);
        MaterialCardView mcvCase = (MaterialCardView) rootView;

        tvLabel.setText(label);

        switch (caseType) {
            case TOTAL_CONFIRMED:
            case DAILY_CONFIRMED: {
                textColorId = context.getColor(R.color.color_red);
                bgColorId = context.getColor(R.color.color_red_card);
                break;
            }
            case TOTAL_RECOVERED:
            case DAILY_RECOVERED: {
                textColorId = context.getColor(R.color.color_green);
                bgColorId = context.getColor(R.color.color_green_card);
                break;
            }
            case TOTAL_DECEASED:
            case DAILY_DECEASED: {
                textColorId = context.getColor(R.color.color_grey);
                bgColorId = context.getColor(R.color.color_grey_card);
                break;
            }
            case TOTAL_ACTIVE:
            case DAILY_ACTIVE:{
                textColorId = context.getColor(R.color.color_blue);
                bgColorId = context.getColor(R.color.color_blue_card);
                break;
            }
        }

        tvLabel.setTextColor(textColorId);
        tvTotalCases.setTextColor(textColorId);
        tvDailyCases.setTextColor(textColorId);

        mcvCase.setCardBackgroundColor(bgColorId);

        totalCases=0;
        dailyCases=0;
        isDailyVisible=true;
        updateDetails();
    }

    private void updateDetails() {
        tvTotalCases.setText(String.format(
                Locale.ROOT, "%, d", totalCases
        ));
        if (isDailyVisible) {
            tvDailyCases.setVisibility(View.VISIBLE);
            String format;
            if(dailyCases>=0){
                format="(+%, d)";
            } else {
                format="(%, d)";
            }
            tvDailyCases.setText(String.format(
                    Locale.ENGLISH, format, dailyCases
            ));
        } else {
            tvDailyCases.setVisibility(View.GONE);
        }
    }
}
