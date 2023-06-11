package com.example.pebuplan.model;

public class BudgetModel {

    String category;
    String budget;
    String spent;
    String remain;

    public BudgetModel(String category, String budget, String spent, String remain) {
        this.category = category;
        this.budget = budget;
        this.spent = spent;
        this.remain = remain;
    }

    public BudgetModel(String category, String budget, String spent) {
        this.category = category;
        this.budget = budget;
        this.spent = spent;
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

    public String getSpent() {
        return spent;
    }

    public void setSpent(String spent) {
        this.spent = spent;
    }

    public String getRemain() {
        return remain;
    }

    public void setRemain(String remain) {
        this.remain = remain;
    }
}