package com.cat.serge.androidprogrammingtask.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * WorldStatus
 *
 * @author korchinsky_s
 * @since 29.10.2015.
 */
public class WorldStatus implements Parcelable{

    public WorldStatus() {
    }

    public long   id;
    public String description;

    protected WorldStatus(Parcel in) {
        id = in.readLong();
        description = in.readString();
    }

    public static final Creator<WorldStatus> CREATOR = new Creator<WorldStatus>() {
        @Override
        public WorldStatus createFromParcel(Parcel in) {
            return new WorldStatus(in);
        }

        @Override
        public WorldStatus[] newArray(int size) {
            return new WorldStatus[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        dest.writeLong(id);
        dest.writeString(description);
    }
}
