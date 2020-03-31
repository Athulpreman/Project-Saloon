package com.example.project1;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class CToast
{
    public CToast()
    {
    }
    public void toast(Context context,String s,int i)
    {
        if (i==1)
        {
            Toast toast=Toast.makeText(context,s,Toast.LENGTH_LONG);
            View view =toast.getView();
            view.setBackgroundColor(Color.GREEN);
            TextView toastMessage = (TextView) toast.getView().findViewById(android.R.id.message);
            toastMessage.setTextColor(Color.BLACK);
            toast.show();
        }
        else
        {
            Toast toast=Toast.makeText(context,s,Toast.LENGTH_SHORT);
            View view =toast.getView();
            view.setBackgroundColor(Color.GREEN);
            TextView toastMessage = (TextView) toast.getView().findViewById(android.R.id.message);
            toastMessage.setTextColor(Color.BLACK);
            toast.show();
        }
    }



    /*public boolean isInternetWorking() {
        boolean success = false;
        try {
            URL url = new URL("https://google.com");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(10000);
            connection.connect();
            success = connection.getResponseCode() == 200;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return success;//true when there is connection
    }*/



}
