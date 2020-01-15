package com.example.goods_ledger.ConsumerPart.ui;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.goods_ledger.Assets.Product;
import com.example.goods_ledger.ConsumerPart.ConsumerAccountInfoActivity;
import com.example.goods_ledger.ConsumerPart.ConsumerStartActivity;
import com.example.goods_ledger.MainActivity;
import com.example.goods_ledger.ManufacturerPart.ManufacturerStartActivity;
import com.example.goods_ledger.R;
import com.google.zxing.Result;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import me.dm7.barcodescanner.core.ViewFinderView;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ConsumerHomeFragment extends Fragment{

    private ImageView profilePictureImageView;
    private TextView accountNameTextView, accountEmailTextView, ownedProductCountTextView, checkedProductsCountTextView;
    private LinearLayout accountSeeMoreLinearLayout, productsHereLinearLayout;
    private Button addProductsButton,checkProductsButton;
    private Dialog addProductsPopup, checkProductsPopup, profilePicturePopup;
    private String accountKey;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_consumer_home, container, false);

        profilePictureImageView = root.findViewById(R.id.fragment_consumer_home_profilePicture_ImageView);
        accountNameTextView = root.findViewById(R.id.fragment_consumer_home_accountName_TextView);
        accountEmailTextView = root.findViewById(R.id.fragment_consumer_home_accountEmail_TextView);
        ownedProductCountTextView = root.findViewById(R.id.fragment_consumer_home_ownedProductCount_TextView);
        checkedProductsCountTextView = root.findViewById(R.id.fragment_consumer_home_checkedProductsCount_TextView);
        accountSeeMoreLinearLayout = root.findViewById(R.id.fragment_consumer_home_accountSeeMore_LinearLayout);
        productsHereLinearLayout = root.findViewById(R.id.fragment_consumer_home_productsHere_LinearLayout);
        addProductsButton = root.findViewById(R.id.fragment_consumer_home_addProducts_Button);
        checkProductsButton = root.findViewById(R.id.fragment_consumer_home_checkProducts_Button);
        addProductsPopup = new Dialog(getActivity());
        checkProductsPopup = new Dialog(getActivity());
        profilePicturePopup = new Dialog(getActivity());

        profilePictureImageView.setImageBitmap(ConsumerStartActivity.getAccountUsernameBitmap());

        accountNameTextView.setText(MainActivity.getSavedValues().getAccountName());
        accountEmailTextView.setText(MainActivity.getSavedValues().getAccountEmail());
        ownedProductCountTextView.setText(MainActivity.getSavedValues().getOwnedProductsCount());

        accountSeeMoreLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), ConsumerAccountInfoActivity.class));
            }
        });

        accountKey = MainActivity.getSavedValues().getAccountKey();

        profilePictureImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView productQRCodeImage;

                profilePicturePopup.setContentView(R.layout.popup_qrcode_image);
                profilePicturePopup.setCanceledOnTouchOutside(true);

                productQRCodeImage = profilePicturePopup.findViewById(R.id.popup_qrcode_image_ImageView);

                productQRCodeImage.setImageDrawable(profilePictureImageView.getDrawable());

                profilePicturePopup.show();
                profilePicturePopup.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            }
        });

        addProductsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final LinearLayout firstLinearLayout, secondLinearLayout;
                final ZXingScannerView scannerView;
                final Button cameraButton, ownProductButton;
                final ProgressBar progressBar;
                final ImageView resultImageView;
                final TextView resultTextView;

                addProductsPopup.setContentView(R.layout.popup_qrcode_scanner);
                addProductsPopup.setCanceledOnTouchOutside(true);

                firstLinearLayout = addProductsPopup.findViewById(R.id.popup_qrcode_scanner_first_LinearLayout);
                secondLinearLayout = addProductsPopup.findViewById(R.id.popup_qrcode_scanner_second_LinearLayout);
                scannerView = addProductsPopup.findViewById(R.id.popup_qrcode_scanner_ZXingScannerView);
                cameraButton = addProductsPopup.findViewById(R.id.popup_qrcode_scanner_camera_Button);
                ownProductButton = addProductsPopup.findViewById(R.id.popup_qrcode_scanner_ownProduct_Button);
                progressBar = addProductsPopup.findViewById(R.id.popup_qrcode_scanner_Progressbar);
                resultImageView = addProductsPopup.findViewById(R.id.popup_qrcode_scanner_result_ImageView);
                resultTextView = addProductsPopup.findViewById(R.id.popup_qrcode_scanner_result_TextView);

                firstLinearLayout.setVisibility(View.VISIBLE);
                secondLinearLayout.setVisibility(View.INVISIBLE);
                scannerView.setVisibility(View.INVISIBLE);
                progressBar.setVisibility(View.INVISIBLE);

                addProductsPopup.show();
                addProductsPopup.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                addProductsPopup.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        scannerView.stopCamera();
                    }
                });

                Dexter.withActivity(getActivity())
                        .withPermission(Manifest.permission.CAMERA)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse response) {

                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse response) {
                                Toast.makeText(getActivity(), "You must give camera permission!", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {

                            }
                        })
                        .check();

                cameraButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        firstLinearLayout.setVisibility(View.INVISIBLE);
                        scannerView.setVisibility(View.VISIBLE);
                        scannerView.startCamera();
                        scannerView.setResultHandler(new ZXingScannerView.ResultHandler() {
                            @Override
                            public void handleResult(Result rawResult) {
                                scannerView.stopCamera();
                                scannerView.setVisibility(View.INVISIBLE);
                                progressBar.setVisibility(View.VISIBLE);

                                String qrScannerResult = rawResult.getText().trim();

                                if(qrScannerResult.length() == 60){

                                    final String productCode = qrScannerResult;
                                    Log.d("Code", productCode);

                                    StringRequest stringRequest = new StringRequest(Request.Method.POST, MainActivity.getLinks().getURL_queryProductbyCode(),
                                            new Response.Listener<String>() {
                                                @Override
                                                public void onResponse(String response) {
                                                    try {
                                                        Log.d("Response", response);
                                                        JSONArray array = new JSONArray(response);

                                                        JSONObject jsonObject = array.getJSONObject(0);

                                                        final String productKeyResponse = jsonObject.getString("Key").trim();
                                                        JSONObject object = jsonObject.getJSONObject("Record");

                                                        final String productOwnerAccountIDResponse = object.getString("ProductOwnerAccountID").trim();
                                                        final String productManufacturerIDResponse = object.getString("ProductManufacturerID").trim();
                                                        final String productManufacturerNameResponse = object.getString("ProductManufacturerName").trim();
                                                        final String productFactoryIDResponse = object.getString("ProductFactoryID").trim();
                                                        final String productIDResponse = object.getString("ProductID").trim();
                                                        final String productNameResponse = object.getString("ProductName").trim();
                                                        final String productTypeResponse = object.getString("ProductType").trim();
                                                        final String productBatchResponse = object.getString("ProductBatch").trim();
                                                        final String productManufacturingDateResponse = object.getString("ProductManufacturingDate").trim();
                                                        final String productExpiryDateResponse = object.getString("ProductExpiryDate").trim();


                                                        if(productOwnerAccountIDResponse.equals(accountKey)){
                                                            String resultText = "You are already the owner of this product.";

                                                            progressBar.setVisibility(View.GONE);

                                                            resultTextView.setText(resultText);
                                                            resultImageView.setImageResource(R.drawable.real);
                                                            ownProductButton.setVisibility(View.INVISIBLE);

                                                            secondLinearLayout.setVisibility(View.VISIBLE);
                                                        } else if(productOwnerAccountIDResponse.equals("*#@%")){
                                                            String resultText = "The product is manufactured by " + productManufacturerNameResponse + ".\n The product hasn't been owned by anyone yet.\n Want to own this product? Click the button below.";

                                                            progressBar.setVisibility(View.GONE);

                                                            resultTextView.setText(resultText);
                                                            resultImageView.setImageResource(R.drawable.real);
                                                            ownProductButton.setVisibility(View.VISIBLE);

                                                            ownProductButton.setOnClickListener(new View.OnClickListener() {
                                                                @Override
                                                                public void onClick(View v) {
                                                                    final String productOwnerAccountID = MainActivity.getSavedValues().getAccountKey();
                                                                    final String productKey = productCode;
                                                                    progressBar.setVisibility(View.VISIBLE);

                                                                    StringRequest stringRequest = new StringRequest(Request.Method.POST, MainActivity.getLinks().getURL_updateProductOwner(),
                                                                            new Response.Listener<String>() {
                                                                                @Override
                                                                                public void onResponse(String response) {
                                                                                    try {
                                                                                        JSONObject object = new JSONObject(response);

                                                                                        Product newOwnedProduct = new Product(productKeyResponse, productOwnerAccountIDResponse, productManufacturerIDResponse, productManufacturerNameResponse,productFactoryIDResponse, productIDResponse,
                                                                                                productNameResponse, productTypeResponse, productBatchResponse, productManufacturingDateResponse, productExpiryDateResponse);

                                                                                        ConsumerStartActivity.getOwnedProductsArray().add(newOwnedProduct);
                                                                                        MainActivity.getSavedValues().setOwnedProductsCount(Integer.toString(1 + Integer.parseInt(MainActivity.getSavedValues().getOwnedProductsCount())));

                                                                                        ownedProductCountTextView.setText(MainActivity.getSavedValues().getProductsCount());

                                                                                        Toast.makeText(getActivity(), "Product owned successfully!", Toast.LENGTH_SHORT).show();

                                                                                        addProductsPopup.dismiss();
                                                                                    } catch (JSONException e) {
                                                                                        e.printStackTrace();
                                                                                        progressBar.setVisibility(View.GONE);
                                                                                        Toast.makeText(getActivity(), "Product owning failed! Error: " + e.toString(), Toast.LENGTH_SHORT).show();
                                                                                        Log.d("catchError", e.toString());
                                                                                    }
                                                                                }
                                                                            },
                                                                            new Response.ErrorListener() {
                                                                                @Override
                                                                                public void onErrorResponse(VolleyError error) {
                                                                                    progressBar.setVisibility(View.GONE);
                                                                                    Toast.makeText(getActivity(), "Product owning failed! Error: " + error.toString(), Toast.LENGTH_SHORT).show();
                                                                                    Log.d("responseError", error.toString());
                                                                                }
                                                                            })
                                                                    {
                                                                        @Override
                                                                        protected Map<String, String> getParams() {
                                                                            Map<String, String> params = new HashMap<>();
                                                                            params.put("productKey", productKey);
                                                                            params.put("productOwnerAccountID", productOwnerAccountID);
                                                                            return params;
                                                                        }
                                                                    };

                                                                    stringRequest.setRetryPolicy(MainActivity.getRetryPolicy());

                                                                    RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
                                                                    requestQueue.add(stringRequest);
                                                                }
                                                            });

                                                            secondLinearLayout.setVisibility(View.VISIBLE);
                                                        } else{
                                                            String resultText = "The product is manufactured by " + productManufacturerNameResponse + ".\n It's owned by someone else!\n If you claim to own it, then first request the original owner of the product to change it's ownership.\n Otherwise, perhaps your product is a counterfeit product.";

                                                            progressBar.setVisibility(View.GONE);

                                                            resultTextView.setText(resultText);
                                                            resultImageView.setImageResource(R.drawable.caution);
                                                            ownProductButton.setVisibility(View.INVISIBLE);

                                                            secondLinearLayout.setVisibility(View.VISIBLE);
                                                        }

                                                    } catch (JSONException e) {
                                                        e.printStackTrace();
                                                        Log.d("catchError", e.toString());

                                                        String resultText = "The product is a counterfeit product. You can't own it.";
                                                        progressBar.setVisibility(View.GONE);

                                                        resultTextView.setText(resultText);
                                                        resultImageView.setImageResource(R.drawable.counterfeit);
                                                        ownProductButton.setVisibility(View.INVISIBLE);

                                                        secondLinearLayout.setVisibility(View.VISIBLE);
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
                                            params.put("productCode", productCode);
                                            return params;
                                        }
                                    };

                                    stringRequest.setRetryPolicy(MainActivity.getRetryPolicy());

                                    RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
                                    requestQueue.add(stringRequest);

                                }
                                else{
                                    String resultText = "The code you scanned doesn't seem to be a valid code.";
                                    progressBar.setVisibility(View.GONE);

                                    resultTextView.setText(resultText);
                                    resultImageView.setImageResource(R.drawable.counterfeit);
                                    ownProductButton.setVisibility(View.INVISIBLE);

                                    secondLinearLayout.setVisibility(View.VISIBLE);
                                }
                            }
                        });

                    }
                });
            }
        });

        checkProductsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ZXingScannerView scannerView;
                Button cameraButton;

                checkProductsPopup.setContentView(R.layout.popup_qrcode_scanner);
                checkProductsPopup.setCanceledOnTouchOutside(true);

                scannerView = checkProductsPopup.findViewById(R.id.popup_qrcode_scanner_ZXingScannerView);
                cameraButton = checkProductsPopup.findViewById(R.id.popup_qrcode_scanner_camera_Button);

                checkProductsPopup.show();
                checkProductsPopup.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                Dexter.withActivity(getActivity())
                        .withPermission(Manifest.permission.CAMERA)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse response) {

                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse response) {
                                Toast.makeText(getActivity(), "You must give camera permission!", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {

                            }
                        })
                        .check();

                cameraButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        scannerView.setVisibility(View.VISIBLE);
                        scannerView.startCamera();
                        scannerView.setResultHandler(new ZXingScannerView.ResultHandler() {
                            @Override
                            public void handleResult(Result rawResult) {
                                scannerView.stopCamera();
                                scannerView.setVisibility(View.INVISIBLE);
                                Toast.makeText(getActivity(), rawResult.getText(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            }
        });

        productsHereLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_navigation_consumer_home_to_navigation_consumer_products);
            }
        });

        return root;
    }
}