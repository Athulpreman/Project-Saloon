package com.example.project1;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class RegisterPage extends AppCompatActivity
{
    String sOwnerName,sShopName,sShopID,sOwnerMobile,sAddress,sEmployeeName,sEmployeeMobile,sPassword,sImage1="",sImage2="",sImage3="";
    EditText eOwnerName,eShopName,eShopID,eOwnerMobile,eAddress,eEmployeeName,eEmployeeMobile,ePassword;
    ImageView iimg1,iimg2,iimg3;
    Button signup;
    TextView gotologin;
    DatabaseReference reference;
    Owner owner;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);

        eOwnerName=(EditText)findViewById(R.id.registerFullName);
        eShopName=(EditText)findViewById(R.id.RegisterShopName);
        eShopID=(EditText)findViewById(R.id.registerShopID);
        eOwnerMobile=(EditText)findViewById(R.id.registerMobileNo);
        eAddress=(EditText)findViewById(R.id.registerAddress);
        eEmployeeName=(EditText)findViewById(R.id.registerEmployeeName);
        eEmployeeMobile=(EditText)findViewById(R.id.registerEmployeeContactNumber);
        ePassword=(EditText)findViewById(R.id.registerPassword);
        gotologin=(TextView) findViewById(R.id.registerGoToLogin);

        iimg1=(ImageView)findViewById(R.id.img1);
        iimg2=(ImageView)findViewById(R.id.img2);
        iimg3=(ImageView)findViewById(R.id.img3);

        signup=(Button)findViewById(R.id.registerSignupButton);

        reference= FirebaseDatabase.getInstance().getReference().child("Shop_Owners");
        owner=new Owner();

        gotologin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent=new Intent(getApplicationContext(),LoginPage.class);
                startActivity(intent);
            }
        });
        signup.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                sOwnerName=eOwnerName.getText().toString();
                sShopName=eShopName.getText().toString();
                sShopID=eShopID.getText().toString();
                sOwnerMobile=eOwnerMobile.getText().toString();
                sAddress=eAddress.getText().toString();
                sEmployeeName=eEmployeeName.getText().toString();
                sEmployeeMobile=eEmployeeMobile.getText().toString();
                sPassword=ePassword.getText().toString();

                if(sOwnerName.isEmpty())
                {
                    eOwnerName.setError("Owner Name Required");
                    eOwnerName.requestFocus();
                }
                else if(sShopName.isEmpty())
                {
                    eShopName.setError("Shop Name Required");
                    eShopName.requestFocus();
                }
                else if(sShopID.isEmpty())
                {
                    eShopID.setError("Shop ID Required");
                    eShopID.requestFocus();
                }
                else if(sOwnerMobile.isEmpty()||sOwnerMobile.length()<10)
                {
                    eOwnerMobile.setError("Enter correct mobile number");
                    eOwnerMobile.requestFocus();
                }
                else if(sAddress.isEmpty())
                {
                    eAddress.setError("Shop Address Required");
                    eAddress.requestFocus();
                }
                else if (sEmployeeName.isEmpty())
                {
                    eEmployeeName.setError("Employee Name Required");
                    eEmployeeName.requestFocus();
                }
                else if (sEmployeeMobile.isEmpty())
                {
                    eEmployeeMobile.setError("Employee Mobile Number Required");
                    eEmployeeMobile.requestFocus();
                }
                else if (sPassword.isEmpty())
                {
                    ePassword.setError("Passwor Required");
                    ePassword.requestFocus();
                }
                else if (sImage1.isEmpty())
                {
                    Toast.makeText(getApplicationContext(), "Select 1st Image", Toast.LENGTH_SHORT).show();
                }
                else if (sImage2.isEmpty())
                {
                    Toast.makeText(getApplicationContext(), "Select 2nd Image", Toast.LENGTH_SHORT).show();
                }
                else if (sImage3.isEmpty())
                {
                    Toast.makeText(getApplicationContext(), "Select 3rd Image", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Query query=reference.orderByChild("shopID").equalTo(sShopID);
                    query.addListenerForSingleValueEvent(new ValueEventListener()
                    {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                        {
                            if (dataSnapshot.exists())
                            {
                                eShopID.setError("Shop already Registered Try Sign in");
                            }
                            else
                            {
                                owner.setOwnerName(sOwnerName);
                                owner.setShopName(sShopName);
                                owner.setShopID(sShopID);
                                owner.setOwnerMobile(sOwnerMobile);
                                owner.setAddress(sAddress);
                                owner.setEmployeeName(sEmployeeName);
                                owner.setEmployeeMobile(sEmployeeMobile);
                                owner.setPassword(sPassword);
                                owner.setImage1(sImage1);
                                owner.setImage2(sImage2);
                                owner.setImage3(sImage3);

                                reference.push().setValue(owner).addOnSuccessListener(new OnSuccessListener<Void>()
                                {
                                    @Override
                                    public void onSuccess(Void aVoid)
                                    {
                                        Toast.makeText(getApplicationContext(), "Shop Registered Successfuly", Toast.LENGTH_SHORT).show();

                                        eOwnerName.setText("");
                                        eShopName.setText("");
                                        eShopID.setText("");
                                        eOwnerMobile.setText("");
                                        eAddress.setText("");
                                        eEmployeeName.setText("");
                                        eEmployeeMobile.setText("");
                                        ePassword.setText("");

                                        iimg1.setImageResource(R.drawable.hair);
                                        iimg2.setImageResource(R.drawable.hair);
                                        iimg3.setImageResource(R.drawable.hair);
                                    }
                                });
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }
            }
        });
        iimg1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent i=new Intent(Intent.ACTION_GET_CONTENT);
                i.setType("image/*");
                startActivityForResult(i,1);
            }
        });
        iimg2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent i=new Intent(Intent.ACTION_GET_CONTENT);
                i.setType("image/*");
                startActivityForResult(i,2);
            }
        });
        iimg3.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent i=new Intent(Intent.ACTION_GET_CONTENT);
                i.setType("image/*");
                startActivityForResult(i,3);
            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1)
        {
            if (resultCode == RESULT_OK)
            {
                Uri fileuri = data.getData();

                StorageReference folder = FirebaseStorage.getInstance().getReference().child("ShopImage");

                String timestamp = String.valueOf(System.currentTimeMillis());

                final StorageReference filename = folder.child(timestamp + fileuri.getLastPathSegment());

                filename.putFile(fileuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        filename.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {

                                sImage1 = String.valueOf(uri);
                                Picasso.with(getApplicationContext()).load(sImage1).into(iimg1);
                            }
                        });

                    }
                });
            }

        }
        if (requestCode == 2)
        {
            if (resultCode == RESULT_OK)
            {
                Uri fileuri = data.getData();

                StorageReference folder = FirebaseStorage.getInstance().getReference().child("ShopImage");

                String timestamp = String.valueOf(System.currentTimeMillis());

                final StorageReference filename = folder.child(timestamp + fileuri.getLastPathSegment());

                filename.putFile(fileuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        filename.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {

                                sImage2 = String.valueOf(uri);
                                Picasso.with(getApplicationContext()).load(sImage2).into(iimg2);
                            }
                        });

                    }
                });
            }

        }
        if (requestCode == 3)
        {
            if (resultCode == RESULT_OK)
            {
                Uri fileuri = data.getData();

                StorageReference folder = FirebaseStorage.getInstance().getReference().child("ShopImage");

                String timestamp = String.valueOf(System.currentTimeMillis());

                final StorageReference filename = folder.child(timestamp + fileuri.getLastPathSegment());

                filename.putFile(fileuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        filename.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {

                                sImage3 = String.valueOf(uri);
                                Picasso.with(getApplicationContext()).load(sImage3).into(iimg3);
                            }
                        });

                    }
                });
            }

        }
    }
}
