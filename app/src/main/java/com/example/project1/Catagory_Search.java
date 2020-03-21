package com.example.project1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class Catagory_Search extends AppCompatActivity
{
    TextView activityName;
    String acName;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catagory__search);

        activityName=(TextView)findViewById(R.id.activityName);

        Intent intent=getIntent();
        acName=intent.getStringExtra("activity");

        activityName.setText(acName);
        Toast.makeText(this, acName, Toast.LENGTH_SHORT).show();





    }
}
