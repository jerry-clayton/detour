package edu.bsu.cs222.detour;

import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.location.Address;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import android.view.inputmethod.InputMethodManager;
import android.widget.PopupWindow;
import edu.bsu.cs222.detour.model.GasURLBuilder;
import edu.bsu.cs222.detour.model.InputDecoder;
import edu.bsu.cs222.detour.model.MarkerBuilder;
import edu.bsu.cs222.detour.model.PolylineBuilder;
import edu.bsu.cs222.detour.model.PolylineURLBuilder;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,LocationListener{
    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;
    private final String TAG = MapsActivity.class.getSimpleName();
    private final int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    private LocationRequest mLocationRequest;
    private Marker previousMarker;
    private PopupWindow popupWindow;
    private LayoutInflater layoutInflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        createGoogleApiClient();
        mGoogleApiClient.connect();
        buildLocationRequest();
    }

    private void createGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .addApi(LocationServices.API)
                .build();
    }

    private void buildLocationRequest() {
        int second = 1000;
        int fifthOfSecond=5;
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(second)
                .setFastestInterval((second / fifthOfSecond));
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        Location currentLocation=mMap.getMyLocation();
        if (currentLocation==null){
            System.out.print("NULLLLLLLLLL");
        }

    }

    private void hideSoftKeyboardOnClick(){
        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public void onGo(View view) throws ExecutionException, InterruptedException {
        hideSoftKeyboardOnClick();
        String userInput=getUserInput();
        Boolean statement=checkUserInput(userInput);
        if(statement.equals(true)) {
            Context context = this;
            InputDecoder decoder = new InputDecoder.Builder().setUserInput(userInput).setContext(context).build();
            LatLng destinationLatLng = decoder.decodeInput();
            Address destinationAddress = decoder.getDestinationAddress();
            mMap.clear();
            int addressTitleIndex=0;
            mMap.addMarker(new MarkerOptions().position(destinationLatLng).title(destinationAddress.getAddressLine(addressTitleIndex)));
            Location currentLocation = mMap.getMyLocation();
            currentLocation = checkCurrentLocation(currentLocation);
            LatLng originLatLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
            PolylineURLBuilder builder = new PolylineURLBuilder
                    .Builder()
                    .setStart(originLatLng)
                    .setEnd(destinationLatLng)
                    .build();
            String stringURL = builder.createURL();
            PolylineBuilder polylineBuilder = new PolylineBuilder(stringURL);
            PolylineOptions polyLine = polylineBuilder.buildPolyline();
            mMap.addPolyline(polyLine);
        }
    }

    private String getUserInput(){
        EditText editText = (EditText) findViewById(R.id.addressBar);
        return editText.getText().toString();
    }

    private Boolean checkUserInput(String input){
        Boolean statement=true;
        if(input.equals("")){
            statement=false;
        }
        return statement;
    }

    public void onGas(View view) throws ExecutionException, InterruptedException {
        Location currentLocation = mMap.getMyLocation();
        currentLocation = checkCurrentLocation(currentLocation);
        LatLng currentLatLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
        GasURLBuilder builder = new GasURLBuilder(currentLatLng);
        String url = builder.buildURL();
        MarkerBuilder markerBuilder = new MarkerBuilder(url);
        ArrayList<MarkerOptions> markers = markerBuilder.buildMarkers();
        for(MarkerOptions currentMarker : markers){
            System.out.println(currentMarker.getTitle());
            mMap.addMarker(currentMarker);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(currentMarker.getPosition()));
            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    if(marker.getTitle() != "You"){//change "you" to "user"
                        try {
                            routeToGas(marker);
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        return true;
                    }
                    return false;
                }
            });
        }
    }

    private void routeToGas(Marker marker) throws ExecutionException, InterruptedException {
        LatLng destinationLatLng=marker.getPosition();
        mMap.clear();
        mMap.addMarker(new MarkerOptions().position(destinationLatLng).icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_destination_marker)));
        Location currentLocation = mMap.getMyLocation();
        Location location= checkCurrentLocation(currentLocation);
        LatLng originLatLng=new LatLng(location.getLatitude(), location.getLongitude());
        PolylineURLBuilder builder= new PolylineURLBuilder.Builder()
                .setStart(originLatLng)
                .setEnd(destinationLatLng)
                .build();
        String stringURL=builder.createURL();
        PolylineBuilder polylineBuilder = new PolylineBuilder(stringURL);
        PolylineOptions polyLine = polylineBuilder.buildPolyline();
        mMap.addPolyline(polyLine);
    }

    private Location checkCurrentLocation(Location current) throws InterruptedException {
        if(current==null){
            System.out.println("NULL");
            current = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        }
        return current;
    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.i(TAG, "Location services connected.");
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        Location currentLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        handleNewLocation(currentLocation);
    }

    @Override
    public void onLocationChanged(Location location) {
        handleNewLocation(location);
    }

    private void handleNewLocation(Location location) {
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        if(previousMarker != null) {
            previousMarker.remove();
        }
        MarkerOptions currentMarker = new MarkerOptions()
                .position(latLng)
                .title("You")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_start_marker));
        previousMarker = mMap.addMarker(currentMarker);;
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i(TAG, "Location services suspended. Please reconnect.");
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        if (connectionResult.hasResolution()) {
            try {
                //
                connectionResult.startResolutionForResult(this, CONNECTION_FAILURE_RESOLUTION_REQUEST);
            } catch (IntentSender.SendIntentException e) {
                e.printStackTrace();
            }
        } else {
            Log.i(TAG, "Location services connection failed with code " + connectionResult.getErrorCode());
        }
    }
}
