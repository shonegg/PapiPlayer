package com.papi.player.net;

/**
 * Author   Shone
 * Date     04/07/16.
 * Github   https://github.com/shonegg
 */

import android.content.Context;
import android.net.NetworkInfo;

import java.util.Observable;
import java.util.Observer;

public class NetObservable extends Observable {
    private final String TAG = "FSNetObservable";
    private Context context;

    public NetObservable(Context context) {
        super();
        this.context = context;
    }

    public void addObserver(Observer observer) {
        try {
            super.addObserver(observer);
            NetworkInfo networkInfo = Network.getCurrentActiveNetwork(this.context);
            if (networkInfo != null) {
                if (!networkInfo.isAvailable()) {
                    observer.update(this, new NetAction(false, false, false));
                    return;
                }

                if (networkInfo.getType() == 1) {
                    observer.update(this, new NetAction(true, true, true));
                    return;
                }

                observer.update(this, new NetAction(true, false, false));
                return;
            }

            observer.update(this, new NetAction(false, false, false));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void notifyObservers(Object data) {
        try {
            this.setChanged();
            super.notifyObservers(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}