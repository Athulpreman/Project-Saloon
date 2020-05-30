package com.example.project1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.FileObserver;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SearchEditShop_Admin extends AppCompatActivity
{
    DatabaseReference reference;
    ArrayList<Owner>list;
    RecyclerView recyclerView;
    AdapterAdminViewShop adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_edit_shop__admin);
        list=new ArrayList<Owner>();

        recyclerView=(RecyclerView)findViewById(R.id.rvadminViewShop);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        reference= FirebaseDatabase.getInstance().getReference().child("ShopOwners");
        reference.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if (dataSnapshot.exists())
                {
                    for (DataSnapshot snapshot:dataSnapshot.getChildren())
                    {
                        Owner owner=snapshot.getValue(Owner.class);
                        list.add(owner);
                    }
                    if (list.isEmpty())
                    {
                        Toast.makeText(SearchEditShop_Admin.this, "No shop registered", Toast.LENGTH_SHORT).show();
                    }
                    adapter = new AdapterAdminViewShop(SearchEditShop_Admin.this, list);
                    recyclerView.setAdapter(adapter);
                }
                else
                {
                    Toast.makeText(SearchEditShop_Admin.this, "No shop exists", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {

            }
        });

    }
}
