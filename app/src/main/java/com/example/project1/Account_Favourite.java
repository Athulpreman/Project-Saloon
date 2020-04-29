package com.example.project1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

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

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account__favourite);

        SharedPreferences sharedPreferences=getSharedPreferences("UserLogin",MODE_PRIVATE);
        MobNoo=sharedPreferences.getString("MobNo",null);

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
}
