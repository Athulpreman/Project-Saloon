package com.example.project1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CustomerRateShop extends AppCompatActivity
{
    RatingBar ratingBar;
    TextView ratingText;
    String subject,feedback,qrString,phone,shopID,activity,currentDate;
    int rating;
    EditText esubject,efeedback;
    Button submit;
    ProgressBar progressBar;
    TextView progressText,complaint;
    CRate cRate;
    int a,coum;
    double drating=0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_rate_shop);

        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy,MM,dd");
        currentDate = df.format(c.getTime());

        ratingBar=(RatingBar)findViewById(R.id.ratingbar);
        ratingText=(TextView)findViewById(R.id.ratingTextview);
        esubject=(EditText)findViewById(R.id.ratingsubject);
        efeedback=(EditText)findViewById(R.id.ratingfeedback);
        submit=(Button)findViewById(R.id.ratingSubmit);
        progressBar=(ProgressBar)findViewById(R.id.Progressba);
        progressText=(TextView)findViewById(R.id.ProgressbaText);
        complaint=(TextView)findViewById(R.id.customerGiveComplaint);

        cRate=new CRate();

        Intent intent=getIntent();
        shopID=intent.getStringExtra("shopID");
        phone=intent.getStringExtra("phone");
        qrString=intent.getStringExtra("qrString");
        activity=intent.getStringExtra("activity");

        ratingBar.setNumStars(5);
        ratingBar.setStepSize(1);
        ratingBar.setRating(1);
        ratingText.setText("Rating : "+ratingBar.getProgress());

        complaint.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent1=new Intent(getApplicationContext(),CustomerGiveComplaint.class);
                intent1.putExtra("shopID",shopID);
                intent1.putExtra("qrString",qrString);
                intent1.putExtra("activity",activity);
                intent1.putExtra("phone",phone);
                startActivity(intent1);
            }
        });

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener()
        {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser)
            {
                ratingText.setText("Rating : "+ratingBar.getProgress());
            }
        });
        submit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                subject=esubject.getText().toString().trim();
                feedback=efeedback.getText().toString().trim();
                if (subject.equals(""))
                {
                    esubject.setError("Subject cannot be empty");
                    esubject.requestFocus();
                }
                else if (feedback.equals(""))
                {
                    efeedback.setError("Feedback cannot be empty");
                    efeedback.requestFocus();
                }
                else
                {
                    progressText.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.VISIBLE);
                    rating=ratingBar.getProgress();

                    cRate.reating=rating;
                    cRate.feedback=feedback;
                    cRate.subject=subject;
                    cRate.qrString=qrString;
                    cRate.date=currentDate;
                    cRate.mobile=phone;

                    DatabaseReference reference;
                    reference= FirebaseDatabase.getInstance().getReference().child("ShopOwners").child(shopID).child("Activity").child(activity).child("Rating").child(qrString);
                    reference.setValue(cRate).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid)
                        {
                            DatabaseReference ref;
                            ref=FirebaseDatabase.getInstance().getReference().child("ShopOwners").child(shopID).child("Activity").child(activity).child("Rating");
                            ref.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                                {
                                    a=0;
                                    for (DataSnapshot snapshot:dataSnapshot.getChildren())
                                    {
                                        a++;
                                        CRate cRate=new CRate();
                                        cRate=snapshot.getValue(CRate.class);
                                        drating=drating+cRate.reating;
                                        if (a==dataSnapshot.getChildrenCount())
                                        {
                                            drating=drating/(dataSnapshot.getChildrenCount());
                                            DatabaseReference referencee;
                                            referencee= FirebaseDatabase.getInstance().getReference().child("ShopOwners").child(shopID).child("Activity");
                                            Query query=referencee.orderByChild("activity").equalTo(activity);
                                            query.addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                                                {
                                                    for (DataSnapshot snapshot1:dataSnapshot.getChildren())
                                                    {
                                                        snapshot1.getRef().child("rating").setValue(drating).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                            @Override
                                                            public void onSuccess(Void aVoid)
                                                            {
                                                                CToast cToast=new CToast();
                                                                cToast.toast(getApplicationContext(),"Sucessfully submitted rating",0);
                                                                progressText.setVisibility(View.INVISIBLE);
                                                                progressBar.setVisibility(View.INVISIBLE);

                                                                Intent intent1=new Intent(getApplicationContext(),CustomerSignedIn1.class);
                                                                startActivity(intent1);
                                                            }
                                                        });
                                                    }
                                                }
                                                @Override
                                                public void onCancelled(@NonNull DatabaseError databaseError)
                                                {

                                                }
                                            });
                                        }
                                    }
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
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(getApplicationContext(),Account_Booking_History.class);
        startActivity(intent);
    }
}
