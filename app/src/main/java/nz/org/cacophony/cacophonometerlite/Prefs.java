package nz.org.cacophony.cacophonometerlite;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

//import org.slf4j.Logger;

/**
 * This class helps static classes that don't have an application Context to get and save Shared Preferences (Server.java..)
 * Expanded to keep all settings in one place
 */

class Prefs {

    private static boolean testing = true;
    private static boolean walkingPeriodicRecordingsEnabled = false;

    private static final String TAG = Prefs.class.getName();
    private static Context context = null;


    private static final String PREFS_NAME = "CacophonyPrefs";

    private static final String PRODUCTION_SERVER_URL = "https://api.cacophony.org.nz";       // HTTPS Server URL
   // private static final String PRODUCTION_SERVER_URL_HTTP = "http://103.16.20.22";       // Non HTTPS Server URL

    private static final String TEST_SERVER_URL = "https://api-test.cacophony.org.nz";       // Server URL

    //private static final String SERVER_URL_KEY = "SERVER_URL";
    private static final String PASSWORD_KEY = "PASSWORD";
    private static final String DEVICE_NAME_KEY = "DEVICE_NAME";
    private static final String GROUP_NAME_KEY = "GROUP_NAME";
    private static final String TOKEN_KEY = "TOKEN";
    private static final long TOKEN_TIMEOUT_SECONDS = 60 * 60 * 24 * 7; // 1 week
   // private static final long TOKEN_LAST_REFRESHED = 0;
    private static final String TOKEN_LAST_REFRESHED_KEY = "TOKEN_LAST_REFRESHED";
    private static final String LATITUDE_KEY = "LATITUDE";
    private static final String LONGITUDE_KEY = "LONGITUDE";
    private static final String DEVICE_ID = "UNKNOWN";
    private static final String RECORDING_DURATION_SECONDS_KEY = "RECORDING_DURATION_SECONDS";
   private static final double RECORDING_DURATION_SECONDS = 60;

    private static final String TIME_BETWEEN_RECORDINGS_SECONDS_KEY = "TIME_BETWEEN_RECORDINGS";
    private static final double TIME_BETWEEN_RECORDINGS_SECONDS = 3600;  //3600 is one hour!

    private static final String TIME_BETWEEN_FREQUENT_RECORDINGS_SECONDS_KEY = "TIME_BETWEEN_FREQUENT_RECORDINGS_SECONDS";
    private static final double TIME_BETWEEN_FREQUENT_RECORDINGS_SECONDS = 900;  //900 is 15 minutes

    private static final String TIME_BETWEEN_VERY_FREQUENT_RECORDINGS_SECONDS_KEY = "TIME_BETWEEN_VERY_FREQUENT_RECORDINGS_SECONDS";
    private static final double TIME_BETWEEN_VERY_FREQUENT_RECORDINGS_SECONDS = 120;  //120 is two minutes, use for testing


    private static final String BATTERY_LEVEL_CUTOFF_REPEATING_RECORDINGS_KEY = "BATTERY_LEVEL_CUTOFF_REPEATING_RECORDINGS";
    private static final double BATTERY_LEVEL_CUTOFF_REPEATING_RECORDINGS = 70;

    private static final String IGNORE_BATTERY_LEVEL_CUTOFF_REPEATING_RECORDINGS_KEY = "IGNORE_BATTERY_LEVEL_CUTOFF_REPEATING_RECORDINGS";
    private static final double IGNORE_BATTERY_LEVEL_CUTOFF_REPEATING_RECORDINGS = 0; // for testing battery

    private static final String BATTERY_LEVEL_CUTOFF_DAWN_DUSK_RECORDINGS_KEY = "BATTERY_LEVEL_CUTOFF_DAWN_DUSK_RECORDINGS";
    private static final double BATTERY_LEVEL_CUTOFF_DAWN_DUSK_RECORDINGS = 50;

    private static final String IGNORE_BATTERY_LEVEL_CUTOFF_DAWN_DUSK_RECORDINGS_KEY = "IGNORE_BATTERY_LEVEL_CUTOFF_DAWN_DUSK_RECORDINGS";
    private static final double IGNORE_BATTERY_LEVEL_CUTOFF_DAWN_DUSK_RECORDINGS = 0; // for testing battery

