package com.jim.productdemo.data;

import com.jim.productdemo.api.ApiCallback;
import java.util.List;

/**
 * The interface of how to retrieve product data
 */
public interface ProductDataSource {
  /**
   * List the product
   * @param offset
   * @param limit
   * @return
   */
  void getProducts(int offset, int limit, ApiCallback<List<Product>> callback);

  /**
   * Get the product by id
   * @param id
   * @param callback
   */
  void getProduct(String id, ApiCallback<Product> callback);

  /**
   * Get previous product
   * @param id
   * @param callback
   */
  void getPreviousProduct(String id, ApiCallback<Product> callback);

  /**
   * Get next product
   * @param id
   * @param callback
   */
  void getNextProduct(String id, ApiCallback<Product> callback);
}
