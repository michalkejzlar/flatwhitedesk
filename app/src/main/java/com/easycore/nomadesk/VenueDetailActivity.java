package com.easycore.nomadesk;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class VenueDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_venue_detail);

        final View mContentView = findViewById(R.id.fullscreen_content);

        assert mContentView != null;

    }

}
