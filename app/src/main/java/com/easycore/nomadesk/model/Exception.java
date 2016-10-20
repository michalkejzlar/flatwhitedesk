package com.easycore.nomadesk.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Jakub Begera (jakub@easycoreapps.com) on 20.10.16.
 */
public class Exception implements Parcelable {

    private int capacityDecrease;
    private String description;
    private String start;
    private String end;

    public Exception() {
    }

    protected Exception(Parcel in) {
        capacityDecrease = in.readInt();
        description = in.readString();
        start = in.readString();
        end = in.readString();
    }

    public static final Creator<Exception> CREATOR = new Creator<Exception>() {
        @Override
        public Exception createFromParcel(Parcel in) {
            return new Exception(in);
        }

        @Override
        public Exception[] newArray(int size) {
            return new Exception[size];
        }
    };

    public int getCapacityDecrease() {
        return capacityDecrease;
    }

    public void setCapacityDecrease(int capacityDecrease) {
        this.capacityDecrease = capacityDecrease;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(capacityDecrease);
        dest.writeString(description);
        dest.writeString(start);
        dest.writeString(end);
    }
}
