package com.example.project1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.ServiceConfigurationError;

public class Account_Feedback extends AppCompatActivity
{
    EditText efeed;
    TextView date;
    Button bfeed;
    String sfeed,MobNoo,name,currentDate;
    CToast cToast;
    DatabaseReference reference;
    ClassFeedback classFeedback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account__feedback);

        cToast=new CToast();
        classFeedback=new ClassFeedback();
        efeed=(EditText)findViewById(R.id.feedbackText);
        bfeed=(Button)findViewById(R.id.feedbackSubmit);
        date=(TextView)findViewById(R.id.datefeed);

        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy,MM,dd");
        currentDate = df.format(c.getTime());

        date.setText(currentDate);

        SharedPreferences sharedPreferences=getSharedPreferences("UserLogin",MODE_PRIVATE);
        MobNoo=sharedPreferences.getString("MobNo",null);
        name=sharedPreferences.getString("Name",null);

        bfeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                sfeed=efeed.getText().toString();
                if (sfeed.equals(""))
                {
                    cToast.toast(getApplicationContext(),"Feedback cannot be empty",0);
                }
                else
                {
                    classFeedback.date=currentDate;
                    classFeedback.feedback=sfeed;
                    classFeedback.number=MobNoo;
                    classFeedback.username=name;

                    reference= FirebaseDatabase.getInstance().getReference().child("Feedback").child(MobNoo);
                    reference.setValue(classFeedback).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid)
                        {
                            cToast.toast(getApplicationContext(),"Thank you for your feedback",1);
                            efeed.setText("");
                        }
                    });
                }
            }
        });
    }
}
