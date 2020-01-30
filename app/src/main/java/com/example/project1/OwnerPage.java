package com.example.project1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class OwnerPage extends AppCompatActivity
{
    String shopID1;
    Button AddActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_page);

        Intent intent=getIntent();
        shopID1=intent.getStringExtra("shopID");

        AddActivity=(Button)findViewById(R.id.OwnerPageAddActivityButton);

        AddActivity.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent1=new Intent(getApplicationContext(),OwnerSignedIn.class);
                intent1.putExtra("shopID",shopID1);
                startActivity(intent1);
            }
        });
    }
}
