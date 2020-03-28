package com.example.project1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Check_Availability_Of_Shop extends AppCompatActivity
{
    String selectedDate;
    Button b1,b2,b3,b4,b5,b6,b7,b8;
    DatabaseReference reference;
    String shopID="1234",date="2020,04,30";
    CBookShop cBookShop;
    int i,selected;
    String[]array;
    int []listBookingStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check__availability__of__shop);

        b1=(Button)findViewById(R.id.one);
        b2=(Button)findViewById(R.id.two);
        b3=(Button)findViewById(R.id.three);
        b4=(Button)findViewById(R.id.four);
        b5=(Button)findViewById(R.id.five);
        b6=(Button)findViewById(R.id.six);
        b7=(Button)findViewById(R.id.seven);
        b8=(Button)findViewById(R.id.eight);

        cBookShop=new CBookShop();

        listBookingStatus= new int[]{0, 0, 0, 0, 0, 0, 0, 0};
        array=new String[]{"0","0","0","0","0","0","0","0","0"};


        reference= FirebaseDatabase.getInstance().getReference().child("ShopOwners").child(shopID).child("Booking").child("2020,04,24");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {

                i=0;
                int count= (int) dataSnapshot.getChildrenCount();
                for (DataSnapshot snapshot:dataSnapshot.getChildren())
                {
                    if (snapshot.exists())
                    {
                        cBookShop=snapshot.getValue(CBookShop.class);
                        array[i]=cBookShop.time;
                        Log.d("aaaaaaaaaaa",cBookShop.time);
                        i++;
                    }
                    if (i==count)
                    {
                        calcu();
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        calcu();



        refresh();
        for (i=0;i<=7;i++)
        {
            Log.d("aaaaaaaaaliststatus", String.valueOf(listBookingStatus[i])+i);
        }

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if (listBookingStatus[0]==1)
                {

                }
                else
                {
                    Toast.makeText(getApplicationContext(), "1", Toast.LENGTH_SHORT).show();
                    refresh();
                    b1.setBackgroundColor(getResources().getColor(R.color.clicked));
                    selected=1;
                }
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if (listBookingStatus[1]==1)
                {

                }
                else
                {
                    Toast.makeText(getApplicationContext(), "2", Toast.LENGTH_SHORT).show();
                    refresh();
                    b2.setBackgroundColor(getResources().getColor(R.color.clicked));
                    selected=2;
                }
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if (listBookingStatus[2]==1)
                {

                }
                else
                {
                    Toast.makeText(getApplicationContext(), "3", Toast.LENGTH_SHORT).show();
                    refresh();
                    b3.setBackgroundColor(getResources().getColor(R.color.clicked));
                    selected=3;
                }
            }
        });
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if (listBookingStatus[3]==1)
                {

                }
                else
                {
                    Toast.makeText(getApplicationContext(), "4", Toast.LENGTH_SHORT).show();
                    refresh();
                    b4.setBackgroundColor(getResources().getColor(R.color.clicked));
                    selected=4;
                }
            }
        });
        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if (listBookingStatus[4]==1)
                {

                }
                else
                {
                    Toast.makeText(getApplicationContext(), "5", Toast.LENGTH_SHORT).show();
                    refresh();
                    b5.setBackgroundColor(getResources().getColor(R.color.clicked));
                    selected=5;
                }
            }
        });
        b6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if (listBookingStatus[5]==1)
                {

                }
                else
                {
                    Toast.makeText(getApplicationContext(), "6", Toast.LENGTH_SHORT).show();
                    refresh();
                    b6.setBackgroundColor(getResources().getColor(R.color.clicked));
                    selected=6;
                }
            }
        });
        b7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if (listBookingStatus[6]==1)
                {

                }
                else
                {
                    Toast.makeText(getApplicationContext(), "7", Toast.LENGTH_SHORT).show();
                    refresh();
                    b7.setBackgroundColor(getResources().getColor(R.color.clicked));
                    selected=7;
                }
            }
        });
        b8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if (listBookingStatus[7]==1)
                {

                }
                else
                {
                    Toast.makeText(getApplicationContext(), "8", Toast.LENGTH_SHORT).show();
                    refresh();
                    b8.setBackgroundColor(getResources().getColor(R.color.clicked));
                    selected=8;
                }
            }
        });


    }
    public void refresh()
    {
        for(i=0;i<8;i++)
        {
            if (i==0)
            {
                if (listBookingStatus[i]==1)
                {
                    b1.setBackgroundColor(getResources().getColor(R.color.booked));
                }
                else
                {
                    b1.setBackgroundColor(getResources().getColor(R.color.common_google_signin_btn_text_dark_disabled));
                }
            }
            else if (i==1)
            {
                if (listBookingStatus[i]==1)
                {
                    b2.setBackgroundColor(getResources().getColor(R.color.booked));
                }
                else
                {
                    b2.setBackgroundColor(getResources().getColor(R.color.common_google_signin_btn_text_dark_disabled));
                }
            }
            else if (i==2)
            {
                if (listBookingStatus[i]==1)
                {
                    b3.setBackgroundColor(getResources().getColor(R.color.booked));
                }
                else
                {
                    b3.setBackgroundColor(getResources().getColor(R.color.common_google_signin_btn_text_dark_disabled));
                }
            }
            else if (i==3)
            {
                if (listBookingStatus[i]==1)
                {
                    b4.setBackgroundColor(getResources().getColor(R.color.booked));
                }
                else
                {
                    b4.setBackgroundColor(getResources().getColor(R.color.common_google_signin_btn_text_dark_disabled));
                }
            }
            else if (i==4)
            {
                if (listBookingStatus[i]==1)
                {
                    b5.setBackgroundColor(getResources().getColor(R.color.booked));
                }
                else
                {
                    b5.setBackgroundColor(getResources().getColor(R.color.common_google_signin_btn_text_dark_disabled));
                }
            }
            else if (i==5)
            {
                if (listBookingStatus[i]==1)
                {
                    b6.setBackgroundColor(getResources().getColor(R.color.booked));
                }
                else
                {
                    b6.setBackgroundColor(getResources().getColor(R.color.common_google_signin_btn_text_dark_disabled));
                }
            }
            else if (i==6)
            {
                if (listBookingStatus[i]==1)
                {
                    b7.setBackgroundColor(getResources().getColor(R.color.booked));
                }
                else
                {
                    b7.setBackgroundColor(getResources().getColor(R.color.common_google_signin_btn_text_dark_disabled));
                }
            }
            else if (i==7)
            {
                if (listBookingStatus[i]==1)
                {
                    b8.setBackgroundColor(getResources().getColor(R.color.booked));
                }
                else
                {
                    b8.setBackgroundColor(getResources().getColor(R.color.common_google_signin_btn_text_dark_disabled));
                }
            }
            else
            {
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
            }

        }
    }
    public void calcu()
    {
        for (i=0;i<=8;i++)
        {
            if (array[i].equals("10-11"))
            {
                listBookingStatus[0]=1;
                Log.d("aaaaaa","1");
            }
            else if (array[i].equals("11-12"))
            {
                listBookingStatus[1]=1;
            }
            else if (array[i].equals("12-13"))
            {
                listBookingStatus[2]=1;
            }
            else if (array[i].equals("14-15"))
            {
                listBookingStatus[3]=1;
            }
            else if (array[i].equals("15-16"))
            {
                listBookingStatus[4]=1;
            }
            else if (array[i].equals("16-17"))
            {
                listBookingStatus[5]=1;
            }
            else if (array[i].equals("17-18"))
            {
                listBookingStatus[6]=1;
            }
            else if (array[i].equals("18-19"))
            {
                Log.d("aaaaaa","1");
                listBookingStatus[7]=1;
            }
            else if (array[i].equals("0"))
            {
                refresh();
                break;
            }
        }
    }
}
