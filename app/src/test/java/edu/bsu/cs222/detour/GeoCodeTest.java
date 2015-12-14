package edu.bsu.cs222.detour;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import com.google.android.gms.maps.model.LatLng;
import junit.framework.Assert;
import org.junit.Test;
import java.io.IOException;
import java.util.List;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class GeoCodeTest {
    private MapsActivityTest mMap = new MapsActivityTest();
    private List<Address> addressList;

    @Test
    public void testFindLocation() {
        String ballState = "Ball State University";
        LatLng ballStateLatLng = decodeInput(ballState);
        LatLng actualBallStateLatLng = new LatLng(40.218746, -85.406699);
        Assert.assertTrue(ballStateLatLng.equals(actualBallStateLatLng));
    }

    private LatLng decodeInput(String userInput) {
        Context mapsActivityContext=mMap.getMapsActivityContext();
        if (userInput != null || !userInput.equals("")) {
            int addressListIndex = 1;
            Geocoder coder = new Geocoder(mapsActivityContext);
            try {
                addressList = coder.getFromLocationName(userInput, addressListIndex);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Address address = createAddress();
        LatLng latLng = createLatLng(address);
        return latLng;
    }

    private Address createAddress() {
        int addressIndex = 0;
        if(addressList==null){
            System.out.println("-------------------------NULLLLL");
        }
        Address address = addressList.get(addressIndex);
        return address;
    }

    private LatLng createLatLng(Address address) {
        LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
        return latLng;
    }
}