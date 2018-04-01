package com.example.shriganesh.customnavigationdrawer.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.transition.Slide;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.shriganesh.customnavigationdrawer.R;
import com.example.shriganesh.customnavigationdrawer.adapters.AdapterProducts;
import com.example.shriganesh.customnavigationdrawer.modelclasses.Images;
import com.example.shriganesh.customnavigationdrawer.modelclasses.Products;
import com.example.shriganesh.customnavigationdrawer.modelclasses.SubCategory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by shri ganesh on 29/03/2017.
 */

public class FragmentProducts extends Fragment {

    private ArrayList<Products> mProductsArrayList;
    private AdapterProducts mAdapterProducts;
    private FragmentManager mFragmentManager;
    private RecyclerView mRecyclerView;
    private Toolbar mProductToolbar;
    private RequestQueue mRequestQueue;
    private ProgressBar mProgressBar;
    private SubCategory subCategory;
    private int subId;
    private String url = "http://bitcodetech.in/ecommerce/ws/ws_products.php?cmd=get&type=products&mid=1&sid=";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {

        mFragmentManager = getFragmentManager();


            View view = inflater.inflate(R.layout.lay_products, null);

            mProgressBar = (ProgressBar) view.findViewById(R.id.progressBar);
            mProductToolbar = (Toolbar) view.findViewById(R.id.productstoolbar);
            mProgressBar.setVisibility(View.INVISIBLE);
            mProgressBar.setBackgroundColor(Color.WHITE);

            Bundle bundle = getArguments();
            subCategory = (SubCategory) bundle.getSerializable("subcategoryTitle");

            subId = subCategory.getSubCategoryId();

            mProductToolbar.setTitle(subCategory.getSubCategoryTitle());

            AppCompatActivity appCompatActivity = (AppCompatActivity) getActivity();
            appCompatActivity.setSupportActionBar(mProductToolbar);

            if(appCompatActivity.getSupportActionBar()!= null){

                mProductToolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
            }
            mProductToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    mFragmentManager.popBackStack();

                }
            });

            mRecyclerView = (RecyclerView) view.findViewById(R.id.subcategoryRecycler);

            mRequestQueue = Volley.newRequestQueue(getActivity());

            mProductsArrayList = new ArrayList<>();
            mAdapterProducts = new AdapterProducts(mProductsArrayList, getActivity());

            getProducts();


            mRecyclerView.setAdapter(mAdapterProducts);

            mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

            mAdapterProducts.setOnProductClickListener(new ProductClickListener());
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
            return view;
        }


    public void getProducts() {
        mProgressBar.setVisibility(View.VISIBLE);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url+subId,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                           boolean status = response.getBoolean("status");

                            if(status){
                                JSONArray jsonArray = response.getJSONArray("results");

                                for (int i=0;i<jsonArray.length(); i++){
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                                    Products products = new Products();
                                    products.setProductPrice(Integer.parseInt(jsonObject.getString("product_actualprice")));
                                    products.setProductTitle(jsonObject.getString("product_title"));
                                    products.setProductId(Integer.parseInt(jsonObject.getString("product_id")));

                                    if(jsonObject.has("images")) {
                                        JSONArray imagesArray = jsonObject.getJSONArray("images");

                                        for (int j=0 ; j<imagesArray.length(); j++) {

                                            JSONObject mainImage = imagesArray.getJSONObject(j);

                                            Images images = new Images();
                                            images.setProductImages(mainImage.getString("product_imageurl"));
                                            products.setImagesArrayList(images);
                                        }
                                    }
                                    mProductsArrayList.add(products);
                                    mAdapterProducts.notifyDataSetChanged();
                                }
                            }
                            else {
                                return;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        mProgressBar.setVisibility(View.GONE);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("VOLLEY","ERROR");
                    }
                }
        );
        mRequestQueue.add(jsonObjectRequest);
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(20000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }
    class ProductClickListener implements AdapterProducts.OnProductClickListener{

        @Override
        public void onProductClick(int position) {
            FragmentProductDetails fragmentProductDetails = new FragmentProductDetails();

            // set click product to the details fragment...
            fragmentProductDetails.setProductPosition(mProductsArrayList.get(position));
            fragmentProductDetails.setEnterTransition(new Slide(Gravity.BOTTOM));
            fragmentProductDetails.setExitTransition(new Slide(Gravity.TOP));
            mFragmentManager.beginTransaction().add(R.id.activity_main, fragmentProductDetails, null)
                    .addToBackStack("BackStack").commit();
        }
    }
}



