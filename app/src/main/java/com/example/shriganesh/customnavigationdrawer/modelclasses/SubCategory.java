package com.example.shriganesh.customnavigationdrawer.modelclasses;

import java.io.Serializable;

/**
 * Created by shri ganesh on 26/03/2017.
 */

public class SubCategory implements Serializable {

    private int mSubCategoryId;
    private int mRefMainCategoryId;
    private String mSubCategoryIcon;
    private String mSubCategoryTitle;

    public SubCategory()
    {
    }

    public SubCategory(int subId, String subTitle, String subIcon, int refMainId) {
        mSubCategoryId = subId;
        mSubCategoryTitle = subTitle;
        mSubCategoryIcon = subIcon;
        mRefMainCategoryId = refMainId;
    }

    public int getSubCategoryId() {
        return mSubCategoryId;
    }

    public void setSubCategoryId(int subCategoryId) {
        this.mSubCategoryId = subCategoryId;
    }

    public int getRefMainCategoryId() {
        return mRefMainCategoryId;
    }

    public void setRefMainCategoryId(int refMainCategoryId) {
        this.mRefMainCategoryId = refMainCategoryId;
    }

    public String getSubCategoryIcon() {
        return mSubCategoryIcon;
    }

    public void setSubCategoryIcon(String subCategoryIcon) {
        this.mSubCategoryIcon = subCategoryIcon;
    }

    public String getSubCategoryTitle() {
        return mSubCategoryTitle;
    }

    public void setSubCategoryTitle(String subCategoryTitle) {
        this.mSubCategoryTitle = subCategoryTitle;
    }
}
