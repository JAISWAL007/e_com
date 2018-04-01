package com.example.shriganesh.customnavigationdrawer;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.transition.Slide;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.shriganesh.customnavigationdrawer.adapters.AdapterMainCategory;
import com.example.shriganesh.customnavigationdrawer.fragments.FragmentCart;
import com.example.shriganesh.customnavigationdrawer.fragments.FragmentSubCategory;
import com.example.shriganesh.customnavigationdrawer.modelclasses.MainCategory;
import com.example.shriganesh.customnavigationdrawer.modelclasses.SubCategory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private Toolbar mToolbar;
    private RecyclerView mDrawerRecycler;
    private ArrayList<MainCategory> mMainCategoryArrayList;
    private AdapterMainCategory mAdapterMainCategory;
    private ActionBarDrawerToggle mActionBarDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private RequestQueue mRequestQueue;
    private NavigationView mNavigationView;
    private FragmentManager mFragmentManager;
    private DrawerLayout drawer;
    private int CART=1, checkNetwork;
    private Snackbar mSnackbar;
    private DbHelperEcommerce mDBHelperEcommerce;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*mFrameLayout = (FrameLayout) findViewById(R.id.activity_main);
        mFrameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "onClick...", Toast.LENGTH_SHORT).show();
                onBackPressed();
            }
        });*/
        mRequestQueue = Volley.newRequestQueue(this);
        mFragmentManager = getSupportFragmentManager();

        mDrawerRecycler = (RecyclerView) findViewById(R.id.mainNavRecycler);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigationView);
        final FrameLayout container = (FrameLayout) findViewById(R.id.activity_main);
        setSupportActionBar(mToolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawerLayout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, mToolbar, R.string.open, R.string.close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        mDBHelperEcommerce = new DbHelperEcommerce(this, "db_ecomm", null, 1);

        mMainCategoryArrayList = new ArrayList<>();

        // Get datafrom database..
        mMainCategoryArrayList = mDBHelperEcommerce.getAllMainCategories();

        mAdapterMainCategory = new AdapterMainCategory(mMainCategoryArrayList, this);
        mDrawerRecycler.setAdapter(mAdapterMainCategory);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mDrawerRecycler.setLayoutManager(linearLayoutManager);
        mAdapterMainCategory.setOnMainCategoryClickListener(new MainCatagoryItemClick());
        if (mMainCategoryArrayList.size() == 0) {
            Toast.makeText(this, "record not found in daabase...", Toast.LENGTH_SHORT).show();
            checkNetwork = NetworkUtil.getConnectivityStatus(this);
            if(checkNetwork == 1 || checkNetwork == 2)
            {
                getMainCategory();
            }
            else
            {
               /* while ((checkNetwork == 1 || checkNetwork == 2)) {
                    mSnackbar = Snackbar.make(container, "Please check your internet connection...", Snackbar.LENGTH_INDEFINITE)
                            .setAction("Retry", new View.OnClickListener() {
                                @Override
                                public void onClick(View view)
                                {
                                    mSnackbar.show();
                                    checkNetwork = NetworkUtil.getConnectivityStatus(MainActivity.this);
                                }
                            });
                }
                mSnackbar.show();*/

                //Toast.makeText(this, "Please check your internet connection...", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Toast.makeText(this, "record found in the daabase...", Toast.LENGTH_SHORT).show();
        }

        navigationView.setNavigationItemSelectedListener(MainActivity.this);
        mAdapterMainCategory.setOnMainCategoryClickListener(new MainCatagoryItemClick());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0,CART,0,"cart").setIcon(R.drawable.ic_cart).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == CART){
            FragmentCart fragmentCart = new FragmentCart();
            fragmentCart.setEnterTransition(new Slide(Gravity.RIGHT));
            fragmentCart.setExitTransition(new Slide(Gravity.LEFT) );
            mFragmentManager.beginTransaction().add(R.id.activity_main, fragmentCart, null)
                    .addToBackStack("").commit();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        drawer = (DrawerLayout) findViewById(R.id.drawerLayout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

        mActionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.open, R.string.close)
        {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
        mDrawerLayout.addDrawerListener(mActionBarDrawerToggle);
        mActionBarDrawerToggle.syncState();

        mNavigationView = new NavigationView(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void getMainCategory(){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                "http://bitcodetech.in/ecommerce/ws/ws_category_with_sub.php?cmd=get&type=cat",
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            boolean status = response.getBoolean("status");

                            if(status == true){
                                JSONArray jsonArray = response.getJSONArray("results");

                                for (int i=0; i<jsonArray.length(); i++){
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                                    MainCategory mainCategory = new MainCategory();

                                    mainCategory.setMainCatId(Integer.parseInt(jsonObject.getString("maincategory_id")));

                                    int refMainId= Integer.parseInt(jsonObject.getString("maincategory_id"));

                                    mainCategory.setMainCatTitle(jsonObject.getString("maincategory_title"));
                                    mainCategory.setMainCatIcon(jsonObject.getString("maincategory_icon"));
                                    mainCategory.setMainCatDesc(jsonObject.getString("maincategory_description"));
                                    mDBHelperEcommerce.insertMainCategory(mainCategory);

                                    if(jsonObject.has("subcategories")) {
                                        JSONArray subCategory = jsonObject.getJSONArray("subcategories");

                                        for (int j = 0; j < subCategory.length(); j++) {
                                            JSONObject subcategory = subCategory.getJSONObject(j);

                                            SubCategory subCat = new SubCategory();

                                            subCat.setSubCategoryTitle(subcategory.getString("subcategory_title"));
                                            subCat.setSubCategoryId(Integer.parseInt(subcategory.getString("subcategory_id")));
                                            subCat.setSubCategoryIcon(subcategory.getString("subcategory_icon"));
                                            subCat.setRefMainCategoryId(refMainId);

                                            mDBHelperEcommerce.insertSubCategory(subCat);
                                            mainCategory.setSubCategoryArrayList(subCat);
                                        }
                                    }
                                    mMainCategoryArrayList.add(mainCategory);
                                    mAdapterMainCategory.notifyDataSetChanged();
                                }
                            }
                        }
                        catch (JSONException e) {
                            Toast.makeText(MainActivity.this, "Respone is Null....", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "VOLLEY ERROR", Toast.LENGTH_SHORT).show();
                    }
                }
        );
        mRequestQueue.add(jsonObjectRequest);
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(15000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }
    class MainCatagoryItemClick implements AdapterMainCategory.OnMainCategoryClickListener{
        @Override
        public void onMainCategoryClick(View v, int pos) {
            FragmentSubCategory fragmentSubCategory = new FragmentSubCategory();
            Bundle bundle = new Bundle();
            bundle.putSerializable("mainCategoryTitle", mMainCategoryArrayList.get(pos));
            fragmentSubCategory.setArguments(bundle);
            //set animation to open next fragment...
            fragmentSubCategory.setEnterTransition(new Slide(Gravity.LEFT));
            fragmentSubCategory.setExitTransition(new Slide(Gravity.RIGHT) );
            mFragmentManager.beginTransaction().add(R.id.activity_main,fragmentSubCategory,null).addToBackStack("").commit();
            onBackPressed();
        }
    }
}
