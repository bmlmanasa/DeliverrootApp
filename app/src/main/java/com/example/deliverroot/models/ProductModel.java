package com.example.deliverroot.models;

public class ProductModel {

    private int image;
    private String title;
    private float rating;
    private String price;


    public ProductModel(int image, String name,float rating,String price) {
        this.image = image;
        this.title = name;
        this.rating = rating;
        this.price = price;
    }

    public int getImage() {
        return image;
    }

    public String getName() {
        return title;
    }

    public float getRating() {
        return rating;
    }

    public String getPrice() {
        return price;
    }
}
