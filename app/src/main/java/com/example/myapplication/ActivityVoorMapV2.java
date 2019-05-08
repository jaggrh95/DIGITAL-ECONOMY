package com.example.myapplication;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.IBinder;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

public class ActivityVoorMapV2 extends AppCompatActivity {

    public static final String EXTRA_MESSAGE =
            "com.example.android.twoactivities.extra.long";
    public static final String EXTRA_MESSAGE1 =
            "com.example.android.twoactivities.extra.latt";
    private static final int REQUEST_LOCATION = 1;
    Button button;

    LocationManager locationManager;
    String lattitude,longitude,location;
    boolean mBounded;
    BlueServer myServer;
    TextView textView;
    Intent intent; @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voor_map);

        ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);

        intent = new Intent(this, MapActivity.class);
        textView=findViewById(R.id.textView);

    }
    @Override
    protected void onStart() {
        super.onStart();

        Intent mIntent = new Intent(this, BlueServer.class);
        bindService(mIntent, mConnection, BIND_AUTO_CREATE);
    };

    ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceDisconnected(ComponentName name) {

            mBounded = false;
            myServer = null;
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

            mBounded = true;
            BlueServer.LocalBinder mLocalBinder = (BlueServer.LocalBinder)service;
            myServer = mLocalBinder.getServerInstance();
            textView.setText("Welkom "+myServer.Name);


        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mBounded) {
            unbindService(mConnection);
            mBounded = false;
        }
    };

    public void onClick(View view) {

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();

        } else if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            getLocation();
        }
    }

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(ActivityVoorMapV2.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                (ActivityVoorMapV2.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {



        } else {
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            Location location1 = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            Location location2 = locationManager.getLastKnownLocation(LocationManager. PASSIVE_PROVIDER);

            if (location != null) {
                double latti = location.getLatitude();
                double longi = location.getLongitude();
                lattitude = String.valueOf(latti);
                longitude = String.valueOf(longi);
                intent.putExtra(EXTRA_MESSAGE,longitude);
                intent.putExtra(EXTRA_MESSAGE1,lattitude);



            } else  if (location1 != null) {
                double latti = location1.getLatitude();
                double longi = location1.getLongitude();
                lattitude = String.valueOf(latti);
                longitude = String.valueOf(longi);
                intent.putExtra(EXTRA_MESSAGE,longitude);
                intent.putExtra(EXTRA_MESSAGE1,lattitude);



            } else  if (location2 != null) {
                double latti = location2.getLatitude();
                double longi = location2.getLongitude();
                lattitude = String.valueOf(latti);
                longitude = String.valueOf(longi);
                intent.putExtra(EXTRA_MESSAGE,longitude);
                intent.putExtra(EXTRA_MESSAGE1,lattitude);


            }else{

                Toast.makeText(this,"Unable to Trace your location",Toast.LENGTH_SHORT).show();

            }
            if(longitude!=null && lattitude != null)
            {
                startActivity(intent);
            }
        }
    }

    protected void buildAlertMessageNoGps() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Please Turn ON your GPS Connection")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    public void launchsecondAct(View view) {
    }

    public void OnClickGeo(View view) throws IOException {
        EditText ET = findViewById(R.id.Input);
        location = ET.getText().toString();
        Geocoder gc = new Geocoder(this);
        List<Address> list = gc.getFromLocationName(location,1);
        Address add = list.get(0);
        String Locality = add.getLocality();
        Toast.makeText(this,Locality,Toast.LENGTH_LONG).show();
        String lattitude = String.valueOf(add.getLatitude());
        String longitude = String.valueOf(add.getLongitude());
        Log.d("testjeeeee","test");
        intent.putExtra(EXTRA_MESSAGE,longitude);
        intent.putExtra(EXTRA_MESSAGE1,lattitude);
        startActivity(intent);
    }


}
