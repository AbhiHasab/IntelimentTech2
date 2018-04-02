package com.education.abhihasabe.intelimenttechnologies.broadcastReceiver;

/**
 * Created by Lincoln on 18/03/16.
 */

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.education.abhihasabe.intelimenttechnologies.dataObjects.Util;

public class ConnectivityReceiver extends BroadcastReceiver {

    public static ConnectivityReceiverListener connectivityReceiverListener;

    public ConnectivityReceiver() {
        super();
    }

    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onReceive(Context context, Intent arg1) {
        Util.scheduleJob(context);
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null
                && activeNetwork.isConnectedOrConnecting();

        if (connectivityReceiverListener != null) {
            connectivityReceiverListener.onNetworkConnectionChanged();
        }
    }

    public interface ConnectivityReceiverListener {
        void onNetworkConnectionChanged();
    }

}