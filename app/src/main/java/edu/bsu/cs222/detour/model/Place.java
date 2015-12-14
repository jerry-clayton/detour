package edu.bsu.cs222.detour.model;

/**
 * Created by s-knob on 11/18/15.
 */
public class Place {
    public Place() {
    }


    private String id;
    private String place_id;
    private String name;
    private String reference;
    private Double lat;
    private Double lng;

    public void setId(String id){
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setPlace_id(String place_id) {
        this.place_id = place_id;
    }

    public String getPlace_id() {
        return place_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }
}
