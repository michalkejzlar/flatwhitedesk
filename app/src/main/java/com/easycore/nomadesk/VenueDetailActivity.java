package com.easycore.nomadesk;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import com.easycore.nomadesk.dialogs.CheckOutDialog;
import com.easycore.nomadesk.model.Venue;
import com.easycore.nomadesk.widget.AuthorTextView;
import com.easycore.nomadesk.widget.BaselineGridTextView;
import com.easycore.nomadesk.widget.CircularImageView;
import com.easycore.nomadesk.widget.RatingLayout;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class VenueDetailActivity extends AppCompatActivity implements OnMapReadyCallback {

    public static void start(Context context, Venue venue) {
        Intent intent = new Intent(context, VenueDetailActivity.class);
        intent.putExtra("venue", venue);
        context.startActivity(intent);
    }

    @BindView(R.id.venuePictureImageView) ImageView venuePictureImageView;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.backdrop_toolbar) CollapsingToolbarLayout backdropToolbar;
    @BindView(R.id.venueNameTextView) TextView venueNameTextView;
    @BindView(R.id.ratingLayout) RatingLayout ratingLayout;
    @BindView(R.id.shortDescTextView) TextView shortDescTextView;
    @BindView(R.id.availabilityTextView) TextView availabilityTextView;
    @BindView(R.id.openingHoursTextView) TextView openingHoursTextView;
    @BindView(R.id.commentsLayout) LinearLayout commentsLayout;

    private Venue venue;

    @BindView(R.id.check_out_container) ViewGroup checkOutContainer;
    @BindView(R.id.btn_check_out) Button btnCheckOut;
    @BindView(R.id.btn_check_in) Button btnCheckIn;
    @BindView(R.id.chronometer) Chronometer chronometer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venue_detail);
        ButterKnife.bind(this);

        this.venue = getIntent().getParcelableExtra("venue");

        Glide.with(this)
                .load(venue.getPicturesUrls().get(0))
                .centerCrop()
                .into(venuePictureImageView);

        venueNameTextView.setText(venue.getName());
        setupActionbar();
        setupMap();

        if (venue.getReviews() != null) {
            ratingLayout.setRating((float) venue.getReviews().getAvgStars(),
                    venue.getReviews().getReviews().size());
        } else {
            ratingLayout.setRating(0.0f, 0);
        }

        setOpeningHours("(8:30 - 18:00)", "OPEN");

        setupComment("Chris Doe", "chris.jpg", "3 hours ago", "\"Very beautiful place. Highly recommended!\"");
        setupComment("Jane Nowak", "jane.jpg", "7 hours ago", "\"Excellent coffee, quite space for Skype calls. Thumbs up!\"");
        setupComment("Peter Stone", "peter.jpg", "2 days ago", "\"Had a great time with Mike, the bartender! \"");

        setOpeningHours("(8:30 - 18:00)", "OPENED");

        btnCheckIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewUtils.gone(btnCheckIn);
                ViewUtils.visible(checkOutContainer);
                chronometer.setBase(SystemClock.elapsedRealtime());
                chronometer.start();
            }
        });
        btnCheckOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewUtils.gone(checkOutContainer);
                ViewUtils.visible(btnCheckIn);
                String burnedTime = chronometer.getText().toString();
                CheckOutDialog checkOutDialog = new CheckOutDialog();
                checkOutDialog.setTitle(venue.getName());
                checkOutDialog.setBurnedTime(burnedTime);
                checkOutDialog.show(getSupportFragmentManager(), "");
            }
        });
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
        wordtoSpan.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this, R.color.availabilityOpened)),
                label.length(), wordtoSpan.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        wordtoSpan.setSpan(new StyleSpan(Typeface.BOLD), label.length(), wordtoSpan.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        availabilityTextView.setText(wordtoSpan);
        openingHoursTextView.setText(hours);
    }

    private void setupComment(final String author,
                              final String picture,
                              final String time,
                              final String comment) {

        final View view = View.inflate(this, R.layout.venue_comment, null);
        CircularImageView imageView = (CircularImageView) view.findViewById(R.id.player_avatar);
        BaselineGridTextView timeAgo = (BaselineGridTextView) view.findViewById(R.id.comment_time_ago);
        timeAgo.setText(time);
        AuthorTextView authorTextView = (AuthorTextView) view.findViewById(R.id.comment_author);
        authorTextView.setText(author);
        BaselineGridTextView commentsTextView = (BaselineGridTextView) view.findViewById(R.id.comment_text);
        commentsTextView.setText(comment);
        commentsLayout.addView(view);
        Glide.with(this)
                .loadFromMediaStore(Uri.parse("file:///android_asset/" + picture))
                .centerCrop()
                .into(imageView);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        final String[] arr = venue.getLoc().split(",");
        final LatLng target = new LatLng(Double.valueOf(arr[0]), Double.valueOf(arr[1]));

        googleMap.getUiSettings().setAllGesturesEnabled(false);
        googleMap.addMarker(new MarkerOptions().position(target).title(venue.getName()));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(target, 15.0f));
    }

    @Override
    public void onBackPressed() {
        supportFinishAfterTransition();
    }
}
