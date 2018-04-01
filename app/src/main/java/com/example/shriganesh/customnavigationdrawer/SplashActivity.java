package com.example.shriganesh.customnavigationdrawer;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by shri ganesh on 22/05/2017.
 */

public class SplashActivity extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lay_splash);

        Animation animation= AnimationUtils.loadAnimation(SplashActivity.this,R.anim.anim_alpha);
        animation.reset();
        RelativeLayout relativeLayout= (RelativeLayout) findViewById(R.id.relative);
        relativeLayout.clearAnimation();
        relativeLayout.startAnimation(animation);


        animation=AnimationUtils.loadAnimation(SplashActivity.this,R.anim.anim_translate);
        animation.reset();
        TextView textView= (TextView) findViewById(R.id.textView);
        textView.clearAnimation();
        textView.startAnimation(animation);

        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        /*Thread thread = new Thread(){
            @Override
            public void run() {
                try {
                    sleep(3000);
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();*/
    }
}
