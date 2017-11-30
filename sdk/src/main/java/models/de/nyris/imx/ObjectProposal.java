package de.nyris.imx;

import android.graphics.RectF;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sidali on 30.11.17.
 */

public class ObjectProposal {
    @SerializedName("confidence")
    private float confidence;

    @SerializedName("region")
    private RectF region;
    public ObjectProposal(){}

    public float getConfidence() {
        return confidence;
    }

    public RectF getRegion() {
        return region;
    }
}
