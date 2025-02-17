package com.example.database_project;

public class Customers {
    private int Cid;
    private String cphone;
    private String Cname;
    private String Caddress;
    private int Cage;
    private int licenceID;

    public Customers(int cid, String cphone, String cname, String caddress, int cage, int licenceID) {
        Cid = cid;
        this.cphone = cphone;
        Cname = cname;
        Caddress = caddress;
        Cage = cage;
        this.licenceID = licenceID;
    }

    public String getCphone() {
        return cphone;
    }

    public int getCid() {
        return Cid;
    }

    public String getCname() {
        return Cname;
    }

    public String getCaddress() {
        return Caddress;
    }

    public int getCage() {
        return Cage;
    }

    public int getLicenceID() {
        return licenceID;
    }

    public void setCid(int cid) {
        Cid = cid;
    }

    public void setCname(String cname) {
        Cname = cname;
    }

    public void setCaddress(String caddress) {
        Caddress = caddress;
    }

    public void setCage(int cage) {
        Cage = cage;
    }

    public void setLicenceID(int licenceID) {
        this.licenceID = licenceID;
    }

    public void setCphone(String cphone) {
        this.cphone = cphone;
    }
}
