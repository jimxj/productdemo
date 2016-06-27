package com.jim.productdemo.product.list.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import com.jim.productdemo.data.Product;
import java.util.ArrayList;
import java.util.List;

/**
 * Base adapter for product list
 * @param <T>
 */
public abstract class ProductItemRecyclerViewAdapter<T extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<T> {
  protected final List<Product> mValues;
  protected final Context mContext;

  //abstract int getItemLayoutResId();

  public ProductItemRecyclerViewAdapter(Context context) {
    mContext = context;
    mValues = new ArrayList<>();
  }

  public void addData(List<Product> data) {
    int originalDataSize = mValues.size();

    mValues.addAll(data);

    notifyItemRangeInserted(originalDataSize, data.size());
  }

  @Override public int getItemCount() {
    return mValues.size();
  }
}
