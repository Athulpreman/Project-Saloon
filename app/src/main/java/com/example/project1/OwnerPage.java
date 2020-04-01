package com.example.project1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class OwnerPage extends AppCompatActivity
{
    String shopID1;
    Toast backToast;
    long backpress;
    CardView c1,c2,c3,c4,c5,c6,c7,c8;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_page);
        this.setTitle("Owner");

        SharedPreferences sharedPreferences=getSharedPreferences("OwnerLogin",MODE_PRIVATE);
        shopID1=sharedPreferences.getString("shopID",null);

        c1=(CardView)findViewById(R.id.card1);
        c2=(CardView)findViewById(R.id.card2);
        c3=(CardView)findViewById(R.id.card3);
        c4=(CardView)findViewById(R.id.card4);
        c5=(CardView)findViewById(R.id.card5);
        c6=(CardView)findViewById(R.id.card6);
        c7=(CardView)findViewById(R.id.card7);
        c8=(CardView)findViewById(R.id.card8);

        c6.setOnClickListener(new View.OnClickListener()
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

        c2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                Intent intent1=new Intent(getApplicationContext(),OwnerSignedIn.class);
                startActivity(intent1);
            }
        });
        c8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
              Intent intent=new Intent(getApplicationContext(),Location_Owner.class);
                startActivity(intent);
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
