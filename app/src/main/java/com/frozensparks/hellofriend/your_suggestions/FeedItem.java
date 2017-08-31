package com.frozensparks.hellofriend.your_suggestions;

/**
 * Created by Emanuel Graf on 24.08.2017.
 */

public class FeedItem {
    private String picture;
    private String SC_username;
    private  int check;
    private int gender;
    private int value;
    private int year;
    private int id;
    private  String country;

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getSC_username() {
        return SC_username;
    }

    public void setSC_username(String SC_username) {
        this.SC_username = SC_username;
    }

    public int getCheck() {
        return check;
    }
    public void setCheck(int check) {
        this.check = check;
    }

    public void setGender(String gender) {
        this.gender= Integer.valueOf(gender);
    }
    public int getGender(){
        return gender;
    }

    public void setYear(String year) {
        this.year= Integer.valueOf(year);
    }
    public int getYear(){
        return year;
    }

    public void setValue(String value) {
        this.value= Integer.valueOf(value);
    }
    public int getValue(){
        return value;
    }

    public void setID(String id) {
        this.id= Integer.valueOf(id);
    }
    public int getID(){
        return id;
    }

    public void setCountry(String country) {
        this.country= country;
    }
    public String getCountry(){
        return country;
    }


}