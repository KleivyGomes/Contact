package com.example.contact;

public class MyListData {
    // Contact attribute
    private String name, email;
    private String phone;
    private Boolean favorito = false;
    private String day, month, year, sex;

    //constructor
    public MyListData(String name, String email, String phone, String day, String month, String year, String sex) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.day = day;
        this.month = month;
        this.year = year;
        this.sex = sex;
    }

    //getters and setters
    public String getName() {
        return name;
    }


    public String getPhone() {
        return phone;
    }


    public String getEmail() {
        return email;
    }


    public Boolean getFavorito() {
        return favorito;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setFavorito(Boolean favorito) {
        this.favorito = favorito;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
