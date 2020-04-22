package com.example.project1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Catagory_Search extends AppCompatActivity
{
    TextView activityName;
    String acName,Name;
    DatabaseReference ref1,ref2,ref3;
    RecyclerView recyclerView;
    ArrayList<OwnerAdd> list;
    ArrayList<Owner> listowner;
    ArrayList<String> shopList;
    ArrayList<String> hasshopList;
    Adapter_Search_Place adapter;
    Owner owner,owner1;
    String s;
    int count,j,i,k;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catagory__search);

        recyclerView=(RecyclerView)findViewById(R.id.rvSearchResult);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        activityName=(TextView)findViewById(R.id.activityName);

        Intent intent=getIntent();
        acName=intent.getStringExtra("activity");

        activityName.setText(acName);
        owner1=new Owner();
        owner=new Owner();


        shopList=new ArrayList<String>();
        hasshopList=new ArrayList<String>();
        list=new ArrayList<OwnerAdd>();
        listowner=new ArrayList<Owner>();

        ref1=FirebaseDatabase.getInstance().getReference().child("ShopOwners");
        ref1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                i=0;
                count= (int) dataSnapshot.getChildrenCount();
                for (DataSnapshot snapshot:dataSnapshot.getChildren())
                {
                    if (dataSnapshot.exists())
                    {
                        owner=snapshot.getValue(Owner.class);
                        String id=owner.ShopID;
                        shopList.add(id);
                    }
                    i++;
                    if (count==i)
                    {
                       addOwner();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void addOwner()
    {
        j=0;
        for (final String s:shopList)
        {
            ref2=FirebaseDatabase.getInstance().getReference().child("ShopOwners").child(s).child("Activity");
            Query query=ref2.orderByChild("activity").equalTo(acName);
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                {
                    if (dataSnapshot.exists())
                    {
                        hasshopList.add(s);
                        j++;
                        if (j==shopList.size())
                        {
                            findOwner();
                        }
                    }
                    else
                    {
                        j++;
                        if (j==shopList.size())
                        {
                            findOwner();
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


        }
    }
    public void findOwner()
    {
        for (String s2:hasshopList)
        {
            k=0;
            ref3=FirebaseDatabase.getInstance().getReference().child("ShopOwners");
            Query query=ref3.orderByChild("shopID").equalTo(s2);
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                {
                   for (DataSnapshot snapshot:dataSnapshot.getChildren())
                   {
                       owner1=snapshot.getValue(Owner.class);
                       listowner.add(owner1);
                       k++;
                       if (k==hasshopList.size())
                       {
                           adapter = new Adapter_Search_Place(Catagory_Search.this, listowner);
                           recyclerView.setAdapter(adapter);
                       }
                   }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError)
                {
                }
            });
        }
    }
}

