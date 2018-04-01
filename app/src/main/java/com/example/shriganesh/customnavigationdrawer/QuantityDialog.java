package com.example.shriganesh.customnavigationdrawer;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by shri ganesh on 30/04/2017.
 */

public class QuantityDialog extends Dialog {

    private ImageView mBtnPlus, mBtnMinus;
    private Button mBtnOk;
    private TextView mTxtQty;

    public QuantityDialog(Context context) {
        super(context);
        hookup();
    }

    public  interface OnBtnOkClickListener{
        void onOkClick(int qty);
    }

    private OnBtnOkClickListener mOnBtnOkClickListener;

    public void setOnBtnOkClickListener(OnBtnOkClickListener listener){
        mOnBtnOkClickListener = listener;
    }

    private void hookup(){
        setContentView(R.layout.lay_qtydialog);

        mBtnPlus = (ImageView) findViewById(R.id.btnPlus);
        mBtnMinus = (ImageView) findViewById(R.id.btnminus);
        mTxtQty = (TextView) findViewById(R.id.txtCount);
        mBtnOk = (Button) findViewById(R.id.btnOk);

        mBtnPlus.setOnClickListener(new BtnOkListener());
        mBtnMinus.setOnClickListener(new BtnOkListener());
        mBtnMinus.setEnabled(false);
        mBtnOk.setOnClickListener(new BtnOkListener());
    }

    private class BtnOkListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if(view.equals(mBtnPlus)){

                mBtnMinus.setEnabled(true);
                String qtyPlus = String.valueOf(Integer.parseInt(mTxtQty.getText().toString())+1);
                mTxtQty.setText(qtyPlus);
            }
            if (view.equals(mBtnMinus)){
                if(Integer.parseInt(mTxtQty.getText().toString()) == 1)
                {
                    mBtnMinus.setEnabled(false);
                }
                else {
                    mBtnMinus.setEnabled(true);
                    String qtyMinnus = String.valueOf(Integer.parseInt(mTxtQty.getText().toString()) - 1);
                    mTxtQty.setText(qtyMinnus);
                }
            }
            if (view.equals(mBtnOk)){

                int qty = Integer.parseInt(String.valueOf(mTxtQty.getText()));
                mOnBtnOkClickListener.onOkClick(qty);
                dismiss();
            }
        }
    }
}
