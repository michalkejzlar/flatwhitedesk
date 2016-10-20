package com.easycore.nomadesk.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Jakub Begera (jakub@easycoreapps.com) on 20.10.16.
 */
public class Capacity implements Parcelable {

    private ArrayList<Exception> exceptions;
    private int total;

    public Capacity() {
    }

    protected Capacity(Parcel in) {
        exceptions = in.createTypedArrayList(Exception.CREATOR);
        total = in.readInt();
    }

    public static final Creator<Capacity> CREATOR = new Creator<Capacity>() {
        @Override
        public Capacity createFromParcel(Parcel in) {
            return new Capacity(in);
        }

        @Override
        public Capacity[] newArray(int size) {
            return new Capacity[size];
        }
    };

    public ArrayList<Exception> getExceptions() {
        return exceptions;
    }

    public void setExceptions(ArrayList<Exception> exceptions) {
        this.exceptions = exceptions;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(exceptions);
        dest.writeInt(total);
    }
}
