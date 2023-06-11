package com.example.pebuplan.fragments.tracker;

public class TrackerModel {

    private String category;
    private String budget;
    private String expense;

    public TrackerModel(String category, String budget, String expense) {
        this.category = category;
        this.budget = budget;
        this.expense = expense;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getBudget() {
        return budget;
    }

    public void setBudget(String budget) {
        this.budget = budget;
    }

    public String getExpense() {
        return expense;
    }

    public void setExpense(String expense) {
        this.expense = expense;
    }
}
