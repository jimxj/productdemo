package com.jim.productdemo.product.detail;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.GestureDetectorCompat;
import android.text.Html;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.jim.productdemo.R;
import com.jim.productdemo.api.ApiCallback;
import com.jim.productdemo.api.ApiError;
import com.jim.productdemo.data.Product;
import com.jim.productdemo.data.ProductRepository;
import com.jim.productdemo.product.list.ProductListActivity;
import com.jim.productdemo.view.ReviewView;
import com.squareup.picasso.Picasso;
import org.sufficientlysecure.htmltextview.HtmlTextView;

/**
 * A fragment representing a single Product detail screen.
 * This fragment is either contained in a {@link ProductListActivity}
 * in two-pane mode (on tablets) or a {@link ProductDetailActivity}
 * on handsets.
 */
public class ProductDetailFragment extends Fragment implements GestureDetector.OnGestureListener {
  private static final String TAG = ProductDetailFragment.class.getSimpleName();
  /**
   * The fragment argument representing the item ID that this fragment
   * represents.
   */
  public static final String ARG_ITEM_ID = "item_id";

  private Product mProduct;
  private String mProductId;

  @BindView(R.id.llContent)
  LinearLayout mContentLayout;

  @BindView(R.id.tvProductName)
  TextView mNameText;

  @BindView(R.id.reviewProduct)
  ReviewView mProductReview;

  @BindView(R.id.ivProduct)
  ImageView mProductImage;

  @BindView(R.id.tvPrice)
  TextView mPriceText;

  @BindView(R.id.tvStock)
  TextView mInStockText;

  @BindView(R.id.tvShortDes)
  HtmlTextView mShortDescText;

  @BindView(R.id.tvDes)
  HtmlTextView mDescText;

  private GestureDetectorCompat mDetector;

  /**
   * Mandatory empty constructor for the fragment manager to instantiate the
   * fragment (e.g. upon screen orientation changes).
   */
  public ProductDetailFragment() {
  }

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    mProductId = getArguments().getString(ARG_ITEM_ID, null);

    setHasOptionsMenu(true);
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.product_detail, container, false);
    ButterKnife.bind(this, rootView);

    setupGestureDetector();

    loadData();

    return rootView;
  }

  @Override public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    inflater.inflate(R.menu.product_detail_menu, menu);

    super.onCreateOptionsMenu(menu, inflater);
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.miFavorite:
        Snackbar.make(getView(), "Added to favorite", Snackbar.LENGTH_LONG)
            .setAction("Action", null)
            .show();
        return true;
      case R.id.miShare:
        shareProduct();
        return true;
      default:
        break;
    }
    return super.onOptionsItemSelected(item);
  }

  private void shareProduct() {
    //if (mShareActionProvider != null) {
    //  mShareActionProvider.setShareIntent(get);
    //}
    startActivity(Intent.createChooser(getDefaultShareIntent(), "Share a product"));
  }

  private Intent getDefaultShareIntent() {
    Intent sharingIntent = new Intent(Intent.ACTION_SEND);
    sharingIntent.setType("text/html");
    sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, Html.fromHtml("<p>\"" + mProduct.getProductName() + "\" from " + mProduct.getProductImage() + "</p>"));
    //startActivity(Intent.createChooser(sharingIntent, "Share a product"));
    return sharingIntent;
  }

  private void setupGestureDetector() {
    mDetector = new GestureDetectorCompat(getContext(),this);
    mContentLayout.setOnTouchListener(new View.OnTouchListener() {
      @Override
      public boolean onTouch(View view, MotionEvent motionEvent) {
        mDetector.onTouchEvent(motionEvent);
        return true;
      }
    });
  }

  private void loadData() {
    if (null != mProductId) {
      ProductRepository.getInstance().getProduct(mProductId, getProductCallback);
    }
  }

  private void setData() {
    mNameText.setText(mProduct.getProductName());
    mProductReview.setReview(mProduct.getReviewRatingRounded(), mProduct.getReviewCount());
    Picasso.with(getContext())
        .load(mProduct.getProductImage())
        .placeholder(R.drawable.ic_placeholder)
        .into(mProductImage);
    mPriceText.setText(mProduct.getPrice());
    mInStockText.setText(mProduct.isInStock() ? "In Stock" : "Out of Stock");
    //mShortDescText.setText(Html.fromHtml(mProduct.getShortDescription()));
    //mDescText.setText(Html.fromHtml(mProduct.getLongDescription()), TextView.BufferType.SPANNABLE);
    mShortDescText.setHtmlFromString(mProduct.getShortDescription(), new HtmlTextView.LocalImageGetter());
    mDescText.setHtmlFromString(mProduct.getLongDescription(), new HtmlTextView.LocalImageGetter());

  }

  @Override public boolean onDown(MotionEvent e) {
    return true;
  }

  @Override public void onShowPress(MotionEvent e) {

  }

  @Override public boolean onSingleTapUp(MotionEvent e) {
    return false;
  }

  @Override
  public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
    return false;
  }

  @Override public void onLongPress(MotionEvent e) {

  }

  @Override
  public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
    if (e1.getX() < e2.getX()) {
      Log.d(TAG, "Left to Right swipe performed");
      loadPreviousProduct();
    }

    if (e1.getX() > e2.getX()) {
      Log.d(TAG, "Right to Left swipe performed");
      loadNextProduct();
    }

    if (e1.getY() < e2.getY()) {
      Log.d(TAG, "Up to Down swipe performed");
    }

    if (e1.getY() > e2.getY()) {
      Log.d(TAG, "Down to Up swipe performed");
    }

    return true;
  }

  private void loadPreviousProduct() {
    ProductRepository.getInstance().getPreviousProduct(mProductId, getProductCallback);
  }

  private void loadNextProduct() {
    ProductRepository.getInstance().getNextProduct(mProductId, getProductCallback);
  }

  private ApiCallback<Product> getProductCallback = new ApiCallback<Product>() {
    @Override public void onSuccess(Product result) {
      mProduct = result;
      mProductId = result.getProductId();
      setData();
    }

    @Override public void onFailure(ApiError apiError) {
      Log.d(TAG, "Failed to load product with id \n" + apiError);
    }
  };
}
