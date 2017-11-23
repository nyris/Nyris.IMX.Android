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

import android.os.Build;

import com.facebook.stetho.okhttp.StethoInterceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.util.concurrent.TimeUnit;

class HttpRequestHelper{
    private static int timeout = 60;
    private static String userAgent;
    static{
        userAgent = "de.nyris.imx";
        userAgent += "/"+ BuildConfig.MAJOR_MINOR_PATCH;
        userAgent+= " ("+ BuildConfig.LAST_COMMIT_HASH+"; Android "+ Build.VERSION.RELEASE+")";
    }

    /**
     * GET function that help to create and HTTP GET request
     * @param url A variable of type String
     * @param token A variable of type AccessToken
     * @see AccessToken
     * @return A Response of get request
     */
    static Response get(String url, AccessToken token){
        if(token == null)
            return null;
        OkHttpClient mHttpClient =  new OkHttpClient();
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
            return mHttpClient.newCall(request).execute();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * POST function that help to create and HTTP GET request
     * @param url A variable of type String
     * @param formBody A Variable of type Request Body
     * @return A Response of get request
     */
    static Response post(String url, RequestBody formBody){
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
            e.printStackTrace();
        }
        return null;
    }

    /**
     * POST function that help to create and HTTP POST request
     * @param request A variable of type Request
     * @see AccessToken
     * @return A Response of post request
     */
    static Response post(Request request) {
        OkHttpClient mHttpClient =  new OkHttpClient();
        if(NyrisEndpoints.DEBUG)
            mHttpClient.networkInterceptors().add(new StethoInterceptor());
        mHttpClient.setConnectTimeout(timeout, TimeUnit.SECONDS);
        mHttpClient.setReadTimeout(timeout, TimeUnit.SECONDS);

        try{
            return mHttpClient.newCall(request).execute();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
