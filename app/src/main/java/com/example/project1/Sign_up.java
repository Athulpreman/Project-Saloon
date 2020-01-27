package com.example.project1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Sign_up extends AppCompatActivity
{
    Button gotologin,bsignup;
    EditText ename,ephoneNo,epasswoed;
    String name,mobno,password,phoneno;
    DatabaseReference reference;
    Customer customer;
    long backpress;
    Toast backToast;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        gotologin=(Button)findViewById(R.id.signupButtonLogin);
        bsignup=(Button)findViewById(R.id.signupButtonsignup);
        ephoneNo=(EditText)findViewById(R.id.signupMobNo);
        epasswoed=(EditText)findViewById(R.id.signupPassword);
        ename=(EditText)findViewById(R.id.signupName);

        customer=new Customer();
        reference= FirebaseDatabase.getInstance().getReference().child("Customer");


        bsignup.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mobno=ephoneNo.getText().toString();
                password=epasswoed.getText().toString();
                name=ename.getText().toString().trim();

                if (name.isEmpty())
                {
                    ename.setError("Name is required");
                    ename.requestFocus();
                }
                else if (mobno.isEmpty())
                {
                    ephoneNo.setError("Mobile No. is required");
                    ephoneNo.requestFocus();
                }
                else if (mobno.length()<10)
                {
                    ephoneNo.setError("Mobile No. must have 10 numbers");
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
                    phoneno="+" + code + mobno;


                    Query query=reference.orderByChild("mobileNum").equalTo(phoneno);
                    query.addListenerForSingleValueEvent(new ValueEventListener()
                    {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                        {
                            if (dataSnapshot.exists())
                            {
                                ephoneNo.setError("Customer already exist..! Try Login");
                                ephoneNo.requestFocus();
                            }
                            else
                            {
                                Intent intent=new Intent(getApplicationContext(),signupOTP_Verification.class);
                                intent.putExtra("signMobile",phoneno);
                                intent.putExtra("signPassword",password);
                                intent.putExtra("signName",name);
                                startActivity(intent);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError)
                        {

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
                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
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
