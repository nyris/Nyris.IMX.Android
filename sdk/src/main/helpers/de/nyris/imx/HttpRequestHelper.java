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
import android.os.Build;
import android.support.annotation.NonNull;
import android.util.Log;

import com.facebook.stetho.okhttp.StethoInterceptor;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.util.concurrent.TimeUnit;

import javax.net.ssl.HttpsURLConnection;

public class HttpRequestHelper extends Helper{
    static HttpRequestHelper instance;
    private static int timeout = 60;
    private String userAgent;

    /**
     * A private constructor
     */
    private HttpRequestHelper(){}

    /**
     * Retrieve the value of the instance
     * @return HttpRequestHelper instance
     */
    public static HttpRequestHelper getInstance(){
        if(instance == null)
            instance = new HttpRequestHelper();
        return instance;
    }

    @Override
    protected void init(Context context){
        userAgent = context.getPackageName();
        userAgent += "/"+ BuildConfig.MAJOR_MINOR_PATCH;
        userAgent+= " ("+ BuildConfig.LAST_COMMIT_HASH+"; Android "+ Build.VERSION.RELEASE+")";
    }

    /**
     * GET function that help to create and HTTP GET request, this function have retry feature when
     * there are HTTP_UNAUTHORIZED Code, or when the token is expired.
     * @param url A variable of type String
     * @param token A variable of type AccessToken
     * @param reporter A variable of type ICrashReporter
     * @see ICrashReporter
     * @see AccessToken
     * @return A Response of get request
     */
    public Response get(String url, AccessToken token, @NonNull ICrashReporter reporter){
        int nbRrefreshToken = 0;
        Response response = null;
        if(token == null)
            return null;
        OkHttpClient mHttpClient =  new OkHttpClient();
        do{
            if(NyrisEndpoints.DEBUG)
                mHttpClient.networkInterceptors().add(new StethoInterceptor());
            mHttpClient.setConnectTimeout(timeout, TimeUnit.SECONDS);
            mHttpClient.setReadTimeout(timeout, TimeUnit.SECONDS);
            Request request = new Request.Builder()
                    .addHeader("Authorization", token.getTokenType() + " " + token.getAccessToken())
                    .addHeader("User-Agent", userAgent)
                    .url(url)
                    .build();
            try{
                response = mHttpClient.newCall(request).execute();
                if(response.code() == HttpsURLConnection.HTTP_UNAUTHORIZED){
                    token = refreshToken(token, reporter);
                }
                else return response;
            }
            catch(Exception e){
                reporter.reportException(e);
                Log.e(HttpRequestHelper.class.getName(),e.getMessage());
            }
            nbRrefreshToken++;
        } while(nbRrefreshToken < 3);
        return response;
    }

    /**
     * GET function that help to create and HTTP GET request, this function have retry feature when
     * there are HTTP_UNAUTHORIZED Code, or when the token is expired.
     * @param url A variable of type String
     * @param formBody A Variable of type Request Body
     * @param reporter A variable of type ICrashReporter
     * @see ICrashReporter
     * @return A Response of get request
     */
    public Response post(String url, RequestBody formBody, @NonNull ICrashReporter reporter){
        OkHttpClient mHttpClient =  new OkHttpClient();
        if(NyrisEndpoints.DEBUG)
            mHttpClient.networkInterceptors().add(new StethoInterceptor());
        mHttpClient.setConnectTimeout(timeout, TimeUnit.SECONDS); // connect timeout
        mHttpClient.setReadTimeout(timeout, TimeUnit.SECONDS);
        Request request = new Request.Builder()
                .url(url)
                .addHeader("User-Agent", userAgent)
                .post(formBody)
                .build();
        try{
            return mHttpClient.newCall(request).execute();
        }
        catch (Exception e){
            reporter.reportException(e);
        }
        return null;
    }

    /**
     * POST function that help to create and HTTP POST request, this function have retry feature when
     * there are HTTP_UNAUTHORIZED Code, or when the token is expired.
     * @param url A variable of type String
     * @param token A variable of type AccessToken
     * @param image A variable of type Array of bytes "body request"
     * @param reporter A variable of type ICrashReporter
     * @see ICrashReporter
     * @see AccessToken
     * @return A Response of post request
     */
    public Response post(String url, AccessToken token, byte[] image, @NonNull ICrashReporter reporter){
        int nbRrefreshToken = 0;
        Response response = null;
        final MediaType MEDIA_TYPE_JPG = MediaType.parse("image/jpg");
        OkHttpClient mHttpClient =  new OkHttpClient();
        do{
            if(NyrisEndpoints.DEBUG)
                mHttpClient.networkInterceptors().add(new StethoInterceptor());
            mHttpClient.setConnectTimeout(timeout, TimeUnit.SECONDS);
            mHttpClient.setReadTimeout(timeout, TimeUnit.SECONDS);
            Request.Builder builder = new Request.Builder()
                    .url(url)
                    .addHeader("Authorization", token.getTokenType() + " " + token.getAccessToken())
                    .addHeader("Content-Length", image.length+"")
                    .post(RequestBody.create(MEDIA_TYPE_JPG, image));
            Request request = builder.build();
            try{
                response = mHttpClient.newCall(request).execute();
                if(response.code() == HttpsURLConnection.HTTP_UNAUTHORIZED){
                    token = refreshToken(token, reporter);
                }
                else return response;
            }
            catch(Exception e){
                reporter.reportException(e);
                Log.e(HttpRequestHelper.class.getName(),e.getMessage());
            }
            nbRrefreshToken++;
            nbRrefreshToken++;
        }
        while(nbRrefreshToken < 3);
        return response;
    }

    /**
     * When token is expired or you get HTTP_UNAUTHORIZED code you call this to refresh token
     * @param token A variable of type AccessToken
     * @param reporter A variable of type ICrashReporter
     * @see ICrashReporter
     * @see AccessToken
     * @return An the new AccessToken
     */
    private AccessToken refreshToken(AccessToken token, ICrashReporter reporter){
        RequestBody formBody = new FormEncodingBuilder()
                .add(ParamKeys.grantType, ParamKeys.refreshToken)
                .add(ParamKeys.refreshToken, token.getRefreshToken())
                .add(ParamKeys.clientId, Nyris.getInstance().getClientId())
                .build();

        OkHttpClient mHttpClient =  new OkHttpClient();
        mHttpClient.setConnectTimeout(timeout, TimeUnit.SECONDS); // connect timeout
        mHttpClient.setReadTimeout(timeout, TimeUnit.SECONDS);
        Request request = new Request.Builder()
                .url(Nyris.getInstance().getEverybagEndpoits().getOpenIdApi())
                .post(formBody)
                .build();
        try {
            Response response = mHttpClient.newCall(request).execute();
            return BaseTask.evaluateContentAuthResponse(response.body().string(), null);
        }
        catch (Exception e){
            e.printStackTrace();
            if(reporter!= null)
                reporter.reportException(e);
        }
        return null;
    }
}
