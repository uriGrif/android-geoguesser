package com.example.tpmaps.helpers;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class GoogleMapsHelper {
    /**
     *  Genero un MarkerOptions segun los parametros enviados.
     */
    public static MarkerOptions CreateMarker(double lat, double lng){
        MarkerOptions   returnValue;
        LatLng latLng;

        latLng      = new LatLng(lat, lng);
        returnValue = CreateMarker (latLng, "", "");
        return returnValue;
    }

    public static MarkerOptions CreateMarker(double lat, double lng, String title, String description){
        MarkerOptions   returnValue;
        LatLng latLng;

        latLng      = new LatLng(lat, lng);
        returnValue = CreateMarker (latLng, title, description);
        return returnValue;
    }

    /**
     *  Genero un MarkerOptions segun los parametros enviados.
     */
    public static  MarkerOptions CreateMarker(double lat, double lng, String title, String description, float fltHueClor){
        MarkerOptions   returnValue;
        LatLng          latLng;

        latLng      = new LatLng(lat, lng);
        returnValue = CreateMarker (latLng, title, description, fltHueClor);

        return returnValue;
    }

    /**
     *  Genero un MarkerOptions segun los parametros enviados.
     */
    public static  MarkerOptions CreateMarker(LatLng latLng, String title, String description){
        MarkerOptions   returnValue;

        returnValue = new MarkerOptions().position(latLng).title(title).snippet(description);
        returnValue = returnValue.icon(BitmapDescriptorFactory.defaultMarker());
        return returnValue;
    }

    /**
     *  Genero un MarkerOptions segun los parametros enviados.
     */
    public static  MarkerOptions CreateMarker(LatLng latLng, String title, String description, float fltHueClor){
        MarkerOptions   returnValue;

        returnValue = CreateMarker(latLng, title, description);
        returnValue = returnValue.icon(BitmapDescriptorFactory.defaultMarker(fltHueClor));
        return returnValue;
    }

    public static float distanceInMeters (LatLng latLng_a, LatLng latLng_b){
        return distanceInMeters((float)latLng_a.latitude, (float)latLng_a.longitude, (float)latLng_b.latitude, (float)latLng_b.longitude );
    }

    public static float distanceInMeters (float lat_a, float lng_a, float lat_b, float lng_b )
    {
        double earthRadius = 3958.75;
        double latDiff = Math.toRadians(lat_b-lat_a);
        double lngDiff = Math.toRadians(lng_b-lng_a);
        double a = Math.sin(latDiff /2) * Math.sin(latDiff /2) +
                Math.cos(Math.toRadians(lat_a)) * Math.cos(Math.toRadians(lat_b)) *
                        Math.sin(lngDiff /2) * Math.sin(lngDiff /2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double distance = earthRadius * c;

        int meterConversion = 1609;

        return new Float(distance * meterConversion).floatValue();
    }

    public static float distanceInKM (float lat_a, float lng_a, float lat_b, float lng_b )
    {
        double earthRadius = 3958.75;
        double latDiff = Math.toRadians(lat_b-lat_a);
        double lngDiff = Math.toRadians(lng_b-lng_a);
        double a = Math.sin(latDiff /2) * Math.sin(latDiff /2) +
                Math.cos(Math.toRadians(lat_a)) * Math.cos(Math.toRadians(lat_b)) *
                        Math.sin(lngDiff /2) * Math.sin(lngDiff /2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double distance = earthRadius * c;

        int meterConversion = 1609;

        return new Float(distance * meterConversion).floatValue()/1000;
    }

}
