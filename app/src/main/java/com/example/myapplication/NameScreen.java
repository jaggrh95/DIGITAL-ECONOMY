package com.example.myapplication;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class NameScreen extends AppCompatActivity {
    boolean mBounded;
    BlueServer myServer;
    EditText textfield;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name_screen);
        textfield=findViewById(R.id.editText);
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
    public void GoToNextScreen(View view) {
        Intent intentje = new Intent(this,ActivityVoorMapV2.class);
        startActivity(intentje);
        myServer.Name=textfield.getText().toString();


    }
}
