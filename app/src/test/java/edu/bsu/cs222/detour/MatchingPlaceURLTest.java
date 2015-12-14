package edu.bsu.cs222.detour;

import com.google.android.gms.maps.model.LatLng;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by ToanTran on 12/6/15.
 */
public class MatchingPlaceURLTest {
    @Test
    public void testURLStrings(){
        LatLng BALL_STATE=new LatLng(40.218746, -85.406699);
        GasURLBuilder urlBuilder=new GasURLBuilder(BALL_STATE);
        String testerURL=urlBuilder.buildURL();
        String acutalURL="https://maps.googleapis.com/maps/api/place/nearbysearch/json?location="+BALL_STATE.latitude+","+
                BALL_STATE.longitude+"&radius=6000&types=gas_station&key=AIzaSyDivGp-JdNg43pVpnGwwxBUabYI6XkY2NA";
        Assert.assertTrue(testerURL.equals(acutalURL));
    }
}
