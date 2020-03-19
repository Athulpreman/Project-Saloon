package com.example.project1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class Account_Manage_Profile extends AppCompatActivity
{
    ImageView ProPic;
    EditText eEmail,eAddress1,eAddress2,eAddress3,eAddress4;
    String sEmail,sAddress1,sAddress2,sAddress3,sAddress4,sGender,MobNoo,status;
    String ssEmail,ssAddress1,ssAddress2,ssAddress3,ssAddress4,ssGender;
    Button SaveBtn;
    RadioGroup radioGroup;
    DatabaseReference reference,refe,refee;
    CManageProfile cManageProfile,cManageProfile1;
    Customer customer;
    RadioButton r1,r2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account__manage__profile);
        this.setTitle("Manage Profile");

        SharedPreferences sharedPreferences=getSharedPreferences("UserLogin",MODE_PRIVATE);
        MobNoo=sharedPreferences.getString("MobNo",null);

        ProPic=(ImageView)findViewById(R.id.mpProfilePic);
        eEmail=(EditText)findViewById(R.id.mpMail);
        eAddress1=(EditText)findViewById(R.id.mpAddress1);
        eAddress2=(EditText)findViewById(R.id.mpAddress2);
        eAddress3=(EditText)findViewById(R.id.mpAddress3);
        eAddress4=(EditText)findViewById(R.id.mpAddress4);
        SaveBtn=(Button)findViewById(R.id.mpSaveBtn);
        radioGroup=(RadioGroup)findViewById(R.id.mpGender);
        r1=(RadioButton)findViewById(R.id.male);
        r2=(RadioButton)findViewById(R.id.female);

        customer=new Customer();
        refe=FirebaseDatabase.getInstance().getReference().child("Customer");
        Query query=refe.orderByChild("mobileNum").equalTo(MobNoo);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapee:dataSnapshot.getChildren())
                {
                    customer=snapee.getValue(Customer.class);
                    if (!customer.profilePic.equals("0000000"))
                    {
                        Picasso.get().load(customer.profilePic).into(ProPic);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        cManageProfile1=new CManageProfile();
        refee=FirebaseDatabase.getInstance().getReference().child("Customer").child(MobNoo).child("MoreDetails");
        refee.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot1)
            {
               if (dataSnapshot1.exists())
               {
                   cManageProfile1=dataSnapshot1.getValue(CManageProfile.class);
                   ssAddress1=cManageProfile1.Address1;
                   ssAddress2=cManageProfile1.Address2;
                   ssAddress3=cManageProfile1.Address3;
                   ssAddress4=cManageProfile1.Address4;
                   ssEmail=cManageProfile1.Email;
                   ssGender=cManageProfile1.Gender;

                   eAddress1.setText(ssAddress1);
                   eAddress2.setText(ssAddress2);
                   eAddress3.setText(ssAddress3);
                   eAddress4.setText(ssAddress4);
                   eEmail.setText(ssEmail);
                   Toast.makeText(Account_Manage_Profile.this, ssGender, Toast.LENGTH_SHORT).show();
                   if (ssGender.equals("Male"))
                   {
                       r1.setChecked(true);
                       r2.setChecked(false);
                   }
                   else if (ssGender.equals("Female"))
                   {
                       r2.setChecked(true);
                       r1.setChecked(false);
                   }
               }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        cManageProfile=new CManageProfile();

        SaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                sEmail=eEmail.getText().toString();
                sAddress1=eAddress1.getText().toString();
                sAddress2=eAddress2.getText().toString();
                sAddress3=eAddress3.getText().toString();
                sAddress4=eAddress4.getText().toString();
                sGender = ((RadioButton)findViewById(radioGroup.getCheckedRadioButtonId())).getText().toString();

                if (sEmail.equals(""))
                {
                    eEmail.setError("Email is empty");
                }
                else if (sGender.equals(""))
                {
                    Toast.makeText(Account_Manage_Profile.this, "Select Gender", Toast.LENGTH_SHORT).show();
                }
                else if (sAddress1.equals(""))
                {
                    eAddress1.setError("1st Address is empty");
                }
                else if (sAddress2.equals(""))
                {
                    eAddress2.setError("1st Address is empty");
                }
                else if (sAddress3.equals(""))
                {
                    eAddress3.setError("1st Address is empty");
                }
                else if (sAddress4.equals(""))
                {
                    eAddress4.setError("1st Address is empty");
                }
                else
                {
                    cManageProfile.Email=sEmail;
                    cManageProfile.Gender=sGender;
                    cManageProfile.Address1=sAddress1;
                    cManageProfile.Address2=sAddress2;
                    cManageProfile.Address3=sAddress3;
                    cManageProfile.Address4=sAddress4;

                    reference= FirebaseDatabase.getInstance().getReference().child("Customer").child(MobNoo).child("MoreDetails");
                    reference.setValue(cManageProfile).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid)
                        {
                            Toast.makeText(Account_Manage_Profile.this, "Successfully Added", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
}
