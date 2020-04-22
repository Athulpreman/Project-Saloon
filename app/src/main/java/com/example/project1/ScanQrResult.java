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
            scanImg.setImageResource(R.drawable.scan_cross_mark);
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
            scanImg.setImageResource(R.drawable.scan_cross_mark);
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
                        scanImg.setImageResource(R.drawable.scan_cross_mark);
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
                        scanImg.setImageResource(R.drawable.scan_cross_mark);
                        accept.setVisibility(View.INVISIBLE);
                        decline.setVisibility(View.INVISIBLE);

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
                        scanImg.setImageResource(R.drawable.scan_cross_mark);
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
                        scanImg.setImageResource(R.drawable.scan_cross_mark);
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError)
                {
                }
            });
            reff=FirebaseDatabase.getInstance().getReference().child("Customer").child(items[1]).child("Booking");
            Query query1=reff.orderByChild("date").equalTo(currentDate);
            Log.d("aaaa","rrrr");
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
                            if (time[0].equals(hour))
                            {
                                status.setText("On Time");
                                decline.setVisibility(View.INVISIBLE);
                                progressBar.setVisibility(View.INVISIBLE);
                                progreeText.setVisibility(View.INVISIBLE);
                            }
                            else
                            {
                                status.setText("not On Time");
                                acceptingStatus=1;
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

                    //Setting message manually and performing action on button click
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
                                    //  Action for 'NO' Button
                                    dialog.cancel();
                                }
                            });

                    //Creating dialog box
                    AlertDialog alert = builder.create();
                    //Setting the title manually
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
        refee= FirebaseDatabase.getInstance().getReference().child("Customer");
        final Query query=refee.orderByChild("mobileNum").equalTo(items[1]);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                for (DataSnapshot snapshot:dataSnapshot.getChildren())
                {
                    Customer customer=new Customer();
                    customer=snapshot.getValue(Customer.class);
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
}
