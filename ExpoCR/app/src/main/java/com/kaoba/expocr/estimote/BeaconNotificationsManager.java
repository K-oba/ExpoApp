package com.kaoba.expocr.estimote;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.estimote.coresdk.observation.region.beacon.BeaconRegion;
import com.estimote.coresdk.service.BeaconManager;
import com.kaoba.expocr.activities.BrochureActivity;
import com.kaoba.expocr.activities.WelcomeActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class BeaconNotificationsManager {

    private static final String TAG = "BeaconNotifications";

    private BeaconManager beaconManager;

    private List<BeaconRegion> regionsToMonitor = new ArrayList<>();
    private HashMap<String, String> enterMessages = new HashMap<>();
    private HashMap<String, String> exitMessages = new HashMap<>();
    private HashMap<String, String> standIds = new HashMap<>();

    private Context context;

    private int notificationID = 0;

    public BeaconNotificationsManager(Context context) {
        this.context = context;
        beaconManager = new BeaconManager(context);
        beaconManager.setMonitoringListener(new BeaconManager.BeaconMonitoringListener() {
            @Override
            public void onEnteredRegion(BeaconRegion region, List<com.estimote.coresdk.recognition.packets.Beacon> list) {
                Log.d(TAG, "onEnteredRegion: " + region.getIdentifier());
                String titulo = enterMessages.get(region.getIdentifier());
                String descripcion = exitMessages.get(region.getIdentifier());
                String idstand = standIds.get(region.getIdentifier());
                if (titulo != null) {
                    if (descripcion == null) descripcion = "see more";
                    showNotification(titulo,descripcion,idstand);
                }
            }

            @Override
            public void onExitedRegion(BeaconRegion region) {
                Log.d(TAG, "onExitedRegion: " + region.getIdentifier());
//                String message = exitMessages.get(region.getIdentifier());
//                if (message != null) {
//                    showNotification(message);
//                }
            }
        });

//        beaconManager.setRangingListener(new BeaconManager.BeaconRangingListener() {
//            @Override
//            public void onBeaconsDiscovered(BeaconRegion beaconRegion, List<com.estimote.coresdk.recognition.packets.Beacon> list) {
//                Log.d(TAG,"te vi!");
//            }
//        });
    }

    public void addNotification(Beacon beaconID, String enterMessage, String exitMessage) {
        BeaconRegion region = beaconID.toBeaconRegion();
        enterMessages.put(region.getIdentifier(), enterMessage);
        exitMessages.put(region.getIdentifier(), exitMessage);
        regionsToMonitor.add(region);
    }

    public void startMonitoring() {
        beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
            @Override
            public void onServiceReady() {
                for (BeaconRegion region : regionsToMonitor) {
                    beaconManager.startMonitoring(region);

                }

//                beaconManager.startRanging(new BeaconRegion("rid",UUID.fromString("B9407F30-F5F8-466E-AFF9-25556B57FE6D"),null,null));
            }
        });
    }

    private void showNotification(String titulo, String descripcion, String idStand) {
        Intent resultIntent = new Intent(context, BrochureActivity.class);
        resultIntent.putExtra("idStand",idStand);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(
                context, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle(titulo)
                .setContentText(descripcion)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(resultPendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(notificationID++, builder.build());
    }

    public void addNotification(Beacon beacon, String nombre, String tipo, String standId) {
        BeaconRegion region = beacon.toBeaconRegion();
        enterMessages.put(region.getIdentifier(), nombre);
        exitMessages.put(region.getIdentifier(), tipo);
        standIds.put(region.getIdentifier(),standId);
        regionsToMonitor.add(region);

    }
}
