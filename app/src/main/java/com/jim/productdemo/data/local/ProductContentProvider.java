package com.jim.productdemo.data.local;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;
import com.jim.productdemo.data.ProductDataSource;

public class ProductContentProvider extends ContentProvider {
  private static final String TAG = ProductContentProvider.class.getSimpleName();

  private ProductSqliteOpenHelper sqliteOpenHelper;

  private static final String[] allColums = new String[] {
      ProductDataConstants.PROPERTY_PRODUCT_ID,
      ProductDataConstants.PROPERTY_NAME,
      ProductDataConstants.PROPERTY_PRICE,
      ProductDataConstants.PROPERTY_SHORT_DESC,
      ProductDataConstants.PROPERTY_LONG_DESC,
      ProductDataConstants.PROPERTY_RATING,
      ProductDataConstants.PROPERTY_REVIEW_COUNT
  };

  public static final int ITEM_ALL = 1;
  public static final int ITEM_ID  = 2;

  public static final Uri CONTENT_URI = Uri.parse("content://" + ProductDataConstants.AUTHORITY +"/" + ProductDataConstants.DATA_TYPE);

  private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);
  static {
    sURIMatcher.addURI(ProductDataConstants.AUTHORITY, ProductDataConstants.DATA_TYPE, ITEM_ALL);
    sURIMatcher.addURI(ProductDataConstants.AUTHORITY, ProductDataConstants.DATA_TYPE + "/#", ITEM_ID);
  }

  public ProductContentProvider() {
  }

  @Override public int delete(Uri uri, String selection, String[] selectionArgs) {
    SQLiteDatabase db = sqliteOpenHelper.getWritableDatabase();
    //db.beginTransaction();
    //int result = 0;
    //try {
    //  result = db.delete(ProductDataConstants.DATA_TYPE, selection, selectionArgs);
    //  db.setTransactionSuccessful();
    //} finally {
    //  db.endTransaction();
    //  db.close();
    //}

    int count = 0;
    switch (sURIMatcher.match(uri)) {
      case ITEM_ALL:
        count = db.delete(ProductDataConstants.DATA_TYPE, selection, selectionArgs);
        Log.d(TAG, "delete ITEM uri=" + uri + ", count=" + count);
        break;
      case ITEM_ID:
        String id = uri.getPathSegments().get(1);
        count = db.delete(ProductDataConstants.DATA_TYPE, ProductDataConstants.PROPERTY_PRODUCT_ID + "=?", new String[]{id});
        Log.d(TAG, "delete ITEM_ID id=" + id +", uri=" + uri + ", count=" + count);
        break;
      default:
        throw new IllegalArgumentException("Unknown URI" + uri);
    }

    getContext().getContentResolver().notifyChange(uri, null);

    return count;
  }

  @Override public String getType(Uri uri) {
    int match = sURIMatcher.match(uri);
    switch (match) {
      case ITEM_ALL:
        return "vnd.android.cursor.dir/" + ProductDataConstants.DATA_TYPE;
      case ITEM_ID:
        return "vnd.android.cursor.item/" + ProductDataConstants.DATA_TYPE;
      default:
        return null;
    }
  }

  @Override public Uri insert(Uri uri, ContentValues values) {
    if (sURIMatcher.match(uri) != ITEM_ALL) {
      throw new IllegalArgumentException("Unknown URI"+uri);
    }

    SQLiteDatabase db = sqliteOpenHelper.getWritableDatabase();
    db.beginTransaction();
    try {
      long rowId = db.insert(ProductDataConstants.DATA_TYPE, null, values);
      if(rowId > 0) {
        Uri newUri = ContentUris.withAppendedId(CONTENT_URI, rowId);
        getContext().getContentResolver().notifyChange(newUri, null);
        Log.d(TAG, "insert uri = " + uri);
        return newUri;
      }
      return Uri.parse("content://" + ProductDataConstants.AUTHORITY + "/" + ProductDataConstants.DATA_TYPE + "/" + String.valueOf(values.get(ProductDataConstants.PROPERTY_PRODUCT_ID)));
    } catch (Throwable e) {
      Log.e(TAG, "Failed to insert", e);
    } finally {
      db.close();
    }

    return null;
  }

  @Override public boolean onCreate() {
    sqliteOpenHelper = new ProductSqliteOpenHelper(getContext());
    return true;
  }

  @Override
  public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
    SQLiteDatabase db = sqliteOpenHelper.getReadableDatabase();
    switch (sURIMatcher.match(uri)) {
      case ITEM_ALL:
        return db.query(ProductDataConstants.DATA_TYPE, projection, selection, selectionArgs, null, null, sortOrder);
      case ITEM_ID:
        String id = uri.getPathSegments().get(1);
        return db.query(ProductDataConstants.DATA_TYPE, projection, ProductDataConstants.PROPERTY_PRODUCT_ID + "=?", new String[]{id}, null, null, sortOrder);
      default:
        throw new IllegalArgumentException("Unknown URI" + uri);
    }
  }

  @Override public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
    SQLiteDatabase db = sqliteOpenHelper.getWritableDatabase();

    int count = 0;
    switch (sURIMatcher.match(uri)) {
      case ITEM_ALL:
        count = db.update(ProductDataConstants.DATA_TYPE, values, selection, selectionArgs);
        Log.d(TAG, "update ITEM uri=" + uri + ", count=" + count);
        break;
      case ITEM_ID:
        String id = uri.getPathSegments().get(1);
        count = db.update(ProductDataConstants.DATA_TYPE, values, ProductDataConstants.PROPERTY_PRODUCT_ID + "=?", new String[]{id});
        Log.d(TAG, "update ITEM_ID id=" + id +", uri=" + uri + ", count=" + count);
        break;
      default:
        throw new IllegalArgumentException("Unknown URI" + uri);
    }

    getContext().getContentResolver().notifyChange(uri, null);

    return count;
  }
}
