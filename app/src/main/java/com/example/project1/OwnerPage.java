package com.example.project1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
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

public class OwnerPage extends AppCompatActivity
{
    String shopID1;
    Toast backToast;
    long backpress;
    CardView c1,c2,c3,c4,c5,c6,c7,c8,c9;
    TextView ownerMob,shopID,shopName;
    ImageView shopIMG;
    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_page);
        this.setTitle("Owner");

        SharedPreferences sharedPreferences=getSharedPreferences("OwnerLogin",MODE_PRIVATE);
        shopID1=sharedPreferences.getString("shopID",null);

        c1=(CardView)findViewById(R.id.card1);
        c2=(CardView)findViewById(R.id.card2);
        c3=(CardView)findViewById(R.id.card3);
        c4=(CardView)findViewById(R.id.card4);
        c5=(CardView)findViewById(R.id.card5);
        c6=(CardView)findViewById(R.id.card6);
        c7=(CardView)findViewById(R.id.card7);
        c8=(CardView)findViewById(R.id.card8);
        c9=(CardView)findViewById(R.id.cardrating);

        ownerMob=(TextView)findViewById(R.id.opOwnerNumber);
        shopID=(TextView)findViewById(R.id.opShopID);
        shopName=(TextView)findViewById(R.id.opShopName);

        shopIMG=(ImageView)findViewById(R.id.opShopImg);

        ref= FirebaseDatabase.getInstance().getReference().child("ShopOwners");
        Query query=ref.orderByChild("shopID").equalTo(shopID1);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                for (DataSnapshot snapshot:dataSnapshot.getChildren())
                {
                    Owner owner=new Owner();
                    owner=snapshot.getValue(Owner.class);
                    ownerMob.setText(owner.OwnerMobile);
                    shopName.setText(owner.ShopName);
                    shopID.setText(owner.ShopID);
                    Picasso.get().load(owner.Image1).into(shopIMG);
                    ref.removeEventListener(this);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {
            }
        });

        c6.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(getApplicationContext(), "Logged Out", Toast.LENGTH_SHORT).show();
                SharedPreferences.Editor editor=getSharedPreferences("OwnerLogin",MODE_PRIVATE).edit();
                editor.clear();
                editor.commit();

                Intent intent=new Intent(getApplicationContext(),LoginPage.class);
                startActivity(intent);
            }
        });

        c1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent=new Intent(getApplicationContext(),ScanQrShop.class);
                startActivity(intent);
            }
        });
        c2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                Intent intent1=new Intent(getApplicationContext(),OwnerSignedIn.class);
                startActivity(intent1);
            }
        });
        c3.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent=new Intent(getApplicationContext(),OwnerViewUpcomingBookings.class);
                startActivity(intent);
            }
        });
        c4.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent=new Intent(getApplicationContext(),OwnerManageServices.class);
                startActivity(intent);
            }
        });
        c5.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent=new Intent(getApplicationContext(),OwnerViewComplaints.class);
                startActivity(intent);
            }
        });
        c7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent=new Intent(getApplicationContext(),Owner_About.class);
                startActivity(intent);
            }
        });
        c8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
              Intent intent=new Intent(getApplicationContext(),Location_Owner.class);
                startActivity(intent);
            }
        });
        c9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent=new Intent(getApplicationContext(),OwnerViewRating.class);
                startActivity(intent);
            }
        });

    }

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
            backToast= Toast.makeText(getApplicationContext(), "Press again to exit", Toast.LENGTH_SHORT);
            backToast.show();
        }
        backpress=System.currentTimeMillis();
    }
}
