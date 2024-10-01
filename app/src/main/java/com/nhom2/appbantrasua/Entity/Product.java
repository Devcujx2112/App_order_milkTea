package com.nhom2.appbantrasua.Entity;

import java.io.Serializable;

public class Product implements Serializable {
    private int id;
    private String name;
    private String description;
    private double price;
    private String imageResource;
    private Topping Topping;
    private int Quality;

    public Product(int id, String name, String description, double price, String imageResource) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.imageResource = imageResource;
    }

    public int getQuality() {
        return Quality;
    }

    public Product.Topping getTopping() {
        return Topping;
    }

    public void setTopping(Product.Topping topping) {
        Topping = topping;
    }

    public void setQuality(int quality) {
        Quality = quality;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getImageResource() {
        return imageResource;
    }

    public void setImageResource(String imageResource) {
        this.imageResource = imageResource;
    }

    public class Topping {
        public int da;
        public int Duong;
        public int size;
        public int toping;

        public Topping(int da, int duong, int size, int toping) {
            this.da = da;
            Duong = duong;
            this.size = size;
            this.toping = toping;
        }
    }
}


