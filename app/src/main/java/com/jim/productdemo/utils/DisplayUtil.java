package com.jim.productdemo.utils;

import android.app.Activity;
import android.util.DisplayMetrics;
import android.util.Log;

public class DisplayUtil {
  private static final String TAG = DisplayUtil.class.getSimpleName();

  public static boolean isTablet(Activity activity) {
    DisplayMetrics dm = new DisplayMetrics();
    activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
    int width = dm.widthPixels;
    int height = dm.heightPixels;
    int dens = dm.densityDpi;
    double wi = (double) width/(double)dens;
    double hi = (double) height/(double)dens;
    double x = Math.pow(wi,2);
    double y = Math.pow(hi,2);
    double screenInches = Math.sqrt(x+y);
    Log.d(TAG, "Screen width " + width);
    Log.d(TAG, "screenInches " + screenInches);
    return screenInches > 6;
  }
}
