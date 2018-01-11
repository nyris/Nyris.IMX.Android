package de.nyris.imx;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by sidali on 1/5/18.
 */

public class CustomIds implements Serializable, Parcelable {
    @SerializedName("GTIN")
    private String gtin;

    public CustomIds() {
    }

    protected CustomIds(Parcel in) {
        gtin = in.readString();
    }

    public static final Creator<CustomIds> CREATOR = new Creator<CustomIds>() {
        @Override
        public CustomIds createFromParcel(Parcel in) {
            return new CustomIds(in);
        }

        @Override
        public CustomIds[] newArray(int size) {
            return new CustomIds[size];
        }
    };

    public String getGtin() {
        return gtin;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(gtin);
    }
}
