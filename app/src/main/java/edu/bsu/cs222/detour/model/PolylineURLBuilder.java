package edu.bsu.cs222.detour.model;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by ToanTran on 11/13/15.
 */
public class PolylineURLBuilder {
    public static final class Builder {
        private LatLng start;
        private LatLng end;

        public Builder setStart(LatLng startA) {
            this.start = startA;
            return this;
        }

        public Builder setEnd(LatLng endB) {
            this.end = endB;
            return this;
        }

        public PolylineURLBuilder build() {
            return new PolylineURLBuilder(this);
        }
    }

    private LatLng start;
    private LatLng end;

    public PolylineURLBuilder(Builder build) {
        this.start = build.start;
        this.end = build.end;
    }

    private String getPointA() {
        String pointA = start.latitude + "," + start.longitude;
        String origin = "origin=" + pointA;
        return origin;
    }

    private String getPointB() {
        String pointB = end.latitude + "," + end.longitude;
        String destination = "&destination=" + pointB;
        return destination;
    }

    private String getServerKey() {
        String apiServerKey = "";
        return "&key=" + apiServerKey;
    }

    private String getParameter() {
        String pointA = getPointA();
        String pointB = getPointB();
        String apiKey = getServerKey();
        return pointA + pointB + apiKey;
    }

    private String getOutput() {
        return "json?";
    }

    public String createURL() {
        String output = getOutput();
        String parameter = getParameter();
        String URLString = "https://maps.googleapis.com/maps/api/directions/"
                + output
                + parameter;
        return URLString;
    }
}
