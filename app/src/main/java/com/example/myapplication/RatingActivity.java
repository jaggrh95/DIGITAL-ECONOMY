package com.example.myapplication;

import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

public class RatingActivity extends AppCompatActivity {

    LatLng coordin;
    boolean mBounded;
    BlueServer myServer;
    int Rating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);

        Button btnSubmit = (Button)findViewById(R.id.btnSubmit);
        final TextView txtRating = (TextView)findViewById(R.id.txtRating);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int rating = Integer.parseInt(txtRating.getText().toString());
                if (rating < 0 || rating > 10){
                    Toast.makeText(getApplicationContext(), "Rating has to be between 1 and 10", Toast.LENGTH_SHORT).show();
                } else {
                    Rating = rating;
                }
            }
        });
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
            myServer.Rating = Rating;

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
}