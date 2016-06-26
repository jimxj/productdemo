package com.jim.productdemo.api;

/**
 * The callback of async API call
 */

public interface ApiCallback<T> {
  void onSuccess(T result);

  void onFailure(ApiError apiError);
}
