package com.easycore.nomadesk.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.StringDef;

import java.util.ArrayList;

/**
 * Created by Jakub Begera (jakub@easycoreapps.com) on 20.10.16.
 */
public class Parameters implements Parcelable {

    public final static String PARAM_KEY_COFFEE = "coffee";
    public final static String PARAM_KEY_ETHERNET = "ethernet";
    public final static String PARAM_KEY_PROJECTOR = "projector";
    public final static String PARAM_KEY_MEETING_ROOM = "meeting_room";
    public final static String PARAM_KEY_PHONE_BOOTH = "phone_booth";
    public final static String PARAM_KEY_PRINTER = "printer";
    @StringDef({
            PARAM_KEY_COFFEE,
            PARAM_KEY_ETHERNET,
            PARAM_KEY_PROJECTOR,
            PARAM_KEY_MEETING_ROOM,
            PARAM_KEY_PHONE_BOOTH,
            PARAM_KEY_PRINTER
            })
    public @interface ParamKey{}

    private String coffee;
    private String wifi;
    private OpeningHours openingHours;
    private ArrayList<String> paramKeys;

    public Parameters() {
    }

    protected Parameters(Parcel in) {
        coffee = in.readString();
        wifi = in.readString();
        openingHours = in.readParcelable(OpeningHours.class.getClassLoader());
        paramKeys = in.createStringArrayList();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(coffee);
        dest.writeString(wifi);
        dest.writeParcelable(openingHours, flags);
        dest.writeStringList(paramKeys);
    }

    public boolean containsParamKey(@ParamKey String key){
        return paramKeys.contains(key);
    }

    @Override
    public int describeContents() {
        return 0;
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

    public ArrayList<String> getParamKeys() {
        return paramKeys;
    }

    public void setParamKeys(ArrayList<String> paramKeys) {
        this.paramKeys = paramKeys;
    }
}