    private static final String TIME_BETWEEN_UPLOADS_SECONDS_KEY = "TIME_BETWEEN_UPLOADS";
    private static final double TIME_BETWEEN_UPLOADS_SECONDS = 21600;  //21600 is six hours!

    private static final String TIME_BETWEEN_FREQUENT_UPLOADS_SECONDS_KEY = "TIME_BETWEEN_FREQUENT_UPLOADS_SECONDS";
    private static final double TIME_BETWEEN_FREQUENT_UPLOADS_SECONDS = 1;  // for testing battery

    private static final String DAWN_DUSK_OFFSET_MINUTES_KEY = "DAWN_DUSK_OFFSET_MINUTES";
    private static final double DAWN_DUSK_OFFSET_MINUTES = 60;

    private static final String DAWN_DUSK_INCREMENT_MINUTES_KEY = "DAWN_DUSK_INCREMENT_MINUTES";
    private static final double DAWN_DUSK_INCREMENT_MINUTES = 10;



    private static final String LENGTH_OF_TWILIGHT_KEY = "LENGTH_OF_TWILIGHT"; // Twilight is the time between dawn and sunrise, or sunset and dusk
    private static final double LENGTH_OF_TWILIGHT_SECONDS = 29 * 60; // 29 minutes http://www.gaisma.com/en/location/nelson.html

    private static final String  HAS_ROOT_ACCESS_KEY = "HAS_ROOT_ACCESS";

    private static final String USE_VERY_FREQUENT_RECORDINGS_KEY = "USE_VERY_FREQUENT_RECORDINGS";
    private static final String USE_FREQUENT_RECORDINGS_KEY = "USE_FREQUENT_RECORDINGS";



    private static final String  USE_SHORT_RECORDINGS_KEY = "USE_SHORT_RECORDINGS";
    private static final String  USE_FREQUENT_UPLOADS_KEY = "USE_FREQUENT_UPLOADS";
    private static final String  IGNORE_LOW_BATTERY_KEY = "IGNORE_LOW_BATTERY";

    private static final String  USE_TEST_SERVER_KEY = "USE_TEST_SERVER";
    private static final String OFFLINE_MODE_KEY = "OFFLINE_MODE";
    private static final String PLAY_WARNING_SOUND_KEY = "PLAY_WARNING_SOUND";

    private static final String BATTERY_LEVEL_KEY = "BATTERY_LEVEL";
    private static final String MAXIMUM_BATTERY_LEVEL_KEY = "MAXIMUM_BATTERY_LEVEL";
    private static final String DATE_TIME_LAST_UPLOAD_KEY = "DATE_TIME_LAST_UPLOAD";
    private static final String DATE_TIME_LAST_CALCULATED_DAWN_DUSK_KEY = "DATE_TIME_LAST_CALCULATED_DAWN_DUSK";
    private static final String DATE_TIME_LAST_REPEATING_ALARM_FIRED_KEY = "DATE_TIME_LAST_REPEATING_ALARM_FIRED";
//    private static Logger logger = null;

    Prefs(Context context) {
        this.context = context;

    }

    public static long getTokenTimeoutSeconds() {
        return TOKEN_TIMEOUT_SECONDS;
    }

    public static boolean isWalkingPeriodicRecordingsEnabled() {
        return walkingPeriodicRecordingsEnabled;
    }

    public static void setWalkingPeriodicRecordingsEnabled(boolean walkingPeriodicRecordingsEnabled) {
        Prefs.walkingPeriodicRecordingsEnabled = walkingPeriodicRecordingsEnabled;
    }


    private String getString(String key) {
        if (context == null) {
            Log.e(TAG, "Context was null when trying to get preferences.");
          //  logger.error("Context was null when trying to get preferences.");
            return null;
        } else {
            SharedPreferences preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
            return preferences.getString(key, null);
        }
    }

    private void setString(String key, String val) {
        if (context == null) {
            Log.e(TAG, "Context was null when trying to get preferences.");
          //  logger.error("Context was null when trying to get preferences.");
            return;
        }
        SharedPreferences preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        preferences.edit().putString(key, val).apply();
    }

