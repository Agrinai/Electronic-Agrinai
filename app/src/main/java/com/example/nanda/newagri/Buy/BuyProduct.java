package com.example.nanda.newagri.Buy;

/**
 * Created by Nanda on 2/4/2018.
 */
public class BuyProduct {
    private int id;
    private String name;
    private String sellerId;
    private String sellerUserId;
    private String mobilenumber;
    private String productname;
    private String kilo;
    private String price;
    private String image;
    private String mlat;
    private String mlong;

    public BuyProduct(int id,String sellerId,String sellerUserId, String name,String mobilenumber ,String productname, String kilo, String price, String image,String mlat,String mlong) {
        this.id = id;
        this.name = name;
        this.sellerId = sellerId;
        this.sellerUserId = sellerUserId;
        this.mobilenumber = mobilenumber;
        this.productname = productname;
        this.kilo = kilo;
        this.price = price;
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
    public String getSellerId() {
        return sellerId;
    }
    public String getSellerUserId() {
        return sellerUserId;
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

    public String getPrice() {
        return price;
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