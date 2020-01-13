package com.example.goods_ledger.ManufacturerPart.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.goods_ledger.LoginActivity;
import com.example.goods_ledger.MainActivity;
import com.example.goods_ledger.ManufacturerPart.ManufacturerAccountInfoActivity;
import com.example.goods_ledger.ManufacturerPart.ManufacturerEditAccountActivity;
import com.example.goods_ledger.R;

import java.util.HashMap;
import java.util.Map;

public class ManufacturerOptionsFragment extends Fragment {

    private LinearLayout accountInfoLinearLayout, editAccountLinearLayout, editManufacturerLinearLayout, logoutLinearLayout;
    private ProgressBar progressBar;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_manufacturer_options, container, false);

        accountInfoLinearLayout = root.findViewById(R.id.fragment_manufacturer_options_accountInfo_linearLayout);
        editAccountLinearLayout = root.findViewById(R.id.fragment_manufacturer_options_editAccount_linearLayout);
        editManufacturerLinearLayout = root.findViewById(R.id.fragment_manufacturer_options_editManufacturer_linearLayout);
        logoutLinearLayout = root.findViewById(R.id.fragment_manufacturer_options_logout_linearLayout);
        progressBar = root.findViewById(R.id.fragment_manufacturer_options_progressbar);

        final String accountKey = MainActivity.getSavedValues().getAccountKey();
        final String accountToken = "";

        accountInfoLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), ManufacturerAccountInfoActivity.class));
            }
        });

        editAccountLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), ManufacturerEditAccountActivity.class));
            }
        });

        editManufacturerLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ///
            }
        });

        logoutLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                StringRequest stringRequest = new StringRequest(Request.Method.POST, MainActivity.getLinks().getURL_updateAccountToken(),
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                MainActivity.getSavedValues().setFactoriesCount("0");
                                MainActivity.getSavedValues().setProductsCount("0");
                                MainActivity.getSavedValues().setAccountToken(accountToken);

                                startActivity(new Intent(getActivity(), LoginActivity.class));
                                getActivity().finish();
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(getActivity(), "Logout failed! Error: " + error.toString(), Toast.LENGTH_SHORT).show();
                                Log.d("responseError", error.toString());
                            }
                        })
                {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<>();
                        params.put("accountKey", accountKey);
                        params.put("accountToken", accountToken);
                        return params;
                    }
                };

                stringRequest.setRetryPolicy(MainActivity.getRetryPolicy());

                RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
                requestQueue.add(stringRequest);
            }
        });

        return root;
    }
}