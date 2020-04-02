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
    DatabaseReference refee,refOwnerName,ref,ref1,ref2,ref3;
    RecyclerView recyclerView;
    ArrayList<OwnerAdd> list;
    ArrayList<Owner> listowner;
    ArrayList<String> shopList;
    ArrayList<String> hasshopList;
    Adapter_Search_Place adapter;
    Owner owner,owner1;
    String s;
    int count,count2,j,i,k;

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
                        Log.d("aaaaaa","b");
                        owner=snapshot.getValue(Owner.class);
                        String id=owner.ShopID;
                        shopList.add(id);
                    }
                    i++;
                    if (count==i)
                    {
                        Log.d("aaaaaaashoplstSize", String.valueOf(shopList.size()));
                       addOwner();
                    }
                }

                CToast c=new CToast();
                if (listowner.isEmpty())
                {

                    c.toast(getApplicationContext(),String.valueOf(count),1);
                }else
                {
                    c.toast(getApplicationContext(),"not  empty",1);
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
            Log.d("aaaaaassss",s);
            ref2=FirebaseDatabase.getInstance().getReference().child("ShopOwners").child(s).child("Activity");
            Query query=ref2.orderByChild("activity").equalTo(acName);
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                {
                    if (dataSnapshot.exists())
                    {
                        Log.d("aaaaaa","c");
                        hasshopList.add(s);
                        j++;
                        Log.d("aaaaaa  ShoSice  j", String.valueOf(hasshopList.size()));
                        Log.d("aaaaaa  ShoSice  i", String.valueOf(shopList.size()));
                        Log.d("aaaaaa  ShoSice  j", String.valueOf(j));
                        if (j==shopList.size())
                        {
                            Log.d("aaaaaaaHasshoplstSize", String.valueOf(hasshopList.size()));
                            findOwner();
                        }
                    }
                    else
                    {
                        j++;
                        if (j==shopList.size())
                        {
                            Log.d("aaaaaaaHasshoplstSize", String.valueOf(hasshopList.size()));
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
            Log.d("aaaaaa","d");
            ref3=FirebaseDatabase.getInstance().getReference().child("ShopOwners");
            Query query=ref3.orderByChild("shopID").equalTo(s2);
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                {
                   for (DataSnapshot snapshot:dataSnapshot.getChildren())
                   {
                       Log.d("aaaaaa","e");
                       owner1=snapshot.getValue(Owner.class);
                       listowner.add(owner1);
                       k++;
                       if (k==hasshopList.size())
                       {
                           Log.d("aaaaaa","f");
                           Log.d("aaaaaaalistOwner", String.valueOf(listowner.size()));
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

