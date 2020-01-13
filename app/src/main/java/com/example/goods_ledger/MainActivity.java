package com.example.goods_ledger;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.goods_ledger.ConsumerPart.ConsumerStartActivity;
import com.example.goods_ledger.ManufacturerPart.AddManufacturerActivity;
import com.example.goods_ledger.ManufacturerPart.ManufacturerStartActivity;
import com.example.goods_ledger.Assets.Links;
import com.example.goods_ledger.Assets.SavedValues;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private Context context;
    private SharedPreferences sharedPreferences;
    private static SavedValues savedValues;
    private static Links links;
    private static RetryPolicy retryPolicy;

    private String accountToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = getApplicationContext();

        sharedPreferences = context.getSharedPreferences("GOODSLEDGER_SHARED_PREFERENCES", Context.MODE_PRIVATE);
        savedValues = new SavedValues(sharedPreferences);

        links = new Links();

        retryPolicy = new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);

        accountToken = savedValues.getAccountToken();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                StringRequest stringRequest = new StringRequest(Request.Method.POST, links.getURL_queryAccountbyToken(),
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.d("Response", response);
                                try {
                                    JSONObject jsonObject = new JSONObject(response);

                                    JSONObject object = jsonObject.getJSONObject("Record");

                                    String accountKeyResponse = jsonObject.getString("Key").trim();;
                                    String accountTokenResponse = object.getString("AccountToken").trim();
                                    String accountTypeResponse = object.getString("AccountType").trim();
                                    String accountNameResponse = object.getString("AccountName").trim();
                                    String accountUsernameResponse = object.getString("AccountUsername").trim();
                                    String accountEmailResponse = object.getString("AccountEmail").trim();
                                    String accountIsManufacturerAssignedResponse = object.getString("AccountIsManufacturerAssigned").trim();

                                    MainActivity.getSavedValues().setAccountKey(accountKeyResponse);
                                    MainActivity.getSavedValues().setAccountToken(accountTokenResponse);
                                    MainActivity.getSavedValues().setAccountType(accountTypeResponse);
                                    MainActivity.getSavedValues().setAccountName(accountNameResponse);
                                    MainActivity.getSavedValues().setAccountUsername(accountUsernameResponse);
                                    MainActivity.getSavedValues().setAccountEmail(accountEmailResponse);
                                    MainActivity.getSavedValues().setAccountIsManufacturerAssigned(accountIsManufacturerAssignedResponse);

                                    if (accountToken.equals("")){

                                        startActivity(new Intent(MainActivity.this, LoginActivity.class));
                                        finish();
                                    }
                                    else if(accountTypeResponse.equals("Manufacturer")){

                                        if(accountIsManufacturerAssignedResponse.equals("true")){

                                            startActivity(new Intent(MainActivity.this, ManufacturerStartActivity.class));
                                            finish();
                                        }
                                        else if (accountIsManufacturerAssignedResponse.equals("false")){

                                            startActivity(new Intent(MainActivity.this, AddManufacturerActivity.class));
                                            finish();
                                        }
                                    }
                                    else if(accountTypeResponse.equals("Consumer")){

                                        startActivity(new  Intent(MainActivity.this, ConsumerStartActivity.class));
                                        finish();
                                    }
                                }
                                catch (JSONException e){
                                    e.printStackTrace();
                                    Log.d("catchError", e.toString());
                                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                                    finish();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d("responseError", error.toString());
                                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                                finish();
                            }
                        })
                {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<>();
                        params.put("accountToken", accountToken);
                        return params;
                    }
                };

                stringRequest.setRetryPolicy(retryPolicy);

                RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
                requestQueue.add(stringRequest);
            }
        }, 1000);
    }

    public static SavedValues getSavedValues() {
        return savedValues;
    }

    public static Links getLinks() {
        return links;
    }

    public static RetryPolicy getRetryPolicy() {
        return retryPolicy;
    }
}