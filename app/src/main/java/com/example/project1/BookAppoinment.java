package com.example.project1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class BookAppoinment extends AppCompatActivity
{
    EditText date;
    Spinner time;
    Button nextbtn;
    String getDate,getTime,date1,name,mob,shopID,shopName,shopAdress;
    Customer customer1;
    DatabaseReference refee,reference,ref,customerRef,refee1;
    String MobNoo;
    CBookShop cBookShop;
    Owner owner;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_appoinment);

        SharedPreferences sharedPreferences=getSharedPreferences("UserLogin",MODE_PRIVATE);
        MobNoo=sharedPreferences.getString("MobNo",null);
        mob=MobNoo;
        name=sharedPreferences.getString("Name",null);

        cBookShop=new CBookShop();
        customer1=new Customer();
        owner=new Owner();

        refee= FirebaseDatabase.getInstance().getReference().child("Customer");

        date=(EditText)findViewById(R.id.getDate);
        time=(Spinner)findViewById(R.id.getTime);
        nextbtn=(Button)findViewById(R.id.BookAppoinmentNext);

        final Calendar calendar = Calendar.getInstance();
        final int year=calendar.get(calendar.YEAR);
        final int month=calendar.get(calendar.MONTH);
        final int day=calendar.get(calendar.DAY_OF_MONTH);

        Intent intent=getIntent();
        shopID=intent.getStringExtra("shopID");

        final Calendar c = Calendar.getInstance();
        final SimpleDateFormat df = new SimpleDateFormat("yyyy,MM,dd");
        final String formattedDate = df.format(c.getTime());

        date.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

               DatePickerDialog datePickerDialog=new DatePickerDialog(BookAppoinment.this, new DatePickerDialog.OnDateSetListener() {
                    @Override

                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth)
                    {
                        month=month+1;
                        String smonth=Integer.toString(month);
                        String sday=Integer.toString(dayOfMonth);
                        if (sday.length()==1)
                        {
                            sday="0"+sday;
                        }
                        if (smonth.length()==1)
                        {
                            smonth="0"+smonth;
                        }
                        date1=year+","+smonth+","+sday;
                        date.setText(date1);
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });

        nextbtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                getDate=date.getText().toString();
                getTime=time.getSelectedItem().toString();

                if (getDate.isEmpty())
                {
                    Toast.makeText(BookAppoinment.this, "Chose a date", Toast.LENGTH_SHORT).show();              }
                else
                {
                    if (formattedDate.compareTo(date1)>0)
                    {
                        Toast.makeText(BookAppoinment.this, "Chosen date is not currect", Toast.LENGTH_LONG).show();
                    }
                    else
                        {
                            Query query=refee.orderByChild("mobileNum").equalTo(MobNoo);
                            query.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.exists())
                                    {
                                        for (DataSnapshot ds:dataSnapshot.getChildren())
                                        {

                                            customer1=ds.getValue(Customer.class);
                                            if (MobNoo.equals(customer1.mobileNum))
                                            {
                                                name=customer1.mobileNum;
                                                mob=customer1.name;
                                            }

                                            else
                                            {
                                                Toast.makeText(BookAppoinment.this, "snapshot doesnot exist", Toast.LENGTH_SHORT).show();
                                            }

                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });


                            refee1=FirebaseDatabase.getInstance().getReference().child("ShopOwners");
                            Query query1=refee.orderByChild("shopID").equalTo(shopID);
                            query1.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.exists())
                                    {
                                        for (DataSnapshot ds:dataSnapshot.getChildren())
                                        {
                                            owner=ds.getValue(Owner.class);
                                            shopAdress=owner.Address;
                                            shopName=owner.ShopName;
                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });


                            customerRef= FirebaseDatabase.getInstance().getReference().child("Customer").child(MobNoo).child("Booking").child(getDate);
                            reference = FirebaseDatabase.getInstance().getReference().child("ShopOwners").child(shopID).child("Booking").child(getDate);
                            reference.addValueEventListener(new ValueEventListener()
                            {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                                {
                                    if (dataSnapshot.exists())
                                    {
                                        reference.child(getTime).addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                                            {
                                                if (dataSnapshot.exists())
                                                {
                                                   Toast.makeText(BookAppoinment.this, "The Time Has already Booked...Chose a different time", Toast.LENGTH_LONG).show();
                                                }
                                                else
                                                {
                                                    Log.d("aaaa","b");
                                                    cBookShop.Date=getDate;
                                                    cBookShop.time=getTime;
                                                    cBookShop.CustomerName=name;
                                                    cBookShop.CustomerMob=mob;
                                                    cBookShop.ShopID=shopID;
                                                    cBookShop.shopAddress=shopAdress;
                                                    cBookShop.shopName=shopName;
                                                    reference.child(getDate).setValue(cBookShop).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void aVoid)
                                                        {
                                                            customerRef.setValue(cBookShop).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                @Override
                                                                public void onSuccess(Void aVoid)
                                                                {
                                                                    Toast.makeText(BookAppoinment.this, "booking Placed", Toast.LENGTH_SHORT).show();
                                                                    Intent intent1=new Intent(getApplicationContext(),Bookin_status_show.class);
                                                                    startActivity(intent1);
                                                                }
                                                            });
                                                        }
                                                    });

                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError)
                                            {
                                                Toast.makeText(BookAppoinment.this, "Database Error....!", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }
                                    else
                                    {
                                        cBookShop.Date=getDate;
                                        cBookShop.time=getTime;
                                        cBookShop.CustomerName=name;
                                        cBookShop.CustomerMob=mob;
                                        cBookShop.ShopID=shopID;
                                        cBookShop.shopAddress=shopAdress;
                                        cBookShop.shopName=shopName;
                                        ref=FirebaseDatabase.getInstance().getReference().child("ShopOwners").child(shopID).child("Booking").child(getDate);
                                        ref.child(getTime).setValue(cBookShop).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid)
                                            {
                                                customerRef.setValue(cBookShop).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid)
                                                    {
                                                        Toast.makeText(BookAppoinment.this, "booking Placed", Toast.LENGTH_SHORT).show();
                                                        Intent intent1=new Intent(getApplicationContext(),Bookin_status_show.class);
                                                        startActivity(intent1);
                                                    }
                                                });
                                            }
                                        });
                                    }
                                }
                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError)
                                {
                                    Toast.makeText(BookAppoinment.this, "Database Error...!", Toast.LENGTH_SHORT).show();
                                }
                            });

                        }
                }
            }
        });
    }
}
