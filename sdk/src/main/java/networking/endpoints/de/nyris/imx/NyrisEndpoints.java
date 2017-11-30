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

import android.net.Uri;
/**
 * NyrisEndpoints.java - An abstract class that implements INyrisEndpoints
 *
 * @see INyrisEndpoints
 * @author Sidali Mellouk
 * Created by nyris GmbH
 * Copyright © 2017 nyris GmbH. All rights reserved.
 */

public abstract class NyrisEndpoints implements INyrisEndpoints{
    private String scheme;
    String apiServer;
    String version;
    public static boolean DEBUG;

    NyrisEndpoints()
    {
        scheme ="https";
    }

    public static INyrisEndpoints getInstance(){
        return new NyrisLiveEndpoints();
    }

    @Override
    public String getImageMatchingApi() {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme(scheme)
                .authority(apiServer)
                .appendPath("find")
                .appendPath(version);

        return builder.build().toString();
    }

    @Override
    public String getImageMatchingApi(double lat, double lon, double acc) {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme(scheme)
                .authority(apiServer)
                .appendPath("find")
                .appendPath(version);
        if(lat!= -1 && lon != -1 && acc !=1) {
            builder.appendQueryParameter("lat", String.valueOf(lat))
                    .appendQueryParameter("lon", String.valueOf(lon))
                    .appendQueryParameter("acc", String.valueOf(acc));
        }
        return builder.build().toString();
    }

    @Override
    public String getObjectProposalApi() {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme(scheme)
                .authority(apiServer)
                .appendPath("find")
                .appendPath(version)
                .appendPath("regions");
        return builder.build().toString();
    }
}
