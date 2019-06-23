package com.popular.movies.data.network;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Helper class for Internet Connection check (singleton pattern).
 */
public class ConnectionHelper {
    private static final ConnectionHelper ourInstance = new ConnectionHelper();

    public static ConnectionHelper getInstance() {
        return ourInstance;
    }

    private ConnectionHelper() {
    }

    /**
     * Checks if there is an Internet connection.
     *
     * @return internet connection exists
     */
    public boolean isInternetConnection(Application application) {
        ConnectivityManager connMgr = (ConnectivityManager)
                application.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = null;
        if (connMgr != null) {
            networkInfo = connMgr.getActiveNetworkInfo();
        }

        return (networkInfo != null && networkInfo.isConnected());

    }
}
