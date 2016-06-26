package com.jim.productdemo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import com.jim.productdemo.R;
import java.util.Arrays;
import java.util.List;

/**
 * A custom view to display review
 */

public class ReviewView extends LinearLayout {
  public static final int MAX_SCORE = 5;

  ImageView ivStar1;
  ImageView ivStar2;
  ImageView ivStar3;
  ImageView ivStar4;
  ImageView ivStar5;

  List<ImageView> reviewstars;

  TextView tvNumber;

  public ReviewView(Context context) {
    super(context, null);
  }

  public ReviewView(Context context, AttributeSet attrs) {
    super(context, attrs);

    setOrientation(LinearLayout.HORIZONTAL);
    setGravity(Gravity.CENTER_VERTICAL);

    LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    View view = inflater.inflate(R.layout.custom_review, this, true);

    ivStar1 = (ImageView) view.findViewById(R.id.ivStar1);
    ivStar2 = (ImageView) view.findViewById(R.id.ivStar2);
    ivStar3 = (ImageView) view.findViewById(R.id.ivStar3);
    ivStar4 = (ImageView) view.findViewById(R.id.ivStar4);
    ivStar5 = (ImageView) view.findViewById(R.id.ivStar5);

    reviewstars = Arrays.asList(ivStar1, ivStar2, ivStar3, ivStar4, ivStar5);

    tvNumber = (TextView) view.findViewById(R.id.tvReview);
  }

  public void setReview(int score, int number) {
    if(score < 0) {
      score = 0;
    }
    if(score > MAX_SCORE) {
      score = MAX_SCORE;
    }

    int i = 0;
    for(; i < score; i++) {
      reviewstars.get(i).setImageResource(R.drawable.ic_star);
    }
    for(; i < MAX_SCORE; i++) {
      reviewstars.get(i).setImageResource(R.drawable.ic_star_gray);
    }

    tvNumber.setText("(" + number + ")");
  }
}
