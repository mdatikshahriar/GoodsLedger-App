package com.example.goods_ledger.Assets;

public class Factory {

    private String factoryKey, factoryManufacturerID, factoryID, factoryLocation;

    public Factory(String factoryKey, String factoryManufacturerID, String factoryID, String factoryLocation) {
        this.factoryKey = factoryKey;
        this.factoryManufacturerID = factoryManufacturerID;
        this.factoryID = factoryID;
        this.factoryLocation = factoryLocation;
    }

    public String getFactoryKey() {
        return factoryKey;
    }

    public void setFactoryKey(String factoryKey) {
        this.factoryKey = factoryKey;
    }

    public String getFactoryManufacturerID() {
        return factoryManufacturerID;
    }

    public void setFactoryManufacturerID(String factoryManufacturerID) {
        this.factoryManufacturerID = factoryManufacturerID;
    }

    public String getFactoryID() {
        return factoryID;
    }

    public void setFactoryID(String factoryID) {
        this.factoryID = factoryID;
    }

    public String getFactoryLocation() {
        return factoryLocation;
    }

    public void setFactoryLocation(String factoryLocation) {
        this.factoryLocation = factoryLocation;
    }
}
