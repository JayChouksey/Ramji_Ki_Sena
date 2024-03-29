package com.example.ramjikisena.recyclerfiles;

public class Devotee {
    private String rank;
    private String name;
    private String todaysCount;
    private String totalCount;

    public Devotee(String rank, String name, String todaysCount, String totalCount){
        this.rank = rank;
        this.name = name;
        this.todaysCount = todaysCount;
        this.totalCount = totalCount;
    }

    public String getName() {
        return name;
    }

    public String getRank() {
        return rank;
    }

    public String getTodaysCount() {
        return todaysCount;
    }

    public String getTotalCount() {
        return totalCount;
    }
}