    private double getDouble(String key) {
        if (context == null) {
            Log.e(TAG, "Context was null when trying to get preferences.");
          //  logger.error("Context was null when trying to get preferences.");
            return 0;
        }
        SharedPreferences preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return Double.longBitsToDouble(preferences.getLong(key, 0));
    }

    private long getLong(String key) {
        if (context == null) {
            Log.e(TAG, "Context was null when trying to get preferences.");
          //  logger.error("Context was null when trying to get preferences.");
            return 0;
        }
        SharedPreferences preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return preferences.getLong(key, 0);
    }

    private void setDouble(String key, double val) {
        if (context == null) {
            Log.e(TAG, "Context was null when trying to get preferences.");
          //  logger.error("Context was null when trying to get preferences.");
            return;
        }
        SharedPreferences preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        preferences.edit().putLong(key, Double.doubleToRawLongBits(val)).apply();
    }

    private void setLong(String key, long val) {
        if (context == null) {
            Log.e(TAG, "Context was null when trying to get preferences.");
//            logger.error("Context was null when trying to get preferences.");
            return;
        }
        SharedPreferences preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        preferences.edit().putLong(key, val).apply();
    }

    private boolean getBoolean(String key) {
        if (context == null) {
            Log.e(TAG, "Context was null when trying to get preferences.");
//            logger.error("Context was null when trying to get preferences.");
            return false;
        }
        SharedPreferences preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return preferences.getBoolean(key, false);

    }

    private void setBoolean(String key, boolean val) {
        if (context == null) {
            Log.e(TAG, "Context was null when trying to get preferences.");
//            logger.error("Context was null when trying to get preferences.");
            return;
        }
        SharedPreferences preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        preferences.edit().putBoolean(key, val).apply();

    }



     String getServerUrl() {
        if (getBoolean(USE_TEST_SERVER_KEY)){
            return TEST_SERVER_URL;
        }else{
             return PRODUCTION_SERVER_URL;
            }
     }

    String getTestServerUrl() {
            return TEST_SERVER_URL;
    }


    String getPassword() {
        return getString(PASSWORD_KEY);
    }

    void setPassword(String password) {
        setString(PASSWORD_KEY, password);
    }

    String getDeviceName() {
        return getString(DEVICE_NAME_KEY);
    }

    void setDeviceName(String name) {
        setString(DEVICE_NAME_KEY, name);
    }

    void setToken(String token){
        setString(TOKEN_KEY, token);
    }

    String getToken(){
        return getString(TOKEN_KEY);
    }

    long getTokenLastRefreshed(){
        return getLong(TOKEN_LAST_REFRESHED_KEY);
    }

    void setTokenLastRefreshed(long timeTokenLastRefreshed){
        setLong(TOKEN_LAST_REFRESHED_KEY, timeTokenLastRefreshed);
    }

    String getGroupName() {
        return getString(GROUP_NAME_KEY);
    }

    void setGroupName(String name) {
        setString(GROUP_NAME_KEY, name);
    }

    double getLatitude() {
        return getDouble(LATITUDE_KEY);
    }

    void setLatitude(double val) {
        setDouble(LATITUDE_KEY, val);
    }

    double getLongitude() {
        return getDouble(LONGITUDE_KEY);
    }

    void setLongitude(double val) {
        setDouble(LONGITUDE_KEY, val);
    }

    void setDeviceId(String deviceID) {
        setString(DEVICE_ID, deviceID);
    }

    double getRecordingDuration() {
        return getDouble(RECORDING_DURATION_SECONDS_KEY);
    }

    void setRecordingDurationSeconds() {
        setDouble(RECORDING_DURATION_SECONDS_KEY, RECORDING_DURATION_SECONDS);
    }

    double getTimeBetweenRecordingsSeconds() {
        if (getBoolean(USE_VERY_FREQUENT_RECORDINGS_KEY)){
            return getDouble(TIME_BETWEEN_VERY_FREQUENT_RECORDINGS_SECONDS_KEY);
        }else  if (getBoolean(USE_FREQUENT_RECORDINGS_KEY)){
                return getDouble(TIME_BETWEEN_FREQUENT_RECORDINGS_SECONDS_KEY);
        }else{
            return getDouble(TIME_BETWEEN_RECORDINGS_SECONDS_KEY);
        }
    }

