package com.kaoba.expocr.estimote;

import android.app.Application;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.estimote.coresdk.cloud.google.model.Beacons;
import com.estimote.coresdk.common.config.EstimoteSDK;
import com.google.gson.JsonParser;
import com.kaoba.expocr.R;
import com.kaoba.expocr.activities.ListStandActivity;
import com.kaoba.expocr.constants.Constants;
import com.kaoba.expocr.constants.VolleyCallBack;
import com.kaoba.expocr.models.StandPOJO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class BeaconAppManager extends Application {

    private static final String EXPO_PATH = "exposicions/";

    private boolean beaconNotificationsEnabled = false;
    private ArrayList<Beacon> beaconsExpo;
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

//        BeaconNotificationsManager beaconNotificationsManager = new BeaconNotificationsManager(this);

//        beaconNotificationsManager.addNotification(
//                new Beacon("B9407F30-F5F8-466E-AFF9-25556B57FE6D", 21323, 17231),
//                "Hello, world.",
//                "Goodbye, world.");
        try {
            setBeaconByStand();
//            beaconNotificationsManager.startMonitoring();

        } catch (JSONException e) {
            e.printStackTrace();
        }

        beaconNotificationsEnabled = true;
    }

    public boolean isBeaconNotificationsEnabled() {
        return beaconNotificationsEnabled;
    }

    public void setBeaconByStand() throws JSONException {
        Constants constants = new Constants();
        final RequestQueue queue = Volley.newRequestQueue(this);
        constants.executeGetRequest(new VolleyCallBack() {
            @Override
            public void onSuccess(JSONObject response) {
                try {
                    BeaconNotificationsManager beaconNotificationsManager = new BeaconNotificationsManager(getApplicationContext());

                    JSONObject object;
                    JSONArray jsonArray = response.getJSONArray("stands");
                    for(int i = 0; i < jsonArray.length(); i++){
                        object = jsonArray.getJSONObject(i);

                        String beaconsIds[] = object.getJSONObject("beacon").getString("uuid").split(":");
                        beaconNotificationsManager.addNotification(
                                new Beacon(beaconsIds[0],Integer.parseInt(beaconsIds[1]),Integer.parseInt(beaconsIds[2])),object.getString("nombre"),object.getString("tipo"),object.getString("id"));

                        Log.d("Ne",object.getJSONObject("beacon").getString("uuid"));
                    }

                    beaconNotificationsManager.startMonitoring();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onSuccessList(JSONArray response) {

            }

            @Override
            public void onError(String error) {

            }
        }, queue, "exposicions/1");/** Require id SESSION */
    }

}
