package com.jim.productdemo;

import android.app.Application;
import android.content.Context;
import com.facebook.stetho.Stetho;

public class ProductDemoApplication extends Application {

  private static Context appContext;
  @Override
  public void onCreate() {
    super.onCreate();
    appContext = this;

    if (BuildConfig.DEBUG) {
      Stetho.initializeWithDefaults(this);
    }
  }

  public static Context getAppContext() {
    return appContext;
  }

}
