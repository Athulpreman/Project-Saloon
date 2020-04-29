package com.example.project1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class OwnerEditService extends AppCompatActivity
{
    String shopID,Activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_edit_service);

        Intent intent=getIntent();
        Activity=intent.getStringExtra("Activity");
        shopID=intent.getStringExtra("shopID");

    }
}
