package org.me.gcu.jyogat200v5;
/*JOHN YOGATHAS - S1708974 */

//Imports

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapsFragment extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap Map;
    private ArrayList<PullParser> alist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_maps);
        alist = (ArrayList<PullParser>) getIntent().getExtras().getSerializable("Items");
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
        Map = googleMap;

        /* Glasgow's Latitude and Longitude is:
        55.8642 degrees North
        4.2518 degrees West
        Used as the default starting position for the map
        */
        LatLng uk = new LatLng(55.87465, -4.25375);

        for (int i = 0; i < alist.size(); i++) {
            BitmapDescriptor bmd = null;
            // Determines which pins are which colour
            if (Float.parseFloat(alist.get(i).getMagnitude()) < 1) {
                bmd = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN);
            } else if (Float.parseFloat(alist.get(i).getMagnitude()) >= 1 && Float.parseFloat(alist.get(i).getMagnitude()) <= 1.9) {
                bmd = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW);
            } else if (Float.parseFloat(alist.get(i).getMagnitude()) > 1.9) {
                bmd = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED);
            }
            Map.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(alist.get(i).getLatitude()), Double.parseDouble(alist.get(i).getLongitude()))).icon(bmd).title(alist.get(i).getLocation()));
        }
        //Map.moveCamera(CameraUpdateFactory.newLatLng(uk));
        //Map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(56,4) , 10.0f) );

        //This makes sure the camera is zoomed in by default a little, for convenience
        Map.moveCamera(CameraUpdateFactory.newLatLngZoom(uk, 7));
    }
}