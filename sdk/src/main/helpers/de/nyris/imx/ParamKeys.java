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
 * ParamKeys.java - A class that have static keys used for prams saving or http requests
 *
 * @author Sidali Mellouk
 * Created by nyris GmbH
 * Copyright © 2017 nyris GmbH. All rights reserved.
 */
class ParamKeys{
    static String grantType = "grant_type";
    static String refreshToken = "refresh_token";
    static String scope = "scope";
    static String clientId = "client_id";
    static String clientSecret = "client_secret";
    static String accessToken = "accessToken";
    static String clientCredentials = "client_credentials";

    static String live = "live_";
    static String openId = "open_id_server";
    static String api = "api_server";
}
