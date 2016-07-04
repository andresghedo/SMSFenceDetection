package com.sm.app.alert.sghedoni.andrea.dev;

/**
 * Created by andrea on 02/07/16.
 */
public class Fence {

    private int id;

    private String name;

    private Double lat;

    private Double lng;

    private Double range;

    private String address;

    private String city;

    private String province;

    private boolean active;

    public Fence() {}

    public Fence(int id, String name, String address, String city,String province, Double lat, Double lng, Double range, boolean active) {
        this.id = id;
        this.name = name;
        this.lat = lat;
        this.lng = lng;
        this.range = range;
        this.address = address;
        this.city = city;
        this.province = province;
        this.active = active;
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

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public void setRange(Double range) {
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

    public Double getRange() {
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

}
