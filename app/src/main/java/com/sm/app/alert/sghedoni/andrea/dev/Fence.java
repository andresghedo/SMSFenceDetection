package com.sm.app.alert.sghedoni.andrea.dev;

/**
 * Created by andrea on 02/07/16.
 */
public class Fence {

    private int id;

    private String name;

    private String lat;

    private String lng;

    private String range;

    private String address;

    public Fence() {}

    public Fence(int id, String name, String lat, String lng, String range) {
        this.id = id;
        this.name = name;
        this.lat = lat;
        this.lng = lng;
        this.range =range;
    }

    public int getId() {
        return this.id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLat() {
        return this.lat;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getLng() {
        return this.lng;
    }

    public void setRange(String range) {
        this.range = range;
    }

    public String getRange() {
        return this.range;
    }

    public String getAddress() { return this.address; }

    public void setAddress(String address) { this.address = address; }

}
