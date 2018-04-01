package com.example.shriganesh.customnavigationdrawer.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shriganesh.customnavigationdrawer.R;
import com.example.shriganesh.customnavigationdrawer.modelclasses.MainCategory;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by shri ganesh on 25/03/2017.
 */

public class AdapterMainCategory extends RecyclerView.Adapter<AdapterMainCategory.CategoryViewHolder> {

    private ArrayList<MainCategory> mMainCategoryArrayList;
    private Context mContext;
    private OnMainCategoryClickListener mOnMainCategoryClickListener;

    public AdapterMainCategory(ArrayList<MainCategory> mainCategoryArrayList, Context context) {
        mMainCategoryArrayList = mainCategoryArrayList;
        mContext = context;
    }

    public interface OnMainCategoryClickListener
    {
        void onMainCategoryClick(View v,int pos);
    }

    public void setOnMainCategoryClickListener(OnMainCategoryClickListener listener){
        mOnMainCategoryClickListener  = listener;
    }

    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.lay_navdraweritem, parent,false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CategoryViewHolder holder, int position) {

        MainCategory mainCategory = mMainCategoryArrayList.get(position);

        Picasso.with(mContext).load(mMainCategoryArrayList.get(position).getMainCatIcon()).into(holder.mMainIcon);
        holder.mTxtMainTitle.setText(mainCategory.getMainCatTitle());
    }

    @Override
    public int getItemCount() {
        return mMainCategoryArrayList.size();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView mMainIcon;
        TextView mTxtMainTitle;

        public CategoryViewHolder(View itemView) {

            super(itemView);
            itemView.setOnClickListener(this);
            mMainIcon = (ImageView) itemView.findViewById(R.id.mainImageIcon);
            mTxtMainTitle = (TextView) itemView.findViewById(R.id.mainTxtInfo);
        }

        @Override
        public void onClick(View view) {
            mOnMainCategoryClickListener.onMainCategoryClick(view,getAdapterPosition());
        }
    }
}
