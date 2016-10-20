package com.easycore.nomadesk.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Jakub Begera (jakub@easycoreapps.com) on 20.10.16.
 */
public class Reviews implements Parcelable {

    private double avgStars;
    private ArrayList<Review> reviews;

    public Reviews() {
    }

    protected Reviews(Parcel in) {
        avgStars = in.readDouble();
        reviews = in.createTypedArrayList(Review.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(avgStars);
        dest.writeTypedList(reviews);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Reviews> CREATOR = new Creator<Reviews>() {
        @Override
        public Reviews createFromParcel(Parcel in) {
            return new Reviews(in);
        }

        @Override
        public Reviews[] newArray(int size) {
            return new Reviews[size];
        }
    };

    public double getAvgStars() {
        return avgStars;
    }

    public void setAvgStars(double avgStars) {
        this.avgStars = avgStars;
    }

    public ArrayList<Review> getReviews() {
        return reviews;
    }

    public void setReviews(ArrayList<Review> reviews) {
        this.reviews = reviews;
    }

}
