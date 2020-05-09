package com.example.project1;

public class CComplaint
{
    String shopID,activity,qrString,phone,subject,description;

    public CComplaint() {
    }

    public CComplaint(String shopID, String activity, String qrString, String phone, String subject, String description) {
        this.shopID = shopID;
        this.activity = activity;
        this.qrString = qrString;
        this.phone = phone;
        this.subject = subject;
        this.description = description;
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

    public String getQrString() {
        return qrString;
    }

    public void setQrString(String qrString) {
        this.qrString = qrString;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
