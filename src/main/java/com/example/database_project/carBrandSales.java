package com.example.database_project;

public class carBrandSales {
    private String carBrand;
    private int sales;

    public carBrandSales(String carBrand, int sales) {
        this.carBrand = carBrand;
        this.sales = sales;
    }

    public String getCarBrand() {
        return carBrand;
    }

    public int getSales() {
        return sales;
    }

    public void setCarBrand(String carBrand) {
        this.carBrand = carBrand;
    }

    public void setSales(int sales) {
        this.sales = sales;
    }
}
