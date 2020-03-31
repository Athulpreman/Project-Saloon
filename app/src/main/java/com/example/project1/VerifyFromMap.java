package com.example.project1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class VerifyFromMap extends FragmentActivity implements OnMapReadyCallback
{
    GoogleMap mMap;
    String longi,lati;
    int longitude,latitude;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_from_map);

        Intent intent=getIntent();
        longi=intent.getStringExtra("longi");
        lati=intent.getStringExtra("lati");

        longitude=Integer.parseInt(longi.replaceAll("[\\D]",""));
        latitude=Integer.parseInt(lati.replaceAll("[\\D]",""));

        SupportMapFragment mapFragment=(SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        mMap=googleMap;
        LatLng shop=new LatLng(latitude,longitude);
        MarkerOptions markerOptions=new MarkerOptions().position(shop).title("APs shop").snippet("Welcome to shop");
        mMap.addMarker(markerOptions);
    }
}
