package com.example.project1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.HashMap;

public class Account_Favourite extends AppCompatActivity
{
    DatabaseReference ref,reference,refee;
    RecyclerView recyclerView;
    AdapterFavourite adapterFavourite;
    ArrayList<OwnerAdd> listService;
    ArrayList<CFav> list;
    String MobNoo;
    int a,b,position;
    TextView noFav;
    ProgressBar progressBar;
    TextView progressText;
    SliderLayout sliderLayout;
    HashMap<String,String>hashMap;
    DatabaseReference refer;
    ArrayList<Owner>listimg;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account__favourite);

        SharedPreferences sharedPreferences=getSharedPreferences("UserLogin",MODE_PRIVATE);
        MobNoo=sharedPreferences.getString("MobNo",null);

        listimg=new ArrayList<Owner>();
        /*
        sliderLayout=(SliderLayout)findViewById(R.id.sliderLayout);
        hashMap=new HashMap<String, String>();

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
                        m=1;
                        for (int i=0;i<listimg.size()-1;i++)
                        {
                            hashMap.put(listimg.get(i).ShopName,listimg.get(i).Image1);
                        }
                        for (String name:hashMap.keySet())
                        {
                            TextSliderView textSliderView=new TextSliderView(Account_Favourite.this);
                            textSliderView.description(name).image(hashMap.get(name))
                                    .setScaleType(BaseSliderView.ScaleType.Fit).
                                    setOnSliderClickListener((BaseSliderView.OnSliderClickListener) getApplicationContext());
                            textSliderView.bundle(new Bundle());
                            textSliderView.getBundle().putString("extra",name);
                            sliderLayout.addSlider(textSliderView);
                        }
                        sliderLayout.setPresetTransformer(SliderLayout.Transformer.Accordion);
                        sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
                        sliderLayout.setCustomAnimation(new DescriptionAnimation());
                        sliderLayout.setDuration(3000);
                        sliderLayout.addOnPageChangeListener((ViewPagerEx.OnPageChangeListener) getApplicationContext());
                    }
                }
                if (m==0)
                {
                    for (int i=0;i<listimg.size()-1;i++)
                    {
                        hashMap.put(listimg.get(i).ShopName,listimg.get(i).Image1);
                    }
                    for (String name:hashMap.keySet())
                    {
                        TextSliderView textSliderView=new TextSliderView(Account_Favourite.this);
                        textSliderView.description(name).image(hashMap.get(name))
                                .setScaleType(BaseSliderView.ScaleType.Fit).
                                setOnSliderClickListener((BaseSliderView.OnSliderClickListener) Account_Favourite.this);
                        textSliderView.bundle(new Bundle());
                        textSliderView.getBundle().putString("extra",name);
                        sliderLayout.addSlider(textSliderView);
                    }
                    sliderLayout.setPresetTransformer(SliderLayout.Transformer.Accordion);
                    sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
                    sliderLayout.setCustomAnimation(new DescriptionAnimation());
                    sliderLayout.setDuration(3000);
                    sliderLayout.addOnPageChangeListener((ViewPagerEx.OnPageChangeListener) getApplicationContext());



                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {
            }
        });
*/


        //



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















        //

        list=new ArrayList<CFav>();
        listService=new ArrayList<OwnerAdd>();

        recyclerView=(RecyclerView)findViewById(R.id.favrv);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        noFav=(TextView)findViewById(R.id.noFavourite);
        progressBar=(ProgressBar)findViewById(R.id.Progressba);
        progressText=(TextView)findViewById(R.id.ProgressbaText);

        ref= FirebaseDatabase.getInstance().getReference().child("Customer").child(MobNoo).child("Favourite");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if (!dataSnapshot.exists())
                {
                    noFav.setVisibility(View.VISIBLE);
                }
                else
                {
                    a=0;
                    for (DataSnapshot snapshot:dataSnapshot.getChildren())
                    {
                        a++;
                        CFav c=new CFav();
                        c=snapshot.getValue(CFav.class);
                        list.add(c);
                        Log.d("aaaa add CFav",c.activity);
                        if (a==dataSnapshot.getChildrenCount())
                        {
                            Log.d("aaaa call","showfav");
                            showfav();
                        }
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void showfav()
    {
        if (list.isEmpty())
        {

        }
        else
        {
            position=0;
            for (CFav cFav:list)
            {
                if (position<list.size())
                {
                    position++;
                    refee=FirebaseDatabase.getInstance().getReference().child("ShopOwners").child(cFav.getShopID()).child("Activity");
                    Query query=refee.orderByChild("activity").equalTo(cFav.activity);
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                        {
                            for (DataSnapshot snapshot:dataSnapshot.getChildren())
                            {
                                OwnerAdd ownerAdd=new OwnerAdd();
                                ownerAdd=snapshot.getValue(OwnerAdd.class);
                                listService.add(ownerAdd);
                                Log.d("aaaa service add",ownerAdd.Activity);
                            }
                            if (position==list.size())
                            {
                                progressBar.setVisibility(View.INVISIBLE);
                                progressText.setVisibility(View.INVISIBLE);

                                Log.d("aaaa","adapter");
                                adapterFavourite = new AdapterFavourite(Account_Favourite.this, listService);
                                recyclerView.setAdapter(adapterFavourite);
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
    }
    public void slider()
    {
        SliderView sliderView = findViewById(R.id.imageSlider);

        AdapterSlider adapter = new AdapterSlider(this);
        adapter.renewItems(listimg);
        sliderView.setSliderAdapter(adapter);

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
