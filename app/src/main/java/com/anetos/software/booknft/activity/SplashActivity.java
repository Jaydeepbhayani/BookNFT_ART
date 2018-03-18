package com.anetos.software.booknft.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.anetos.software.booknft.R;
import com.anetos.software.booknft.support.ui.BaseActivity;

public class SplashActivity extends BaseActivity {

    private static final int SPLASH_TIME_OUT = 1500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
        return R.layout.activity_spalsh;
    }
}
