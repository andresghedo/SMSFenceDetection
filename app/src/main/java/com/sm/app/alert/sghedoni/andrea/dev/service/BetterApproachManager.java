package com.sm.app.alert.sghedoni.andrea.dev.service;

import android.location.Location;

import com.google.android.gms.location.LocationRequest;
import com.sm.app.alert.sghedoni.andrea.dev.utils.Constant;
import com.sm.app.alert.sghedoni.andrea.dev.entity.Fence;

/**
 * Class that provide a WeightedRequest, based on current location and a particular Fence.
 * The strategy considers 3 parameters:
 *      .DISTANCE(fence range - current loc.)
 *      .DIRECTION
 *      .SPEED
 *
 * @author Andrea Sghedoni
 */
public class BetterApproachManager {

    protected static final String TAG = "[DebApp]BetterAManager";

    /* return a WeightedRequest, based on AUTO-ADAPTIVE EVALUATION, based on distance, direction, speed */
    public WeightedRequest getBetterRequest(Location newLocation, Location oldLocation, long deltaSecond, Fence fence) {

        float speedKmH = this.getSpeed(oldLocation, newLocation, deltaSecond);
        float distanceFenceToNewLocation = newLocation.distanceTo(fence.getLocation());

        float evalDirection = this.getEvalDirection(oldLocation, newLocation, fence);               // Direction evaluation
        float evalSpeed = this.getEvalSpeed(speedKmH);                                              // Speed evaluation
        float evalDistance = this.getEvalDistance(distanceFenceToNewLocation - fence.getRange());   // Distance evaluation

        float totalEval = evalDirection + evalSpeed + evalDistance;                                 // TOTAL Evaluation

        int level = Math.round(totalEval);
        long updateTimeMs = Constant.UPDATE_REQUEST_MILLIS_8_MIN;
        int priority = LocationRequest.PRIORITY_LOW_POWER;

        switch (level){
            case (0):                                                               // 8 min, LOW PRIORITY
                break;
            case (1):
                updateTimeMs = Constant.UPDATE_REQUEST_MILLIS_5_MIN;                // 5 min, LOW PRIORITY
                break;
            case (2):
                updateTimeMs = Constant.UPDATE_REQUEST_MILLIS_3_MIN;                // 3 min, LOW PRIORITY
                break;
            case (3):
                priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY;        // 1 min, BALANCED PRIORITY
                updateTimeMs = Constant.UPDATE_REQUEST_MILLIS_1_MIN;
                break;
            case (4):
                priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY;        // 30 sec, BALANCED PRIORITY
                updateTimeMs = Constant.UPDATE_REQUEST_MILLIS_30_SEC;
                break;
            case (5):
                priority = LocationRequest.PRIORITY_HIGH_ACCURACY;                  // 5 sec, HIGH PRIORITY
                updateTimeMs = Constant.UPDATE_REQUEST_MILLIS_5_SEC;
                break;
            default:
                break;
        }

        return new WeightedRequest(level, updateTimeMs, priority);
    }

    //if direction is right +0,5, else 0
    private float getEvalDirection(Location oldLocation, Location currentLocation, Fence fence) {
        if (oldLocation.distanceTo(fence.getLocation()) > currentLocation.distanceTo(fence.getLocation()))  // right direction
            return 5 * Constant.GAMMA;
return 0;                                                                                                   // wrong direction
    }

    // return speed km/h
    private float getSpeed(Location oldLocation, Location currentLocation, long deltaSecond) {
        float meters = oldLocation.distanceTo(currentLocation);
        float speedMS = meters / deltaSecond;
        float conversion = 3.6f;
        return (speedMS * conversion);
    }

    // return speed evaluation
    private float getEvalSpeed(float speedKmH) {

        float beta = Constant.BETA;
        if (speedKmH >= 130)                                        // > 130 km/h
            return 5 * beta;
        else if ((speedKmH >= 100) && (speedKmH < 130))             // 130 km/h - 100 km/h
            return 4 * beta;
        else if ((speedKmH >= 60) && (speedKmH < 100))              // 100 km/h - 60 km/h
            return 3 * beta;
        else if ((speedKmH >= 20) && (speedKmH < 60))               // 60 km/h - 20 km/h
            return 2 * beta;
        else if (speedKmH < 20)                                     // < 20 km/h
            return 1 * beta;
        return 0;
    }

    private float getEvalDistance(float distanceM) {
        float alpha = Constant.ALPHA;
        if (distanceM < 8000)                                       // < 8km
            return 5 * alpha;
        else if ((distanceM >= 8000) && (distanceM < 30000))        // 8km - 30km
            return 4 * alpha;
        else if ((distanceM >= 30000) && (distanceM < 50000))       // 30km - 50km
            return 3 * alpha;
        else if ((distanceM >= 50000) && (distanceM < 100000))      // 50km - 100km
            return 2 * alpha;
        else if ((distanceM >= 100000) && (distanceM < 200000))     // 100km - 200km
            return 1 * alpha;
        else if (distanceM >= 200000)                               // >= 200000km
            return 0;
        return 0;
    }
}
