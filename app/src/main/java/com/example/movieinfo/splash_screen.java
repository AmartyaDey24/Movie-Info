package com.example.movieinfo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

public class splash_screen extends AppCompatActivity {

    LottieAnimationView lottieAnimationView;
    TextView ani_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(splash_screen.this,MainActivity.class));
                finish();
            }
        },3200);

        lottieAnimationView = findViewById(R.id.animation);
        ani_text = findViewById(R.id.ani_text);

        lottieAnimationView.animate().translationX(3200).setDuration(1000).setStartDelay(4000);
    }
}