Nyris Image Matching SDK for Android
=======
![](nyris_logo.png)

Nyris Image Matching SDK for Android allows to the usage of Image Matching service that provide a list
of offers from a given image.

Supports >=4.1 Android devices
For more information please see [nyris.io](https://nyris.io]/)

## Releases
Current release is 1.7.0

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
* [Init SDK First](#init-sdk-first)
* [Integrate nyris Camera](#integrate-nyris-camera)
* [Match Taken Pictures](#match-taken-pictures)
* [Extract Objects from Pictures](#extract-objects-from-pictures)
* [Clear running or pending tasks](#clear-running-or-pending-tasks)

### Init SDK First:
Init your SDK before to start anything else 
```java
public class YouApp_Or_Your_First_Activity{    
    @Override
    public void onCreate() {
        super.onCreate();

        //Init the SDK
        Nyris.getInstance()
                .init(this, BuildConfig.CLIENT_ID)
                //Set preferred offer language
                /*.setLanguage("de")*/
                //You can add your own output format
                /*.setOutputformat("YOUR_OUTPUT_FORMAT")*/;
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
    compile "de.nyris:camera:0.55.3"
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
    ...
}
```

### Match Taken Pictures

Demo sample to match taken pictures, before you send a byte array to the SDK you need to insure that the minimum size of the picture is 512x512 and maximum size is low than 0.5mb and the media type of the images must be *.JPEG/*.JPG.

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
                    @Deprecated
                    public void onMatched(List<OfferInfo> offerInfos) {
                        //onMatched List offers object
                    }
                    
                    @Override
                    public void onMatched(List<Offer> offerInfos) {
                        //onMatched List offers object
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

### Extract Objects from Pictures
Demo sample to extract object from pictures, before you send a byte array to the SDK you need to insure that the minimum size of the picture is 512x512 and maximum size is low than 0.5mb and the media type of the images must be *.JPEG/*.JPG.

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
                .extractObjects(data, new IObjectExtractCallback() {
                      @Override
                      public void onObjectExtracted(List<ObjectProposal> objectProposals) {
                          //Extracted objects 
                      }
    
                      @Override
                      public void onObjectExtracted(String json) {
    
                      }
    
                      @Override
                      public void onError(ResponseError error) {
                          //onError
                      }
                  });
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
            .getInstance()
            .clearAllTasks();
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
