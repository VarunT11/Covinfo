package com.example.covinfo.enums;

public enum TimeSeriesType {
    ALL("All"),
    WEEK("7 Days"),
    MONTH("30 Days");

    private final String displayValue;

    TimeSeriesType(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }
}
