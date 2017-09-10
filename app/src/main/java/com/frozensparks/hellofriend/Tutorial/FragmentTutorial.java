package com.frozensparks.hellofriend.Tutorial;

import android.animation.Animator;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.frozensparks.hellofriend.NewAndHot.Hot_People_fragment;
import com.frozensparks.hellofriend.R;
import com.frozensparks.hellofriend.your_suggestions.Your_Suggestions;
import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;
import com.sackcentury.shinebuttonlib.ShineButton;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Emanuel Graf on 28.08.2017.
 */

public class FragmentTutorial extends Fragment {




    /**
     * Retrieving this instance's number from its arguments.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
           }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tutorial_layout,
                container, false);

        View rootview =container;

        int position = getArguments() != null ? getArguments().getInt("position") : 1;

        ImageView arrow_tutorial =view.findViewById(R.id.arrow_tutorial);
        TextView title_tutorial = view.findViewById(R.id.title_tutorial);
        final ShimmerTextView subtitle_tutorial = view.findViewById(R.id.subtitle_tutorial);

        final ImageButton added_btn_tutorial = view.findViewById(R.id.added_btn_tutorial);

        final ShineButton add_btn_tutorial_clicker = view.findViewById(R.id.add_btn_tutorial);

        final ImageButton add_btn_tutorial = view.findViewById(R.id.add_btn_tutorial_toremove);
        add_btn_tutorial_clicker.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {

                    int finalRadius = Math.max(view.getWidth(), view.getHeight());

                    // create the animator for this view (the start radius is zero)
                    Animator anim = ViewAnimationUtils.createCircularReveal(added_btn_tutorial, (int) motionEvent.getX(), (int) motionEvent.getY(),
                            0, finalRadius);
                    anim.setDuration(1000);
                    anim.setInterpolator(new FastOutSlowInInterpolator());


                    // make the view visible and start the animation
                    added_btn_tutorial.setVisibility(View.VISIBLE);
                    anim.start();

                    anim.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animator) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animator) {

                            add_btn_tutorial.setVisibility(View.INVISIBLE);
                            add_btn_tutorial_clicker.setVisibility(View.INVISIBLE);

                        }
                        @Override
                        public void onAnimationCancel(Animator animator) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animator) {

                        }
                    });

                    Shimmer shimmer = new Shimmer();
                    shimmer.setDirection(Shimmer.ANIMATION_DIRECTION_RTL)
                            .setDuration(1500);


                    subtitle_tutorial.setText(R.string.swipetocontinue);
                    shimmer.start(subtitle_tutorial);

                    Snackbar snack = Snackbar.make(view, getString(R.string.perfect) + " :)", Snackbar.LENGTH_LONG);
                    View view2 = snack.getView();
                    TextView tv = (TextView) view2.findViewById(android.support.design.R.id.snackbar_text);
                    tv.setTextColor(Color.WHITE);
                    snack.show();
                }
                return false;
            }
        });

        if(position==0){
            arrow_tutorial.setRotation(180);
            Shimmer shimmer = new Shimmer();
            shimmer.setDirection(Shimmer.ANIMATION_DIRECTION_RTL)
                    .setDuration(1500);
            shimmer.start(subtitle_tutorial);
            add_btn_tutorial_clicker.setVisibility(View.INVISIBLE);

        }
        if(position==1){
            arrow_tutorial.setTag("2");
            title_tutorial.setText(R.string.addsomeone);
            subtitle_tutorial.setText(R.string.click);
            add_btn_tutorial.setVisibility(View.VISIBLE);
            add_btn_tutorial_clicker.setVisibility(View.VISIBLE);


        }
        if(position==2){
            add_btn_tutorial_clicker.setVisibility(View.INVISIBLE);

            arrow_tutorial.setTag("3");
            add_btn_tutorial.setVisibility(View.INVISIBLE);
            added_btn_tutorial.setVisibility(View.INVISIBLE);

            title_tutorial.setText(R.string.havefun);
            subtitle_tutorial.setText(R.string.swipeorcheckoutthemenu);

            SharedPreferences.Editor editor = getActivity().getSharedPreferences("tutorial", MODE_PRIVATE).edit();
            editor.putBoolean("show", false);
            editor.apply();

        }
        if(position==3) {
            title_tutorial.setText(R.string.letsgo);
            add_btn_tutorial.setVisibility(View.INVISIBLE);
            added_btn_tutorial.setVisibility(View.INVISIBLE);

            subtitle_tutorial.setVisibility(View.INVISIBLE);


        }
        if(position==4) {
            Your_Suggestions nextFrag= new Your_Suggestions();
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_main, nextFrag,"")
                    .addToBackStack(null)
                    .commit();
        }



            return view;

    }


    public static Fragment init(int position) {
        FragmentTutorial truitonList = new FragmentTutorial();

        // Supply val input as an argument.
        Bundle args = new Bundle();
        args.putInt("position", position);

        truitonList.setArguments(args);

        return truitonList;
    }
}
