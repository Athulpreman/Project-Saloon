package com.example.project1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class ScanQrResult extends AppCompatActivity
{
    String result,shopID,currentDate,hour,minute;
    ImageView scanImg;
    Button accept,decline;
    TextView name,mobile,date,time,status;
    DatabaseReference refee,ref,reff;
    int a;
    String items[]={"","","","",""};
    TextView progreeText;
    ProgressBar progressBar;
    AlertDialog.Builder builder;

    //
    String stime;
    int acceptingStatus=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_qr_result);

        accept=(Button)findViewById(R.id.scanAcceptButton);
        decline=(Button)findViewById(R.id.scanDeclineButton);
        scanImg=(ImageView)findViewById(R.id.scanimgstatus);
        name=(TextView)findViewById(R.id.scanName);
        mobile=(TextView)findViewById(R.id.scanMobile);
        date=(TextView)findViewById(R.id.scanDate);
        time=(TextView)findViewById(R.id.scanTime);
        status=(TextView)findViewById(R.id.scanStatus);
        progreeText=(TextView)findViewById(R.id.scanProgressText);
        progressBar=(ProgressBar)findViewById(R.id.scanProgressbar);

        Intent i=getIntent();
        result=i.getStringExtra("scanResult");

        SharedPreferences sharedPreference=getSharedPreferences("OwnerLogin",MODE_PRIVATE);
        shopID=sharedPreference.getString("shopID",null);

        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy,MM,dd");
        currentDate = df.format(c.getTime());

        //current time
        Calendar cal1 = Calendar.getInstance();
        hour=String.valueOf(cal1.get(Calendar.HOUR));
        minute=String.valueOf(cal1.get(Calendar.MINUTE));


        items = result.split("-");
        items[0]=items[0].trim();
        items[1]=items[1].trim();
        items[2]=items[2].trim();
        items[3]=items[3].trim();

        if (items[0].equals(null)||items[1].equals(null)||items[2].equals(null)||items[3].equals(null)||items[0].length()<4||items[1].length()!=13||items[2].length()!=10||items[3].length()!=2)
        {
            Log.d("aaaa","jj");
            name.setText("Error...!");
            mobile.setText("Error...!");
            time.setText("Error...!");
            date.setText("Error...!");
            status.setText("Error...!");
            scanImg.setImageResource(R.drawable.vector_cross);
            accept.setVisibility(View.INVISIBLE);
            decline.setVisibility(View.INVISIBLE);

            progressBar.setVisibility(View.INVISIBLE);
            progreeText.setVisibility(View.INVISIBLE);
        }
        else if (!shopID.equals(items[0]))
        {
            Log.d("aaaa","kk");
            Log.d("aaaa id",items[0]+" "+shopID);
            Log.d("aaaa id",items[1]+" "+shopID);
            Log.d("aaaa id",items[2]+" "+shopID);
            Log.d("aaaa id",items[3]+" "+shopID);
            Log.d("aaaa id",items[4]+" "+shopID);
            name.setText("Error...!");
            mobile.setText("Error...!");
            time.setText("Error...!");
            date.setText("Error...!");
            status.setText("Not on the currect Shop");
            scanImg.setImageResource(R.drawable.vector_cross);
            accept.setVisibility(View.INVISIBLE);
            decline.setVisibility(View.INVISIBLE);

            progressBar.setVisibility(View.INVISIBLE);
            progreeText.setVisibility(View.INVISIBLE);
        }
        else if (!items[2].equals(currentDate))
        {
            refee= FirebaseDatabase.getInstance().getReference().child("Customer");
            Query query=refee.orderByChild("mobileNum").equalTo(items[1]);
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                {
                    if (!dataSnapshot.exists())
                    {
                        Log.d("aaaa","ll");
                        name.setText("Error...!");
                        mobile.setText("Error...!");
                        time.setText("Error...!");
                        date.setText("Error...!");
                        status.setText("Error...!");
                        scanImg.setImageResource(R.drawable.vector_cross);
                        accept.setVisibility(View.INVISIBLE);
                        decline.setVisibility(View.INVISIBLE);

                        progressBar.setVisibility(View.INVISIBLE);
                        progreeText.setVisibility(View.INVISIBLE);
                    }
                    a=0;
                    for (DataSnapshot snapshot:dataSnapshot.getChildren())
                    {
                        a++;
                        Customer customer=new Customer();
                        customer=snapshot.getValue(Customer.class);
                        name.setText(customer.name);
                        mobile.setText(items[1]);
                        time.setText(items[3]);
                        date.setText(items[2]);
                        status.setText("Not on the currect DATE");
                        scanImg.setImageResource(R.drawable.vector_cross);
                        accept.setVisibility(View.INVISIBLE);
                        decline.setText("Home");

                        progressBar.setVisibility(View.INVISIBLE);
                        progreeText.setVisibility(View.INVISIBLE);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        else
        {
            refee= FirebaseDatabase.getInstance().getReference().child("Customer");
            Query query=refee.orderByChild("mobileNum").equalTo(items[1]);
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                {
                    if (!dataSnapshot.exists())
                    {
                        Log.d("aaaa","mm");
                        name.setText("Error...!");
                        mobile.setText("Error...!");
                        time.setText("Error...!");
                        date.setText("Error...!");
                        status.setText("Error...!");
                        scanImg.setImageResource(R.drawable.vector_cross);
                        accept.setVisibility(View.INVISIBLE);
                        decline.setVisibility(View.INVISIBLE);

                        progressBar.setVisibility(View.INVISIBLE);
                        progreeText.setVisibility(View.INVISIBLE);
                    }
                    a=0;
                    for (DataSnapshot snapshot:dataSnapshot.getChildren())
                    {
                        a++;
                        Customer customer=new Customer();
                        customer=snapshot.getValue(Customer.class);
                        name.setText(customer.name);
                        mobile.setText(items[1]);
                        time.setText(items[3]);
                        date.setText(items[2]);
                        scanImg.setImageResource(R.drawable.vector_cross);
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError)
                {
                }
            });
            reff=FirebaseDatabase.getInstance().getReference().child("Customer").child(items[1]).child("Booking");
            Query query1=reff.orderByChild("date").equalTo(currentDate);
            query1.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                {
                        for (DataSnapshot snapshot:dataSnapshot.getChildren())
                        {
                            Log.d("aaaa","for");
                            CBookShop cBookShop=new CBookShop();
                            cBookShop=snapshot.getValue(CBookShop.class);
                            stime=cBookShop.time;
                            String []time = stime.split("-");
                            time[0]=time[0].trim();
                            time[1]=time[1].trim();
                            Log.d("aaaaa1 hour",hour);
                            Log.d("aaaaa2 time1",time[0]);
                            Log.d("aaaaa3 time 2",time[1]);
                            if (hour.equals("0"))
                            {
                                Log.d("aaaa","rrrr1");
                                if (time[0].equals("12"))
                                {
                                    Log.d("aaaa","rrrr1.111");
                                    status.setText("On Time");
                                    decline.setVisibility(View.INVISIBLE);
                                    progressBar.setVisibility(View.INVISIBLE);
                                    progreeText.setVisibility(View.INVISIBLE);
                                    //scanImg.setImageResource(R.drawable.vector_pending_green);
                                    scanImg.setImageDrawable(getResources().getDrawable(R.drawable.vector_pending_green));

                                }
                                else
                                {
                                    status.setText("not On Time");
                                    acceptingStatus=1;
                                    scanImg.setImageResource(R.drawable.vector_pending_green);
                                }
                            }
                            else if (Integer.parseInt(time[0])>12)
                            {
                                Log.d("aaaa","rrrr2");
                                int a=Integer.parseInt(time[0]);
                                a=a-12;
                                if (String.valueOf(a).equals(hour))
                                {
                                    status.setText("On Time");
                                    decline.setVisibility(View.INVISIBLE);
                                    progressBar.setVisibility(View.INVISIBLE);
                                    progreeText.setVisibility(View.INVISIBLE);
                                    scanImg.setImageResource(R.drawable.vector_pending_green);
                                }
                                else
                                {
                                    status.setText("not On Time");
                                    acceptingStatus=1;
                                    scanImg.setImageResource(R.drawable.vector_pending);
                                }

                            }
                            else if (time[0].equals(hour))
                            {
                                Log.d("aaaa","rrrr3");
                                status.setText("On Time");
                                decline.setVisibility(View.INVISIBLE);
                                progressBar.setVisibility(View.INVISIBLE);
                                progreeText.setVisibility(View.INVISIBLE);
                                scanImg.setImageResource(R.drawable.vector_pending_green);
                            }
                            else
                            {
                                Log.d("aaaa","rrrr4");
                                status.setText("not On Time");
                                acceptingStatus=1;
                                scanImg.setImageResource(R.drawable.vector_pending);
                            }
                            progressBar.setVisibility(View.INVISIBLE);
                            progreeText.setVisibility(View.INVISIBLE);

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if (acceptingStatus==1)
                {
                    builder = new AlertDialog.Builder(ScanQrResult.this);
                    builder.setMessage("Customer is not on time. Accept now ?") .setTitle("Accept");

                    builder.setMessage("Customer is not on time. Accept now ?")
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id)
                                {
                                    Book();
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id)
                                {
                                    dialog.cancel();
                                }
                            });

                    AlertDialog alert = builder.create();
                    alert.setTitle("Accept");
                    alert.show();
                }
                else
                {
                    Book();
                }
            }
        });
        decline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent=new Intent(getApplicationContext(),OwnerPage.class);
                startActivity(intent);
            }
        });
    }
    public void Book()
    {
        refee= FirebaseDatabase.getInstance().getReference().child("Customer").child(items[1]).child("Booking");
        final Query query=refee.orderByChild("date").equalTo(items[2]);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                for (DataSnapshot snapshot:dataSnapshot.getChildren())
                {
                    snapshot.getRef().child("statusBit").setValue("1").addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid)
                        {
                            ref=FirebaseDatabase.getInstance().getReference().child("ShopOwners").child(shopID).child("Booking").child(currentDate);
                            Query query1=ref.orderByChild("time").equalTo(stime);
                            query1.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                                {
                                    for (DataSnapshot snapshot1:dataSnapshot.getChildren())
                                    {
                                        snapshot1.getRef().child("statusBit").setValue("1").addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid)
                                            {
                                                Toast.makeText(ScanQrResult.this, "Sucess", Toast.LENGTH_SHORT).show();
                                                scanImg.setImageResource(R.drawable.scan_tick_mark);

                                                accept.setVisibility(View.INVISIBLE);
                                                decline.setText("Home");
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

    @Override
    public void onBackPressed()
    {
        Intent intent=new Intent(getApplicationContext(),OwnerPage.class);
        startActivity(intent);
    }
}
