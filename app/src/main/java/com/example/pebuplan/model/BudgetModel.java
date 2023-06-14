package com.example.pebuplan.model;

public class BudgetModel {

    String category;
    String daily;
    String weekly;
    String monthly;
    String expense;
    String savings;


    public BudgetModel(String category, String daily, String weekly, String monthly) {
        this.category = category;
        this.daily = daily;
        this.weekly = weekly;
        this.monthly = monthly;
    }

    public BudgetModel(String category, String daily, String expense) {
        this.category = category;
        this.daily = daily;
        this.expense = expense;
    }

    public BudgetModel(String category, String daily) {
        this.category = category;
        this.daily = daily;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDaily() {
        return daily;
    }

    public void setDaily(String daily) {
        this.daily = daily;
    }

    public String getWeekly() {
        return weekly;
    }

    public void setWeekly(String weekly) {
        this.weekly = weekly;
    }

    public String getMonthly() {
        return monthly;
    }

    public void setMonthly(String monthly) {
        this.monthly = monthly;
    }

    public String getExpense() {
        return expense;
    }

    public void setExpense(String expense) {
        this.expense = expense;
    }

    public String getSavings() {
        return savings;
    }

    public void setSavings(String savings) {
        this.savings = savings;
    }
}