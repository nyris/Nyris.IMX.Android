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
 * OfferInfo.java - A class model that contain offer information
 *
 * @author  Sidali Mellouk
 * Created by nyris GmbH
 * Copyright © 2017 nyris GmbH. All rights reserved.
 */
public class OfferInfo implements Serializable, Parcelable{
    @SerializedName("id")
    private String id;
    @SerializedName("p")
    private Price price;
    @SerializedName("mer")
    private String merchant;
    @SerializedName("title")
    private String title;
    @SerializedName("desc")
    private String description;
    @SerializedName("img")
    private ImageInfo imageInfo;
    @SerializedName("l")
    private String link;

    /**
     * Default constructor
     */
    public OfferInfo(){
    }

    protected OfferInfo(Parcel in) {
        id = in.readString();
        title = in.readString();
        description = in.readString();
        merchant = in.readString();
        price = in.readParcelable(Price.class.getClassLoader());
        link = in.readString();
        imageInfo = in.readParcelable(ImageInfo.class.getClassLoader());
    }

    public static final Creator<OfferInfo> CREATOR = new Creator<OfferInfo>() {
        @Override
        public OfferInfo createFromParcel(Parcel in) {
            return new OfferInfo(in);
        }

        @Override
        public OfferInfo[] newArray(int size) {
            return new OfferInfo[size];
        }
    };

    /**
     * Get Offer id
     * @return String value
     */
    public String getId() {
        return id;
    }

    /**
     * Get price Information
     * @return Price object
     * @see Price
     */
    public Price getPrice() {
        return price;
    }

    /**
     * Get Merchant
     * @return String value
     */
    public String getMerchant() {
        return merchant;
    }

    /**
     * Get title
     * @return String value, could contain HTML tags
     */
    public String getTitle() {
        return title;
    }

    /**
     * Get description
     * @return String value, could contain HTML tags
     */
    public String getDescription() {
        return description;
    }

    /**
     * Get Offer Image information
     * @return ImageInfo object
     * @see ImageInfo
     */
    public ImageInfo getImageInfo() {
        return imageInfo;
    }

    /**
     * Get offer link
     * @return String value
     */
    public String getLink() {
        return link;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(merchant);
        dest.writeParcelable(price, flags);
        dest.writeString(link);
        dest.writeParcelable(imageInfo, flags);
    }
}
