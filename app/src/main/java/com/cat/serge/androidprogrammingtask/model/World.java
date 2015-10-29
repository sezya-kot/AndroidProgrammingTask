package com.cat.serge.androidprogrammingtask.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * World
 *
 * @author korchinsky_s
 * @since 29.10.2015.
 */
public class World implements Parcelable{

    public World() {
    }

    public long        id;
    public String      language;
    public String      url;
    public String      country;
    public WorldStatus worldStatus;
    public String      mapURL;
    public String      name;

    protected World(Parcel in) {
        id = in.readLong();
        language = in.readString();
        url = in.readString();
        country = in.readString();
        worldStatus = in.readParcelable(WorldStatus.class.getClassLoader());
        mapURL = in.readString();
        name = in.readString();
    }

    public static final Creator<World> CREATOR = new Creator<World>() {
        @Override
        public World createFromParcel(Parcel in) {
            return new World(in);
        }

        @Override
        public World[] newArray(int size) {
            return new World[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        dest.writeLong(id);
        dest.writeString(language);
        dest.writeString(url);
        dest.writeString(country);
        dest.writeParcelable(worldStatus, flags);
        dest.writeString(mapURL);
        dest.writeString(name);
    }
}
