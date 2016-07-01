package com.sm.app.wifi.sghedoni.andrea.wifism;

import android.app.IntentService;
import android.content.Intent;
import android.location.Location;
import android.util.Log;
import com.google.android.gms.location.LocationResult;


/**
 * Created by andrea on 01/07/16.
 */
public class PositionIntentService extends IntentService {

    protected static final String TAG = "[DebApp]PositionIS";

    protected static final String NameService = "AlertAppPosIS";

    /**
     * This constructor is required, and calls the super IntentService(String)
     * constructor with the name for a worker thread.
     */
    public PositionIntentService() {
        // Use the TAG to name the worker thread.
        super(NameService);
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    /**
     * Handles incoming intents.
     * @param intent sent by Location Services. This Intent is provided to Location
     *               Services (inside a PendingIntent) when addGeofences() is called.
     */
    @Override
    protected void onHandleIntent(Intent intent) {

        Log.d(TAG, "onHandleIntent");

        Log.d(TAG, String.valueOf(intent));
        if (LocationResult.hasResult(intent)) {
            LocationResult locationResult = LocationResult.extractResult(intent);
            Location location = locationResult.getLastLocation();
            Log.d(TAG, location.toString());
            if (location != null) {
                Log.d(TAG, "POSIZIONE: " + location.getAccuracy() + " LAT: " + location.getLatitude() + " LONG: " + location.getLongitude());
            }
        }
    }

}