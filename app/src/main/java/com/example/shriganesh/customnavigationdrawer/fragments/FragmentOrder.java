package com.example.shriganesh.customnavigationdrawer.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toolbar;

import com.example.shriganesh.customnavigationdrawer.R;

/**
 * Created by shri ganesh on 25/05/2017.
 */

public class FragmentOrder extends Fragment {

    private Toolbar mToolbar;
    private FragmentManager mFragmentManager;
    private Button mBtnCurrLoc, mBtnPlaceOrder;
    private TextInputLayout mTxtInputCity, mTxtInputArea, mTxtInputFlatno, mTxtInputPincode, mTxtInputState, mTxtInputLandmark, mTxtInputName, mTxtInputPhoneNo, mTxtInputAltPhoneNo;
    private EditText mEdtCity, mEdtArea, mEdtFlatno, mEdtPincode, mEdtState, mEdtLandmark, mEdtName, mEdtPhoneNo, mEdtAltPhoneNo;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.lay_order, null);
        mToolbar = (Toolbar) view.findViewById(R.id.orderTollbar);
        mFragmentManager = getFragmentManager();

        mBtnCurrLoc = (Button) view.findViewById(R.id.btnCurrenLocation);
        mBtnPlaceOrder = (Button) view.findViewById(R.id.btnPlaceOrder);

        mTxtInputCity = (TextInputLayout) view.findViewById(R.id.txtInputCity);
        mTxtInputArea = (TextInputLayout) view.findViewById(R.id.txtInputArea);
        mTxtInputFlatno = (TextInputLayout) view.findViewById(R.id.txtInputFlat);
        mTxtInputPincode = (TextInputLayout) view.findViewById(R.id.txtInputPincode);
        mTxtInputState = (TextInputLayout) view.findViewById(R.id.txtInputState);
        mTxtInputLandmark = (TextInputLayout) view.findViewById(R.id.txtInputLandmark);
        mTxtInputName = (TextInputLayout) view.findViewById(R.id.txtInputName);
        mTxtInputPhoneNo = (TextInputLayout) view.findViewById(R.id.txtInputPhoneNo);
        mTxtInputAltPhoneNo = (TextInputLayout) view.findViewById(R.id.txtInputAlternamePhone);

        mEdtCity = (EditText) view.findViewById(R.id.edtCity);
        mEdtArea = (EditText) view.findViewById(R.id.edtArea);
        mEdtFlatno = (EditText) view.findViewById(R.id.edtFlat);
        mEdtPincode = (EditText) view.findViewById(R.id.edtPincode);
        mEdtState = (EditText) view.findViewById(R.id.edtState);
        mEdtLandmark = (EditText) view.findViewById(R.id.edtLandmark);
        mEdtName = (EditText) view.findViewById(R.id.edtName);
        mEdtPhoneNo = (EditText) view.findViewById(R.id.edtPhone);
        mEdtAltPhoneNo = (EditText) view.findViewById(R.id.edtAlternamePhone);

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar();
        mToolbar.setTitle("\t\t\t\t\t");
        mToolbar.setTitle(R.string.ordertitle);
        if (activity.getSupportActionBar() != null) {

            mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        }
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mFragmentManager.popBackStack();
            }
        });

        mBtnPlaceOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(mEdtCity.getText().toString()))
                {
                    mTxtInputCity.setError("Please Enter City");
                }
                if (TextUtils.isEmpty(mEdtArea.getText().toString()))
                {
                    mTxtInputArea.setError("Please Enter Area");
                }
                if (TextUtils.isEmpty(mEdtFlatno.getText().toString()))
                {
                    mTxtInputFlatno.setError("Please Enter Flatno");
                }
                if (TextUtils.isEmpty(mEdtPincode.getText().toString()))
                {
                    mTxtInputPincode.setError("Please Enter Pincode");
                }
                if (TextUtils.isEmpty(mEdtState.getText().toString()))
                {
                    mTxtInputState.setError("Please Enter State");
                }
                if (TextUtils.isEmpty(mEdtPhoneNo.getText().toString()))
                {
                    mTxtInputPhoneNo.setError("Please Enter PhoneNo");
                }
                if (!(TextUtils.isEmpty(mEdtPhoneNo.getText().toString()) && TextUtils.isEmpty(mEdtCity.getText().toString()) &&
                        TextUtils.isEmpty(mEdtArea.getText().toString()) && TextUtils.isEmpty(mEdtFlatno.getText().toString()) &&
                        TextUtils.isEmpty(mEdtPincode.getText().toString()) && TextUtils.isEmpty(mEdtState.getText().toString()) &&
                        TextUtils.isEmpty(mEdtPhoneNo.getText().toString())) )
                {
                    //    Post order data from here....
                }
                else {
                    mTxtInputCity.setError("Please Enter City");
                    mTxtInputArea.setError("Please Enter Area");
                    mTxtInputFlatno.setError("Please Enter Flatno");
                    mTxtInputPincode.setError("Please Enter Pincode");
                    mTxtInputState.setError("Please Enter State");
                    mTxtInputPhoneNo.setError("Please Enter PhoneNo");
                }


            }
        });
       /* mBtnCurrLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final LocationManager manager = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);
                if (manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {

                    LocationManager locationManager = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);
                    Criteria criteria = new Criteria();
                    criteria.setCostAllowed(true);
                    criteria.setAccuracy(Criteria.ACCURACY_FINE);
                    criteria.setAltitudeRequired(true);
                    criteria.setPowerRequirement(Criteria.POWER_LOW);

                    String provider = locationManager.getBestProvider(criteria, true);

                    if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    Location location = locationManager.getLastKnownLocation(provider);
                    double lattitude = location.getLatitude();
                    double longitude = location.getLongitude();
                    Toast.makeText(getActivity(), "Lat="+lattitude+"\n Long="+longitude, Toast.LENGTH_LONG).show();
                    Log.e("tag==", "Lat="+lattitude+"\t Long="+longitude);
                }
                else {
                    Toast.makeText(getActivity(), "Please Turn ON Location...", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    getActivity().startActivity(intent);
                }
            }
        });*/
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        return view;
    }
}
