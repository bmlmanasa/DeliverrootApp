package com.example.deliverroot.models;

import java.io.Serializable;

public class CartModel implements Serializable {

    String name;
    String price;

    public String getQuantity() {
        return quantity;
    }

    String quantity;


    public CartModel() {

    }


    String imageurl;
    String id;


    public CartModel(String id, String imageurl, String name, String price, String quantity ) {
        this.name = name;
        this.price = price;
        this.imageurl = imageurl;
        this.quantity=quantity;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getImageurl() {
        return imageurl;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

}
