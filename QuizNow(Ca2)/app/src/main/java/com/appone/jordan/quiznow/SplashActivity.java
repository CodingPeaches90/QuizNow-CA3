package com.appone.jordan.quiznow;

import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toolbar;

import com.appone.jordan.quiznow.R;

public class SplashActivity extends AppCompatActivity
{
    private ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        /* Assign our Image variable to the Object "Logo" */
        image = findViewById(R.id.logo);
        /* Animate the screen using the XML we defined */
        Animation a = AnimationUtils.loadAnimation(this, R.anim.transition);
        image.startAnimation(a);
        final Intent i = new Intent(this, HomeActivity.class);

        /* Thread */
        Thread thread = new Thread(){
            @Override
            public void run() {
                try{
                    sleep(5000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }finally {
                    startActivity(i);
                    finish();
                }
            }
        };
        thread.start();
    }
}
