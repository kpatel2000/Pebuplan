package com.example.pebuplan.model;

public class MonthlyBillModel {

    String category;
    String amount;
    String pay;
    String debt;
    String status;

    public MonthlyBillModel(String category, String amount, String pay, String debt, String status) {
        this.category = category;
        this.amount = amount;
        this.pay = pay;
        this.debt = debt;
        this.status = status;
    }

    public MonthlyBillModel(String category, String amount, String pay) {
        this.category = category;
        this.amount = amount;
        this.pay = pay;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getPay() {
        return pay;
    }

    public void setPay(String pay) {
        this.pay = pay;
    }

    public String getDebt() {
        return debt;
    }

    public void setDebt(String debt) {
        this.debt = debt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
