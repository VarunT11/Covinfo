package com.geekhaven.covinfo.enums;

public enum CaseType {
    TOTAL_CONFIRMED("Confirmed"),
    DAILY_CONFIRMED("Daily Confirmed"),
    TOTAL_RECOVERED("Recovered"),
    DAILY_RECOVERED("Daily Recovered"),
    TOTAL_DECEASED("Deaths"),
    DAILY_DECEASED("Daily Deaths"),
    TOTAL_ACTIVE("Total Active"),
    DAILY_ACTIVE("Daily Active");

    private final String displayValue;

    CaseType(String name) {
        displayValue = name;
    }

    public String getDisplayValue() {
        return displayValue;
    }
}
