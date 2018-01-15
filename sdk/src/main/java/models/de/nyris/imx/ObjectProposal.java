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

import android.content.Context;
import android.graphics.RectF;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * ObjectProposal.java - A class model that contain Extracted object information
 *
 * @author  Sidali Mellouk
 * Created by nyris GmbH
 * Copyright © 2017 nyris GmbH. All rights reserved.
 */

public class ObjectProposal implements Serializable {
    @SerializedName("confidence")
    private float confidence;

    @SerializedName("region")
    private RectF region;

    @SerializedName("className")
    private String className;
    /**
     * Default constructor
     */
    public ObjectProposal(){
    }

    /**
     * Constructor
     * @param confidence A variable of type float
     * @param region A variable of type RectF
     * @param className A variable of type string
     */
    public ObjectProposal(float confidence, RectF region, String className){
        this.confidence = confidence;
        this.region = region;
        this.className = className;
    }

    /**
     * Set Confidence
     * @param confidence A variable of type float
     */
    public void setConfidence(float confidence) {
        this.confidence = confidence;
    }

    /**
     * Set Region
     * @param region A variable of type RectF
     */
    public void setRegion(RectF region) {
        this.region = region;
    }

    /**
     * Set Region
     * @param className A variable of type string
     */
    public void setClassName(String className) {
        this.className = className;
    }

    /**
     * Get Confidence
     * @return float value
     */
    public float getConfidence() {
        return confidence;
    }

    /**
     * Get Region
     * @return return RectF Object
     */
    public RectF getRegion() {
        return region;
    }

    /**
     * Get class name
     * @return String value
     */
    public String getClassName() {
        return className;
    }
}
