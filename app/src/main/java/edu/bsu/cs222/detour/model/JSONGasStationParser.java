package edu.bsu.cs222.detour.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by s-knob on 11/18/15.
 */
public class JSONGasStationParser {
    private JSONArray jResultArray;
    private String GEOMETRY_TAG = "geometry";
    private String LOCATION_TAG = "location";
    private String LATITUDE_TAG = "lat";
    private String LONGITUDE_TAG = "lng";

    public ArrayList<Place> parse(JSONObject jObject) throws JSONException {
        jResultArray = jObject.getJSONArray("results");
        ArrayList<Place> places = new ArrayList<>();
        for (int i = 0; i < jResultArray.length(); i++) {
            Place currentPlace = new Place();
            currentPlace.setId((String) ((JSONObject) jResultArray
                    .get(i))
                    .get("id"));
            currentPlace.setLat((Double) ((JSONObject) ((JSONObject) ((JSONObject) jResultArray
                    .get(i))
                    .get(GEOMETRY_TAG))
                    .get(LOCATION_TAG))
                    .get(LATITUDE_TAG));
            currentPlace.setLng((Double) ((JSONObject) ((JSONObject) ((JSONObject) jResultArray
                    .get(i))
                    .get(GEOMETRY_TAG))
                    .get(LOCATION_TAG))
                    .get(LONGITUDE_TAG));
            currentPlace.setName((String) ((JSONObject) jResultArray
                    .get(i))
                    .get("name"));
            currentPlace.setPlace_id((String) ((JSONObject) jResultArray
                    .get(i))
                    .get("place_id"));
            currentPlace.setReference((String) ((JSONObject) jResultArray
                    .get(i))
                    .get("reference"));
            places.add(currentPlace);
        }
        return places;
    }
}
