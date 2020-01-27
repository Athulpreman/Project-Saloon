package com.example.project1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AdminCheckNewShopAcceptance extends AppCompatActivity
{
    DatabaseReference refee;
    RecyclerView recyclerView;
    OwnerAdapter adapter;
    ArrayList<Owner> list;

    List<Owner> itemList;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_check_new_shop_acceptance);


        recyclerView=(RecyclerView)findViewById(R.id.rv);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list=new ArrayList<Owner>();

        refee= FirebaseDatabase.getInstance().getReference().child("Shop_Owners");
        refee.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                for (DataSnapshot studentDatasnapshot : dataSnapshot.getChildren())
                {
                    Owner owner = studentDatasnapshot.getValue(Owner.class);
                    if (owner.status.equals(false))
                    {
                        list.add(owner);
                    }
                }
                adapter = new OwnerAdapter(AdminCheckNewShopAcceptance.this,list);
                recyclerView.setAdapter(adapter);
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {
                Toast.makeText(getApplicationContext(),"something wnt wrong",Toast.LENGTH_LONG).show();
            }
        });




    }


}
