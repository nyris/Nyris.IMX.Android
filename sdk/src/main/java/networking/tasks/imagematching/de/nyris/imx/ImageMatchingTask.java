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

import com.google.gson.Gson;

import java.util.List;

import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * ImageMatchingTask.java - Async Task class that send request to Image matching API to get matched
 * offers
 *
 * @see BaseTask
 * @author Sidali Mellouk
 * Created by nyris GmbH
 * Copyright © 2017 nyris GmbH. All rights reserved.
 */
class ImageMatchingTask extends BaseTask{
    private double lat = -1;
    private double lon = -1;
    private double acc = -1;
    private byte[] image;
    private String outputFormat;
    private boolean isOnlySimilarOffers;
    private IMatchCallback matchCallback;

    /**
     * Constructor
     * @param context A variable of type Context
     * @param clientId A variable of type String
     * @param image A variable of type array of bytes
     * @param outputFormat A variable of type String
     * @param lat A variable of type double
     * @param lon A variable of type double
     * @param acc A variable of type double
     * @param matchCallback A variable of type IMatchCallback
     * @param endpoints A variable of type INyrisEndpoints
     * @see Context
     * @see IMatchCallback
     * @see INyrisEndpoints
     */
    ImageMatchingTask(Context context, String clientId, byte[] image, String outputFormat,double lat, double lon, double acc,
                      IMatchCallback matchCallback, INyrisEndpoints endpoints){
        super(context, clientId, matchCallback,endpoints);
        this.image = image;
        this.outputFormat = outputFormat;
        this.lat = lat;
        this.lon = lon;
        this.acc = acc;
        this.matchCallback = matchCallback;

        if(this.outputFormat == null){
            this.outputFormat = "application/offers.complete+json";
        }
    }

    /**
     * Constructor
     * @param context A variable of type Context
     * @param clientId A variable of type String
     * @param image A variable of type array of bytes
     * @param outputFormat A variable of type String
     * @param matchCallback A variable of type IMatchCallback
     * @param endpoints A variable of type INyrisEndpoints
     * @see Context
     * @see IMatchCallback
     * @see INyrisEndpoints
     */
    ImageMatchingTask(Context context, String clientId, byte[] image, String outputFormat,
                      IMatchCallback matchCallback, INyrisEndpoints endpoints){
        this(context, clientId, image, outputFormat, -1, -1, -1, matchCallback, endpoints);
    }

    /**
     * Constructor
     * @param context A variable of type Context
     * @param clientId A variable of type String
     * @param image A variable of type array of bytes
     * @param outputFormat A variable of type String
     * @param isOnlySimilarOffers A variable of type boolean
     * @param matchCallback A variable of type IMatchCallback
     * @param endpoints A variable of type INyrisEndpoints
     * @see Context
     * @see IMatchCallback
     * @see INyrisEndpoints
     */
    ImageMatchingTask(Context context, String clientId, byte[] image, String outputFormat,
                      boolean isOnlySimilarOffers, IMatchCallback matchCallback, INyrisEndpoints endpoints){
        this(context, clientId, image, outputFormat, matchCallback, endpoints);
        this.isOnlySimilarOffers = isOnlySimilarOffers;
    }

    @Override
    protected Object doInBackground(Void... params) {
        //Build Request
        String strEndpoints;
        if(lat == -1)
            strEndpoints = endpoints.getImageMatchingApi();
        else
            strEndpoints = endpoints.getImageMatchingApi(lat, lon, acc);

        Request.Builder builder = new Request.Builder()
                .url(strEndpoints)
                .addHeader("X-Api-Key", clientId)
                .addHeader("Content-Length", image.length+"")
                //Add this if you want to get offers based on our Model
                .addHeader("Accept", outputFormat);
        if(isOnlySimilarOffers)
            builder.addHeader("X-Only-Semantic-Search","nyris");
        builder.post(RequestBody.create(MediaType.parse("image/jpg"), image));

        Response response = HttpRequestHelper.post(builder);
        Object content = null;
        try {
            content = getResponseContent(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content;
    }

    /**
     * Evaluate the content of response
     * @param content of type Object it could be ErrorResponse or a String
     */
    @Override
    protected void onPostExecute(Object content) {
        if(matchCallback == null)
            return;
        if(content instanceof ResponseError){
            ResponseError responseError = (ResponseError) content;
            matchCallback.onError(responseError);
        }
        else{
            String strContent = (String) content;
            try {
                if(!strContent.isEmpty())
                    matchCallback.onMatched(strContent);
                ResponseImage responseImage = new Gson().fromJson(strContent, ResponseImage.class);
                if(responseImage != null){
                    matchCallback.onMatched(responseImage.getOfferInfos());
                    matchCallback.onMatched(responseImage.getOffers());
                }
                else {
                    responseError.setErrorCode(ResponseCode.IMAGE_NOT_FOUND_ERROR);
                    responseError.setErrorDescription(context.getString(R.string.error_offer_not_found));
                    matchCallback.onError(responseError);
                }
            }
            catch (Exception e){
                e.printStackTrace();
                responseError.setErrorCode(ResponseCode.UNKNOWN_ERROR);
                responseError.setErrorDescription(e.getMessage());
                matchCallback.onError(responseError);
            }
        }
    }
}
