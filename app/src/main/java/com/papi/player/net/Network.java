package com.papi.player.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * Author   Shone
 * Date     04/07/16.
 * Github   https://github.com/shonegg
 */
public class Network {

    public enum Type {
        UNKNOWN, WIFI, MOBILE, MOBILE2G, MOBILE3G, MOBILE4G;
    }

    public static NetworkInfo getCurrentActiveNetwork(Context context) {
        try {
            ConnectivityManager localConnectivityManager = (ConnectivityManager) context.getSystemService("connectivity");

            if (localConnectivityManager == null) {
                return null;
            }

            NetworkInfo networkInfo = localConnectivityManager.getActiveNetworkInfo();
            if (networkInfo == null) {
                return null;
            }
            return networkInfo;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getIPAddress() {
        try {
            Enumeration<NetworkInterface> netWorkInterface = NetworkInterface.getNetworkInterfaces();
            while (netWorkInterface.hasMoreElements()) {
                Enumeration<InetAddress> address = netWorkInterface.nextElement().getInetAddresses();
                while (address.hasMoreElements()) {
                    InetAddress inetAddr = address.nextElement();
                    if (!inetAddr.isLoopbackAddress()) {
                        if (inetAddr instanceof Inet4Address) {
                            return inetAddr.getHostAddress().toString();
                        }
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return "0.0.0.0";
    }

    public static Type getSubType(Context context) {
        Type type = Type.UNKNOWN;
        NetworkInfo netWorkInfo = Network.getCurrentActiveNetwork(context);
        if (netWorkInfo != null && netWorkInfo.getType() == 0) {
            switch (netWorkInfo.getSubtype()) {
                case 1:
                case 2:
                case 4: {
                    return Type.MOBILE2G;
                }
                case 5:
                case 6: {
                    type = Type.MOBILE3G;
                }
            }
            return type;
        }
        return type;
    }

    public static String getSubTypeName(Context context) {
        NetworkInfo netWorkInfo = Network.getCurrentActiveNetwork(context);
        return netWorkInfo != null ? netWorkInfo.getSubtypeName() : null;
    }

    public static Type getType(Context context) {
        NetworkInfo netWork = Network.getCurrentActiveNetwork(context);
        if (netWork != null) {
            switch (netWork.getType()) {
                case 0: {
                    return Type.MOBILE;
                }
                case 1: {
                    return Type.WIFI;
                }
            }
        }
        return Type.UNKNOWN;
    }

    public static String getTypeName(Context context) {
        NetworkInfo net = Network.getCurrentActiveNetwork(context);
        return net != null ? net.getTypeName() : null;
    }

    public static boolean isAvailable(Context context) {
        NetworkInfo net = Network.getCurrentActiveNetwork(context);
        return net != null && net.isAvailable();
    }

    public static boolean isConnected(Context context) {
        NetworkInfo net = Network.getCurrentActiveNetwork(context);
        return net != null && net.isConnected();
    }
}
