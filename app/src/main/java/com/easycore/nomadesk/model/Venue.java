package com.easycore.nomadesk.model;

import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Jakub Begera (jakub@easycoreapps.com) on 19.10.16.
 */
public class Venue implements Parcelable, Comparable<Venue> {

    private Capacity capacity;
    private String loc;
    private String name;
    private Parameters parameters;
    private Reviews reviews;
    private double distance;
    private ArrayList<String> picturesUrls = new ArrayList<>();

    public Venue() {
    }

    protected Venue(Parcel in) {
        capacity = in.readParcelable(Capacity.class.getClassLoader());
        loc = in.readString();
        name = in.readString();
        parameters = in.readParcelable(Parameters.class.getClassLoader());
        reviews = in.readParcelable(Reviews.class.getClassLoader());
        distance = in.readDouble();
        picturesUrls = in.createStringArrayList();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(capacity, flags);
        dest.writeString(loc);
        dest.writeString(name);
        dest.writeParcelable(parameters, flags);
        dest.writeParcelable(reviews, flags);
        dest.writeDouble(distance);
        dest.writeStringList(picturesUrls);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Venue> CREATOR = new Creator<Venue>() {
        @Override
        public Venue createFromParcel(Parcel in) {
            return new Venue(in);
        }

        @Override
        public Venue[] newArray(int size) {
            return new Venue[size];
        }
    };

    public void computeDistance(Location currentLoc){
        if (loc == null) return;
        Location l = new Location("reverseGeocoded");
        String[] split = loc.split(",");
        if (split.length != 2) return;

        l.setLatitude(Double.parseDouble(split[0]));
        l.setLongitude(Double.parseDouble(split[1]));
        distance = currentLoc.distanceTo(l);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Capacity getCapacity() {
        return capacity;
    }

    public void setCapacity(Capacity capacity) {
        this.capacity = capacity;
    }

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

    public Parameters getParameters() {
        return parameters;
    }

    public void setParameters(Parameters parameters) {
        this.parameters = parameters;
    }

    public Reviews getReviews() {
        return reviews;
    }

    public void setReviews(Reviews reviews) {
        this.reviews = reviews;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public ArrayList<String> getPicturesUrls() {
        return picturesUrls;
    }

    @Override
    public int compareTo(Venue o) {
        return (int) (distance - o.distance);
    }
}
