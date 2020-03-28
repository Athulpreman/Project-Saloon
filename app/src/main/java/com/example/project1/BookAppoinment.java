package com.example.project1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
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
import java.util.Date;

public class BookAppoinment extends AppCompatActivity
{
    EditText date,aa;
    Spinner time;
    Button nextbtn;
    String getDate,getTime,date1,name,mob,shopID,shopName,shopAdress,qrString,sActivity,ssActivity;
    Customer customer1;
    DatabaseReference refee,reference,ref,customerRef,refee1,refActivity,refCheckCustomerBooked;
    String MobNoo;
    CBookShop cBookShop,cBookShop1;
    Owner owner;
    ArrayList<String>listActivity;
    String list[];
    int k;
    int xxx=0;
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
        /*if (!price.isEmpty())
        {
            tprice.setText(price);
        }
        if (true)
        {
            rs.setVisibility(View.INVISIBLE);
        }
        if (!aactivity.isEmpty())
        {
            aa.setText(aactivity);
        }*/

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

                //finding the day from an outside function
                String dayOftheWeek=getDayFromDateString(getDate,"yyyy,MM,dd");



                if (ssActivity.equals(""))
                {
                    autoCompleteTextView.setError("Enter a service");
                    autoCompleteTextView.requestFocus();
                    progressBar.setVisibility(View.INVISIBLE);
                    progressText.setVisibility(View.INVISIBLE);
                }
                if (!listActivity.contains(ssActivity))
                {
                    autoCompleteTextView.setError("The service is not available in this shop");
                    autoCompleteTextView.requestFocus();
                    progressBar.setVisibility(View.INVISIBLE);
                    progressText.setVisibility(View.INVISIBLE);
                }
                else if (getDate.isEmpty())
                {
                    Toast.makeText(BookAppoinment.this, "Chose a date", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.INVISIBLE);
                    progressText.setVisibility(View.INVISIBLE);
                }
                else if (dayOftheWeek.equals("sunday"))
                {
                    Toast.makeText(BookAppoinment.this, "Sunday is holiday \n Chose a different day", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.INVISIBLE);
                    progressText.setVisibility(View.INVISIBLE);
                }
                else
                {

                    String ss=getDate;
                    String[]split=ss.split(",");
                    final String a=split[0];
                    String b=split[1];
                    String c=split[2];
                    String dateSelected=a+b+c;

                    String tt=formattedDate;
                    String[]split1=ss.split(",");
                    String d=split[0];
                    String e=split[1];
                    String f=split[2];
                    String currentdateinNum=d+e+f;



                    //checking date is after the current date
                    if (formattedDate.compareTo(date1)>0)
                    {
                        Toast.makeText(BookAppoinment.this, "Chosen date is not currect", Toast.LENGTH_LONG).show();
                        progressBar.setVisibility(View.INVISIBLE);
                        progressText.setVisibility(View.INVISIBLE);
                    }
                    //cannot book date that is 7 days after current date
                    else if (Integer.parseInt(currentdateinNum)-Integer.parseInt(dateSelected)>7)
                    {
                        Toast.makeText(BookAppoinment.this, "Can only book shop for the 7 days from today", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.INVISIBLE);
                        progressText.setVisibility(View.INVISIBLE);
                    }
                    else
                    {
                        //checking customer booked on the same date
                        refCheckCustomerBooked=FirebaseDatabase.getInstance().getReference().child("Customer").child(MobNoo).child("Booking").child(getDate);
                        Query query=refCheckCustomerBooked.orderByChild("date").equalTo(formattedDate);
                        query.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                            {
                                if (dataSnapshot.exists())
                                {
                                    Toast.makeText(BookAppoinment.this, "You have Already booked the in the same date. You can only book once a day", Toast.LENGTH_LONG).show();
                                    progressBar.setVisibility(View.INVISIBLE);
                                    progressText.setVisibility(View.INVISIBLE);
                                }
                                else
                                {   progressText.setText("Making Your Booking....");
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
                                        public void onCancelled(@NonNull DatabaseError databaseError)
                                        {

                                        }
                                    });


                                    customerRef= FirebaseDatabase.getInstance().getReference().child("Customer").child(MobNoo).child("Booking").child(getDate);
                                    reference = FirebaseDatabase.getInstance().getReference().child("ShopOwners").child(shopID).child("Booking").child(getDate);

                                    xxx=0;
                                    Query query2=reference.orderByChild("time").equalTo(getTime);
                                    query2.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                                        {
                                            if (dataSnapshot.exists())
                                            {
                                               for (DataSnapshot snapshot:dataSnapshot.getChildren())
                                               {
                                                   CBookShop cBookShop=new CBookShop();
                                                   cBookShop=snapshot.getValue(CBookShop.class);
                                                   if (cBookShop.time.equals(getTime))
                                                   {
                                                       Toast.makeText(BookAppoinment.this, "The time is not available \n Chose a different time", Toast.LENGTH_SHORT).show();
                                                       xxx=1;
                                                       progressBar.setVisibility(View.INVISIBLE);
                                                       progressText.setVisibility(View.INVISIBLE);
                                                   }
                                               }
                                            }
                                            else
                                            {
                                                xxx=1;
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
                                                reference.child(getTime).setValue(cBookShop).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid)
                                                    {
                                                        customerRef.setValue(cBookShop).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                            @Override
                                                            public void onSuccess(Void aVoid)
                                                            {
                                                                Toast.makeText(BookAppoinment.this, "booking has been Placed", Toast.LENGTH_LONG).show();
                                                                Intent intent1=new Intent(getApplicationContext(),Check_Availability_Of_Shop.class);
                                                                progressBar.setVisibility(View.INVISIBLE);
                                                                progressText.setVisibility(View.INVISIBLE);
                                                                intent1.putExtra("qrString",qrString);
                                                                startActivity(intent1);
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
                                    /*if (xxx==0)
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
                                        reference.child(getTime).setValue(cBookShop).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid)
                                            {
                                                customerRef.setValue(cBookShop).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid)
                                                    {
                                                        Toast.makeText(BookAppoinment.this, "Your booking has been Placed", Toast.LENGTH_LONG).show();
                                                        progressBar.setVisibility(View.INVISIBLE);
                                                        progressText.setVisibility(View.INVISIBLE);
                                                        Intent intent1=new Intent(getApplicationContext(),Bookin_status_show.class);
                                                        intent1.putExtra("qrString",qrString);
                                                        startActivity(intent1);
                                                    }
                                                });
                                            }
                                        });
                                    }*/
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
    public static String getDayFromDateString(String stringDate,String dateTimeFormat)
    {
        String[] daysArray = new String[] {"sunday","monday","tuesday","wednesday","thursday","friday","saturday"};
        String day ="";

        int dayOfWeek =0;
        //dateTimeFormat = yyyy-MM-dd HH:mm:ss
        SimpleDateFormat formatter = new SimpleDateFormat(dateTimeFormat);
        Date date;
        try {
            date = formatter.parse(stringDate);
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            dayOfWeek = c.get(Calendar.DAY_OF_WEEK)-1;
            if (dayOfWeek < 0) {
                dayOfWeek += 7;
            }
            day = daysArray[dayOfWeek];
        } catch (Exception e) {
            e.printStackTrace();
        }

        return day;
    }
}
