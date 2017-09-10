package com.frozensparks.hellofriend.Tutorial;

import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.frozensparks.hellofriend.R;
import com.frozensparks.hellofriend.Tools.ResizeableImageView;

/**
 * Created by Emanuel Graf on 01.09.2017.
 */

public class FragmentTransformerTutorial implements ViewPager.PageTransformer {

    @Override
    public void transformPage(View view, float position) {

        ImageView arrow_tutorial =view.findViewById(R.id.arrow_tutorial);
        if(position!=0) {
            if (arrow_tutorial.getTag().equals("1"))
            arrow_tutorial.setRotation(position < 0 ? 180-position * -90 : 90 + 180-position * 90);
            arrow_tutorial.setTranslationX(position < 0 ? -position * view.getWidth() : -position * view.getWidth());
            if (arrow_tutorial.getTag().equals("2"))
                arrow_tutorial.setRotation(position < 0 ? 90 - position * 135 :  90-position * -90 );
            arrow_tutorial.setTranslationX(position < 0 ? -position * view.getWidth() : -position * view.getWidth());
            if (arrow_tutorial.getTag().equals("3"))
                arrow_tutorial.setRotation(position < 0 ? -position * 90 :  215- position * 115 );
            arrow_tutorial.setTranslationX(position < 0 ? -position * view.getWidth() : -position * view.getWidth());
        }

        TextView title_tutorial = view.findViewById(R.id.title_tutorial);
        title_tutorial.setTranslationX(position < 0 ? -position * view.getWidth() : -position * view.getWidth());
        title_tutorial.setAlpha(position < 0 ? 1f + position*5 : 1f - position*5);


        TextView subtitle_tutorial = view.findViewById(R.id.subtitle_tutorial);
        subtitle_tutorial.setTranslationX(position < 0 ? -position * view.getWidth() : -position * view.getWidth());
        subtitle_tutorial.setAlpha(position < 0 ? 1f + position*5 : 1f - position*5);



/*
        int MAX_ROTATION =90;

        ImageButton button2 = view.findViewById(R.id.liked_btn_suggestion);
        button2.setPivotY((int)(position < 0 ? 2000 : view.getWidth()));
        final float rotation2 = +position * MAX_ROTATION;
        button2.setPivotX(position < 0 ? 1 : view.getWidth());
        button2.setRotation(rotation2);


        ImageButton heart_shadow = view.findViewById(R.id.like_btn_suggestion);
        heart_shadow.setPivotY((int)(position < 0 ? 2000 : view.getWidth()));
        final float heart_shadow1= +position * MAX_ROTATION;
        heart_shadow.setPivotX(position < 0 ? 1 : view.getWidth());
        heart_shadow.setRotation(heart_shadow1);


        ImageButton flag = view.findViewById(R.id.flag_person);
        flag.setPivotY((int)(position < 0 ? 2000 : view.getWidth()));
        flag.setPivotX(position*2 < 0 ? 1 : view.getWidth()/2);
        flag.setRotation(heart_shadow1*2);


        ImageButton button3 = view.findViewById(R.id.add_btn_suggestion);
        button3.setPivotY(position < 0 ? -view.getWidth()*position*100 : view.getWidth()*position*10);
        final float rotation5 = +position *5;//* MAX_ROTATION;
        button3.setPivotX(view.getWidth()/2);
        //button3.setPivotX(position < 0 ? 0 : view.getWidth());
        // button3.setScaleX( position);
        button3.setRotation(rotation5);
        button3.setElevation(position < 0 ? 0 : 1);
        button3.setAlpha(position < 0 ? 1f + position : 1f - position);





        ResizeableImageView imageView1 = view.findViewById(R.id.daimajia_slider_image);
        imageView1.setPivotY(2500);
        final float rotation3 = +position * MAX_ROTATION;
        imageView1.setPivotX(view.getWidth()/2);
        imageView1.setRotation(rotation3);

        RelativeLayout imageView2 = view.findViewById(R.id.outerRL);
        imageView2.setPivotY(2000);
        final float rotation4 = +position * MAX_ROTATION;
        imageView2.setPivotX(view.getWidth()/2);
        imageView2.setRotation(rotation4);
        */


    }
}