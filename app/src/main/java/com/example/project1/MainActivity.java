package com.example.project1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.wifi.hotspot2.pps.HomeSp;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Vector;

public class MainActivity extends AppCompatActivity
{
    Button blogin,gotosignup;
    TextView tshopOption,tforgotPass;
    EditText ephoneNo,epasswoed;
    String mobno,password;
    long backpress;
    Toast backToast;
    DatabaseReference reference;
    Customer customer;
    private static int timout=4000;
    ProgressBar progressBar;
    TextView progressText;

    //signup
    Button gotologin,bsignup;
    EditText eename,eephoneNo,eepasswoed;
    String sname,smobno,spassword,sphoneno;
    DatabaseReference rreference;
    Customer ccustomer;
    long bbackpress;
    Toast bbackToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setTitle("Login");

        SharedPreferences sharedPreferences=getSharedPreferences("UserLogin",MODE_PRIVATE);
        String value=sharedPreferences.getString("MobNo",null);
        if (value!=null)
        {
            Intent intent=new Intent(getApplicationContext(),CustomerSignedIn1.class);
            startActivity(intent);
        }

        SharedPreferences sharedPreference=getSharedPreferences("OwnerLogin",MODE_PRIVATE);
        String value1=sharedPreference.getString("shopID",null);
        if (value1!=null)
        {
            Intent intent=new Intent(getApplicationContext(),OwnerPage.class);
            startActivity(intent);
        }

        blogin=(Button)findViewById(R.id.loginButtonlogin);
        gotosignup=(Button)findViewById(R.id.signinSignupButton);
        tshopOption=(TextView)findViewById(R.id.loginShopOption);
        tforgotPass=(TextView)findViewById(R.id.loginForgotPassword);
        ephoneNo=(EditText)findViewById(R.id.loginMobNo);
        epasswoed=(EditText)findViewById(R.id.loginPassword);

        progressBar=(ProgressBar)findViewById(R.id.Progressba);
        progressText=(TextView)findViewById(R.id.ProgressbaText);

        customer=new Customer();
        reference= FirebaseDatabase.getInstance().getReference().child("Customer");


        blogin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mobno=ephoneNo.getText().toString();
                password=epasswoed.getText().toString();

                if (mobno.isEmpty()||mobno.length()<10)
                {
                    ephoneNo.setError("Enter correct mob no");
                    ephoneNo.requestFocus();
                }
                else if (password.isEmpty())
                {
                    epasswoed.setError("Password is rquired");
                    epasswoed.requestFocus();
                }
                else
                {
                    progressText.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.VISIBLE);

