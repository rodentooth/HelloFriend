package com.frozensparks.hellofriend.your_suggestions;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.frozensparks.hellofriend.R;

import java.util.List;

/**
 * Created by Emanuel Graf on 24.08.2017.
 */

public class RecycleViewAdapter_your_suggestions  extends RecyclerView.Adapter<RecycleViewAdapter_your_suggestions.CustomViewHolder> {
    private List<FeedItem> feedItemList;
    private Context mContext;
    private int check = 0;
    private int allow = 1;


    public RecycleViewAdapter_your_suggestions(Context context, List<FeedItem> feedItemList) {
        this.feedItemList = feedItemList;
        this.mContext = context;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.personslideview, null);
        view.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.MATCH_PARENT));

        CustomViewHolder viewHolder = new CustomViewHolder(view);


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final CustomViewHolder customViewHolder, final int i) {
        final FeedItem feedItem = feedItemList.get(i);


        customViewHolder.Add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, feedItem.getSC_username(), Toast.LENGTH_SHORT).show();
            }
        });


        Glide.with(mContext).load(feedItem.getPicture()).into(customViewHolder.target);
        Glide.with(mContext).load(feedItem.getPicture()).into(customViewHolder.target_shadow);

        customViewHolder.target_shadow.setColorFilter(Color.WHITE);


        customViewHolder.load_more_check.setVisibility(View.VISIBLE);
        final Handler ha = new Handler();
        ha.postDelayed(new Runnable() {


            @Override
            public void run() {

                check = feedItem.getCheck();

                if (check == 1) {

                    if (customViewHolder.load_more_check != null && customViewHolder.load_more_check.isShown() && allow == 1) {

                        Your_Suggestions.load_user();

                        allow = 0;
                        feedItem.setCheck(0);
                        Toast.makeText(mContext, "loadingMoar from : " + feedItem.getSC_username(), Toast.LENGTH_SHORT).show();


                    }
                }
                final Toast toast = Toast.makeText(mContext, Integer.toString(i), Toast.LENGTH_SHORT);
                //toast.show();

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        toast.cancel();
                    }
                }, 10);
                ha.postDelayed(this, 100);
            }
        }, 3000);

    }

    @Override
    public int getItemCount() {
        return (null != feedItemList ? feedItemList.size() : 0);
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {
        protected ImageView target;
        protected ImageView target_shadow;
        protected RelativeLayout load_more_check;
        protected ImageButton Add_btn;
        protected ImageButton heart_btn;

        public CustomViewHolder(View view) {
            super(view);
            this.target = (ImageView) view.findViewById(R.id.daimajia_slider_image);
            this.target_shadow = (ImageView) view.findViewById(R.id.imageView4);
            this.load_more_check = view.findViewById(R.id.load_more_check);
            this.Add_btn = view.findViewById(R.id.button2);
            this.heart_btn = view.findViewById(R.id.button3);
        }
    }


}

