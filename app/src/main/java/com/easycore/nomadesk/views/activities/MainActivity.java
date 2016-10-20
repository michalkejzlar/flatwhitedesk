package com.easycore.nomadesk.views.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.easycore.nomadesk.R;
import com.easycore.nomadesk.views.fragments.QrFragment;
import com.easycore.nomadesk.views.fragments.VenuesFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.pager)
    protected ViewPager mPager;
    @BindView(R.id.tabs)
    protected TabLayout mTabs;
    private PagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        pagerAdapter = new PagerAdapter(getSupportFragmentManager(), this);
        mPager.setAdapter(pagerAdapter);
        mTabs.setupWithViewPager(mPager);
        for (int i = 0; i < pagerAdapter.getCount(); i++) {
            mTabs.getTabAt(i).setCustomView(pagerAdapter.getTabView(i));
        }

    }

    private static class PagerAdapter extends FragmentPagerAdapter {

        private final Context context;

        public PagerAdapter(FragmentManager fm, Context context) {
            super(fm);
            this.context = context;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new VenuesFragment();
                case 1:
                    return new QrFragment();
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 2;
        }

        public View getTabView(int position) {
            View view = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                    .inflate(R.layout.custom_tab, null, false);
            ImageView icon = (ImageView) view.findViewById(R.id.icon);
            switch (position) {
                case 0:
                    icon.setImageDrawable(context.getDrawable(R.drawable.ic_home));
                    break;
                case 1:
                    icon.setImageDrawable(context.getDrawable(R.drawable.ic_qr));
                    break;
            }
            return view;
        }

    }
}
