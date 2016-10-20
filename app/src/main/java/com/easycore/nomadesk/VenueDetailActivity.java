package com.easycore.nomadesk;

import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import com.easycore.nomadesk.widget.RatingLayout;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class VenueDetailActivity extends AppCompatActivity implements OnMapReadyCallback {

    @BindView(R.id.venuePictureImageView) ImageView venuePictureImageView;

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.backdrop_toolbar) CollapsingToolbarLayout backdropToolbar;

    @BindView(R.id.venueNameTextView) TextView venueNameTextView;
    @BindView(R.id.ratingLayout) RatingLayout ratingLayout;
    @BindView(R.id.shortDescTextView) TextView shortDescTextView;

    @BindView(R.id.availabilityTextView) TextView availabilityTextView;
    @BindView(R.id.openingHoursTextView) TextView openingHoursTextView;

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
        setupMap();
        ratingLayout.setRating(3.5f, 850);
        setOpeningHours("(8:30 - 18:00)", "OPENED");
    }

    private void setupActionbar() {
        setSupportActionBar(toolbar);

        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void setupMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private void setOpeningHours(final String hours, final String availabilityNow) {
        final String label = "Currently: ";
        Spannable wordtoSpan = new SpannableString(label + availabilityNow);
        wordtoSpan.setSpan(new ForegroundColorSpan(getColor(R.color.availabilityOpened)),
                label.length(), wordtoSpan.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        wordtoSpan.setSpan(new StyleSpan(Typeface.BOLD), label.length(), wordtoSpan.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        availabilityTextView.setText(wordtoSpan);
        openingHoursTextView.setText(hours);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng sydney = new LatLng(-34, 151);

        googleMap.getUiSettings().setAllGesturesEnabled(false);
        googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}
