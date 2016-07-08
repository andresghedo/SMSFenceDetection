package com.sm.app.alert.sghedoni.andrea.dev.service;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.sm.app.alert.sghedoni.andrea.dev.Constant;
import com.sm.app.alert.sghedoni.andrea.dev.Controller;
import com.sm.app.alert.sghedoni.andrea.dev.Fence;

import java.util.ArrayList;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by andrea on 01/07/16.
 */
public class BetterApproachService extends Service implements LocationListener, GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks {

    protected static final String TAG = "[DebApp]BetterAStrategy";

    /** indicates how to behave if the service is killed */
    int mStartMode;

    GoogleApiClient mGoogleApiClient;

    /** interface for clients that bind */
    IBinder mBinder;

    /** indicates whether onRebind should be used */
    boolean mAllowRebind;

    Location prevLocation;

    Location currentLocation;

    int prevLocationQueryInSecond = -1;



    /** Called when the service is being created. */
    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate");
        mGoogleApiClient = this.buildGoogleApiClient();
        mGoogleApiClient.connect();
        this.init();
    }

    /** The service is starting, due to a call to startService() */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand" + mGoogleApiClient.toString());
        return mStartMode;
    }

    /** A client is binding to the service with bindService() */
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    /** Called when all clients have unbound with unbindService() */
    @Override
    public boolean onUnbind(Intent intent) {
        return mAllowRebind;
    }

    /** Called when a client is binding to the service with bindService()*/
    @Override
    public void onRebind(Intent intent) {

    }

    /** Called when The service is no longer used and is being destroyed */
    @Override
    public void onDestroy() {

        LocationServices.FusedLocationApi.removeLocationUpdates(
                mGoogleApiClient, this);
        Toast.makeText(this, Constant.TOAST_TEXT_BETTER_APP_SERVICE_STOP, Toast.LENGTH_LONG).show();
        Log.d(TAG, "onDestroy");
    }

    protected synchronized GoogleApiClient buildGoogleApiClient() {
        Log.d(TAG, "buildGoogleApiClient dal Service");
        return new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.d(TAG, "Connessione APIs riuscita dal Service!");
        try {
            LocationRequest mLocationRequest = new LocationRequest();
            mLocationRequest.setInterval(40*1000); // 5 sec
            mLocationRequest.setFastestInterval(40*1000);  // 5 sec
            mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, (LocationListener) this);
        } catch (SecurityException securityException) {
            // Catch exception generated if the app does not use ACCESS_FINE_LOCATION permission.
            Log.e(TAG, "Invalid location permission. " + "NON HAI I PERMESSI PER LA FINE LOCATION ", securityException);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d(TAG, "Connessione APIs sospesa dal Service!");
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.d(TAG, "Connessione APIs fallita dal Service!");
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d(TAG, "LocationChanged: " + location.toString());
        this.getMatchedFences(location);
    }

    private ArrayList<Fence> getMatchedFences(Location current) {
        ArrayList<Fence> matchedFences = new ArrayList<Fence>();
        for (int i=0;i< Controller.fences.size();i++) {
            Location l = new Location(Controller.fences.get(i).getName());
            l.setLatitude(Controller.fences.get(i).getLat());
            l.setLongitude(Controller.fences.get(i).getLng());
            Calendar c = Calendar.getInstance();
            long timeInMilliseconds = c.getTimeInMillis();
            Log.d(TAG, "DISTANCE TO " + Controller.fences.get(i).getName() + " , meters: " + current.distanceTo(l) + " , Time: " + timeInMilliseconds + " , PROVIDED BY: " + l.getProvider());
        }
        return null;
    }

    private void init() {
        Controller.getInstance();
        Controller.setDbManager(getApplicationContext());
        Controller.resumeFencesFromDb();
    }

}