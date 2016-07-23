package com.papi.player.net;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.NetworkInfo;

import java.util.Observer;

public class NetMonitor extends BroadcastReceiver {
    private static final String TAG = "NetMonitor";
    private static NetMonitor instance;
    private NetObservable observable;


    public void addObserver(NetObserver observer) {
        this.observable.addObserver(((Observer) observer));
    }

    public void delObserver(NetObserver observer) {
        this.observable.deleteObserver(((Observer) observer));
    }

    public void destory() {
        this.observable.deleteObservers();
    }

    public static NetMonitor getInstance() {
        if (NetMonitor.instance == null) {
            NetMonitor.instance = new NetMonitor();
        }
        return NetMonitor.instance;
    }

    public void init(Context context) {
        this.observable = new NetObservable(context);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        context.registerReceiver(this, intentFilter);
    }

    private void notifyNetState(Context context) {
        try {
            NetworkInfo networkInfo = Network.getCurrentActiveNetwork(context);
            if (networkInfo != null) {
                if (!networkInfo.isAvailable()) {
                    this.observable.notifyObservers(new NetAction(false, false, false));
                    return;
                }

                if (networkInfo.getType() == 1) {
                    this.observable.notifyObservers(new NetAction(true, true, true));
                    return;
                }

                this.observable.notifyObservers(new NetAction(true, false, false));
                return;
            }

            this.observable.notifyObservers(new NetAction(false, false, false));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onReceive(Context context, Intent intent) {
        this.notifyNetState(context);
    }
}

