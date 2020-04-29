package com.example.project1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class OwnerViewUpcomingBookings extends AppCompatActivity
{
    TextView viewTodaysBooking,date,noBooking;
    Button search;
    String sDate,shopID,currentDate;
    DatabaseReference reference;
    ArrayList<CBookShop>list;
    int m;
    ProgressBar progressBar;
    TextView progressText;
    RecyclerView recyclerView;
    AdapterUpcomingBookings adapterUpcomingBookings;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_view_upcoming_bookings);

        SharedPreferences sharedPreferences=getSharedPreferences("OwnerLogin",MODE_PRIVATE);
        shopID=sharedPreferences.getString("shopID",null);

        search=(Button)findViewById(R.id.viewUpcomingBookingSearchButton);
        viewTodaysBooking=(TextView)findViewById(R.id.ViewTodaysbooingTextview);
        date=(TextView)findViewById(R.id.upcomingBookingDate);
        noBooking=(TextView)findViewById(R.id.noBookingTextview);
        progressBar=(ProgressBar)findViewById(R.id.Progressba);
        progressText=(TextView)findViewById(R.id.ProgressbaText);

        list=new ArrayList<CBookShop>();

        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy,MM,dd");
        currentDate = df.format(c.getTime());

        final Calendar calendar = Calendar.getInstance();
        final int year=calendar.get(calendar.YEAR);
        final int month=calendar.get(calendar.MONTH);
        final int day=calendar.get(calendar.DAY_OF_MONTH);

        recyclerView=(RecyclerView)findViewById(R.id.OwnerViewUpcomingBookingrv);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        date.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                DatePickerDialog datePickerDialog=new DatePickerDialog(OwnerViewUpcomingBookings.this, new DatePickerDialog.OnDateSetListener() {
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
                        sDate=year+","+smonth+","+sday;
                        date.setText(sDate);
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });

        viewTodaysBooking.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                list.clear();
                recyclerView.removeAllViews();

                progressBar.setVisibility(View.VISIBLE);
                progressText.setVisibility(View.VISIBLE);

                noBooking.setVisibility(View.INVISIBLE);
                date.setText(currentDate);
                reference= FirebaseDatabase.getInstance().getReference().child("ShopOwners").child(shopID).child("Booking").child(currentDate);
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                    {
                        if (dataSnapshot.exists())
                        {
                            m=0;
                            for (DataSnapshot snapshot:dataSnapshot.getChildren())
                            {
                                m++;
                                CBookShop cBookShop=new CBookShop();
                                cBookShop=snapshot.getValue(CBookShop.class);
                                list.add(cBookShop);
                                if (m==dataSnapshot.getChildrenCount())
                                {
                                    progressBar.setVisibility(View.INVISIBLE);
                                    progressText.setVisibility(View.INVISIBLE);

                                    adapterUpcomingBookings = new AdapterUpcomingBookings(OwnerViewUpcomingBookings.this, list);
                                    recyclerView.setAdapter(adapterUpcomingBookings);
                                }
                            }
                        }
                        else
                        {
                            progressBar.setVisibility(View.INVISIBLE);
                            progressText.setVisibility(View.INVISIBLE);
                            noBooking.setVisibility(View.VISIBLE);
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError)
                    {

                    }
                });
            }
        });

        search.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                list.clear();
                recyclerView.removeAllViews();

                noBooking.setVisibility(View.INVISIBLE);
                sDate=date.getText().toString();
                if (sDate.equals(""))
                {
                    Toast.makeText(OwnerViewUpcomingBookings.this, "Select a date", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    progressBar.setVisibility(View.VISIBLE);
                    progressText.setVisibility(View.VISIBLE);

                    reference= FirebaseDatabase.getInstance().getReference().child("ShopOwners").child(shopID).child("Booking").child(sDate);
                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                        {
                            if (dataSnapshot.exists())
                            {
                                m=0;
                                for (DataSnapshot snapshot:dataSnapshot.getChildren())
                                {
                                    m++;
                                    CBookShop cBookShop=new CBookShop();
                                    cBookShop=snapshot.getValue(CBookShop.class);
                                    list.add(cBookShop);
                                    if (m==dataSnapshot.getChildrenCount())
                                    {
                                        progressBar.setVisibility(View.INVISIBLE);
                                        progressText.setVisibility(View.INVISIBLE);

                                        adapterUpcomingBookings = new AdapterUpcomingBookings(OwnerViewUpcomingBookings.this, list);
                                        recyclerView.setAdapter(adapterUpcomingBookings);
                                    }
                                }
                            }
                            else
                            {
                                progressBar.setVisibility(View.INVISIBLE);
                                progressText.setVisibility(View.INVISIBLE);
                                noBooking.setVisibility(View.VISIBLE);
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError)
                        {

                        }
                    });
                }
            }
        });

    }
}
