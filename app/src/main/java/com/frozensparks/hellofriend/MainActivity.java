package com.frozensparks.hellofriend;

import android.content.Context;
import android.graphics.Color;
import android.media.Image;
import android.net.LinkAddress;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.transition.Transition;
import android.support.transition.TransitionManager;
import android.support.transition.TransitionSet;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.transition.Slide;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.frozensparks.hellofriend.NewAndHot.dataFragment;
import com.frozensparks.hellofriend.your_suggestions.Your_Suggestions;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    SparseArray<Fragment> myFragments;
    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        setFragment(new Your_Suggestions());//init
        myFragments = new SparseArray<Fragment>();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {

            Fragment fragment;

            // get cached instance of the fragment
            fragment = myFragments.get(1);

            // if fragment doesn't exist in myFragments, create one and add to it
            if (fragment == null) {
                fragment = new Your_Suggestions();
                myFragments.put(1, fragment);
            }
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);


        } else if (id == R.id.nav_gallery) {

            setFragment(new dataFragment());


        } else if (id == R.id.nav_slideshow) {
            //urprofile

            final LayoutInflater lyInflater = (LayoutInflater) getApplicationContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final ConstraintLayout yLayout = (ConstraintLayout) lyInflater .inflate(
                    R.layout.your_profile_layout, null);

            Animation slide_up = AnimationUtils.loadAnimation(getApplicationContext(),
                    R.anim.slide_up);

            slide_up.setInterpolator(new FastOutSlowInInterpolator());
            slide_up.setStartOffset(2000);
            yLayout.startAnimation(slide_up);


            ConstraintLayout.LayoutParams lp = new ConstraintLayout.LayoutParams(
                    ConstraintLayout.LayoutParams.MATCH_PARENT,
                    ConstraintLayout.LayoutParams.MATCH_PARENT);
            ConstraintLayout background_ur_profile = yLayout.findViewById(R.id.background_ur_profile);

            LinearLayout picturebar_your_profile = yLayout.findViewById(R.id.picturebar_your_profile);
            picturebar_your_profile.setBackground(getResources().getDrawable(R.drawable.shadow1));
            LinearLayout.LayoutParams lllp = new LinearLayout.LayoutParams(
                    ConstraintLayout.LayoutParams.MATCH_PARENT,
                    ConstraintLayout.LayoutParams.WRAP_CONTENT);
            picturebar_your_profile.setLayoutParams(lllp);


            LinearLayout your_profile_likes_bar = yLayout.findViewById(R.id.your_profile_likes_bar);
            your_profile_likes_bar.setBackground(getResources().getDrawable(R.drawable.shadow1));

            LinearLayout your_profile_edit_bar = yLayout.findViewById(R.id.your_profile_edit_bar);
            your_profile_edit_bar.setBackground(getResources().getDrawable(R.drawable.shadow1));




            ImageView profile_picturebar_1 = yLayout.findViewById(R.id.profile_picturebar_1);
            profile_picturebar_1.setImageDrawable(getResources().getDrawable(R.drawable.ic_person_black));

            ImageView profile_picturebar_2 = yLayout.findViewById(R.id.profile_picturebar_2);
            profile_picturebar_2.setImageDrawable(getResources().getDrawable(R.drawable.ic_person_black));

            ImageView profile_picturebar_3 = yLayout.findViewById(R.id.profile_picturebar_3);
            profile_picturebar_3.setImageDrawable(getResources().getDrawable(R.drawable.ic_person_black));

            ImageView profile_picturebar_4 = yLayout.findViewById(R.id.profile_picturebar_4);
            profile_picturebar_4.setImageDrawable(getResources().getDrawable(R.drawable.ic_person_black));

            ImageView profilpicture_porfile = yLayout.findViewById(R.id.profilpicture_porfile);
            profilpicture_porfile.setImageDrawable(getResources().getDrawable(R.drawable.ic_person_black));

            TextView LIKES = yLayout.findViewById(R.id.LIKES);
            LIKES.setAllCaps(true);
            LIKES.setTextColor(Color.BLACK);
            LIKES.setText(R.string.likes);

            picturebar_your_profile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getApplicationContext(), "hoi", Toast.LENGTH_SHORT).show();
                }
            });

            background_ur_profile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((ViewManager)yLayout.getParent()).removeView(yLayout);

                }
            });



            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);

            this.addContentView(yLayout,lp);


        }  else if (id == R.id.nav_share) {

            //Getdiamonds

        }

        return true;
    }

    public void setFragment(Fragment fragment){
        if(fragment!=null){
            FragmentTransaction ft= getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_main,fragment);
            ft.commit();

            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
        }

    }

}