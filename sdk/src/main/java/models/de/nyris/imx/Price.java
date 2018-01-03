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
 * Price.java - A class model that contain Offer Price information
 *
 * @author  Sidali Mellouk
 * Created by nyris GmbH
 * Copyright © 2017 nyris GmbH. All rights reserved.
 */
public class Price implements Serializable, Parcelable {
    @SerializedName("c")
    private String currency;
    @SerializedName("ve")
    private String valueExludingTax;
    @SerializedName("vi")
    private String valueIncludingTax;
    @SerializedName("t")
    private String taxRates;

    /**
     * Default Constructor
     */
    public Price(){
    }

    protected Price(Parcel in) {
        currency = in.readString();
        valueExludingTax = in.readString();
        valueIncludingTax = in.readString();
        taxRates = in.readString();
    }

    public static final Creator<Price> CREATOR = new Creator<Price>() {
        @Override
        public Price createFromParcel(Parcel in) {
            return new Price(in);
        }

        @Override
        public Price[] newArray(int size) {
            return new Price[size];
        }
    };

    /**
     * Get currency type
     * @return String value
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * Get Price Exluding Tax
     * @return String value
     */
    public String getValueExludingTax() {
        return valueExludingTax;
    }

    /**
     * Get Price including tax
     * @return String value
     */
    public String getValueIncludingTax() {
        return valueIncludingTax;
    }

    /**
     * Get Digital Price including tax
     * @return String value
     */
    public float getDigitalValueIncludingTax() {
        return Float.parseFloat(valueIncludingTax)/100;
    }

    /**
     * Get Tax Rates
     * @return String value
     */
    public String getTaxRates() {
        if(taxRates== null || taxRates.isEmpty())
        {
            float valueExludingTax = Float.parseFloat(this.valueExludingTax);
            float valueIncludingTax = Float.parseFloat(this.valueIncludingTax);
            float taxRates = Math.round((valueIncludingTax/valueExludingTax)-1);
            this.taxRates = taxRates+"";
        }
        return taxRates;
    }

    /**
     * Get taxes
     * @return Float value
     */
    public float getTaxes(){
        float valueExludingTax = Float.parseFloat(this.valueExludingTax);
        float valueIncludingTax = Float.parseFloat(this.valueIncludingTax);
        return (valueIncludingTax - valueExludingTax)/100;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(currency);
        dest.writeString(valueExludingTax);
        dest.writeString(valueIncludingTax);
        dest.writeString(taxRates);
    }
}
