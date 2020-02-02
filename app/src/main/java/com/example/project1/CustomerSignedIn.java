package com.example.project1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CustomerSignedIn extends AppCompatActivity
{
    String mobNo;
    Toast backToast;
    long backpress;
    Button Logout;

    CgetOwner cgetOwner;

    int i=0;

    ArrayList<String>sAOwnerName;

    DatabaseReference refOwnerName,refActivity,ref;
    RecyclerView recyclerView;
    AdapterCustomerHome adapter;
    String listOName[];


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_signed_in);

        cgetOwner=new CgetOwner();

        Logout=(Button)findViewById(R.id.CustomerSignedInLogoutButton);

        recyclerView=(RecyclerView)findViewById(R.id.rvCustomerHome);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this,3));


        refOwnerName=FirebaseDatabase.getInstance().getReference().child("Shop_Owner");
        refOwnerName.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {


                if (dataSnapshot.exists())
                {
                    Toast.makeText(getApplicationContext(), "on", Toast.LENGTH_SHORT).show();

                }

                for (DataSnapshot snapOwnerName:dataSnapshot.getChildren())
                {

                    Toast.makeText(getApplicationContext(), "oonilla", Toast.LENGTH_SHORT).show();
                    OwnerAdd ownerAdd=snapOwnerName.getValue(OwnerAdd.class);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {

            }
        });


        Logout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                SharedPreferences.Editor editor=getSharedPreferences("UserLogin",MODE_PRIVATE).edit();
                editor.clear();
                editor.commit();

                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
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
            backToast=Toast.makeText(getApplicationContext(), "Press again to exit", Toast.LENGTH_SHORT);
            backToast.show();
        }
        backpress=System.currentTimeMillis();

    }
}
