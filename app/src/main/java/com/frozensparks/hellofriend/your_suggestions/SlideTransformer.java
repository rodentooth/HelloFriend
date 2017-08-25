package com.frozensparks.hellofriend.your_suggestions;

import android.media.Image;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.daimajia.slider.library.Transformers.BaseTransformer;
import com.frozensparks.hellofriend.R;
import com.nineoldandroids.view.ViewHelper;

import static java.lang.Math.exp;

/**
 * Created by Emanuel Graf on 22.08.2017.
 */

public class SlideTransformer extends BaseTransformer {

    private static final float MAX_ROTATION = 90;



    @Override
    protected void onTransform(View view, float position) {
       // ViewHelper.setPivotX(view, position < 0 ? 0 : view.getWidth());
        //ViewHelper.setScaleX(view, position < 0 ? 1f + position : 1f - position);

/*
        view.setPivotY(2500);

        final float rotation = +position * MAX_ROTATION;

        view.setPivotX(500);
        view.setRotation(rotation);
*/
       // view.findViewById(R.id.slider);

        ImageButton button2 = view.findViewById(R.id.button3);
        button2.setPivotY((int)(position < 0 ? 2000 : view.getWidth()));
        final float rotation2 = +position * MAX_ROTATION;
        button2.setPivotX(position < 0 ? 1 : view.getWidth());
        button2.setRotation(rotation2);

        RelativeLayout heart_shadow = view.findViewById(R.id.heart_shadow);
        heart_shadow.setPivotY((int)(position < 0 ? 2000 : view.getWidth()));
        final float heart_shadow1= +position * MAX_ROTATION;
        heart_shadow.setPivotX(position < 0 ? 1 : view.getWidth());
        heart_shadow.setRotation(heart_shadow1);

        ImageButton button3 = view.findViewById(R.id.button2);
        button3.setPivotY(position < 0 ? -view.getWidth()*position*100 : view.getWidth()*position*10);
        final float rotation5 = +position *5;//* MAX_ROTATION;
        button3.setPivotX(view.getWidth()/2);
        //button3.setPivotX(position < 0 ? 0 : view.getWidth());
       // button3.setScaleX( position);
        button3.setRotation(rotation5);
        button3.setElevation(position < 0 ? 0 : 1);
        button3.setAlpha(position < 0 ? 1f + position : 1f - position);





        RelativeLayout imageView1 = view.findViewById(R.id.innerRL);
        imageView1.setPivotY(2500);
        final float rotation3 = +position * MAX_ROTATION;
        imageView1.setPivotX(view.getWidth()/2);
        imageView1.setRotation(rotation3);

        RelativeLayout imageView2 = view.findViewById(R.id.outerRL);
        imageView2.setPivotY(2000);
        final float rotation4 = +position * MAX_ROTATION;
        imageView2.setPivotX(view.getWidth()/2);
        imageView2.setRotation(rotation4);

        }
    }

