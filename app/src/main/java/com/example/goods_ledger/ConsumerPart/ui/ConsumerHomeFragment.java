package com.example.goods_ledger.ConsumerPart.ui;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
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

import com.example.goods_ledger.ConsumerPart.ConsumerAccountInfoActivity;
import com.example.goods_ledger.MainActivity;
import com.example.goods_ledger.R;
import com.google.zxing.Result;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ConsumerHomeFragment extends Fragment{

    private ImageView profilePictureImageView;
    private TextView accountNameTextView, accountEmailTextView, ownedProductCountTextView, checkedProductsCountTextView;
    private LinearLayout accountSeeMoreLinearLayout, productsHereLinearLayout;
    private Button addProductsButton,checkProductsButton;
    private Dialog addProductsPopup, checkProductsPopup;

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

        accountNameTextView.setText(MainActivity.getSavedValues().getAccountName());
        accountEmailTextView.setText(MainActivity.getSavedValues().getAccountEmail());

        accountSeeMoreLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), ConsumerAccountInfoActivity.class));
            }
        });

        addProductsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ZXingScannerView scannerView;
                Button cameraButton;

                addProductsPopup.setContentView(R.layout.popup_qrcode_scanner);
                addProductsPopup.setCanceledOnTouchOutside(true);

                scannerView = addProductsPopup.findViewById(R.id.popup_qrcode_scanner_ZXingScannerView);
                cameraButton = addProductsPopup.findViewById(R.id.popup_qrcode_scanner_camera_Button);

                addProductsPopup.show();
                addProductsPopup.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

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