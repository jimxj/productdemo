package com.jim.productdemo.product.list.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.jim.productdemo.R;
import com.jim.productdemo.data.Product;
import com.jim.productdemo.product.detail.ProductDetailActivity;
import com.jim.productdemo.utils.DisplayUtil;
import com.jim.productdemo.view.ReviewView;
import com.squareup.picasso.Picasso;

/**
 * Adapter to show product info in a cardview
 */

public class CardProductItemRecyclerViewAdapter extends
    ProductItemRecyclerViewAdapter<CardProductItemRecyclerViewAdapter.ProductViewHolder> {

  private static final int IMAGE_SIZE = 120;

  private static int dpi = 0;

  public CardProductItemRecyclerViewAdapter(Activity context) {
    super(context);
    dpi = DisplayUtil.getDensity(context);
  }

  @Override public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.product_item_card, parent, false);
    return new ProductViewHolder(view);
  }

  @Override public void onBindViewHolder(final ProductViewHolder holder, int position) {
    Product item = mValues.get(position);
    holder.mItem = item;
    holder.mProductNameView.setText(item.getProductName());
    Picasso.with(mContext).load(item.getProductImage())
        .placeholder(R.drawable.ic_placeholder)
        .into(holder.mProductImageView);
    holder.mPriceView.setText(item.getPrice());
    holder.reviewProduct.setReview(item.getReviewRatingRounded(), item.getReviewCount());
    holder.mInstockView.setText(item.isInStock() ? "In Stock" : "Out of Stock");

    holder.mView.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        ProductDetailActivity.startProductDetailActivity(v.getContext(), holder.mItem.getProductId());
      }
      //  if (mTwoPane) {
      //    Bundle arguments = new Bundle();
      //    arguments.putString(ProductDetailFragment.ARG_ITEM_ID, holder.mItem.getProductId());
      //    ProductDetailFragment fragment = new ProductDetailFragment();
      //    fragment.setArguments(arguments);
      //    getSupportFragmentManager().beginTransaction()
      //        .replace(R.id.product_detail_container, fragment)
      //        .commit();
      //  } else {
      //    Context context = v.getContext();
      //    Intent intent = new Intent(context, ProductDetailActivity.class);
      //    intent.putExtra(ProductDetailFragment.ARG_ITEM_ID, holder.mItem.getProductId());
      //
      //    context.startActivity(intent);
      //  }
      //}
    });
  }

  public class ProductViewHolder extends RecyclerView.ViewHolder {
    final View mView;
    @BindView(R.id.tvProductName) TextView mProductNameView;
    @BindView(R.id.ivProduct) ImageView mProductImageView;
    @BindView(R.id.tvPrice) TextView mPriceView;
    @BindView(R.id.tvInstock) TextView mInstockView;
    @BindView(R.id.reviewProduct) ReviewView reviewProduct;
    Product mItem;

    public ProductViewHolder(View view) {
      super(view);
      mView = view;
      ButterKnife.bind(this, view);
    }

    @Override public String toString() {
      return super.toString() + " '" + mProductNameView.getText() + "'";
    }
  }
}
