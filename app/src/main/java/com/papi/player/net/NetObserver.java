package com.papi.player.net;

/**
 * Author   Shone
 * Date     04/07/16.
 * Github   https://github.com/shonegg
 */

import java.util.Observable;
import java.util.Observer;

public abstract class NetObserver implements Observer {
    public class NetAction {
        private boolean isAvailable;
        private boolean isWifi;
        private boolean stopUsing = true;

        public NetAction(boolean isAvailable, boolean isWifi, boolean stopUsing) {
            super();
            this.isAvailable = isAvailable;
            this.isWifi = isWifi;
            this.stopUsing = stopUsing;
        }

        public boolean isAvailable() {
            return this.isAvailable;
        }

        public boolean isStopUsing() {
            return this.stopUsing;
        }

        public boolean isWifi() {
            return this.isWifi;
        }
    }

    public abstract void notify(NetAction arg1);

    public void update(Observable observable, Object data) {
        this.notify(((NetAction) data));
    }
}
