package com.example.project1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class Account_Booking_History extends AppCompatActivity
{
    DatabaseReference refeecart;
    RecyclerView recyclerViewcart;
    AdapterCart adaptercart;
    ArrayList<CBookShop> listcart;
    CBookShop cBookShop;
    Customer customer;
    String MobNoo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account__booking__history);

        this.setTitle("My Cart");

        recyclerViewcart=(RecyclerView)findViewById(R.id.rrvCart);
        recyclerViewcart.setHasFixedSize(true);
        recyclerViewcart.setLayoutManager(new LinearLayoutManager(this));

        final Calendar c = Calendar.getInstance();
        final SimpleDateFormat df = new SimpleDateFormat("yyyy,MM,dd");
        final String formattedDate = df.format(c.getTime());
        cBookShop=new CBookShop();
        listcart=new ArrayList<CBookShop>();

        SharedPreferences sharedPreferences=getSharedPreferences("UserLogin",MODE_PRIVATE);
        MobNoo=sharedPreferences.getString("MobNo",null);

        listcart.clear();
        refeecart= FirebaseDatabase.getInstance().getReference().child("Customer").child(MobNoo).child("Booking");
        refeecart.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                for (DataSnapshot snapshot:dataSnapshot.getChildren())
                {
                    cBookShop=new CBookShop();
                    cBookShop=snapshot.getValue(CBookShop.class);
                    if (cBookShop.Date.compareTo(formattedDate)<0)
                    {
                        listcart.add(cBookShop);
                    }
                }
                if (listcart.isEmpty())
                {
                    Toast.makeText(Account_Booking_History.this, "empty lis", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(Account_Booking_History.this, "not empty", Toast.LENGTH_SHORT).show();
                }
                adaptercart = new AdapterCart(Account_Booking_History.this, listcart);
                recyclerViewcart.setAdapter(adaptercart);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
