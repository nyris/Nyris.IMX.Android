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

/**
 * ResponseError.java - A class model that show error description
 *
 * @author Sidali Mellouk
 * Created by nyris GmbH
 * Copyright © 2017 nyris GmbH. All rights reserved.
 */
public class ResponseError {
    @SerializedName("error")
    private String errorCode;
    @SerializedName("error_description")
    private String errorDescription;

    /**
     * Constructor
     */
    public ResponseError(){}

    /**
     * Constructor
     * @param errorCode a variable of type String
     * @param errorDescription a variable of type String
     */
    public ResponseError(String errorCode, String errorDescription){
        this.errorCode = errorCode;
        this.errorDescription = errorDescription;
    }

    /**
     * Get Error Code
     * @return Error Code of type String
     */
    public String getErrorCode() {
        return errorCode;
    }

    /**
     * Set Error Code
     * @param errorCode a variable of type String
     */
    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    /**
     * Get Error Description
     * @return Description of type String
     */
    public String getErrorDescription() {
        return errorDescription;
    }

    /**
     * Set Error Description
     * @param errorDescription a variable of type String
     */
    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }
}
