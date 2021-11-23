package com.example.deliverroot.models;

public class CategoryProductModel {

    String productName;
    String desc;
    String cost;
    String imageurl;
    String id;

    public CategoryProductModel() {

    }
    public CategoryProductModel(String imageurl, String productName, String desc, String cost, String id) {
        this.productName = productName;
        this.desc = desc;
        this.cost = cost;
        this.imageurl=imageurl;
        this.id = id;
    }

    public String getImageurl() {
        return imageurl;
    }

    public String getProductName() {
        return productName;
    }

    public String getDesc() {
        return desc;
    }

    public String getCost() {
        return cost;
    }

    public String getId() {
        return id;
    }


}
