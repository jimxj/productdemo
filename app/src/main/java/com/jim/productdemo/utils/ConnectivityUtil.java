package com.jim.productdemo.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Utility to get network status
 */

public class ConnectivityUtil {
  /**
   * Get the network info
   * @param context
   * @return
   */
  public static NetworkInfo getNetworkInfo(Context context){
    ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    return cm.getActiveNetworkInfo();
  }

  /**
   * Check if there is any connectivity
   * @param context
   * @return
   */
  public static boolean isConnected(Context context){
    NetworkInfo info = getNetworkInfo(context);
    return (info != null && info.isConnected());
  }
}
