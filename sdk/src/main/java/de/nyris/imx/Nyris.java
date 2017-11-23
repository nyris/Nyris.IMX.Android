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
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Nyris.java - A class that implements INyris that contain different SDK operations
 *
 * @see INyris
 * @author Sidali Mellouk
 * Created by nyris GmbH
 * Copyright © 2017 nyris GmbH. All rights reserved.
 */
public class Nyris implements INyris {
    private static Nyris instance;
    private boolean isCrashReport;
    private List<AsyncTask> tasks;
    private Context mContext;

    //Managers
    private IAuthManager authManager;

    //Endpoints
    private INyrisEndpoints endpoints;

    @Override
    public void init(Context context){
        if(context == null)
            throw new RuntimeException("Context is null");
        mContext = context;
        tasks = new ArrayList<>();
        endpoints = NyrisEndpoints.getInstance();
    }
    /**
     * Get Instance of SDK and init params to defaults values
     * @return Instance of INyris
     */
    public static INyris getInstance(){
        if(instance == null){
            instance = new Nyris();
            instance.tasks = new ArrayList<>();
        }
        return instance;
    }

    public static INyris getInstance(Context context){
        if(instance == null){
            instance = new Nyris();
            instance.init(context);
        }
        return instance;
    }

    @Override
    public INyris enableCrashReport(boolean isEnbaled) {
        this.isCrashReport = isEnbaled;
        return this;
    }

    @Override
    public boolean isCrashReport() {
        return isCrashReport;
    }

    @Override
    public INyrisEndpoints getNyrisEndpoits() {
        return endpoints;
    }

    public String getClientId() {
        return authManager== null ? null : authManager.getClientId();
    }

    @Override
    public void login(final @NonNull String clientId, final @NonNull String clientSecret,
                      final @NonNull IAuthCallback authCallback){
        loginWithClientIdAndSecret(clientId, clientSecret, Scope.image_matching, authCallback);
    }

    /**
     * Login with client id and secret
     * @param clientId A variable of type String
     * @param clientSecret A varibale of type String
     * @param scope A variable of type Scope
     * @param authCallback A varibale of type IAuthCallback
     * @see Scope
     * @see IAuthCallback
     */
    private void loginWithClientIdAndSecret(String clientId, String clientSecret, Scope scope, IAuthCallback authCallback){
        endpoints = new NyrisLiveEndpoints();
        authManager = new AuthManager(mContext,authCallback,clientId,clientSecret, scope, endpoints);
        authManager.execute();
    }

    @Override
    public void match(final @NonNull byte[] image, final @NonNull IMatchCallback matchCallback) {
        if(authManager == null || authManager.getToken() == null)
            throw new RuntimeException("You need to login first before using match image");
        ImageMatchingTask task = new ImageMatchingTask(mContext,image,matchCallback, authManager.getToken(), endpoints);
        task.execute();
        tasks.add(task);
    }

    @Override
    public void match(byte[] image, boolean isOnlySimilarOffers, IMatchCallback matchCallback) {
        if(authManager == null || authManager.getToken() == null)
            throw new RuntimeException("You need to login first before using match image");
        ImageMatchingTask task = new ImageMatchingTask(mContext, image,isOnlySimilarOffers, matchCallback, authManager.getToken(), endpoints);
        task.execute();
        tasks.add(task);
    }

    @Override
    public void clearAllTasks() {
        if(authManager == null)
            return;
        authManager.cancel();
        for (AsyncTask task : tasks) {
            if(task.getStatus() != AsyncTask.Status.FINISHED)
                task.cancel(true);
        }
        for (AsyncTask task : tasks) {
            tasks.remove(task);
        }
    }
}
