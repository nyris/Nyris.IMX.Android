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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

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
    private int cacheValidity = 3600; //1 hour
    private List<AsyncTask> tasks;

    //Managers
    private IAuthManager authManager;

    //Endpoints
    private INyrisEndpoints endpoints;

    //Analytics
    private ICrashReporter reporter;

    private OnCompleteListener<Void> onCompleteListener = new OnCompleteListener<Void>() {
        @Override
        public void onComplete(@NonNull Task<Void> task) {
            if (task.isSuccessful()) {
                refreshEndoints();
            } else {
                FirebaseRemoteConfig.getInstance().fetch(cacheValidity);
            }
        }
    };

    /**
     * Refresh endpoints and client id value from firebase remote config
     */
    private void refreshEndoints(){
        FirebaseRemoteConfig.getInstance().activateFetched();

        Helpers.getInstance().saveParam(ParamKeys.live+ParamKeys.openId,
                FirebaseRemoteConfig.getInstance().getString(ParamKeys.live+ParamKeys.openId));
        Helpers.getInstance().saveParam(ParamKeys.live+ParamKeys.imageMatching,
                FirebaseRemoteConfig.getInstance().getString(ParamKeys.live+ParamKeys.imageMatching));

        String endpoint = Helpers.getInstance().getParam(ParamKeys.live+ParamKeys.openId);
        if( endpoint == null
                || endpoint.isEmpty())
            return;

        endpoints = NyrisEndpoints.getInstance();
    }

    @Override
    public void init(Context context){
        if(context == null)
            throw new RuntimeException("Context is null");

        tasks = new ArrayList<>();

        if(reporter == null)
            reporter = new FirebaseCrashReporter(context.getApplicationContext());

        Helpers.getInstance().init(context, reporter);

        FirebaseRemoteConfig.getInstance().fetch(cacheValidity).addOnCompleteListener(onCompleteListener);

        String endpoint = Helpers.getInstance().getParam(ParamKeys.live+ParamKeys.openId);
        if( endpoint == null
                || endpoint.isEmpty())
            return;
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
    public INyrisEndpoints getEverybagEndpoits() {
        return endpoints;
    }

    public String getClientId() {
        return authManager== null ? null : authManager.getClientId();
    }

    @Override
    public void login(final @NonNull String clientId, final @NonNull String clientSecret,
                      final @NonNull IAuthCallback authCallback){
        if(!isEndpointsSet()) {
            loadRemoteConfig(new IRemoteConfigCallback() {
                @Override
                public void onSuccess() {
                    loginWithClientIdAndSecret(clientId, clientSecret, Scope.image_matching, authCallback);
                }
            });
        }else{
            loginWithClientIdAndSecret(clientId, clientSecret, Scope.image_matching, authCallback);
        }
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
        authManager = new AuthManager(authCallback,clientId,clientSecret, scope, endpoints, reporter);
        authManager.execute();
    }

    /**
     * Check if Endpoints is set
     * @return Boolean value, true for set false for unset
     * @see ICallback
     */
    private boolean isEndpointsSet(){
        return endpoints != null;
    }

    /**
     * Get Remote config from firebase
     * @param callback A variable of type IRemoteConfigCallback
     * @see IRemoteConfigCallback
     */
    private void loadRemoteConfig(final IRemoteConfigCallback callback){
        FirebaseRemoteConfig.getInstance().fetch(cacheValidity).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    refreshEndoints();
                    callback.onSuccess();
                }else {
                    FirebaseRemoteConfig.getInstance().fetch(cacheValidity).addOnCompleteListener(this);
                }
            }
        });
    }

    @Override
    public void match(final @NonNull byte[] image, final @NonNull IMatchCallback matchCallback) {
        if(authManager == null || authManager.getToken() == null)
            throw new RuntimeException("You need to login first before using match image");
        ImageMatchingTask task = new ImageMatchingTask(image,matchCallback, authManager.getToken(), endpoints, reporter);
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
