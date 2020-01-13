package com.example.goods_ledger.ManufacturerPart;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.goods_ledger.Assets.Factory;
import com.example.goods_ledger.Assets.Product;
import com.example.goods_ledger.MainActivity;
import com.example.goods_ledger.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ManufacturerStartActivity extends AppCompatActivity {

    private static ArrayList<Product> productsArray;
    private static ArrayList<Factory> factoriesArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manufacturer_start);
        BottomNavigationView navView = findViewById(R.id.nav_manufacturer_view);

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_manufacturer_home, R.id.navigation_manufacturer_factories, R.id.navigation_manufacturer_products, R.id.navigation_manufacturer_options)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_manufacturer_host_fragment);
        NavigationUI.setupWithNavController(navView, navController);

        productsArray = new ArrayList<>();
        factoriesArray = new ArrayList<>();

        final String productManufacturerID = MainActivity.getSavedValues().getManufacturerKey();

        StringRequest stringRequest1 = new StringRequest(Request.Method.POST, MainActivity.getLinks().getURL_queryProductbyManufacturerID(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array = new JSONArray(response);

                            MainActivity.getSavedValues().setProductsCount(Integer.toString(array.length()));

                            for (int i=0; i<array.length(); i++){

                                JSONObject jsonObject = array.getJSONObject(i);

                                String productKeyResponse = jsonObject.getString("Key").trim();
                                JSONObject object = jsonObject.getJSONObject("Record");

                                String productManufacturerIDResponse = object.getString("ProductManufacturerID").trim();
                                String productFactoryIDResponse = object.getString("ProductFactoryID").trim();
                                String productIDResponse = object.getString("ProductID").trim();
                                String productNameResponse = object.getString("ProductName").trim();
                                String productTypeResponse = object.getString("ProductType").trim();
                                String productBatchResponse = object.getString("ProductBatch").trim();
                                String productManufacturingDateResponse = object.getString("ProductManufacturingDate").trim();
                                String productExpiryDateResponse = object.getString("ProductExpiryDate").trim();

                                Product newProduct = new Product(productKeyResponse, productManufacturerIDResponse, productFactoryIDResponse, productIDResponse,
                                        productNameResponse, productTypeResponse, productBatchResponse, productManufacturingDateResponse, productExpiryDateResponse);

                                productsArray.add(newProduct);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d("catchError", e.toString());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("responseError", error.toString());
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("productManufacturerID", productManufacturerID);
                return params;
            }
        };

        stringRequest1.setRetryPolicy(MainActivity.getRetryPolicy());

        RequestQueue requestQueue1 = Volley.newRequestQueue(ManufacturerStartActivity.this);
        requestQueue1.add(stringRequest1);

        final String factoryManufacturerID = MainActivity.getSavedValues().getManufacturerKey();

        StringRequest stringRequest2 = new StringRequest(Request.Method.POST, MainActivity.getLinks().getURL_queryFactorybyManufacturerID(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array = new JSONArray(response);

                            MainActivity.getSavedValues().setFactoriesCount(Integer.toString(array.length()));

                            for (int i=0; i<array.length(); i++){

                                JSONObject jsonObject = array.getJSONObject(i);

                                String factoryKeyResponse = jsonObject.getString("Key").trim();
                                JSONObject object = jsonObject.getJSONObject("Record");

                                String factoryManufacturerIDResponse = object.getString("FactoryManufacturerID").trim();
                                String factoryIDResponse = object.getString("FactoryID").trim();
                                String factoryLocationResponse = object.getString("FactoryLocation").trim();

                                Factory newFactory = new Factory(factoryKeyResponse, factoryManufacturerIDResponse, factoryIDResponse, factoryLocationResponse);

                                factoriesArray.add(newFactory);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d("catchError", e.toString());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("responseError", error.toString());
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("factoryManufacturerID", factoryManufacturerID);
                return params;
            }
        };

        stringRequest2.setRetryPolicy(MainActivity.getRetryPolicy());

        RequestQueue requestQueue2 = Volley.newRequestQueue(ManufacturerStartActivity.this);
        requestQueue2.add(stringRequest2);
    }

    public static ArrayList<Product> getProductsArray() {
        return productsArray;
    }

    public static ArrayList<Factory> getFactoriesArray() {
        return factoriesArray;
    }
}
