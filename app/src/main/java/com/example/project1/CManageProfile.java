package com.example.project1;

public class CManageProfile
{
    String Email,Gender,Address1,Address2,Address3,Address4,Image;

    public CManageProfile()
    {
    }

    public CManageProfile(String email, String gender, String address1, String address2, String address3, String address4, String image) {
        Email = email;
        Gender = gender;
        Address1 = address1;
        Address2 = address2;
        Address3 = address3;
        Address4 = address4;
        Image = image;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getAddress1() {
        return Address1;
    }

    public void setAddress1(String address1) {
        Address1 = address1;
    }

    public String getAddress2() {
        return Address2;
    }

    public void setAddress2(String address2) {
        Address2 = address2;
    }

    public String getAddress3() {
        return Address3;
    }

    public void setAddress3(String address3) {
        Address3 = address3;
    }

    public String getAddress4() {
        return Address4;
    }

    public void setAddress4(String address4) {
        Address4 = address4;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}
