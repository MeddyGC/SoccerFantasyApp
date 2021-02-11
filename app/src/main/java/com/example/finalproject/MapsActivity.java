package com.example.finalproject;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private static final int LOCATION_REQUEST_CODE = 101;
    String league = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        Intent intent = getIntent();
        league = intent.getStringExtra("League");
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        UiSettings mapSettings;
        mapSettings = mMap.getUiSettings();
        mapSettings.setZoomControlsEnabled(true);
        mapSettings.setZoomGesturesEnabled(true);
        mapSettings.setScrollGesturesEnabled(true);
        mapSettings.setTiltGesturesEnabled(true);
        mapSettings.setRotateGesturesEnabled(true);

        // Add a marker in Sydney and move the camera
        //LatLng sydney = new LatLng(-34, 151);
        //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
//        LatLng Moorhead = new LatLng(46, -96);
//        mMap.addMarker(new MarkerOptions().position(Moorhead).title("Marker in Moorhead"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(Moorhead));
        LatLng italy = new LatLng(41.902782, 12.496366);
        LatLng france = new LatLng(48.858093,  2.294694);
        LatLng england = new LatLng(53.483959,-2.244644);
        LatLng spain = new LatLng(40.416775,  -3.703790);
        LatLng germany = new LatLng(48.137154, 11.576124);
        Marker atItaly = mMap.addMarker(new MarkerOptions()
                .position(italy)
                .title("Italy")
                .snippet("SERIE A"));

        Marker atfrance = mMap.addMarker(new MarkerOptions()
                .position(france)
                .title("France")
                .snippet("Ligue 1"));

        Marker atspain = mMap.addMarker(new MarkerOptions()
                .position(spain)
                .title("Spain")
                .snippet("La Liga Santander"));

        Marker atengland = mMap.addMarker(new MarkerOptions()
                .position(england)
                .title("England")
                .snippet("English Premier League"));

        Marker atgermany = mMap.addMarker(new MarkerOptions()
                .position(germany)
                .title("UEFA")
                .snippet("Champions League"));
        LatLng v = germany;


        if (league.equals("ENGLAND: Premier League")){
            v = england;
        }
        else if(league.equals("SPAIN: La Liga")){
            v = spain;
        }
        else if(league.equals("FRANCE: Ligue 1")){
            v = france;
        }
        else if(league.equals("ITALY: Serie A")){
            v =italy;
        }
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(v)
                .zoom(50)
                .bearing(70)
                .tilt(25)
                .build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        mMap.addCircle(
                new CircleOptions()
                        .center(v)
                        .radius(500.0)
                        .strokeWidth(10f)
                        .strokeColor(Color.BLUE)
                        .fillColor(Color.argb(70,10,50,150))
        );

        ArrayList val = new ArrayList();
        PolygonOptions rectOptions = new PolygonOptions()
                .add(england)
                .add(spain)
                .add(france)
                .add(italy);
                //.add(new LatLng(12.635898, -7.971547));

        Polygon polygon = mMap.addPolygon(rectOptions);

        mMap.addPolyline(new PolylineOptions()
                .add(v)
                .add(spain)
                .width(3f)
                .color(Color.RED)
        );





        if (mMap != null){
            int permission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
            if (permission == PackageManager.PERMISSION_GRANTED){
                mMap.setMyLocationEnabled(true);
            }
            else{
                requestPermission(Manifest.permission.ACCESS_FINE_LOCATION,LOCATION_REQUEST_CODE);
            }
            mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
            //mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
            //mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);

        }
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int)(width*.5),(int)(height*.5));

    }

    protected void requestPermission(String permissionType, int requestCode){
        ActivityCompat.requestPermissions(this, new String[]{permissionType},requestCode);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[],int[] grantResults){
        switch (requestCode){
            case LOCATION_REQUEST_CODE: {
                if (grantResults.length ==  0 || grantResults[0] != PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this,"Cannot show location - permission denied",Toast.LENGTH_LONG).show();
                }
                else{
                    SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().
                            findFragmentById(R.id.map);
                    mapFragment.getMapAsync(this);
                }
            }
        }
    }
}
