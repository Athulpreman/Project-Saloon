package com.example.project1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CustomerSignedIn extends AppCompatActivity
{
    String Name;
    Toast backToast;
    long backpress;
    Button Logout,Load;
    Owner owner;

    public String AShopID[];
    ArrayList<OwnerAdd> list;
    ArrayList<String> shopList;

    CgetOwner cgetOwner;

    int i=0;


    DatabaseReference refOwnerName,refee,refee1;
    RecyclerView recyclerView;
    AdapterCustomerHome adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_signed_in);

        cgetOwner=new CgetOwner();

        list=new ArrayList<OwnerAdd>();
        shopList=new ArrayList<String>();


        Logout=(Button)findViewById(R.id.CustomerSignedInLogoutButton);

        recyclerView=(RecyclerView)findViewById(R.id.rvCustomerHome);
        recyclerView.setLayoutManager(new GridLayoutManager(this,3));

        refOwnerName=FirebaseDatabase.getInstance().getReference().child("ShopOwners");
        refOwnerName.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                for (DataSnapshot snapOwnerName:dataSnapshot.getChildren())
                {
                    owner=new Owner();
                    owner=snapOwnerName.getValue(Owner.class);
                    String Namee=owner.ShopID;
                    shopList.add(Namee);
                    Log.d("ShopId ",Namee);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {
                Toast.makeText(getApplicationContext(), "Error....!", Toast.LENGTH_SHORT).show();

            }
        });
        refee=FirebaseDatabase.getInstance().getReference().child("ShopOwners");
        refee.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                for (int j=0;j<shopList.size();j++)
                {

                    Name=shopList.get(j);
                    Log.d("foree",Name);
                    Toast.makeText(getApplicationContext(), Name, Toast.LENGTH_SHORT).show();
                    refee1 = FirebaseDatabase.getInstance().getReference().child("ShopOwners").child(Name).child("Activity");

                    refee1.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot datasnapshot1 : dataSnapshot.getChildren())
                            {
                                if (dataSnapshot.exists())
                                {
                                    OwnerAdd ownerAdd = datasnapshot1.getValue(OwnerAdd.class);
                                    list.add(ownerAdd);
                                }
                            }
                            adapter = new AdapterCustomerHome(CustomerSignedIn.this, list);
                            recyclerView.setAdapter(adapter);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Toast.makeText(getApplicationContext(), "something wnt wrong", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        Logout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                SharedPreferences.Editor editor=getSharedPreferences("UserLogin",MODE_PRIVATE).edit();
                editor.clear();
                editor.commit();

                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed()
    {
        if (backpress+2000>System.currentTimeMillis())
        {
            backToast.cancel();
            moveTaskToBack(true);
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        }
        else
        {
            backToast=Toast.makeText(getApplicationContext(), "Press again to exit", Toast.LENGTH_SHORT);
            backToast.show();
        }
        backpress=System.currentTimeMillis();

    }
}
