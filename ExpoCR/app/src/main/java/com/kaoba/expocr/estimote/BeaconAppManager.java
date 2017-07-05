package com.kaoba.expocr.estimote;

import android.app.Application;

import com.estimote.coresdk.cloud.google.model.Beacons;
import com.estimote.coresdk.common.config.EstimoteSDK;

//
// Running into any issues? Drop us an email to: contact@estimote.com
//

public class BeaconAppManager extends Application {

    private boolean beaconNotificationsEnabled = false;
    private Beacons[] beaconsExpo;
    private Beacon beaconExpo;
    private boolean standsBeacons = false;

    @Override
    public void onCreate() {
        super.onCreate();

        EstimoteSDK.initialize(getApplicationContext(), "notificationbeacon-jst", "f96ca4a225f969818743184a6ae83922");

        // uncomment to enable debug-level logging
        // it's usually only a good idea when troubleshooting issues with the Estimote SDK
//        EstimoteSDK.enableDebugLogging(true);
    }

    public void enableBeaconNotifications() {
        if (beaconNotificationsEnabled) { return; }

        BeaconNotificationsManager beaconNotificationsManager = new BeaconNotificationsManager(this);
        beaconNotificationsManager.addNotification(
                new Beacon("B9407F30-F5F8-466E-AFF9-25556B57FE6D", 21323, 17231),
                "Hello, world.",
                "Goodbye, world.");
        beaconNotificationsManager.startMonitoring();

        beaconNotificationsEnabled = true;
    }

    public boolean isBeaconNotificationsEnabled() {
        return beaconNotificationsEnabled;
    }

    public void loadBeaconsByExpo() {

    }

    public void beaconDistance(){

    }
}
