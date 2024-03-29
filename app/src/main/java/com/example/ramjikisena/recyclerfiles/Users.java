package com.example.ramjikisena.recyclerfiles;

public class Users {

    String username;
    String name;
    String city;
    String totalCount;
    String contact;
    String joiningDate;

    public Users(String username, String name, String city, String totalCount, String contact, String joiningDate){
        this.username = username;
        this.name = name;
        this.city = city;
        this.totalCount = totalCount;
        this.contact = contact;
        this.joiningDate = joiningDate;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    public String getTotalCount() {
        return totalCount;
    }

    public String getContact() {
        return contact;
    }

    public String getJoiningDate() {
        return joiningDate;
    }
}
