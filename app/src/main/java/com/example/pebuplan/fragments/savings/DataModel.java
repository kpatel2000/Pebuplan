package com.example.pebuplan.fragments.savings;

public class DataModel {

    private String month;
    private String numOfDays;
    private String savings;

    public DataModel(String month, String numOfDays, String savings) {
        this.month = month;
        this.numOfDays = numOfDays;
        this.savings = savings;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getNumOfDays() {
        return numOfDays;
    }

    public void setNumOfDays(String numOfDays) {
        this.numOfDays = numOfDays;
    }

    public String getSavings() {
        return savings;
    }

    public void setSavings(String savings) {
        this.savings = savings;
    }
}