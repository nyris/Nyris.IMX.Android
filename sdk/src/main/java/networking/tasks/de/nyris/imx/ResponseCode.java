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
 * ResponseCode.java - A class that that contain defined HTTP_ERROR_CODE
 *
 * @author Sidali Mellouk
 * Created by nyris GmbH
 * Copyright © 2017 nyris GmbH. All rights reserved.
 */
class ResponseCode {
    static String HTTP_INTERNAL_ERROR = "HTTP_INTERNAL_ERROR";
    static String HTTP_BAD_REQUEST_OR_UNAUTHORIZED = "HTTP_BAD_REQUEST_OR_UNAUTHORIZED";
    static String HTTP_UNKNOWN_ERROR = "HTTP_UNKNOWN_ERROR";
    static String UNKNOWN_ERROR = "UNKNOWN_ERROR";
    static String HTTP_NOT_FOUND = "HTTP_NOT_FOUND";
    static String NO_INTERNET = "NO_INTERNET";
    static String IMAGE_NOT_FOUND_ERROR = "IMAGE_NOT_FOUND_ERROR";
    static String OBJECT_NOT_FOUND_ERROR = "OBJECT_NOT_FOUND_ERROR";
    static String HTTP_TIME_OUT = "HTTP_TIME_OUT";
}
