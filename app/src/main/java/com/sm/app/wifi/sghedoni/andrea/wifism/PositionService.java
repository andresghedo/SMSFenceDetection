package com.sm.app.wifi.sghedoni.andrea.wifism;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;
import android.support.design.widget.NavigationView;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

/**
 * Created by andrea on 01/07/16.
 */
public class PositionService extends Service implements LocationListener, GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks {

    protected static final String TAG = "[DebApp]PositionService";

    /** indicates how to behave if the service is killed */
    int mStartMode;

    GoogleApiClient mGoogleApiClient;

    float x=1;

    /** interface for clients that bind */
    IBinder mBinder;

    /** indicates whether onRebind should be used */
    boolean mAllowRebind;

    /** Called when the service is being created. */
    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate");
        mGoogleApiClient = this.buildGoogleApiClient();
        mGoogleApiClient.connect();
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
            mLocationRequest.setInterval(3000);
            mLocationRequest.setFastestInterval(3000);
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
    }
}