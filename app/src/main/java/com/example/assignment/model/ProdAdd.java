package com.example.assignment.model;

public class ProdAdd {
    private int id;
    private String status;

    public ProdAdd() {
    }

    public ProdAdd(int id, String status) {
        this.id = id;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
