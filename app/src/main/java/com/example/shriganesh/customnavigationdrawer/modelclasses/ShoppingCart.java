package com.example.shriganesh.customnavigationdrawer.modelclasses;

/**
 * Created by shri ganesh on 29/04/2017.
 */

public class ShoppingCart {

    private int mProductId;
    private int mProductPrice;
    private int mProductQuantity;
    private String mProductName;
    private String mProductImage;

    public ShoppingCart(){}

    public ShoppingCart(int mProductId, String mProductName, int mProductPrice, int mProductQuantity, String mProductImage) {
        this.mProductId = mProductId;
        this.mProductName = mProductName;
        this.mProductPrice = mProductPrice;
        this.mProductQuantity = mProductQuantity;
        this.mProductImage = mProductImage;
    }

    public int getProductQuantity() {
        return mProductQuantity;
    }

    public void setProductQuantity(int productQuantity) {
        this.mProductQuantity = productQuantity;
    }

    public int getProductId() {
        return mProductId;
    }

    public void setProductId(int productId) {
        this.mProductId = productId;
    }

    public int getProductPrice() {
        return mProductPrice;
    }

    public void setProductPrice(int productPrice) {
        this.mProductPrice = productPrice;
    }

    public String getProductName() {
        return mProductName;
    }

    public void setProductName(String productName) {
        this.mProductName = productName;
    }

    public String getProductImage() {
        return mProductImage;
    }

    public void setProductImage(String productImage) {
        this.mProductImage = productImage;
    }
}
