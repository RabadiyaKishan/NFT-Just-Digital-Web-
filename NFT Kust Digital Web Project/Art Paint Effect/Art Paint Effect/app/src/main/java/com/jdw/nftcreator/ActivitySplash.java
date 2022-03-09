package com.jdw.nftcreator;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class ActivitySplash extends AppCompatActivity {

    private final int SPLASH_DISPLAY_LENGTH = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent mainIntent = new Intent(ActivitySplash.this, MainActivity.class);
                ActivitySplash.this.startActivity(mainIntent);
                ActivitySplash.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}