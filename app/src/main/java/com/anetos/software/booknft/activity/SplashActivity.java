package com.anetos.software.booknft.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.anetos.software.booknft.R;
import com.anetos.software.booknft.support.ui.BaseActivity;

import butterknife.BindView;

public class SplashActivity extends BaseActivity {

    private static final int SPLASH_TIME_OUT = 1500;
    @BindView(R.id.tv_version)
    TextView tvVersion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tvVersion.setText(getVersionName());
        new Handler().postDelayed(new Runnable() {

			/*
             * Showing splash screen with a timer. This will be useful when you
			 * want to show case your app logo / company
			 */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                startActivity(new Intent(SplashActivity.this, NFTBookActivity.class));
                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);
    }

    @Override
    protected boolean shouldInflateToolbar() {
        return false;
    }

    @Override
    protected int provideLayoutResourceId() {
        return R.layout.activity_splash;
    }

    private String getVersionName() {
        /*try {
            PackageInfo pInfo = this.getPackageManager().getPackageInfo(getPackageName(), 0);
            return pInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }*/
        try {
            return getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return getResources().getString(R.string.version);
    }
}
