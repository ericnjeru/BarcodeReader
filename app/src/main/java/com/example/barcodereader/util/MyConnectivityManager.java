package com.example.barcodereader.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;
import android.util.Log;

public class MyConnectivityManager {

    private static MyConnectivityManager instance;
    private Context context;
    private static final String TAG = "CheckConnectivity";

    public MyConnectivityManager(Context context) {
        this.context = context;
    }

    public static synchronized MyConnectivityManager getInstance(Context context) {
        if (instance == null) {
            instance = new MyConnectivityManager(context);
        }
        return instance;
    }


    /*
     * TODO: Required permission android.permission.ACCESS_NETWORK_STATE
     * Check if network is connected: Mobile Data or WiFi*/

    private boolean checkNetworkAvailability() {
        ConnectivityManager connMgr =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        boolean isWifiConn = false;
        boolean isMobileConn = false;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            for (Network network : connMgr.getAllNetworks()) {
                NetworkInfo networkInfo = connMgr.getNetworkInfo(network);
                if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                    isWifiConn |= networkInfo.isConnected();
                }
                if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                    isMobileConn |= networkInfo.isConnected();
                }
            }
        }else {
            NetworkInfo activeNetwork = connMgr.getActiveNetworkInfo();

            if (activeNetwork != null) {
                // connected to the internet
                if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                    isWifiConn = activeNetwork.isConnected();
                } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                    isMobileConn = activeNetwork.isConnected();
                }
            }
        }
        Log.d(TAG, "Wifi connected: " + isWifiConn);
        Log.d(TAG, "Mobile connected: " + isMobileConn);

        return isMobileConn || isWifiConn;

    }

    /*Check if can actually connect to the internet*/
    public boolean isOnline() {
        android.net.ConnectivityManager connMgr = (android.net.ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected() && checkNetworkAvailability());
    }
}
