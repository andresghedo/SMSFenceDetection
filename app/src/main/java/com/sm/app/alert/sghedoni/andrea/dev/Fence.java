package com.sm.app.alert.sghedoni.andrea.dev;

import android.location.Location;

/**
 * Created by andrea on 02/07/16.
 */
public class Fence {

    private int id;

    private String name;

    private Double lat;

    private Double lng;

    private Float range;

    private String address;

    private String city;

    private String province;

    private boolean active;

    private boolean match;

    private Location location;

    public Fence() {}

    public Fence(int id, String name, String address, String city, String province, Double lat, Double lng, Float range, boolean active, boolean match) {
        this.id = id;
        this.name = name;
        this.lat = lat;
        this.lng = lng;
        this.range = range;
        this.address = address;
        this.city = city;
        this.province = province;
        this.active = active;
        this.match = match;
        this.location = new Location(this.name);
        this.location.setLongitude(this.lng);
        this.location.setLatitude(this.lat);
    }

    /*
    * ************** SETTER **************
    * */

    public void setName(String name) {
        this.name = name;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public void setLng(Float range) {
        this.range = range;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setProvince(String province) {
        this.province = province ;
    }

    public void setActive(boolean flag) { this.active = flag; }

    public void setMatch(boolean match) { this.match = match; }

    /*
    * ************** GETTER **************
    * */

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public Double getLat() {
        return this.lat;
    }

    public Double getLng() {
        return this.lng;
    }

    public Float getRange() {
        return this.range;
    }

    public String getAddress() {
        return this.address;
    }

    public String getCity() {
        return this.city;
    }

    public String getProvince() {
        return this.province;
    }

    public boolean isActive() { return this.active; }

    public boolean isMatch() { return this.match; }

    public Location getLocation() { return this.location; }

    public boolean isInRange(Location l) {

        if (l.distanceTo(this.location) <= this.range)
            return true;
        return false;
    }
}
