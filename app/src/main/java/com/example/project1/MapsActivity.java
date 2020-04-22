package com.example.project1;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    String longi="",lati="";
    long longitude,latitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        Intent intent=getIntent();
        longi=intent.getStringExtra("longi");
        lati=intent.getStringExtra("lati");

        longitude=Integer.parseInt(longi.replaceAll("[\\D]",""));
        latitude=Integer.parseInt(lati.replaceAll("[\\D]",""));

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng sydney = new LatLng(-longitude, latitude);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Shop"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}
