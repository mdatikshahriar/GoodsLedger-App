package com.example.goods_ledger.ManufacturerPart.ui.Factories;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.goods_ledger.Assets.Factory;
import com.example.goods_ledger.MainActivity;
import com.example.goods_ledger.ManufacturerPart.ui.Products.ProductsAdapter;
import com.example.goods_ledger.R;

import java.util.ArrayList;

public class FactoriesAdapter extends RecyclerView.Adapter <RecyclerView.ViewHolder> {

    private ArrayList<Factory> factoriesArray;

    public FactoriesAdapter(ArrayList<Factory> factoriesArray) {
        this.factoriesArray = factoriesArray;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_manufacturer_factories, parent, false);
        return new FactoriesAdapter.FactoriesHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Factory factory = factoriesArray.get(position);

        FactoriesAdapter.FactoriesHolder factoriesHolder = (FactoriesAdapter.FactoriesHolder) holder;

        TextView factoryID = factoriesHolder.factoryID;
        TextView factoryManufacturerName = factoriesHolder.factoryManufacturerName;
        TextView factoryLocation = factoriesHolder.factoryLocation;

        factoryID.setText(factory.getFactoryID());
        factoryManufacturerName.setText(MainActivity.getSavedValues().getManufacturerName());
        factoryLocation.setText(factory.getFactoryLocation());
    }

    @Override
    public int getItemCount() {

        return factoriesArray.size();
    }

    private class FactoriesHolder extends RecyclerView.ViewHolder{
        TextView factoryID, factoryManufacturerName, factoryLocation;

        public FactoriesHolder(@NonNull View itemView) {
            super(itemView);

            factoryID = itemView.findViewById(R.id.item_manufacturer_factories_factoryID_textView);
            factoryManufacturerName = itemView.findViewById(R.id.item_manufacturer_factories_factoryManufacturerName_textView);
            factoryLocation = itemView.findViewById(R.id.item_manufacturer_factories_factoryLocation_textView);
        }
    }
}
