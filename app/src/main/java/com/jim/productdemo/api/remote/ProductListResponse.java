package com.jim.productdemo.api.remote;

import com.jim.productdemo.data.Product;
import java.util.List;

/**
 * The response of product list API
 */
public class ProductListResponse {
  private List<Product> products;
  private int totoalProducts;
  private int pageNumber;
  private int pageSize;
  private int status;
  private String kind;
  private String etag;

  public List<Product> getProducts() {
    return products;
  }

  public int getTotoalProducts() {
    return totoalProducts;
  }

  public int getPageNumber() {
    return pageNumber;
  }

  public int getPageSize() {
    return pageSize;
  }

  public int getStatus() {
    return status;
  }

  public String getKind() {
    return kind;
  }

  public String getEtag() {
    return etag;
  }
}
