package com.easycore.nomadesk.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Jakub Begera (jakub@easycoreapps.com) on 20.10.16.
 */
public class Parameters implements Parcelable {

    private String coffee;
    private String wifi;
    private OpeningHours openingHours;

    public Parameters() {
    }

    protected Parameters(Parcel in) {
        coffee = in.readString();
        wifi = in.readString();
        openingHours = in.readParcelable(OpeningHours.class.getClassLoader());
    }

    public static final Creator<Parameters> CREATOR = new Creator<Parameters>() {
        @Override
        public Parameters createFromParcel(Parcel in) {
            return new Parameters(in);
        }

        @Override
        public Parameters[] newArray(int size) {
            return new Parameters[size];
        }
    };

    public String getCoffee() {
        return coffee;
    }

    public void setCoffee(String coffee) {
        this.coffee = coffee;
    }

    public String getWifi() {
        return wifi;
    }

    public void setWifi(String wifi) {
        this.wifi = wifi;
    }

    public OpeningHours getOpeningHours() {
        return openingHours;
    }

    public void setOpeningHours(OpeningHours openingHours) {
        this.openingHours = openingHours;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(coffee);
        dest.writeString(wifi);
        dest.writeParcelable(openingHours, flags);
    }
}
