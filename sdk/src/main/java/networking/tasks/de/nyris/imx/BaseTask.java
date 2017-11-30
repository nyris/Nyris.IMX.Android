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
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;

import javax.net.ssl.HttpsURLConnection;

import okhttp3.Response;

class BaseTask extends AsyncTask<Void, Void, Object> {
    Context context;
    String clientId;
    ICallback callback;
    INyrisEndpoints endpoints;
    ResponseError responseError;

    /**
     * Constructor
     * @param context A Variable of type Context
     * @param callback A Variable of type ICallback
     * @param endpoints A variable of type INyrisEndpoints
     * @see ICallback
     * @see INyrisEndpoints
     */
    BaseTask(Context context, String clientId, ICallback callback, INyrisEndpoints endpoints){
        this.context = context;
        this.clientId = clientId;
        this.callback = callback;
        this.endpoints = endpoints;
        this.responseError = new ResponseError();
    }

    /**
     * Check Network availability before start any requests
     */
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if(!Helpers.isOnline(context) && callback != null){
            responseError.setErrorCode(ResponseCode.NO_INTERNET);
            responseError.setErrorDescription(context.getString( R.string.error_no_network_msg));
            callback.onError(responseError);
        }
    }

    /**
     * Send request o nyris APIs
     * @param params Array of void
     * @return Object response
     */
    @Override
    protected Object doInBackground(Void... params){
        return null;
    }

    /**
     * Get Response Content : this method evaluate the response based on the HTTP_CODE and extract
     * the content of the reponse
     * @param response A variable of type Object (Could be String or ResponseError)
     * @return
     */
    Object getResponseContent(Response response){
        if(response == null)
            return new ResponseError(ResponseCode.HTTP_TIME_OUT, "Unable to connect to Nyris services, check your internet connection.");
        String content = null;
        try {
            content = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.i("error code : "+response.code(),content);
        switch (response.code()){
            case HttpsURLConnection.HTTP_OK:
            case HttpsURLConnection.HTTP_CREATED:
            case HttpsURLConnection.HTTP_NO_CONTENT:
            case HttpsURLConnection.HTTP_ACCEPTED:
                return content ;

            default:
                ResponseError responseError = null;
                try {
                    Gson gson = new Gson();
                    responseError = gson.fromJson(content.toString(), ResponseError.class);
                }
                catch (Exception ignored){}

                if(responseError == null){
                    responseError = new ResponseError();
                    responseError.setErrorCode(convertErrorResponseCode(response));
                    String message = response.message();
                    if(message == null || message.isEmpty())
                        message = "Unknown Error";
                    responseError.setErrorDescription(message);
                }
                return responseError;
        }
    }

    /**
     * Convert Response code to defined Error Code
     * @param response A variable of type Response
     * @return String value Error Code
     */
    private String convertErrorResponseCode(Response response){
        String errorCode;
        if(response.code() == -1)
            return ResponseCode.HTTP_UNKNOWN_ERROR;
        switch (response.code()){
            case HttpsURLConnection.HTTP_INTERNAL_ERROR :
                errorCode = ResponseCode.HTTP_INTERNAL_ERROR;
                break;
            case HttpsURLConnection.HTTP_BAD_REQUEST :
            case HttpsURLConnection.HTTP_UNAUTHORIZED :
                errorCode = ResponseCode.HTTP_BAD_REQUEST_OR_UNAUTHORIZED;
                break;
            case HttpsURLConnection.HTTP_NOT_FOUND :
                errorCode = ResponseCode.HTTP_NOT_FOUND;
                break;
            case HttpsURLConnection.HTTP_ENTITY_TOO_LARGE :
                errorCode = ResponseCode.HTTP_ENTITY_TOO_LARGE;
                break;
            default:
                errorCode = ResponseCode.HTTP_UNKNOWN_ERROR;
        }
        return errorCode;
    }
}
