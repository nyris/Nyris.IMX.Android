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
import com.google.gson.annotations.SerializedName;

/**
 * OfferInfo.java - A class model that contain offer information
 *
 * @author  Sidali Mellouk
 * Created by nyris GmbH
 * Copyright © 2017 nyris GmbH. All rights reserved.
 */
public class OfferInfo{
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
}
