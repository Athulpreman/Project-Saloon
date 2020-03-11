package com.example.project1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class CustomerSignedIn1 extends AppCompatActivity
{
    Button bhome,bsearch,bcatagory,bcart,baccount,logout;
    String Name;
    String sSearchItem,sSearchType;
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


            final TextView showAutoText;
            final AutoCompleteTextView ACTextView;
            DatabaseReference databaseReference;
            final ArrayList<String> ShopName=new ArrayList<String>();
            final ArrayList<String> ShopPlace=new ArrayList<String>();

            Button search;
            final Spinner SelectSearchingType;

            search=(Button)findViewById(R.id.searchShop);
            SelectSearchingType=(Spinner)findViewById(R.id.ItemSpinner);

            showAutoText=(TextView)findViewById(R.id.tv);
            ACTextView=(AutoCompleteTextView)findViewById(R.id.actv);

            databaseReference=FirebaseDatabase.getInstance().getReference().child("ShopOwners");
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                {
                    for (DataSnapshot snap:dataSnapshot.getChildren())
                    {
                        Owner own1=new Owner();
                        own1=snap.getValue(Owner.class);
                        ShopName.add(own1.ShopName);
                        ShopPlace.add(own1.place);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError)
                {
                    Toast.makeText(CustomerSignedIn1.this, "Error....!!!!", Toast.LENGTH_SHORT).show();
                }
            });
            ArrayAdapter<String> adapter=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,ShopName);
            ACTextView.setAdapter(adapter);

            SelectSearchingType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String s=SelectSearchingType.getSelectedItem().toString();
                    if (s.equals("Place"))
                    {
                        Log.d("aa","1st");
                        ArrayAdapter<String> adapter=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,ShopPlace);
                        ACTextView.setAdapter(adapter);
                    }
                    else if (s.equals("Shop"))
                    {
                        Log.d("aa","2md");
                        ArrayAdapter<String> adapter=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,ShopName);
                        ACTextView.setAdapter(adapter);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            search.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    sSearchType =SelectSearchingType.getSelectedItem().toString();
                    sSearchItem=ACTextView.getText().toString();

                    if (sSearchItem.isEmpty())
                    {
                        ACTextView.setError("Field is empty");
                        ACTextView.requestFocus();
                    }
                    else
                    {
                        if (sSearchType.equals("Shop"))
                        {
                            DatabaseReference databaseReference1;

                            databaseReference1=FirebaseDatabase.getInstance().getReference().child("ShopOwners");
                            databaseReference1.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot1)
                                {
                                    for (DataSnapshot snap:dataSnapshot1.getChildren())
                                    {
                                        Owner own=new Owner();
                                        own=snap.getValue(Owner.class);
                                        if (own.ShopName.equalsIgnoreCase(sSearchItem))
                                        {
                                            Intent intent=new Intent(getApplicationContext(),ShopDetails.class);
                                            intent.putExtra(own.ShopID,"ShopId");
                                            startActivity(intent);
                                        }
                                        else
                                        {
                                            Toast.makeText(CustomerSignedIn1.this, "No Shop Exist By That Name", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError)
                                {
                                    Toast.makeText(CustomerSignedIn1.this, "Error....!", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                        else
                        {
                            DatabaseReference refee;
                            final RecyclerView recyclerView;
                            final Adapter_Search_Place[] adapter = new Adapter_Search_Place[1];
                            final ArrayList<Owner> list;
                            final TextView tvResult;

                            tvResult=(TextView)findViewById(R.id.ShowResult);
                            recyclerView=(RecyclerView)findViewById(R.id.rvSearchShop);
                            recyclerView.setHasFixedSize(true);
                            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                            list=new ArrayList<Owner>();

                            refee= FirebaseDatabase.getInstance().getReference().child("ShopOwners");
                            refee.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                                {
                                    for (DataSnapshot studentDatasnapshot : dataSnapshot.getChildren())
                                    {
                                        Owner owner = studentDatasnapshot.getValue(Owner.class);
                                        if (owner.place.equalsIgnoreCase(sSearchItem))
                                        {
                                            list.add(owner);
                                        }
                                    }
                                    int size=list.size();
                                    if (size==0)
                                    {
                                        tvResult.setText("No Shope In The Given Place");
                                    }
                                    else
                                    {
                                        tvResult.setText(sSearchItem);
                                        adapter[0] = new Adapter_Search_Place(CustomerSignedIn1.this,list);
                                        recyclerView.setAdapter(adapter[0]);
                                    }

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError)
                                {
                                    Toast.makeText(getApplicationContext(),"something wnt wrong",Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    }
                }
            });
        }
        else if (a==3)
        {
            setContentView(R.layout.customer_catogary);
            this.setTitle("Catagory");

            bhome=(Button)findViewById(R.id.home);
            bsearch=(Button)findViewById(R.id.search);
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
