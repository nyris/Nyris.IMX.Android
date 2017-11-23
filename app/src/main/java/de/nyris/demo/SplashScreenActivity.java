package de.nyris.demo;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import de.nyris.imx.AccessToken;
import de.nyris.imx.Helpers;
import de.nyris.imx.IAuthCallback;
import de.nyris.imx.Nyris;
import de.nyris.imx.ResponseError;

/**
 * Created by sidali on 06.09.17.
 */

public class SplashScreenActivity extends AppCompatActivity implements IAuthCallback {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        if(!Helpers.isOnline(this)){
            HelperDialog.messageBoxDialog(
                this,
                "No networks",
                "No networks available, please check your network connection.",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                });
            return;
        }

        // Login using client id and secret
        Nyris
            .getInstance()
            .login(BuildConfig.CLIENT_ID,
                    BuildConfig.SECRET,
                    SplashScreenActivity.this);
    }

    @Override
    public void onSuccess(AccessToken accessToken) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        },500);
    }

    @Override
    public void onError(ResponseError responseError) {
        HelperDialog.messageBoxDialog(
            this,
            "Auth issues",
            responseError.getErrorDescription(),
            new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish();
                }
            });
    }
}
