package edu.bsu.cs222.detour.model;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import edu.bsu.cs222.detour.R;

/**
 * Created by s-knob on 11/18/15.
 */
public class MarkerBuilder {
    private MarkerOptions currentMarker = new MarkerOptions();
    private String url;

    public MarkerBuilder(String url) {
        this.url = url;
    }

    public ArrayList<MarkerOptions> buildMarkers() throws ExecutionException, InterruptedException {
        ConnectionTask connectionTask = new ConnectionTask();
        connectionTask.execute(url);
        String data = connectionTask.get();
        GasStationDataParserTask parserTask = new GasStationDataParserTask();
        parserTask.execute(data);
        ArrayList<Place> places = parserTask.get();

        for(Place currentPlace : places){
            System.out.println(currentPlace.getId());
        }
        System.out.println(url);
        return buildMarkersFromPlaces(places);
    }

    private ArrayList<MarkerOptions> buildMarkersFromPlaces(ArrayList<Place> places) {
        ArrayList<MarkerOptions> markers = new ArrayList<MarkerOptions>();
        for (Place currentPlace : places){
            MarkerOptions currentMarker = new MarkerOptions();
            currentMarker
                    .position(new LatLng(currentPlace.getLat(), currentPlace.getLng()))
                    .title(currentPlace.getName());

            markers.add(currentMarker);

        }
        configureMarkerOptions();
        return markers;
    }

    private void configureMarkerOptions() {
        currentMarker.icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_gas_station_marker));
    }
}
