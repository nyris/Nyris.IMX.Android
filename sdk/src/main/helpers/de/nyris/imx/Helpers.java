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
 * Created by nyris GmbH
 * Copyright © 2017 nyris GmbH. All rights reserved.
 */
public class Helpers{
    /**
     * Save value to Shared Preferences
     * @param key A variable of type String
     * @param value A variable of type String
     */
    static void saveParam(Context context, String key, String value){
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
    static String getParam(Context context, String key){
        SharedPreferences mPrefs = context.getSharedPreferences(context.getApplicationContext().getPackageName(),
                Context.MODE_PRIVATE);
        return mPrefs.getString(key, "");
    }

    /**
     * Check if there internet
     * @return Boolean value true for connected false for disconnected
     */
    public static boolean isOnline(Context context) {
        try{
            ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if(cm.getActiveNetworkInfo()== null)
                return false;
            return cm.getActiveNetworkInfo().isConnectedOrConnecting();
        }
        catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
