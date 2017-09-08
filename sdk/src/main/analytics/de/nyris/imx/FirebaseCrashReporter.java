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
import android.support.compat.BuildConfig;

import com.google.firebase.FirebaseApp;
import com.google.firebase.crash.FirebaseCrash;

/**
 * FirebaseCrashReporter.java - A class that extend interface ICrashReporter. The goal if this is to
 * send exception to firebase.
 *
 * @see ICrashReporter
 * @author Sidali Mellouk
 * Created by nyris GmbH
 * Copyright © 2017 nyris GmbH. All rights reserved.
 */
class FirebaseCrashReporter implements ICrashReporter {

    /**
     * Constructor initializing FireBaseApp
     * @param context a variable of type Context
     */
    FirebaseCrashReporter(Context context){
        try {
            if(FirebaseApp.getApps(context).isEmpty())
                FirebaseApp.initializeApp(context);
        }
        catch (Exception ignored){}
    }

    @Override
    public void reportException(String message){
        if(!Helpers.getInstance().isOnline())
            return;
        if(!Nyris.getInstance().isCrashReport())
            return;
        try {
            log();
            FirebaseCrash.report(new Exception(message));
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void reportException(Exception exception) {
        if(!Helpers.getInstance().isOnline())
            return;
        if(!Nyris.getInstance().isCrashReport())
            return;
        try {
            log();
            FirebaseCrash.report(exception);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void reportException(RuntimeException exception) {
        if(!Helpers.getInstance().isOnline())
            return;
        if(!Nyris.getInstance().isCrashReport())
            return;
        try {
            log();
            FirebaseCrash.report(exception);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Log Client Id and SDK version
     */
    private void log(){
        if(!Helpers.getInstance().isOnline())
            return;
        FirebaseCrash.log("Client Id : "+ Nyris.getInstance().getClientId());
        FirebaseCrash.log("SDK Version: "+ BuildConfig.VERSION_NAME);
    }
}
