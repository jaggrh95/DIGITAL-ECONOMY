package com.example.myapplication;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMyLocationButtonClickListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback, OnMyLocationButtonClickListener {
    public static final String MARKER_MESSAGE =
            "com.example.android.twoactivities.extra.long";
    public GoogleMap mMap;
    double Longitude;
    double Lattitude;

    Intent intent1;
    private static final int REQUEST_LOCATION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        Intent intent = getIntent();

        String holderlong = intent.getStringExtra(ActivityVoorMapV2.EXTRA_MESSAGE);
        String holderlat = intent.getStringExtra(ActivityVoorMapV2.EXTRA_MESSAGE1);
        Log.i("inmaps",""+holderlat + " en " + holderlong);
        intent1 = new Intent(this,MainActivity.class);
        Longitude =  Double.parseDouble(holderlong);
        Lattitude =  Double.parseDouble(holderlat);

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setOnMyLocationButtonClickListener(this);
        //mMap.setOnMyLocationClickListener(this);
        enableMyLocation();

        // Add a marker in Sydney and move the camera

        LatLng UserPosition = new LatLng(Lattitude, Longitude);

        LatLng StarBux = new LatLng(51.217222,4.421111);
        LatLng Pizzahut = new LatLng(51.217980,4.417590);

        //mMap.addMarker(new MarkerOptions().position(sydney).title("MyLocation"));
        mMap.addMarker(new MarkerOptions().position(StarBux).title("Starbucks"));
        mMap.addMarker(new MarkerOptions().position(Pizzahut).title("Pizzahut"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(UserPosition, 15.0f));
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(final Marker marker) {
                intent1.putExtra(MARKER_MESSAGE,marker.getTitle());
                startActivity(intent1);
                return false;
            }
        });
    }

    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission to access the location is missing.
            ActivityCompat.requestPermissions(MapActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        } else if (mMap != null) {
            // Access to the location has been granted to the app.
            mMap.setMyLocationEnabled(true);
        }
    }

    @Override
    public boolean onMyLocationButtonClick() {
        Toast.makeText(this, "MyLocation button clicked", Toast.LENGTH_SHORT).show();
        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).
        return false;
    }
}









