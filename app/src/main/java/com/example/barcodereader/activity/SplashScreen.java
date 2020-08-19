package com.example.barcodereader.activity;

import android.annotation.SuppressLint;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;

import com.example.barcodereader.R;
import com.example.barcodereader.databinding.ActivitySplashScreenBinding;


public class SplashScreen extends AppCompatActivity {

    ActivitySplashScreenBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       binding= DataBindingUtil.setContentView(this,R.layout.activity_splash_screen);


        new Thread(new Runnable() {
            public void run() {
                doWork();
                startApp();
            }
        }).start();

    }
    private void doWork() {
        for (int progress=0; progress<=100; progress+=10) {
            try {
                Thread.sleep(200);
                binding.progress.setProgress(progress);
            } catch (Exception e) {
                e.printStackTrace();
//                Timber.e(e.getMessage());
            }
        }
    }

    private void startApp() {
        startActivity(new Intent(SplashScreen.this, MainActivity.class));
        finish();
    }
}
