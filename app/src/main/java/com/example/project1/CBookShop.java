package com.example.project1;

import java.util.Date;

public class CBookShop
{
    String CustomerName,CustomerMob,time,Date,ShopID,statusBit,shopAddress,shopName,qrCode;

    public CBookShop()
    {
        statusBit="0";
        qrCode="0";
    }

    public CBookShop(String customerName, String customerMob, String time, String date, String shopID, String statusBit, String shopAddress, String shopName, String qrCode) {
        CustomerName = customerName;
        CustomerMob = customerMob;
        this.time = time;
        Date = date;
        ShopID = shopID;
        this.statusBit = statusBit;
        this.shopAddress = shopAddress;
        this.shopName = shopName;
        this.qrCode = qrCode;
    }

    public String getCustomerName() {
        return CustomerName;
    }

    public void setCustomerName(String customerName) {
        CustomerName = customerName;
    }

    public String getCustomerMob() {
        return CustomerMob;
    }

    public void setCustomerMob(String customerMob) {
        CustomerMob = customerMob;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getShopID() {
        return ShopID;
    }

    public void setShopID(String shopID) {
        ShopID = shopID;
    }

    public String getStatusBit() {
        return statusBit;
    }

    public void setStatusBit(String statusBit) {
        this.statusBit = statusBit;
    }

    public String getShopAddress() {
        return shopAddress;
    }

    public void setShopAddress(String shopAddress) {
        this.shopAddress = shopAddress;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }
}