    void setTimeBetweenRecordingsSeconds() {
        setDouble(TIME_BETWEEN_RECORDINGS_SECONDS_KEY, TIME_BETWEEN_RECORDINGS_SECONDS);
    }


    void setTimeBetweenVeryFrequentRecordingsSeconds() {
        setDouble(TIME_BETWEEN_VERY_FREQUENT_RECORDINGS_SECONDS_KEY, TIME_BETWEEN_VERY_FREQUENT_RECORDINGS_SECONDS);
    }

    void setTimeBetweenFrequentRecordingsSeconds() {
        setDouble(TIME_BETWEEN_FREQUENT_RECORDINGS_SECONDS_KEY, TIME_BETWEEN_FREQUENT_RECORDINGS_SECONDS);
    }

    double getTimeBetweenUploadsSeconds() {
        if (getBoolean(USE_FREQUENT_UPLOADS_KEY)){
            return getDouble(TIME_BETWEEN_FREQUENT_UPLOADS_SECONDS_KEY);
        }else{
            return getDouble(TIME_BETWEEN_UPLOADS_SECONDS_KEY);
        }
    }

    void setTimeBetweenUploadsSeconds() {
        setDouble(TIME_BETWEEN_UPLOADS_SECONDS_KEY, TIME_BETWEEN_UPLOADS_SECONDS);
    }

    void setTimeBetweenFrequentUploadsSeconds() {
        setDouble(TIME_BETWEEN_FREQUENT_UPLOADS_SECONDS_KEY, TIME_BETWEEN_FREQUENT_UPLOADS_SECONDS);
    }

    double getBatteryLevelCutoffRepeatingRecordings() {
        if (getBoolean(IGNORE_LOW_BATTERY_KEY)){
            return getDouble(IGNORE_BATTERY_LEVEL_CUTOFF_REPEATING_RECORDINGS_KEY);
        }else{
            return getDouble(BATTERY_LEVEL_CUTOFF_REPEATING_RECORDINGS_KEY);
        }
    }

    double getBatteryLevelCutoffDawnDuskRecordings() {
        if (getBoolean(IGNORE_LOW_BATTERY_KEY)){
            return getDouble(IGNORE_BATTERY_LEVEL_CUTOFF_DAWN_DUSK_RECORDINGS_KEY );
        }else{
            return getDouble(BATTERY_LEVEL_CUTOFF_DAWN_DUSK_RECORDINGS_KEY );
        }
    }

    void setBatteryLevelCutoffRepeatingRecordings() {
        setDouble(BATTERY_LEVEL_CUTOFF_REPEATING_RECORDINGS_KEY, BATTERY_LEVEL_CUTOFF_REPEATING_RECORDINGS);
    }

    void setBatteryLevelCutoffDawnDuskRecordings() {
        setDouble(BATTERY_LEVEL_CUTOFF_DAWN_DUSK_RECORDINGS_KEY, BATTERY_LEVEL_CUTOFF_DAWN_DUSK_RECORDINGS);
    }



    double getDawnDuskOffsetMinutes() {
        return getDouble(DAWN_DUSK_OFFSET_MINUTES_KEY);
    }

    void setDawnDuskOffsetMinutes() {
        setDouble(DAWN_DUSK_OFFSET_MINUTES_KEY, DAWN_DUSK_OFFSET_MINUTES);
    }

    double getDawnDuskIncrementMinutes(){
        return getDouble(DAWN_DUSK_INCREMENT_MINUTES_KEY);
    }

    void setDawnDuskIncrementMinutes() {
        setDouble(DAWN_DUSK_INCREMENT_MINUTES_KEY, DAWN_DUSK_INCREMENT_MINUTES);
    }


    double getLengthOfTwilightSeconds() {
        return getDouble(LENGTH_OF_TWILIGHT_KEY);
    }

    void setLengthOfTwilightSeconds() {
        setDouble(LENGTH_OF_TWILIGHT_KEY, LENGTH_OF_TWILIGHT_SECONDS);
    }

