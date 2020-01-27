package com.example.project1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class CustomerSignedIn extends AppCompatActivity
{
    String mobNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_signed_in);

        Intent intent=getIntent();
        mobNo=intent.getStringExtra("mob2");
        Toast.makeText(getApplicationContext(), mobNo, Toast.LENGTH_SHORT).show();
    }
}
