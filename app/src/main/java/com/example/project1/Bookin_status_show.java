package com.example.project1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Bookin_status_show extends AppCompatActivity
{
    Button gotoHome;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookin_status_show);

        gotoHome=(Button)findViewById(R.id.gotoHome);

        gotoHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent=new Intent(getApplicationContext(),CustomerSignedIn1.class);
                startActivity(intent);
            }
        });

    }

   /* @Override
    public void onBackPressed()
    {

    }*/
}
