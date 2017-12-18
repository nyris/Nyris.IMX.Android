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

/**
 * INyris.java - An interface that contain different calls of the SDK
 *
 * @author Sidali Mellouk
 * Created by nyris GmbH
 * Copyright © 2017 nyris GmbH. All rights reserved.
 */
public interface INyris {
    /**
     * Init SDK
     * @param context A variable of type Context
     * @param clientId A variable of type String
     */
    INyris init(Context context, String clientId);

    /**
     * Get Nyris endpoints
     * @see INyrisEndpoints
     * @return Instance of INyrisEndpoints
     */
    INyrisEndpoints getNyrisEndpoits();

    /**
     * Get Current Client Id
     * @return A String value of the Client Id
     */
    String getClientId();

    /**
     * Set clientId with clientId and secret
     * @param clientId A variable of type String
     */
    INyris setClientId(String clientId);

    /**
     * Set output format for image matching response
     * @param outputFormat A variable of type String
     */
    INyris setOutputformat(String outputFormat);

    /**
     * Match an image
     * @param image A variable of type of array of bytes
     * @param matchCallback A variable of type IMatchCallback
     * @see IMatchCallback
     */
    void match(byte[] image, IMatchCallback matchCallback);

    /**
     * Match an image
     * @param image A variable of type of array of bytes
     * @param isOnlySimilarOffers A variable of type Boolean
     * @param matchCallback A variable of type IMatchCallback
     * @see IMatchCallback
     */
    void match(byte[] image, boolean isOnlySimilarOffers, IMatchCallback matchCallback);

    /**
     * Extract object from picture
     * @param image A variable of type of array of bytes
     * @param callback A variable of type IObjectExtractCallback
     * @see IObjectExtractCallback
     */
    void extractObjects(byte[] image, IObjectExtractCallback callback);

    /**
     * Clear pending or running Tasks
     */
    void clearAllTasks();
}
