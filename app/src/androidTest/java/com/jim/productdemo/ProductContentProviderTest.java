package com.jim.productdemo;

import android.content.ContentProviderClient;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.RemoteException;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.MediumTest;
import android.support.test.runner.AndroidJUnit4;
import com.jim.productdemo.data.local.ProductDataConstants;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.assertNotNull;

@MediumTest @RunWith(AndroidJUnit4.class)
public class ProductContentProviderTest {

  @Test
  public void testProductContentProvider() throws RemoteException {
    ContentProviderClient
        contentProviderClient = InstrumentationRegistry.getTargetContext().getContentResolver().acquireContentProviderClient(
        ProductDataConstants.AUTHORITY);
    assertNotNull(contentProviderClient);

    ContentValues cv = new ContentValues();
    cv.put(ProductDataConstants.PROPERTY_PRODUCT_ID, "123");
    cv.put(ProductDataConstants.PROPERTY_NAME, "55 Inch TV");
    cv.put(ProductDataConstants.PROPERTY_SHORT_DESC, "A nice TV");
    cv.put(ProductDataConstants.PROPERTY_PRICE, 599);
    cv.put(ProductDataConstants.PROPERTY_RATING, 2.3);
    Uri newItemUri = contentProviderClient.insert(Uri.parse("content://" + ProductDataConstants.AUTHORITY + "/" + ProductDataConstants.DATA_TYPE), cv);

    System.out.println(" newItemUri : " + newItemUri);

    Cursor cursor = contentProviderClient.query(newItemUri, null, null, null, null);
    printCursor(cursor);

    ContentValues updateCv = new ContentValues();
    updateCv.put(ProductDataConstants.PROPERTY_PRICE, 699);
    cv.put(ProductDataConstants.PROPERTY_NAME, "55 Inch TV");
    contentProviderClient.update(newItemUri, updateCv, null, null);

    Cursor cursor2 = contentProviderClient.query(newItemUri, null, null, null, null);
    printCursor(cursor2);
  }

  private void printCursor(Cursor cursor) {
    if(cursor.moveToFirst()) {
      do {
        System.out.print(cursor.getString(1) + " " + cursor.getString(2));
      } while (cursor.moveToNext());
    }
    cursor.close();
  }
}
