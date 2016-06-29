package com.jim.productdemo.data.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class ProductSqliteOpenHelper extends SQLiteOpenHelper {
  public static final String DB_NAME = "product_db";
  public static final int VERSION = 1;

  // Database creation sql statement
  private static final String DATABASE_CREATE = "create table "
      + ProductDataConstants.DATA_TYPE + "("
      //+  BaseColumns._ID + " INTEGER primary key autoincrement, "
      + "_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
      + ProductDataConstants.PROPERTY_PRODUCT_ID + " TEXT NOT NULL,"
      + ProductDataConstants.PROPERTY_NAME + " TEXT NOT NULL,"
      + ProductDataConstants.PROPERTY_PRICE + " REAL NOT NULL,"
      + ProductDataConstants.PROPERTY_SHORT_DESC + " TEXT,"
      + ProductDataConstants.PROPERTY_LONG_DESC + " TEXT,"
      + ProductDataConstants.PROPERTY_IMAGE + " TEXT,"
      + ProductDataConstants.PROPERTY_RATING + " REAL,"
      + ProductDataConstants.PROPERTY_REVIEW_COUNT + " INTEGER"
      + ");";

  public ProductSqliteOpenHelper(Context context) {
    super(context, DB_NAME, null, VERSION);
  }

  @Override public void onCreate(SQLiteDatabase db) {
    db.execSQL(DATABASE_CREATE);
  }

  @Override public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

  }
}
