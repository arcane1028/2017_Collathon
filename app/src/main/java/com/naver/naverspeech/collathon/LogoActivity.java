package com.naver.naverspeech.collathon;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class LogoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.logo_main);

        final ImageView iv = (ImageView)findViewById(R.id.logo_down);

        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.alpha_ani);
        iv.startAnimation(animation);

        Handler mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(LogoActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        },3200);


    }
}
