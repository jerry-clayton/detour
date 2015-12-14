package edu.bsu.cs222.detour.model;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by ToanTran on 12/4/15.
 */
public class JSONParser {
    //private PolylinePointDecoder pointDecoder=new PolylinePointDecoder();
    private List<List<HashMap<String, String>>> route = new ArrayList<List<HashMap<String, String>>>();
    private List<HashMap<String, String>> path;
    private JSONArray jLeg;
    private JSONArray jStep;

    public List<List<HashMap<String, String>>> parse(JSONObject jObject) {
        try {
            JSONArray jRoute = jObject.getJSONArray("routes");
            traverseRoute(jRoute);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return route;
    }

    private void traverseRoute(JSONArray route) {
        try {
            for (int i = 0; i < route.length(); i++) {
                jLeg = ((JSONObject) route
                        .get(i))
                        .getJSONArray("legs");
                path = new ArrayList<HashMap<String, String>>();
                traverseLeg();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void traverseLeg() {
        try {
            for (int j = 0; j < jLeg.length(); j++) {
                jStep = ((JSONObject) jLeg
                        .get(j))
                        .getJSONArray("steps");
                traverseStep();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void traverseStep() {
        try {
            for (int k = 0; k < jStep.length(); k++) {
                String polyline = "";
                polyline = (String) ((JSONObject) ((JSONObject) jStep
                        .get(k))
                        .get("polyline"))
                        .get("points");
                PolylinePointDecoder pointDecoder = new PolylinePointDecoder(polyline);
                List<LatLng> list = pointDecoder.decodePoly();
                traversePoint(list);
            }
            route.add(path);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void traversePoint(List<LatLng> list) {
        String LATITUDE_TAG = "lat";
        String LONGITUDE_TAG = "lng";
        for (int l = 0; l < list.size(); l++) {
            HashMap<String, String> hm = new HashMap<String, String>();
            hm.put(LATITUDE_TAG,
                    Double.toString(((LatLng) list
                            .get(l))
                            .latitude));
            hm.put(LONGITUDE_TAG,
                    Double.toString(((LatLng) list
                            .get(l))
                            .longitude));
            path.add(hm);
        }
    }
}
