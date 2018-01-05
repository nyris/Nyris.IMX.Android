package de.nyris.imx;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by sidali on 1/5/18.
 */

public class Offer extends OfferInfo implements Serializable, Parcelable{
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

    public static final Parcelable.Creator<OfferInfo> CREATOR = new Parcelable.Creator<OfferInfo>() {
        @Override
        public OfferInfo createFromParcel(Parcel in) {
            return new OfferInfo(in);
        }

        @Override
        public OfferInfo[] newArray(int size) {
            return new OfferInfo[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getDescriptionLong() {
        return descriptionLong;
    }

    public String getLanguage() {
        return language;
    }

    public String getBrand() {
        return brand;
    }

    public float[] getCatalogNumbers() {
        return catalogNumbers;
    }

    public CustomIds getCustomIds() {
        return customIds;
    }

    public String[] getKeywords() {
        return keywords;
    }

    public String[] getCategories() {
        return categories;
    }

    public String getAvailability() {
        return availability;
    }

    public String getGroupId() {
        return groupId;
    }

    public String getPriceStr() {
        return price;
    }

    public String getSalePrice() {
        return salePrice;
    }

    public Links getLinks() {
        return links;
    }

    public String[] getImages() {
        return images;
    }

    public String getMetadata() {
        return metadata;
    }

    public String getSku() {
        return sku;
    }

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
