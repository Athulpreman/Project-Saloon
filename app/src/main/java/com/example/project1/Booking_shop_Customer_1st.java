package com.example.project1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
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
    String shopID,activity,price,MobNoo;
    Owner owner;
    DatabaseReference reference,reffav,reffav1;
    AlertDialog.Builder builder;
    ImageView addToFav;
    int a,b,c;
    boolean state;

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
        addToFav=(ImageView)findViewById(R.id.addToFav);

        SharedPreferences sharedPreferences=getSharedPreferences("UserLogin",MODE_PRIVATE);
        MobNoo=sharedPreferences.getString("MobNo",null);

        Intent intent=getIntent();
        shopID=intent.getStringExtra("shopID");
        activity=intent.getStringExtra("activity");
        price=intent.getStringExtra("price");

        reference= FirebaseDatabase.getInstance().getReference().child("ShopOwners");
        owner=new Owner();

        findFav();

        /*reffav=FirebaseDatabase.getInstance().getReference().child("Customer").child(MobNoo).child("Favourite");
        reffav.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if (!dataSnapshot.exists())
                {
                    addToFav.setImageResource(R.drawable.fav_unfav);
                }
                a=0;
                for (DataSnapshot snapshot:dataSnapshot.getChildren())
                {
                    a++;
                    CFav cFav=new CFav();
                    cFav=snapshot.getValue(CFav.class);
                    if (cFav.activity.equals(activity)&&cFav.shopID.equals(shopID))
                    {
                        addToFav.setImageResource(R.drawable.fav_fav);
                    }
                    if (a==dataSnapshot.getChildrenCount())
                    {
                        addToFav.setImageResource(R.drawable.fav_unfav);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/

        Query query=reference.orderByChild("shopID").equalTo(shopID);
        query.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                for (DataSnapshot dataSnapshot11:dataSnapshot.getChildren())
                {
                    owner=dataSnapshot11.getValue(Owner.class);

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
        addToFav.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (state==false)
                {
                    CToast cToast=new CToast();
                    cToast.toast(getApplicationContext(),"Please Waite",0);
                }
                else
                {
                    if (c==0)
                    {
                        Log.d("aaaaa","if");
                        CFav cFav=new CFav();
                        cFav.activity=activity;
                        cFav.shopID=shopID;
                        reffav=FirebaseDatabase.getInstance().getReference().child("Customer").child(MobNoo).child("Favourite");
                        reffav.push().setValue(cFav).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid)
                            {
                                CToast cToast=new CToast();
                                cToast.toast(getApplicationContext(),"Added to favourite list",0);
                                findFav();
                            }
                        });
                    }
                    else
                    {
                        Log.d("aaaaa","else");
                        reffav=FirebaseDatabase.getInstance().getReference().child("Customer").child(MobNoo).child("Favourite");
                        Query query1=reffav.orderByChild("shopID").equalTo(shopID);
                        query1.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                            {
                                Log.d("aaaaa","before for");
                                for (DataSnapshot snapshot:dataSnapshot.getChildren())
                                {
                                    Log.d("aaaaa","for");
                                    CFav cFav=new CFav();
                                    cFav=snapshot.getValue(CFav.class);
                                    if (cFav.activity.equals(activity))
                                    {
                                        Log.d("aaaaa","2if");
                                        snapshot.getRef().removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid)
                                            {
                                                Log.d("aaaaa","removed");
                                                CToast cToast=new CToast();
                                                cToast.toast(getApplicationContext(),"Removed from favourite list",0);
                                                findFav();
                                            }
                                        });
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                }
            }
        });
        book.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent inte=new Intent(getApplicationContext(),BookAppoinment.class);
                inte.putExtra("shopID",shopID);
                inte.putExtra("price",price);
                inte.putExtra("activity",activity);
                startActivity(inte);
            }
        });

    }
    public void favdisplay(int x)
    {
        c=x;
        state=true;
        if(x==0)
        {
            addToFav.setImageResource(R.drawable.fav_unfav);
        }
        if (x==1)
        {
            addToFav.setImageResource(R.drawable.fav_fav);
        }
    }
    public void findFav()
    {
        state=false;
        b=0;
        reffav1=FirebaseDatabase.getInstance().getReference().child("Customer").child(MobNoo).child("Favourite");
        reffav1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if (!dataSnapshot.exists())
                {
                    b=0;
                    favdisplay(b);
                }
                a=0;
                for (DataSnapshot snapshot:dataSnapshot.getChildren())
                {
                    a++;
                    CFav cFav=new CFav();
                    cFav=snapshot.getValue(CFav.class);
                    if (cFav.activity.equals(activity)&&cFav.shopID.equals(shopID))
                    {
                        b=1;
                        favdisplay(b);
                    }
                    if (a==dataSnapshot.getChildrenCount())
                    {
                        if (b!=1)
                        {
                            favdisplay(b);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    @Override
    public void onBackPressed()
    {
        SharedPreferences.Editor editor=getSharedPreferences("Booking",MODE_PRIVATE).edit();
        editor.clear();
        editor.commit();

        Intent intent=new Intent(getApplicationContext(),CustomerSignedIn1.class);
        startActivity(intent);
    }
}
