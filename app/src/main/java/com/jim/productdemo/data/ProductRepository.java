package com.jim.productdemo.data;

import android.content.ContentResolver;
import android.net.Uri;
import com.jim.productdemo.ProductDemoApplication;
import com.jim.productdemo.api.ApiCallback;
import com.jim.productdemo.api.ApiError;
import com.jim.productdemo.data.local.ProductDataConstants;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * The repository to retrieve product data which abstracts the implementation
 *
 */
public class ProductRepository implements ProductDataSource {

  private static ProductRepository sInstance = null;

  private ProductDataSource mRemoteDataSource;

  // In memory cash
  LinkedHashMap<String, Product> mCache;

  public static final Uri CONTENT_URI = Uri.parse("content://" + ProductDataConstants.AUTHORITY + "/" + ProductDataConstants.DATA_TYPE);

  private ContentResolver mContentResolver = null;

  public static ProductRepository getInstance() {
    if(null == sInstance) {
      sInstance = new ProductRepository();
    }

    return sInstance;
  }

  private ProductRepository() {
    mRemoteDataSource = ProductRemoteDataSource.getInstance();

    mCache = new LinkedHashMap<>();

    mContentResolver = ProductDemoApplication.getAppContext().getContentResolver();
  }

  @Override public void getProducts(final int offset, int limit, final ApiCallback<List<Product>> callback) {
    if(0 != offset) {
      List<Product> cachedResult = getFromCache(offset, limit);
      if (null != cachedResult && !cachedResult.isEmpty()) {
        if (null != callback) {
          callback.onSuccess(cachedResult);
        }

        return;
      }
    }

    mRemoteDataSource.getProducts(offset, limit, new ApiCallback<List<Product>>() {
      @Override public void onSuccess(List<Product> result) {
        if(0 == offset) {
          mCache.clear();
        }
        for(Product p : result) {
          mCache.put(p.getProductId(), p);
        }

        if(null != callback) {
          callback.onSuccess(result);
        }
      }

      @Override public void onFailure(ApiError apiError) {
        if(null != callback) {
          callback.onFailure(apiError);
        }
      }
    });
  }

  @Override public void getProduct(String id, ApiCallback<Product> callback) {
    if(null != callback) {
      Product product = mCache.get(id);
      if(null != product) {
        callback.onSuccess(product);
      } else {
        callback.onFailure(new ApiError("Product is not found with id " + id ));
      }
    }
  }

  @Override public void getPreviousProduct(String id, ApiCallback<Product> callback) {
    getProductFromCacheByOffset(id, true, callback);
  }

  @Override public void getNextProduct(String id, ApiCallback<Product> callback) {
    getProductFromCacheByOffset(id, false, callback);
  }

  private List<Product> getFromCache(int offset, int limit) {
    if(mCache.size() <= offset + limit) {
      return null;
    }

    List<Product> result = new ArrayList<>(limit);

    Iterator<String> keyIt = mCache.keySet().iterator();
    int count = 0;
    while(keyIt.hasNext()){
      String key = keyIt.next();
      if(count < offset) {
        count++;
        continue;
      }
      result.add(mCache.get(key));
      count++;
    };

    return result;
  }

  private void getProductFromCacheByOffset(String productId, boolean isPrevious, ApiCallback<Product> callback) {
    Iterator<String> keyIt = mCache.keySet().iterator();
    Product result = null;
    Product previous = null;
    boolean found = false;
    while(keyIt.hasNext()){
      String key = keyIt.next();

      Product p = mCache.get(key);

      if(found) {
        if(!isPrevious) {
          result = p;
          break;
        }
      }

      if(p.getProductId().equals(productId)) {
        found = true;
        if (isPrevious) {
          result = previous;
          break;
        }
      }
      previous = p;
    };

    if(null != callback) {
      if(null != result) {
        callback.onSuccess(result);
      } else {
        callback.onFailure(new ApiError("Failed to find " + (isPrevious ? "Previous" : "Next" + " for " + productId)));
      }
    }
  }
}
