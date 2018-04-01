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
import android.widget.Button;

import com.example.shriganesh.customnavigationdrawer.DbHelperEcommerce;
import com.example.shriganesh.customnavigationdrawer.R;
import com.example.shriganesh.customnavigationdrawer.adapters.AdapterCart;
import com.example.shriganesh.customnavigationdrawer.modelclasses.ShoppingCart;

import java.util.ArrayList;

/**
 * Created by shri ganesh on 31/03/2017.
 */

public class FragmentCart extends Fragment {

    private Toolbar mCartToolbar;
    private RecyclerView mCartRecycler;
    private ArrayList<ShoppingCart> mShoppingCartArrayList;
    private AdapterCart mAdapterCart;
    private DbHelperEcommerce mDbHelperEcommerce;
    private FragmentManager mFragmentManager;
    private Button mBtnContinue;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.lay_cart, null);

        mCartToolbar = (Toolbar) view.findViewById(R.id.carttoolbar);
        mCartRecycler = (RecyclerView) view.findViewById(R.id.cartRecycler);
        mBtnContinue = (Button) view.findViewById(R.id.btnContinue);
        mFragmentManager = getFragmentManager();

        mDbHelperEcommerce = new DbHelperEcommerce(getActivity(), "db_ecomm", null, 1);

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar();
        mCartToolbar.setTitle(R.string.mycart);
        if (activity.getSupportActionBar() != null) {

            mCartToolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        }
        mCartToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mFragmentManager.popBackStack();
            }
        });

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        mShoppingCartArrayList = new ArrayList<>();
        // get all products from datanbase...
        mShoppingCartArrayList = mDbHelperEcommerce.getAllProductsFromCart();
        mAdapterCart = new AdapterCart(mShoppingCartArrayList, getActivity());
        mCartRecycler.setAdapter(mAdapterCart);
        mAdapterCart.notifyDataSetChanged();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mCartRecycler.setLayoutManager(linearLayoutManager);

        mBtnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentLogin fragmentLogin = new FragmentLogin();
                fragmentLogin.setEnterTransition(new Slide(Gravity.LEFT));
                fragmentLogin.setExitTransition(new Slide(Gravity.RIGHT) );
                mFragmentManager.beginTransaction().add(R.id.activity_main, fragmentLogin, null).addToBackStack("BackStack").commit();
            }
        });
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        return view;
    }
}

