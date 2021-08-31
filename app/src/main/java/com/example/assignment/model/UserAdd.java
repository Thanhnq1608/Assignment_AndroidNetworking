package com.example.assignment.model;

public class UserAdd {
    private int id;
    private String status;

    public UserAdd() {
    }

    public UserAdd(int id, String status) {
        this.id = id;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }
}
