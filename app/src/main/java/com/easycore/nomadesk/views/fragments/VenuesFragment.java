package com.easycore.nomadesk.views.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.easycore.nomadesk.R;
import com.easycore.nomadesk.VenueDetailActivity;
import com.easycore.nomadesk.model.Venue;
import com.easycore.nomadesk.views.adapers.VenuesAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 */
public class VenuesFragment extends Fragment implements VenuesAdapter.Callback{

    @BindView(R.id.venues_rcv)
    protected RecyclerView rcv;
    private LinearLayoutManager mLinearLayoutManager;
    private VenuesAdapter adapter;

    private DatabaseReference mFirebaseDatabaseReference;

    public VenuesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_venues, container, false);
        ButterKnife.bind(this, v);

        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference("venues");

        adapter = new VenuesAdapter(mFirebaseDatabaseReference, getContext(), this);
        mLinearLayoutManager = new LinearLayoutManager(getContext());
        rcv.setLayoutManager(mLinearLayoutManager);
        rcv.setAdapter(adapter);
        return v;
    }


    @Override
    public void onVenueClicked(Venue venue, ImageView imageView) {
        Intent intent = new Intent(getActivity(), VenueDetailActivity.class);
        intent.putExtra("venue", venue);
        ActivityOptionsCompat options = ActivityOptionsCompat.
                makeSceneTransitionAnimation(getActivity(), (View) imageView, "picture");
        startActivity(intent, options.toBundle());

    }
}
