package com.example.shriganesh.customnavigationdrawer.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.transition.Slide;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.shriganesh.customnavigationdrawer.DbHelperEcommerce;
import com.example.shriganesh.customnavigationdrawer.NetworkUtil;
import com.example.shriganesh.customnavigationdrawer.R;
import com.example.shriganesh.customnavigationdrawer.adapters.AdapterSubCategory;
import com.example.shriganesh.customnavigationdrawer.modelclasses.MainCategory;
import com.example.shriganesh.customnavigationdrawer.modelclasses.SubCategory;

import java.util.ArrayList;

/**
 * Created by shri ganesh on 26/03/2017.
 */

public class FragmentSubCategory extends Fragment {

    private RecyclerView mRecyclerView;
    private AdapterSubCategory mAdapterSubCategory;
    private FragmentManager mFragmentManager;
    private DbHelperEcommerce mDBHelperEcommerce;
    private ArrayList<SubCategory> mSubCategoryArrayList;
    private Toolbar mSubCategoryToolbar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.lay_subcategories, null);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.subcategoryRecycler);
        mSubCategoryToolbar = (Toolbar) view.findViewById(R.id.subcategorytoolbar);

        mDBHelperEcommerce = new DbHelperEcommerce(getActivity(), "db_ecomm", null, 1);

        mFragmentManager = getFragmentManager();

        Bundle bundle = getArguments();
        MainCategory mainCategory = (MainCategory) bundle.getSerializable("mainCategoryTitle");

        AppCompatActivity appCompatActivity = (AppCompatActivity) getActivity();
        appCompatActivity.setSupportActionBar(mSubCategoryToolbar);

        if(appCompatActivity.getSupportActionBar()!= null){

            mSubCategoryToolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        }
        mSubCategoryToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mFragmentManager.popBackStack();

            }
        });
        mSubCategoryArrayList = new ArrayList<>();
        mSubCategoryArrayList = mDBHelperEcommerce.getAllSubCategories(mainCategory.getMainCatId());

        mAdapterSubCategory = new AdapterSubCategory(mSubCategoryArrayList, getActivity());
        mRecyclerView.setAdapter(mAdapterSubCategory);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mSubCategoryToolbar.setTitle(mainCategory.getMainCatTitle());

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        mAdapterSubCategory.setSelectProductListener(new GetProducts());

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        return view;
    }

    public class GetProducts implements AdapterSubCategory.SelectProductListener{

        @Override
        public void selectProduct(int position) {
            // before going to products frament check the internet connectivity...

            int checkNetwork = NetworkUtil.getConnectivityStatus(getActivity());
            if(checkNetwork == 1 || checkNetwork == 2)
            {
                FragmentProducts fragmentProducts = new FragmentProducts();
                Bundle bundle = new Bundle();
                bundle.putSerializable("subcategoryTitle", mSubCategoryArrayList.get(position));
                fragmentProducts.setArguments(bundle);
                fragmentProducts.setEnterTransition(new Slide(Gravity.BOTTOM));
                fragmentProducts.setExitTransition(new Slide(Gravity.TOP));
                mFragmentManager.beginTransaction().add(R.id.activity_main, fragmentProducts, null).addToBackStack("BackStack").commit();
            }
            else
            {
                Toast.makeText(getActivity(), "Please check your internet connection...", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
