package com.example.shriganesh.customnavigationdrawer.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.transition.Slide;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
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

import java.util.HashMap;

/**
 * Created by shri ganesh on 22/05/2017.
 */

public class FragmentLogin extends Fragment {

    private TextInputLayout mTxtInputUname, mTxtInputPass;
    private EditText mEdtUname, mEdtPass;
    private Button mBtnLogin;
    private TextView mTxtNewUser;
    private FragmentManager mFragmentManager;
    private RequestQueue mRequestQueue;
    private String uname, password;
    private boolean mStatus;
    private Toolbar mToolbar;
    private ProgressBar mProgressBar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.lay_login, null);

        mRequestQueue = Volley.newRequestQueue(getActivity());

        mTxtInputUname = (TextInputLayout) view.findViewById(R.id.txtInputUname);
        mTxtInputPass = (TextInputLayout) view.findViewById(R.id.txtInputPass);
        mEdtUname = (EditText) view.findViewById(R.id.edtUname);
        mEdtPass = (EditText) view.findViewById(R.id.edtPass);
        mBtnLogin = (Button) view.findViewById(R.id.btnLogin);
        mTxtNewUser = (TextView) view.findViewById(R.id.txtNewUser);
        mToolbar = (Toolbar) view.findViewById(R.id.loginTollbar);
        mProgressBar = (ProgressBar) view.findViewById(R.id.progressBarLogin);
        mProgressBar.setVisibility(View.INVISIBLE);

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar();
        mToolbar.setTitle("\t\t\t\t\t");
        mToolbar.setTitle(R.string.titlelogin);
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
        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if (TextUtils.isEmpty(mEdtUname.getText().toString()))
                {
                    mTxtInputUname.setError("Please enter the username");
                }
                if (TextUtils.isEmpty(mEdtPass.getText().toString()))
                {
                    mTxtInputPass.setError("Please enter the password");
                }
                if (TextUtils.isEmpty(mEdtUname.getText().toString()) && TextUtils.isEmpty(mEdtPass.getText().toString()))
                {
                    mTxtInputUname.setError("Please enter the username");
                    mTxtInputPass.setError("Please enter the password");
                    mEdtPass.setText("");
                }
                else
                {
                    uname = mEdtUname.getText().toString();
                    password = mEdtPass.getText().toString();

                    HashMap<String, String> parm = new HashMap<>();

                    parm.put("user_name", uname);
                    parm.put("user_password", password);
                    mTxtInputUname.setError("");
                    mTxtInputPass.setError("");
                    String url = "http://www.bitcodetech.in/ecommerce/ws/ProjectWeb.php?user_name="+uname+"&"+"user_password="+password;
                    getLoginStatus(url);
                    mProgressBar.setVisibility(View.VISIBLE);
                }
            }
        });

        mTxtNewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentRegistration fragmentRegistration = new FragmentRegistration();
                fragmentRegistration.setEnterTransition(new Slide(Gravity.LEFT));
                fragmentRegistration.setExitTransition(new Slide(Gravity.RIGHT) );
                mFragmentManager.beginTransaction().add(R.id.activity_main, fragmentRegistration, null)
                                .addToBackStack("BackStack")
                                .commit();
            }
        });

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        return view;
    }

    /*private void checkUser(String url, final HashMap<String, String> parm) {
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response)
                    {
                        Log.e("tag==", "responase="+response);
                        String url = "http://www.bitcodetech.in/ecommerce/ws/ProjectWeb.php?user_name="+uname+"&"+"user_password="+password;
                        getLoginStatus(url);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        mProgressBar.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), "Login failed please check the username or password...", Toast.LENGTH_LONG).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return parm;
            }
        };
        mRequestQueue.add(stringRequest);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(15000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }
*/
    public void getLoginStatus(String url)
    {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                       if (response!=null)
                       {
                           try {
                               mStatus = response.getBoolean("result");
                               if (mStatus){
                                   mProgressBar.setVisibility(View.GONE);
                                   Toast.makeText(getActivity(), "Login Successful...", Toast.LENGTH_SHORT).show();
                                   FragmentOrder fragmentOrder = new FragmentOrder();
                                   fragmentOrder.setEnterTransition(new Slide(Gravity.BOTTOM));
                                   fragmentOrder.setExitTransition(new Slide(Gravity.TOP) );
                                   mFragmentManager.beginTransaction().add(R.id.activity_main, fragmentOrder, null).addToBackStack("backStack").commit();
                                   mEdtUname.setText("");
                                   mEdtPass.setText("");
                               }
                               else {
                                   mProgressBar.setVisibility(View.GONE);
                                   Toast.makeText(getActivity(), "Login failed please check the username or password...", Toast.LENGTH_SHORT).show();
                               }

                           } catch (JSONException e) {
                               e.printStackTrace();
                           }
                       }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        mProgressBar.setVisibility(View.GONE);
                        Toast.makeText(getActivity(),"VOLLEY ERROR...", Toast.LENGTH_SHORT).show();
                    }
                });
        mRequestQueue.add(jsonObjectRequest);
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(15000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }
}
