package com.frozensparks.hellofriend;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.util.SparseArray;
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

import com.bumptech.glide.Glide;
import com.frozensparks.hellofriend.NewAndHot.dataFragment;
import com.frozensparks.hellofriend.SignUp.SignUp;
import com.frozensparks.hellofriend.Tools.OnBackPressedListener;
import com.frozensparks.hellofriend.your_suggestions.Your_Suggestions;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    SparseArray<Fragment> myFragments;
    Context context;
    RelativeLayout yLayout;
    Toolbar toolbar;
    protected OnBackPressedListener onBackPressedListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


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

            AppBarLayout.LayoutParams params =
                    (AppBarLayout.LayoutParams) toolbar.getLayoutParams();
            params.setScrollFlags(0);
            params.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SNAP);



            setFragment(new Your_Suggestions());


        } else if (id == R.id.nav_gallery) {

            AppBarLayout.LayoutParams params =
                    (AppBarLayout.LayoutParams) toolbar.getLayoutParams();
            params.setScrollFlags(0);
            params.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS | AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL);


            setFragment(new dataFragment());


        } else if (id == R.id.nav_slideshow) {


            SharedPreferences filter = this.getSharedPreferences("user", Context.MODE_PRIVATE);
            String gender = filter.getString("gender", "");
            String user_id = String.valueOf(filter.getInt("userid", 0));

            if (gender.equals("")) {

                setFragment(new SignUp());

            } else {
                //urprofile

                final LayoutInflater lyInflater = (LayoutInflater) getApplicationContext()
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                yLayout = (RelativeLayout) lyInflater.inflate(
                        R.layout.your_profile_layout, null);


                Animation fade_in = AnimationUtils.loadAnimation(getApplicationContext(),
                        R.anim.fade_in);


                RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.MATCH_PARENT,
                        RelativeLayout.LayoutParams.MATCH_PARENT);
                RelativeLayout background_ur_profile = yLayout.findViewById(R.id.background_ur_profile);

                background_ur_profile.startAnimation(fade_in);


                Animation slide_up1 = AnimationUtils.loadAnimation(getApplicationContext(),
                        R.anim.slide_up);
                slide_up1.setInterpolator(new FastOutSlowInInterpolator());
                slide_up1.setStartOffset(10);


                Animation slide_up2 = AnimationUtils.loadAnimation(getApplicationContext(),
                        R.anim.slide_up);
                slide_up2.setInterpolator(new FastOutSlowInInterpolator());
                slide_up2.setStartOffset(400);


                Animation slide_up3 = AnimationUtils.loadAnimation(getApplicationContext(),
                        R.anim.slide_up);
                slide_up3.setInterpolator(new FastOutSlowInInterpolator());
                slide_up3.setStartOffset(800);

                Animation slide_up4 = AnimationUtils.loadAnimation(getApplicationContext(),
                        R.anim.slide_up);
                slide_up4.setInterpolator(new FastOutSlowInInterpolator());
                slide_up4.setStartOffset(1000);


                Animation slide_up5 = AnimationUtils.loadAnimation(getApplicationContext(),
                        R.anim.slide_up);
                slide_up5.setInterpolator(new FastOutSlowInInterpolator());
                slide_up5.setStartOffset(600);


                RelativeLayout keilandmainLayout = yLayout.findViewById(R.id.mainLayout_your_profile);

                keilandmainLayout.startAnimation(slide_up1);


                ImageView profilpicture_porfile = yLayout.findViewById(R.id.profilpicture_porfile);

                Glide.with(this).load("http://snapchat.frozensparks.com/user/" + user_id + "/pictures/1.jpg").into(profilpicture_porfile);

                profilpicture_porfile.setImageDrawable(getResources().getDrawable(R.drawable.ic_person_black));

                profilpicture_porfile.startAnimation(slide_up2);

                LinearLayout picturebar_your_profile = yLayout.findViewById(R.id.picturebar_your_profile);
                picturebar_your_profile.setBackground(getResources().getDrawable(R.drawable.shadow1));
                LinearLayout.LayoutParams lllp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                lllp.setMargins(0, 30, 0, 0);
                picturebar_your_profile.setLayoutParams(lllp);

                LinearLayout.LayoutParams params_view_in_profile = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                params_view_in_profile.setMargins(0, 10, 0, 0);

                picturebar_your_profile.startAnimation(slide_up5);


                ImageView profile_picturebar_1 = yLayout.findViewById(R.id.profile_picturebar_1);
                profile_picturebar_1.setImageDrawable(getResources().getDrawable(R.drawable.ic_person_black));

                ImageView profile_picturebar_2 = yLayout.findViewById(R.id.profile_picturebar_2);
                profile_picturebar_2.setImageDrawable(getResources().getDrawable(R.drawable.ic_person_black));

                ImageView profile_picturebar_3 = yLayout.findViewById(R.id.profile_picturebar_3);
                profile_picturebar_3.setImageDrawable(getResources().getDrawable(R.drawable.ic_person_black));

                ImageView profile_picturebar_4 = yLayout.findViewById(R.id.profile_picturebar_4);
                profile_picturebar_4.setImageDrawable(getResources().getDrawable(R.drawable.ic_person_black));

                LinearLayout your_profile_likes_bar = yLayout.findViewById(R.id.your_profile_likes_bar);
                your_profile_likes_bar.setBackground(getResources().getDrawable(R.drawable.shadow1));
                your_profile_likes_bar.setLayoutParams(params_view_in_profile);

                your_profile_likes_bar.startAnimation(slide_up3);

                LinearLayout your_profile_edit_bar = yLayout.findViewById(R.id.your_profile_edit_bar);
                your_profile_edit_bar.setBackground(getResources().getDrawable(R.drawable.shadow1));

                your_profile_edit_bar.setLayoutParams(params_view_in_profile);
                your_profile_edit_bar.startAnimation(slide_up4);


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
                        removeYour_profile();

                    }
                });


                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);

                this.addContentView(yLayout, lp);
            }

        } else if (id == R.id.nav_share) {

            //Getdiamonds

        }

        return true;
    }

    private void removeYour_profile() {


        Animation fade_out = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.fade_out);


        Animation slide_up1 = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.slide_right_out);
        slide_up1.setInterpolator(new FastOutSlowInInterpolator());
        slide_up1.setStartOffset(10);


        Animation slide_up2 = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.slide_right_out);
        slide_up2.setInterpolator(new FastOutSlowInInterpolator());
        slide_up2.setStartOffset(50);


        Animation slide_up3 = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.slide_right_out);
        slide_up3.setInterpolator(new FastOutSlowInInterpolator());
        slide_up3.setStartOffset(100);

        Animation slide_up4 = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.slide_right_out);
        slide_up4.setInterpolator(new FastOutSlowInInterpolator());
        slide_up4.setStartOffset(200);


        Animation slide_up5 = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.slide_right_out);
        slide_up5.setInterpolator(new FastOutSlowInInterpolator());
        slide_up5.setStartOffset(300);


        RelativeLayout keilandmainLayout = yLayout.findViewById(R.id.mainLayout_your_profile);
        keilandmainLayout.startAnimation(slide_up5);

        ImageView profilpicture_porfile = yLayout.findViewById(R.id.profilpicture_porfile);
        profilpicture_porfile.startAnimation(slide_up4);

        LinearLayout picturebar_your_profile = yLayout.findViewById(R.id.picturebar_your_profile);
        picturebar_your_profile.startAnimation(slide_up3);

        LinearLayout your_profile_likes_bar = yLayout.findViewById(R.id.your_profile_likes_bar);
        your_profile_likes_bar.startAnimation(slide_up2);

        LinearLayout your_profile_edit_bar = yLayout.findViewById(R.id.your_profile_edit_bar);
        your_profile_edit_bar.startAnimation(slide_up1);


        final RelativeLayout background_ur_profile = yLayout.findViewById(R.id.background_ur_profile);
        background_ur_profile.startAnimation(fade_out);


        fade_out.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                background_ur_profile.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        slide_up5.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                ((ViewManager) yLayout.getParent()).removeView(yLayout);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }

    public void setFragment(Fragment fragment) {
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_main, fragment);
            ft.commit();

            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
        }

    }

    public void setOnBackPressedListener(OnBackPressedListener onBackPressedListener) {
        this.onBackPressedListener = onBackPressedListener;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (onBackPressedListener != null)
            onBackPressedListener.doBack();
        else
            super.onBackPressed();
    }
}