package com.example.shriganesh.customnavigationdrawer.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shriganesh.customnavigationdrawer.R;
import com.example.shriganesh.customnavigationdrawer.modelclasses.SubCategory;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by shri ganesh on 26/03/2017.
 */

public class AdapterSubCategory extends RecyclerView.Adapter<AdapterSubCategory.SubCategoryViewHolder> {


    private ArrayList<SubCategory> mSubCategoryArrayList;
    private Context mContext;
    private SelectProductListener mSelectProductListener;

    public AdapterSubCategory(ArrayList<SubCategory> subCategoryArrayList, Context context){
        mSubCategoryArrayList = subCategoryArrayList;
        mContext = context;
    }

    public interface SelectProductListener{
        void selectProduct(int position);
    }

    public void setSelectProductListener(SelectProductListener selectProductListener){
        mSelectProductListener = selectProductListener;
    }
    @Override
    public SubCategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.lay_adaptersubcategory, parent, false);
        return new SubCategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SubCategoryViewHolder holder, int position) {

        SubCategory subCategory = mSubCategoryArrayList.get(position);
        Picasso.with(mContext).load(subCategory.getSubCategoryIcon()).into(holder.mImageSubCategoryIcon);
        holder.mTxtSubCategory.setText(subCategory.getSubCategoryTitle());
    }

    @Override
    public int getItemCount() {
        return mSubCategoryArrayList.size();
    }

    public class SubCategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView mTxtSubCategory;
        ImageView mImageSubCategoryIcon;

        public SubCategoryViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);
            mTxtSubCategory = (TextView) itemView.findViewById(R.id.txtSubCategory);
            mImageSubCategoryIcon = (ImageView) itemView.findViewById(R.id.subCategoryIcon);
        }

        @Override
        public void onClick(View view) {
            mSelectProductListener.selectProduct(getPosition());
        }
    }
}
