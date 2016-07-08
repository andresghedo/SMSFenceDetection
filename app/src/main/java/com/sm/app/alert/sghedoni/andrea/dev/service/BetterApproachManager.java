package com.sm.app.alert.sghedoni.andrea.dev.service;

import android.location.Location;

import com.google.android.gms.location.LocationRequest;
import com.sm.app.alert.sghedoni.andrea.dev.Constant;
import com.sm.app.alert.sghedoni.andrea.dev.Fence;

/**
 * Created by andrea on 06/07/16.
 */
public class BetterApproachManager {

    public WeightedRequest getBetterRequest(Location newLocation, Location oldLocation, int seconds, Fence fence) {

        float speedKmH = this.getSpeed(oldLocation, newLocation, seconds);
        float distanceFenceToNewLocation = newLocation.distanceTo(fence.getLocation());

        float evalDirection = this.getEvalDirection(oldLocation, newLocation, fence);
        float evalSpeed = this.getEvalSpeed(speedKmH);
        float evalDistance = this.getEvalDistance(distanceFenceToNewLocation);

        float totalEval = evalDirection + evalSpeed + evalDistance;

        int level = Math.round(totalEval);
        long updateTimeMs = Constant.UPDATE_REQUEST_MILLIS_25_MIN;
        int priority = LocationRequest.PRIORITY_LOW_POWER;

        switch (level){
            case (0):
                break;
            case (1):
                updateTimeMs = Constant.UPDATE_REQUEST_MILLIS_15_MIN;
                break;
            case (2):
                updateTimeMs = Constant.UPDATE_REQUEST_MILLIS_3_MIN;
                break;
            case (3):
                priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY;
                updateTimeMs = Constant.UPDATE_REQUEST_MILLIS_1_MIN;
                break;
            case (4):
                priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY;
                updateTimeMs = Constant.UPDATE_REQUEST_MILLIS_30_SEC;
                break;
            case (5):
                priority = LocationRequest.PRIORITY_HIGH_ACCURACY;
                updateTimeMs = Constant.UPDATE_REQUEST_MILLIS_5_SEC;
                break;
            default:
                break;
        }

        return new WeightedRequest(level, updateTimeMs, priority);
    }

    //if direction is right +1, else -1
    private float getEvalDirection(Location oldLocation, Location currentLocation, Fence fence) {
        if (oldLocation.distanceTo(fence.getLocation()) > currentLocation.distanceTo(fence.getLocation()))
            return 1;
        return -1;
    }

    // return km/h speed
    private float getSpeed(Location oldLocation, Location currentLocation, int seconds) {
        float meters = oldLocation.distanceTo(currentLocation);
        float speedMS = meters / seconds;
        float conversion = 3.6f;
        return (speedMS * conversion);
    }

    private float getEvalSpeed(float speedKmH) {

        float beta = 0.2f;
        if (speedKmH >= 130)
            return 5 * beta;
        else if ((speedKmH >= 100) && (speedKmH < 130))
            return 4 * beta;
        else if ((speedKmH >= 60) && (speedKmH < 100))
            return 3 * beta;
        else if ((speedKmH >= 20) && (speedKmH < 60))
            return 2 * beta;
        else if (speedKmH < 20)
            return 1 * beta;
        return 0;
    }

    private float getEvalDistance(float distanceM) {
        float alpha = 0.6f;
        if (distanceM < 8000)
            return 5 * alpha;
        else if ((distanceM >= 8000) && (distanceM < 30000))
            return 4 * alpha;
        else if ((distanceM >= 30000) && (distanceM < 50000))
            return 3 * alpha;
        else if ((distanceM >= 50000) && (distanceM < 100000))
            return 2 * alpha;
        else if (distanceM >= 100000)
            return 1 * alpha;
        return 0;
    }
}
