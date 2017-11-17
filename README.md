Nyris Image Matching SDK for Android
=======
![](nyris_logo.png)

Nyris Image Matching SDK for Android allows to the usage of Image Matching service that provide a list
of offers from a given image.

Supports >=4.1 Android devices
For more information please see [nyris.io](https://nyris.io]/)

## Releases
Current release is 1.2.2

## Download
Download via Gradle:

```groovy
repositories {
    maven {
        url "https://www.myget.org/F/nyris/maven/"
    }
}

dependencies {
    ...
    compile 'de.nyris:imx:x.x.x'
}
```

## Get Started
#### Jump to Section
* [Init SDK First](#init-the-sdk-first)
* [Set app key and IAuthCallback](#set-app-key-and-iauthcallback)
* [Integrate nyris Camera](#integrate-nyris-camera)
* [Match Taken Pictures](#match-taken-pictures)
* [Clear running or pending tasks](#clear-running-or-pending-tasks)

### Init SDK First:
Init yoru SDK before to start anything else 
```java
public class YouApp_Or_Your_First_Activity{    
    @Override
    public void onCreate() {
        super.onCreate();

        //Init the SDK
        Nyris.getInstance().init(this);
    }
}
```

### Set app key and IAuthCallback:
Start nyris imx SDK by getting access to our APIs
```java
public class SplashScreenActivity extends AppCompatActivity implements IAuthCallback {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        ...
        //Set
        Nyris
            .getInstance()
            //Enabling crash report will keep us updated in case you face issues with our SDK
            .enableCrashReport(true)
            .login(YOUR_CLIENT_ID, 
                YOUR_SECRET, 
                SplashScreenActivity.this);
    }

    @Override
    public void onSuccess(AccessToken accessToken) {
        //When it's success login, AccessToken is managed by nyris SDK you don't need to save it
        Intent intent = new Intent(SplashScreen.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onError(ResponseError responseError) {
        //When it's error
    }
}
```

### Integrate nyris Camera

With nyris Custom Camera SDK you can integrate camera just in few code lines
```groovy
repositories {
    maven {
        url "https://www.myget.org/F/nyris/maven/"
    }
}

dependencies {
    ...
    compile "de.nyris:camera:0.46.1"
}
```

```xml
<FrameLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    xmlns:app="http://schemas.android.com/apk/res-auto">
    <de.nyris.camera.CameraView
        android:id="@+id/camera"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:adjustViewBounds="true"
        app:imageSize="512x512"  //size of the image
        app:imageQuality="60"    //quality of the image
        app:autoFocus="true"     //auto focus
        app:recognition="none"/> //type of recognition
</FrameLayout>
```

```java
public class MainActivity extends Activity implements Callback {
    private CameraView camera;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        ...
         camera = (CameraView) findViewById(R.id.camera);
                                camera.addCallback(this);
                                camera.start();
        ...
    }

    //when app resuemd
    @Override
    protected void onResume() {
        super.onResume();
        camera.start();
    }

    //when lock screen or App paused
    @Override
    protected void onPause() {
        super.onPause();
        camera.stop();
    }

    //Android >=6.0 permissions
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == ICamera.REQUEST_CAMERA_PERMISSION) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //On Permission OK
            }
            else
            {
                //Permission Deny
            }
        }
    }

   @Override
   public void onCameraOpened(CameraView cameraView) {
        //On Camera Opened
   }

   @Override
   public void onCameraClosed(CameraView cameraView) {
        //On Camera Closed
   }

   @Override
   public void onPictureTaken(CameraView cameraView, byte[] data) {
       //On Picture Taken
   } 
   
   @Override
   public boolean isWithExif() {
        // Put this to true if you want to get byte array with exif information
       return false;
   }  

   @Override
   public void onError(CameraView cameraView, String errorMessage) {
       //On Camera Error
   }
    ...
}
```

### Match Taken Pictures

Demo sample to match taken pcitures
```java
public class MainActivity extends Activity implements Callback {
    public void onCreate(Bundle savedInstanceState) {
        ...
        View btnTakePicture = findViewById(R.id.btnTakePicture);
        btnTakePicture.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                camera.takePicture();
            }
        });
    }

    //On taken picture from nyris Camera
    @Override
    public void onTakenPicture(byte[] image) {
        byte[] image = getIntent().getExtras().getByteArray("image");
        Nyris.getInstance()
                .match(data, new IMatchCallback() {
                    @Override
                    public void onMatched(List<OfferInfo> offerInfos) {
                        //onMatched List offers object
                    }

                    @Override
                    public void onMatched(JSONObject jsonObject) {
                        //onMatched JsonObject
                    }

                    @Override
                    public void onMatched(String json) {
                        //onMatched String Json
                    }

                    @Override
                    public void onError(ResponseError error) {
                        //On Error
                    }
                });
        // Or searching only similar offers
        Nyris.getInstance()
                        .match(data, true, new IMatchCallback(){...});
    }
    ...
}
```

### Clear running or pending tasks
To clear pending or running background tasks
```java
public class MainActivity extends Activity{
    ...
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Nyris
            .getInstance().clearAllTasks();
    }
}
```

License
=======

    Copyright 2017 nyris GmbH

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.