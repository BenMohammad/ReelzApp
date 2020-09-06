package com.benmohammad.reelz.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.benmohammad.reelz.R;



public class SplashActivity extends AppCompatActivity {

    private Animation fadeIn;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        TextView logo = findViewById(R.id.logo);
        fadeIn = AnimationUtils.loadAnimation(this, R.anim.alpha_anim);

        logo.startAnimation(fadeIn);

        Thread timerThread = new Thread(){
            public void run(){
                try{
                    sleep (2300);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }finally{
                    startActivity(new Intent(SplashActivity.this,MainActivity.class));
                    overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                }
            }
        };
        timerThread.start();




    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
