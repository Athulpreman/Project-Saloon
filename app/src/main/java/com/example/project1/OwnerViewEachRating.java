package com.example.project1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class OwnerViewEachRating extends AppCompatActivity
{
    String shopID,activity;
    DatabaseReference reference;
    ArrayList<CRate>list;
    AdapterViewEachRating adapterViewEachRating;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_view_each_rating);

        recyclerView=(RecyclerView)findViewById(R.id.recycleallrating);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Intent intent=getIntent();
        shopID=intent.getStringExtra("shopID");
        activity=intent.getStringExtra("activity");

        list=new ArrayList<CRate>();

        reference= FirebaseDatabase.getInstance().getReference().child("ShopOwners").child(shopID).child("Activity").child(activity).child("Rating");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                for (DataSnapshot snapshot:dataSnapshot.getChildren())
                {
                    CRate cRate=snapshot.getValue(CRate.class);
                    list.add(cRate);
                }
                adapterViewEachRating = new AdapterViewEachRating(OwnerViewEachRating.this, list);
                recyclerView.setAdapter(adapterViewEachRating);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {
            }
        });

    }
}
