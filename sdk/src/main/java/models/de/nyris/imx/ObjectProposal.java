package de.nyris.imx;

import android.graphics.RectF;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by sidali on 30.11.17.
 */

public class ObjectProposal implements Serializable {
    @SerializedName("confidence")
    private float confidence;

    @SerializedName("region")
    private RectF region;

    @SerializedName("className")
    private String className;

    public ObjectProposal(){

    }

    public ObjectProposal(float confidence, RectF region, String className){
        this.confidence = confidence;
        this.region = region;
        this.className = className;
    }

    public void setConfidence(float confidence) {
        this.confidence = confidence;
    }

    public void setRegion(RectF region) {
        this.region = region;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public float getConfidence() {
        return confidence;
    }

    public RectF getRegion() {
        return region;
    }

    public String getClassName() {
        return className;
    }
}
