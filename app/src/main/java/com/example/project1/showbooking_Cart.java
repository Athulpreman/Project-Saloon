package com.example.project1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
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
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class showbooking_Cart extends AppCompatActivity
{
    String MobNoo,shopID,Activity,sprice;
    String date,time;
    TextView price,name,date1,time1;
    DatabaseReference reference,refe;
    CBookShop cBookShop,cBook;
    Button showMap,showQR;
    ImageView imgQR;
    Bitmap bitmap;
    String qrString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showbooking__cart);

        cBookShop=new CBookShop();
        cBook=new CBookShop();

        name=(TextView) findViewById(R.id.shopName);
        time1=(TextView) findViewById(R.id.timeBooking);
        date1=(TextView) findViewById(R.id.dateBooking);
        price=(TextView) findViewById(R.id.Amount);
        showMap=(Button)findViewById(R.id.shoMap);
        showQR=(Button)findViewById(R.id.showQRCode);
        imgQR=(ImageView)findViewById(R.id.imgQR);

        imgQR.setVisibility(View.INVISIBLE);

        SharedPreferences sharedPreferences=getSharedPreferences("UserLogin",MODE_PRIVATE);
        MobNoo=sharedPreferences.getString("MobNo",null);

        Intent intent=getIntent();
        date=intent.getStringExtra("Date");
        time=intent.getStringExtra("Time");
        shopID=intent.getStringExtra("shopID");
        Activity=intent.getStringExtra("Activity");

        /*refe=FirebaseDatabase.getInstance().getReference().child("ShopOwners").child(shopID).child("Activity");
        Query query=refe.orderByChild("activity").equalTo(Activity);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                OwnerAdd add=new OwnerAdd();
                add=dataSnapshot.getValue(OwnerAdd.class);
                sprice=add.getPrice();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/

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
                        price.setText(cBookShop.getPrice());
                        name.setText(cBookShop.getShopName());
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
                    qrString=MobNoo+"-"+date+"-"+time;
                    if (qrString!=null)
                    {
                        imgQR.setVisibility(View.VISIBLE);
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
        showMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

            }
        });

    }
}
