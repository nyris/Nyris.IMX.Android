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

package de.nyris.demo;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

class HelperDialog{

    static void messageBoxDialog(Context context, String title, String text, DialogInterface.OnClickListener listener){
        AlertDialog.Builder messageBox = new AlertDialog.Builder(context);
        messageBox.setMessage(text);
        messageBox.setIcon(R.mipmap.ic_launcher);
        messageBox.setTitle(title);
        messageBox.setPositiveButton("OK", listener);
        messageBox.setOnCancelListener(null);
        messageBox.create().show();
    }
}
