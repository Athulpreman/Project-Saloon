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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class Booking_shop_Customer_1st extends AppCompatActivity
{
    EditText shopName,ownName,empName,ownMob,empMob,address;
    Button book;
    ImageView im1,im2,im3;
    String shopID;
    Owner owner;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_shop__customer_1st);

        shopName=(EditText)findViewById(R.id.showshopName);
        ownName=(EditText)findViewById(R.id.showOwnerName);
        empName=(EditText)findViewById(R.id.showEmpName);
        ownMob=(EditText)findViewById(R.id.showOwnerMobile);
        empMob=(EditText)findViewById(R.id.showEmpMobile);
        address=(EditText)findViewById(R.id.showAddress);
        book=(Button)findViewById(R.id.ownerViewGrantPermisssionButton);
        im1=(ImageView)findViewById(R.id.img1);
        im2=(ImageView)findViewById(R.id.img2);
        im3=(ImageView)findViewById(R.id.img3);

        Intent intent=getIntent();
        shopID=intent.getStringExtra("shopID");

        reference= FirebaseDatabase.getInstance().getReference().child("ShopOwners");
        owner=new Owner();

        Query query=reference.orderByChild("shopID").equalTo(shopID);
        query.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren())
                {
                    owner=dataSnapshot1.getValue(Owner.class);

                    ownName.setText(owner.getOwnerName());
                    shopName.setText(owner.getShopName());
                    ownMob.setText(owner.getOwnerMobile());
                    address.setText(owner.getAddress());
                    empName.setText(owner.getEmployeeName());
                    empMob.setText(owner.getEmployeeMobile());

                    Picasso.get().load(owner.getImage1()).into(im1);
                    Picasso.get().load(owner.getImage2()).into(im2);
                    Picasso.get().load(owner.getImage3()).into(im3);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {
                Toast.makeText(Booking_shop_Customer_1st.this, "Database Error....!", Toast.LENGTH_SHORT).show();
            }
        });
        book.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent inte=new Intent(getApplicationContext(),BookAppoinment.class);
                inte.putExtra("shopID",shopID);
                startActivity(inte);
            }
        });

    }
}
