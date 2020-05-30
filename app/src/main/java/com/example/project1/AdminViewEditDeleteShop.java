package com.example.project1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class AdminViewEditDeleteShop extends AppCompatActivity
{
    EditText shopName,ownName,empName,ownMob,empMob,address,id;
    ImageView im1,im2,im3;
    Button disable,delete;
    Owner owner;
    DatabaseReference reference,reffav,reffav1;
    String shopID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view_edit_delete_shop);

        shopName=(EditText)findViewById(R.id.showshopName);
        ownName=(EditText)findViewById(R.id.showOwnerName);
        empName=(EditText)findViewById(R.id.showEmpName);
        ownMob=(EditText)findViewById(R.id.showOwnerMobile);
        empMob=(EditText)findViewById(R.id.showEmpMobile);
        address=(EditText)findViewById(R.id.showAddress);
        id=(EditText)findViewById(R.id.showshoID);
        disable=(Button)findViewById(R.id.admindesableShop);
        delete=(Button)findViewById(R.id.admindeleteShop);
        im1=(ImageView)findViewById(R.id.img1);
        im2=(ImageView)findViewById(R.id.img2);
        im3=(ImageView)findViewById(R.id.img3);

        owner=new Owner();

        Intent intent=getIntent();
        shopID=intent.getStringExtra("shopID");

        reference= FirebaseDatabase.getInstance().getReference().child("ShopOwners");
        Query query=reference.orderByChild("shopID").equalTo(shopID);
        query.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if (dataSnapshot.exists())
                {
                    for (DataSnapshot snapshot:dataSnapshot.getChildren())
                    {
                        owner=snapshot.getValue(Owner.class);
                        Picasso.get().load(owner.Image1).into(im1);
                        Picasso.get().load(owner.Image2).into(im2);
                        Picasso.get().load(owner.Image3).into(im3);
                        shopName.setText(owner.getShopName());
                        ownName.setText(owner.getOwnerName());
                        empName.setText(owner.getEmployeeName());
                        ownMob.setText(owner.getOwnerMobile());
                        empMob.setText(owner.getEmployeeMobile());
                        address.setText(owner.getAddress());
                        id.setText(owner.getShopID());
                        if (owner.status.equals(true))
                        {
                            disable.setText("Disable Account");
                        }
                        else
                        {
                            disable.setText("Enable Account");
                        }
                    }
                }
                else
                {
                    Toast.makeText(AdminViewEditDeleteShop.this, "Something went wrong try again", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {

            }
        });
        disable.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                reffav=FirebaseDatabase.getInstance().getReference().child("ShopOwners");
                Query query1=reffav.orderByChild("shopID").equalTo(shopID);
                query1.addListenerForSingleValueEvent(new ValueEventListener()
                {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                    {
                        for (DataSnapshot snapshot:dataSnapshot.getChildren())
                        {
                           if (owner.status.equals(true))
                           {
                               snapshot.getRef().child("status").setValue(false).addOnSuccessListener(new OnSuccessListener<Void>() {
                                   @Override
                                   public void onSuccess(Void aVoid)
                                   {
                                       Toast.makeText(AdminViewEditDeleteShop.this, "Shop Disabled", Toast.LENGTH_SHORT).show();
                                       doe();
                                   }
                               });
                           }
                           else
                           {
                               snapshot.getRef().child("status").setValue(true).addOnSuccessListener(new OnSuccessListener<Void>() {
                                   @Override
                                   public void onSuccess(Void aVoid)
                                   {
                                       Toast.makeText(AdminViewEditDeleteShop.this, "Shop Disabled", Toast.LENGTH_SHORT).show();
                                       doe();
                                   }
                               });
                           }
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError)
                    {
                    }
                });
            }
        });
        delete.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                reffav=FirebaseDatabase.getInstance().getReference().child("ShopOwners");
                Query query1=reffav.orderByChild("shopID").equalTo(shopID);
                query1.addListenerForSingleValueEvent(new ValueEventListener()
                {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                    {
                        for (DataSnapshot snapshot:dataSnapshot.getChildren())
                        {
                            snapshot.getRef().removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid)
                                {
                                    Toast.makeText(AdminViewEditDeleteShop.this, "Shop deleted", Toast.LENGTH_SHORT).show();
                                    Intent intent1=new Intent(getApplicationContext(),SearchEditShop_Admin.class);
                                    startActivity(intent1);
                                }
                            });
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError)
                    {
                    }
                });
            }
        });
    }
    public void doe()
    {
        reference= FirebaseDatabase.getInstance().getReference().child("ShopOwners");
        Query query=reference.orderByChild("shopID").equalTo(shopID);
        query.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if (dataSnapshot.exists())
                {
                    for (DataSnapshot snapshot:dataSnapshot.getChildren())
                    {
                        owner=snapshot.getValue(Owner.class);
                        Picasso.get().load(owner.Image1).into(im1);
                        Picasso.get().load(owner.Image2).into(im2);
                        Picasso.get().load(owner.Image3).into(im3);
                        shopName.setText(owner.getShopName());
                        ownName.setText(owner.getOwnerName());
                        empName.setText(owner.getEmployeeName());
                        ownMob.setText(owner.getOwnerMobile());
                        empMob.setText(owner.getEmployeeMobile());
                        address.setText(owner.getAddress());
                        if (owner.status.equals(true))
                        {
                            disable.setText("Disable Account");
                        }
                        else
                        {
                            disable.setText("Enable Account");
                        }
                    }
                }
                else
                {
                    Toast.makeText(AdminViewEditDeleteShop.this, "Something went wrong try again", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {

            }
        });
    }
}
