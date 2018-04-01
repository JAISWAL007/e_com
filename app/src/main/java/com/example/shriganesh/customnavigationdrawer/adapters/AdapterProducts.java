package com.example.shriganesh.customnavigationdrawer.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shriganesh.customnavigationdrawer.R;
import com.example.shriganesh.customnavigationdrawer.modelclasses.Images;
import com.example.shriganesh.customnavigationdrawer.modelclasses.Products;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by shri ganesh on 29/03/2017.
 */

public class AdapterProducts extends RecyclerView.Adapter<AdapterProducts.ProductViewHolder>{

    private ArrayList<Products> mProdutsList;
    private Context mContext;
    private OnProductClickListener mOnProductClickListener;

    public AdapterProducts(ArrayList<Products> productsArrayList, Context context){
        mProdutsList = productsArrayList;
        mContext = context;
    }

    public interface OnProductClickListener
    {
        void onProductClick(int position);
    }

    public void setOnProductClickListener(OnProductClickListener productListener){
        mOnProductClickListener = productListener;
    }
    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.lay_product_card, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {

         Products products = mProdutsList.get(position);

          ArrayList<Images> imagesArrayList = products.getImagesArrayList();
        // get first image of the product from images array list...
         Images images = imagesArrayList.get(0);
         Picasso.with(mContext).load(images.getProductImage()).into(holder.mProductImage);
         holder.mTxtProductTitle.setText(products.getProductTitle() );
        holder.mTxtPrice.setText("Rs."+products.getProductPrice()+"\t");

            }

    @Override
    public int getItemCount() {
        return mProdutsList.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder implements RecyclerView.OnClickListener{

        TextView mTxtProductTitle,mTxtPrice;
        ImageView mProductImage;

        public ProductViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);
            mTxtProductTitle = (TextView) itemView.findViewById(R.id.productTitle);
            mProductImage = (ImageView) itemView.findViewById(R.id.productImage);
            mTxtPrice= (TextView) itemView.findViewById(R.id.productPrice);
        }
        @Override
        public void onClick(View view) {
             mOnProductClickListener.onProductClick(getAdapterPosition());
        }
    }
}
