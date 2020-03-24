package com.example.project1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class BookAppoinment extends AppCompatActivity
{
    EditText date,aa;
    Spinner time;
    Button nextbtn;
    String getDate,getTime,date1,name,mob,shopID,shopName,shopAdress,qrString,sActivity,ssActivity;
    Customer customer1;
    DatabaseReference refee,reference,ref,customerRef,refee1,refActivity;
    String MobNoo;
    CBookShop cBookShop,cBookShop1;
    Owner owner;
    ArrayList<String>listActivity;
    String list[];
    int k;
    ArrayAdapter<String> adapter1;
    AutoCompleteTextView autoCompleteTextView;
    TextView progressText,tprice,rs;
    ProgressBar progressBar;
    String aactivity,price;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_appoinment);

        SharedPreferences sharedPreferences=getSharedPreferences("UserLogin",MODE_PRIVATE);
        MobNoo=sharedPreferences.getString("MobNo",null);
        mob=MobNoo;
        name=sharedPreferences.getString("Name",null);
        autoCompleteTextView=findViewById(R.id.au);
        cBookShop=new CBookShop();
        cBookShop1=new CBookShop();
        customer1=new Customer();
        owner=new Owner();
        listActivity=new ArrayList<>();
        tprice=(TextView)findViewById(R.id.price);
        rs=(TextView)findViewById(R.id.rs);


        Intent inten=getIntent();
        price=inten.getStringExtra("price");
        aactivity=inten.getStringExtra("activity");
        shopID=inten.getStringExtra("shopID");
        if (!price.isEmpty())
        {
            tprice.setText(price);
        }
        else
        {
            rs.setVisibility(View.INVISIBLE);
        }
        if (!aactivity.isEmpty())
        {
            aa.setText(aactivity);
        }

        progressBar=(ProgressBar)findViewById(R.id.Progressba);
        progressText=(TextView)findViewById(R.id.ProgressbaText);

        refee= FirebaseDatabase.getInstance().getReference().child("Customer");

        date=(EditText)findViewById(R.id.getDate);
        time=(Spinner)findViewById(R.id.getTime);
        nextbtn=(Button)findViewById(R.id.BookAppoinmentNext);
        final Calendar calendar = Calendar.getInstance();
        final int year=calendar.get(calendar.YEAR);
        final int month=calendar.get(calendar.MONTH);
        final int day=calendar.get(calendar.DAY_OF_MONTH);


        final Calendar c = Calendar.getInstance();
        final SimpleDateFormat df = new SimpleDateFormat("yyyy,MM,dd");
        final String formattedDate = df.format(c.getTime());

        refActivity=FirebaseDatabase.getInstance().getReference().child("ShopOwners").child(shopID).child("Activity");
        refActivity.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                Toast.makeText(BookAppoinment.this, shopID, Toast.LENGTH_SHORT).show();
                listActivity.clear();
                if (dataSnapshot.exists())
                {
                    for (DataSnapshot snapshot:dataSnapshot.getChildren())
                    {
                        cBookShop1=snapshot.getValue(CBookShop.class);
                        listActivity.add(cBookShop1.activity);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {

            }
        });
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,listActivity);
        autoCompleteTextView.setAdapter(adapter);

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

                progressBar.setVisibility(View.VISIBLE);
                progressText.setVisibility(View.VISIBLE);

                getDate=date.getText().toString();
                getTime=time.getSelectedItem().toString();
                ssActivity=autoCompleteTextView.getText().toString();
                Toast.makeText(BookAppoinment.this, ssActivity, Toast.LENGTH_SHORT).show();

                if (getDate.isEmpty())
                {
                    Toast.makeText(BookAppoinment.this, "Chose a date", Toast.LENGTH_SHORT).show();
                }
                else if (ssActivity.equals(""))
                {
                    autoCompleteTextView.setError("Chose an activity");
                    autoCompleteTextView.requestFocus();
                }
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
                                            }

                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });


                            //getting the shop details
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

                            Query query2=reference.orderByChild("statusBit").equalTo("0");
                            query2.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                                {
                                    if (dataSnapshot.exists())
                                    {
                                        Toast.makeText(BookAppoinment.this, "The time is booked Chose another time", Toast.LENGTH_SHORT).show();
                                    }
                                    else
                                    {

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
                                                                cBookShop.activity=ssActivity;
                                                                qrString=MobNoo+"-"+getDate+"-"+getTime;
                                                                cBookShop.qrCode=qrString;
                                                                Toast.makeText(BookAppoinment.this, qrString, Toast.LENGTH_SHORT).show();

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
                                                                                intent1.putExtra("qrString",qrString);
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
                                                    cBookShop.activity=ssActivity;
                                                    qrString=MobNoo+"-"+getDate+"-"+getTime;
                                                    cBookShop.qrCode=qrString;

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
                                                                    intent1.putExtra("qrString",qrString);
                                                                    intent1.putExtra("MobNoo",MobNoo);
                                                                    intent1.putExtra("getDate",getDate);
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

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });

                        }
                }
            }
        });
    }
}
