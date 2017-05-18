package com.example.mohamed.project;

import android.content.Context;
import android.net.ConnectivityManager;

import static android.content.Context.CONNECTIVITY_SERVICE;

/**
 * Created by mohamed on 5/18/2017.
 */

public class CheckConnection {
    static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }
    public  static boolean isNetworkAvailableAndConnected(Context context){
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);

        boolean isAvailable=cm.getActiveNetworkInfo()!=null;
        boolean isConnected=isAvailable && cm.getActiveNetworkInfo().isConnected();
        return isConnected;
    }
}
