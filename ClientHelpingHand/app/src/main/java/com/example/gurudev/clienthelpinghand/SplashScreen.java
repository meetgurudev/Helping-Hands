package com.example.gurudev.clienthelpinghand;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashScreen extends AppCompatActivity {

    // Global
    private static int SPLASH_TIME_OUT = 5000;
    @Override
    
    // on create method
    protected void onCreate(Bundle savedInstanceState) {
        
        super.onCreate(savedInstanceState);

        // Set the content on view
        setContentView(R.layout.activity_splash_screen);

        // Create handler
        new Handler().postDelayed(new Runnable() {
            @Override
            
            // Set the intent
            public void run() {
                Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}
