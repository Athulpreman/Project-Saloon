package com.example.project1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

public class Catagory_Search extends AppCompatActivity
{
    TextView activityName;
    String acName;
    DatabaseReference refee,databaseReference,ref;
    RecyclerView recyclerView;
    ArrayList<Owner> list;
    ArrayList<String> sShopID;
    ArrayList<String> shoplist;
    Adapter_Search_Place adapter11;
    Owner owner111,owner1;
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
        Toast.makeText(this, acName, Toast.LENGTH_SHORT).show();
        activityName.setText(acName);
        owner111=new Owner();
        owner1=new Owner();

        sShopID=new ArrayList<String>();
        shoplist=new ArrayList<String>();
        list=new ArrayList<Owner>();

        databaseReference= FirebaseDatabase.getInstance().getReference().child("ShopOwners");
        databaseReference.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                for (DataSnapshot snapOwnerName:dataSnapshot.getChildren())
                {
                    owner111=new Owner();
                    owner111=snapOwnerName.getValue(Owner.class);
                    String Namee=owner111.ShopID;
                    sShopID.add(Namee);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {
                Toast.makeText(getApplicationContext(), "Error....!", Toast.LENGTH_SHORT).show();

            }
        });
        if (sShopID.isEmpty())
        {
            Toast.makeText(this, "Empty list", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(this, "not empty", Toast.LENGTH_SHORT).show();
        }

        for (String o:sShopID)
        {
            refee= FirebaseDatabase.getInstance().getReference().child("ShopOwners").child(o).child("Activity");
            refee.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                {
                    for (DataSnapshot studentDatasnapshot : dataSnapshot.getChildren())
                    {
                        OwnerAdd owner = studentDatasnapshot.getValue(OwnerAdd.class);
                        if (owner.Activity.equalsIgnoreCase(acName))
                        {
                            shoplist.add(owner.ShopID);
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError)
                {
                    Toast.makeText(getApplicationContext(),"something wnt wrong",Toast.LENGTH_LONG).show();
                }
            });
            if (shoplist.isEmpty())
            {
                Toast.makeText(this, "No shop has this service", Toast.LENGTH_SHORT).show();
            }
            else
            {
                for (String ap:shoplist)
                {
                    ref=FirebaseDatabase.getInstance().getReference().child("ShopOwners");
                    Query query=ref.orderByChild("shopID").equalTo(ap);
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                        {
                            Owner own=new Owner();
                            own=dataSnapshot.getValue(Owner.class);
                            list.add(own);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                    adapter11= new Adapter_Search_Place(Catagory_Search.this,list);
                    recyclerView.setAdapter(adapter11);
                }
            }
        }

    }
}
