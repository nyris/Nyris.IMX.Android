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
    private List<AsyncTask> tasks;
    private Context mContext;
    private INyrisEndpoints endpoints;
    private String mClientId;
    private String mOutputFormat;

    @Override
    public INyris init(Context context, String clientId){
        if(context == null)
            throw new RuntimeException("Context is null");
        if(clientId == null)
            throw new RuntimeException("clientId is null, you need to set your client id!");
        mContext = context;
        mClientId = clientId;
        tasks = new ArrayList<>();
        endpoints = NyrisEndpoints.getInstance();
        return this;
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

    public static INyris getInstance(Context context, String clientId){
        if(instance == null){
            instance = new Nyris();
            instance.init(context, clientId);
        }
        return instance;
    }

    @Override
    public INyrisEndpoints getNyrisEndpoits() {
        return endpoints;
    }

    public String getClientId() {
        return mClientId;
    }

    @Override
    public INyris setClientId(final @NonNull String clientId){
        mClientId = clientId;
        return this;
    }

    @Override
    public INyris setOutputformat(String outputFormat) {
        mOutputFormat = outputFormat;
        return this;
    }

    @Override
    public void match(final @NonNull byte[] image, final @NonNull IMatchCallback matchCallback) {
        if(mClientId == null)
            throw new RuntimeException("clientId is null, you need to set your client id!");
        ImageMatchingTask task = new ImageMatchingTask(mContext, mClientId, image, mOutputFormat, matchCallback, endpoints);
        task.execute();
        tasks.add(task);
    }

    @Override
    public void match(byte[] image, boolean isOnlySimilarOffers, IMatchCallback matchCallback) {
        if(mClientId == null)
            throw new RuntimeException("clientId is null, you need to set your client id!");
        ImageMatchingTask task = new ImageMatchingTask(mContext, mClientId, image, mOutputFormat, isOnlySimilarOffers, matchCallback, endpoints);
        task.execute();
        tasks.add(task);
    }

    @Override
    public void extractObjects(byte[] image, IObjectExtractCallback callback) {
        if(mClientId == null)
            throw new RuntimeException("clientId is null, you need to set your client id!");
        ObjectProposalTask task = new ObjectProposalTask(mContext, mClientId, image, callback, endpoints);
        task.execute();
        tasks.add(task);
    }

    @Override
    public void clearAllTasks() {
        if(tasks == null)
            return;
        for (AsyncTask task : tasks) {
            try {
                if(task.getStatus() != AsyncTask.Status.FINISHED)
                    task.cancel(true);
            }
            catch (Exception ignore){}
        }
        tasks.clear();
    }
}
