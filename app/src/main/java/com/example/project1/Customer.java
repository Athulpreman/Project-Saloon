package com.example.project1;

public class Customer
{
    String name,mobileNum,password;

    public Customer()
    {
    }

    public Customer(String name, String mobileNum, String password)
    {
        this.name = name;
        this.mobileNum = mobileNum;
        this.password = password;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getMobileNum()
    {
        return mobileNum;
    }

    public void setMobileNum(String mobileNum)
    {
        this.mobileNum = mobileNum;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }
}
