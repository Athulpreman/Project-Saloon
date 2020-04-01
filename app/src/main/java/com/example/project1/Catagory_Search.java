package com.example.project1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DownloadManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Catagory_Search extends AppCompatActivity
{
    TextView activityName;
    String acName,Name;
    DatabaseReference refee,refOwnerName,ref,ref1,ref2;
    RecyclerView recyclerView;
    ArrayList<OwnerAdd> list;
    ArrayList<Owner> listowner;
    ArrayList<String> shopList;
    Adapter_Search_Place adapter;
    Owner owner,owner1;
    String s;

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
        list=new ArrayList<OwnerAdd>();
        listowner=new ArrayList<Owner>();

        ref1=FirebaseDatabase.getInstance().getReference().child("ShopOwners");
        ref1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                for (DataSnapshot snapshot:dataSnapshot.getChildren())
                {
                    Log.d("aaaaaa","b");
                    owner=snapshot.getValue(Owner.class);
                    ref2=FirebaseDatabase.getInstance().getReference().child("ShopOwners").child(owner.ShopID).child("Activity");
                    Query query=ref2.orderByChild("activity").equalTo(acName);
                    query.addListenerForSingleValueEvent(new ValueEventListener()
                    {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                        {
                            int count=Integer.parseInt(dataSnapshot.getChildrenCount());
                            for (DataSnapshot snapshot1:dataSnapshot.getChildren())
                            {
                                Log.d("aaaaaa","c");
                                if (snapshot1.exists())
                                {
                                    listowner.add(owner);
                                    Log.d("aaaaaa","d");
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
                adapter = new Adapter_Search_Place(Catagory_Search.this, listowner);
                recyclerView.setAdapter(adapter);
                Log.d("aaaaaa","e");
                CToast c=new CToast();
                if (listowner.isEmpty())
                {

                    c.toast(getApplicationContext(),"empty",1);
                }else
                {
                    c.toast(getApplicationContext(),"not  empty",1);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        /*for (int i=0;i<listowner.size();i++)
        {
            Owner own=new Owner();
            own=listowner.get(i);
            String id=own.ShopID;
            ref=FirebaseDatabase.getInstance().getReference().child("ShopOwners");
            Query query=ref.orderByChild("activity").equalTo(acName);
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                {
                    for (DataSnapshot snapshot:dataSnapshot.getChildren())
                    {
                        Owner owner=new Owner();
                        owner=snapshot.getValue(Owner.class);
                        listowner.add(owner);
                        Log.d("aaaaaa","c");

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            Log.d("aaaaaa","d");

        }*/
    }
}

