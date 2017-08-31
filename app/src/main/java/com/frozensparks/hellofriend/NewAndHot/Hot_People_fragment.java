package com.frozensparks.hellofriend.NewAndHot;

import android.animation.Animator;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.ViewManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Space;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.frozensparks.hellofriend.SignUp.SignUp;
import com.frozensparks.hellofriend.Tools.BaseBackPressedListener;
import com.frozensparks.hellofriend.MainActivity;
import com.frozensparks.hellofriend.Tools.OnBackPressedListener;
import com.frozensparks.hellofriend.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Calendar;

import static android.content.Context.CLIPBOARD_SERVICE;
import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Mako on 1/13/2017.
 */
public class Hot_People_fragment extends Fragment {



    int fillposition = 0;
    int position;
    int totalrows;
    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        view = inflater.inflate(R.layout.content, container, false);


        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) getActivity().findViewById(R.id.toolbar);
        AppBarLayout.LayoutParams params =
                (AppBarLayout.LayoutParams) toolbar.getLayoutParams();
        params.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS | AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL);


        ((MainActivity) getActivity()).setOnBackPressedListener(new BaseBackPressedListener(getActivity()));

        final AsyncTask_hotPeople get_user = new AsyncTask_hotPeople(getContext());
        get_user.execute("get_users", "", "");

        return view;


    }


    public class AsyncTask_hotPeople extends android.os.AsyncTask<String, Void, String> {

        String doafter = "";
        Context context;
        String type;
        int result_int;
        String sc_username;


        public AsyncTask_hotPeople(Context activity) {


            context = activity;

        }


        @Override
        protected String doInBackground(String... params) {

            type = params[0];


            if (type.equals("get_users")) {
                try {
                    String URL = "http://snapchat.frozensparks.com/get_top_user.php";


                    SharedPreferences filter = context.getSharedPreferences("filter", MODE_PRIVATE);
                    String gender = filter.getString("gender", "");
                    int minage = filter.getInt("minage", 13);
                    int maxage = filter.getInt("maxage", 99);
                    String country = filter.getString("country", "");


                    java.net.URL url = new URL(URL);
                    HttpURLConnection httpurlconn = (HttpURLConnection) url.openConnection();
                    httpurlconn.setRequestMethod("POST");
                    httpurlconn.setDoOutput(true);
                    httpurlconn.setDoInput(true);
                    OutputStream outputStream = httpurlconn.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    String post_data = URLEncoder.encode("gender", "UTF-8") + "=" + URLEncoder.encode(gender, "UTF-8") + "&" +
                            URLEncoder.encode("minage", "UTF-8") + "=" + URLEncoder.encode(Integer.toString(minage), "UTF-8") + "&" +
                            URLEncoder.encode("maxage", "UTF-8") + "=" + URLEncoder.encode(Integer.toString(maxage), "UTF-8") + "&" +
                            URLEncoder.encode("country", "UTF-8") + "=" + URLEncoder.encode(country, "UTF-8") + "&" +
                            URLEncoder.encode("position", "UTF-8") + "=" + URLEncoder.encode(Integer.toString(position), "UTF-8");
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
            if (type.equals("buy_id")) {
                try {
                    sc_username = params[2];

                    String URL = "http://snapchat.frozensparks.com/buy_name.php";


                    SharedPreferences filter = context.getSharedPreferences("user", MODE_PRIVATE);
                    int id = filter.getInt("userid", 0);


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

            if (type.equals("like")) {
                try {
                    sc_username = params[2];

                    String URL = "http://snapchat.frozensparks.com/like.php";


                    SharedPreferences filter = context.getSharedPreferences("user", MODE_PRIVATE);
                    int id = filter.getInt("userid", 0);


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


                if (type.equals("buy_id")) {
                    if (result.equals("OK")) {
                        //todo Credits Abziehen


                        Intent internetIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("snapchat://add/" + sc_username));
                        context.startActivity(internetIntent);


                        Snackbar snack = Snackbar.make(view, R.string.copied_scname, Snackbar.LENGTH_LONG);
                        View view2 = snack.getView();
                        TextView tv = (TextView) view2.findViewById(android.support.design.R.id.snackbar_text);
                        tv.setTextColor(Color.WHITE);
                        snack.show();

                        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(CLIPBOARD_SERVICE);
                        ClipData clip = ClipData.newPlainText("label", sc_username);
                        clipboard.setPrimaryClip(clip);


                        SharedPreferences prefs2 = context.getSharedPreferences(
                                "user_storage", Context.MODE_PRIVATE);
                        final String blockstring = prefs2.getString("added", "0");

                        SharedPreferences.Editor editor = context.getSharedPreferences("user_storage", MODE_PRIVATE).edit();
                        editor.putString("added", blockstring + "," + sc_username);
                        editor.apply();

                    } else {

                        //todo nicht gekauft, new credits
                        //int credits = String.valueOf(result);
                    }

                }

                if (type.equals("like")) {
                    if (result.equals("OK")) {
                        //todo Credits Abziehen


                        SharedPreferences prefs2 = context.getSharedPreferences(
                                "user_storage", Context.MODE_PRIVATE);
                        final String blockstring = prefs2.getString("like", "0");

                        SharedPreferences.Editor editor = context.getSharedPreferences("user_storage", MODE_PRIVATE).edit();
                        editor.putString("like", blockstring + "," + sc_username);
                        editor.apply();

                        Snackbar snack = Snackbar.make(view, R.string.you_like_it, Snackbar.LENGTH_LONG);
                        View view2 = snack.getView();
                        TextView tv = (TextView) view2.findViewById(android.support.design.R.id.snackbar_text);
                        tv.setTextColor(Color.WHITE);
                        snack.show();
                    } else {

                        //todo nicht gekauft, new credits
                        //int credits = String.valueOf(result);
                    }


                }
                if (type.equals("get_users")) try {
                    position = position + 10;

                    JSONObject jsonObj = new JSONObject(result);

                    totalrows = Integer.parseInt(jsonObj.getString("AMOUNT"));

                    Log.d("json", "total rows: " + Integer.toString(totalrows));

                    // Getting JSON Array node

                    if (totalrows == 0) {

                        //todo no data


                    }
                    JSONArray toplevels = jsonObj.getJSONArray("USERS");


                    // Save state
                   /* Parcelable recyclerViewState;
                    recyclerViewState = mRecyclerView.getLayoutManager().onSaveInstanceState();

                    adapter = new RecycleViewAdapter_your_suggestions(Your_Suggestions.context, feedsList);
                    mRecyclerView.setAdapter(adapter);
*/

                    // looping through All Contacts
                    for (int i = 0; i < toplevels.length(); i++) {
                        JSONObject c = toplevels.getJSONObject(i);


                        final String id = c.getString("id");

                        final String gender = c.getString("gender");
                        final String sc_username = c.getString("sc_username");
                        final String year = c.getString("year");
                        final String value = c.getString("value");
                        final String Country = c.getString("country");
                        final int trigger = Integer.valueOf(c.getString("trigger"));
                        final View rootview = view;


                        final String PB_link = "http://snapchat.frozensparks.com/user/" + id + "/pictures/1.jpg";
                        final String PB_link2 = "http://snapchat.frozensparks.com/user/" + id + "/pictures/2.jpg";
                        final String PB_link3 = "http://snapchat.frozensparks.com/user/" + id + "/pictures/3.jpg";
                        final String PB_link4 = "http://snapchat.frozensparks.com/user/" + id + "/pictures/4.jpg";

                        final LinearLayout contentlist1 = view.findViewById(R.id.contentlist1);
                        final LinearLayout contentlist2 = view.findViewById(R.id.contentlist2);
                        LinearLayout contentlist3 = view.findViewById(R.id.contentlist3);

                    /*
                   if(( contentlist1).getChildCount() > 0)
                        ( contentlist1).removeAllViews();
                    if(( contentlist2).getChildCount() > 0)
                        ( contentlist2).removeAllViews();
                    if(( contentlist3).getChildCount() > 0)
                        ( contentlist3).removeAllViews();


*/

                        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) contentlist1.getLayoutParams();
                        params.width = view.getWidth() / 3;
                        //contentlist1.setLayoutParams(params);

                        final View v = getLayoutInflater().inflate(R.layout.user_image, null);
/*
                    if (v.getParent() != null)
                        ((ViewGroup) v.getParent()).removeView(v);
*/
                        //LinearLayout.LayoutParams  params2 = (new LinearLayout.LayoutParams);
//                    params2.setMargins(5,0,5,5);
                        v.setLayoutParams(params);


                        final ImageView image = (v.findViewById(R.id.image_person_content));


                        final View personpreview = getLayoutInflater().inflate(R.layout.personpreview, null);

                        image.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                final ImageButton addonSnap_personpreview = personpreview.findViewById(R.id.addonSnap_personpreview);
                                addonSnap_personpreview.setVisibility(View.VISIBLE);
                                final ImageButton addedonSnap_personpreview = personpreview.findViewById(R.id.addedonsnap_personpreview);
                                addedonSnap_personpreview.setVisibility(View.VISIBLE);
                                addedonSnap_personpreview.setVisibility(View.INVISIBLE);


                                Boolean canadd = false;
                                SharedPreferences prefs2 = context.getSharedPreferences(
                                        "user_storage", Context.MODE_PRIVATE);
                                final String blockstring = prefs2.getString("added", "");

                                if (blockstring.contains(",")) {
                                    String[] separated = blockstring.split(",");

                                    for (int j = 0; j < separated.length; j++) {
                                        if (separated[j].equals(sc_username)) {

                                            addedonSnap_personpreview.setVisibility(View.VISIBLE);
                                            addonSnap_personpreview.setVisibility(View.INVISIBLE);

                                            canadd = true;

                                        }
                                    }
                                } else {
                                    if (blockstring.equals(sc_username)) {

                                        addedonSnap_personpreview.setVisibility(View.VISIBLE);
                                        addonSnap_personpreview.setVisibility(View.INVISIBLE);

                                    }
                                }
                                final ImageButton like_btn_preson_preview = personpreview.findViewById(R.id.like_btn_preson_preview);
                                final ImageButton liked_btn_preson_preview = personpreview.findViewById(R.id.liked_btn_preson_preview);
                                liked_btn_preson_preview.setVisibility(View.VISIBLE);
                                liked_btn_preson_preview.setVisibility(View.INVISIBLE);

                                final String blockstring2 = prefs2.getString("like", "");

                                if (blockstring2.contains(",")) {
                                    String[] separated = blockstring2.split(",");

                                    for (int j = 0; j < separated.length; j++) {
                                        if (separated[j].equals(id)) {

                                            liked_btn_preson_preview.setVisibility(View.VISIBLE);
                                            like_btn_preson_preview.setVisibility(View.INVISIBLE);


                                        }
                                    }
                                } else {
                                    if (blockstring2.equals(id)) {

                                        liked_btn_preson_preview.setVisibility(View.VISIBLE);
                                        like_btn_preson_preview.setVisibility(View.INVISIBLE);

                                    }
                                }


                                RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                                        RelativeLayout.LayoutParams.MATCH_PARENT,
                                        RelativeLayout.LayoutParams.MATCH_PARENT);

                                final float finalRadius = (float) (Math.max(rootview.getWidth(), rootview.getHeight()) * 1.1);


                                if (personpreview.getParent() != null) {
                                    ((ViewGroup) personpreview.getParent()).removeView(personpreview);
                                }
                                getActivity().addContentView(personpreview, lp);

                                Rect rectf = new Rect();
                                image.getLocalVisibleRect(rectf);


                                int locationOnScreen[] = new int[2];
                                v.getLocationOnScreen(locationOnScreen);

                                // create the animator for this view (the start radius is zero)
                                Animator anim = ViewAnimationUtils.createCircularReveal(personpreview, (locationOnScreen[0]) + (rectf.width() / 2), locationOnScreen[1] + (rectf.height() / 2),
                                        0, finalRadius);
                                anim.setDuration(1000);
                                anim.setInterpolator(new FastOutSlowInInterpolator());

                                // make the view visible and start the animation
                                personpreview.setVisibility(View.VISIBLE);
                                anim.start();

                                RequestOptions options = new RequestOptions();
                                options.centerCrop();
                                options.placeholder(null);

                                ImageView imageView_personpreview_1 = personpreview.findViewById(R.id.imageView_personpreview_1);
                                Glide.with(personpreview.getContext())
                                        .load(PB_link)
                                        .apply(options)
                                        .into(imageView_personpreview_1);

                                ImageView imageView_personpreview_2 = personpreview.findViewById(R.id.imageView_personpreview_2);
                                Glide.with(personpreview.getContext())
                                        .load(PB_link2)
                                        .apply(options)
                                        .into(imageView_personpreview_2);

                                ImageView imageView_personpreview_3 = personpreview.findViewById(R.id.imageView_personpreview_3);
                                Glide.with(personpreview.getContext())
                                        .load(PB_link3)
                                        .apply(options)
                                        .into(imageView_personpreview_3);

                                ImageView imageView_personpreview_4 = personpreview.findViewById(R.id.imageView_personpreview_4);
                                Glide.with(personpreview.getContext())
                                        .load(PB_link4)
                                        .apply(options)
                                        .into(imageView_personpreview_4);


                                ImageView gender_symbol_preview = personpreview.findViewById(R.id.gender_symbol_preview);
                                switch (Integer.parseInt(gender)) {
                                    case 0:
                                        gender_symbol_preview.setImageDrawable(getResources().getDrawable(R.drawable.ic_man));
                                        break;
                                    case 1:
                                        gender_symbol_preview.setImageDrawable(getResources().getDrawable(R.drawable.ic_woman));
                                        break;
                                    case 2:
                                        gender_symbol_preview.setImageDrawable(getResources().getDrawable(R.drawable.ic_nogender));
                                        break;

                                }
                                TextView desc_preview = personpreview.findViewById(R.id.desc_preview);

                                int Age = Calendar.getInstance().get(Calendar.YEAR) - Integer.parseInt(year);

                                desc_preview.setText(Age + ", " + Country);

                                addonSnap_personpreview.setOnTouchListener(new View.OnTouchListener() {
                                    @Override
                                    public boolean onTouch(View view, MotionEvent motionEvent) {

                                        if (motionEvent.getAction() == MotionEvent.ACTION_UP) {

                                            SharedPreferences filter = getContext().getSharedPreferences("user", Context.MODE_PRIVATE);
                                            String gender = filter.getString("gender", "");

                                            if (gender.equals("")) {


                                                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                                                        getContext());

                                                // set title
                                                alertDialogBuilder.setTitle(R.string.heystranger);

                                                // set dialog message
                                                alertDialogBuilder
                                                        .setMessage(R.string.toaddthispersonpleasesignup)
                                                        .setCancelable(false)
                                                        .setPositiveButton(R.string.signup, new DialogInterface.OnClickListener() {
                                                            public void onClick(DialogInterface dialog, int id) {

                                                                SignUp nextFrag = new SignUp();
                                                                getActivity().getSupportFragmentManager().beginTransaction()
                                                                        .replace(R.id.content_main, nextFrag, "")
                                                                        .addToBackStack(null)
                                                                        .commit();

                                                                if (personpreview != null)
                                                                    personpreview.setVisibility(View.GONE);
                                                                ((ViewManager) personpreview.getParent()).removeView(personpreview);

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

                                            } else {
                                                int finalRadius = Math.max(view.getWidth(), view.getHeight());

                                                // create the animator for this view (the start radius is zero)
                                                Animator anim = ViewAnimationUtils.createCircularReveal(addedonSnap_personpreview, (int) motionEvent.getX(), (int) motionEvent.getY(),
                                                        0, finalRadius);
                                                anim.setDuration(1000);
                                                anim.setInterpolator(new FastOutSlowInInterpolator());


                                                // make the view visible and start the animation
                                                addedonSnap_personpreview.setVisibility(View.VISIBLE);
                                                anim.start();

                                                anim.addListener(new Animator.AnimatorListener() {
                                                    @Override
                                                    public void onAnimationStart(Animator animator) {

                                                    }

                                                    @Override
                                                    public void onAnimationEnd(Animator animator) {

                                                        addonSnap_personpreview.setVisibility(View.GONE);

                                                        final AsyncTask_hotPeople get_user = new AsyncTask_hotPeople(context);
                                                        get_user.execute("buy_id", id, sc_username);
                                                    }

                                                    @Override
                                                    public void onAnimationCancel(Animator animator) {

                                                    }

                                                    @Override
                                                    public void onAnimationRepeat(Animator animator) {

                                                    }
                                                });

                                            }
                                        }
                                        return false;
                                    }
                                });

                                final Boolean finalCanadd = canadd;
                                addedonSnap_personpreview.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                        if (finalCanadd) {
                                            Intent internetIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("snapchat://add/" + sc_username));
                                            context.startActivity(internetIntent);

                                            Snackbar snack = Snackbar.make(view, R.string.copied_scname, Snackbar.LENGTH_LONG);
                                            View view2 = snack.getView();
                                            TextView tv = (TextView) view2.findViewById(android.support.design.R.id.snackbar_text);
                                            tv.setTextColor(Color.WHITE);
                                            snack.show();
                                        }
                                    }
                                });

                                like_btn_preson_preview.setOnTouchListener(new View.OnTouchListener() {
                                    @Override
                                    public boolean onTouch(View view, MotionEvent motionEvent) {


                                        if (motionEvent.getAction() == MotionEvent.ACTION_UP) {

                                            SharedPreferences filter = getContext().getSharedPreferences("user", Context.MODE_PRIVATE);
                                            String gender = filter.getString("gender", "");

                                            if (gender.equals("")) {


                                                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                                                        getContext());

                                                // set title
                                                alertDialogBuilder.setTitle(R.string.heystranger);

                                                // set dialog message
                                                alertDialogBuilder
                                                        .setMessage(R.string.thispersonwouldliketoknowyou)
                                                        .setCancelable(false)
                                                        .setPositiveButton(R.string.signup, new DialogInterface.OnClickListener() {
                                                            public void onClick(DialogInterface dialog, int id) {

                                                                SignUp nextFrag = new SignUp();
                                                                getActivity().getSupportFragmentManager().beginTransaction()
                                                                        .replace(R.id.content_main, nextFrag, "")
                                                                        .addToBackStack(null)
                                                                        .commit();

                                                                if (personpreview != null)
                                                                    personpreview.setVisibility(View.GONE);
                                                                ((ViewManager) personpreview.getParent()).removeView(personpreview);


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

                                            } else {
                                                int finalRadius = Math.max(view.getWidth(), view.getHeight());

                                                // create the animator for this view (the start radius is zero)
                                                Animator anim = ViewAnimationUtils.createCircularReveal(liked_btn_preson_preview, (int) motionEvent.getX(), (int) motionEvent.getY(),
                                                        0, finalRadius);
                                                anim.setDuration(1000);
                                                anim.setInterpolator(new FastOutSlowInInterpolator());


                                                // make the view visible and start the animation
                                                liked_btn_preson_preview.setVisibility(View.VISIBLE);
                                                anim.start();

                                                anim.addListener(new Animator.AnimatorListener() {
                                                    @Override
                                                    public void onAnimationStart(Animator animator) {

                                                    }

                                                    @Override
                                                    public void onAnimationEnd(Animator animator) {

                                                        like_btn_preson_preview.setVisibility(View.GONE);

                                                        final AsyncTask_hotPeople get_user = new AsyncTask_hotPeople(context);
                                                        get_user.execute("like", id, id);
                                                    }

                                                    @Override
                                                    public void onAnimationCancel(Animator animator) {

                                                    }

                                                    @Override
                                                    public void onAnimationRepeat(Animator animator) {

                                                    }
                                                });
                                            }
                                        }
                                        return false;
                                    }
                                });

                                personpreview.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                        if (personpreview != null)
                                            personpreview.setVisibility(View.GONE);
                                        ((ViewManager) personpreview.getParent()).removeView(personpreview);


                                    }
                                });
                                ((MainActivity) getActivity()).setOnBackPressedListener(new OnBackPressedListener() {
                                    public void doBack() {

                                        if (personpreview != null)
                                            personpreview.setVisibility(View.GONE);
                                        ((ViewManager) personpreview.getParent()).removeView(personpreview);

                                    }
                                });
                            }
                        });


                        RequestOptions options = new RequestOptions();
                        options.centerCrop();
                        Glide.with(v.getContext())
                                .load(PB_link)
                                .apply(options)
                                .into(image);


                        final RelativeLayout rl;
                        if (trigger == 1) {
                            rl = new RelativeLayout(context);

                            rl.setBackgroundColor(Color.BLACK);


                            rl.setMinimumWidth(200);
                            rl.setMinimumHeight(200);


                            if (rl.getParent() != null) {
                                ((ViewGroup) rl.getParent()).removeView(rl);
                            }
                            contentlist2.addView(rl);
//                        contentlist2.addView(v);
                            //                 contentlist3.addView(v);


                            final Handler ha = new Handler();
                            ha.postDelayed(new Runnable() {

                                Boolean visible = false;

                                @Override
                                public void run() {
                                    NestedScrollView newandhotscrollview = view.findViewById(R.id.newandhotscrollview);
                                    Rect mReact = new Rect();
                                    newandhotscrollview.getHitRect(mReact);
                                    if (rl.getLocalVisibleRect(mReact)) {
// visible
                                        visible = true;

                                    } else {
// invisible
                                    }

                                    if (rl != null && visible && rl.isShown() && rl.getVisibility() != (View.GONE)) {
                                        rl.setVisibility(View.GONE);
                                        rl.setMinimumWidth(0);
                                        rl.setMinimumHeight(0);
                                        contentlist2.removeView(rl);


                                        final AsyncTask_hotPeople get_user = new AsyncTask_hotPeople(context);
                                        get_user.execute("get_users", "", "");
                                        //Toast.makeText(context, "loading more", Toast.LENGTH_SHORT).show();


                                    }

                                    ha.postDelayed(this, 10);
                                }
                            }, 100);
                        }


                        switch (fillposition) {
                            case 0:
                                fillposition = 1;
                                if (v.getParent() != null) {
                                    ((ViewGroup) v.getParent()).removeView(v);
                                }

                                contentlist1.addView(v);
                                break;
                            case 1:
                                if (v.getParent() != null) {
                                    ((ViewGroup) v.getParent()).removeView(v);
                                }

                                Space spaceright = v.findViewById(R.id.spaceright);
                                spaceright.setVisibility(View.GONE);
                                Space spaceleft = v.findViewById(R.id.spaceleft);
                                spaceleft.setVisibility(View.GONE);
                                contentlist2.addView(v);
                                fillposition = 2;
                                break;
                            case 2:
                                if (v.getParent() != null) {
                                    ((ViewGroup) v.getParent()).removeView(v);
                                }

                                contentlist3.addView(v);
                                fillposition = 0;
                                break;

                        }

                    }


                } catch (final JSONException e) {

                    Log.d("json", String.valueOf(e));


                }

                if (doafter.equals("")) {
                    //todo
                }


            }


        }


    }


}