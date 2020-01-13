package com.example.goods_ledger.ManufacturerPart.ui.Products;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.goods_ledger.R;
import com.example.goods_ledger.Assets.Product;

import java.util.ArrayList;

public class ProductsAdapter extends RecyclerView.Adapter <RecyclerView.ViewHolder> {

    private ArrayList<Product> productsArray;

    ProductsAdapter(ArrayList<Product> productsArray) {
        this.productsArray = productsArray;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_manufacturer_products, parent, false);
        return new ProductsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Product product = productsArray.get(position);

        ProductsHolder productsHolder = (ProductsHolder) holder;

        TextView productBatchID = productsHolder.productBatchID;
        TextView productName = productsHolder.productName;
        TextView productType = productsHolder.productType;
        TextView productCount = productsHolder.productCount;

        productBatchID.setText(product.getProductBatch());
        productName.setText(product.getProductName());
        productType.setText(product.getProductType());
        productCount.setText(product.getProductID());
    }

    @Override
    public int getItemCount() {

        return productsArray.size();
    }

    private class ProductsHolder extends RecyclerView.ViewHolder{
        TextView productBatchID, productName, productType, productCount;

        public ProductsHolder(@NonNull View itemView) {
            super(itemView);

            productBatchID = itemView.findViewById(R.id.item_manufacturer_products_productBatchID_textView);
            productName = itemView.findViewById(R.id.item_manufacturer_products_productName_textView);
            productType = itemView.findViewById(R.id.item_manufacturer_products_productType_textView);
            productCount = itemView.findViewById(R.id.item_manufacturer_products_productID_textView);
        }
    }
}
