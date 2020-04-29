package com.example.project1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class OwnerManageServices extends AppCompatActivity
{
    DatabaseReference reference;
    String shopID;
    ArrayList<OwnerAdd>list;
    RecyclerView recyclerView;
    AdapterManageService adapterManageService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_manage_services);

        SharedPreferences sharedPreferences=getSharedPreferences("OwnerLogin",MODE_PRIVATE);
        shopID=sharedPreferences.getString("shopID",null);

        recyclerView=(RecyclerView)findViewById(R.id.rvManageServices);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list=new ArrayList<OwnerAdd>();

        reference= FirebaseDatabase.getInstance().getReference().child("ShopOwners").child(shopID).child("Activity");
        reference.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                for (DataSnapshot snapshot:dataSnapshot.getChildren())
                {
                    OwnerAdd ownerAdd=new OwnerAdd();
                    ownerAdd=snapshot.getValue(OwnerAdd.class);
                    list.add(ownerAdd);
                }
                adapterManageService = new AdapterManageService(OwnerManageServices.this, list);
                recyclerView.setAdapter(adapterManageService);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {
            }
        });

    }
}
