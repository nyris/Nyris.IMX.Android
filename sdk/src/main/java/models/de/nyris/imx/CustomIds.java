/*
 * Copyright (C) 2017 nyris GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.nyris.imx;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * CustomIds.java - A class model that contain ids(Barcode, Gtin, ...)
 *
 * @author  Sidali Mellouk
 * Created by nyris GmbH
 * Copyright © 2017 nyris GmbH. All rights reserved.
 */
public class CustomIds implements Serializable, Parcelable {
    @SerializedName("GTIN")
    private String gtin;

    /**
     * Default constructor
     */
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

    /**
     * Get Gtin
     * @return String value
     */
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
