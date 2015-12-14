package edu.bsu.cs222.detour.model;

import com.google.android.gms.maps.model.LatLng;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ToanTran on 12/4/15.
 */
public class PolylinePointDecoder {
    private String encoded;
    private int index = 0;
    private int latitude = 0;
    private int longitude = 0;

    public PolylinePointDecoder(String encoded) {
        this.encoded = encoded;
    }

    public List<LatLng> decodePoly() {
        //function provided by Google, Inc.
        // http://bit.ly/1QF7S4Y
        List<LatLng> poly = new ArrayList<LatLng>();
        int stringLength = encoded.length();
        while (index < stringLength) {
            int decodedLatitude = getDecodedLatitude();
            latitude += decodedLatitude;
            int decodedLongitude = getDecodedLongitude();
            longitude += decodedLongitude;
            LatLng latLng = new LatLng((((double) latitude / 1E5)),
                    (((double) longitude / 1E5)));
            poly.add(latLng);
        }
        return poly;
    }

    private int getDecodedLatitude() {
        int initialValue = 0;
        int result = shiftLeft(initialValue);
        return ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
    }

    private int getDecodedLongitude() {
        int initialValue = 0;
        int result = shiftLeft(initialValue);
        return ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
    }

    private int shiftLeft(int result) {
        int b;
        int shift = 0;
        do {
            b = encoded.charAt(index++) - 63;
            result |= (b & 0x1f) << shift;
            shift += 5;
        } while (b >= 0x20);
        return result;
    }
}
