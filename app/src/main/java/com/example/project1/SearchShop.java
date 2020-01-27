package com.example.project1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class SearchShop extends AppCompatActivity
{
    String mobno;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_shop);

        Intent intent=getIntent();
        mobno=intent.getStringExtra("mobee");
        Toast.makeText(getApplicationContext(), mobno, Toast.LENGTH_SHORT).show();

    }
}
