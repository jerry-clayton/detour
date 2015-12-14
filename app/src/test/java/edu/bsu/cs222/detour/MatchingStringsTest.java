package edu.bsu.cs222.detour;

import com.google.android.gms.maps.model.LatLng;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by s-knob on 11/15/15.
 */
public class MatchingStringsTest {
    @Test
    public void testStrings(){
        LatLng BALL_STATE=new LatLng(40.218746, -85.406699);
        LatLng FORT_WAYNE= new LatLng(41.079273, -85.139351);
        StringURLBuilder builder = new StringURLBuilder.Builder().setStart(BALL_STATE).setEnd(FORT_WAYNE).build();
        String testerURLString=builder.createURL();
        String actualURLString = "https://maps.googleapis.com/maps/api/directions/json?"
                + "origin=40.218746,-85.406699"
                + "&destination=41.079273,-85.139351"
                + "&key=AIzaSyDivGp-JdNg43pVpnGwwxBUabYI6XkY2NA";
        Assert.assertTrue(testerURLString.equals(actualURLString));
    }
}
