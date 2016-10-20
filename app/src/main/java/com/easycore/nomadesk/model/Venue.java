package com.easycore.nomadesk.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Jakub Begera (jakub@easycoreapps.com) on 19.10.16.
 */
public class Venue implements Parcelable {

    private Capacity capacity;
    private String loc;
    private String name;
    private Parameters parameters;
    private Reviews reviews;

    public Venue() {
    }

    protected Venue(Parcel in) {
        capacity = in.readParcelable(Capacity.class.getClassLoader());
        loc = in.readString();
        name = in.readString();
        parameters = in.readParcelable(Parameters.class.getClassLoader());
        reviews = in.readParcelable(Reviews.class.getClassLoader());
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(capacity, flags);
        dest.writeString(loc);
        dest.writeString(name);
        dest.writeParcelable(parameters, flags);
        dest.writeParcelable(reviews, flags);
    }
}
