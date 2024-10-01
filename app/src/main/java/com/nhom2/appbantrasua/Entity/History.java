package com.nhom2.appbantrasua.Entity;

import java.util.List;

public class History {
    public String userName;
    public String name;
    public String Phone;
    public String Address;
    public List<Product> listProduct;
    public String totalAmount;
    public History() {

    }

    public History(String name, String phone, String address, List<Product> listProduct, String totalAmount) {
        this.name = name;
        Phone = phone;
        Address = address;
        this.listProduct = listProduct;
        this.totalAmount = totalAmount;
    }

    public History(String userName, String name, String phone, String address, List<Product> listProduct, String totalAmount) {
        this.userName = userName;
        this.name = name;
        Phone = phone;
        Address = address;
        this.listProduct = listProduct;
        this.totalAmount = totalAmount;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Product> getListProduct() {
        return listProduct;
    }

    public void setListProduct(List<Product> listProduct) {
        this.listProduct = listProduct;
    }
}
