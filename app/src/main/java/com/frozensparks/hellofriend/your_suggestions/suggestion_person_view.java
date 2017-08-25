package com.frozensparks.hellofriend.your_suggestions;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Space;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.frozensparks.hellofriend.R;

import java.io.File;

/**
 * Created by Emanuel Graf on 22.08.2017.
 */

public class suggestion_person_view  extends BaseSliderView {


    int allow=1;
    private static String mnUrl;
    private String mUrl;
    private File mFile;
    private int mRes;
    ImageView target;
    ImageView target_shadow;

    private ScaleType mScaleType = ScaleType.Fit;


    int check=0;
    public ImageButton button;
    public ImageButton button3;
    private ImageLoadListener mLoadListener;
    RelativeLayout load_more_check;

    protected OnSliderClickListener mOnSliderClickListener2;


    public suggestion_person_view(Context context) {
            super(context);
        }

        @Override
        public View getView() {
            View v = LayoutInflater.from(getContext()).inflate(R.layout.personslideview, null);
            target = (ImageView) v.findViewById(R.id.daimajia_slider_image);
            target_shadow = v.findViewById(R.id.imageView4);
            //TextView description = (TextView)v.findViewById(R.id.description);
            button = (ImageButton) v.findViewById(R.id.button2);
            button3 = (ImageButton) v.findViewById(R.id.button3);
            load_more_check = v.findViewById(R.id.load_more_check);

            bindEventAndShow(v, target, target_shadow,load_more_check);
            return v;
        }


    protected void bindEventAndShow(final View v, ImageView targetImageView, final ImageView target_shadow, final RelativeLayout load_more_check) {
        final BaseSliderView me = this;

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnSliderClickListener != null) {
                    mOnSliderClickListener.onSliderClick(me);
                }
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnSliderClickListener2 != null) {
                    mOnSliderClickListener2.onSliderClick(me);
                }
            }
        });


        if (targetImageView == null)
            return;

        //mUrl glide to target
            load_more_check.setVisibility(View.VISIBLE);



        Glide.with(v).load(getUrl()).into(targetImageView);
        Glide.with(v).load(getUrl()).into(target_shadow);

        target_shadow.setColorFilter(Color.WHITE);

        //targetImageView.setBackground( v.getResources().getDrawable(R.drawable.shadow1));
    }

    public static suggestion_person_view images(String url){

        mnUrl = url;
       // Glide.with(getView()).load(url).into(target);

        return null;}
    public void trigger(){

        check=1;
    }


    public BaseSliderView setOnSliderClickListener2(OnSliderClickListener l){
        mOnSliderClickListener2 = l;
        return this;
    }



}



