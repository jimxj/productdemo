package com.jim.productdemo.data;

import android.util.Log;
import com.jim.productdemo.ProductDemoApplication;
import com.jim.productdemo.api.ApiCallback;
import com.jim.productdemo.api.remote.ProductListResponse;
import com.jim.productdemo.api.remote.ProductService;
import com.jim.productdemo.api.remote.ReWriteCacheControlInterceptor;
import java.io.File;
import java.util.Collections;
import java.util.List;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * The product repository which get data from remote RESTful API
 */

public class ProductRemoteDataSource implements ProductDataSource {
  private static final String TAG = ProductRemoteDataSource.class.getSimpleName();

  private static final String BASE_URL = "https://walmartlabs-test.appspot.com/_ah/api/walmart/v1/";
  private static final String API_KEY = "add26e4a-c43e-4ce2-87e8-ebcb41cc6b61";

  private static ProductDataSource sInstance = null;

  private ProductService mProductService;

  public static ProductDataSource getInstance() {
    if(null == sInstance) {
      sInstance = new ProductRemoteDataSource();
    }

    return sInstance;
  }

  private ProductRemoteDataSource() {
    //setup mCache
    File httpCacheDirectory = new File(ProductDemoApplication.getAppContext().getCacheDir(), "responses");
    int cacheSize = 10 * 1024 * 1024; // 10 MiB
    Cache cache = new Cache(httpCacheDirectory, cacheSize);

    OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new ReWriteCacheControlInterceptor())
        .cache(cache).build();

    Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build();
    mProductService = retrofit.create(ProductService.class);
  }

  @Override public void getProducts(int pageNumber, int pageSize, final ApiCallback<List<Product>> callback) {
    Call<ProductListResponse> call = mProductService.getProducts(API_KEY, pageNumber, pageSize);
    call.enqueue(new Callback<ProductListResponse>() {
      @Override public void onResponse(Call<ProductListResponse> call,
          Response<ProductListResponse> response) {
        if(response.isSuccessful()) {
          ProductListResponse productListResponse = response.body();
          if(Log.isLoggable(TAG, Log.DEBUG)) {
            Log.d(TAG, "Got products :\n" + productListResponse);
          }
          if(null != productListResponse) {
            if(null != callback) {
              callback.onSuccess(null != productListResponse.getProducts() ? productListResponse.getProducts() : Collections.EMPTY_LIST);
            }
          }
        } else {
          Log.e(TAG, "Failed to get products due to :" + response.message());
        }
      }

      @Override public void onFailure(Call<ProductListResponse> call, Throwable t) {
        Log.e(TAG, "Failed to get products due to :", t);
      }
    });
  }

  @Override public void getProduct(String id, ApiCallback<Product> callback) {
    throw new UnsupportedOperationException();
  }

  @Override public void getPreviousProduct(String id, ApiCallback<Product> callback) {
    throw new UnsupportedOperationException();
  }

  @Override public void getNextProduct(String id, ApiCallback<Product> callback) {
    throw new UnsupportedOperationException();
  }
}
