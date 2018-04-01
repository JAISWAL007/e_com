package com.example.shriganesh.customnavigationdrawer.modelclasses;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by shri ganesh on 26/03/2017.
 */

public class MainCategory implements Serializable{

    private int mMainCatId;
    private String mMainCatTitle;
    private String mMainCatIcon;
    private String mMainCatDesc;
    private ArrayList<SubCategory> mSubCategoryArrayList;

    public MainCategory()
    {
        mSubCategoryArrayList = new ArrayList<>();
    }

    public MainCategory(int mainId, String mainTitle, String mainDes, String mainIcon)
    {
        mMainCatId = mainId;
        mMainCatTitle = mainTitle;
        mMainCatIcon = mainIcon;
        mMainCatDesc = mainDes;

    }

    public int getMainCatId() {
        return mMainCatId;
    }

    public void setMainCatId(int mainCatId) {
        this.mMainCatId = mainCatId;
    }

    public String getMainCatTitle() {
        return mMainCatTitle;
    }

    public void setMainCatTitle(String mainCatTitle) {
        this.mMainCatTitle = mainCatTitle;
    }

    public String getMainCatIcon() {
        return mMainCatIcon;
    }

    public void setMainCatIcon(String mainCatIcon) {
        this.mMainCatIcon = mainCatIcon;
    }

    public String getMainCatDesc() {
        return mMainCatDesc;
    }

    public void setMainCatDesc(String mainCatDesc) {
        this.mMainCatDesc = mainCatDesc;
    }

    public ArrayList<SubCategory> getSubCategoryArrayList() {
        return mSubCategoryArrayList;
    }

    public void setSubCategoryArrayList(SubCategory subcategory) {
        mSubCategoryArrayList.add(subcategory);
    }
}


