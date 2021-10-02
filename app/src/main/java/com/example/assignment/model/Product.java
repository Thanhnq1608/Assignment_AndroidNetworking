package com.example.assignment.model;

import android.text.TextUtils;

public class Product {

    private int id;
    private String avatar;
    private String name;
    private int price;
    private int soLuongTon;
    private String description;

    public Product() {
    }

    public Product(int id, String avatar, String name, int price, int soLuongTon, String description) {
        this.id = id;
        this.avatar = avatar;
        this.name = name;
        this.price = price;
        this.soLuongTon = soLuongTon;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getSoLuongTon() {
        return soLuongTon;
    }

    public void setSoLuongTon(int soLuongTon) {
        this.soLuongTon = soLuongTon;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", avatar='" + avatar + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", soLuongTon=" + soLuongTon +
                ", description='" + description + '\'' +
                '}';
    }

    public boolean isValidAva(){
        return !TextUtils.isEmpty(avatar);
    }
    public boolean isValidName(){
        return !TextUtils.isEmpty(name);
    }
    public boolean isValidPrice(){
        return !TextUtils.isEmpty(String.valueOf(price));
    }
    public boolean isValidSoluongton(){
        return !TextUtils.isEmpty(String.valueOf(soLuongTon));
    }
    public boolean isValidDes(){
        return !TextUtils.isEmpty(description);
    }
}
