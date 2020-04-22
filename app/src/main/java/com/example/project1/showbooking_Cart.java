package com.example.project1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class showbooking_Cart extends AppCompatActivity
{
    String MobNoo,shopID,Activity,sprice,sname;
    String date,time;
    TextView price,shopName,date1,time1;
    DatabaseReference reference,refeShopName,refePrice,refecancelCutomer,refecancelOwner;
    CBookShop cBookShop,cBook;
    Button showMap,showQR,cancel;
    ImageView imgQR;
    Bitmap bitmap;
    String qrString;
    ProgressBar progressBar;
    TextView progressText;
    AlertDialog.Builder builder;
    ScrollView scroll;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showbooking__cart);

        cBookShop=new CBookShop();
        cBook=new CBookShop();

        shopName=(TextView) findViewById(R.id.shopName);
        time1=(TextView) findViewById(R.id.timeBooking);
        date1=(TextView) findViewById(R.id.dateBooking);
        price=(TextView) findViewById(R.id.Amount);
        showMap=(Button)findViewById(R.id.shoMap);
        cancel=(Button)findViewById(R.id.cancelBooking);
        showQR=(Button)findViewById(R.id.showQRCode);
        imgQR=(ImageView)findViewById(R.id.imgQR);
        progressBar=(ProgressBar)findViewById(R.id.Progressba);
        progressText=(TextView)findViewById(R.id.ProgressbaText);

        scroll = (ScrollView) this.findViewById(R.id.scroll);


        imgQR.setVisibility(View.INVISIBLE);

        SharedPreferences sharedPreferences=getSharedPreferences("UserLogin",MODE_PRIVATE);
        MobNoo=sharedPreferences.getString("MobNo",null);

        Intent intent=getIntent();
        date=intent.getStringExtra("Date");
        time=intent.getStringExtra("Time");
        shopID=intent.getStringExtra("shopID");
        Activity=intent.getStringExtra("Activity");

        refePrice=FirebaseDatabase.getInstance().getReference().child("ShopOwners").child(shopID).child("Activity");
        Query query=refePrice.orderByChild("activity").equalTo(Activity);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                for (DataSnapshot snapshot:dataSnapshot.getChildren())
                {
                    OwnerAdd add=new OwnerAdd();
                    add=snapshot.getValue(OwnerAdd.class);
                    sprice=add.getPrice();
                    price.setText(sprice);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {
            }
        });

        refeShopName=FirebaseDatabase.getInstance().getReference().child("ShopOwners");
        Query query1=refeShopName.orderByChild("shopID").equalTo(shopID);
        query1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren())
                {
                    Owner owner=new Owner();
                    owner=dataSnapshot1.getValue(Owner.class);
                    sname=owner.ShopName;
                    Toast.makeText(showbooking_Cart.this, sname, Toast.LENGTH_SHORT).show();
                    shopName.setText(sname);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {
            }
        });

        reference= FirebaseDatabase.getInstance().getReference().child("Customer").child(MobNoo).child("Booking");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                for (DataSnapshot snapshot:dataSnapshot.getChildren())
                {
                    cBookShop=snapshot.getValue(CBookShop.class);
                    if (cBookShop.Date.equals(date))
                    {
                        date1.setText(cBookShop.getDate());
                        time1.setText(cBookShop.getTime());
                        qrString=cBookShop.qrCode;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        showQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if (!MobNoo.isEmpty()&&!date.isEmpty()&&!time.isEmpty())
                {

                    if (qrString!=null)
                    {
                        LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(1000,1000);
                        imgQR.setLayoutParams(parms);
                        imgQR.setVisibility(View.VISIBLE);
                        scroll.scrollTo(0, scroll.getBottom());
                        try
                        {
                            MultiFormatWriter multiFormatWriter=new MultiFormatWriter();
                            BitMatrix bitMatrix=multiFormatWriter.encode(qrString, BarcodeFormat.QR_CODE,330,330);
                            BarcodeEncoder barcodeEncoder=new BarcodeEncoder();
                            bitmap=barcodeEncoder.createBitmap(bitMatrix);
                            imgQR.setImageBitmap(bitmap);

                        }
                        catch (WriterException e)
                        {
                            Toast.makeText(showbooking_Cart.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "Empty", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(showbooking_Cart.this, "Error loading QR code", Toast.LENGTH_SHORT).show();
                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                AlertDialog.Builder adb = new AlertDialog.Builder(showbooking_Cart.this);
                adb.setTitle("Cancel");
                adb.setMessage("Are you want to cancel?");
                adb.setCancelable(false);
                String yesButtonText = "Yes";
                String noButtonText = "No";
                adb.setPositiveButton(yesButtonText, new DialogInterface.OnClickListener()
                { @Override public void onClick(DialogInterface dialog, int which)
                {
                    progressText.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.VISIBLE);
                    refecancelCutomer=FirebaseDatabase.getInstance().getReference().child("Customer").child(MobNoo).child("Booking");
                    refecancelOwner=FirebaseDatabase.getInstance().getReference().child("ShopOwners").child(shopID).child("Booking").child(date);
                    Query query2=refecancelOwner.orderByChild("time").equalTo(time);
                    query2.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                        {
                            for (DataSnapshot snapshot:dataSnapshot.getChildren())
                            {
                                snapshot.getRef().removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid)
                                    {
                                        progressText.setText("Canceling");
                                        Query query3=refecancelCutomer.orderByChild("date").equalTo(date);
                                        query3.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                                            {
                                                for (DataSnapshot snapshot1:dataSnapshot.getChildren())
                                                {
                                                    snapshot1.getRef().removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void aVoid)
                                                        {
                                                            Toast.makeText(showbooking_Cart.this, "Cancelled succesfully", Toast.LENGTH_SHORT).show();
                                                            progressText.setVisibility(View.INVISIBLE);
                                                            progressBar.setVisibility(View.INVISIBLE);

                                                            Intent intent1=new Intent(getApplicationContext(),CustomerSignedIn1.class);
                                                            startActivity(intent1);
                                                        }
                                                    });
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
                                        });
                                    }
                                });
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });


                }
                }); adb.setNegativeButton(noButtonText, new DialogInterface.OnClickListener()
            { @Override public void onClick(DialogInterface dialog, int which)
            {

            } });
                adb.show();
            }
        });
        showMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

            }
        });


    }
}
