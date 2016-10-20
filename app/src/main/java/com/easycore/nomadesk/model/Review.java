package com.easycore.nomadesk.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Jakub Begera (jakub@easycoreapps.com) on 20.10.16.
 */
public class Review implements Parcelable {

    private String created;
    private int stars;
    private String text;
    private String user;

    public Review() {
    }

    protected Review(Parcel in) {
        created = in.readString();
        stars = in.readInt();
        text = in.readString();
        user = in.readString();
    }

    public static final Creator<Review> CREATOR = new Creator<Review>() {
        @Override
        public Review createFromParcel(Parcel in) {
            return new Review(in);
        }

        @Override
        public Review[] newArray(int size) {
            return new Review[size];
        }
    };

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(created);
        dest.writeInt(stars);
        dest.writeString(text);
        dest.writeString(user);
    }
}
