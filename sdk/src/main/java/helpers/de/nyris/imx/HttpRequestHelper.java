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

import com.facebook.stetho.okhttp3.StethoInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

class HttpRequestHelper{
    private static int timeout = 60;
    private static String userAgent;
    static{
        userAgent = "de.nyris.imx";
        userAgent += "/"+ BuildConfig.MAJOR_MINOR_PATCH;
        userAgent+= " ("+ BuildConfig.LAST_COMMIT_HASH+"; Android "+ Build.VERSION.RELEASE+")";
    }

    /**
     * POST function that help to create and HTTP GET request
     * @param url A variable of type String
     * @param formBody A Variable of type Request Body
     * @return A Response of get request
     * @see Response
     */
    static Response post(String url, RequestBody formBody){
        OkHttpClient.Builder httpBuilder = new OkHttpClient.Builder()
                .connectTimeout(timeout, TimeUnit.SECONDS)
                .readTimeout(timeout, TimeUnit.SECONDS);
        if(NyrisEndpoints.DEBUG)
            httpBuilder.networkInterceptors().add(new StethoInterceptor());
        OkHttpClient mHttpClient = httpBuilder.build();

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
     * @param builder A variable of type Request.Builder
     * @return A Response of post request
     * @see Response
     * @see Request.Builder
     */
    static Response post(Request.Builder builder) {
        OkHttpClient.Builder httpBuilder = new OkHttpClient.Builder()
                .connectTimeout(timeout, TimeUnit.SECONDS)
                .readTimeout(timeout, TimeUnit.SECONDS);
        if(NyrisEndpoints.DEBUG)
            httpBuilder.networkInterceptors().add(new StethoInterceptor());
        OkHttpClient mHttpClient = httpBuilder.build();

        builder.addHeader("User-Agent", userAgent);
        try{
            return mHttpClient.newCall(builder.build()).execute();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
