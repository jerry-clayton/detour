package edu.bsu.cs222.detour;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by ToanTran on 12/6/15.
 */
public class GasURLBuilder {
    private LatLng currentLatLng;

    public GasURLBuilder(LatLng currentLatLng) {
        this.currentLatLng = currentLatLng;
    }

    public String buildURL(){
        String output = getOutput();
        String params = getParams();
        return "https://maps.googleapis.com/maps/api/place/nearbysearch/" + output + params;
    }

    private String getOutput() {
        return "json?";
    }

    private String getParams() {
        String location = getLocation();
        String radius = getRadius();
        String type = getType();
        String key = getServerKey();
        return location + radius + type + key;
    }

    private String getLocation(){
        return "location=" + currentLatLng.latitude +","+ currentLatLng.longitude;
    }

    private String getRadius(){
        int meters=6000;
        return "&radius=" + meters;
    }

    private String getType(){
        String placeType= "gas_station";
        return "&types=" + placeType;
    }

    private String getServerKey(){
        String apiServerKey="AIzaSyDivGp-JdNg43pVpnGwwxBUabYI6XkY2NA";;
        return "&key=" + apiServerKey;
    }
}
