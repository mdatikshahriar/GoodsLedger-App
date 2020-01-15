package com.example.goods_ledger.ConsumerPart;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.goods_ledger.Assets.Product;
import com.example.goods_ledger.MainActivity;
import com.example.goods_ledger.ManufacturerPart.ManufacturerStartActivity;
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

public class ConsumerStartActivity extends AppCompatActivity {

    private static Bitmap accountUserameBitmap;
    private static ArrayList<Product> ownedProductsArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consumer_start);
        BottomNavigationView navView = findViewById(R.id.nav_consumer_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_consumer_home, R.id.navigation_consumer_products, R.id.navigation_consumer_queries, R.id.navigation_consumer_options)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_consumer_host_fragment);
        NavigationUI.setupWithNavController(navView, navController);

        accountUserameBitmap = MainActivity.getQRcodeBitmap(MainActivity.getSavedValues().getAccountUsername());

        ownedProductsArray = new ArrayList<>();

        final String productOwnerAccountID = MainActivity.getSavedValues().getAccountKey();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, MainActivity.getLinks().getURL_queryProductbyOwnerAccountID(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array = new JSONArray(response);

                            MainActivity.getSavedValues().setOwnedProductsCount(Integer.toString(array.length()));

                            for (int i=0; i<array.length(); i++){

                                JSONObject jsonObject = array.getJSONObject(i);

                                String productKeyResponse = jsonObject.getString("Key").trim();
                                JSONObject object = jsonObject.getJSONObject("Record");

                                String productOwnerAccountIDResponse = object.getString("ProductOwnerAccountID").trim();
                                String productManufacturerIDResponse = object.getString("ProductManufacturerID").trim();
                                String productManufacturerNameResponse = object.getString("ProductManufacturerName").trim();
                                String productFactoryIDResponse = object.getString("ProductFactoryID").trim();
                                String productIDResponse = object.getString("ProductID").trim();
                                String productNameResponse = object.getString("ProductName").trim();
                                String productTypeResponse = object.getString("ProductType").trim();
                                String productBatchResponse = object.getString("ProductBatch").trim();
                                String productManufacturingDateResponse = object.getString("ProductManufacturingDate").trim();
                                String productExpiryDateResponse = object.getString("ProductExpiryDate").trim();


                                Product newProduct = new Product(productKeyResponse, productOwnerAccountIDResponse, productManufacturerIDResponse, productManufacturerNameResponse, productFactoryIDResponse, productIDResponse,
                                        productNameResponse, productTypeResponse, productBatchResponse, productManufacturingDateResponse, productExpiryDateResponse);

                                ownedProductsArray.add(newProduct);
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
                params.put("productOwnerAccountID", productOwnerAccountID);
                return params;
            }
        };

        stringRequest.setRetryPolicy(MainActivity.getRetryPolicy());

        RequestQueue requestQueue = Volley.newRequestQueue(ConsumerStartActivity.this);
        requestQueue.add(stringRequest);
    }

    public static Bitmap getAccountUsernameBitmap() {
        return accountUserameBitmap;
    }

    public static ArrayList<Product> getOwnedProductsArray() {
        return ownedProductsArray;
    }
}
