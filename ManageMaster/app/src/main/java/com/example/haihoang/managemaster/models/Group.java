package com.example.haihoang.managemaster.models;

import java.util.ArrayList;

/**
 * Created by haihoang on 10/23/17.
 */

public class Group {
    String name;
    ArrayList<EmployeeModel> listEmployee;
    int numberOfPerson;

    public Group(String name, ArrayList<EmployeeModel> listEmployee) {
        this.name = name;
        this.listEmployee = listEmployee;
    }

    public ArrayList<EmployeeModel> getListEmployee() {
        return listEmployee;
    }

    public void setListEmployee(ArrayList<EmployeeModel> listEmployee) {
        this.listEmployee = listEmployee;
    }

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
