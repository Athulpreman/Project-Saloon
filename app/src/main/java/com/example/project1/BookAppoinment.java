package com.example.project1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
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
    EditText date;
    Button nextbtn;
    String getDate,date1,name,mob,shopID,shopName,shopAdress,ssActivity,formattedDate,aactivity="",price="",MobNoo;
    Customer customer1;
    DatabaseReference refee,refee1,refActivity,refCheckCustomerBooked;
    CBookShop cBookShop,cBookShop1;
    Owner owner;
    ArrayList<String>listActivity;
    AutoCompleteTextView autoCompleteTextView;
    TextView progressText,tprice,rs;
    ProgressBar progressBar;
    AlertDialog.Builder builder;

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

        Intent intent=getIntent();
        shopID=intent.getStringExtra("shopID");
        price=intent.getStringExtra("price");
        aactivity=intent.getStringExtra("activity");


        try
        {
            if (shopID.equals("")||shopID.isEmpty()||shopID.equals(null))
            {
                Log.d("aaaaaa","shopID empty");
            }

            if (!price.equals("")&&!price.isEmpty()&&!price.equals(null))
            {
                tprice.setText(price);
            }
            else
            {
                rs.setVisibility(View.INVISIBLE);
                tprice.setVisibility(View.INVISIBLE);
            }
            if (!aactivity.equals("")&&!aactivity.isEmpty()&&!aactivity.equals(null))
            {
                autoCompleteTextView.setText(aactivity);
            }
        }
        catch (Exception e)
        {
            rs.setVisibility(View.INVISIBLE);
            tprice.setVisibility(View.INVISIBLE);
        }
        progressBar=(ProgressBar)findViewById(R.id.Progressba);
        progressText=(TextView)findViewById(R.id.ProgressbaText);

        refee= FirebaseDatabase.getInstance().getReference().child("Customer");

        date=(EditText)findViewById(R.id.getDate);
        nextbtn=(Button)findViewById(R.id.BookAppoinmentNext);
        final Calendar calendar = Calendar.getInstance();
        final int year=calendar.get(calendar.YEAR);
        final int month=calendar.get(calendar.MONTH);
        final int day=calendar.get(calendar.DAY_OF_MONTH);


        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy,MM,dd");
        formattedDate = df.format(c.getTime());


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
                    Calendar now1= Calendar.getInstance();
                    now1.add(Calendar.DATE, +7);
                    int a1,a2,a3;
                    String b1,b2,b3;
                    a1=now1.get(Calendar.DATE);
                    a2=now1.get((Calendar.MONDAY)+0);
                    a2=a2+1;
                    a3=now1.get(Calendar.YEAR);
                    b1=String.valueOf(a1);
                    b2=String.valueOf(a2);
                    b3=String.valueOf(a3);
                    if (b1.length()==1)
                    {
                        b1="0"+b1;
                    }
                    if (b2.length()==1)
                    {
                        b2="0"+b2;
                    }
                    String beforedate=b3+","+b2+","+b1;
                    int a,b;
                    a=Integer.parseInt(beforedate.replaceAll("[\\D]",""));
                    b=Integer.parseInt(getDate.replaceAll("[\\D]",""));

                    //checking date is after the current date
                    if (formattedDate.compareTo(date1)>0)
                    {
                        Toast.makeText(BookAppoinment.this, "Chosen date is not currect", Toast.LENGTH_LONG).show();
                        progressBar.setVisibility(View.INVISIBLE);
                        progressText.setVisibility(View.INVISIBLE);
                    }
                    //cannot book date that is 7 days after current date
                    else if (b>a)
                    {
                        Toast.makeText(BookAppoinment.this, "Can only book shop for the 7 days from today", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.INVISIBLE);
                        progressText.setVisibility(View.INVISIBLE);
                    }
                    else
                    {
                        //checking customer booked on the same date
                        refCheckCustomerBooked=FirebaseDatabase.getInstance().getReference().child("Customer").child(MobNoo).child("Booking");
                        Query query=refCheckCustomerBooked.orderByChild("date").equalTo(getDate);
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
                                {   progressText.setText("Checking....");
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

                                    Toast.makeText(BookAppoinment.this, "Forwarding to pick time slot", Toast.LENGTH_LONG).show();

                                    SharedPreferences.Editor editor=getSharedPreferences("Book",MODE_PRIVATE).edit();
                                    editor.putString("date",getDate);
                                    editor.putString("activity",ssActivity);
                                    editor.putString("shopID",shopID);
                                    editor.putString("name",name);
                                    editor.putString("mobile",mob);
                                    editor.putString("address",shopAdress);
                                    editor.putString("shopName",shopName);
                                    editor.putString("price",price);
                                    editor.apply();

                                    progressBar.setVisibility(View.INVISIBLE);
                                    progressText.setVisibility(View.INVISIBLE);


                                    Intent intent1=new Intent(getApplicationContext(),Check_Availability_Of_Shop.class);
                                    startActivity(intent1);
                                    //refActivity.removeEventListener((ChildEventListener) getApplicationContext());

                                    /*refCheckCustomerBooked.removeEventListener((ChildEventListener) getApplicationContext());
                                    refee.removeEventListener((ChildEventListener) getApplicationContext());
                                    refee1.removeEventListener((ChildEventListener) getApplicationContext());
                                    refActivity.removeEventListener((ChildEventListener) getApplicationContext());*/
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

    @Override
    public void onBackPressed()
    {
        builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure want to LOGOUT ?") .setTitle("Cancel");

        //Setting message manually and performing action on button click
        builder.setMessage("Are you sure want to cancel ?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        SharedPreferences.Editor editor=getSharedPreferences("Booking",MODE_PRIVATE).edit();
                        editor.clear();
                        editor.commit();
                        Toast.makeText(getApplicationContext(),"Booking cancelled", Toast.LENGTH_SHORT).show();

                        Intent intent=new Intent(getApplicationContext(),CustomerSignedIn1.class);
                        startActivity(intent);
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
        alert.setTitle("CANCEL");
        alert.show();
    }
}
