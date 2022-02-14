package com.geekhaven.covinfo.views;

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
import com.geekhaven.covinfo.classes.stats.CovidStats;
import com.geekhaven.covinfo.enums.CaseType;
import com.geekhaven.covinfo.enums.TimeSeriesType;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textfield.TextInputLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class GraphView {

    private final AppCompatActivity activity;
    private final View rootView;
    private final ArrayList<CaseType> caseTypeList;

    private final String title;

    private TextView tvNotAvailable;
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
        if (statsList.size() == 0) {
            lineChart.setVisibility(View.GONE);
            tilMenuRangeType.setVisibility(View.GONE);
            tilMenuCasesType.setVisibility(View.GONE);
            tvNotAvailable.setVisibility(View.VISIBLE);
        } else {
            lineChart.setVisibility(View.VISIBLE);
            tilMenuRangeType.setVisibility(View.VISIBLE);
            tilMenuCasesType.setVisibility(View.VISIBLE);
            tvNotAvailable.setVisibility(View.GONE);
            updateGraphRange();
        }
    }

    private void initializeViews() {
        tvNotAvailable = rootView.findViewById(R.id.tvTimeSeriesNotAvailable);
        TextView tvHeading = rootView.findViewById(R.id.tvGraphLayoutTitle);
        mcvGraph = rootView.findViewById(R.id.mcvGraphLayoutGraph);
        tilMenuCasesType = rootView.findViewById(R.id.textMenuCasesType);
        tilMenuRangeType = rootView.findViewById(R.id.textMenuRangeType);
        lineChart = rootView.findViewById(R.id.lineChartGraph);

        acTvMenuCasesType = (AutoCompleteTextView) tilMenuCasesType.getEditText();
        acTvMenuRangeType = (AutoCompleteTextView) tilMenuRangeType.getEditText();

        initializeGraph();

        if (title.equals("")) {
            tvHeading.setVisibility(View.GONE);
        } else {
            tvHeading.setVisibility(View.VISIBLE);
            tvHeading.setText(title);
        }

        setMenu();

        statsList = new ArrayList<>();
        updateGraphRange();
    }

    public void setMenu() {
        ArrayList<TimeSeriesType> rangeTypeList = new ArrayList<>(Arrays.asList(TimeSeriesType.values()));

        ArrayList<String> rangeTypeStringList = new ArrayList<>();
        for (TimeSeriesType type : rangeTypeList) {
            rangeTypeStringList.add(type.getDisplayValue());
        }

        ArrayList<String> caseTypeStringList = new ArrayList<>();
        for (CaseType caseType : caseTypeList) {
            caseTypeStringList.add(caseType.getDisplayValue());
        }

        ArrayAdapter<String> rangeAdapter = new ArrayAdapter<>(activity, R.layout.range_type_menu_item, rangeTypeStringList);
        ArrayAdapter<String> caseAdapter = new ArrayAdapter<>(activity, R.layout.case_menu_item, caseTypeStringList);

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

        yAxisRight.setEnabled(false);
        yAxisLeft.setDrawAxisLine(false);

        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);
        xAxis.setTextSize(8f);
        xAxis.setTextColor(activity.getColor(R.color.color_text_light));
        xAxis.setTypeface(Typeface.create("poppins_semibold", Typeface.BOLD));
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("d MMM ''yy", Locale.ENGLISH);
                Date date = new Date((long) value);
                return dateFormat.format(date);
            }
        });

        yAxisLeft.setGridLineWidth(1F);
        yAxisLeft.setLabelCount(11);
        yAxisLeft.setGridColor(activity.getColor(R.color.color_text_light));
        yAxisLeft.setTextSize(8f);
        yAxisLeft.setTextColor(activity.getColor(R.color.color_text_light));
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
            entries.add(new Entry(getLongTime(timeSeriesStats.getDateOfStat()), data_entry));
        }

        LineDataSet lineDataSet = new LineDataSet(entries, "");
        lineDataSet.setDrawCircles(false);
        lineDataSet.setMode(LineDataSet.Mode.LINEAR);
        lineDataSet.setLineWidth(2.4F);
        lineDataSet.setDrawValues(false);

        int textColor = Color.BLACK;
        int bgColor = Color.WHITE;

        switch (currentCaseType) {
            case TOTAL_CONFIRMED:
            case DAILY_CONFIRMED: {
                textColor = activity.getColor(R.color.color_red);
                bgColor = activity.getColor(R.color.color_red_graph_bg);
                break;
            }
            case TOTAL_RECOVERED:
            case DAILY_RECOVERED: {
                textColor = activity.getColor(R.color.color_green);
                bgColor = activity.getColor(R.color.color_green_graph_bg);
                break;
            }
            case TOTAL_DECEASED:
            case DAILY_DECEASED: {
                textColor = activity.getColor(R.color.color_grey);
                bgColor = activity.getColor(R.color.color_grey_graph_bg);
                break;
            }
            case TOTAL_ACTIVE:
            case DAILY_ACTIVE: {
                textColor = activity.getColor(R.color.color_blue);
                bgColor = activity.getColor(R.color.color_blue_graph_bg);
                break;
            }
        }

        lineDataSet.setColor(textColor);
        acTvMenuCasesType.setTextColor(textColor);
        tilMenuCasesType.setEndIconTintList(ColorStateList.valueOf(textColor));
        mcvGraph.setCardBackgroundColor(bgColor);

        LineData lineData = new LineData(lineDataSet);
        lineChart.setData(lineData);
        lineChart.invalidate();
    }

    private long getLongTime(String dateOfStat) {
        String datePattern = "yyyy-MM-dd";
        SimpleDateFormat dateFormat = new SimpleDateFormat(datePattern, Locale.ENGLISH);
        try {
            Date date = dateFormat.parse(dateOfStat);
            if (date != null) {
                return date.getTime();
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

}
