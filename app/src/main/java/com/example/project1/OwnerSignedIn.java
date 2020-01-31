package com.example.project1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
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
    DatabaseReference reference,ref;
    String shopID1;

    Query query;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_signed_in);

        SharedPreferences sharedPreferences=getSharedPreferences("OwnerLogin",MODE_PRIVATE);
        shopID1=sharedPreferences.getString("shopID",null);

        ownerAdd=new OwnerAdd();

        Activity=(Spinner)findViewById(R.id.ownerAddSpinnerActivity);
        Amount=(EditText)findViewById(R.id.ownerAddAmount);
        Time=(EditText)findViewById(R.id.ownerAddTime);

        Submit=(Button)findViewById(R.id.OwnerAddSubmitButton);

        reference= FirebaseDatabase.getInstance().getReference().child("Shop_Owners").child(shopID1);
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
                        ref= FirebaseDatabase.getInstance().getReference().child("Shop_Owners").child(shopID1).child("Activity").child(sActivity);
                        ref.setValue(ownerAdd).addOnSuccessListener(new OnSuccessListener<Void>()
                        {
                            @Override
                            public void onSuccess(Void aVoid)
                            {
                                Toast.makeText(getApplicationContext(), "Added successfully", Toast.LENGTH_SHORT).show();
                                Time.setText("");
                                Amount.setText("");

                                Intent intent1=new Intent(getApplicationContext(),OwnerPage.class);
                                startActivity(intent1);
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
