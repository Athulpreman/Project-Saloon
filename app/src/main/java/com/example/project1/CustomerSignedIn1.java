package com.example.project1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CustomerSignedIn1 extends AppCompatActivity
{
    Button bhome,bsearch,bcatagory,bcart,baccount,logout;
    String Name;
    Toast backToast;
    long backpress;
    Owner owner;

    ArrayList<OwnerAdd> list;
    ArrayList<String> shopList;

    CgetOwner cgetOwner;

    int i=0;


    DatabaseReference refOwnerName,refee,refee1;
    RecyclerView recyclerView;
    AdapterCustomerHome adapter;


    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_signed_in1);
        this.setTitle("Home");


        viewPager=(ViewPager) findViewById(R.id.viewpager);
        ImageAdapter adapterimg=new ImageAdapter(this);
        viewPager.setAdapter(adapterimg);

        bhome=(Button)findViewById(R.id.home);
        bsearch=(Button)findViewById(R.id.search);
        bcatagory=(Button)findViewById(R.id.catagory);
        bcart=(Button)findViewById(R.id.cart);
        baccount=(Button)findViewById(R.id.account);

       /* bhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                decide(1);
            }
        });*/
        bsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                decide(2);
            }
        });
        bcatagory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                decide(3);
            }
        });
        bcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                decide(4);
            }
        });
        baccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                decide(5);
            }
        });

        cgetOwner=new CgetOwner();

        list=new ArrayList<OwnerAdd>();
        shopList=new ArrayList<String>();

        recyclerView=(RecyclerView)findViewById(R.id.rvCustomerHome);
        recyclerView.setLayoutManager(new GridLayoutManager(this,3));

        refOwnerName= FirebaseDatabase.getInstance().getReference().child("ShopOwners");
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
                            adapter = new AdapterCustomerHome(CustomerSignedIn1.this, list);
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


       /* Logout.setOnClickListener(new View.OnClickListener()
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
        });*/


    }
    public void decide(final int a)
    {
        if (a==1)
        {
            setContentView(R.layout.activity_customer_signed_in1);
            this.setTitle("Home");

            {
                final String[] Name = new String[1];
                Toast backToast;
                long backpress;
                final Owner[] owner = new Owner[1];

                final ArrayList<OwnerAdd> list;
                final ArrayList<String> shopList;

                CgetOwner cgetOwner;

                int i=0;

                ViewPager viewPager;

                DatabaseReference refOwnerName;
                DatabaseReference refee;
                final DatabaseReference[] refee1 = new DatabaseReference[1];
                final RecyclerView recyclerView;
                final AdapterCustomerHome[] adapter = new AdapterCustomerHome[1];


                cgetOwner=new CgetOwner();

                list=new ArrayList<OwnerAdd>();
                shopList=new ArrayList<String>();

                viewPager=(ViewPager) findViewById(R.id.viewpager);
                ImageAdapter adapterimg=new ImageAdapter(this);
                viewPager.setAdapter(adapterimg);

                recyclerView=(RecyclerView)findViewById(R.id.rvCustomerHome);
                recyclerView.setLayoutManager(new GridLayoutManager(this,3));

                refOwnerName= FirebaseDatabase.getInstance().getReference().child("ShopOwners");
                refOwnerName.addValueEventListener(new ValueEventListener()
                {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                    {
                        for (DataSnapshot snapOwnerName:dataSnapshot.getChildren())
                        {
                            owner[0] =new Owner();
                            owner[0] =snapOwnerName.getValue(Owner.class);
                            String Namee= owner[0].ShopID;
                            shopList.add(Namee);

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

                            Name[0] =shopList.get(j);
                            refee1[0] = FirebaseDatabase.getInstance().getReference().child("ShopOwners").child(Name[0]).child("Activity");

                            refee1[0].addValueEventListener(new ValueEventListener() {
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
                                    adapter[0] = new AdapterCustomerHome(CustomerSignedIn1.this, list);
                                    recyclerView.setAdapter(adapter[0]);
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

            }

            bhome=(Button)findViewById(R.id.home);
            bsearch=(Button)findViewById(R.id.search);
            bcatagory=(Button)findViewById(R.id.catagory);
            bcart=(Button)findViewById(R.id.cart);
            baccount=(Button)findViewById(R.id.account);

            bhome.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    if (a!=1)
                    {
                        decide(1);
                    }
                }
            });
            bsearch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    decide(2);
                }
            });
            bcatagory.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    decide(3);
                }
            });
            bcart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    decide(4);
                }
            });
            baccount.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    decide(5);
                }
            });
        }
        else if (a==2)
        {
            setContentView(R.layout.customer_search);
            this.setTitle("Search Shop");

            bhome=(Button)findViewById(R.id.home);
            bsearch=(Button)findViewById(R.id.search);
            bcatagory=(Button)findViewById(R.id.catagory);
            bcart=(Button)findViewById(R.id.cart);
            baccount=(Button)findViewById(R.id.account);

            bhome.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    decide(1);
                }
            });
            bsearch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    if (a!=2) {
                        decide(2);
                    }
                }
            });
            bcatagory.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    decide(3);
                }
            });
            bcart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    decide(4);
                }
            });
            baccount.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    decide(5);
                }
            });
        }
        else if (a==3)
        {
            setContentView(R.layout.customer_catogary);
            this.setTitle("Catagory");

            bhome=(Button)findViewById(R.id.home);
            bsearch=(Button)findViewById(R.id.search);
            bcatagory=(Button)findViewById(R.id.catagory);
            bcart=(Button)findViewById(R.id.cart);
            baccount=(Button)findViewById(R.id.account);

            bhome.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    decide(1);
                }
            });
            bsearch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    decide(2);
                }
            });
            bcatagory.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    if (a!=3) {
                        decide(3);
                    }
                }
            });
            bcart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    decide(4);
                }
            });
            baccount.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    decide(5);
                }
            });
        }
        else if (a==4)
        {
            setContentView(R.layout.customer_cart);
            this.setTitle("My Cart");

            bhome=(Button)findViewById(R.id.home);
            bsearch=(Button)findViewById(R.id.search);
            bcatagory=(Button)findViewById(R.id.catagory);
            bcart=(Button)findViewById(R.id.cart);
            baccount=(Button)findViewById(R.id.account);

            bhome.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    decide(1);
                }
            });
            bsearch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    decide(2);
                }
            });
            bcatagory.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    decide(3);
                }
            });
            bcart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    if (a!=4) {
                        decide(4);
                    }
                }
            });
            baccount.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    decide(5);
                }
            });
        }
        else if (a==5)
        {


            setContentView(R.layout.customer_account);
            this.setTitle("My Account");

            logout=(Button)findViewById(R.id.logoutcustomer);
            bhome=(Button)findViewById(R.id.home);
            bsearch=(Button)findViewById(R.id.search);
            bcatagory=(Button)findViewById(R.id.catagory);
            bcart=(Button)findViewById(R.id.cart);
            baccount=(Button)findViewById(R.id.account);


            bhome.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    decide(1);
                }
            });
            bsearch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    decide(2);
                }
            });
            bcatagory.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    decide(3);
                }
            });
            bcart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    decide(4);
                }
            });
            baccount.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    if (a!=5) {
                        decide(5);
                    }
                }
            });
            logout.setOnClickListener(new View.OnClickListener()
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
    }

    @Override
    public void onBackPressed() {
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
