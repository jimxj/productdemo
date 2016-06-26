package com.jim.productdemo;

import android.app.Application;
import android.content.Context;


public class ProductDemoApplication extends Application {

  private static Context appContext;
  @Override
  public void onCreate() {
    super.onCreate();
    appContext = this;
  }

  public static Context getAppContext() {
    return appContext;
  }

}
