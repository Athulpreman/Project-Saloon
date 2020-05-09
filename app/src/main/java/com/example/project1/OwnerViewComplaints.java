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

public class OwnerViewComplaints extends AppCompatActivity
{
    DatabaseReference reference;
    String shopID;
    ArrayList<CComplaint>list;
    AdapterComplaint adapterComplaint;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_view_complaints);

        list=new ArrayList<CComplaint>();

        recyclerView=(RecyclerView)findViewById(R.id.OwnerviewComplaints);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        SharedPreferences sharedPreferences=getSharedPreferences("OwnerLogin",MODE_PRIVATE);
        shopID=sharedPreferences.getString("shopID",null);

        reference= FirebaseDatabase.getInstance().getReference().child("ShopOwners").child(shopID).child("Complaint");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if (!dataSnapshot.exists())
                {
                    CToast c=new CToast();
                    c.toast(getApplicationContext(),"No Complaints",0);
                }
                for (DataSnapshot snapshot:dataSnapshot.getChildren())
                {
                    CComplaint cComplaint=snapshot.getValue(CComplaint.class);
                    list.add(cComplaint);
                }
                adapterComplaint = new AdapterComplaint(OwnerViewComplaints.this, list);
                recyclerView.setAdapter(adapterComplaint);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {
            }
        });
    }
}
