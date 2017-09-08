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

import com.google.gson.Gson;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

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
    private double lat;
    private double lon;
    private double acc;
    private byte[] image;
    private IMatchCallback matchCallback;

    /**
     * Constructor
     * @param image A variable of type array of bytes
     * @param lat A variable of type double
     * @param lon A variable of type double
     * @param acc A variable of type double
     * @param matchCallback A variable of type IMatchCallback
     * @param accessToken A Variable of type AccessToken
     * @param endpoints A variable of type INyrisEndpoints
     * @param reporter A variable of type ICrashReporter
     * @see IMatchCallback
     * @see AccessToken
     * @see INyrisEndpoints
     * @see ICrashReporter
     */
    ImageMatchingTask(byte[] image, double lat, double lon, double acc, IMatchCallback matchCallback, AccessToken accessToken,
                      INyrisEndpoints endpoints, ICrashReporter reporter){
        super(matchCallback,accessToken,endpoints,reporter);
        this.image = image;
        this.lat = lat;
        this.lon = lon;
        this.acc = acc;
        this.matchCallback = matchCallback;
    }

    /**
     * Constructor
     * @param image A variable of type array of bytes
     * @param matchCallback A variable of type IMatchCallback
     * @param accessToken A Variable of type AccessToken
     * @param endpoints A variable of type INyrisEndpoints
     * @param reporter A variable of type ICrashReporter
     * @see IMatchCallback
     * @see AccessToken
     * @see INyrisEndpoints
     * @see ICrashReporter
     */
    ImageMatchingTask(byte[] image, IMatchCallback matchCallback, AccessToken accessToken, INyrisEndpoints endpoints, ICrashReporter reporter){
        super(matchCallback, accessToken,endpoints,reporter);
        this.image = image;
        this.matchCallback = matchCallback;
    }

    @Override
    protected Object doInBackground(Void... params) {
        Response response = HttpRequestHelper.getInstance().post(endpoints.getImageMatchingApi(), accessToken, image,reporter);
        Object content = null;
        try {
            content = getResponseContent(response);
        } catch (Exception e) {
            e.printStackTrace();
            if(reporter!= null)
                reporter.reportException(e);
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
                if(!strContent.isEmpty())
                    try {
                        matchCallback.onMatched(new JSONObject(strContent));
                    } catch (JSONException e) {
                        responseError.setErrorCode(ResponseCode.JSON_ERROR);
                        responseError.setErrorDescription(e.getMessage());
                        matchCallback.onError(responseError);
                        e.printStackTrace();
                    }
                ResponseImage responseImage = new Gson().fromJson(strContent, ResponseImage.class);
                if(responseImage != null){
                    List<OfferInfo> offerInfos = responseImage.getOfferInfos();
                    matchCallback.onMatched(offerInfos);
                }
                else {
                    responseError.setErrorCode(ResponseCode.IMAGE_NOT_FOUND_ERROR);
                    responseError.setErrorDescription(Helpers.getInstance().getText(R.string.error_offer_not_found));
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
