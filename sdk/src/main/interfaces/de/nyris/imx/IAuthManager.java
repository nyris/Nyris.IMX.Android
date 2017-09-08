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


/**
 * IAuthManager.java - An interface that contain definition AuthManager features
 *
 * @see IExecute
 * @author Sidali Mellouk
 * Created by nyris GmbH
 * Copyright © 2017 nyris GmbH. All rights reserved.
 */
interface IAuthManager extends IExecute {
    /**
     * Get Token
     * @return Token value of type AccessToken
     * @see AccessToken
     */
    AccessToken getToken();

    /**
     * Get Current Client Id
     * @return String value of the Client Id
     */
    String getClientId();

    /**
     * Get Client Secret
     * @return String value of Secret
     */
    String getClientSecret();

    /**
     * Get the Current Scope
     * @return Scope value
     * @see Scope
     */
    Scope getScope();
}

