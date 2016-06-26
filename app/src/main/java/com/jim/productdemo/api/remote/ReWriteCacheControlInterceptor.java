package com.jim.productdemo.api.remote;

import com.jim.productdemo.ProductDemoApplication;
import com.jim.productdemo.utils.ConnectivityUtil;
import java.io.IOException;
import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * OKHTTP interceptor to overwrite cache policy to support offline
 */

public class ReWriteCacheControlInterceptor implements Interceptor {
  private static  final int maxStale = 60 * 60 * 24 * 1; // tolerate 1 day stale
  private static  final int maxAge = 60; // 1 mins

  @Override public Response intercept(Chain chain) throws IOException {
    Response originalResponse = chain.proceed(chain.request());
    if (ConnectivityUtil.isConnected(ProductDemoApplication.getAppContext())) {
      return originalResponse.newBuilder()
          .header("Cache-Control", "public, max-age=" + maxAge)
          .build();
    } else {
      return originalResponse.newBuilder()
          .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
          .build();
    }
  }
}
