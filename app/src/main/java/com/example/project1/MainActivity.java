package com.example.project1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPreferences=getSharedPreferences("UserLogin",MODE_PRIVATE);
        String value=sharedPreferences.getString("MobNo",null);
        if (value!=null)
        {
            Intent intent=new Intent(getApplicationContext(),CustomerSignedIn.class);
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
                                        editor.commit();


                                        Toast.makeText(getApplicationContext(), "Logged in", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getApplicationContext(), CustomerSignedIn.class);
                                        startActivity(intent);

                                        ephoneNo.setText("");
                                        epasswoed.setText("");
                                    }
                                    else
                                    {
                                        Toast.makeText(getApplicationContext(), "Wrong password", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                            else
                            {
                                ephoneNo.setError("User dont exists");
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
                Intent intent=new Intent(getApplicationContext(),Sign_up.class);
                startActivity(intent);
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
        tforgotPass.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

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
}
