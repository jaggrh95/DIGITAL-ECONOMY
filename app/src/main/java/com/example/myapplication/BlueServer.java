package com.example.myapplication;

import android.Manifest;
import android.app.Activity;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

public class BlueServer extends Service  {


    public Activity myAct;
    public LatLng Coordin;
    public String Name;




    @Override
    public void onCreate() {
        super.onCreate();

    }

    IBinder mBinder = new LocalBinder();
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }





    public class LocalBinder extends Binder {
        public BlueServer getServerInstance() {
            return BlueServer.this;
        }
    }


}
