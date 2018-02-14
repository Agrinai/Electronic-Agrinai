package com.example.nanda.newagri.Buy;

/**
 * Created by Nanda on 2/4/2018.
 */
public class BuyProduct {
    private int id;
    private String name;
    private String mobilenumber;
    private String productname;
    private String kilo;
    private String price;
    private String image;

    public BuyProduct(int id, String name,String mobilenumber ,String productname, String kilo, String price, String image) {
        this.id = id;
        this.name = name;
        this.mobilenumber = mobilenumber;
        this.productname = productname;
        this.kilo = kilo;
        this.price = price;
        this.image = image;
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

    public String getPrice() {
        return price;
    }

    public String getImage() {
        return image;
    }
}