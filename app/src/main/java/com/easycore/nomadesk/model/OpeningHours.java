package com.easycore.nomadesk.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Jakub Begera (jakub@easycoreapps.com) on 20.10.16.
 */
public class OpeningHours implements Parcelable {

    private String weekday;
    private String weekend;

    public OpeningHours() {
    }

    protected OpeningHours(Parcel in) {
        weekday = in.readString();
        weekend = in.readString();
    }

    public static final Creator<OpeningHours> CREATOR = new Creator<OpeningHours>() {
        @Override
        public OpeningHours createFromParcel(Parcel in) {
            return new OpeningHours(in);
        }

        @Override
        public OpeningHours[] newArray(int size) {
            return new OpeningHours[size];
        }
    };

    public String getWeekday() {
        return weekday;
    }

    public void setWeekday(String weekday) {
        this.weekday = weekday;
    }

    public String getWeekend() {
        return weekend;
    }

    public void setWeekend(String weekend) {
        this.weekend = weekend;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(weekday);
        dest.writeString(weekend);
    }
}
