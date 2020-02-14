package com.example.project1;

import java.util.Date;

public class CBookShop
{
    String CustomerName,CustomerMob,time;
    Date Date;
    Boolean status;

    public CBookShop()
    {
        status=false;
    }

    public CBookShop(String customerName, String customerMob, String time, java.util.Date date, Boolean status) {
        CustomerName = customerName;
        CustomerMob = customerMob;
        this.time = time;
        Date = date;
        this.status = status;
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

    public java.util.Date getDate() {
        return Date;
    }

    public void setDate(java.util.Date date) {
        Date = date;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