                    String code="91";
                    mobno="+" + code + mobno;
                    Query query=reference.orderByChild("mobileNum").equalTo(mobno);
                    query.addListenerForSingleValueEvent(new ValueEventListener()
                    {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            if (dataSnapshot.exists())
                            {

                                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                                {
                                    customer = snapshot.getValue(Customer.class);
                                    if (password.equals(customer.password))
                                    {
                                        SharedPreferences.Editor editor=getSharedPreferences("UserLogin",MODE_PRIVATE).edit();
                                        editor.putString("MobNo",mobno);
                                        editor.putString("Name",customer.name);
                                        editor.commit();


                                        Toast.makeText(getApplicationContext(), "Logged in", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getApplicationContext(), CustomerSignedIn1.class);
                                        startActivity(intent);

                                        ephoneNo.setText("");
                                        epasswoed.setText("");
                                    }
                                    else
                                    {
                                        Toast.makeText(getApplicationContext(), "Wrong password", Toast.LENGTH_SHORT).show();
                                        progressText.setVisibility(View.INVISIBLE);
                                        progressBar.setVisibility(View.INVISIBLE);
                                    }
                                }
                            }
                            else
                            {
                                ephoneNo.setError("User dont exists");
                                progressText.setVisibility(View.INVISIBLE);
                                progressBar.setVisibility(View.INVISIBLE);
                            }
                        }


                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }
        });
        gotosignup.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                decide(2);
            }
        });
        tshopOption.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent=new Intent(getApplicationContext(),LoginPage.class);
                startActivity(intent);
            }
        });
        tforgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
    public void decide(int s)
    {
        if (s==1)
        {
            setContentView(R.layout.activity_main);
            this.setTitle("Login");
            SharedPreferences sharedPreferences=getSharedPreferences("UserLogin",MODE_PRIVATE);
            String value=sharedPreferences.getString("MobNo",null);
            if (value!=null)
            {
                Intent intent=new Intent(getApplicationContext(),CustomerSignedIn1.class);
                startActivity(intent);
            }

            SharedPreferences sharedPreference=getSharedPreferences("OwnerLogin",MODE_PRIVATE);
            String value1=sharedPreference.getString("shopID",null);
            if (value1!=null)
            {
                Intent intent=new Intent(getApplicationContext(),OwnerPage.class);
                startActivity(intent);
            }

            blogin=(Button)findViewById(R.id.loginButtonlogin);
            gotosignup=(Button)findViewById(R.id.signinSignupButton);
            tshopOption=(TextView)findViewById(R.id.loginShopOption);
            tforgotPass=(TextView)findViewById(R.id.loginForgotPassword);
            ephoneNo=(EditText)findViewById(R.id.loginMobNo);
            epasswoed=(EditText)findViewById(R.id.loginPassword);

            customer=new Customer();
            reference= FirebaseDatabase.getInstance().getReference().child("Customer");


            blogin.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    mobno=ephoneNo.getText().toString();
                    password=epasswoed.getText().toString();

                    if (mobno.isEmpty()||mobno.length()<10)
                    {
                        ephoneNo.setError("Enter correct mob no");
                        ephoneNo.requestFocus();
                    }
                    else if (password.isEmpty())
                    {
                        epasswoed.setError("Password is rquired");
                        epasswoed.requestFocus();
                    }
                    else
                    {
                        progressText.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.VISIBLE);

                        String code="91";
                        mobno="+" + code + mobno;
                        Query query=reference.orderByChild("mobileNum").equalTo(mobno);
                        query.addListenerForSingleValueEvent(new ValueEventListener()
                        {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                if (dataSnapshot.exists())
                                {

                                    for (DataSnapshot snapshot : dataSnapshot.getChildren())
                                    {
                                        customer = snapshot.getValue(Customer.class);
                                        if (password.equals(customer.password))
                                        {
                                            SharedPreferences.Editor editor=getSharedPreferences("UserLogin",MODE_PRIVATE).edit();
                                            editor.putString("MobNo",mobno);
                                            editor.putString("Name",customer.name);
                                            editor.commit();


                                            Toast.makeText(getApplicationContext(), "Logged in", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(getApplicationContext(), CustomerSignedIn1.class);
                                            startActivity(intent);

                                            ephoneNo.setText("");
                                            epasswoed.setText("");
                                        }
                                        else
                                        {
                                            Toast.makeText(getApplicationContext(), "Wrong password", Toast.LENGTH_SHORT).show();
                                            progressText.setVisibility(View.INVISIBLE);
                                            progressBar.setVisibility(View.INVISIBLE);
                                        }
                                    }
                                }
                                else
                                {
                                    ephoneNo.setError("User dont exists");
                                    progressText.setVisibility(View.INVISIBLE);
                                    progressBar.setVisibility(View.INVISIBLE);
                                }
                            }


                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                }
            });
            gotosignup.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    decide(2);
                }
            });
            tshopOption.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Intent intent=new Intent(getApplicationContext(),LoginPage.class);
                    startActivity(intent);
                }
            });
            tforgotPass.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
        if (s==2)
        {
            setContentView(R.layout.activity_sign_up);
            this.setTitle("Sign Up");

            gotologin=(Button)findViewById(R.id.signupButtonLogin);
            bsignup=(Button)findViewById(R.id.signupButtonsignup);
            eephoneNo=(EditText)findViewById(R.id.signupMobNo);
            eepasswoed=(EditText)findViewById(R.id.signupPassword);
            eename=(EditText)findViewById(R.id.signupName);

            ccustomer=new Customer();


            bsignup.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    smobno=eephoneNo.getText().toString();
                    spassword=eepasswoed.getText().toString();
                    sname=eename.getText().toString().trim();

                    if (sname.isEmpty())
                    {
                        eename.setError("Name is required");
                        eename.requestFocus();
                    }
                    else if (smobno.isEmpty())
                    {
                        eephoneNo.setError("Mobile No. is required");
                        eephoneNo.requestFocus();
                    }
                    else if (smobno.length()<10)
                    {
                        eephoneNo.setError("Mobile No. must have 10 numbers");
                        eephoneNo.requestFocus();
                    }
                    else if (spassword.isEmpty())
                    {
                        eepasswoed.setError("Password is rquired");
                        eepasswoed.requestFocus();
                    }
                    else
                    {
                        progressText.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.VISIBLE);

                        String code="91";
                        sphoneno="+" + code + smobno;


                        rreference= FirebaseDatabase.getInstance().getReference().child("Customer");

                        Query query=rreference.orderByChild("mobileNum").equalTo(sphoneno);
                        query.addListenerForSingleValueEvent(new ValueEventListener()
                        {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                            {
                                if (dataSnapshot.exists())
                                {
                                    eephoneNo.setError("Customer already exist..! Try Login");
                                    eephoneNo.requestFocus();
                                    progressText.setVisibility(View.INVISIBLE);
                                    progressBar.setVisibility(View.INVISIBLE);
                                }
                                else
                                {
                                    Intent intent=new Intent(getApplicationContext(),signupOTP_Verification.class);
                                    intent.putExtra("signMobile",sphoneno);
                                    intent.putExtra("signPassword",spassword);
                                    intent.putExtra("signName",sname);
                                    progressText.setVisibility(View.INVISIBLE);
                                    progressBar.setVisibility(View.INVISIBLE);
                                    startActivity(intent);


                                    eename.setText("");
                                    eepasswoed.setText("");
                                    eephoneNo.setText("");
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError)
                            {
                                Toast.makeText(MainActivity.this, "Database error", Toast.LENGTH_SHORT).show();
                                progressText.setVisibility(View.INVISIBLE);
                                progressBar.setVisibility(View.INVISIBLE);
                            }
                        });

                    }
                }
            });
            gotologin.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    decide(1);
                }
            });
        }
    }
}
