package com.benmohammad.reelz.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.benmohammad.reelz.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class InfoActivity extends AppCompatActivity {

    private AdView adView;
    private Animation fadeIn;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        TextView logo = findViewById(R.id.logoInfo);

        fadeIn = AnimationUtils.loadAnimation(this, R.anim.alpha_anim);

        logo.startAnimation(fadeIn);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        adView = findViewById(R.id.adViewInfo);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                finish();
                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
