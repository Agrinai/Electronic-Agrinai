package com.example.nanda.newagri.Sell;

/**
 * Created by Nanda on 2/4/2018.
 */
public class SellProduct {
    private int id;
    private String name;
    private String mobilenumber;
    private String productname;
    private String kilo;
    private String image;
    private String mlat;
    private String mlong;

    public SellProduct(int id, String name,String mobilenumber, String productname, String kilo,String image,String mlat,String mlong) {
        this.id = id;
        this.name = name;
        this.mobilenumber = mobilenumber;
        this.productname = productname;
        this.kilo = kilo;
        this.image = image;
        this.mlat = mlat;
        this.mlong = mlong;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getMobilenumber() {
        return mobilenumber;
    }

    public String getProductname() {
        return productname;
    }

    public String getKilo() {
        return kilo;
    }

    public String getImage() {
        return image;
    }

    public String getLat() {
        return mlat;
    }
    public String getLong() {
        return mlong;
    }
}