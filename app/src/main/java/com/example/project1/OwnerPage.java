package com.example.project1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class OwnerPage extends AppCompatActivity
{
    String shopID1;
    Button AddActivity,Logout;
    Toast backToast;
    long backpress;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_page);
        this.setTitle("Owner");

        SharedPreferences sharedPreferences=getSharedPreferences("OwnerLogin",MODE_PRIVATE);
        shopID1=sharedPreferences.getString("shopID",null);

        AddActivity=(Button)findViewById(R.id.OwnerPageAddActivityButton);
        Logout=(Button)findViewById(R.id.OwnerPageLogout);

        Logout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(getApplicationContext(), "Logged Out", Toast.LENGTH_SHORT).show();
                SharedPreferences.Editor editor=getSharedPreferences("OwnerLogin",MODE_PRIVATE).edit();
                editor.clear();
                editor.commit();

                Intent intent=new Intent(getApplicationContext(),LoginPage.class);
                startActivity(intent);
            }
        });

        AddActivity.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                Intent intent1=new Intent(getApplicationContext(),OwnerSignedIn.class);
                startActivity(intent1);
            }
        });
    }

    public void onBackPressed()
    {
        if (backpress+2000>System.currentTimeMillis())
        {
            backToast.cancel();
            moveTaskToBack(true);
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        }
        else
        {
            backToast= Toast.makeText(getApplicationContext(), "Press again to exit", Toast.LENGTH_SHORT);
            backToast.show();
        }
        backpress=System.currentTimeMillis();
    }
}
