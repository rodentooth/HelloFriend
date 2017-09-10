package com.frozensparks.hellofriend.Tutorial;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.frozensparks.hellofriend.R;
import com.frozensparks.hellofriend.your_suggestions.FeedItem;
import com.rd.PageIndicatorView;
import com.rd.animation.type.AnimationType;

import java.util.List;

public class MainFragmentTutorial extends Fragment {

    private static Context context;
    int position = 0;
    int totalrows;
    View view;
    static int load_more =0;
    private static List<FeedItem> feedsList = null;
    LinearLayoutManager llm;

    //private ViewPager mPager;
    //private MyPagerAdapter mAdapter;

    MyAdapter mAdapter;
    ViewPager mPager;
    ViewPager bg1;
    ViewPager bg2;
    RelativeLayout empty_state_your_suggestions;
    final Handler ha2 = new Handler();
    int bgimagenr=0;
    Boolean play=true;




    public MainFragmentTutorial() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


/*
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) getActivity().findViewById(R.id.toolbar);
        AppBarLayout.LayoutParams params =
                (AppBarLayout.LayoutParams) toolbar.getLayoutParams();
        params.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS | AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL);
*/

        view= inflater.inflate(R.layout.fragment_tutorial_main, container, false);

        mAdapter = new MyAdapter(getActivity().getSupportFragmentManager());
        mPager =  view.findViewById(R.id.pager);
        mPager.setAdapter(mAdapter);

        mPager.setPageMargin(0);

        mPager.setPageTransformer(true, new FragmentTransformerTutorial());

        mPager.getAdapter().notifyDataSetChanged();


        final PageIndicatorView pageIndicatorView = (PageIndicatorView) view.findViewById(R.id.pageIndicatorView);
        pageIndicatorView.setAnimationType(AnimationType.DROP);
        pageIndicatorView.setDynamicCount(false);
        pageIndicatorView.setCount(3);
        pageIndicatorView.setSelection(0);

                bg1 =  view.findViewById(R.id.tutbg1);
        bg1.setBackgroundResource(R.mipmap.bg_tutorial_1);
        bg2 =  view.findViewById(R.id.tutbg2);
        bg2.setBackgroundResource(R.mipmap.bg_tutorial_1);


        mPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                int icon = (R.mipmap.bg_tutorial_1);
                int i1 = 0;
                int i2 = 1;
                switch (position) {
                    case 0:
                        bg1.setBackgroundResource(R.mipmap.bg_tutorial_2);
                        bg2.setBackgroundResource(R.mipmap.bg_tutorial_1);
                        bgimagenr = 1;
                        //bg1.setTranslationX(positionOffset < 0 ? -positionOffset * view.getWidth() : -positionOffset * view.getWidth());
                        //bg1.setTranslationX(positionOffset < 0 ? -positionOffset * view.getWidth() : -positionOffset * view.getWidth());
                        bg2.setAlpha(positionOffset < 0 ? 1f + positionOffset : 1f - positionOffset);
                        //bg2.setTranslationX(positionOffset < 0 ? -position * view.getWidth() : -positionOffset * view.getWidth());
                        bg1.setAlpha(positionOffset < 0 ? 1f - positionOffset : 1f + positionOffset);
                        i1 = 1;
                        i2 = 0;
                        break;
                    case 1:
                        bg1.setBackgroundResource(R.mipmap.bg_tutorial_3);
                        bg2.setBackgroundResource(R.mipmap.bg_tutorial_2);
                        icon = (R.mipmap.bg_tutorial_3);
                        //bg1.setTranslationX(positionOffset < 0 ? -positionOffset * view.getWidth() : -positionOffset * view.getWidth());
                        bg2.setAlpha(positionOffset < 0 ? 1f + positionOffset : 1f - positionOffset);
                        //bg2.setTranslationX(positionOffset < 0 ? -position * view.getWidth() : -positionOffset * view.getWidth());
                        bg1.setAlpha(positionOffset < 0 ? 1f - positionOffset : 1f + positionOffset);
                        bgimagenr = 0;
                        i1 = 0;
                        i2 = 1;
                        break;



                }
                switch(position) {
                    case 0:
                        pageIndicatorView.setProgress(0, positionOffset);
                        pageIndicatorView.setSelection(0);

                        break;
                    case 1:
                        pageIndicatorView.setProgress(1, positionOffset);
                        pageIndicatorView.setSelection(1);

                        break;
                    case 2:
                        pageIndicatorView.setProgress(2, positionOffset);
                        pageIndicatorView.setSelection(2);

                        break;

                }



                //ImageViewAnimatedChange(getActivity(), bg1, icon, i1);
                //ImageViewAnimatedChange(getActivity(), bg2, icon, i2);
            }

            @Override
            public void onPageSelected(int position) {



            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        return view;



    }


    public static void ImageViewAnimatedChange(Context c, final ViewPager v, final int new_image, final int i) {
        final Animation anim;
        if (i==1){
            anim = AnimationUtils.loadAnimation(c, android.R.anim.fade_out);
        }else{
            anim  = AnimationUtils.loadAnimation(c, android.R.anim.fade_in);

        }
        anim.setDuration(3000);
        anim.setAnimationListener(new Animation.AnimationListener()
        {
            @Override public void onAnimationStart(Animation animation) {
                if (i!=1)
                    v.setBackgroundResource(new_image);
                v.setVisibility(View.VISIBLE);
            }
            @Override public void onAnimationRepeat(Animation animation) {}
            @Override public void onAnimationEnd(Animation animation) {
                if(i==1)
                    v.setVisibility(View.INVISIBLE);
            }
        });
        v.startAnimation(anim);
    }


    public static class MyAdapter extends FragmentStatePagerAdapter {
        public MyAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case 0: return FragmentTutorial.init(position);
                case 1: return FragmentTutorial.init(position);
                case 2: return FragmentTutorial.init(position);
                case 3: return FragmentTutorial.init(position);
                case 4: return FragmentTutorial.init(position);

            }
            return new FragmentTutorial();
        }


        @Override
        public int getCount() {
            return 5;
        }


        }


    @Override
    public void onDestroy () {

        play=false;
        super.onDestroy ();

    }

    @Override
    public void onResume() {
        play=true;
        super.onResume();
    }

    @Override
    public void onPause() {
        play=false;

        super.onPause();
    }




}
