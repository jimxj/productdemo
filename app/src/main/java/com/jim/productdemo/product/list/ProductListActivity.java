package com.jim.productdemo.product.list;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.google.firebase.crash.FirebaseCrash;
import com.jim.productdemo.Constants;
import com.jim.productdemo.R;
import com.jim.productdemo.api.ApiCallback;
import com.jim.productdemo.api.ApiError;
import com.jim.productdemo.data.Product;
import com.jim.productdemo.data.ProductDataSource;
import com.jim.productdemo.data.ProductRepository;
import com.jim.productdemo.product.detail.ProductDetailActivity;
import com.jim.productdemo.product.list.adapter.CardProductItemRecyclerViewAdapter;
import com.jim.productdemo.product.list.adapter.ProductItemRecyclerViewAdapter;
import com.jim.productdemo.utils.DisplayUtil;
import com.jim.productdemo.utils.EndlessRecyclerViewScrollListener;
import java.util.List;

/**
 * An activity to show a list of Products. When one item is touched,
 * it will navigate to a {@link ProductDetailActivity} to show the
 * product details.
 */
public class ProductListActivity extends AppCompatActivity {
  private static final String TAG = ProductListActivity.class.getSimpleName();

  private ProductItemRecyclerViewAdapter mRecyclerViewAdapter;

  private ProductDataSource mProductRepository;

  private int pageSize;

  @BindView(R.id.product_list)
  RecyclerView mRvProducts;

  @BindView(R.id.srProducts)
  SwipeRefreshLayout mSrProducts;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_product_list);

    ButterKnife.bind(this);

    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    toolbar.setTitle(getTitle());

    mProductRepository = ProductRepository.getInstance();
    assert mRvProducts != null;

    setupRecyclerView();

    setUpSwipeRefresh();

    mSrProducts.post(new Runnable() {
      @Override public void run() {
        fetchData(0);
        mSrProducts.setRefreshing(true);
      }
    });
  }

  private void setupRecyclerView() {
    mRecyclerViewAdapter = new CardProductItemRecyclerViewAdapter(this);
    mRvProducts.setAdapter(mRecyclerViewAdapter);

    RecyclerView.LayoutManager layoutManager = null;
    if(!DisplayUtil.isTablet(this)) {
      pageSize = Constants.DEFAULT_PAGE_SIZE;
      layoutManager = new LinearLayoutManager(this);
    } else {
      pageSize = Constants.DEFAULT_PAGE_SIZE * 2;
      layoutManager = new GridLayoutManager(this, 2);
    }
    mRvProducts.setLayoutManager(layoutManager);
    mRvProducts.addOnScrollListener(new EndlessRecyclerViewScrollListener(layoutManager) {
      @Override
      public void onLoadMore(int page, int totalItemsCount) {
        Log.d(TAG, "Loading page " + page + " , totalItemsCount " + totalItemsCount);
        fetchData(page);
      }
    });
  }

  private void setUpSwipeRefresh() {
    mSrProducts.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
      @Override
      public void onRefresh() {
        fetchData(0);
      }
    });

    mSrProducts.setColorSchemeResources(android.R.color.holo_blue_bright,
        android.R.color.holo_green_light,
        android.R.color.holo_orange_light,
        android.R.color.holo_red_light);
  }

  private void fetchData(final int page) {
    mProductRepository.getProducts(page * pageSize, pageSize, new ApiCallback<List<Product>>() {
      @Override public void onSuccess(List<Product> result) {
        Log.d(TAG, "fetched items " + result.size());
        if(null != mRecyclerViewAdapter) {
          mRecyclerViewAdapter.addData(result);
          finishFetch();
        }
      }

      @Override public void onFailure(ApiError apiError) {
        Log.e(TAG, "Failed to fetch product data : " + apiError);
        finishFetch();
      }

      private void finishFetch() {
        if(null != mSrProducts) {
          if (page == 0) {
            mSrProducts.setRefreshing(false);
          }
        }
      }
    });
  }
}
