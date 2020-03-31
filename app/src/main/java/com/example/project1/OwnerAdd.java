package com.example.project1;

import java.io.Serializable;

public class OwnerAdd
{
    String Price,Activity,ModelImg,ModelName,ShopID;

    public OwnerAdd()
    {
    }

    public OwnerAdd(String price, String activity , String modelImg, String modelName, String shopID) {
        Price = price;
        Activity = activity;
        ModelImg = modelImg;
        ModelName = modelName;
        ShopID = shopID;
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
}
