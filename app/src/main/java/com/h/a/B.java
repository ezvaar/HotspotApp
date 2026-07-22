package com.h.a;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

public class B extends BroadcastReceiver {
    private static final String TAG = "HotspotBootReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);

            if (wifiManager == null) return;

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                try {
                    wifiManager.startLocalOnlyHotspot(new WifiManager.LocalOnlyHotspotCallback() {
                        @Override
                        public void onStarted(WifiManager.LocalOnlyHotspotReservation reservation) {
                            super.onStarted(reservation);
                            Log.d(TAG, "Local Hotspot started successfully.");
                        }

                        @Override
                        public void onFailed(int reason) {
                            super.onFailed(reason);
                            Log.e(TAG, "Hotspot start failed with code: " + reason);
                        }
                    }, new Handler(Looper.getMainLooper()));
                } catch (SecurityException e) {
                    Log.e(TAG, "Missing required permissions to start hotspot: " + e.getMessage());
                }
            }
        }
    }
}
