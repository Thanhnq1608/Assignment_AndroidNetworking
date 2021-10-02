package com.example.assignment.model;

import android.text.TextUtils;
import android.util.Patterns;

public class User {
    private int id,position;
    private String username,password,phonenumber,gmail;

    public User() {
    }

    public User(int id, String username, String password,int position, String phonenumber, String gmail) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.position = position;
        this.phonenumber = phonenumber;
        this.gmail = gmail;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getGmail() {
        return gmail;
    }

    public void setGmail(String gmail) {
        this.gmail = gmail;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", position=" + position +
                ", phonenumber='" + phonenumber + '\'' +
                ", gmail='" + gmail + '\'' +
                '}';
    }

    public boolean isValidGmail(){
        return !TextUtils.isEmpty(gmail) && Patterns.EMAIL_ADDRESS.matcher(gmail).matches();
    }
    public boolean isValidUsername(){
        return !TextUtils.isEmpty(username);
    }
    public boolean isValidPass(){
        return !TextUtils.isEmpty(password);
    }
    public boolean isValidPhone(){
        return !TextUtils.isEmpty(phonenumber);
    }
}
