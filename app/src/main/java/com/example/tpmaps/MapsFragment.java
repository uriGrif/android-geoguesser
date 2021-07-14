package com.example.tpmaps;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tpmaps.helpers.GoogleMapsHelper;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsFragment extends Fragment {

    GoogleMap gMap;
    public float lat, lng;

    private OnMapReadyCallback callback = new OnMapReadyCallback() {
        @Override
        public void onMapReady(GoogleMap googleMap) {
            gMap = googleMap;
            gMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
            gMap.getUiSettings().setZoomGesturesEnabled(false);
//            LatLng sydney = new LatLng(-34, 151);
//            gMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//            gMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
            AgregarMarker(lat, lng);
        }
    };

    public void AgregarMarker(double lat, double lng) {
        if(gMap != null) {
            gMap.clear();
            gMap.addMarker(GoogleMapsHelper.CreateMarker(lat, lng));
            //gMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(lat, lng)));
            gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lng), 5));
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_maps, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }
}