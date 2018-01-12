package de.nyris.imx;

import android.content.Context;

import com.google.gson.Gson;

import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * TextSearchTask.java - Async Task class that send text/sku/id request to Image matching API to
 * get offers
 *
 * @see BaseTask
 * @author Sidali Mellouk
 * Created by nyris GmbH
 * Copyright © 2017 nyris GmbH. All rights reserved.
 */
public class TextSearchTask extends BaseTask{
    private String text;
    private IMatchCallback callback;
    private String outputFormat;
    private String language;
    /**
     * Constructor
     * @param context A variable of type Context
     * @param text A variable of type String
     * @param callback A variable of type IMatchObjectProposalCallback
     * @param clientId A Variable of type String
     * @param endpoints A variable of type INyrisEndpoints
     * @see Context
     * @see IMatchCallback
     * @see INyrisEndpoints
     */
    TextSearchTask(Context context, String clientId, String text,String outputFormat,String language,
                   IMatchCallback callback, INyrisEndpoints endpoints){
        super(context, clientId, callback,endpoints);
        this.text = text;
        this.callback = callback;
        this.outputFormat = outputFormat;
        this.language = language;

        if(this.outputFormat == null){
            this.outputFormat = "application/offers.complete+json";
        }

        if(this.language == null){
            this.language = "*";
        }
    }


    @Override
    protected Object doInBackground(Void... params) {
        Request.Builder builder = new Request.Builder()
                .url(endpoints.getTextSearchApi())
                .addHeader("X-Api-Key", clientId)
                .addHeader("Content-Length", text.length()+"")
                .addHeader("Accept-Language", language)
                .addHeader("Accept", outputFormat);
        builder.post(RequestBody.create(MediaType.parse("text/plain; charset=utf-8"), text));

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
        if(callback == null)
            return;
        if(content instanceof ResponseError){
            ResponseError responseError = (ResponseError) content;
            callback.onError(responseError);
        }
        else{
            String strContent = (String) content;
            try {
                if(!strContent.isEmpty())
                    callback.onMatched(strContent);
                ResponseImage responseImage = new Gson().fromJson(strContent, ResponseImage.class);
                if(responseImage != null){
                    callback.onMatched(responseImage.getOffers());
                }
                else {
                    responseError.setErrorCode(ResponseCode.IMAGE_NOT_FOUND_ERROR);
                    responseError.setErrorDescription(context.getString(R.string.error_offer_not_found));
                    callback.onError(responseError);
                }
            }
            catch (Exception e){
                e.printStackTrace();
                responseError.setErrorCode(ResponseCode.UNKNOWN_ERROR);
                responseError.setErrorDescription(e.getMessage());
                callback.onError(responseError);
            }
        }
    }
}