    boolean getHasRootAccess() {
        return getBoolean(HAS_ROOT_ACCESS_KEY);
    }


    void setHasRootAccess(boolean hasRootAccess) {
        setBoolean(HAS_ROOT_ACCESS_KEY, hasRootAccess);
    }

    boolean getUseShortRecordings() {
        return getBoolean(USE_SHORT_RECORDINGS_KEY);
    }

    boolean getUseTestServer() {
        return getBoolean(USE_TEST_SERVER_KEY);
    }

    boolean getUseVeryFrequentRecordings() {
        return getBoolean(USE_VERY_FREQUENT_RECORDINGS_KEY);
    }

    boolean getUseFrequentRecordings() {
        return getBoolean(USE_FREQUENT_RECORDINGS_KEY);
    }

    boolean getUseFrequentUploads() {
        return getBoolean(USE_FREQUENT_UPLOADS_KEY);
    }

    boolean getIgnoreLowBattery() {
        return getBoolean(IGNORE_LOW_BATTERY_KEY);
    }

    boolean getOffLineMode() {
        return getBoolean(OFFLINE_MODE_KEY);
    }

    boolean getPlayWarningSound() {
        return getBoolean(PLAY_WARNING_SOUND_KEY);
    }



    void setUseShortRecordings(boolean useShortRecordings) {
        setBoolean(USE_SHORT_RECORDINGS_KEY, useShortRecordings);
    }

    void setUseTestServer(boolean useTestServer) {
        setBoolean(USE_TEST_SERVER_KEY, useTestServer);
    }

    void setUseVeryFrequentRecordings(boolean useVeryFrequentRecordings) {
        setBoolean(USE_VERY_FREQUENT_RECORDINGS_KEY, useVeryFrequentRecordings);
    }

    void setUseFrequentRecordings(boolean useFrequentRecordings) {
        setBoolean(USE_FREQUENT_RECORDINGS_KEY, useFrequentRecordings);
    }

    void setUseFrequentUploads(boolean useFrequentUploads) {
        setBoolean(USE_FREQUENT_UPLOADS_KEY, useFrequentUploads);
    }

    void setIgnoreLowBattery(boolean ignoreLowBattery) {
        setBoolean(IGNORE_LOW_BATTERY_KEY, ignoreLowBattery);
    }



    void setOffLineMode(boolean OffLineMode) {
        setBoolean(OFFLINE_MODE_KEY, OffLineMode);
    }

    void setPlayWarningSound(boolean playWarningSound) {
        setBoolean(PLAY_WARNING_SOUND_KEY, playWarningSound);
    }

    void setBatteryLevel(double batteryLevel) {
        setDouble(BATTERY_LEVEL_KEY, batteryLevel);
    }

    double getMaximumBatteryLevel() {
        return getDouble(MAXIMUM_BATTERY_LEVEL_KEY);
    }

    void setMaximumBatteryLevel(double batteryLevel) {
        setDouble(MAXIMUM_BATTERY_LEVEL_KEY, batteryLevel);
    }

    void setDateTimeLastUpload(long dateTimeLastUpload) {
        setLong(DATE_TIME_LAST_UPLOAD_KEY, dateTimeLastUpload);
    }

    long getDateTimeLastUpload() {
        return getLong(DATE_TIME_LAST_UPLOAD_KEY);
    }

    long getDateTimeLastCalculatedDawnDusk() {
        return getLong(DATE_TIME_LAST_CALCULATED_DAWN_DUSK_KEY);
    }

    void setDateTimeLastCalculatedDawnDusk(long dateTimeLastCalculatedDawnDusk) {
        setLong(DATE_TIME_LAST_CALCULATED_DAWN_DUSK_KEY, dateTimeLastCalculatedDawnDusk);
    }

    long getDateTimeLastRepeatingAlarmFired() {
        return getLong(DATE_TIME_LAST_REPEATING_ALARM_FIRED_KEY);
    }

    void setDateTimeLastRepeatingAlarmFired(long dateTimeLastRepeatingAlarmFired) {
        setLong(DATE_TIME_LAST_REPEATING_ALARM_FIRED_KEY, dateTimeLastRepeatingAlarmFired);
    }


}