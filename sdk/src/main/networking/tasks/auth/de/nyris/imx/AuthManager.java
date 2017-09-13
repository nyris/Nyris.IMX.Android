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

import com.google.gson.Gson;

import java.util.Date;

/**
 * AuthManager.java - A class that handle authentication request
 *
 * @see IAuthManager
 * @author Sidali Mellouk
 * Created by nyris GmbH
 * Copyright © 2017 nyris GmbH. All rights reserved.
 */

class AuthManager implements IAuthManager {
    private IAuthCallback callback;
    private String clientId;
    private String clientSecret;
    private Scope scope;
    private AuthTask authTask;
    private ICrashReporter reporter;
    /**
     * Constructor
     * @param authCallback A variable of type IAuthCallback
     * @param clientId A variable of type String
     * @param clientSecret A variable of type String
     * @param scope A variable of type Scope
     * @param endpoints A variable of type INyrisEndpoints
     * @param reporter A variable of type ICrashReporter     *
     * @see IAuthCallback
     * @see INyrisEndpoints
     * @see ICrashReporter
     */
    AuthManager(IAuthCallback authCallback, String clientId, String clientSecret,
                       Scope scope, INyrisEndpoints endpoints, ICrashReporter reporter){
        this.callback = authCallback;
        this.scope = scope;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.reporter = reporter;
        authTask = new AuthTask(authCallback, clientId, clientSecret, scope, endpoints, reporter);
    }

    @Override
    public AccessToken getToken() {
        String accessTokeStr = Helpers.getInstance().getParam(ParamKeys.accessToken);
        if(!accessTokeStr.isEmpty()){
            try {
                Gson gson = new Gson();
                AccessToken accessToken = gson.fromJson(accessTokeStr, AccessToken.class);
                return accessToken;
            }
            catch (Exception e){
                reporter.reportException(e);
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    @Override
    public String getClientId() {
        return clientId;
    }
    @Override
    public String getClientSecret() {
        return clientSecret;
    }
    @Override
    public Scope getScope() {
        return scope;
    }

    @Override
    public void execute() {
        if(authTask== null){
            if(callback != null)
                callback.onError(new ResponseError(ResponseCode.UNKNOWN_ERROR, "authTask is not implemented"));
            return;
        }
        AccessToken accessToken = getToken();
        if(accessToken!= null && !accessToken.isExpired()){
            callback.onSuccess(accessToken);
            return;
        }
        authTask.execute();
    }

    @Override
    public void cancel() {
        try{
            if(authTask!= null && authTask.getStatus() != AsyncTask.Status.FINISHED)
                authTask.cancel(true);
        }
        catch (Exception ignored){}
    }
}
