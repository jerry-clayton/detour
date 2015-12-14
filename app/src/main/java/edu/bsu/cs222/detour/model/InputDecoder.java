package edu.bsu.cs222.detour.model;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import com.google.android.gms.maps.model.LatLng;
import java.io.IOException;
import java.util.List;

/**
 * Created by ToanTran on 11/11/15.
 */
public class InputDecoder {
    public static final class Builder {
        private String userInput;
        private Context mapsActivityContext;

        public Builder setUserInput(String input) {
            this.userInput = input;
            return this;
        }

        public Builder setContext(Context activityContext) {
            this.mapsActivityContext = activityContext;
            return this;
        }

        public InputDecoder build() {
            return new InputDecoder(this);
        }
    }

    private List<Address> addressList;
    private String userInput;
    private Context mapsActivityContext;
    private Address address;

    public InputDecoder(Builder build) {
        this.userInput = build.userInput;
        this.mapsActivityContext = build.mapsActivityContext;
    }

    public LatLng decodeInput() {
        if (userInput != null || !userInput.equals("")) {
            int addressListIndex = 1;
            Geocoder coder = new Geocoder(mapsActivityContext);
            try {
                addressList = coder.getFromLocationName(userInput, addressListIndex);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        createAddress();
        LatLng latLng = createLatLng(address);
        return latLng;
    }

    private void createAddress() {
        int addressIndex = 0;
        address = addressList.get(addressIndex);
    }

    private LatLng createLatLng(Address address) {
        LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
        return latLng;
    }

    public Address getDestinationAddress(){
        return address;
    }
}
