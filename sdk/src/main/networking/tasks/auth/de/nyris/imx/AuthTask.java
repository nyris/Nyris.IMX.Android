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

import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

/**
 * AuthTask.java - Async Task class that send request to login with
 * client id and secret
 *
 * @see BaseTask
 * @author Sidali Mellouk
 * Created by nyris GmbH
 * Copyright © 2017 nyris GmbH. All rights reserved.
 */
class AuthTask extends BaseTask{
    private IAuthCallback authCallback;
    private String clientId;
    private String clientSecret;
    private Scope scope;

    /**
     * Constructor
     * @param authCallback A variable of type IAuthCallback
     * @param clientId A variable of type String
     * @param clientSecret A variable of type String
     * @param scope A variable of type Scope
     * @param endpoints A variable of type INyrisEndpoints
     * @see Scope
     * @see INyrisEndpoints
     */
    AuthTask(Context context, IAuthCallback authCallback, String clientId, String clientSecret, Scope scope,
             INyrisEndpoints endpoints){
        super(context, authCallback, endpoints);
        this.scope = scope;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.authCallback= authCallback;
        this.endpoints = endpoints;
    }

    @Override
    protected Object doInBackground(Void... params) {
        if(!Helpers.isOnline(context))
            return null;

        RequestBody formBody = new FormEncodingBuilder()
                .add(ParamKeys.grantType, ParamKeys.clientCredentials)
                .add(ParamKeys.scope, scope.name())
                .add(ParamKeys.clientId, clientId)
                .add(ParamKeys.clientSecret, clientSecret)
                .build();
        Response response = HttpRequestHelper.post(endpoints.getOpenIdApi(), formBody);
        Object content = null;
        try {
            content = getResponseContent(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content;
    }

    @Override
    protected void onPostExecute(Object content) {
        if(authCallback== null)
            return;
        if(content instanceof ResponseError) {
            ResponseError responseError = (ResponseError) content;
            authCallback.onError(responseError);
        }
        else {
            String strContent = (String) content;
            evaluateContentAuthResponse(context, strContent, authCallback);
        }
    }
}
