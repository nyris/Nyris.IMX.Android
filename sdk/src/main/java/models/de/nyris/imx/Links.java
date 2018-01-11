package de.nyris.imx;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by sidali on 1/5/18.
 */

public class Links implements Serializable, Parcelable {
    @SerializedName("main")
    private String main;

    @SerializedName("mobile")
    private String mobile;

    public Links() {
    }

    protected Links(Parcel in) {
        main = in.readString();
        mobile = in.readString();
    }

    public static final Creator<Links> CREATOR = new Creator<Links>() {
        @Override
        public Links createFromParcel(Parcel in) {
            return new Links(in);
        }

        @Override
        public Links[] newArray(int size) {
            return new Links[size];
        }
    };

    public String getMain() {
        return main;
    }

    public String getMobile() {
        return mobile;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(main);
        parcel.writeString(mobile);
    }
}
