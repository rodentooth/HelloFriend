package com.frozensparks.hellofriend.Tools;

/**
 * Created by Emanuel Graf on 30.08.2017.
 */

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

public class ResizeableImageView extends android.support.v7.widget.AppCompatImageView {

    public ResizeableImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
        Drawable d = getDrawable();

        if(d!=null){
            // ceil not round - avoid thin vertical gaps along the left/right edges
            int width = MeasureSpec.getSize(widthMeasureSpec);
            int height = (int) Math.ceil((float) width * (float) d.getIntrinsicHeight() / (float) d.getIntrinsicWidth());
            /*if((height)>(width*1.5)){
                setMeasuredDimension((int)(width/1.4), (int)(height/1));

            }
            else if((height)>(width*1.4)){
                setMeasuredDimension((int)(width/1.3), (int)(height/1));

            }
            else if((height)>(width*1.3)){
                setMeasuredDimension((int)(width/1.2), (int)(height/1));

            }
            else if((height)>(width*1.2)){
                setMeasuredDimension((int)(width/1.1), (int)(height/1));

            }

            else */{
                setMeasuredDimension(width, height);
            }
        }else{
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

}