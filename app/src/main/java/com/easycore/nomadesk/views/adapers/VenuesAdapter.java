package com.easycore.nomadesk.views.adapers;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.ColorMatrixColorFilter;
import android.location.Location;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.easycore.nomadesk.ObservableColorMatrix;
import com.easycore.nomadesk.R;
import com.easycore.nomadesk.ViewUtils;
import com.easycore.nomadesk.model.*;
import com.easycore.nomadesk.model.Exception;
import com.easycore.nomadesk.widget.RatingLayout;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

import static com.easycore.nomadesk.AnimUtils.getFastOutSlowInInterpolator;

/**
 * Created by Jakub Begera (jakub@easycoreapps.com) on 20.10.16.
 */
public class VenuesAdapter extends RecyclerView.Adapter<VenuesAdapter.VenueViewHolder> {

    private ArrayList<Venue> venues;
    private final DatabaseReference mFirebaseDatabaseReference;
    private final Context context;
    private Callback callback;
    private Location currentLocation;

    public interface Callback {
        void onVenueClicked(Venue venue, ImageView imageView);
    }

    public VenuesAdapter(DatabaseReference mFirebaseDatabaseReference, final Context context, Callback callback) {
        this.mFirebaseDatabaseReference = mFirebaseDatabaseReference;
        this.context = context;
        this.callback = callback;
        venues = new ArrayList<>();
        currentLocation = new Location("");
        currentLocation.setAccuracy(1);
        currentLocation.setAltitude(200);
        currentLocation.setLatitude(51.502991);
        currentLocation.setLongitude(0);
        mFirebaseDatabaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.i("Venue added", dataSnapshot.toString());
                Venue venue = new Venue();

                if (dataSnapshot.hasChild("name")) {
                    venue.setName(dataSnapshot.child("name").getValue().toString());
                }

                if (dataSnapshot.hasChild("loc")) {
                    venue.setLoc(dataSnapshot.child("loc").getValue().toString());
                }

                if (dataSnapshot.hasChild("capacity")) {
                    Capacity capacity = new Capacity();
                    DataSnapshot capacityD = dataSnapshot.child("capacity");

                    if (capacityD.hasChild("total")) {
                        capacity.setTotal(Integer.parseInt(capacityD.child("total").getValue().toString()));
                    }
                    capacity.setExceptions(new ArrayList<Exception>());
                    if (capacityD.hasChild("exceptions")) {
                        DataSnapshot exceptions = capacityD.child("exceptions");
                        for (DataSnapshot ex : exceptions.getChildren()) {
                            Exception e = new Exception();

                            if (ex.hasChild("capacityDecrease")) {
                                e.setCapacityDecrease(Integer.parseInt(
                                        ex.child("capacityDecrease").getValue().toString()));
                            }
                            if (ex.hasChild("description")) {
                                e.setDescription(ex.child("description").getValue().toString());
                            }
                            if (ex.hasChild("start")) {
                                e.setStart(ex.child("start").getValue().toString());
                            }
                            if (ex.hasChild("end")) {
                                e.setEnd(ex.child("end").getValue().toString());
                            }

                            capacity.getExceptions().add(e);
                        }
                    }

                    venue.setCapacity(capacity);
                }

                if (dataSnapshot.hasChild("parameters")) {
                    DataSnapshot params = dataSnapshot.child("parameters");
                    Parameters p = new Parameters();

                    if (params.hasChild("coffee")) {
                        p.setCoffee(params.child("coffee").getValue().toString());
                    }
                    if (params.hasChild("wifi")) {
                        p.setWifi(params.child("wifi").getValue().toString());
                    }
                    if (params.hasChild("openingHours")) {
                        DataSnapshot openingHours = params.child("openingHours");
                        OpeningHours oh = new OpeningHours();

                        if (openingHours.hasChild("weekday")) {
                            oh.setWeekday(openingHours.child("weekday").getValue().toString());
                        }
                        if (openingHours.hasChild("weekend")) {
                            oh.setWeekend(openingHours.child("weekend").getValue().toString());
                        }

                        p.setOpeningHours(oh);
                    }

                    ArrayList<String> paramKeys = new ArrayList<>();
                    if (params.hasChild("paramsArr")) {
                        String pArr = params.child("paramsArr").getValue().toString();
                        String[] split = pArr.split(",");
                        for (String ss : split) {
                            paramKeys.add(ss);
                        }
                    }
                    p.setParamKeys(paramKeys);

                    venue.setParameters(p);
                }

                if (dataSnapshot.hasChild("reviews")) {
                    DataSnapshot reviews = dataSnapshot.child("reviews");
                    Reviews r = new Reviews();
                    r.setReviews(new ArrayList<Review>());

                    if (reviews.hasChild("avgStars")) {
                        r.setAvgStars(Double.parseDouble(reviews.child("avgStars").getValue().toString()));
                    }

                    for (DataSnapshot ds : reviews.getChildren()) {
                        if (ds.getKey().equals("avgStars")) {
                            continue;
                        }
                        Review rr = new Review();

                        if (ds.hasChild("stars")) {
                            rr.setStars(Integer.parseInt(ds.child("stars").getValue().toString()));
                        }
                        if (ds.hasChild("created")) {
                            rr.setCreated(ds.child("created").getValue().toString());
                        }
                        if (ds.hasChild("text")) {
                            rr.setText(ds.child("text").getValue().toString());
                        }
                        if (ds.hasChild("user")) {
                            rr.setUser(ds.child("user").getValue().toString());
                        }

                        r.getReviews().add(rr);
                    }

                    venue.setReviews(r);
                }

                if (dataSnapshot.hasChild("pictures")) {
                    DataSnapshot pictures = dataSnapshot.child("pictures");
                    for (DataSnapshot ds : pictures.getChildren()) {
                        venue.getPicturesUrls().add(ds.getValue().toString());
                    }
                }

                venue.computeDistance(currentLocation);
                venues.add(venue);
                Collections.sort(venues);
                notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public VenueViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.cri_venue, parent, false);
        return new VenueViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final VenueViewHolder holder, int position) {
        final Venue venue = venues.get(position);
        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.onVenueClicked(venue, holder.imvPicture);
            }
        });

        holder.txvName.setText(venue.getName());
        if (venue.getReviews() != null) {
            holder.ratingLayout.setVisibility(View.VISIBLE);
            holder.ratingLayout.setRating((float) venue.getReviews().getAvgStars(), venue.getReviews().getReviews().size());
        } else {
            holder.ratingLayout.setVisibility(View.GONE);
        }

        double miles = venue.getDistance() * 0.000621371192;
        if (miles < 10) {
            holder.txvDistance.setText(String.format(Locale.UK, "%.1f mi away", miles));
        } else {
            holder.txvDistance.setText(String.format(Locale.UK, "%.0f mi away", miles));
        }

        if (venue.getParameters() == null || venue.getParameters().getParamKeys() == null
                || venue.getParameters().getParamKeys().isEmpty()) {
            holder.paramsContainer.setVisibility(View.GONE);
        } else {
            holder.paramsContainer.setVisibility(View.VISIBLE);

            ViewUtils.visibleOrGone(
                    venue.getParameters().containsParamKey(Parameters.PARAM_KEY_COFFEE),
                    holder.tmvParamCoffee
            );
            ViewUtils.visibleOrGone(
                    venue.getParameters().containsParamKey(Parameters.PARAM_KEY_ETHERNET),
                    holder.tmvParamEthernet
            );
            ViewUtils.visibleOrGone(
                    venue.getParameters().containsParamKey(Parameters.PARAM_KEY_MEETING_ROOM),
                    holder.tmvParamMeetingRoom
            );
            ViewUtils.visibleOrGone(
                    venue.getParameters().containsParamKey(Parameters.PARAM_KEY_PHONE_BOOTH),
                    holder.tmvParamPhoneBooth
            );
            ViewUtils.visibleOrGone(
                    venue.getParameters().containsParamKey(Parameters.PARAM_KEY_PRINTER),
                    holder.tmvParamPrinter
            );
            ViewUtils.visibleOrGone(
                    venue.getParameters().containsParamKey(Parameters.PARAM_KEY_PROJECTOR),
                    holder.tmvParamProjector
            );
        }

        Glide.with(context)
                .load(venue.getPicturesUrls().isEmpty() ? getFakePictureUrl() : venue.getPicturesUrls().get(0))
                .centerCrop()
                .listener(new RequestListener<String, GlideDrawable>() {

                    @Override
                    public boolean onException(java.lang.Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource,
                                                   String model,
                                                   Target<GlideDrawable> target,
                                                   boolean isFromMemoryCache,
                                                   boolean isFirstResource) {
                        holder.imvPicture.setHasTransientState(true);
                        final ObservableColorMatrix cm = new ObservableColorMatrix();
                        final ObjectAnimator saturation = ObjectAnimator.ofFloat(
                                cm, ObservableColorMatrix.SATURATION, 0f, 1f);
                        saturation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener
                                () {
                            @Override
                            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                                // just animating the color matrix does not invalidate the
                                // drawable so need this update listener.  Also have to create a
                                // new CMCF as the matrix is immutable :(
                                holder.imvPicture.setColorFilter(new ColorMatrixColorFilter(cm));
                            }
                        });
                        saturation.setDuration(2000L);
                        saturation.setInterpolator(getFastOutSlowInInterpolator(context));
                        saturation.addListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                holder.imvPicture.clearColorFilter();
                                holder.imvPicture.setHasTransientState(false);
                            }
                        });
                        saturation.start();
                        return false;
                    }
                })
                .into(holder.imvPicture);
    }

    @Override
    public int getItemCount() {
        return venues.size();
    }

    public static String getFakePictureUrl() {
        double random = Math.random() * 10;
        if (random < 2.5) {
            return "http://www.launchablemag.com/assets/images/upload/Inspire9-coworking5523-space-1-873860efa01f35f8937e82aa706b4d10.jpg";
        }
        if (random < 5) {
            return "https://cdn.cheapoguides.com/wp-content/uploads/sites/2/2015/04/mov_005.jpg";
        }
        if (random < 7.5) {
            return "https://artconnect.s3-eu-west-1.amazonaws.com/attachments/91819/original.jpg?1429015018";
        } else {
            return "http://s3.amazonaws.com/media.skillcrush.com/skillcrush/wp-content/uploads/2014/07/coworking-space.jpg";
        }
    }

    public static class VenueViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.venue_container)
        public ViewGroup container;
        @BindView(R.id.venue_txv_name)
        public TextView txvName;
        @BindView(R.id.venue_imv_picture)
        public ImageView imvPicture;
        @BindView(R.id.ratingLayout)
        public RatingLayout ratingLayout;
        @BindView(R.id.venue_txv_distance)
        public TextView txvDistance;
        @BindView(R.id.venue_param_imv_coffee)
        public ImageView tmvParamCoffee;
        @BindView(R.id.venue_param_imv_ethernet)
        public ImageView tmvParamEthernet;
        @BindView(R.id.venue_param_imv_meeting_room)
        public ImageView tmvParamMeetingRoom;
        @BindView(R.id.venue_param_imv_phone_booth)
        public ImageView tmvParamPhoneBooth;
        @BindView(R.id.venue_param_imv_printer)
        public ImageView tmvParamPrinter;
        @BindView(R.id.venue_param_imv_projector)
        public ImageView tmvParamProjector;
        @BindView(R.id.venue_params)
        public ViewGroup paramsContainer;

        public VenueViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
