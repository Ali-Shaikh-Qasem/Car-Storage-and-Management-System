package com.example.database_project;

public class Cars {
    private int carID, supplierID;
    private String make;
    private String model;
    private int year;
    private String color;
    private double price;
    private int warehouseID;

    public Cars(int carID, int warehouseID, double price, String color, int year, String model, String make, int supplierID) {
        this.carID = carID;
        this.warehouseID = warehouseID;
        this.price = price;
        this.color = color;
        this.year = year;
        this.model = model;
        this.make = make;
        this.supplierID = supplierID;
    }

    public void setCarID(int carID) {
        this.carID = carID;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setSupplierID(int supplierID) {
        this.supplierID = supplierID;
    }

    public void setWarehouseID(int warehouseID) {
        this.warehouseID = warehouseID;
    }
    public int getCarID() {
        return carID;
    }

    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    public int getYear() {
        return year;
    }

    public String getColor() {
        return color;
    }

    public double getPrice() {
        return price;
    }

    public int getSupplierID() {
        return supplierID;
    }

    public int getWarehouseID() {
        return warehouseID;
    }
}
