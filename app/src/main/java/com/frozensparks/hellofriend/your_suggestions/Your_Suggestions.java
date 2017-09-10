package com.frozensparks.hellofriend.your_suggestions;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;


import com.frozensparks.hellofriend.NewAndHot.New_People_fragment;
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
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Your_Suggestions extends Fragment {

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
    RelativeLayout empty_state_your_suggestions;
    int bgimagenr = ThreadLocalRandom.current().nextInt(0  , 7 + 1);

    Boolean play=true;





    public Your_Suggestions() {
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

        view= inflater.inflate(R.layout.fragment_your__suggestions, container, false);
        context = getContext();

        feedsList = new ArrayList<>();

        final AsyncTask_Your_suggestions get_user = new AsyncTask_Your_suggestions(getContext());
        get_user.execute("get_users","","");


        empty_state_your_suggestions = view.findViewById(R.id.empty_state_your_suggestions);
        empty_state_your_suggestions.setVisibility(View.VISIBLE);
        empty_state_your_suggestions.setVisibility(View.INVISIBLE);

        mAdapter = new MyAdapter(getActivity().getSupportFragmentManager());
        mPager =  view.findViewById(R.id.pager);
        mPager.setAdapter(mAdapter);

        mPager.setPageTransformer(true, new FragmentTransformer());

        mPager.getAdapter().notifyDataSetChanged();

        final ViewPager bg1 =  view.findViewById(R.id.bgpager1);
        final ViewPager bg2 =  view.findViewById(R.id.bgpager2);

        final Handler ha2 = new Handler();


        ha2.postDelayed(new Runnable() {


            @Override
            public void run() {

                if(play && empty_state_your_suggestions.getVisibility() == View.INVISIBLE) {
                    ImageView background_suggestions = view.findViewById(R.id.background_suggestions);
                    int icon = (R.mipmap.bg_suggestions_1);
                    int i1 = 0;
                    int i2 = 1;
                    switch (bgimagenr) {
                        case 0:
                            icon = (R.mipmap.bg_suggestions_2);
                            bgimagenr = 1;
                            i1 = 1;
                            i2 = 0;
                            break;
                        case 1:
                            icon = (R.mipmap.bg_suggestions_3);
                            bgimagenr = 2;
                            break;
                        case 2:
                            icon = (R.mipmap.bg_suggestions_4);
                            bgimagenr = 3;
                            i1 = 1;
                            i2 = 0;
                            break;
                        case 3:
                            icon = (R.mipmap.bg_suggestions_5);
                            bgimagenr = 4;
                            break;
                        case 4:
                            icon = (R.mipmap.bg_suggestions_6);
                            bgimagenr = 5;
                            i1 = 1;
                            i2 = 0;
                            break;
                        case 5:
                            icon = (R.mipmap.bg_suggestions_7);
                            bgimagenr = 6;
                            break;
                        case 6:
                            icon = (R.mipmap.bg_suggestions_8);
                            bgimagenr = 7;
                            i1 = 1;
                            i2 = 0;
                            break;
                        case 7:
                            icon = (R.mipmap.bg_suggestions_1);
                            bgimagenr = 0;
                            break;
                    }

                    ImageViewAnimatedChange(getActivity(), bg1, icon, i1);
                    ImageViewAnimatedChange(getActivity(), bg2, icon, i2);


                    ha2.postDelayed(this, 24000);
                }
            }
        }, 100);

        //load more user based on slideview position
        final Handler ha=new Handler();
        ha.postDelayed(new Runnable() {


            @Override
            public void run() {

                if(load_more==1){

                    load_more = 0;
                    final AsyncTask_Your_suggestions get_user = new AsyncTask_Your_suggestions(context);
                    get_user.execute("get_users", "", "");

                }
                mAdapter.notifyDataSetChanged();


                ha.postDelayed(this, 1);
            }
        }, 100);

        return view;


    }

    public static void ImageViewAnimatedChange(Context c, final ViewPager v, final int new_image, final int i) {
        final Animation anim;
        if (i==1){
            anim = AnimationUtils.loadAnimation(c, android.R.anim.fade_out);
        }else{
            anim  = AnimationUtils.loadAnimation(c, android.R.anim.fade_in);

        }
        anim.setDuration(12000);
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

            FeedItem item = new FeedItem();
            item.setPicture("");
            item.setCheck(0);
            item.setSC_username("");
            if(feedsList!=null && feedsList.size()!=0) {
                item = feedsList.get(position);
                if(item.getCheck()==1){
                    load_more = 1;

                }
            }

            return person_fragment.init(position,item);
        }


        @Override
        public int getCount() {
            if(feedsList!=null) {
                return feedsList.size();
            }
            else{
                return 0;
            }
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


public static void load_user(){


    load_more=1;

    }





    public class AsyncTask_Your_suggestions extends android.os.AsyncTask<String, Void, String> {

        String doafter ="";
        private Context context;
        String type;
        int result_int;
        private Context mContext;
        String gender  ;
        int minage     ;
        int maxage     ;
        String country ;

        public AsyncTask_Your_suggestions(Context activity) {


            this.context = activity;
            SharedPreferences filter = activity.getSharedPreferences("filter", Context.MODE_PRIVATE);
             gender   = filter.getString("gender", "");
             minage      = filter.getInt("minage", 13);
             maxage      = filter.getInt("maxage", 99);
             country  = filter.getString("country", "");

        }


        @Override
        protected String doInBackground(String... params) {

            type = params[0];
            doafter = params[1];


            if (type.equals("get_users")) {
                try {
                    String URL = "http://snapchat.frozensparks.com/your_suggestions.php";

                    java.net.URL url = new URL(URL);
                    HttpURLConnection httpurlconn = (HttpURLConnection) url.openConnection();
                    httpurlconn.setRequestMethod("POST");
                    httpurlconn.setDoOutput(true);
                    httpurlconn.setDoInput(true);
                    OutputStream outputStream = httpurlconn.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    String post_data = URLEncoder.encode("gender", "UTF-8") + "=" + URLEncoder.encode(gender, "UTF-8") +"&"+
                            URLEncoder.encode("minage", "UTF-8") + "=" + URLEncoder.encode(Integer.toString(minage), "UTF-8") +"&"+
                            URLEncoder.encode("maxage", "UTF-8") + "=" + URLEncoder.encode(Integer.toString(maxage), "UTF-8") +"&"+
                            URLEncoder.encode("country", "UTF-8") + "=" + URLEncoder.encode(country, "UTF-8") +"&"+
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
                Log.d("AsyncTask says",result);
            }

            if (type.equals("get_users")){


                try {

                    JSONObject jsonObj = new JSONObject(result);

                    totalrows= Integer.parseInt(jsonObj.getString("AMOUNT"));

                    Log.d("json", "total rows: " +Integer.toString(totalrows));

                    // Getting JSON Array node

                    if(totalrows==0&&position==0){
                        empty_state_your_suggestions.setVisibility(View.VISIBLE);
                    }
                    else{
                        empty_state_your_suggestions.setVisibility(View.GONE);
                    }

                    position=position +10;


                    JSONArray toplevels = jsonObj.getJSONArray("USERS");


                    if (feedsList==null) {
                        feedsList = new ArrayList<>();

                    }

                    // Save state
                   /* Parcelable recyclerViewState;
                    recyclerViewState = mRecyclerView.getLayoutManager().onSaveInstanceState();

                    adapter = new RecycleViewAdapter_your_suggestions(Your_Suggestions.context, feedsList);
                    mRecyclerView.setAdapter(adapter);
*/

                    SharedPreferences prefs2 = context.getSharedPreferences(
                            "user_storage", Context.MODE_PRIVATE);
                    final String blockstring = prefs2.getString("flag", "0");

                    // looping through All Contacts
                    for (int i = 0; i < toplevels.length(); i++) {
                        JSONObject c = toplevels.getJSONObject(i);


                        final String id = c.getString("id");
                        final String gender = c.getString("gender");
                        final String sc_username = c.getString("sc_username");
                        final String year = c.getString("year");
                        final String value = c.getString("value");
                        final String country = c.getString("country");
                        final int trigger = Integer.valueOf(c.getString("trigger"));



                        final String PB_link = "http://snapchat.frozensparks.com/user/"+id+"/pictures/1.jpg";






                        FeedItem item = new FeedItem();
                        item.setPicture(PB_link);

                        item.setGender(gender);
                        item.setYear(year);
                        item.setValue(value);
                        item.setCountry(country);
                        item.setCheck(trigger);
                        item.setSC_username(sc_username);
                        item.setID(id);

                        int added=0;
                        if (blockstring.contains(",")) {
                            String[] separated = blockstring.split(",");

                            for (int j = 0; j < separated.length; j++) {
                                if (!separated[j].equals(id)) {
                                    if(added==0){
                                        feedsList.add(item);
                                        added=1;
                                    }

                                }else{
                                    feedsList.remove(item);
                                    break;

                                }
                            }
                        }
                        else{
                            feedsList.add(item);
                        }


                        mAdapter.notifyDataSetChanged();

                        //FeedItem FeedItem=item;

                      //  mSwipeView.addView(new person_card(getContext(), FeedItem, mSwipeView));

                        // Restore state
                       // mRecyclerView.getLayoutManager().onRestoreInstanceState(recyclerViewState);


                    }
                    if(feedsList.size()==0){


                        FeedItem item = new FeedItem();
                        item.setPicture("0");

                        item.setGender("0");
                        item.setYear("0");
                        item.setValue("0");
                        item.setCountry("0");
                        item.setCheck(0);
                        item.setSC_username("0");
                        item.setID("0");
                        feedsList.add(item);
                        mAdapter.notifyDataSetChanged();

                    }else{
                        mPager.getAdapter().notifyDataSetChanged();

                    }

                } catch (final JSONException e) {

                    Log.d("json", String.valueOf(e));


                }


            }




        }

    }



}
