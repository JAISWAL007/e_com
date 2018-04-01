package com.example.shriganesh.customnavigationdrawer.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.shriganesh.customnavigationdrawer.R;
import com.example.shriganesh.customnavigationdrawer.modelclasses.Images;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by shri ganesh on 08/05/2017.
 */

public class PagerImageAdapter extends PagerAdapter {

    private ArrayList<Images> mImagesArrayList;
    private Context mContext;
    private ImageView mImageView;
    private PhotoViewAttacher mPhotoViewAttacher;

    public PagerImageAdapter(ArrayList<Images> imagesArrayList, Context context) {
        this.mImagesArrayList = imagesArrayList;
        mContext = context;
    }

    @Override
    public int getCount() {
        return mImagesArrayList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view==(LinearLayout)object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.lay_pager_image_adapter, container, false);
        Images images = mImagesArrayList.get(position);

         mImageView = (ImageView) view.findViewById(R.id.fullimage);
         Picasso.with(mContext).load(images.getProductImage()).into(mImageView);
         container.addView(view);
         mPhotoViewAttacher = new PhotoViewAttacher(mImageView);
         mPhotoViewAttacher.update();
         return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout)object);
    }

}
