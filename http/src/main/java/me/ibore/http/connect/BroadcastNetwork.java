package me.ibore.http.connect;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;

public class BroadcastNetwork implements Network {

    private final Context mContext;
    private final NetworkReceiver mReceiver;

    public BroadcastNetwork(Context context) {
        this.mContext = context.getApplicationContext();
        this.mReceiver = new NetworkReceiver(new NetworkChecker(mContext));

        IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        filter.addAction("android.net.ethernet.STATE_CHANGE");
        filter.addAction("android.net.ethernet.ETHERNET_STATE_CHANGED");
        mContext.registerReceiver(mReceiver, filter);
    }

    @Override
    public boolean isAvailable() {
        return mReceiver.mAvailable;
    }

    public void destroy() {
        mContext.unregisterReceiver(mReceiver);
    }

    private static class NetworkReceiver extends BroadcastReceiver {

        private boolean mAvailable;
        private NetworkChecker mChecker;

        public NetworkReceiver(NetworkChecker checker) {
            this.mChecker = checker;
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            mAvailable = mChecker.isWifiConnected() || mChecker.isWiredConnected() || mChecker.isMobileConnected();
        }
    }
}