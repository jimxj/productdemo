package com.jim.productdemo.api.remote;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Retrofit interface for remote Product API
 */
public interface ProductService {

  @GET("walmartproducts/{apiKey}/{pageNumber}/{pageSize}")
  Call<ProductListResponse> getProducts(@Path("apiKey") String apiKey,
      @Path("pageNumber") int pageNumber,
      @Path("pageSize") int pageSize);
}
