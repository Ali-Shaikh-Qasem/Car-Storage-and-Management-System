package com.example.database_project;

public class Suppliers {
    private int Sid;
    private String Sname;
    private String Sphone;
    private String Scountry;
    private double Srating;

    public Suppliers(int sid, String sname, String sphone, String scountry, double srating) {
        Sid = sid;
        Sname = sname;
        Sphone = sphone;
        Scountry = scountry;
        Srating = srating;
    }

    public int getSid() {
        return Sid;
    }

    public String getSname() {
        return Sname;
    }

    public String getSphone() {
        return Sphone;
    }

    public String getScountry() {
        return Scountry;
    }

    public double getSrating() {
        return Srating;
    }

    public void setSid(int sid) {
        Sid = sid;
    }

    public void setSrating(double srating) {
        Srating = srating;
    }

    public void setSphone(String sphone) {
        Sphone = sphone;
    }

    public void setScountry(String scountry) {
        Scountry = scountry;
    }

    public void setSname(String sname) {
        Sname = sname;
    }
}
