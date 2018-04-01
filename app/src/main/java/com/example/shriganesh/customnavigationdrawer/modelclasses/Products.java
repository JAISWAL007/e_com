package com.example.shriganesh.customnavigationdrawer.modelclasses;

import java.util.ArrayList;

/**
 * Created by shri ganesh on 29/03/2017.
 */

public class Products {

    private int mProductPrice;
    private int mProductId;
    private String mProductTitle;
    private ArrayList<Images> mImagesArrayList;

    public Products(){
        mImagesArrayList = new ArrayList<>();
    }

    public int getProductPrice() {
        return mProductPrice;
    }

    public void setProductPrice(int productPrice) {
        this.mProductPrice = productPrice;
    }

    public String getProductTitle() {
        return mProductTitle;
    }

    public void setProductTitle(String productTitle) {
        this.mProductTitle = productTitle;
    }

    public int getProductId() {
        return mProductId;
    }

    public void setProductId(int productId) {
        mProductId = productId;
    }


    public ArrayList<Images> getImagesArrayList() {
        return mImagesArrayList;
    }

    public void setImagesArrayList(Images images) {
        mImagesArrayList.add(images);
    }
}
