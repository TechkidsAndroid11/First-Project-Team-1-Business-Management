package com.example.haihoang.managemaster.databases;

/**
 * Created by Linh Phan on 10/23/2017.
 */

public class EmployeeModel {
//    Id`	INTEGER PRIMARY KEY AUTOINCREMENT,
//	`Name`	TEXT,
//            `DOB`	TEXT,
//            `Gender`	INTEGER,
//            `Address`	TEXT,
//            `GroupID`	TEXT,
//            `Experience`	TEXT,
//            `Image`	TEXT,
//            `FirstDayWork`	TEXT,
//            `Status`	TEXT,
//            `PreviousMonthSalary`	REAL,
//            `TotalSalary`	REAL,
//            `Note`	TEXT
    int id;
    String name;
    String date;
    int gender;
    String address;
    String groupId;
    String experience;
    String image;
    String firstDayWork;
    int status;
    float previvousMonthSalary;
    float totalSalary;
    String note;

    public EmployeeModel(int id, String name, String date, int gender, String address, String groupId,
                         String experience, String image, String firstDayWork,
                         int status, float previvousMonthSalary, float totalSalary, String note) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.gender = gender;
        this.address = address;
        this.groupId = groupId;
        this.experience = experience;
        this.image = image;
        this.firstDayWork = firstDayWork;
        this.status = status;
        this.previvousMonthSalary = previvousMonthSalary;
        this.totalSalary = totalSalary;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getFirstDayWork() {
        return firstDayWork;
    }

    public void setFirstDayWork(String firstDayWork) {
        this.firstDayWork = firstDayWork;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public float getPrevivousMonthSalary() {
        return previvousMonthSalary;
    }

    public void setPrevivousMonthSalary(float previvousMonthSalary) {
        this.previvousMonthSalary = previvousMonthSalary;
    }

    public float getTotalSalary() {
        return totalSalary;
    }

    public void setTotalSalary(float totalSalary) {
        this.totalSalary = totalSalary;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
