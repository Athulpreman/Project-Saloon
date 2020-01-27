package com.example.project1;

public class Owner
{
    String OwnerName,ShopName,ShopID,OwnerMobile,Address,EmployeeName,EmployeeMobile,Password,Image1,Image2,Image3;
    Boolean status;

    public Owner()
    {
        status=false;
    }

    public Owner(String ownerName, String shopName, String shopID, String ownerMobile, String address, String employeeName, String employeeMobile, String password, String image1, String image2, String image3, Boolean status) {
        OwnerName = ownerName;
        ShopName = shopName;
        ShopID = shopID;
        OwnerMobile = ownerMobile;
        Address = address;
        EmployeeName = employeeName;
        EmployeeMobile = employeeMobile;
        Password = password;
        Image1 = image1;
        Image2 = image2;
        Image3 = image3;
        this.status = status;
    }

    public String getOwnerName() {
        return OwnerName;
    }

    public void setOwnerName(String ownerName) {
        OwnerName = ownerName;
    }

    public String getShopName() {
        return ShopName;
    }

    public void setShopName(String shopName) {
        ShopName = shopName;
    }

    public String getShopID() {
        return ShopID;
    }

    public void setShopID(String shopID) {
        ShopID = shopID;
    }

    public String getOwnerMobile() {
        return OwnerMobile;
    }

    public void setOwnerMobile(String ownerMobile) {
        OwnerMobile = ownerMobile;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getEmployeeName() {
        return EmployeeName;
    }

    public void setEmployeeName(String employeeName) {
        EmployeeName = employeeName;
    }

    public String getEmployeeMobile() {
        return EmployeeMobile;
    }

    public void setEmployeeMobile(String employeeMobile) {
        EmployeeMobile = employeeMobile;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getImage1() {
        return Image1;
    }

    public void setImage1(String image1) {
        Image1 = image1;
    }

    public String getImage2() {
        return Image2;
    }

    public void setImage2(String image2) {
        Image2 = image2;
    }

    public String getImage3() {
        return Image3;
    }

    public void setImage3(String image3) {
        Image3 = image3;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
