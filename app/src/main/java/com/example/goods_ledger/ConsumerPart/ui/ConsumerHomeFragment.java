package com.example.goods_ledger.ConsumerPart.ui;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.goods_ledger.ConsumerPart.ConsumerAccountInfoActivity;
import com.example.goods_ledger.MainActivity;
import com.example.goods_ledger.R;

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

            }
        });

        checkProductsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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