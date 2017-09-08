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
 * ICrashReporter.java - An interface that contain the definition of any Crash Reporter
 *
 * @author Sidali Mellouk
 * Created by nyris GmbH
 * Copyright © 2017 nyris GmbH. All rights reserved.
 */
interface ICrashReporter {

    /**
     * Report exception message
     * @param message a variable of type string
     */
    void reportException(String message);

    /**
     * Report exception
     * @param exception a variable of type Exception
     */
    void reportException(Exception exception);

    /**
     * Report runtime exception
     * @param exception a variable of type RuntimeException
     */
    void reportException(RuntimeException exception);
}
