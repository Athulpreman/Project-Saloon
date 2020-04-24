package com.example.project1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class OwnerViewBookings extends AppCompatActivity
{
    Button serach;
    TextView noBooking,date;
    String date1,shopID,currentDate;
    DatabaseReference reference;
    ArrayList<CBookShop> list;
    RecyclerView recyclerViewcart;
    AdapterCart adaptercart;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_view_bookings);

        final Calendar calendar = Calendar.getInstance();
        final int year=calendar.get(calendar.YEAR);
        final int month=calendar.get(calendar.MONTH);
        final int day=calendar.get(calendar.DAY_OF_MONTH);

        recyclerViewcart=(RecyclerView)findViewById(R.id.ovbcv);
        list=new ArrayList<CBookShop>();

        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy,MM,dd");
        currentDate = df.format(c.getTime());

        SharedPreferences sharedPreferences=getSharedPreferences("OwnerLogin",MODE_PRIVATE);
        shopID=sharedPreferences.getString("shopID",null);

        serach=(Button)findViewById(R.id.ovbsearch);
        noBooking=(TextView)findViewById(R.id.ovbNoBooking);
        date=(TextView)findViewById(R.id.ovbdate);

        reference= FirebaseDatabase.getInstance().getReference().child("ShopOwners").child(shopID).child("Booking").child(currentDate);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                for (DataSnapshot snapshot:dataSnapshot.getChildren())
                {
                    CBookShop cBookShop=new CBookShop();
                    cBookShop=snapshot.getValue(CBookShop.class);
                    list.add(cBookShop);
                }
                adaptercart = new AdapterCart(OwnerViewBookings.this, list);
                recyclerViewcart.setAdapter(adaptercart);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        date.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                DatePickerDialog datePickerDialog=new DatePickerDialog(OwnerViewBookings.this, new DatePickerDialog.OnDateSetListener() {
                    @Override

                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth)
                    {
                        month=month+1;
                        String smonth=Integer.toString(month);
                        String sday=Integer.toString(dayOfMonth);
                        if (sday.length()==1)
                        {
                            sday="0"+sday;
                        }
                        if (smonth.length()==1)
                        {
                            smonth="0"+smonth;
                        }
                        date1=year+","+smonth+","+sday;
                        date.setText(date1);
                        list.clear();
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });
        serach.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                list.clear();
                recyclerViewcart.removeAllViews();

                reference= FirebaseDatabase.getInstance().getReference().child("ShopOwners").child(shopID).child("Booking").child(date1);
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                    {
                        for (DataSnapshot snapshot:dataSnapshot.getChildren())
                        {
                            CBookShop cBookShop=new CBookShop();
                            cBookShop=snapshot.getValue(CBookShop.class);
                            list.add(cBookShop);
                        }
                        adaptercart = new AdapterCart(OwnerViewBookings.this, list);
                        recyclerViewcart.setAdapter(adaptercart);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
    }
}
