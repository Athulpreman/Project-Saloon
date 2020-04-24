package com.example.project1;

public class CFav
{
    String shopID,activity;

    public CFav() {
    }

    public CFav(String shopID, String activity) {
        this.shopID = shopID;
        this.activity = activity;
    }

    public String getShopID() {
        return shopID;
    }

    public void setShopID(String shopID) {
        this.shopID = shopID;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }
}
