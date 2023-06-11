package com.example.pebuplan.fragments.fgoal;

public class Record {
    private String record;
    private String date;

    public Record(String record, String date) {
        this.record = record;
        this.date = date;
    }

    public String getRecord() {
        return record;
    }

    public void setRecord(String record) {
        this.record = record;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
