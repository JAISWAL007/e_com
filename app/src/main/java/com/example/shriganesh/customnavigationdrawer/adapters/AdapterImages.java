package com.example.shriganesh.customnavigationdrawer.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.shriganesh.customnavigationdrawer.R;
import com.example.shriganesh.customnavigationdrawer.modelclasses.Images;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by shri ganesh on 01/05/2017.
 */

public class AdapterImages extends RecyclerView.Adapter<AdapterImages.ImageViewHolder>{

    private Context mContext;
    private ArrayList<Images> mImagesArrayList;

    public AdapterImages(Context context, ArrayList<Images> imagesArrayList) {
        this.mContext = context;
        this.mImagesArrayList = imagesArrayList;
    }

    public interface OnImageClickListener{
        void onImageClick(int pos);
    }

    public   OnImageClickListener mOnImageClickListener;

    public void setOnImageClickListener(OnImageClickListener  listener){
        mOnImageClickListener = listener;
    }
    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.lay_image,parent,false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {

        Images images = mImagesArrayList.get(position);
        Picasso.with(mContext).load(images.getProductImage()).into(holder.mProductImages);
    }

    @Override
    public int getItemCount() {
        return mImagesArrayList.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView mProductImages;

        public ImageViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);
            mProductImages = (ImageView) itemView.findViewById(R.id.productImages);
        }

        @Override
        public void onClick(View view) {
            mOnImageClickListener.onImageClick(getPosition());
        }
    }
}
