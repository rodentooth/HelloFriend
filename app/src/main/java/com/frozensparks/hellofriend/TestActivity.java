package com.frozensparks.hellofriend;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.graphics.ImageFormat;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.BounceInterpolator;
import android.widget.Button;
import android.widget.ImageView;


public class TestActivity extends AppCompatActivity {

    int currentSelected =0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        //final Button imageView = (Button) findViewById(R.id.imageView);

        //final Button button_ok = (Button) findViewById(R.id.button_ok);

        final ImageView button = (ImageView) findViewById(R.id.button);


        button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                show(button, motionEvent);
                return false;
            }
        });
    /*    button_ok.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                show(button, null,button_ok, motionEvent);
                return false;
            }
        });


*/


    }

    // To reveal a previously invisible view using this effect:
    private void show(final View view, final MotionEvent me) {

       // final Button imageView = (Button) findViewById(R.id.imageView);
/*
        if (circle==null){


            // get the final radius for the clipping circle
            int finalRadius = Math.max(view.getWidth(), view.getHeight());

            // create the animator for this view (the start radius is zero)
            Animator anim = ViewAnimationUtils.createCircularReveal(view, (int) me.getX(), (int) me.getY(),
                    0, finalRadius);
            anim.setDuration(100);
            anim.setInterpolator(new FastOutSlowInInterpolator());

            // make the view invisible when the animation is done
            anim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    imageView.setVisibility(View.INVISIBLE);
                    tobegone.setVisibility(View.INVISIBLE);
                }
            });

            // make the view visible and start the animation
            view.setVisibility(View.VISIBLE);
            anim.start();

            return;
        }
/*

        // get the final radius for the clipping circle
        int finalRadiusCircle = Math.max(view.getWidth(), view.getHeight());

        // create the animator for this view (the start radius is zero)
        Animator animcircle = ViewAnimationUtils.createCircularReveal(circle, (int) me.getX(), (int) me.getY(),
                0, finalRadiusCircle);
        animcircle.setDuration(500);
        animcircle.setInterpolator(new FastOutSlowInInterpolator());

        // make the view visible and start the animation
        circle.setVisibility(View.VISIBLE);
        animcircle.start();


*/
        // get the final radius for the clipping circle
        int finalRadius = Math.max(view.getWidth(), view.getHeight());

        // create the animator for this view (the start radius is zero)
        Animator anim = ViewAnimationUtils.createCircularReveal(view, (int) me.getX(), (int) me.getY(),
                0, finalRadius);
        anim.setDuration(1000);
        anim.setInterpolator(new FastOutSlowInInterpolator());

        if (currentSelected == 0) {
            view.setBackgroundResource(R.mipmap.add_button_v2);
            currentSelected = 1;
        } else {
            view.setBackgroundResource(R.mipmap.ok_button);
            currentSelected = 0;

        }
        // make the view visible and start the animation
        view.setVisibility(View.VISIBLE);
        anim.start();



        Handler handler = new Handler();

        final Runnable r = new Runnable() {
            public void run() {


            }
        };

        handler.postDelayed(r, 200);


    }



}
