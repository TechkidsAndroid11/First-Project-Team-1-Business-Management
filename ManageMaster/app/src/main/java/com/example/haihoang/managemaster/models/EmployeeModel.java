package com.example.haihoang.managemaster.models;

import java.io.Serializable;

/**
 * Created by Linh Phan on 10/23/2017.
 */

public class EmployeeModel implements Serializable {
    int id;
    String name;
    int gender;
    String date;
    String phone;
    String address;
    String avatar;
    String experience;
    String group;
    String firstDayWork;
    int daySalary;
    int totalSalary;
    int previousSalary;
    int status;
    String note;

    public EmployeeModel(String name, int daySalary, int totalSalary, int previousSalary) {
        this.name = name;
        this.daySalary = daySalary;
        this.totalSalary = totalSalary;
        this.previousSalary = previousSalary;
    }

    public EmployeeModel(String name, int gender, String date, String phone, String address, String avatar, String experience, String group, String firstDayWork, int daySalary, int totalSalary, int previousSalary, int status, String note) {
        this.name = name;
        this.gender = gender;
        this.date = date;
        this.phone = phone;
        this.address = address;
        this.avatar = avatar;
        this.experience = experience;
        this.group = group;
        this.firstDayWork = firstDayWork;
        this.daySalary = daySalary;
        this.totalSalary = totalSalary;
        this.previousSalary = previousSalary;
        this.status = status;
        this.note = note;
    }
    public EmployeeModel(String name,String date,String startTime,String avatar,int salary,int status)
    {

        this.name=name;
        this.date=date;
        this.firstDayWork=startTime;
        this.avatar=avatar;
        this.daySalary=salary;
        this.status=status;
    }
    public EmployeeModel(int id, String name, int gender, String date, String phone, String address, String avatar, String experience, String group, String firstDayWork, int daySalary, int totalSalary, int previousSalary, int status, String note) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.date = date;
        this.phone = phone;
        this.address = address;
        this.avatar = avatar;
        this.experience = experience;
        this.group = group;
        this.firstDayWork = firstDayWork;
        this.daySalary = daySalary;
        this.totalSalary = totalSalary;
        this.previousSalary = previousSalary;
        this.status = status;
        this.note = note;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getFirstDayWork() {
        return firstDayWork;
    }

    public void setFirstDayWork(String firstDayWork) {
        this.firstDayWork = firstDayWork;
    }

    public int getDaySalary() {
        return daySalary;
    }

    public void setDaySalary(int daySalary) {
        this.daySalary = daySalary;
    }

    public int getTotalSalary() {
        return totalSalary;
    }

    public void setTotalSalary(int totalSalary) {
        this.totalSalary = totalSalary;
    }

    public int getPreviousSalary() {
        return previousSalary;
    }

    public void setPreviousSalary(int previousSalary) {
        this.previousSalary = previousSalary;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return name + " " + id + " " + gender + " " + date + " " + phone + " \n" +
                avatar + "\n" +
                experience + " " + group + " " + daySalary + " " + status + "\n" +
                firstDayWork;
    }
}
