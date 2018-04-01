package com.example.shriganesh.customnavigationdrawer.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shriganesh.customnavigationdrawer.DbHelperEcommerce;
import com.example.shriganesh.customnavigationdrawer.R;
import com.example.shriganesh.customnavigationdrawer.modelclasses.ShoppingCart;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by shri ganesh on 29/04/2017.
 */

public class AdapterCart extends RecyclerView.Adapter<AdapterCart.CartViewHolder> {

    private ArrayList<ShoppingCart> mProductCartArrayList;
    private Context mContext;
    private DbHelperEcommerce mDbHelperEcommerce;
    private int mCurrQty;

    public AdapterCart(ArrayList<ShoppingCart> productCartArrayList, Context context) {
        mProductCartArrayList = productCartArrayList;
        mContext = context;
        mDbHelperEcommerce = new DbHelperEcommerce(mContext, "db_ecomm", null, 1);
    }

    @Override
    public CartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.lay_productcart, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final CartViewHolder holder, final int position) {

        final ShoppingCart productsInCart = mProductCartArrayList.get(position);

        Picasso.with(mContext).load(productsInCart.getProductImage()).into(holder.mProductImage);
        holder.mTxtQuantity.setText("Qty = "+productsInCart.getProductQuantity()+"");
        holder.mTxtQtyUpdate.setText(productsInCart.getProductQuantity()+""+"");
        int total =(productsInCart.getProductPrice()) * (productsInCart.getProductQuantity());
        holder.mTxtDetails.setText("Toatal Price = "+"\t"+total+"/-"+"");
        holder.mTxtPrice.setText("Rs. \t"+productsInCart.getProductPrice()+"/-\t");
        holder.mTxtProductName.setText(productsInCart.getProductName());


      holder.mImgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder=new AlertDialog.Builder(mContext);
                builder.setTitle("Remove");
                builder.setMessage("Are You Sure Want To Remove?");
                builder.setIcon(R.drawable.delete);

                DialogInterface.OnClickListener listener=new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (i==dialogInterface.BUTTON_POSITIVE){

                            mDbHelperEcommerce.removeItemFromCart(productsInCart.getProductId());
                            dialogInterface.dismiss();
                            mProductCartArrayList.remove(position);
                            notifyDataSetChanged();
                        }

                        else if (i==dialogInterface.BUTTON_NEGATIVE){
                            dialogInterface.dismiss();
                        }
                    }
                };
                builder.setPositiveButton("Yes",listener);
                builder.setNegativeButton("No",listener);
                builder.show();
            }
        });

        mCurrQty = productsInCart.getProductQuantity();
        holder.mImgPlusQty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.mImgPlusQty.setEnabled(true);
                mCurrQty = mCurrQty + 1;
                holder.mTxtQuantity.setText("Qty = "+mCurrQty+"");
                int total = (productsInCart.getProductPrice())*(mCurrQty);
                holder.mTxtDetails.setText("Toatal Price = "+"\t"+total+"/-"+"");
                holder.mTxtQtyUpdate.setText(mCurrQty +""+"");
                boolean res = mDbHelperEcommerce.updateQuantityInCart(productsInCart.getProductId(), mCurrQty);
                if (!res){
                    Toast.makeText(mContext, "Updation Failed..", Toast.LENGTH_SHORT).show();
                }

            }
        });

        holder.mImgMinisQty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCurrQty == 1){
                    int total = (productsInCart.getProductPrice())*(mCurrQty);
                    holder.mTxtDetails.setText("Toatal Price = "+"\t"+total+"/-"+"");
                    holder.mTxtQuantity.setText("Qty = "+mCurrQty+"");
                    boolean res = mDbHelperEcommerce.updateQuantityInCart(productsInCart.getProductId(), mCurrQty);
                    if (!res){
                        Toast.makeText(mContext, "Updation Failed..", Toast.LENGTH_SHORT).show();
                    }
                    holder.mImgMinisQty.setEnabled(true);
                }
                else {

                    holder.mImgMinisQty.setEnabled(true);
                    mCurrQty = mCurrQty - 1;
                    holder.mTxtQuantity.setText("Qty = "+mCurrQty+"");
                    int total = (productsInCart.getProductPrice())*(mCurrQty);
                    holder.mTxtDetails.setText("Toatal Price = "+"\t"+total+"/-"+"");
                    holder.mTxtQtyUpdate.setText(mCurrQty + ""+"");
                    boolean res = mDbHelperEcommerce.updateQuantityInCart(productsInCart.getProductId(), mCurrQty);
                    if (!res){
                        Toast.makeText(mContext, "Updation Failed..", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mProductCartArrayList.size();
    }

    public class CartViewHolder extends RecyclerView.ViewHolder {

        ImageView mProductImage,mImgDelete, mImgPlusQty, mImgMinisQty;
        TextView mTxtQuantity, mTxtDetails;
        TextView mTxtProductName,mTxtPrice, mTxtQtyUpdate;


        public CartViewHolder(View itemView) {
            super(itemView);
            mImgDelete= (ImageView) itemView.findViewById(R.id.delete);
            mProductImage = (ImageView) itemView.findViewById(R.id.Card_Icon);
            mTxtProductName = (TextView) itemView.findViewById(R.id.Card_title);
            mTxtPrice= (TextView) itemView.findViewById(R.id.Card_Total);
            mTxtDetails = (TextView) itemView.findViewById(R.id.txtDetails);
            mTxtQtyUpdate = (TextView) itemView.findViewById(R.id.qty_update);
            mTxtQuantity = (TextView) itemView.findViewById(R.id.Quantity);
            mImgPlusQty = (ImageView) itemView.findViewById(R.id.imgPlus);
            mImgMinisQty = (ImageView) itemView.findViewById(R.id.imgMinus);
        }
    }
}
