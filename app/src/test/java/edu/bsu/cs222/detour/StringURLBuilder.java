package edu.bsu.cs222.detour;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by s-knob on 11/15/15.
 */
public class StringURLBuilder {
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

        public StringURLBuilder build() {
            return new StringURLBuilder(this);
        }
    }

    private LatLng start;
    private LatLng end;

    public StringURLBuilder(Builder build) {
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
        return "&key=AIzaSyDivGp-JdNg43pVpnGwwxBUabYI6XkY2NA";
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
        String URLString = "https://maps.googleapis.com/maps/api/directions/" + output + parameter;
        return URLString;
    }
}

