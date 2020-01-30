package com.example.project1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

public class OwnerSignedIn extends AppCompatActivity
{
    EditText Amount,Time;
    Spinner Activity;
    Button Submit;
    String sActivity,sAmount,sTime;
    OwnerAdd ownerAdd;
    DatabaseReference reference,reference2,ref;
    String shopID1;

    Query query;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_signed_in);

        ownerAdd=new OwnerAdd();

        Activity=(Spinner)findViewById(R.id.ownerAddSpinnerActivity);
        Amount=(EditText)findViewById(R.id.ownerAddAmount);
        Time=(EditText)findViewById(R.id.ownerAddTime);

        Submit=(Button)findViewById(R.id.OwnerAddSubmitButton);

        Intent intent=getIntent();
        shopID1=intent.getStringExtra("shopID");

        reference= FirebaseDatabase.getInstance().getReference().child("Shop_Owners");
        Submit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                sActivity=Activity.getSelectedItem().toString();
                sAmount=Amount.getText().toString();
                sTime=Time.getText().toString();

                ownerAdd.setActivity(sActivity);
                ownerAdd.setPrice(sAmount);
                ownerAdd.setTime(sTime);

                query=reference.orderByChild("shopID").equalTo(shopID1);
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                    {

                        Toast.makeText(getApplicationContext(), reference.getKey(), Toast.LENGTH_SHORT).show();
                        reference.push().setValue(ownerAdd).addOnSuccessListener(new OnSuccessListener<Void>()
                        {
                            @Override
                            public void onSuccess(Void aVoid)
                            {
                                Toast.makeText(getApplicationContext(), "Added successfully", Toast.LENGTH_SHORT).show();
                                Time.setText("");
                                Amount.setText("");
                            }
                        });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError)
                    {

                    }
                });

            }
        });
    }
}
