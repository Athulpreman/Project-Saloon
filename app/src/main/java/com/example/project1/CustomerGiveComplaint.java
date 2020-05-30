package com.example.project1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CustomerGiveComplaint extends AppCompatActivity
{
    String shopID,activity,qrString,phone,subject,description;
    Button cancel,forward;
    EditText esubject,edesc;
    DatabaseReference ref;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_give_complaint);
        forward=(Button)findViewById(R.id.complaintForward);
        cancel=(Button)findViewById(R.id.complaintCancel);
        esubject=(EditText)findViewById(R.id.complaintSubject);
        edesc=(EditText)findViewById(R.id.complaintDescription);

        Intent intent=getIntent();
        shopID=intent.getStringExtra("shopID");
        phone=intent.getStringExtra("phone");
        qrString=intent.getStringExtra("qrString");
        activity=intent.getStringExtra("activity");
        cancel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent i=new Intent(getApplicationContext(),CustomerRateShop.class);
                startActivity(i);
            }
        });
        forward.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                subject=esubject.getText().toString().trim();
                description=edesc.getText().toString().trim();
                if (subject.equals(""))
                {
                    esubject.setError("Subject cannot be enpty");
                    esubject.requestFocus();
                }
                else  if (description.equals(""))
                {
                    edesc.setError("description cannot be enpty");
                    edesc.requestFocus();
                }
                else
                {
                    CComplaint cComplaint=new CComplaint();
                    cComplaint.activity=activity;
                    cComplaint.description=description;
                    cComplaint.phone=phone;
                    cComplaint.qrString=qrString;
                    cComplaint.subject=subject;
                    ref= FirebaseDatabase.getInstance().getReference().child("ShopOwners").child(shopID).child("Complaint").child(qrString);
                    ref.setValue(cComplaint).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid)
                        {
                            CToast c=new CToast();
                            c.toast(getApplicationContext(),"Sucessfully forwarded",0);
                            esubject.setText("");
                            edesc.setText("");
                            Intent intent1=new Intent(getApplicationContext(),Account_Booking_History.class);
                            startActivity(intent1);
                        }
                    });
                }
            }
        });
    }
}
