package com.example.project1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

public class AdminPage extends AppCompatActivity
{
    Button CheckNewShop;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_page);

        CheckNewShop=(Button)findViewById(R.id.checkForNewShop);

        CheckNewShop.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent=new Intent(getApplicationContext(),AdminCheckNewShopAcceptance.class);
                startActivity(intent);
            }
        });
    }
}
