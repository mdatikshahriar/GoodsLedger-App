package com.example.goods_ledger.ConsumerPart.ui;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.goods_ledger.ConsumerPart.ConsumerAccountInfoActivity;
import com.example.goods_ledger.ConsumerPart.Demo;
import com.example.goods_ledger.MainActivity;
import com.example.goods_ledger.R;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import github.nisrulz.qreader.QRDataListener;
import github.nisrulz.qreader.QREader;

public class ConsumerHomeFragment extends Fragment {

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

                startActivity(new Intent(getActivity(), Demo.class));

//                final Button cameraButton;
//                ProgressBar progressBar;
//                final SurfaceView surfaceView;
//                final QREader qrEader;
//
//                addProductsPopup.setContentView(R.layout.popup_addproducts_qrcode_scanner);
//                addProductsPopup.setCanceledOnTouchOutside(true);
//
//                cameraButton = addProductsPopup.findViewById(R.id.popup_addproducts_qrcode_scanner_camera_Button);
//                progressBar = addProductsPopup.findViewById(R.id.popup_addproducts_qrcode_scanner_Progressbar);
//                surfaceView = addProductsPopup.findViewById(R.id.popup_addproducts_qrcode_scanner_camera_SurfaceView);
//
//                qrEader = new QREader.Builder(getActivity(), surfaceView, new QRDataListener() {
//                    @Override
//                    public void onDetected(String data) {
//                        Log.d("Scanner",data);
//                    }
//                }).facing(QREader.BACK_CAM)
//                        .enableAutofocus(true)
//                        .height(surfaceView.getHeight())
//                        .width(surfaceView.getWidth())
//                        .build();
//
//                Dexter.withActivity(getActivity())
//                        .withPermission(Manifest.permission.CAMERA)
//                        .withListener(new PermissionListener() {
//                            @Override
//                            public void onPermissionGranted(PermissionGrantedResponse response) {
//                                cameraButton.setOnClickListener(new View.OnClickListener() {
//                                    @Override
//                                    public void onClick(View view) {
//                                        qrEader.start();
//                                    }
//                                });
//                            }
//
//                            @Override
//                            public void onPermissionDenied(PermissionDeniedResponse response) {
//                                Toast.makeText(getActivity(), "Enable Camera Permission", Toast.LENGTH_SHORT).show();
//                            }
//
//                            @Override
//                            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
//
//                            }
//                        }).check();
//
//
//
//                addProductsPopup.show();
//                addProductsPopup.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            }
        });

        checkProductsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Demo.class));
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