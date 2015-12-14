package edu.bsu.cs222.detour.model;

import android.graphics.Color;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by ToanTran on 11/15/15.
 */
public class PolylineBuilder {
    private List<LatLng> latLngPointList = new ArrayList<>();
    private PolylineOptions polyline = new PolylineOptions();
    private String buildFromURL;

    public PolylineBuilder(String URL) {
        this.buildFromURL = URL;
    }

    public PolylineOptions buildPolyline() throws ExecutionException, InterruptedException {
        ConnectionTask connectionTask = new ConnectionTask();
        connectionTask.execute(buildFromURL);
        String data = connectionTask.get();
        ParserTask parserTask = new ParserTask();
        parserTask.execute(data);
        UnparsedPolyline unparsedPolyline = new UnparsedPolyline(parserTask.get());
        PolylineOptions parsedPolyline = parse(unparsedPolyline);
        return parsedPolyline;
    }

    private PolylineOptions parse(UnparsedPolyline unparsedPolyline) {
        for (int i = 0; i < unparsedPolyline.getRawData().size(); i++) {
            List<HashMap<String, String>> path = unparsedPolyline.getRawData().get(i);
            convertPointsToLatLng(path);
        }
        polyline.addAll(latLngPointList);
        configurePolylineOptions();
        return polyline;
    }

    private void convertPointsToLatLng(List<HashMap<String, String>> path) {
        for (int j = 0; j < path.size(); j++) {
            HashMap<String, String> point = path.get(j);
            double lat = Double.parseDouble(point.get("lat"));
            double lng = Double.parseDouble(point.get("lng"));
            LatLng position = new LatLng(lat, lng);
            latLngPointList.add(position);
        }
    }

    private void configurePolylineOptions() {
        int polylineWidth = 20;
        int redLevel = 12;
        int blueLevel = 208;
        int greenLevel = 232;
        polyline.width(polylineWidth);
        polyline.color(Color.rgb(redLevel, blueLevel, greenLevel));
    }
}
