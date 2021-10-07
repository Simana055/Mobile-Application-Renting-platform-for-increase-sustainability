package com.example.myapplication_rentme;

import java.io.Serializable;

public class CartProductModel implements Serializable {

    private int Id;
    private int quantity;
    private String  catName, productId, productName, productImage, productDiscription, productPrice;


    public CartProductModel() {
    }

    public CartProductModel(int id, int quantity, String catName, String productId, String productName, String productImage, String productDiscription, String productPrice) {
        Id = id;
        this.quantity = quantity;
        this.catName = catName;
        this.productId = productId;
        this.productName = productName;
        this.productImage = productImage;
        this.productDiscription = productDiscription;
        this.productPrice = productPrice;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getProductDiscription() {
        return productDiscription;
    }

    public void setProductDiscription(String productDiscription) {
        this.productDiscription = productDiscription;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }
}
