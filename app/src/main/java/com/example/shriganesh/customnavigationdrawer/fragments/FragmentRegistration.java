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
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.Toolbar;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.shriganesh.customnavigationdrawer.R;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by shri ganesh on 23/05/2017.
 */

public class FragmentRegistration extends Fragment {

    private Toolbar mToolbar;
    private FragmentManager mFragmentManager;
    private TextInputLayout mTxtInputName, mTxtInputMobile, mTxtInputEmail, mTxtInputAddress, mTxtInputPassword, mTxtInputConPassword;
    private EditText mEdtName, mEdtMobile, mEdtEmail, mEdtAddress, mEdtPassword, mEdtConPassword;
    private Button mBtnRegister;
    private RequestQueue mRequestQueue;
    private ProgressBar mProgressBar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.lay_registration, null);

        mRequestQueue = Volley.newRequestQueue(getActivity());
        mToolbar = (Toolbar) view.findViewById(R.id.registrationTollbar);
        mTxtInputName = (TextInputLayout) view.findViewById(R.id.txtInputName);
        mTxtInputMobile = (TextInputLayout) view.findViewById(R.id.txtInputMobile);
        mTxtInputEmail = (TextInputLayout) view.findViewById(R.id.txtInputEmail);
        mTxtInputAddress = (TextInputLayout) view.findViewById(R.id.txtInputAddress);
        mTxtInputPassword = (TextInputLayout) view.findViewById(R.id.txtInputPass);
        mTxtInputConPassword = (TextInputLayout) view.findViewById(R.id.txtInputConPass);
        mEdtName = (EditText) view.findViewById(R.id.edtName);
        mEdtMobile = (EditText) view.findViewById(R.id.edtMobile);
        mEdtEmail = (EditText) view.findViewById(R.id.edtEmail);
        mEdtAddress = (EditText) view.findViewById(R.id.edtAddress);
        mEdtPassword = (EditText) view.findViewById(R.id.edtPass);
        mEdtConPassword = (EditText) view.findViewById(R.id.edtConPass);
        mBtnRegister = (Button) view.findViewById(R.id.btnRegister);
        mProgressBar = (ProgressBar) view.findViewById(R.id.progressBarRegistration);
        mProgressBar.setVisibility(View.INVISIBLE);

        mFragmentManager = getFragmentManager();

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar();
        mToolbar.setTitle("\t\t\t\t\t");
        mToolbar.setTitle(R.string.registration);
        if (activity.getSupportActionBar() != null) {

            mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        }
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mFragmentManager.popBackStack();
            }
        });

        mBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(mEdtName.getText().toString()))
                {
                    mTxtInputName.setError("Please enter the name");
                }
                if (TextUtils.isEmpty(mEdtMobile.getText().toString()))
                {
                    mTxtInputMobile.setError("Please enter the mobile");
                }
                if (TextUtils.isEmpty(mEdtEmail.getText().toString()))
                {
                    mTxtInputEmail.setError("Please enter the email");
                }
                if (TextUtils.isEmpty(mEdtAddress.getText().toString()))
                {
                    mTxtInputAddress.setError("Please enter the address");
                }
                if (TextUtils.isEmpty(mEdtPassword.getText().toString()))
                {
                    mTxtInputPassword.setError("Please enter the password");
                }
                if (TextUtils.isEmpty(mEdtConPassword.getText().toString()))
                {
                    mTxtInputConPassword.setError("Please enter the confirm password");
                }
                String pass = mEdtPassword.getText().toString();
                String conPass = mEdtConPassword.getText().toString();
                if (!pass.equals(conPass))
                {
                    mEdtPassword.setText("");
                    mEdtConPassword.setText("");
                    mTxtInputConPassword.setError("Password dosen't match");
                }

                if (    TextUtils.isEmpty(mEdtName.getText().toString())    &&
                        TextUtils.isEmpty(mEdtMobile.getText().toString())  &&
                        TextUtils.isEmpty(mEdtEmail.getText().toString())   &&
                        TextUtils.isEmpty(mEdtAddress.getText().toString()) &&
                        TextUtils.isEmpty(mEdtPassword.getText().toString()) &&
                        TextUtils.isEmpty(mEdtConPassword.getText().toString()) )
                {
                    mTxtInputName.setError("Please enter the name");
                    mTxtInputMobile.setError("Please enter the mobile");
                    mTxtInputEmail.setError("Please enter the email");
                    mTxtInputAddress.setError("Please enter the address");
                    mTxtInputPassword.setError("Please enter the password");
                    mTxtInputConPassword.setError("Please enter the confirm password");
                    Toast.makeText(getActivity(), "All fields are mandetory..", Toast.LENGTH_SHORT).show();
                }

                if (  !(TextUtils.isEmpty(mEdtName.getText().toString())    &&
                        TextUtils.isEmpty(mEdtMobile.getText().toString())  &&
                        TextUtils.isEmpty(mEdtEmail.getText().toString())   &&
                        TextUtils.isEmpty(mEdtAddress.getText().toString()) &&
                        TextUtils.isEmpty(mEdtPassword.getText().toString()) &&
                        TextUtils.isEmpty(mEdtConPassword.getText().toString())) )
                {
                    String url = "http://bitcodetech.in/ecommerce/ws/Registration.php?user_name="+ mEdtName.getText().toString()+"&user_email="+mEdtEmail.getText().toString()+"&user_password="+mEdtConPassword.getText().toString()+"&user_mobile="+mEdtMobile.getText().toString()+"&user_address="+mEdtAddress.getText().toString();
                    checkInsertionStatus(url);
                    mProgressBar.setVisibility(View.VISIBLE);
                }

            }
        });
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        return view;
    }

    /*public void registerUser()
    {
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                mUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                     Log.e("tag==","Response="+response);
                        String url = "http://bitcodetech.in/ecommerce/ws/Registration.php?user_name="+ mEdtName.getText().toString()+"&user_email="+mEdtEmail.getText().toString()+"&user_password="+mEdtConPassword.getText().toString()+"&user_mobile="+mEdtMobile.getText().toString()+"&user_address="+mEdtAddress.getText().toString();
                        checkInsertionStatus(url);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("tag==","VOLLEY ERROR..in registeruser");
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError
            {
                HashMap<String, String> parms = new HashMap<>();

                parms.put("user_name", mEdtName.getText().toString());
                parms.put("user_email", mEdtEmail.getText().toString());
                parms.put("user_password", mEdtPassword.getText().toString());
                parms.put("user_mobile", mEdtMobile.getText().toString());
                parms.put("user_address", mEdtAddress.getText().toString());
                return parms;
            }
        };
        mRequestQueue.add(stringRequest);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(15000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }
*/
    public void checkInsertionStatus(String url)
    {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                      if (response!=null){
                          try {
                              boolean result = response.getBoolean("Insert");
                              if (result){
                                  mProgressBar.setVisibility(View.GONE);
                                  Toast.makeText(getActivity(), "Registration Successful...", Toast.LENGTH_SHORT).show();
                              }
                              else
                              {
                                  mProgressBar.setVisibility(View.GONE);
                                  Toast.makeText(getActivity(), "Registration Failed...", Toast.LENGTH_SHORT).show();
                              }
                          } catch (JSONException e)
                          {
                              e.printStackTrace();
                          }
                      }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        mProgressBar.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), "VOLLEY ERROR...", Toast.LENGTH_SHORT).show();
                    }
                });
        mRequestQueue.add(jsonObjectRequest);
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(15000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }
}
