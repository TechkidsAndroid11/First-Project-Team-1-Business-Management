package com.example.haihoang.managemaster.models;

/**
 * Created by haihoang on 10/23/17.
 */

public class Group {
    String name;
    int numberOfPerson;

    public Group(String name, int numberOfPerson) {
        this.name = name;
        this.numberOfPerson = numberOfPerson;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumberOfPerson() {
        return numberOfPerson;
    }

    public void setNumberOfPerson(int numberOfPerson) {
        this.numberOfPerson = numberOfPerson;
    }
}
