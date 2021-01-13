package com.example.questionnaire;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.example.questionnaire.classes.broadCastingClass;

public class SplashScreenActivity extends AppCompatActivity {

    broadCastingClass broadcastingClass = new broadCastingClass();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Handler handler = new Handler();
        if (broadcastingClass.onReceive(this)) {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
                }
            }, 3000);
        } else {
            Toast.makeText(this, "Not connected to internet", Toast.LENGTH_SHORT).show();
        }

    }

}