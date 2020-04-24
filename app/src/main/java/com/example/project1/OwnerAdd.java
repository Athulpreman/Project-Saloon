package com.example.project1;

import java.io.Serializable;

public class OwnerAdd
{
    String Price,Activity,ModelImg,ModelName,ShopID;
    double rating;

    public OwnerAdd()
    {
        rating=0.0;
    }

    public OwnerAdd(String price, String activity, String modelImg, String modelName, String shopID, double rating) {
        Price = price;
        Activity = activity;
        ModelImg = modelImg;
        ModelName = modelName;
        ShopID = shopID;
        this.rating = rating;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getActivity() {
        return Activity;
    }

    public void setActivity(String activity) {
        Activity = activity;
    }

    public String getModelImg() {
        return ModelImg;
    }

    public void setModelImg(String modelImg) {
        ModelImg = modelImg;
    }

    public String getModelName() {
        return ModelName;
    }

    public void setModelName(String modelName) {
        ModelName = modelName;
    }

    public String getShopID() {
        return ShopID;
    }

    public void setShopID(String shopID) {
        ShopID = shopID;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
}
