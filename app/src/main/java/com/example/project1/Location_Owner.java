package com.example.project1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Location_Owner extends AppCompatActivity
{
    int PERMISSION_ID = 44;
    String lati="",longi="",shopID;
    Button currentLocation;
    FusedLocationProviderClient mFusedLocationClient;
    EditText latitude,longitude;
    Button Show,VerifyFromMap,AddLocation;
    CheckBox checkBox;
    CLocation cLocation;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location__owner);

        SharedPreferences sharedPreference=getSharedPreferences("OwnerLogin",MODE_PRIVATE);
        shopID=sharedPreference.getString("shopID",null);

        reference= FirebaseDatabase.getInstance().getReference().child("ShopOwners").child(shopID).child("Location");

        currentLocation=(Button)findViewById(R.id.currentLocation);
        VerifyFromMap=(Button)findViewById(R.id.verifyUsingMap);
        AddLocation=(Button)findViewById(R.id.addLocation);
        latitude=(EditText)findViewById(R.id.latitude);
        longitude=(EditText)findViewById(R.id.longitude);
        checkBox=(CheckBox) findViewById(R.id.check1);

        cLocation=new CLocation();

        currentLocation.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getApplicationContext());

                getLastLocation();
            }
        });
        AddLocation.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (!checkBox.isChecked())
                {
                    Toast.makeText(Location_Owner.this, "Check the checkbox for verification", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    lati=(latitude.getText().toString());
                    longi=(longitude.getText().toString());
                    if (!longi.isEmpty()&&!lati.isEmpty())
                    {
                        cLocation.latitude=lati;
                        cLocation.longitude=longi;
                        reference.setValue(cLocation).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid)
                            {
                                Toast.makeText(Location_Owner.this, "Location added sucessfully", Toast.LENGTH_SHORT).show();
                                onBackPressed();
                            }
                        });
                    }
                    else
                    {
                        Toast.makeText(Location_Owner.this, "Try show current location", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        VerifyFromMap.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                lati=(latitude.getText().toString());
                longi=(longitude.getText().toString());
               if (!lati.equals("")&&!longi.equals(""))
               {
                   Intent intent=new Intent(getApplicationContext(), com.example.project1.VerifyFromMap.class);
                   intent.putExtra("longi",longitude.getText().toString());
                   intent.putExtra("lati",latitude.getText().toString());
                   startActivity(intent);
               }
               else
               {
                   Toast.makeText(Location_Owner.this, "Click show button", Toast.LENGTH_SHORT).show();
               }
            }
        });
    }

    @SuppressLint("MissingPermission")
    private void getLastLocation(){
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                mFusedLocationClient.getLastLocation().addOnCompleteListener(
                        new OnCompleteListener<Location>() {
                            @Override
                            public void onComplete(@NonNull Task<Location> task) {
                                Location location = task.getResult();
                                if (location == null)
                                {
                                    requestNewLocationData();
                                }
                                else
                                {
                                    latitude.setText(String.valueOf( location.getLatitude()));
                                    longitude.setText(String.valueOf(location.getLongitude()));
                                }
                            }
                        }
                );
            }
            else
            {
                Toast.makeText(this, "Turn on location", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        }
        else
        {
            requestPermissions();
        }
    }
    @SuppressLint("MissingPermission")
    private void requestNewLocationData(){

        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(0);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setNumUpdates(1);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mFusedLocationClient.requestLocationUpdates(
                mLocationRequest, mLocationCallback,
                Looper.myLooper()
        );

    }

    private LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult)
        {
            Location mLastLocation = locationResult.getLastLocation();

            latitude.setText(String.valueOf( mLastLocation.getLatitude()));
            longitude.setText(String.valueOf(mLastLocation.getLongitude()));
        }
    };

    private boolean checkPermissions() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        return false;
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(
                this,
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                PERMISSION_ID
        );
    }

    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
                LocationManager.NETWORK_PROVIDER
        );
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_ID) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            }
        }
    }

   /* @Override
    public void onResume(){
        super.onResume();
        if (checkPermissions()) {
            getLastLocation();
        }

    }*/

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
