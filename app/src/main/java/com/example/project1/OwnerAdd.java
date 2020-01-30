package com.example.project1;

public class OwnerAdd
{
    String Price,Activity,Time;

    public OwnerAdd()
    {
    }

    public OwnerAdd(String price, String activity, String time)
    {
        Price = price;
        Activity = activity;
        Time = time;
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

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }
}
