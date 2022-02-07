package com.example.covinfo.ui;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.covinfo.R;
import com.example.covinfo.classes.CovidStats;
import com.example.covinfo.enums.CaseType;
import com.example.covinfo.enums.TimeSeriesType;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GraphView {

    private AppCompatActivity activity;
    private View rootView;
    private ArrayList<CaseType> caseTypeList;
    private ArrayList<TimeSeriesType> rangeTypeList;

    String title;

    private TextView tvHeading;
    private MaterialCardView mcvGraph;
    private TextInputLayout tilMenuCasesType, tilMenuRangeType;
    private AutoCompleteTextView acTvMenuCasesType, acTvMenuRangeType;
    private LineChart lineChart;

    private ArrayList<CovidStats> statsList, displayList;

    private CaseType currentCaseType;
    private TimeSeriesType currentRangeType;

    public GraphView(AppCompatActivity activity, View rootView, ArrayList<CaseType> caseTypeList, String title) {
        this.activity = activity;
        this.rootView = rootView;
        this.caseTypeList = caseTypeList;
        this.title = title;
        initializeViews();
    }

    public void setGraphData(ArrayList<CovidStats> statsList) {
        this.statsList = statsList;
        updateGraphRange();
    }

    private void initializeViews() {
        tvHeading = rootView.findViewById(R.id.tvGraphLayoutTitle);
        mcvGraph = rootView.findViewById(R.id.mcvGraphLayoutGraph);
        tilMenuCasesType = rootView.findViewById(R.id.textMenuCasesType);
        tilMenuRangeType = rootView.findViewById(R.id.textMenuRangeType);
        lineChart = rootView.findViewById(R.id.lineChartGraph);

        acTvMenuCasesType = (AutoCompleteTextView) tilMenuCasesType.getEditText();
        acTvMenuRangeType = (AutoCompleteTextView) tilMenuRangeType.getEditText();

        initializeGraph();

        tvHeading.setText(title);

        setMenu();

        statsList = new ArrayList<>();
        updateGraphRange();
    }

    public void setMenu(){
        rangeTypeList = new ArrayList<>(Arrays.asList(TimeSeriesType.values()));

        ArrayList<String> rangeTypeStringList = new ArrayList<>();
        for (TimeSeriesType type : rangeTypeList) {
            rangeTypeStringList.add(type.getDisplayValue());
        }

        ArrayList<String> caseTypeStringList = new ArrayList<>();
        for (CaseType caseType : caseTypeList) {
            caseTypeStringList.add(caseType.getDisplayValue());
        }

        ArrayAdapter<String> rangeAdapter = new ArrayAdapter<>(activity, android.R.layout.simple_dropdown_item_1line, rangeTypeStringList);
        ArrayAdapter<String> caseAdapter = new ArrayAdapter<>(activity, android.R.layout.simple_dropdown_item_1line, caseTypeStringList);

        acTvMenuRangeType.setAdapter(rangeAdapter);
        acTvMenuCasesType.setAdapter(caseAdapter);

        currentRangeType = rangeTypeList.get(0);
        currentCaseType = caseTypeList.get(0);

        acTvMenuRangeType.setText(currentRangeType.getDisplayValue(), false);
        acTvMenuCasesType.setText(currentCaseType.getDisplayValue(), false);

        acTvMenuRangeType.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                for (TimeSeriesType type : TimeSeriesType.values())
                    if (type.getDisplayValue().equals(s.toString())) {
                        currentRangeType = type;
                        updateGraphRange();
                    }
            }
        });

        acTvMenuCasesType.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                for (CaseType type : CaseType.values())
                    if (type.getDisplayValue().equals(s.toString())) {
                        currentCaseType = type;
                        updateGraphCase();
                    }
            }
        });
    }

    private void initializeGraph() {
        XAxis xAxis = lineChart.getXAxis();
        YAxis yAxisLeft = lineChart.getAxisLeft();
        YAxis yAxisRight = lineChart.getAxisRight();

        xAxis.setEnabled(false);
        yAxisRight.setEnabled(false);
        yAxisLeft.setDrawAxisLine(false);
        yAxisLeft.setGridLineWidth(1F);
        yAxisLeft.setLabelCount(11);
        yAxisLeft.setGridColor(Color.parseColor("#3005065C"));
        yAxisLeft.setTextSize(8f);
        yAxisLeft.setTextColor(Color.parseColor("#3005065C"));
        yAxisLeft.setTypeface(Typeface.create("poppins_semibold", Typeface.BOLD));

        lineChart.getDescription().setEnabled(false);
        lineChart.getLegend().setEnabled(false);
    }

    private void updateGraphRange() {
        switch (currentRangeType) {
            case ALL: {
                displayList = statsList;
                break;
            }
            case MONTH: {
                int n = statsList.size();
                displayList = new ArrayList<>();
                for (int i = n - 31; i >= 0 && i < n; i++) {
                    displayList.add(statsList.get(i));
                }
                break;
            }
            case WEEK: {
                int n = statsList.size();
                displayList = new ArrayList<>();
                for (int i = n - 8; i >= 0 && i < n; i++) {
                    displayList.add(statsList.get(i));
                }
                break;
            }
        }
        updateGraphCase();
    }

    private void updateGraphCase() {
        List<Entry> entries = new ArrayList<>();
        for (int i = 0; i < displayList.size(); i++) {
            CovidStats timeSeriesStats = displayList.get(i);
            int data_entry = 0;
            switch (currentCaseType) {
                case TOTAL_CONFIRMED:
                    data_entry = timeSeriesStats.getTotalConfirmed();
                    break;
                case DAILY_CONFIRMED:
                    data_entry = timeSeriesStats.getDailyConfirmed();
                    break;
                case TOTAL_RECOVERED:
                    data_entry = timeSeriesStats.getTotalRecovered();
                    break;
                case DAILY_RECOVERED:
                    data_entry = timeSeriesStats.getDailyRecovered();
                    break;
                case TOTAL_DECEASED:
                    data_entry = timeSeriesStats.getTotalDeceased();
                    break;
                case DAILY_DECEASED:
                    data_entry = timeSeriesStats.getDailyDeceased();
                    break;
                case TOTAL_ACTIVE:
                    data_entry = timeSeriesStats.getTotalActive();
                    break;
                case DAILY_ACTIVE:
                    data_entry = timeSeriesStats.getDailyActive();
                    break;
            }
            entries.add(new Entry(i, data_entry));
        }

        LineDataSet lineDataSet = new LineDataSet(entries, "");
        lineDataSet.setDrawCircles(false);
        lineDataSet.setMode(LineDataSet.Mode.LINEAR);
        lineDataSet.setLineWidth(2.4F);
        lineDataSet.setDrawValues(false);

        String textColorString = "#000000";
        String backgroundColorString = "#000000";

        switch (currentCaseType) {
            case TOTAL_CONFIRMED:
            case DAILY_CONFIRMED: {
                textColorString = "#FC1441";
                backgroundColorString = "#FFE7EC";
                break;
            }
            case TOTAL_RECOVERED:
            case DAILY_RECOVERED: {
                textColorString = "#30A64A";
                backgroundColorString = "#e4f4e9";
                break;
            }
            case TOTAL_DECEASED:
            case DAILY_DECEASED: {
                textColorString = "#6D757D";
                backgroundColorString = "#edeef0";
                break;
            }
            case TOTAL_ACTIVE:
            case DAILY_ACTIVE:{
                lineDataSet.setColor(Color.parseColor("#157FFB"));
                textColorString = "#157FFB";
                backgroundColorString = "#e2efff";
                break;
            }
        }

        lineDataSet.setColor(Color.parseColor(textColorString));
        acTvMenuCasesType.setTextColor(Color.parseColor(textColorString));
        tilMenuCasesType.setEndIconTintList(ColorStateList.valueOf(Color.parseColor(textColorString)));
        mcvGraph.setCardBackgroundColor(Color.parseColor(backgroundColorString));

        LineData lineData = new LineData(lineDataSet);
        lineChart.setData(lineData);
        lineChart.invalidate();
    }
}
