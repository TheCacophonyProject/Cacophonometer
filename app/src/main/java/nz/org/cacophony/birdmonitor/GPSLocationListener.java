package nz.org.cacophony.birdmonitor;


import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;

import static nz.org.cacophony.birdmonitor.views.GPSFragment.GPS_ACTION;
import static nz.org.cacophony.birdmonitor.views.GPSFragment.GpsMessageType.GPS_UPDATE_SUCCESS;


/**
 * The app has the ability to save the current GPS location.  When the app asks the Android OS
 * for a new location, this class listens for the broadcast from the OS and responds to it by
 * saving the new location and displaying a message to the use.
 */
class GPSLocationListener implements LocationListener {
    private static final String TAG = GPSLocationListener.class.getName();

    private final Context context;


    GPSLocationListener(Context context) {
        this.context = context;

    }


    public void onLocationChanged(Location location) {

        try {
            Log.d(TAG, "onLocationChanged 1");

            double lat = location.getLatitude();
            lat = Math.round(lat * 1000000.0) / 1000000.0;
            double lon = location.getLongitude();
            lon = Math.round(lon * 1000000.0) / 1000000.0;

            Prefs prefs = new Prefs(context);
            prefs.setLatitude(lat);
            prefs.setLongitude(lon);

            MessageHelper.broadcastMessage("Success", GPS_UPDATE_SUCCESS, GPS_ACTION, context);
        } catch (Exception ex) {
            Log.e(TAG, ex.getLocalizedMessage(), ex);
        }
    }

    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    public void onProviderEnabled(String provider) {
    }

    public void onProviderDisabled(String provider) {
    }

}
