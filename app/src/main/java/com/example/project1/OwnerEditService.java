package com.example.project1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
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
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

public class OwnerEditService extends AppCompatActivity
{
    String shopID,Activity,sModelname,sPrice;
    TextView trating,tservice,tshopID;
    CircularImageView img;
    EditText eModelName,ePrice;
    Button cancel,save;
    DatabaseReference ref,refe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_edit_service);

        SharedPreferences sharedPreferences=getSharedPreferences("OwnerLogin",MODE_PRIVATE);
        shopID=sharedPreferences.getString("shopID",null);

        eModelName=(EditText)findViewById(R.id.oesModelName);
        ePrice=(EditText)findViewById(R.id.oesModelPrice);
        tshopID=(TextView)findViewById(R.id.oesShop);
        trating=(TextView)findViewById(R.id.oesRating);
        tservice=(TextView)findViewById(R.id.oesService);
        cancel=(Button)findViewById(R.id.oescance);
        save=(Button)findViewById(R.id.oessave);
        img=(CircularImageView)findViewById(R.id.oesimg);

        Intent intent=getIntent();
        Activity=intent.getStringExtra("Activity");
        Toast.makeText(this, Activity, Toast.LENGTH_SHORT).show();

        ref=FirebaseDatabase.getInstance().getReference().child("ShopOwners").child(shopID).child("Activity");
        Query query=ref.orderByChild("activity").equalTo(Activity);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                for (DataSnapshot snapshot:dataSnapshot.getChildren())
                {
                    OwnerAdd ownerAdd=new OwnerAdd();
                    ownerAdd=snapshot.getValue(OwnerAdd.class);
                    Picasso.get().load(ownerAdd.ModelImg).into(img);
                    trating.setText(String.valueOf(ownerAdd.rating));
                    tservice.setText(ownerAdd.Activity);ePrice.setText(String.valueOf(ownerAdd.Price));
                    eModelName.setText(ownerAdd.ModelName);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {

            }
        });

        cancel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent i=new Intent(getApplicationContext(),OwnerManageServices.class);
                startActivity(i);
            }
        });
        save.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                sPrice=ePrice.getText().toString().trim();
                sModelname=eModelName.getText().toString().trim();
                if (sModelname.equalsIgnoreCase(""))
                {
                    eModelName.setError("Enter a valid name");
                    eModelName.requestFocus();
                }
                else if (sPrice.equalsIgnoreCase(""))
                {
                    ePrice.setError("Enter price");
                    ePrice.requestFocus();
                }
                else if (sPrice.length()>4)
                {
                    ePrice.setError("price cannot exceeds 9999");
                    ePrice.requestFocus();
                }
                else
                {
                    refe=FirebaseDatabase.getInstance().getReference().child("ShopOwners").child(shopID).child("Activity");
                    Query query1=refe.orderByChild("activity").equalTo(Activity);
                    query1.addListenerForSingleValueEvent(new ValueEventListener()
                    {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                        {
                            for (final DataSnapshot snapshot:dataSnapshot.getChildren())
                            {
                                snapshot.getRef().child("modelName").setValue(sModelname).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid)
                                    {
                                        snapshot.getRef().child("price").setValue(sPrice).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid)
                                            {
                                                CToast c=new CToast();
                                                c.toast(getApplicationContext(),"Updated sucessfully",0);

                                                Intent i=new Intent(getApplicationContext(),OwnerManageServices.class);
                                                startActivity(i);
                                            }
                                        });
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
        });
    }
    @Override
    public void onBackPressed()
    {
        Intent i=new Intent(getApplicationContext(),OwnerPage.class);
        startActivity(i);
    }
}
