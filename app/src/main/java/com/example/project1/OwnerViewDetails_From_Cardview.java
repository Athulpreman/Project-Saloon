package com.example.project1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class OwnerViewDetails_From_Cardview extends AppCompatActivity
{

    String sOwnerName,sShopName,sShopID,sOwnerMobile,sAddress,sEmployeeName,sEmployeeMobile,sPassword,sImage1="",sImage2="",sImage3="";
    EditText eOwnerName,eShopName,eShopID,eOwnerMobile,eAddress,eEmployeeName,eEmployeeMobile,ePassword;
    ImageView iimg1,iimg2,iimg3;
    Button Permit;
    DatabaseReference reference,ref;
    Owner owner;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_view_details__from__cardview);

        Intent intent=getIntent();
        sShopID=intent.getStringExtra("shopID");

        eOwnerName=(EditText)findViewById(R.id.showOwnerName);
        eShopName=(EditText)findViewById(R.id.showshopName);
        eShopID=(EditText)findViewById(R.id.showShopID);
        eOwnerMobile=(EditText)findViewById(R.id.showOwnerMobile);
        eAddress=(EditText)findViewById(R.id.showAddress);
        eEmployeeName=(EditText)findViewById(R.id.showEmpName);
        eEmployeeMobile=(EditText)findViewById(R.id.showEmpMobile);
        Permit=(Button) findViewById(R.id.ownerViewGrantPermisssionButton);

        iimg1=(ImageView)findViewById(R.id.img1);
        iimg2=(ImageView)findViewById(R.id.img2);
        iimg3=(ImageView)findViewById(R.id.img3);

        reference= FirebaseDatabase.getInstance().getReference().child("ShopOwners");
        owner=new Owner();

        Query query=reference.orderByChild("shopID").equalTo(sShopID);
        query.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren())
                {
                    owner=dataSnapshot1.getValue(Owner.class);

                    eOwnerName.setText(owner.getOwnerName());
                    eShopName.setText(owner.getShopName());
                    eShopID.setText(owner.getShopID());
                    eOwnerMobile.setText(owner.getOwnerMobile());
                    eAddress.setText(owner.getAddress());
                    eEmployeeName.setText(owner.getEmployeeName());
                    eEmployeeMobile.setText(owner.getEmployeeMobile());

                    Picasso.get().load(owner.getImage1()).into(iimg1);
                    Picasso.get().load(owner.getImage2()).into(iimg2);
                    Picasso.get().load(owner.getImage3()).into(iimg3);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Permit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Query query=reference.orderByChild("shopID").equalTo(sShopID);
                query.addListenerForSingleValueEvent(new ValueEventListener()
                {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                    {
                        for (DataSnapshot snapshot:dataSnapshot.getChildren())
                        {   Log.d("cccc","for");
                            snapshot.getRef().child("status").setValue(true);
                            Toast.makeText(getApplicationContext(), owner.getShopID()+" Has accepted", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError)
                    {
                        Toast.makeText(getApplicationContext(), "Error....!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(getApplicationContext(),AdminCheckNewShopAcceptance.class);
        startActivity(intent);
    }
}
