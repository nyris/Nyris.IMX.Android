package de.nyris.imx;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Offer.java - A class model that contain full offer information
 *
 * @author  Sidali Mellouk
 * Created by nyris GmbH
 * Copyright © 2017 nyris GmbH. All rights reserved.
 */

public class Offer implements Serializable, Parcelable{
    @SerializedName("title")
    String title;

    @SerializedName("description")
    String description;

    @SerializedName("descriptionLong")
    String descriptionLong;

    @SerializedName("language")
    String language;

    @SerializedName("brand")
    String brand;

    @SerializedName("catalogNumbers")
    float[] catalogNumbers;

    @SerializedName("customIds")
    CustomIds customIds;

    @SerializedName("keywords")
    String[] keywords;

    @SerializedName("categories")
    String[] categories;

    @SerializedName("availability")
    String availability;

    @SerializedName("groupId")
    String groupId;

    @SerializedName("price")
    String price;

    @SerializedName("salePrice")
    String salePrice;

    @SerializedName("links")
    Links links;

    @SerializedName("images")
    String[] images;

    @SerializedName("metadata")
    String metadata;

    @SerializedName("sku")
    String sku;

    @SerializedName("score")
    float score;

    /**
     * Default constructor
     */
    public Offer(){
    }

    protected Offer(Parcel in) {
        title = in.readString();
        description = in.readString();
        descriptionLong = in.readString();
        language = in.readString();
        brand = in.readString();
        catalogNumbers = in.createFloatArray();
        customIds = in.readParcelable(CustomIds.class.getClassLoader());
        keywords = in.createStringArray();
        categories = in.createStringArray();
        availability = in.readString();
        groupId = in.readString();
        price = in.readString();
        salePrice = in.readString();
        links = in.readParcelable(Links.class.getClassLoader());
        images = in.createStringArray();
        metadata = in.readString();
        sku = in.readString();
        score = in.readFloat();
    }

    public static final Parcelable.Creator<Offer> CREATOR = new Parcelable.Creator<Offer>() {
        @Override
        public Offer createFromParcel(Parcel in) {
            return new Offer(in);
        }

        @Override
        public Offer[] newArray(int size) {
            return new Offer[size];
        }
    };

    /**
     * Get Offer title
     * @return String value
     */
    public String getTitle() {
        return title;
    }

    /**
     * Get Offer description
     * @return String value
     */
    public String getDescription() {
        return description;
    }

    /**
     * Get Offer long description
     * @return String value
     */
    public String getDescriptionLong() {
        return descriptionLong;
    }

    /**
     * Get Offer language
     * @return String value
     */
    public String getLanguage() {
        return language;
    }

    /**
     * Get Offer brand
     * @return String value
     */
    public String getBrand() {
        return brand;
    }

    /**
     * Get Offer catalog numbers
     * @return Array of float
     */
    public float[] getCatalogNumbers() {
        return catalogNumbers;
    }

    /**
     * Get Offer custom ids
     * @return CustomIds object
     * @see CustomIds
     */
    public CustomIds getCustomIds() {
        return customIds;
    }

    /**
     * Get Offer keywords
     * @return Array of String
     */
    public String[] getKeywords() {
        return keywords;
    }

    /**
     * Get Offer categories
     * @return Array of String
     */
    public String[] getCategories() {
        return categories;
    }

    /**
     * Get Offer availability
     * @return String value
     */
    public String getAvailability() {
        return availability;
    }

    /**
     * Get Offer group ID
     * @return String value
     */
    public String getGroupId() {
        return groupId;
    }

    /**
     * Get Offer price
     * @return String value
     */
    public String getPriceStr() {
        return price;
    }

    /**
     * Get Offer sale price
     * @return String value
     */
    public String getSalePrice() {
        return salePrice;
    }

    /**
     * Get Offer Links
     * @return Links object
     * @see Links
     */
    public Links getLinks() {
        return links;
    }

    /**
     * Get Offer images
     * @return Array of String
     */
    public String[] getImages() {
        return images;
    }

    /**
     * Get Offer metadata
     * @return String value
     */
    public String getMetadata() {
        return metadata;
    }

    /**
     * Get Offer SKU
     * @return String value
     */
    public String getSku() {
        return sku;
    }

    /**
     * Get Offer SKU
     * @return float value
     */
    public float getScore() {
        return score;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(description);
        parcel.writeString(descriptionLong);
        parcel.writeString(language);
        parcel.writeString(brand);
        parcel.writeFloatArray(catalogNumbers);
        parcel.writeParcelable(customIds, i);
        parcel.writeStringArray(keywords);
        parcel.writeStringArray(categories);
        parcel.writeString(availability);
        parcel.writeString(groupId);
        parcel.writeString(price);
        parcel.writeString(salePrice);
        parcel.writeParcelable(links, i);
        parcel.writeStringArray(images);
        parcel.writeString(metadata);
        parcel.writeString(sku);
        parcel.writeFloat(score);
    }
}
