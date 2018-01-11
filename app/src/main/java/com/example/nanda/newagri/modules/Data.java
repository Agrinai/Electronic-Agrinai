package com.example.nanda.newagri.modules;

import android.widget.ListView;

import java.util.List;

/**
 * Created by Lokesh on 16-08-2017.
 */
public class Data
{
    private String Data;
    private int VegKG,VegPrice;
    private  String VegName;
    private  String name,emailorphone;
    private List<UserId> UserIdList;

    public String getData() {
        return Data;
    }

    public void setData(String data) {
        Data = data;
    }

    public int getVegKG() {
        return VegKG;
    }

    public void setVegKG(int vegKG) {
        VegKG = vegKG;
    }

    public int getVegPrice() {
        return VegPrice;
    }

    public void setVegPrice(int vegPrice) {
        VegPrice = vegPrice;
    }

    public String getVegName() {
        return VegName;
    }

    public void setVegName(String vegName) {
        VegName = vegName;
    }

    public List<UserId> getUserIdList() {
        return UserIdList;
    }

    public void setUserIdList(List<UserId> userIdList) {
        UserIdList = userIdList;
    }



    public static class UserId
    {
        private  String name,emailorphone;

        public String getEmailorphone() {
            return emailorphone;
        }

        public void setEmailorphone(String emailorphone) {
            this.emailorphone = emailorphone;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }


}
