package com.jim.productdemo.data.local;

import android.provider.BaseColumns;

public class ProductDataConstants {
  public static final String AUTHORITY = "com.jim.productdemo.provider";

  public static final String DATA_TYPE = "product";

  //public static final String PROPERTY_ID = BaseColumns._ID ;
  public static final String PROPERTY_PRODUCT_ID = "product_id";
  public static final String PROPERTY_NAME = "name";
  public static final String PROPERTY_SHORT_DESC = "short_desc";
  public static final String PROPERTY_LONG_DESC = "long_desc";
  public static final String PROPERTY_PRICE = "price";
  public static final String PROPERTY_IMAGE = "image";
  public static final String PROPERTY_REVIEW_COUNT = "review_ount";
  public static final String PROPERTY_RATING = "rating";
  public static final String PROPERTY_IN_STOCK = "in_stock";
}
