package com.frozensparks.hellofriend;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.frozensparks.hellofriend.NewAndHot.dataFragment;
import com.frozensparks.hellofriend.SignUp.EditProfile;
import com.frozensparks.hellofriend.SignUp.MoreFragment;
import com.frozensparks.hellofriend.SignUp.SignUp;
import com.frozensparks.hellofriend.Tools.MyFirebaseMessagingService;
import com.frozensparks.hellofriend.Tools.OnBackPressedListener;
import com.frozensparks.hellofriend.Tutorial.MainFragmentTutorial;
import com.frozensparks.hellofriend.likesAndDiamonds.DiamondFragment;
import com.frozensparks.hellofriend.likesAndDiamonds.likes_fragment;
import com.frozensparks.hellofriend.your_suggestions.Your_Suggestions;
import com.mukesh.countrypicker.CountryPicker;
import com.mukesh.countrypicker.CountryPickerListener;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    SparseArray<Fragment> myFragments;
    TextView LIKES_amount;
    RelativeLayout yLayout;
    Toolbar toolbar;
    ArrayList<String> likersid = new ArrayList<>();
    protected OnBackPressedListener onBackPressedListener;
    EditText country_input;
    Dialog dialog;
    Boolean active = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        final Handler haupload=new Handler();
        haupload.postDelayed(new Runnable() {


            @Override
            public void run() {


                SharedPreferences filter = MainActivity.this.getSharedPreferences("imageUpload", MODE_PRIVATE);


                if(!filter.getString("image1", "").equals("")){
                    uploadFile(filter.getString("image1", ""),1);
                }
                else if(!filter.getString("image2", "").equals("")){
                    uploadFile(filter.getString("image2", ""),2);
                }
                else if(!filter.getString("image3", "").equals("")){
                    uploadFile(filter.getString("image3", ""),3);
                }
                else if(!filter.getString("image4", "").equals("")){
                    uploadFile(filter.getString("image4", ""),4);

                }




                haupload.postDelayed(this, 15000);

            }
        }, 15000);



        final Handler ha=new Handler();
        ha.postDelayed(new Runnable() {


            @Override
            public void run() {

                if(active)
                InternetCheck();


        ha.postDelayed(this, 10);

    }
}, 1);


         toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        //devices with hardware menu button (e.g. Samsung Note) don't show action overflow menu
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        final DrawerLayout mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        View headerLayout = navigationView.getHeaderView(0);
        final ImageView imageView_profile = headerLayout.findViewById(R.id.imageView_profile);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        drawer.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(View drawerView) {
                SharedPreferences filter = MainActivity.this.getSharedPreferences("user", Context.MODE_PRIVATE);
                String user_id = String.valueOf(filter.getInt("userid", 0));

                if(!user_id.equals("0") ||!user_id.equals("")){
                    Glide.with(MainActivity.this).load("http://snapchat.frozensparks.com/user/" + user_id + "/pictures/1.jpg")
                            .apply(RequestOptions.circleCropTransform())
                            .into(imageView_profile);
                    TextView profile_title = findViewById(R.id.profile_title);
                    profile_title.setText(": "+ String.valueOf(filter.getInt("credits", 0)));


                }
                else{
                    imageView_profile.setImageResource(0);
                }

            }

            @Override
            public void onDrawerClosed(View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });

        SharedPreferences prefs = getSharedPreferences("tutorial", MODE_PRIVATE);
        boolean tutorial = prefs.getBoolean("show", true);


        if(tutorial)
            setFragment(new MainFragmentTutorial(),"MainFragmentTutorial");

        else
            if (InternetCheck())
            setFragment(new Your_Suggestions(),"Your_Suggestions");//init



        imageView_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                SharedPreferences filter = MainActivity.this.getSharedPreferences("user", Context.MODE_PRIVATE);
                String user_id = String.valueOf(filter.getInt("userid", 0));



                if (user_id.equals("0")) {

                    setFragment(new SignUp(),"SignUp");

                } else {
                    addYour_profile();
                }


            }
        });


        View more_rl = findViewById(R.id.more_rl);
        more_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setFragment(new MoreFragment(),"MoreFragment");

            }
        });


        View navFooter1 = findViewById(R.id.logout_rl);
        navFooter1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);

                // set title
                alertDialogBuilder.setTitle("Logout?");

                // set dialog message
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                SharedPreferences.Editor editor = getSharedPreferences("user", MODE_PRIVATE).edit();
                                editor.clear();
                                editor.apply();

                                SharedPreferences.Editor editor2 = getSharedPreferences("user_storage", MODE_PRIVATE).edit();
                                editor2.clear();
                                editor2.apply();
                                imageView_profile.setImageDrawable(getResources().getDrawable(R.drawable.ic_person_black));
                                mDrawerLayout.closeDrawers();

                                Snackbar snack = Snackbar.make(v, R.string.logoutwasasuccess, Snackbar.LENGTH_LONG);
                                View view2 = snack.getView();
                                TextView tv = (TextView) view2.findViewById(android.support.design.R.id.snackbar_text);
                                tv.setTextColor(Color.WHITE);
                                snack.show();


                            }
                        })
                        .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // if this button is clicked, just close
                                // the dialog box and do nothing
                                dialog.cancel();
                            }
                        });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();
            }
        });


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public boolean InternetCheck(){
        if(dialog==null){
            dialog= new Dialog(MainActivity.this);

        }

        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null) {
            if(!netInfo.isConnected() ) {
                if (!dialog.isShowing()||dialog==null) {
                    dialog.setContentView(R.layout.nointernet_layout);
                    dialog.show();
                }

            }
            else{
                if (dialog!=null&&dialog.isShowing()){
                    dialog.dismiss();
                }
            }
        }else{
            if (!dialog.isShowing()||dialog==null) {

                dialog= new Dialog(MainActivity.this);
                dialog.setContentView(R.layout.nointernet_layout);
                dialog.show();
            }
        }

        if (netInfo != null)
        return netInfo.isConnected();
        else return false;
    }

    @SuppressLint("RestrictedApi")
    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {

        // custom dialog
        final int[] minage = new int[1];
        final int[] maxage = new int[1];

        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.filter_dialog);
        dialog.setTitle(R.string.yoursearchpreferences);
        final String[] gender = {""};

        SharedPreferences filter = this.getSharedPreferences("filter", MODE_PRIVATE);
        gender[0] = filter.getString("gender", "");
        minage[0]=filter.getInt("minage", 13);
        maxage[0]=filter.getInt("maxage", 99);
        final String[] country = {filter.getString("country", "")};

        country_input =  dialog.findViewById(R.id.country_input_filter);
        final RadioGroup radioGroup1 = dialog.findViewById(R.id.radioGroup1_filter);

        if(filter.getString("gender", "")!="") {
            RadioButton r1 = (RadioButton) radioGroup1.getChildAt(Integer.valueOf(filter.getString("gender", "")));
            r1.setChecked(true);
        }


        final CountryPicker picker = CountryPicker.newInstance("Select Country");  // dialog title
        picker.setListener(new CountryPickerListener() {
            @Override
            public void onSelectCountry(String name, String code, String dialCode, int flagDrawableResID) {

                country[0] =name;
                //Toast.makeText(getContext(), code, Toast.LENGTH_SHORT).show();
                country_input.setText(name);
                picker.dismiss();

            }
        });
        country_input.setText(filter.getString("country", ""));
        country_input.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b)
                    picker.show(getSupportFragmentManager(), "COUNTRY_PICKER");

            }
        });


        final NumberPicker np =  dialog.findViewById(R.id.minagepicker);
        np.setMaxValue(99);
        np.setValue(filter.getInt("minage", 13));
        np.setMinValue(13);
        np.setWrapSelectorWheel(false);
        np.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                minage[0] =i1;
            }
        });

        final NumberPicker npmax =  dialog.findViewById(R.id.maxagepicker);
        npmax.setMaxValue(99);
        npmax.setValue(filter.getInt("maxage", 99));
        npmax.setMinValue(13);
        npmax.setWrapSelectorWheel(false);
        npmax.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                maxage[0] =i1;

            }
        });
        Button dialogButton = (Button) dialog.findViewById(R.id.cancelfilter);
        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        final int idx = radioGroup1.indexOfChild(radioGroup1.findViewById(radioGroup1.getCheckedRadioButtonId()));
        RadioButton r = (RadioButton)  radioGroup1.getChildAt(idx);
        if (r!=null) {
            gender[0] = String.valueOf(r.getTag());
        }

        radioGroup1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                // you can check if radiobutton is checked or unchecked here , do needful codes
                    RadioButton b = (RadioButton) dialog.findViewById(checkedId);
                if(b==null)
                    gender[0] ="";

                else
                    gender[0] = (String) b.getTag();

            }
        });

        Button apply = dialog.findViewById(R.id.saveandapplyfilter);
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                SharedPreferences.Editor filter = getApplicationContext().getSharedPreferences("filter", MODE_PRIVATE).edit();
                filter.putString("gender", gender[0]);
                filter.putInt("minage", minage[0]);
                filter.putInt("maxage", maxage[0]);
                filter.putString("country", country[0]);
                filter.apply();
                dialog.dismiss();


                Your_Suggestions myFragment = (Your_Suggestions)getSupportFragmentManager().findFragmentByTag("Your_Suggestions");
                if (myFragment != null && myFragment.isVisible()) {
                    setFragment(new Your_Suggestions(),"Your_Suggestions");
                }

                dataFragment myFragment2 = (dataFragment)getSupportFragmentManager().findFragmentByTag("dataFragment");
                if (myFragment2 != null && myFragment2.isVisible()) {
                    setFragment(new dataFragment(),"dataFragment");
                }
            }
        });

        Button dialogButtonclear = (Button) dialog.findViewById(R.id.clearfilter);
        // if button is clicked, close the custom dialog
        dialogButtonclear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                radioGroup1.clearCheck();
                gender[0] = "";
                minage[0] = 13;
                maxage[0] = 99;
                country[0] ="";
                country_input.setText("");
                npmax.setValue(99);
                np.setValue(13);
                RadioButton r = (RadioButton)  radioGroup1.getChildAt(idx);
                if (r!=null) {
                    r.setChecked(false);
                    gender[0] = "";
                }



            }
        });

        dialog.show();
        getSupportActionBar().closeOptionsMenu();

        return super.onMenuOpened(featureId, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        if (id == R.id.overflow_icon) {
            return true;
        }
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


            setFragment(new Your_Suggestions(),"Your_Suggestions");


        } else if (id == R.id.nav_gallery) {

            setFragment(new dataFragment(),"dataFragment");


        } else if (id == R.id.nav_slideshow) {


            SharedPreferences filter = this.getSharedPreferences("user", Context.MODE_PRIVATE);
            String user_id = String.valueOf(filter.getInt("userid", 0));

            if (user_id.equals("0")) {

                setFragment(new SignUp(),"SignUp");

            } else {
                addYour_profile();
            }

        } else if (id == R.id.nav_share) {

            SharedPreferences filter = this.getSharedPreferences("user", Context.MODE_PRIVATE);
            String user_id = String.valueOf(filter.getInt("userid", 0));

            if (user_id.equals("0")) {

                setFragment(new SignUp(),"SignUp");

            } else {
                setFragment(new DiamondFragment(),"DiamondFragment");
            }


        }

        return true;
    }


    private void addYour_profile(){


        SharedPreferences filter = this.getSharedPreferences("user", Context.MODE_PRIVATE);
        String user_id = String.valueOf(filter.getInt("userid", 0));



        //urprofile
        AsyncTask_MainActivity lol = new AsyncTask_MainActivity(this);
        lol.execute("get_likes","");

        final LayoutInflater lyInflater = (LayoutInflater) getApplicationContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        yLayout = (RelativeLayout) lyInflater.inflate(
                R.layout.your_profile_layout, null);
        yLayout.setVisibility(View.VISIBLE);


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

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true);

        Glide.with(this).load("http://snapchat.frozensparks.com/user/" + user_id + "/pictures/1.jpg")
                .apply(RequestOptions.circleCropTransform())
                .apply(requestOptions)
                .into(profilpicture_porfile);


        profilpicture_porfile.setImageDrawable(getResources().getDrawable(R.drawable.ic_person_black));

        profilpicture_porfile.startAnimation(slide_up2);

        ImageView profilpicture_porfile_shadow = yLayout.findViewById(R.id.profilpicture_porfile_shadow);

        Glide.with(this).load("http://snapchat.frozensparks.com/user/" + user_id + "/pictures/1.jpg")
                .apply(RequestOptions.circleCropTransform())
                .apply(requestOptions)
                .into(profilpicture_porfile_shadow);

        profilpicture_porfile_shadow.setImageDrawable(getResources().getDrawable(R.drawable.ic_person_black));
        profilpicture_porfile_shadow.setColorFilter(Color.WHITE);
        profilpicture_porfile_shadow.startAnimation(slide_up2);



        LinearLayout picturebar_your_profile = yLayout.findViewById(R.id.picturebar_your_profile);
        picturebar_your_profile.setBackgroundColor(Color.WHITE);
        picturebar_your_profile.setElevation(5);
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
        LIKES_amount =yLayout.findViewById(R.id.LIKES_amount);
        LIKES_amount.setTextColor(Color.BLACK);



        final ImageView profile_picturebar_1 = yLayout.findViewById(R.id.profile_picturebar_1);
        profile_picturebar_1.setImageDrawable(getResources().getDrawable(R.drawable.ic_person_black));
        Glide.with(this)
                .load("http://snapchat.frozensparks.com/user/" + user_id + "/pictures/2.jpg")
                .apply(RequestOptions.circleCropTransform())
                .listener(new RequestListener<Drawable>() {

                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        profile_picturebar_1.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .apply(requestOptions)
                .into(profile_picturebar_1);

        final ImageView profile_picturebar_2 = yLayout.findViewById(R.id.profile_picturebar_2);
        profile_picturebar_2.setImageDrawable(getResources().getDrawable(R.drawable.ic_person_black));
        Glide.with(this)
                .load("http://snapchat.frozensparks.com/user/" + user_id + "/pictures/3.jpg")
                .apply(RequestOptions.circleCropTransform())
                .apply(requestOptions)
                .listener(new RequestListener<Drawable>() {

                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        profile_picturebar_2.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(profile_picturebar_2);

        final ImageView profile_picturebar_3 = yLayout.findViewById(R.id.profile_picturebar_3);
        profile_picturebar_3.setImageDrawable(getResources().getDrawable(R.drawable.ic_person_black));
        Glide.with(this)
                .load("http://snapchat.frozensparks.com/user/" + user_id + "/pictures/4.jpg")
                .apply(RequestOptions.circleCropTransform())
                .apply(requestOptions)
                .listener(new RequestListener<Drawable>() {

                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        profile_picturebar_3.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(profile_picturebar_3);


        RelativeLayout your_profile_likes_bar = yLayout.findViewById(R.id.your_profile_likes_bar);
        your_profile_likes_bar.setBackgroundColor(Color.WHITE);
        your_profile_likes_bar.setElevation(5);
        your_profile_likes_bar.setLayoutParams(params_view_in_profile);
        your_profile_likes_bar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(new likes_fragment(),"likes_fragment");
                removeYour_profile();
            }
        });

        your_profile_likes_bar.startAnimation(slide_up3);

        LinearLayout your_profile_edit_bar = yLayout.findViewById(R.id.your_profile_edit_bar);
        your_profile_edit_bar.setBackgroundColor(Color.WHITE);

        your_profile_edit_bar.setLayoutParams(params_view_in_profile);
        your_profile_edit_bar.setBackgroundColor(Color.WHITE);
        your_profile_edit_bar.setElevation(5);


        your_profile_edit_bar.startAnimation(slide_up4);


        TextView LIKES = yLayout.findViewById(R.id.LIKES);
        LIKES.setAllCaps(true);
        LIKES.setTextColor(Color.BLACK);
        LIKES.setText(R.string.likes);

        int gender = Integer.valueOf(filter.getString("gender", ""));
        String country = filter.getString("country", "");
        String sc_username = filter.getString("sc_username", "");
        int age = Integer.valueOf(filter.getString("age", ""));

        TextView SnapChatName_yourProfile =yLayout.findViewById(R.id.SnapChatName_yourProfile);
        SnapChatName_yourProfile.setTextColor(Color.BLACK);

        ImageView gender_your_profile = yLayout.findViewById(R.id.gender_your_profile);
        gender_your_profile.setColorFilter(Color.BLACK);

        TextView age_yourprofile =yLayout.findViewById(R.id.age_yourprofile);
        age_yourprofile.setTextColor(Color.BLACK);

        TextView country_your_profile =yLayout.findViewById(R.id.country_your_profile);
        country_your_profile.setTextColor(Color.BLACK);


        SnapChatName_yourProfile.setText(sc_username);
        switch(gender){
            case 0 : gender_your_profile.setImageDrawable(getResources().getDrawable(R.drawable.ic_man));
                break;
            case 1 : gender_your_profile.setImageDrawable(getResources().getDrawable(R.drawable.ic_woman));
                break;
            case 2 : gender_your_profile.setImageDrawable(getResources().getDrawable(R.drawable.ic_nogender));
                break;
        }

        age_yourprofile.setText(String.valueOf(Calendar.getInstance().get(Calendar.YEAR)-age));

        picturebar_your_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                removeYour_profile();
                setFragment(new EditProfile(),"EditProfile");
            }
        });
        country_your_profile.setText(country);



        Button Edit_Your_Profile_btn = yLayout.findViewById(R.id.Edit_Your_Profile_btn);
        Edit_Your_Profile_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                removeYour_profile();
                setFragment(new EditProfile(),"EditProfile");
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
        ImageView profilpicture_porfile_shadow = yLayout.findViewById(R.id.profilpicture_porfile_shadow);
        profilpicture_porfile_shadow.startAnimation(slide_up4);


        LinearLayout picturebar_your_profile = yLayout.findViewById(R.id.picturebar_your_profile);
        picturebar_your_profile.startAnimation(slide_up3);

        RelativeLayout your_profile_likes_bar = yLayout.findViewById(R.id.your_profile_likes_bar);
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
                yLayout.setVisibility(View.GONE);
                ((ViewManager) yLayout.getParent()).removeView(yLayout);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }

    public void setFragment(Fragment fragment, String tag) {
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_main, fragment,tag);
            ft.commit();

            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
        }

    }

    public void setOnBackPressedListener(OnBackPressedListener onBackPressedListener) {
        this.onBackPressedListener = onBackPressedListener;


        if(yLayout!=null && yLayout.getVisibility()==View.VISIBLE) {
            removeYour_profile();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        else if (yLayout!=null && yLayout.getVisibility()==View.VISIBLE) {
                removeYour_profile();
            }

        else if (onBackPressedListener != null)
            onBackPressedListener.doBack();
        else
            super.onBackPressed();
    }


    public int uploadFile(String sourceFileUri, final int way) {
        String nr = Integer.toString(way);

        String imagePath ="";
        SharedPreferences filter = this.getSharedPreferences("imageUpload", MODE_PRIVATE);
        final String imagePath1 = filter.getString("image1", "");
        final String imagePath2 = filter.getString("image2", "");
        final String imagePath3 = filter.getString("image3", "");
        final String imagePath4 = filter.getString("image4", "");

        SharedPreferences filter2 = MainActivity.this.getSharedPreferences("user", Context.MODE_PRIVATE);
        String user_id = String.valueOf(filter2.getInt("userid", 0));

        String upLoadServerUri = "http://snapchat.frozensparks.com/upload_pic.php?user_id=" + user_id +"&nr=" + nr;


//        Toast.makeText(context, "Creating your product", Toast.LENGTH_SHORT).show();


        if (sourceFileUri == null){
            Log.e("uploadFile", "Source File is null §");

            return 0;


        }

        HttpURLConnection conn;
        DataOutputStream dos;
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1024 * 1024;
        File sourceFile = new File(sourceFileUri);

        if (!sourceFile.isFile()) {

            Log.e("uploadFile", "Source File not exist :" +imagePath);
            return 0;
        }
        else
        {
            int serverResponseCode = 0;
            try {
                Bitmap bitmap = BitmapFactory.decodeFile(sourceFileUri);

                int resizeint;

                do{
                    bitmap = Bitmap.createScaledBitmap(bitmap,
                            (int) (bitmap.getWidth() * 0.9), (int) (bitmap.getHeight() * 0.9), false);
                    resizeint = bitmap.getRowBytes() * bitmap.getHeight();

                    //Toast.makeText(this, Integer.toString(bitmapResized.getRowBytes() * bitmapResized.getHeight()), Toast.LENGTH_SHORT).show();

                    Log.d("resizeBitmap",Integer.toString(resizeint));
                }
                while (resizeint > 20000000);//20000000

                ExifInterface exif = null;
                try {
                    exif = new ExifInterface(sourceFileUri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                        ExifInterface.ORIENTATION_UNDEFINED);

                bitmap = rotateBitmap(bitmap, orientation);


                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                byte[] bitmapdata = outputStream.toByteArray();

                InputStream fileInputStream = new ByteArrayInputStream(bitmapdata);


                // open a URL connection to the Servlet
                //FileInputStream fileInputStream = new FileInputStream(sourceFile);


                URL url = new URL(upLoadServerUri);

                //Open a HTTP  connection to  the URL
                conn = (HttpURLConnection) url.openConnection();
                conn.setDoInput(true); // Allow Inputs
                conn.setDoOutput(true); // Allow Outputs
                conn.setUseCaches(false); // Don't use a Cached Copy
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Connection", "Keep-Alive");
                conn.setRequestProperty("ENCTYPE", "multipart/form-data");
                conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                conn.setRequestProperty("upfile", sourceFileUri);


                dos = new DataOutputStream(conn.getOutputStream());

                dos.writeBytes(twoHyphens + boundary + lineEnd);
                dos.writeBytes("Content-Disposition: form-data; name=\"upfile\";filename="+ sourceFileUri + "" + lineEnd);
                dos.writeBytes(lineEnd);

                // create a buffer of  maximum size
                bytesAvailable = fileInputStream.available();

                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                buffer = new byte[bufferSize];

                // read file and write it into form...
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                while (bytesRead > 0) {

                    dos.write(buffer, 0, bufferSize);
                    bytesAvailable = fileInputStream.available();
                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                }

                // send multipart form data necesssary after file data...
                dos.writeBytes(lineEnd);
                dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

                // Responses from the server (code and message)
                serverResponseCode = conn.getResponseCode();
                InputStream inputStream = conn.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                String result = "";
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    result += line;


                }


                if(serverResponseCode == 200){


                    final String finalResult = result;
                    final int finalServerResponseCode = serverResponseCode;
                    MainActivity.this.runOnUiThread(new Runnable() {
                        public void run() {

                            //Toast.makeText(product_creator.this, finalResult,Toast.LENGTH_SHORT).show();
                            Log.i("uploadFile", "Server Response is : "+ finalResult + ": " + finalServerResponseCode);


                            if(way==1){

                                SharedPreferences.Editor filter = MainActivity.this.getSharedPreferences("imageUpload", MODE_PRIVATE).edit();
                                filter.putString("image1", "");
                                filter.apply();


                                if(!imagePath2.equals("")) {

                                    new Thread(new Runnable() {
                                        public void run() {
                                            uploadFile(imagePath2, 2);
                                        }
                                    }).start();
                                }

                                else if(!imagePath3.equals("")) {

                                    new Thread(new Runnable() {
                                        public void run() {
                                            uploadFile(imagePath3, 3);
                                        }
                                    }).start();

                                }
                                else if(!imagePath4.equals("")){

                                    new Thread(new Runnable() {
                                        public void run() {
                                            uploadFile(imagePath4, 4);
                                        }
                                    }).start();

                                }


                            }
                            if(way==2){
                                SharedPreferences.Editor filter = MainActivity.this.getSharedPreferences("imageUpload", MODE_PRIVATE).edit();
                                filter.putString("image2", "");
                                filter.apply();


                                if(!imagePath3.equals("")) {

                                    new Thread(new Runnable() {
                                        public void run() {
                                            uploadFile(imagePath3, 3);
                                        }
                                    }).start();


                                }
                                else if(!imagePath4.equals("")){

                                    new Thread(new Runnable() {
                                        public void run() {
                                            uploadFile(imagePath4, 4);
                                        }
                                    }).start();

                                }


                            }

                            if(way==3){
                                SharedPreferences.Editor filter = MainActivity.this.getSharedPreferences("imageUpload", MODE_PRIVATE).edit();
                                filter.putString("image3", "");
                                filter.apply();



                                if(!imagePath4.equals("")){

                                    new Thread(new Runnable() {
                                        public void run() {
                                            uploadFile(imagePath4, 4);
                                        }
                                    }).start();

                                }


                            }
                            if(way==4){


                                SharedPreferences.Editor filter = MainActivity.this.getSharedPreferences("imageUpload", MODE_PRIVATE).edit();
                                filter.putString("image4", "");
                                filter.apply();


                            }

                        }
                    });
                }

                //close the streams //
                fileInputStream.close();
                dos.flush();
                dos.close();

            } catch (MalformedURLException ex) {

                ex.printStackTrace();


                Log.e("Upload file to server", "error: " + ex.getMessage(), ex);
            } catch (Exception e) {


                e.printStackTrace();



                Log.e("Upload Error:", "Exception : "
                        + e.getMessage(), e);
            }

            return serverResponseCode;

        } // End else block
    }

    public static Bitmap rotateBitmap(Bitmap bitmap, int orientation) {

        Matrix matrix = new Matrix();
        switch (orientation) {
            case ExifInterface.ORIENTATION_NORMAL:
                return bitmap;
            case ExifInterface.ORIENTATION_FLIP_HORIZONTAL:
                matrix.setScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                matrix.setRotate(180);
                break;
            case ExifInterface.ORIENTATION_FLIP_VERTICAL:
                matrix.setRotate(180);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_TRANSPOSE:
                matrix.setRotate(90);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_90:
                matrix.setRotate(90);
                break;
            case ExifInterface.ORIENTATION_TRANSVERSE:
                matrix.setRotate(-90);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                matrix.setRotate(-90);
                break;
            default:
                return bitmap;
        }
        try {
            Bitmap bmRotated = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            bitmap.recycle();
            return bmRotated;
        }
        catch (OutOfMemoryError e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        active = false;
        MyFirebaseMessagingService.activityPaused();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        active = false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        active = true;
        MyFirebaseMessagingService.activityActive();

    }

    public class AsyncTask_MainActivity extends android.os.AsyncTask<String, Void, String> {

        String doafter = "";
        Context context;
        String type;
        int result_int;
        String sc_username;


        public AsyncTask_MainActivity(Context activity) {


            context = activity;

        }


        @Override
        protected String doInBackground(String... params) {

            type = params[0];


            if (type.equals("get_likes")) {
                try {

                    SharedPreferences filter = context.getSharedPreferences("user", MODE_PRIVATE);
                    int id = filter.getInt("userid", 0);

                    String URL = "http://snapchat.frozensparks.com/user/"+id+"/likers.txt";





                    java.net.URL url = new URL(URL);
                    HttpURLConnection httpurlconn = (HttpURLConnection) url.openConnection();
                    httpurlconn.setRequestMethod("POST");
                    httpurlconn.setDoOutput(true);
                    httpurlconn.setDoInput(true);
                    OutputStream outputStream = httpurlconn.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    String post_data = URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(id), "UTF-8") + "&" +
                            URLEncoder.encode("id_to_buy", "UTF-8") + "=" + URLEncoder.encode(params[1], "UTF-8");
                    bufferedWriter.write(post_data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();

                    InputStream inputStream = httpurlconn.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                    String result = "";
                    String line;

                    while ((line = bufferedReader.readLine()) != null) {
                        result += line;
                    }
                    bufferedReader.close();
                    inputStream.close();
                    httpurlconn.disconnect();

                    return result;


                } catch (IOException e) {
                    e.printStackTrace();
                }


            }


            return null;
        }


        @Override
        protected void onPreExecute() {


        }

        @Override
        protected void onPostExecute(String result) {


            if (result != null) {
                if (result == "") {
                    result = "0";
                }
                Log.d("AsyncTask says", result);


                if (type.equals("get_likes")) {

                    likersid = new ArrayList<>();

                    if (result.contains(",")) {
                        String[] separated = result.split(",");

                        for (int j = 0; j < separated.length; j++) {

                            likersid.add(separated[j]);

                            LIKES_amount.setText(Integer.toString(likersid.size()-1));
                        }


                    } else {

                        LIKES_amount.setText(0);
                    }

                }



            }


        }


    }
}