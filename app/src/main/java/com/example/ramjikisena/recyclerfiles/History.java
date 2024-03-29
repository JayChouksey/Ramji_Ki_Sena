package com.example.ramjikisena.recyclerfiles;

public class History {
    String date;
    String count;

    public History(String date, String count){
        this.date = date;
        this.count = count;
    }

    public String getDate() {
        return date;
    }

    public String getCount() {
        return count;
    }
}
