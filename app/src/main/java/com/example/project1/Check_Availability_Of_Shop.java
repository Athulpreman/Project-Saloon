package com.example.project1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Check_Availability_Of_Shop extends AppCompatActivity
{
    String selectedDate;
    Button b1,b2,b3,b4,b5,b6,b7,b8;
    DatabaseReference reference;
    CBookShop cBookShop;
    int i,click;
    String selected="";
    String[]array;
    int []listBookingStatus;
    Button Book;
    String sdate,sactivity,sshopID,smob,sname,sshopName,saddress,qrstring,stime,price;
    DatabaseReference ref,customerRef;
    TextView progressText;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check__availability__of__shop);

        Book=(Button)findViewById(R.id.bookButtonTime);
        b1=(Button)findViewById(R.id.one);
        b2=(Button)findViewById(R.id.two);
        b3=(Button)findViewById(R.id.three);
        b4=(Button)findViewById(R.id.four);
        b5=(Button)findViewById(R.id.five);
        b6=(Button)findViewById(R.id.six);
        b7=(Button)findViewById(R.id.seven);
        b8=(Button)findViewById(R.id.eight);
        progressBar=(ProgressBar)findViewById(R.id.Progressba);
        progressText=(TextView)findViewById(R.id.ProgressbaText);

        SharedPreferences sharedPreferences=getSharedPreferences("Book",MODE_PRIVATE);
        sactivity=sharedPreferences.getString("activity",null);
        sdate=sharedPreferences.getString("date",null);
        sname=sharedPreferences.getString("name",null);
        saddress=sharedPreferences.getString("address",null);
        sshopID=sharedPreferences.getString("shopID",null);
        sshopName=sharedPreferences.getString("shopName",null);
        smob=sharedPreferences.getString("mobile",null);



        cBookShop=new CBookShop();

        listBookingStatus= new int[]{0, 0, 0, 0, 0, 0, 0, 0};
        array=new String[]{"0","0","0","0","0","0","0","0","0"};


        reference= FirebaseDatabase.getInstance().getReference().child("ShopOwners").child(sshopID).child("Booking").child(sdate);
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
                        i++;
                    }
                    if (i==count)
                    {
                        calcu();
                        checkFullBooked();
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {
            }
        });

        refresh();

        Book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                progressBar.setVisibility(View.VISIBLE);
                progressText.setVisibility(View.VISIBLE);

                if (selected.equals(""))
                {
                    Toast.makeText(Check_Availability_Of_Shop.this, "Chose a time", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.INVISIBLE);
                    progressText.setVisibility(View.INVISIBLE);
                }
                else
                {
                    progressBar.setVisibility(View.VISIBLE);
                    progressText.setVisibility(View.VISIBLE);

                    Book.setEnabled(false);

                    Timer buttonTimer = new Timer();
                    buttonTimer.schedule(new TimerTask() {

                        @Override
                        public void run() {
                            runOnUiThread(new Runnable() {

                                @Override
                                public void run() {
                                    Book.setEnabled(true);
                                }
                            });
                        }
                    }, 5000);

                    qrstring=smob+"-"+sdate+"-"+selected;

                    ref = FirebaseDatabase.getInstance().getReference().child("ShopOwners").child(sshopID).child("Booking").child(selected);
                    customerRef= FirebaseDatabase.getInstance().getReference().child("Customer").child(smob).child("Booking").child(sdate);

                    cBookShop.time=selected;
                    cBookShop.Date=sdate;
                    cBookShop.qrCode=qrstring;
                    cBookShop.activity=sactivity;
                    cBookShop.shopName=sshopName;
                    cBookShop.shopAddress=saddress;
                    cBookShop.ShopID=sshopID;
                    cBookShop.CustomerMob=smob;
                    cBookShop.CustomerName=sname;
                    cBookShop.Price=price;

                    reference.child(selected).setValue(cBookShop).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid)
                        {
                            customerRef.setValue(cBookShop).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid)
                                {
                                    Toast.makeText(Check_Availability_Of_Shop.this, "Booked sucessfully", Toast.LENGTH_SHORT).show();
                                    progressBar.setVisibility(View.INVISIBLE);
                                    progressText.setVisibility(View.INVISIBLE);
                                    Intent intent00=new Intent(getApplicationContext(),Bookin_status_show.class);
                                    startActivity(intent00);
                                }
                            });
                        }
                    });

                }
            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if (listBookingStatus[0]==1)
                {
                    selected="";
                    refresh();
                    Toast.makeText(Check_Availability_Of_Shop.this, "The time slot is booked.....\n Chose another time", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "1", Toast.LENGTH_SHORT).show();
                    refresh();
                    b1.setBackground(getResources().getDrawable(R.drawable.selecteddseat));
                    selected="10-11";
                }
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if (listBookingStatus[1]==1)
                {
                    selected="";
                    refresh();
                    Toast.makeText(Check_Availability_Of_Shop.this, "The time slot is booked.....\n Chose another time", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "2", Toast.LENGTH_SHORT).show();
                    refresh();
                    b2.setBackground(getResources().getDrawable(R.drawable.selecteddseat));
                    selected="11-12";
                }
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if (listBookingStatus[2]==1)
                {
                    selected="";
                    refresh();
                    Toast.makeText(Check_Availability_Of_Shop.this, "The time slot is booked.....\n Chose another time", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "3", Toast.LENGTH_SHORT).show();
                    refresh();
                    b3.setBackground(getResources().getDrawable(R.drawable.selecteddseat));
                    selected="12-13";
                }
            }
        });
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if (listBookingStatus[3]==1)
                {
                    selected="";
                    refresh();
                    Toast.makeText(Check_Availability_Of_Shop.this, "The time slot is booked.....\n Chose another time", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "4", Toast.LENGTH_SHORT).show();
                    refresh();
                    b4.setBackground(getResources().getDrawable(R.drawable.selecteddseat));
                    selected="14-15";
                }
            }
        });
        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if (listBookingStatus[4]==1)
                {
                    selected="";
                    refresh();
                    Toast.makeText(Check_Availability_Of_Shop.this, "The time slot is booked.....\n Chose another time", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "5", Toast.LENGTH_SHORT).show();
                    refresh();
                    b5.setBackground(getResources().getDrawable(R.drawable.selecteddseat));
                    selected="15-16";
                }
            }
        });
        b6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if (listBookingStatus[5]==1)
                {
                    selected="";
                    refresh();
                    Toast.makeText(Check_Availability_Of_Shop.this, "The time slot is booked.....\n Chose another time", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "6", Toast.LENGTH_SHORT).show();
                    refresh();
                    b6.setBackground(getResources().getDrawable(R.drawable.selecteddseat));
                    selected="16-17";
                }
            }
        });
        b7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if (listBookingStatus[6]==1)
                {
                    selected="";
                    refresh();
                    Toast.makeText(Check_Availability_Of_Shop.this, "The time slot is booked.....\n Chose another time", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "7", Toast.LENGTH_SHORT).show();
                    refresh();
                    b7.setBackground(getResources().getDrawable(R.drawable.selecteddseat));
                    selected="17-18";
                }
            }
        });
        b8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if (listBookingStatus[7]==1)
                {
                    selected="";
                    refresh();
                    Toast.makeText(Check_Availability_Of_Shop.this, "The time slot is booked.....\n Chose another time", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "8", Toast.LENGTH_SHORT).show();
                    refresh();
                    b8.setBackground(getResources().getDrawable(R.drawable.selecteddseat));
                    selected="18-19";
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
                    b1.setBackground(getResources().getDrawable(R.drawable.bookedseat_512));
                }
                else
                {
                    b1.setBackground(getResources().getDrawable(R.drawable.availableseat_512));
                }
            }
            else if (i==1)
            {
                if (listBookingStatus[i]==1)
                {
                    b2.setBackground(getResources().getDrawable(R.drawable.bookedseat_512));
                }
                else
                {
                    b2.setBackground(getResources().getDrawable(R.drawable.availableseat_512));
                }
            }
            else if (i==2)
            {
                if (listBookingStatus[i]==1)
                {
                    b3.setBackground(getResources().getDrawable(R.drawable.bookedseat_512));
                }
                else
                {
                    b3.setBackground(getResources().getDrawable(R.drawable.availableseat_512));
                }
            }
            else if (i==3)
            {
                if (listBookingStatus[i]==1)
                {
                    b4.setBackground(getResources().getDrawable(R.drawable.bookedseat_512));
                }
                else
                {
                    b4.setBackground(getResources().getDrawable(R.drawable.availableseat_512));
                }
            }
            else if (i==4)
            {
                if (listBookingStatus[i]==1)
                {
                    b5.setBackground(getResources().getDrawable(R.drawable.bookedseat_512));
                }
                else
                {
                    b5.setBackground(getResources().getDrawable(R.drawable.availableseat_512));
                }
            }
            else if (i==5)
            {
                if (listBookingStatus[i]==1)
                {
                    b6.setBackground(getResources().getDrawable(R.drawable.bookedseat_512));
                }
                else
                {
                    b6.setBackground(getResources().getDrawable(R.drawable.availableseat_512));
                }
            }
            else if (i==6)
            {
                if (listBookingStatus[i]==1)
                {
                    b7.setBackground(getResources().getDrawable(R.drawable.bookedseat_512));
                }
                else
                {
                    b7.setBackground(getResources().getDrawable(R.drawable.availableseat_512));
                }
            }
            else if (i==7)
            {
                if (listBookingStatus[i]==1)
                {
                    b8.setBackground(getResources().getDrawable(R.drawable.bookedseat_512));
                }
                else
                {
                    b8.setBackground(getResources().getDrawable(R.drawable.availableseat_512));
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
                listBookingStatus[7]=1;
            }
            else if (array[i].equals("0"))
            {
                refresh();
                break;
            }
        }
    }
    public void checkFullBooked()
    {
        if (listBookingStatus[0]==1&&listBookingStatus[1]==1&&listBookingStatus[2]==1&&listBookingStatus[3]==1&&listBookingStatus[4]==1&&listBookingStatus[5]==1&&listBookingStatus[6]==1&&listBookingStatus[7]==1)
        {
            Toast.makeText(this, "All slots are booke... \n Chose a different date", Toast.LENGTH_SHORT).show();
        }
    }
}
