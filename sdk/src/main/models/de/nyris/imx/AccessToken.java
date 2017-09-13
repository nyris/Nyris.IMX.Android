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
import com.google.gson.annotations.SerializedName;

import java.util.Calendar;
import java.util.Date;

/**
 * AccessToken.java - A class model that contain token access
 *
 * @author Sidali Mellouk
 * Created by nyris GmbH
 * Copyright © 2017 nyris GmbH. All rights reserved.
 */
public class AccessToken{
    @SerializedName("token_type")
    String tokenType;
    @SerializedName("access_token")
    String accessToken;
    @SerializedName("expires_in")
    long expiresIn;
    @SerializedName("refresh_token")
    String refreshToken;
    @SerializedName("creation_date")
    Date creationDate;
    /**
     * Constructor
     */
    public AccessToken() {
    }

    /**
     * Constructor
     * @param tokenType A variable of type String
     * @param accessToken tokenType A variable of type String
     * @param expiresIn tokenType A variable of type String
     * @param refreshToken tokenType A variable of type String
     */
    public AccessToken(String tokenType, String accessToken, long expiresIn, String refreshToken) {
        this.tokenType = tokenType;
        this.accessToken = accessToken;
        this.expiresIn = expiresIn;
        this.refreshToken = refreshToken;
    }

    /**
     * Get token type
     * @return token type string value
     */
    public String getTokenType() {
        return tokenType;
    }

    /**
     * Get access token
     * @return access token string value
     */
    public String getAccessToken() {
        return accessToken;
    }

    /**
     * Get Expiration token time in seconds.
     * @return token type double value
     */
    public double getExpiresIn() {
        return expiresIn;
    }

    /**
     * Get refresh token
     * @return refresh token string value
     */
    public String getRefreshToken() {
        return refreshToken;
    }

    /**
     * Set Token Creation Date
     */
    void setCreationDate() {
        Calendar cal = Calendar.getInstance();
        this.creationDate = cal.getTime();
    }

    /**
     * Check if token is expired
     * @return Boolean value, true == is expired, false not expired
     */
    public boolean isExpired(){
        long expirationTimeStamp = creationDate.getTime()+expiresIn;
        long currentTimeStamp = Calendar.getInstance().getTime().getTime();
        return (currentTimeStamp - expirationTimeStamp) >= 0;
    }
}
