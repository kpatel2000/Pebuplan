package com.example.pebuplan.activity;

import static java.security.AccessController.getContext;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.example.pebuplan.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        SharedPreferences sharedPref = this.getSharedPreferences("plan", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPref.edit();

        String value = sharedPref.getString("login", "default_value");

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(value.equals("yes")) {
                    Intent intent = new Intent(SplashActivity.this, PinConfirmActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    Intent intent = new Intent(SplashActivity.this, PinActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, 3000);
    }
}