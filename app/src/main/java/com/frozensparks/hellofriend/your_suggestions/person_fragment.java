package com.frozensparks.hellofriend.your_suggestions;

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
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.ViewManager;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.frozensparks.hellofriend.MainActivity;
import com.frozensparks.hellofriend.NewAndHot.Hot_People_fragment;
import com.frozensparks.hellofriend.R;
import com.frozensparks.hellofriend.SignUp.SignUp;
import com.frozensparks.hellofriend.Tools.OnBackPressedListener;

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

import static android.R.attr.value;
import static android.content.Context.CLIPBOARD_SERVICE;
import static android.content.Context.MODE_PRIVATE;
import static com.frozensparks.hellofriend.R.id.imageView_personpreview_1;

/**
 * Created by Emanuel Graf on 28.08.2017.
 */

public class person_fragment extends Fragment {

    int fragNum;
    String pictureUrl;
    private String SC_username;
    private int gender;
    private int value;
    private int year;
    private int id;
    private String country;

    static person_fragment init(int val, FeedItem item) {
        person_fragment truitonList = new person_fragment();

        // Supply val input as an argument.
        Bundle args = new Bundle();
        args.putInt("val", val);
        args.putString("pictureUrl", item.getPicture());
        args.putString("SC_username", item.getSC_username());
        args.putInt("gender", item.getGender());
        args.putInt("value", item.getValue());
        args.putInt("year", item.getYear());
        args.putInt("id", item.getID());
        args.putString("country", item.getCountry());

        truitonList.setArguments(args);

        return truitonList;
    }

    /**
     * Retrieving this instance's number from its arguments.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragNum = getArguments() != null ? getArguments().getInt("val") : 1;
        pictureUrl = getArguments() != null ? getArguments().getString("pictureUrl") : "0";
        SC_username = getArguments() != null ? getArguments().getString("SC_username") : "0";
        gender = getArguments() != null ? getArguments().getInt("gender") : 0;
        value = getArguments() != null ? getArguments().getInt("value") : 0;
        year = getArguments() != null ? getArguments().getInt("year") : 0;
        country = getArguments() != null ? getArguments().getString("country") : "0";
        id = getArguments() != null ? getArguments().getInt("id") : 0;


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layoutView = inflater.inflate(R.layout.personslideview,
                container, false);


        ImageView daimajia_slider_image = layoutView.findViewById(R.id.daimajia_slider_image);
        RequestOptions options = new RequestOptions();
        options.optionalFitCenter();
        options.placeholder(getResources().getDrawable(R.drawable.ic_check));
        Glide.with(this)
                .load(pictureUrl)
                .apply(options)
                .into(daimajia_slider_image);

        ImageView daimajia_slider_image_background = layoutView.findViewById(R.id.daimajia_slider_image_background);
        Glide.with(this)
                .load(pictureUrl)
                .apply(options)
                .into(daimajia_slider_image_background);
        daimajia_slider_image_background.setColorFilter(Color.WHITE);


        final ImageButton addonSnap_personpreview = layoutView.findViewById(R.id.add_btn_suggestion);
        addonSnap_personpreview.setVisibility(View.VISIBLE);
        final ImageButton addedonSnap_personpreview = layoutView.findViewById(R.id.added_btn_suggestion);
        addedonSnap_personpreview.setVisibility(View.VISIBLE);
        addedonSnap_personpreview.setVisibility(View.INVISIBLE);


        Boolean canadd = false;
        SharedPreferences prefs2 = getContext().getSharedPreferences(
                "user_storage", Context.MODE_PRIVATE);
        final String blockstring = prefs2.getString("added", "");

        if (blockstring.contains(",")) {
            String[] separated = blockstring.split(",");

            for (int j = 0; j < separated.length; j++) {
                if (separated[j].equals(SC_username)) {

                    addedonSnap_personpreview.setVisibility(View.VISIBLE);
                    addonSnap_personpreview.setVisibility(View.INVISIBLE);

                    canadd = true;

                }
            }
        } else {
            if (blockstring.equals(SC_username)) {
                addedonSnap_personpreview.setVisibility(View.VISIBLE);
                addonSnap_personpreview.setVisibility(View.INVISIBLE);

            }
        }
        final ImageButton like_btn_preson_preview = layoutView.findViewById(R.id.like_btn_suggestion);
        final ImageButton liked_btn_preson_preview = layoutView.findViewById(R.id.liked_btn_suggestion);
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


        ImageView gender_symbol_preview = layoutView.findViewById(R.id.gender_symbol_preview_suggestion);
        switch (gender) {
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
        TextView desc_preview = layoutView.findViewById(R.id.desc_preview_suggestion);

        int Age = Calendar.getInstance().get(Calendar.YEAR) - year;

        desc_preview.setText(Age + ", " + country);

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

                                        SignUp nextFrag= new SignUp();
                                        getActivity().getSupportFragmentManager().beginTransaction()
                                                .replace(R.id.content_main, nextFrag,"")
                                                .addToBackStack(null)
                                                .commit();

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
                    else{
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

                            final AsyncTask_suggestion get_user = new AsyncTask_suggestion(getContext());
                            get_user.execute("buy_id", String.valueOf(id), SC_username);

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
                    Intent internetIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("snapchat://add/" + SC_username));
                    getContext().startActivity(internetIntent);

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

                                        SignUp nextFrag= new SignUp();
                                        getActivity().getSupportFragmentManager().beginTransaction()
                                                .replace(R.id.content_main, nextFrag,"")
                                                .addToBackStack(null)
                                                .commit();

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
                    else{

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

                            final AsyncTask_suggestion get_user = new AsyncTask_suggestion(getContext());
                            get_user.execute("like", String.valueOf(id), String.valueOf(id));

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


        return layoutView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    public class AsyncTask_suggestion extends android.os.AsyncTask<String, Void, String> {

        String doafter = "";
        Context context;
        String type;
        int result_int;
        String sc_username;


        public AsyncTask_suggestion(Context activity) {


            context = activity;

        }


        @Override
        protected String doInBackground(String... params) {

            type = params[0];


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


                        Snackbar snack = Snackbar.make(getView(), R.string.copied_scname, Snackbar.LENGTH_LONG);
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

                        Snackbar snack = Snackbar.make(getView(), R.string.you_like_it, Snackbar.LENGTH_LONG);
                        View view2 = snack.getView();
                        TextView tv = (TextView) view2.findViewById(android.support.design.R.id.snackbar_text);
                        tv.setTextColor(Color.WHITE);
                        snack.show();
                    } else {

                        //todo nicht gekauft, new credits
                        //int credits = String.valueOf(result);
                    }

                }

            }
        }
    }
}
