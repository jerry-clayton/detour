package edu.bsu.cs222.detour;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;

import junit.framework.Assert;
import org.junit.Test;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

public class LocationTest {
    private MapsActivityTest mMap = new MapsActivityTest();
    private Context mapsActivityContext=mMap.getMapsActivityContext();
    private GoogleApiClient mGoogleApiClient;
    @Test
    private void testFindCurrentLocation() {
        buildGoogleApiClient();
        Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        Assert.assertNotNull(mLastLocation);
    }
    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(mapsActivityContext)
                .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                    @Override
                    public void onConnected(Bundle bundle) {

                    }

                    @Override
                    public void onConnectionSuspended(int i) {

                    }
                })
                .addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(ConnectionResult connectionResult) {

                    }
                })
                .addApi(LocationServices.API)
                .build();
    }
}
