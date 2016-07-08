package com.sm.app.alert.sghedoni.andrea.dev.fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.sm.app.alert.sghedoni.andrea.dev.Controller;
import com.sm.app.alert.sghedoni.andrea.dev.Fence;

import java.util.ArrayList;
import java.util.ResourceBundle;


public class MapFragment extends SupportMapFragment implements OnMapReadyCallback, GoogleMap.OnMyLocationButtonClickListener, GoogleMap.OnMarkerClickListener {

    GoogleMap map = null;
    private static final String TAG = "[DebApp]MapFragment";

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        // mi permette di implementare il metodo onMapReady cosi da ricavarmi mappa e farci operazioni non appena caricata
        this.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        Log.d(TAG, "La mappa si è caricata");
        LatLng centerForZoom = new LatLng(44.5781125, 10.8502881);

        // centro dove posizionare la mappa e lo zoom
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(centerForZoom, 16.0f));
        //setto il pulsante per la propria posizione
        map.setOnMyLocationButtonClickListener(this);
        // attiva la possibilità di trovarsi tramite un click sul cerca posizione
        this.enableMyLocation();
        // aggiunge i fences
        this.addGeoFences(map);

        map.setOnMarkerClickListener(this);
    }

    /**
     * Enables the My Location layer if the fine location permission has been granted.
     */
    private void enableMyLocation() {
        // controllo nel manifest di avere
        if ((ContextCompat.checkSelfPermission(this.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) && (map != null)) {
            Log.d(TAG, "Posizione personale concessa e mappa non nulla");
            map.setMyLocationEnabled(true);
        }
    }

    // aggiungo un marker con un raggio azzurro che indica il geofence.
    // Lat Lang per ora sono cablate a via Palestro 3a formigine
    private void addGeoFences(GoogleMap m) {

            Log.d(TAG, "Draw the fences on Map");
            for (int i=0; i< Controller.fences.size(); i++) {
                Fence fence = Controller.fences.get(i);
                String snippet = fence.getAddress() + ", " + fence.getCity() + ", " + fence.getProvince() + ".";
                this.drawFence(m, fence.getLat(), fence.getLng(), Double.parseDouble(fence.getRange().toString()), fence.isActive(), fence.getName(), snippet);
            }
    }

    private void drawFence(GoogleMap map, Double lat, Double lng, Double range, boolean active, String title, String snippet) {

        LatLng center = new LatLng(lat, lng);
        int green = Color.argb(50, 17, 157, 88);
        int red = Color.argb(50, 216, 46, 46);

        int color =  active ? green : red ;
        int strokeColor = active ? Color.GREEN : Color.RED ;
        float colorMarker = active ? BitmapDescriptorFactory.HUE_GREEN : BitmapDescriptorFactory.HUE_RED ;
        float stroke = 1.50f;

        map.addMarker(new MarkerOptions()
                .position(center)
                .draggable(true)
                .title(title)
                .snippet(snippet)
                .icon(BitmapDescriptorFactory.defaultMarker(colorMarker)));
        map.addCircle(new CircleOptions()
                .center(center)
                .radius(range)
                .strokeWidth(stroke)
                .strokeColor(strokeColor)
                .fillColor(color));
    }

    @Override
    public boolean onMyLocationButtonClick() {
        // ascoltatore al click sul simbolo trova posizione
        Toast.makeText(this.getContext(), "MyLocation button clicked", Toast.LENGTH_SHORT).show();
        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).
        return false;
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        Log.d(TAG, "Click on Marker");
        return false;
    }
}
