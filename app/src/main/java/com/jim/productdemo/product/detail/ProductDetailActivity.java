package com.jim.productdemo.product.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.jim.productdemo.R;

/**
 * An activity to show the details of a product detail screen.
 * The actual content is shown in a Fragment {@link ProductDetailFragment}
 */
public class ProductDetailActivity extends AppCompatActivity {

  @BindView(R.id.toolbar)
  Toolbar mToolbar;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_product_detail);

    ButterKnife.bind(this);

    setSupportActionBar(mToolbar);
    mToolbar.setTitle("Product Details");

    FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
    fab.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        Snackbar.make(view, "Product added to your shopping cart", Snackbar.LENGTH_LONG)
            .setAction("Action", null)
            .show();
      }
    });

    // Show the Up button in the action bar.
    ActionBar actionBar = getSupportActionBar();
    if (actionBar != null) {
      actionBar.setDisplayHomeAsUpEnabled(true);
    }

    // savedInstanceState is non-null when there is fragment state
    // saved from previous configurations of this activity
    // (e.g. when rotating the screen from portrait to landscape).
    // In this case, the fragment will automatically be re-added
    // to its container so we don't need to manually add it.
    // For more information, see the Fragments API guide at:
    //
    // http://developer.android.com/guide/components/fragments.html
    //
    if (savedInstanceState == null) {
      Bundle arguments = new Bundle();
      arguments.putString(ProductDetailFragment.ARG_ITEM_ID, getIntent().getStringExtra(ProductDetailFragment.ARG_ITEM_ID));
      ProductDetailFragment fragment = new ProductDetailFragment();
      fragment.setArguments(arguments);
      getSupportFragmentManager().beginTransaction().add(R.id.product_detail_container, fragment).commit();
    }
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();
    if (id == android.R.id.home) {
      // This ID represents the Home or Up button. In the case of this
      // activity, the Up button is shown. For
      // more details, see the Navigation pattern on Android Design:
      //
      // http://developer.android.com/design/patterns/navigation.html#up-vs-back
      //
      //navigateUpTo(new Intent(this, ProductListActivity.class));
      finish();
      return true;
    }
    return super.onOptionsItemSelected(item);
  }

  public static void startProductDetailActivity(Context context, String productId) {
    Intent intent = new Intent(context, ProductDetailActivity.class);
    intent.putExtra(ProductDetailFragment.ARG_ITEM_ID, productId);

    context.startActivity(intent);
  }
}
