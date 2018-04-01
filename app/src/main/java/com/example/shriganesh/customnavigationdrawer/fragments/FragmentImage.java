package com.example.shriganesh.customnavigationdrawer.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.shriganesh.customnavigationdrawer.R;
import com.example.shriganesh.customnavigationdrawer.adapters.PagerImageAdapter;
import com.example.shriganesh.customnavigationdrawer.modelclasses.Images;

import java.util.ArrayList;

/**
 * Created by shri ganesh on 08/05/2017.
 */

public class FragmentImage extends Fragment {

    private ArrayList<Images> mImagesArrayList;
    private ViewPager mViewPagerImages;
    private PagerImageAdapter mPagerImageAdapter;
    private int mImageClickPosition;
    private Toolbar mToolbar;
    private FragmentManager mFragmentManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.lay_images, null);
        Toast.makeText(getActivity(), "Pinch with two fingers to zoom image", Toast.LENGTH_LONG).show();
        mViewPagerImages = (ViewPager) view.findViewById(R.id.imagesViewPager);
        mToolbar = (android.widget.Toolbar) view.findViewById(R.id.imagestoolbar);
        mFragmentManager = getFragmentManager();

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar();
        mToolbar.setTitle(R.string.app_name);
        if (activity.getSupportActionBar() != null) {

            mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        }
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mFragmentManager.popBackStack();

            }
        });

        mFragmentManager = getFragmentManager();
        Bundle bundle = getArguments();
        mImagesArrayList = new ArrayList<>();
        mImagesArrayList = (ArrayList<Images>) bundle.getSerializable("imagesArray");
        mImageClickPosition = (int) bundle.getSerializable("pos");
        mPagerImageAdapter = new PagerImageAdapter(mImagesArrayList, getActivity());
        mViewPagerImages.setAdapter(mPagerImageAdapter);
        mViewPagerImages.setCurrentItem(mImageClickPosition);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        return view;
    }
}
