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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shriganesh.customnavigationdrawer.DbHelperEcommerce;
import com.example.shriganesh.customnavigationdrawer.QuantityDialog;
import com.example.shriganesh.customnavigationdrawer.R;
import com.example.shriganesh.customnavigationdrawer.adapters.AdapterImages;
import com.example.shriganesh.customnavigationdrawer.modelclasses.Images;
import com.example.shriganesh.customnavigationdrawer.modelclasses.Products;
import com.example.shriganesh.customnavigationdrawer.modelclasses.ShoppingCart;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by shri ganesh on 05/04/2017.
 */

public class FragmentProductDetails extends Fragment {

    private ImageView mProductImage;
    private TextView mTxtProductPrice,mTxtProductTitle;
    private Toolbar mProductDetailsToolbar;
    private FragmentManager mFragmentManager;
    private Products mProducts;
    private DbHelperEcommerce mDbHelperEcommerce;
    private Button mBtnAddToCart, mBtnBuyNow;
    private QuantityDialog mQuantityDialog;
    private ArrayList<Images> mImagesArrayList;
    private AdapterImages mAdapterImages;
    private RecyclerView mImagesRecycler;
    private int mQuantity, mImageClickPos;
    private String mBtnStatus;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.lay_productdetails, null);

        mProductImage = (ImageView) view.findViewById(R.id.productMainImage);
        mTxtProductPrice = (TextView) view.findViewById(R.id.txtProductPrice);
        mTxtProductTitle = (TextView) view.findViewById(R.id.txtProductTitle);
        mBtnAddToCart = (Button) view.findViewById(R.id.btnAddToCart);
        mBtnBuyNow = (Button) view.findViewById(R.id.btnBuyNow);
        mImagesRecycler = (RecyclerView) view.findViewById(R.id.productImagesRecycler);

        mDbHelperEcommerce = new DbHelperEcommerce(getActivity(), "db_ecomm", null, 1);

        mFragmentManager = getFragmentManager();
        mProductDetailsToolbar = (Toolbar) view.findViewById(R.id.detailstoolbar);
        mProductDetailsToolbar.setTitle(R.string.productdetails);

        Picasso.with(getActivity()).load(mProducts.getImagesArrayList().get(0).getProductImage())
                .into(mProductImage);

        mTxtProductPrice.setText("Rs." +mProducts.getProductPrice()+"\t");
        mTxtProductTitle.setText(mProducts.getProductTitle());

        AppCompatActivity appCompatActivity = (AppCompatActivity) getActivity();
        appCompatActivity.setSupportActionBar(mProductDetailsToolbar);

        if(appCompatActivity.getSupportActionBar()!= null){

            mProductDetailsToolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        }
        mProductDetailsToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mFragmentManager.popBackStack();

            }
        });

        mProductImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentImage fragmentImage = new FragmentImage();
                Bundle bundle = new Bundle();
                bundle.putSerializable("imagesArray", mImagesArrayList);
                bundle.putSerializable("pos",mImageClickPos);

                fragmentImage.setArguments(bundle);
                fragmentImage.setEnterTransition(new Slide(Gravity.LEFT));
                fragmentImage.setExitTransition(new Slide(Gravity.RIGHT) );
                mFragmentManager.beginTransaction().add(R.id.activity_main, fragmentImage, null).addToBackStack("backstack").commit();
            }
        });

        mImagesArrayList = new ArrayList<>();
        mImagesArrayList = mProducts.getImagesArrayList();
        mAdapterImages = new AdapterImages(getActivity(), mImagesArrayList);

        mImagesRecycler.setAdapter(mAdapterImages);
        mAdapterImages.setOnImageClickListener(new GetImageClickPosition());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mImagesRecycler.setLayoutManager(linearLayoutManager);

        mBtnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                   chechProductInToCart();
                if (mBtnStatus.equals("ADD TO CART"))
                {
                    mQuantityDialog = new QuantityDialog(getActivity());
                    mQuantityDialog.show();
                    mQuantityDialog.setOnBtnOkClickListener(new GetQuantity());
                }
                if (mBtnStatus.equals("GO To CART"))
                {
                    FragmentCart fragmentCart = new FragmentCart();
                    fragmentCart.setEnterTransition(new Slide(Gravity.LEFT));
                    fragmentCart.setExitTransition(new Slide(Gravity.RIGHT) );
                    mFragmentManager.beginTransaction().add(R.id.activity_main, fragmentCart, null)
                            .addToBackStack("BackStack")
                            .commit();
                }
            }
        });

        mBtnBuyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    FragmentLogin fragmentLogin = new FragmentLogin();
                    fragmentLogin.setEnterTransition(new Slide(Gravity.LEFT));
                    fragmentLogin.setExitTransition(new Slide(Gravity.RIGHT) );
                    mFragmentManager.beginTransaction().add(R.id.activity_main, fragmentLogin, null)
                            .addToBackStack("BackStack")
                            .commit();
            }
        });
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chechProductInToCart();
            }
        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        chechProductInToCart();
    }

    @Override
    public void onResume() {
        super.onResume();
        chechProductInToCart();
    }

    public void setProductPosition(Products products){

        mProducts = products;
    }

    public class GetQuantity implements QuantityDialog.OnBtnOkClickListener{

        @Override
        public void onOkClick(int qty) {
             mQuantity = qty;
            ShoppingCart shoppingCart = new ShoppingCart();
            shoppingCart.setProductId(mProducts.getProductId());
            shoppingCart.setProductName(mProducts.getProductTitle());
            shoppingCart.setProductPrice(mProducts.getProductPrice());
            shoppingCart.setProductQuantity(mQuantity);
            shoppingCart.setProductImage(mProducts.getImagesArrayList().get(0).getProductImage());

            mDbHelperEcommerce.insertIntoShoppingCart(shoppingCart);
            Toast.makeText(getActivity(), "item added to cart.", Toast.LENGTH_SHORT).show();
            mBtnAddToCart.setText(R.string.gotocart);
        }
    }

    class GetImageClickPosition implements AdapterImages.OnImageClickListener{

        @Override
        public void onImageClick(int pos) {
            mImageClickPos = pos;
            Picasso.with(getActivity()).load(mImagesArrayList.get(mImageClickPos).getProductImage()).into(mProductImage);
        }
    }

    public void chechProductInToCart()
    {
        boolean status = mDbHelperEcommerce.checkProductIntoCart(mProducts.getProductId());

        if(status){
            mBtnAddToCart.setText(R.string.gotocart);
            mBtnStatus = mBtnAddToCart.getText().toString();
        }
        else
        {
            mBtnAddToCart.setText(R.string.addtocart);
            mBtnStatus = mBtnAddToCart.getText().toString();
        }
    }
}
