package com.sm.app.wifi.sghedoni.andrea.wifism;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.SeekBar;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class MapFragment extends SupportMapFragment implements OnMapReadyCallback {

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
        this.addGeoFences(map);
    }

    // aggiungo un marker con un raggio azzurro che indica il geofence.
    // Lat Lang per ora sono cablate a via Palestro 3a formigine
    private void addGeoFences(GoogleMap m) {

        LatLng center = new LatLng(44.5781125, 10.8502881);
        Double radius = 100.00;//meters
        float stroke = 1.50f; //meters

        m.addMarker(new MarkerOptions()
                .position(center)
                .draggable(true)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        m.addCircle(new CircleOptions()
                .center(center)
                .radius(radius)
                .strokeWidth(stroke)
                .strokeColor(Color.BLACK)
                .fillColor(Color.argb(50, 121, 225, 241))); // cyano trasparente
    }
}
