package com.example.project1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class CustomerSignedIn1 extends AppCompatActivity
{
    Button bHome, bSearch, bCatagory,bcart,baccount,logout;
    String Name,MobNoo;
    String sSearchItem,sSearchType;
    Toast backToast;
    long backPress;
    Owner owner,owner2;
    DatabaseReference refee1;
    AdapterCustomerHome adapter;
    Adapter_Search_Place adapter11;
    String s="";
    int l,m,n;
    ArrayList<Owner>listimg;

    RecyclerView recyclerView;

    AlertDialog.Builder builder;

    ArrayList<OwnerAdd> list;
    ArrayList<String> shopList;
    ArrayList<String> DateList;
    ArrayList<CBookShop> DateList2;

    CgetOwner cgetOwner;

    int i=0;

    String shopID;

    DatabaseReference refOwnerName,refee,refer;


    //cart
    DatabaseReference refeecart;
    RecyclerView recyclerViewcart;
    AdapterCart adaptercart;
    ArrayList<CBookShop> listcart;
    CBookShop cBookShop;
    Customer customer;

    //catagory
    TabHost host;
    Button hairWash,hairCut,scalpeTreatement,hairLossServises,beardShapping,braiding,straightening;
    Button headMassage,nailTreatement,detoxifyingMudWrap,stressRelievingBackTreatment,bodyGlowWithBodyMasque;
    Button facialHairBleaching,LEDLightTherapy,europianFacial,oxygenFacial,chemicalPeel,fruitFacial;

    //account
    ImageView proPic;
    TextView TproName,TphoneNo;
    String sProName,sPhoneNo,sProPic="";
    Button Notification,MyBookingHistory, ManageProfile,Feedback,Help,Favourite;
    DatabaseReference propicReference,refe;
    Intent i1=new Intent(Intent.ACTION_GET_CONTENT);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_signed_in1);
        this.setTitle("Home");

        Intent intent=getIntent();
        s=intent.getStringExtra("home");
        /*if (s!=null||!s.equals("")||!s.equals(null)||s!="")
        {
            if (s.equals("4"))
            {
                decide(4);
            }
        }*/

        SharedPreferences sharedPreferences=getSharedPreferences("UserLogin",MODE_PRIVATE);
        MobNoo=sharedPreferences.getString("MobNo",null);

        bHome =(Button)findViewById(R.id.home);
        bSearch =(Button)findViewById(R.id.search);
        bCatagory =(Button)findViewById(R.id.catagory);
        bcart=(Button)findViewById(R.id.cart);
        baccount=(Button)findViewById(R.id.account);

        bSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                decide(2);
            }
        });
        bCatagory.setOnClickListener(new View.OnClickListener() {
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
        listimg=new ArrayList<Owner>();
        shopList=new ArrayList<String>();
        DateList=new ArrayList<String>();
        DateList2=new ArrayList<CBookShop>();



        refer=FirebaseDatabase.getInstance().getReference().child("ShopOwners");
        refer.addValueEventListener(new ValueEventListener()
        {
            int k=0,m=0;
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                for (DataSnapshot snapshot:dataSnapshot.getChildren())
                {
                    k++;
                    if (k<4)
                    {
                        Owner owner=snapshot.getValue(Owner.class);
                        listimg.add(owner);
                    }
                    else
                    {
                        Log.d("sssss","aa1");
                        break;
                    }
                    if (k==dataSnapshot.getChildrenCount())
                    {
                        Log.d("sssss","aa2");
                        break;
                    }
                    Log.d("sssss","aa");
                }
                Log.d("sssss","bb");
                slider();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {
            }
        });



        recyclerView=(RecyclerView)findViewById(R.id.rvCustomerHome);
        recyclerView.setLayoutManager(new GridLayoutManager(this,3));

        shopList.clear();
        refOwnerName= FirebaseDatabase.getInstance().getReference().child("ShopOwners");
        refOwnerName.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                l=0;
                shopList.clear();
                for (DataSnapshot snapOwnerName:dataSnapshot.getChildren())
                {
                    l++;
                    owner=new Owner();
                    owner=snapOwnerName.getValue(Owner.class);
                    String Namee=owner.ShopID;
                    shopList.add(Namee);
                    if (l==dataSnapshot.getChildrenCount())
                    {
                        shopmame();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {
                Toast.makeText(getApplicationContext(), "Error....!", Toast.LENGTH_SHORT).show();

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
                String Name;
                Toast backToast;
                long backpress;


                final ArrayList<OwnerAdd> list;
                final ArrayList<String> shopList;

                CgetOwner cgetOwner;

                int i=0;

                cgetOwner=new CgetOwner();

                list=new ArrayList<OwnerAdd>();
                shopList=new ArrayList<String>();

                refer=FirebaseDatabase.getInstance().getReference().child("ShopOwners");
                refer.addValueEventListener(new ValueEventListener()
                {
                    int k=0;
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                    {
                        listimg.clear();
                        for (DataSnapshot snapshot:dataSnapshot.getChildren())
                        {
                            k++;
                            if (k<4)
                            {
                                Owner owner=snapshot.getValue(Owner.class);
                                listimg.add(owner);
                            }
                            else
                            {
                                slider();
                                break;
                            }
                            if (k==dataSnapshot.getChildrenCount())
                            {
                                slider();
                                break;
                            }
                        }
                        slider();
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError)
                    {
                    }
                });

                recyclerView=(RecyclerView)findViewById(R.id.rvCustomerHome);
                recyclerView.setLayoutManager(new GridLayoutManager(this,3));

                shopList.clear();
                refOwnerName= FirebaseDatabase.getInstance().getReference().child("ShopOwners");
                refOwnerName.addValueEventListener(new ValueEventListener()
                {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                    {
                        shopList.clear();
                        for (DataSnapshot snapOwnerName:dataSnapshot.getChildren())
                        {
                            owner2 = new Owner();
                            owner2 =new Owner();
                            owner2 =snapOwnerName.getValue(Owner.class);
                            String Namee= owner2.ShopID;
                            shopList.add(Namee);
                        }
                        shopmame();
                        Log.d("dddddd from","1");
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError)
                    {
                        Toast.makeText(getApplicationContext(), "Error....!", Toast.LENGTH_SHORT).show();

                    }
                });
            }
            bHome =(Button)findViewById(R.id.home);
            bSearch =(Button)findViewById(R.id.search);
            bCatagory =(Button)findViewById(R.id.catagory);
            bcart=(Button)findViewById(R.id.cart);
            baccount=(Button)findViewById(R.id.account);

            bHome.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    if (a!=1)
                    {
                        decide(1);
                    }
                }
            });
            bSearch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    decide(2);
                }
            });
            bCatagory.setOnClickListener(new View.OnClickListener() {
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

            bHome =(Button)findViewById(R.id.home);
            bSearch =(Button)findViewById(R.id.search);
            bCatagory =(Button)findViewById(R.id.catagory);
            bcart=(Button)findViewById(R.id.cart);
            baccount=(Button)findViewById(R.id.account);

            bHome.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    decide(1);
                }
            });
            bCatagory.setOnClickListener(new View.OnClickListener() {
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
                    ShopName.clear();
                    ShopPlace.clear();
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
                        ArrayAdapter<String> adapter=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,ShopPlace);
                        ACTextView.setAdapter(adapter);
                    }
                    else if (s.equals("Shop"))
                    {
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

                    SharedPreferences.Editor editor=getSharedPreferences("Book",MODE_PRIVATE).edit();
                    editor.clear();
                    editor.commit();

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
                                            shopID=own.ShopID;

                                            Toast.makeText(CustomerSignedIn1.this, shopID, Toast.LENGTH_SHORT).show();
                                            Intent intent=new Intent(getApplicationContext(),Booking_shop_Customer_1st.class);
                                            intent.putExtra("shopID",shopID);
                                            intent.putExtra("price","");
                                            intent.putExtra("activity","");
                                            startActivity(intent);
                                            break;
                                        }
                                        else
                                        {
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
                            final ArrayList<Owner> list1;
                            final TextView tvResult;

                            tvResult=(TextView)findViewById(R.id.ShowResult);
                            recyclerView=(RecyclerView)findViewById(R.id.rvSearchShop);
                            recyclerView.setHasFixedSize(true);
                            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                            list1=new ArrayList<Owner>();

                            refee= FirebaseDatabase.getInstance().getReference().child("ShopOwners");
                            refee.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                                {
                                    list1.clear();
                                    for (DataSnapshot studentDatasnapshot : dataSnapshot.getChildren())
                                    {
                                        if (studentDatasnapshot.exists())
                                        {
                                            Owner owner = studentDatasnapshot.getValue(Owner.class);
                                            if (owner.place.equalsIgnoreCase(sSearchItem))
                                            {
                                                list1.add(owner);
                                            }
                                        }
                                    }
                                    if (list1.isEmpty())
                                    {
                                        tvResult.setText("No Shope In The Given Place");
                                    }
                                    else
                                    {
                                        tvResult.setText(sSearchItem);
                                        adapter11= new Adapter_Search_Place(CustomerSignedIn1.this,list1);
                                        recyclerView.setAdapter(adapter11);
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

            bHome =(Button)findViewById(R.id.home);
            bSearch =(Button)findViewById(R.id.search);
            bcart=(Button)findViewById(R.id.cart);
            baccount=(Button)findViewById(R.id.account);

            bHome.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    decide(1);
                }
            });
            bSearch.setOnClickListener(new View.OnClickListener() {
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

            host=(TabHost)findViewById(R.id.tabhost);
            host.setup();

            //tab1
            TabHost.TabSpec spec=host.newTabSpec("Tab One");
            spec.setContent(R.id.Hair_Mustache);
            spec.setIndicator("Hair and Mustache");
            host.addTab(spec);

            //tab2
            spec=host.newTabSpec("Tab Two");
            spec.setContent(R.id.Facial);
            spec.setIndicator("Facial");
            host.addTab(spec);

            //tab3
            spec=host.newTabSpec("Tab Three");
            spec.setContent(R.id.Body_Care);
            spec.setIndicator("Body Care");
            host.addTab(spec);

            //All Buttons

            hairWash=(Button)findViewById(R.id.hairWash);
            hairLossServises=(Button)findViewById(R.id.hairLossServises);
            beardShapping=(Button)findViewById(R.id.beardShapping);
            braiding=(Button)findViewById(R.id.braiding);
            straightening=(Button)findViewById(R.id.straightening);
            hairCut=(Button)findViewById(R.id.hairCut);
            scalpeTreatement=(Button)findViewById(R.id.scalpeTreatement);

            headMassage=(Button)findViewById(R.id.headMassage);
            nailTreatement=(Button)findViewById(R.id.nailTreatement);
            detoxifyingMudWrap=(Button)findViewById(R.id.detoxifyingMudWrap);
            stressRelievingBackTreatment=(Button)findViewById(R.id.stressRelievingBackTreatment);
            bodyGlowWithBodyMasque=(Button)findViewById(R.id.bodyGlowWithBodyMasque);

            facialHairBleaching=(Button)findViewById(R.id.facialHairBleaching);
            LEDLightTherapy=(Button)findViewById(R.id.LEDLightTherapy);
            europianFacial=(Button)findViewById(R.id.europianFacial);
            oxygenFacial=(Button)findViewById(R.id.oxygenFacial);
            chemicalPeel=(Button)findViewById(R.id.chemicalPeel);
            fruitFacial=(Button)findViewById(R.id.fruitFacial);



            hairWash.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    Intent intent=new Intent(getApplicationContext(),Catagory_Search.class);
                    intent.putExtra("activity","Hair Wash");
                    startActivity(intent);
                }
            });
            hairLossServises.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    Intent intent=new Intent(getApplicationContext(),Catagory_Search.class);
                    intent.putExtra("activity","Hair Loss Servises");
                    startActivity(intent);
                }
            });
            beardShapping.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(getApplicationContext(),Catagory_Search.class);
                    intent.putExtra("activity","Beard Shapping");
                    startActivity(intent);

                }
            });
            braiding.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(getApplicationContext(),Catagory_Search.class);
                    intent.putExtra("activity","Braiding");
                    startActivity(intent);

                }
            });
            hairCut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(getApplicationContext(),Catagory_Search.class);
                    intent.putExtra("activity","Hair Cut");
                    startActivity(intent);

                }
            });
            stressRelievingBackTreatment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(getApplicationContext(),Catagory_Search.class);
                    intent.putExtra("activity","Stress Relieving Back Treatment");
                    startActivity(intent);

                }
            });
            scalpeTreatement.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(getApplicationContext(),Catagory_Search.class);
                    intent.putExtra("activity","Scalpe Treatement");
                    startActivity(intent);

                }
            });
            headMassage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(getApplicationContext(),Catagory_Search.class);
                    intent.putExtra("activity","Head Massage");
                    startActivity(intent);

                }
            });
            nailTreatement.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(getApplicationContext(),Catagory_Search.class);
                    intent.putExtra("activity","Nail Treatement");
                    startActivity(intent);

                }
            });
            detoxifyingMudWrap.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(getApplicationContext(),Catagory_Search.class);
                    intent.putExtra("activity","Detoxifying Mud Wrap");
                    startActivity(intent);

                }
            });
            stressRelievingBackTreatment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(getApplicationContext(),Catagory_Search.class);
                    intent.putExtra("activity","Stress Relieving Back Treatment");
                    startActivity(intent);

                }
            });
            bodyGlowWithBodyMasque.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(getApplicationContext(),Catagory_Search.class);
                    intent.putExtra("activity","Body Glow With Body Masque");
                    startActivity(intent);

                }
            });
            facialHairBleaching.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(getApplicationContext(),Catagory_Search.class);
                    intent.putExtra("activity","Facial Hair Bleaching");
                    startActivity(intent);

                }
            });
            LEDLightTherapy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(getApplicationContext(),Catagory_Search.class);
                    intent.putExtra("activity","LED Light Therapy");
                    startActivity(intent);

                }
            });
            europianFacial.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(getApplicationContext(),Catagory_Search.class);
                    intent.putExtra("activity","Europian Facial");
                    startActivity(intent);

                }
            });
            oxygenFacial.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(getApplicationContext(),Catagory_Search.class);
                    intent.putExtra("activity","Oxygen Facial");
                    startActivity(intent);

                }
            });
            chemicalPeel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(getApplicationContext(),Catagory_Search.class);
                    intent.putExtra("activity","Chemical Peel");
                    startActivity(intent);

                }
            });
            fruitFacial.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(getApplicationContext(),Catagory_Search.class);
                    intent.putExtra("activity","Fruit Facial");
                    startActivity(intent);

                }
            });
            straightening.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    Intent intent=new Intent(getApplicationContext(),Catagory_Search.class);
                    intent.putExtra("activity","Straightening");
                    startActivity(intent);
                }
            });



        }
        else if (a==4)
        {
            setContentView(R.layout.customer_cart);
            this.setTitle("My Cart");

            bHome =(Button)findViewById(R.id.home);
            bSearch =(Button)findViewById(R.id.search);
            bCatagory =(Button)findViewById(R.id.catagory);
            bcart=(Button)findViewById(R.id.cart);
            baccount=(Button)findViewById(R.id.account);

            bHome.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    decide(1);
                }
            });
            bSearch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    decide(2);
                }
            });
            bCatagory.setOnClickListener(new View.OnClickListener() {
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

                }
            });
            baccount.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    decide(5);
                }
            });

            recyclerViewcart=(RecyclerView)findViewById(R.id.rvCart);
            final Calendar c = Calendar.getInstance();
            final SimpleDateFormat df = new SimpleDateFormat("yyyy,MM,dd");
            final String formattedDate = df.format(c.getTime());
            cBookShop=new CBookShop();

            recyclerView=(RecyclerView)findViewById(R.id.rvCart);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            listcart=new ArrayList<CBookShop>();

            DateList2.clear();
            m=Integer.parseInt(formattedDate.replaceAll("[\\D]",""));
            refeecart=FirebaseDatabase.getInstance().getReference().child("Customer").child(MobNoo).child("Booking");
            refeecart.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                {
                    for (DataSnapshot snapshot:dataSnapshot.getChildren())
                    {
                        cBookShop=new CBookShop();
                        cBookShop=snapshot.getValue(CBookShop.class);
                        n=Integer.parseInt(cBookShop.getDate().replaceAll("[\\D]",""));
                        if (cBookShop.statusBit.equals("0")&&m<=n)
                        {
                            DateList2.add(cBookShop);
                        }
                    }
                    if (DateList2.isEmpty())
                    {
                        Toast.makeText(CustomerSignedIn1.this, "", Toast.LENGTH_SHORT).show();
                    }
                    adaptercart = new AdapterCart(CustomerSignedIn1.this, DateList2);
                    recyclerViewcart.setAdapter(adaptercart);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        else if (a==5)
        {
            setContentView(R.layout.customer_account);
            this.setTitle("My Account");

            logout=(Button)findViewById(R.id.logoutcustomer);
            bHome =(Button)findViewById(R.id.home);
            bSearch =(Button)findViewById(R.id.search);
            bCatagory =(Button)findViewById(R.id.catagory);
            bcart=(Button)findViewById(R.id.cart);
            baccount=(Button)findViewById(R.id.account);


            bHome.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    decide(1);
                }
            });
            bSearch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    decide(2);
                }
            });
            bCatagory.setOnClickListener(new View.OnClickListener() {
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


            Notification=(Button)findViewById(R.id.notification);
            MyBookingHistory=(Button)findViewById(R.id.bookingHistory);
            ManageProfile =(Button)findViewById(R.id.manageAdress);
            Feedback=(Button)findViewById(R.id.feedback);
            Help=(Button)findViewById(R.id.help);
            Favourite=(Button)findViewById(R.id.favouriteList);
            proPic=(ImageView)findViewById(R.id.proPic);
            TproName=(TextView)findViewById(R.id.proName);
            TphoneNo=(TextView)findViewById(R.id.proPhone);

            customer=new Customer();
            refe=FirebaseDatabase.getInstance().getReference().child("Customer");
            Query query=refe.orderByChild("mobileNum").equalTo(MobNoo);
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapee:dataSnapshot.getChildren())
                    {
                        customer=snapee.getValue(Customer.class);
                        TproName.setText(customer.name.toUpperCase());
                        TphoneNo.setText(customer.mobileNum);
                        if (!customer.profilePic.equals("0000000"))
                        {
                            Picasso.get().load(customer.profilePic).into(proPic);
                        }

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            proPic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    i1.setType("image/*");
                    startActivityForResult(i1,1);

                }

            });

            Notification.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Intent i1=new Intent(CustomerSignedIn1.this,Account_Notification.class);
                    startActivity(i1);
                }
            });
            MyBookingHistory.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Intent i1=new Intent(CustomerSignedIn1.this,Account_Booking_History.class);
                    startActivity(i1);
                }
            });
            ManageProfile.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Intent i1=new Intent(CustomerSignedIn1.this,Account_Manage_Profile.class);
                    startActivity(i1);
                }
            });
            Feedback.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Intent i1=new Intent(CustomerSignedIn1.this,Account_Feedback.class);
                    startActivity(i1);
                }
            });
            Help.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Intent i1=new Intent(CustomerSignedIn1.this,Account_Help.class);
                    startActivity(i1);
                }
            });
            Favourite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    Intent intent=new Intent(getApplicationContext(),Account_Favourite.class);
                    startActivity(intent);
                }
            });

            builder = new AlertDialog.Builder(this);
            logout.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {

                    builder.setMessage("Are you sure want to LOGOUT ?") .setTitle("Logout");

                    //Setting message manually and performing action on button click
                    builder.setMessage("Are you sure want to LOGOUT ?")
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id)
                                {



                                    SharedPreferences.Editor editor=getSharedPreferences("UserLogin",MODE_PRIVATE).edit();
                                    editor.clear();
                                    editor.commit();
                                    Toast.makeText(getApplicationContext(),"Logged Out", Toast.LENGTH_SHORT).show();

                                    Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                                    startActivity(intent);
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id)
                                {
                                    //  Action for 'NO' Button
                                    dialog.cancel();
                                }
                            });

                    //Creating dialog box
                    AlertDialog alert = builder.create();
                    //Setting the title manually
                    alert.setTitle("LOGOUT");
                    alert.show();
                }
            });
        }
    }


    @Override
    public void onBackPressed() {
        if (backPress +2000>System.currentTimeMillis())
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
        backPress =System.currentTimeMillis();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                Uri fileuri = data.getData();

                StorageReference folder = FirebaseStorage.getInstance().getReference().child("ShopImage");

                String timestamp = String.valueOf(System.currentTimeMillis());

                final StorageReference filename = folder.child(timestamp + fileuri.getLastPathSegment());

                filename.putFile(fileuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        filename.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri)
                            {
                                sProPic = String.valueOf(uri);
                                Picasso.get().load(sProPic).into(proPic);
                                if (!sProPic.equals(""))
                                {
                                    propicReference=FirebaseDatabase.getInstance().getReference().child("Customer");
                                    Query query1 = propicReference.orderByChild("profilePic");
                                    query1.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            for (DataSnapshot snap : dataSnapshot.getChildren())
                                            {
                                                Toast.makeText(CustomerSignedIn1.this, "Profile pic updated sucesfully", Toast.LENGTH_SHORT).show();
                                                snap.getRef().child("profilePic").setValue(sProPic);
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {
                                            Toast.makeText(CustomerSignedIn1.this, "DatabaseError......!", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                                else {
                                    Toast.makeText(CustomerSignedIn1.this, "Elseeeeeeeeee", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                    }
                });
            }
        }
    }
    public void shopmame()
    {

        Log.d("dddddd call","1");
        refee=FirebaseDatabase.getInstance().getReference().child("ShopOwners");
        refee.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                for (int j=0;j<shopList.size();j++)
                {

                    list.clear();
                    String Name1=shopList.get(j);
                    refee1= FirebaseDatabase.getInstance().getReference().child("ShopOwners").child(Name1).child("Activity");
                    refee1.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                        {
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
                            //nameShop();
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
    public void nameShop()
    {
        refee=FirebaseDatabase.getInstance().getReference().child("ShopOwners");
        refee.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                for (int j=0;j<shopList.size();j++)
                {
                    list.clear();
                    Name=shopList.get(j);
                    refee1 = FirebaseDatabase.getInstance().getReference().child("ShopOwners").child(Name).child("Activity");
                    refee1.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                        {
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
    public void slider()
    {
        SliderView sliderView = findViewById(R.id.imageSlider);

        AdapterSlider adapter33 = new AdapterSlider(this);
        adapter33.renewItems(listimg);
        sliderView.setSliderAdapter(adapter33);

        sliderView.setIndicatorAnimation(IndicatorAnimations.WORM);
        //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        sliderView.setIndicatorSelectedColor(Color.RED);
        sliderView.setIndicatorUnselectedColor(Color.GRAY);
        sliderView.setScrollTimeInSec(4);
        //set scroll delay in seconds :
        sliderView.startAutoCycle();
    }
}
