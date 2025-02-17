package com.example.database_project;

public class Warehouse {
    private int Wid;
    private String name;
    private String location;
    private int capacity  ;
    private int availableCars;

    public Warehouse(int wid, int capacity, int availableCars, String location, String name) {
        Wid = wid;
        this.capacity = capacity;
        this.availableCars = availableCars;
        this.location = location;
        this.name = name;
    }

    public int getWid() {
        return Wid;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getAvailableCars() {
        return availableCars;
    }

    public void setWid(int wid) {
        Wid = wid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setAvailableCars(int availableCars) {
        this.availableCars = availableCars;
    }
}
