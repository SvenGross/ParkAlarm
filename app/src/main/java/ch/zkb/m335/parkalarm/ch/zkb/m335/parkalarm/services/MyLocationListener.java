package ch.zkb.m335.parkalarm.ch.zkb.m335.parkalarm.services;


import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;

public class MyLocationListener implements LocationListener {

    @Override
    public void onLocationChanged(Location loc) {
        Log.d("Location - Longitude", "" + loc.getLongitude());
        Log.d("Location - Latitude", "" + loc.getLatitude());
    }

    @Override
    public void onProviderDisabled(String provider) {}

    @Override
    public void onProviderEnabled(String provider) {}

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {}
}