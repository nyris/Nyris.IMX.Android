package de.nyris.imx;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

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
class ObjectProposalTask extends BaseTask{
    private byte[] image;
    private IObjectExtractCallback callback;
    /**
     * Constructor
     * @param context A variable of type Context
     * @param image A variable of type array of bytes
     * @param callback A variable of type IMatchObjectProposalCallback
     * @param clientId A Variable of type String
     * @param endpoints A variable of type INyrisEndpoints
     * @see Context
     * @see IObjectExtractCallback
     * @see INyrisEndpoints
     */
    ObjectProposalTask(Context context, String clientId, byte[] image, IObjectExtractCallback callback,
                       INyrisEndpoints endpoints){
        super(context, clientId, callback,endpoints);
        this.image = image;
        this.callback = callback;
    }


    @Override
    protected Object doInBackground(Void... params) {
        Request.Builder builder = new Request.Builder()
                .url(endpoints.getObjectProposalApi())
                .addHeader("X-Api-Key", clientId)
                .addHeader("Content-Length", image.length+"");
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
        if(callback == null)
            return;
        if(content instanceof ResponseError){
            ResponseError responseError = (ResponseError) content;
            callback.onError(responseError);
        }
        else{
            String strContent = (String) content;
            try {
                Gson gson = new Gson();
                List<ObjectProposal> objectProposals = gson.fromJson(strContent, new TypeToken<List<ObjectProposal>>(){}.getType());
                if(objectProposals != null){
                    callback.onObjectExtracted(objectProposals);
                }
                else {
                    responseError.setErrorCode(ResponseCode.OBJECT_NOT_FOUND_ERROR);
                    responseError.setErrorDescription(context.getText(R.string.error_object_not_found).toString());
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
