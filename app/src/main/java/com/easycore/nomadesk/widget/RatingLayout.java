package com.easycore.nomadesk.widget;


import android.content.Context;
import android.support.annotation.FloatRange;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.easycore.nomadesk.R;

import java.util.Locale;

public class RatingLayout extends LinearLayout {

    @BindView(R.id.ratingBar) RatingBar ratingBar;
    @BindView(R.id.ratingLabel) TextView ratingLabel;

    public RatingLayout(Context context) {
        super(context);
        init(context);
    }

    public RatingLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public RatingLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        setOrientation(HORIZONTAL);
        inflate(context, R.layout.rating_layout, this);
        ButterKnife.bind(this);
    }

    public void setRating(@FloatRange(from = 0.0, to = 5.0) final float rating,
                          final int numberOfReviewers) {
        ratingBar.setRating(rating);
        String label = numberOfReviewers == 1 ? "reviewer" : "reviewers";
        ratingLabel.setText(String.format(Locale.getDefault(), "%d %s", numberOfReviewers, label));
        requestLayout();
    }

}
