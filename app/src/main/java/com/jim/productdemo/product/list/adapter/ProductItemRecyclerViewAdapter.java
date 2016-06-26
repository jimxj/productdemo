package com.jim.productdemo.product.list.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.jim.productdemo.R;
import com.jim.productdemo.data.Product;
import com.jim.productdemo.product.detail.ProductDetailActivity;
import com.jim.productdemo.product.detail.ProductDetailFragment;
import com.jim.productdemo.product.list.ProductListActivity;
import java.util.ArrayList;
import java.util.List;

/**
 * Copyright (c) 2012-2016 Magnet Systems. All rights reserved.
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
