package com.sm.app.alert.sghedoni.andrea.dev.entity;

import android.location.Location;

/**
 * Class that map a fence.
 * @author Andrea Sghedoni
 */
public class Fence {

    /* Unique id */
    private int id;

    /* Fence tag name */
    private String name;

    /* Latitude of fence center */
    private Double lat;

    /* Longitude of fence center */
    private Double lng;

    /* Range of detection */
    private Float range;

    /* Address of fence center */
    private String address;

    /* City of fence center */
    private String city;

    /* Province of fence center */
    private String province;

    /* Fence active/disactive */
    private boolean active;

    /* is Current Position match with this fence?? TRUE or FALSE */
    private boolean match;

    /* Location of Fence */
    private Location location;

    /* Number for SMS forwording */
    private String number;

    /* Text of SMS to send */
    private String textSMS;

    /* Event to detect(ENETR, EXIT, ENT/EXIT). See SPINNER_EVENT_* Constants */
    int event;

    public Fence() {}

    public Fence(int id, String name, String address, String city, String province, Double lat, Double lng, Float range, boolean active, boolean match, String number, String textSMS, int event) {
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
        this.number = number;
        this.textSMS = textSMS;
        this.event = event;
    }

    /**********************************************************************************************
    *                                      SETTER                                                 *
    ***********************************************************************************************/

    public void setName(String name) {
        this.name = name;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public void setLng(Double lng) {
        this.lng = lng;
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

    public void setRange(Float range) { this.range = range; }

    public void setNumber(String number) { this.number = number; }

    public void setTextSMS(String textSMS) { this.textSMS = textSMS; }

    public void setEvent(int event) { this.event = event; }

    /*********************************************************************************************
    *                                      GETTER                                                *
    **********************************************************************************************/

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

    public String getNumber() { return this.number; }

    public String getTextSMS() { return this.textSMS; }

    public int getEvent() { return this.event; }
}
