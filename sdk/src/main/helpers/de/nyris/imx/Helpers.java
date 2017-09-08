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
import android.content.SharedPreferences;
import android.net.ConnectivityManager;

/**
 * Helpers.java - A class that contain different helpers
 *
 * @author Sidali Mellouk
 * @see Helper
 * Created by nyris GmbH
 * Copyright © 2017 nyris GmbH. All rights reserved.
 */
public class Helpers extends Helper{
    private static Helpers instance;
    private Context context;
    private ICrashReporter reporter;
    /**
     * A private constructor
     */
    private Helpers(){}

    /**
     * Retrieve the value of the instance
     * @return Helpers instance
     */
    public static Helpers getInstance(){
        if(instance == null){
            instance = new Helpers();
        }
        return instance;
    }

    /**
     * Init Minimum helpers like HttpRequest and HelperLocation
     * @param context A variable of type Context
     * @param reporter A variable of type ICrashReporter
     * @see ICrashReporter
     */
    public void init(Context context, ICrashReporter reporter) {
        this.context = context;
        this.reporter = reporter;
        HttpRequestHelper.getInstance().init(context);
    }

    /**
     * Check if Helpers are initiated
     */
    private void checkIfHelpersIsinitiated(){
        if(context == null)
            throw new RuntimeException("No context set, you need to initialize nyris SDK");
    }

    /**
     * Save value to Shared Preferences
     * @param key A variable of type String
     * @param value A variable of type String
     */
    public void saveParam(String key, String value){
        checkIfHelpersIsinitiated();
        SharedPreferences mPrefs = context.getSharedPreferences(context.getApplicationContext().getPackageName(),
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mPrefs.edit();
        editor.putString(key, value);
        editor.apply();
    }

    /**
     * Get Saved value from Shared Preferences
     * @param key A variable of type String
     * @return String value of key
     */
    public String getParam(String key){
        checkIfHelpersIsinitiated();
        SharedPreferences mPrefs = context.getSharedPreferences(context.getApplicationContext().getPackageName(),
                Context.MODE_PRIVATE);
        return mPrefs.getString(key, "");
    }

    /**
     * Check if there internet
     * @return Boolean value true for connected false for disconnected
     */
    public boolean isOnline() {
        try{
            checkIfHelpersIsinitiated();
            ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if(cm.getActiveNetworkInfo()== null)
                return false;
            return cm.getActiveNetworkInfo().isConnectedOrConnecting();
        }
        catch (Exception e){
            if(reporter!= null)
                reporter.reportException(e);
            e.printStackTrace();
            return false;
        }
    }


    /**
     * Get Text by id
     * @param id A variable of type Int
     * @return String value
     */
    public String getText(int id){
        return context.getText(id).toString();
    }
}
