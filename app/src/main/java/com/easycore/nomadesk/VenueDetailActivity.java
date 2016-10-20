package com.easycore.nomadesk;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;

public class VenueDetailActivity extends AppCompatActivity {

    @BindView(R.id.venuePictureImageView) ImageView venuePictureImageView;

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.backdrop_toolbar) CollapsingToolbarLayout backdropToolbar;

    @BindView(R.id.venueNameTextView) TextView venueNameTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venue_detail);
        ButterKnife.bind(this);

        Glide.with(this)
                .loadFromMediaStore(Uri.parse("file:///android_asset/sample_venue.jpg"))
                .centerCrop()
                .into(venuePictureImageView);

        setupActionbar();

        venueNameTextView.setText("Workshop coffee San Fransisco!");
    }

    private void setupActionbar() {
        setSupportActionBar(toolbar);

        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

}
