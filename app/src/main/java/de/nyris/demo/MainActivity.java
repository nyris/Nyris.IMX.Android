package de.nyris.demo;
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

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import org.json.JSONObject;

import java.util.List;

import de.nyris.camera.Callback;
import de.nyris.camera.CameraView;
import de.nyris.imx.IMatchCallback;
import de.nyris.imx.Nyris;
import de.nyris.imx.OfferInfo;
import de.nyris.imx.ResponseError;

public class MainActivity extends AppCompatActivity implements Callback {
    CameraView camera;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);
        camera = (CameraView) findViewById(R.id.camera);
        camera.addCallback(MainActivity.this);
    }

    public void onTakePicture(View v)
    {
        camera.takePicture();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(camera == null)
            return;
        camera.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(camera == null)
            return;
        camera.stop();
    }

    //Marshmallow permissions
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            //TODO : On Permission OK
        }
        else
        {
            //TODO : On Permission Deny
        }
    }

    @Override
    public void onCameraOpened(CameraView cameraView) {

    }

    @Override
    public void onCameraClosed(CameraView cameraView) {

    }

    @Override
    public boolean isWithExif() {
        return false;
    }

    @Override
    public void onPictureTaken(CameraView cameraView, byte[] data) {
        //Match taken picture
        Nyris.getInstance()
                .match(data, new IMatchCallback() {
                    @Override
                    public void onMatched(List<OfferInfo> offerInfos) {
                        //Render results
                        HelperDialog.messageBoxDialog(MainActivity.this, "Success", "Count offers : "+offerInfos.size() ,null);
                    }

                    @Override
                    public void onMatched(JSONObject jsonObject) {
                    }

                    @Override
                    public void onMatched(String json) {
                    }

                    @Override
                    public void onError(ResponseError error) {
                        HelperDialog.messageBoxDialog(MainActivity.this, "Error", error.getErrorDescription(),null);
                    }
                });
    }

    @Override
    public void onError(CameraView cameraView, String errorMessage) {
        HelperDialog.messageBoxDialog(MainActivity.this, "Camera error", errorMessage,null);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Clear pending or running tasks
        Nyris.getInstance().clearAllTasks();
    }
}
