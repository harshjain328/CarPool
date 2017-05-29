package com.noobs.carpool;

import android.app.Dialog;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.noobs.carpool.api.Api;
import com.noobs.carpool.api.RetrofitCallback;
import com.noobs.carpool.models.DirectionRequest;
import com.noobs.carpool.models.DirectionRequestByLatLng;
import com.noobs.carpool.models.DirectionRequestByPlace;
import com.noobs.carpool.utils.MapModels;
import com.noobs.carpool.utils.RouteDecode;
import com.noobs.carpool.utils.Util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class HitchMap extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,LocationListener, GoogleApiClient.OnConnectionFailedListener, View.OnClickListener {

    GoogleMap map;
    GoogleApiClient mGoogleApiClient;
    Marker cur,des;
    Button loc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(googleServicesAvailable()) {
            setContentView(R.layout.activity_hitch_map);
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            initMap();
        }
        loc=(Button)findViewById(R.id.btnCurLoc);
        loc.setOnClickListener(this);

        // testing route creation
        //createRouteBetween(new DirectionRequestByPlace("ahmedabad", "delhi, india"));
        createRouteBetween(new DirectionRequestByLatLng(23.04451F,72.5203356F, 23.6F, 72));

    }



    //points to the specified location


    //initialise the map
    private void initMap() {
        MapFragment mapFrag=(MapFragment)getFragmentManager().findFragmentById(R.id.map);
        //calls onMapReady method
        mapFrag.getMapAsync(this);
    }

    //checks the availability of play services
    public boolean googleServicesAvailable() {
        GoogleApiAvailability avail = GoogleApiAvailability.getInstance();
        int isAvailable = avail.isGooglePlayServicesAvailable(this);

        if (isAvailable == ConnectionResult.SUCCESS)
            return true;

        //if the play service is not updated or an outdated version is running
        else if (avail.isUserResolvableError(isAvailable)) {
            Dialog dialog = avail.getErrorDialog(this, isAvailable, 0);
            dialog.show();
        } else {
            Toast.makeText(this, "Can't connect to play services", Toast.LENGTH_LONG).show();
        }
        return false;
    }

    //async call for maps
    @Override
    public void onMapReady(GoogleMap googleMap) {
        map=googleMap;
        mGoogleApiClient=new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        mGoogleApiClient.connect();
    }

    //moves the map location
    private void goToLocationZoom(double lat, double lng,float zoom) {
        LatLng loc = new LatLng(lat, lng);
        CameraUpdate update= CameraUpdateFactory.newLatLngZoom(loc,zoom);
        //creates an animation on the map
        map.animateCamera(update);
    }

    LocationRequest mLocationRequest;
    @Override
    public void onConnected(Bundle bundle) {
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    public void requestPermissions(@NonNull String[] permissions, int requestCode)
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for Activity#requestPermissions for more details.
                return;
            }
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,mLocationRequest,this);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        if(location==null)
            Toast.makeText(this,"Can't get current location",Toast.LENGTH_LONG).show();
        else {
            LatLng ll=new LatLng(location.getLatitude(),location.getLongitude());
            goToLocationZoom(location.getLatitude(), location.getLongitude(), 15);
            MarkerOptions options = new MarkerOptions()
                    .title("Current location")
                    .position(ll);
            cur = map.addMarker(options);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnCurLoc:

                EditText locSearch=(EditText)findViewById(R.id.edCurLoc);
                String location=locSearch.getText().toString();

                Geocoder gc=new Geocoder(this);
                List<Address> list= null;
                try {
                    list = gc.getFromLocationName(location,1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Address add = list.get(0);
                double lat=add.getLatitude();
                double lng=add.getLongitude();
                goToLocationZoom(lat,lng,15);
                MarkerOptions options=new MarkerOptions()
                        .title("Destination")
                        .position(new LatLng(add.getLatitude(),add.getLongitude()));
                if(des!=null)
                    des.remove();

                des=map.addMarker(options);


                break;
        }
    }



    /*
        This can accept either DirectionRequestByPlace or DirectionRequestByLatLng
        because both are subclass of DirectionRequest
     */
    private void createRouteBetween(final DirectionRequest directionRequest) {
        Api.Maps.getRoutes(directionRequest, new RetrofitCallback<MapModels.DirectionResults>(this){
            @CallSuper
            @Override
            public void onResponse(Call<MapModels.DirectionResults> call, Response<MapModels.DirectionResults> response) {
                super.onResponse(call, response);

                List<LatLng> routelist = Util.decodeRouteFrom(response.body());

                if (routelist.size() > 0) {
                    PolylineOptions rectLine = new PolylineOptions().width(14).color(Color.BLUE);

                    for (int i = 0; i < routelist.size(); i++) {
                        rectLine.add(routelist.get(i));
                    }

                    // Adding route on the map
                    map.addPolyline(rectLine);

                    MarkerOptions sourceMarker = new MarkerOptions()
                            .position(routelist.get(0))
                            .title(directionRequest.getSource());

                    MarkerOptions destMarker = new MarkerOptions()
                            .position(routelist.get(routelist.size()-1))
                            .title(directionRequest.getDestination());

                    //markerOptions.draggable(true);
                    map.addMarker(sourceMarker);
                    map.addMarker(destMarker);

                }
            }
        });

    }


}