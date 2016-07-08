package com.sm.app.alert.sghedoni.andrea.dev;

import com.google.android.gms.location.LocationRequest;

/**
 * Created by andrea on 08/07/16.
 */
public class WeightedRequest {

    private Double eval;

    private LocationRequest locationRequest;

    public WeightedRequest() {}

    public WeightedRequest(LocationRequest lr, Double eval) {
        this.locationRequest = lr;
        this.eval = eval;
    }

    /* SETTER */
    public void setLocationRequest(LocationRequest lr) {
        this.locationRequest = lr;
    }

    public void setEvall(Double eval) {
        this.eval = eval;
    }

    /* GETTER */
    public LocationRequest getLocationRequest() {
        return this.locationRequest;
    }

    public Double getEvall() {
        return this.eval;
    }
}
