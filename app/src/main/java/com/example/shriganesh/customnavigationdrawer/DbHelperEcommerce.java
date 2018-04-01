package com.example.shriganesh.customnavigationdrawer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.shriganesh.customnavigationdrawer.modelclasses.MainCategory;
import com.example.shriganesh.customnavigationdrawer.modelclasses.ShoppingCart;
import com.example.shriganesh.customnavigationdrawer.modelclasses.SubCategory;

import java.util.ArrayList;

/**
 * Created by shri ganesh on 26/03/2017.
 */

public class DbHelperEcommerce extends SQLiteOpenHelper {

    SQLiteDatabase db;

    public DbHelperEcommerce(Context context, String name,
                             SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table maincategory(" +
                "maincategory_id integer primary key, " +
                "maincategory_title text," +
                " maincategory_desc text, " +
                "maincategory_icon text)");

        db.execSQL("create table subcategory(" +
                "subcategory_id integer primary key, " +
                "subcategory_title text," +
                "subcategory_icon text," +
                "subcategory_mainid integer, foreign key(subcategory_mainid) references maincategory(maincategory_id))");

        db.execSQL("create table shoppingcart(product_id integer primary key, " +
                    "product_name text," +
                    "product_price integer," +
                    "product_qty integer," +
                    "product_image text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }

    public long insertMainCategory(MainCategory mainCategory){

        db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("maincategory_id", mainCategory.getMainCatId());
        values.put("maincategory_title",mainCategory.getMainCatTitle());
        values.put("maincategory_desc", mainCategory.getMainCatDesc());
        values.put("maincategory_icon", mainCategory.getMainCatIcon());

        long rowNum = db.insert("mainCategory",null,values);

        return rowNum;
    }

    public ArrayList<MainCategory> getAllMainCategories(){
        db = getWritableDatabase();

        ArrayList<MainCategory> mainCategoryArrayList = new ArrayList<>();

        Cursor c = db.query("maincategory", null, null, null, null, null, null);

        while (c.moveToNext())
        {
            mainCategoryArrayList.add(new MainCategory(c.getInt(0),
                    c.getString(1), c.getString(2), c.getString(3)));
        }

        c.close();
        return mainCategoryArrayList;
    }

    public long insertSubCategory(SubCategory subCategory){
        db = getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put("subcategory_id", subCategory.getSubCategoryId());
        cv.put("subcategory_title", subCategory.getSubCategoryTitle());
        cv.put("subcategory_icon", subCategory.getSubCategoryIcon());
        cv.put("subcategory_mainid", subCategory.getRefMainCategoryId());

        long rowNum = db.insert("subcategory", null, cv);

        return rowNum;
    }

    public ArrayList<SubCategory> getAllSubCategories(int refMainId){
        db = getWritableDatabase();

        ArrayList<SubCategory> subCategoryArrayList = new ArrayList<>();

        Cursor c = db.query("subcategory", new String[] {"subcategory_id," +
                " subcategory_title, subcategory_icon, subcategory_mainid"},
                "subcategory_mainid = ?", new String[] {refMainId +""}, null, null, null, null);

        while (c.moveToNext()){
            subCategoryArrayList.add(new SubCategory(c.getInt(0),
                    c.getString(1), c.getString(2), c.getInt(3)));
       }

       return subCategoryArrayList;
   }

    public long insertIntoShoppingCart(ShoppingCart shoppingCart){

        db = getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put("product_id", shoppingCart.getProductId());
        cv.put("product_name", shoppingCart.getProductName());
        cv.put("product_price", shoppingCart.getProductPrice());
        cv.put("product_qty", shoppingCart.getProductQuantity());
        cv.put("product_image", shoppingCart.getProductImage());

        long rowNum = db.insert("shoppingcart", null, cv);
        Log.d("Text","Value"+rowNum);

        return rowNum;
    }

    public ArrayList<ShoppingCart> getAllProductsFromCart(){
        db = getReadableDatabase();

        ArrayList<ShoppingCart> shoppingCartArrayList = new ArrayList<>();

        Cursor c = db.query("shoppingcart", null, null, null, null, null, null);

        while (c.moveToNext()){
            shoppingCartArrayList.add(new ShoppingCart( c.getInt(0),
                    c.getString(1), c.getInt(2), c.getInt(3), c.getString(4)));
        }
        return shoppingCartArrayList;
    }

   public int removeItemFromCart(int id) {
       db = getWritableDatabase();
       int count = db.delete("shoppingcart", "product_id=?", new String[]{id + ""});
       db.close();
       return count;
   }

    public boolean updateQuantityInCart(int id, int qtyVal){
        db = getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put("product_qty", qtyVal);
        return db.update("shoppingcart", cv,"product_id="+id,null)>0;
    }

    public boolean checkProductIntoCart(int productId){
             db = getReadableDatabase();

             Cursor cursor = db.query("shoppingcart", new String[] {"product_id, product_name, product_price, product_qty, product_image"},
                     "product_id = ?", new String[] {productId +""}, null, null, null, null);

        if (cursor.getCount() == 1){
            return true;
        }
        else
        {
            return false;
        }
    }
}
