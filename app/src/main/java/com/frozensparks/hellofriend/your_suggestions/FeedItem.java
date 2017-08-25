package com.frozensparks.hellofriend.your_suggestions;

/**
 * Created by Emanuel Graf on 24.08.2017.
 */

public class FeedItem {
    private String picture;
    private String SC_username;
    private  int check;

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
}