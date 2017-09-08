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

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.Response;

import java.io.IOException;

import javax.net.ssl.HttpsURLConnection;

class BaseTask extends AsyncTask<Void, Void, Object> {
    final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private ICallback callback;
    protected AccessToken accessToken;
    protected ICrashReporter reporter;
    protected INyrisEndpoints endpoints;
    ResponseError responseError;

    /**
     * Constructor
     * @param callback A Variable of type ICallback
     * @param accessToken A Variable of type AccessToken
     * @param endpoints A variable of type INyrisEndpoints
     * @param reporter A variable of type ICrashReporter
     * @see ICallback
     * @see AccessToken
     * @see INyrisEndpoints
     * @see ICrashReporter
     */
    public BaseTask(ICallback callback, AccessToken accessToken, INyrisEndpoints endpoints, ICrashReporter reporter){
        this.callback = callback;
        this.accessToken = accessToken;
        this.endpoints = endpoints;
        this.reporter = reporter;
        this.responseError = new ResponseError();
    }

    /**
     * Constructor
     * @param endpoints A variable of type INyrisEndpoints
     * @param reporter A vairbale of type ICrashReporter
     * @see INyrisEndpoints
     * @see ICrashReporter
     */
    public BaseTask(ICallback callback, INyrisEndpoints endpoints, ICrashReporter reporter){
        this.callback = callback;
        this.endpoints = endpoints;
        this.reporter = reporter;
        this.responseError = new ResponseError();
    }


    /**
     * Check Network availability before start any requests
     */
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if(!Helpers.getInstance().isOnline() && callback != null){
            responseError.setErrorCode(ResponseCode.NO_INTERNET);
            responseError.setErrorDescription(Helpers.getInstance().getText(R.string.error_no_network_msg));
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
        switch (response.code())
        {
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

                if(responseError == null)
                {
                    responseError = new ResponseError();
                    responseError.setErrorCode(convertErrorResponseCode(response));
                    if(content == null || content.isEmpty())
                        content = "Unknown Error";
                    responseError.setErrorDescription(content.toString());
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
        String message = response.message();
        if(response.code() == -1)
            return ResponseCode.HTTP_UNKNOWN_ERROR;
        if(response.code()>=500 && response.code() <= 600)
            reporter.reportException(new ServerException(message));
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
            default:
                errorCode = ResponseCode.HTTP_UNKNOWN_ERROR;
        }
        return errorCode;
    }

    /**
     * Evaluate Content of Authentication Response
     * @param content A variable of type String
     * @param authCallback A variable of type IAuthCallback
     * @return value of AccessToken
     * @see IAuthCallback
     * @see AccessToken
     */
    static AccessToken evaluateContentAuthResponse(String content, IAuthCallback authCallback){
        try{
            Helpers.getInstance().saveParam(ParamKeys.accessToken, content);
            Gson gson = new Gson();
            AccessToken token = gson.fromJson(content, AccessToken.class);
            if(token == null || token.getAccessToken() == null || token.getAccessToken().isEmpty()){
                ResponseError responseError = new ResponseError();
                responseError.setErrorCode(ResponseCode.ACCESS_TOKEN_ERROR);
                responseError.setErrorDescription(Helpers.getInstance().getText(R.string.error_token_getting));
                authCallback.onError(responseError);
                return null;
            }
            if(authCallback!= null)
                authCallback.onSuccess(token);
            return token;
        }
        catch (Exception e){
            e.printStackTrace();
            ResponseError responseError = new ResponseError();
            responseError.setErrorCode(ResponseCode.UNKNOWN_ERROR);
            responseError.setErrorDescription(content);
            authCallback.onError(responseError);
        }
        return null;
    }
}
