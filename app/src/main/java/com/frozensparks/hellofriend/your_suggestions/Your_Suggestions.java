package com.frozensparks.hellofriend.your_suggestions;

import android.content.Context;
import android.content.SharedPreferences;
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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
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

public class Your_Suggestions extends Fragment {

    private static Context context;
    int position = 0;
    int totalrows;
    View view;
    RecyclerView mRecyclerView;
    static int load_more =0;
    private static List<FeedItem> feedsList;
    LinearLayoutManager llm;

    //private ViewPager mPager;
    //private MyPagerAdapter mAdapter;

    static final int ITEMS = 10;
    MyAdapter mAdapter;
    ViewPager mPager;






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

        final AsyncTask_Your_suggestions get_user = new AsyncTask_Your_suggestions(getContext());
        get_user.execute("get_users","","");


        mAdapter = new MyAdapter(getActivity().getSupportFragmentManager());
        mPager = (ViewPager) view.findViewById(R.id.pager);
        mPager.setAdapter(mAdapter);


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


                ha.postDelayed(this, 10);
            }
        }, 100);

        return view;


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
            if(feedsList!=null) {
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
                return 1;
            }
        }


        }






/*
    private void setAnimation(View view, int position) {
        // If the bound view wasn't previously displayed on screen, it's animated

        int MAX_ROTATION =90;

        ImageButton button2 = view.findViewById(R.id.button3);
        button2.setPivotY((int)(position < 0 ? 2000 : view.getWidth()));
        final float rotation2 = +position * MAX_ROTATION;
        button2.setPivotX(position < 0 ? 1 : view.getWidth());
        button2.setRotation(rotation2);

        RelativeLayout heart_shadow = view.findViewById(R.id.heart_shadow);
        heart_shadow.setPivotY((int)(position < 0 ? 2000 : view.getWidth()));
        final float heart_shadow1= +position * MAX_ROTATION;
        heart_shadow.setPivotX(position < 0 ? 1 : view.getWidth());
        heart_shadow.setRotation(heart_shadow1);

        ImageButton button3 = view.findViewById(R.id.button2);
        button3.setPivotY(position < 0 ? -view.getWidth()*position*100 : view.getWidth()*position*10);
        final float rotation5 = +position *5;//* MAX_ROTATION;
        button3.setPivotX(view.getWidth()/2);
        //button3.setPivotX(position < 0 ? 0 : view.getWidth());
        // button3.setScaleX( position);
        button3.setRotation(rotation5);
        button3.setElevation(position < 0 ? 0 : 1);
        button3.setAlpha(position < 0 ? 1f + position : 1f - position);





        RelativeLayout imageView1 = view.findViewById(R.id.innerRL);
        imageView1.setPivotY(2500);
        final float rotation3 = +position * MAX_ROTATION;
        imageView1.setPivotX(view.getWidth()/2);
        imageView1.setRotation(rotation3);

        RelativeLayout imageView2 = view.findViewById(R.id.outerRL);
        imageView2.setPivotY(2000);
        final float rotation4 = +position * MAX_ROTATION;
        imageView2.setPivotX(view.getWidth()/2);
        imageView2.setRotation(rotation4);
    }




    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

@Override
public void onStop() {
    super.onStop();
}
    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
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
                    position=position +10;

                    JSONObject jsonObj = new JSONObject(result);

                    totalrows= Integer.parseInt(jsonObj.getString("AMOUNT"));

                    Log.d("json", "total rows: " +Integer.toString(totalrows));

                    // Getting JSON Array node

                    if(totalrows==0){

                        //todo no data


                    }
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
                        feedsList.add(item);
                        //FeedItem FeedItem=item;

                      //  mSwipeView.addView(new person_card(getContext(), FeedItem, mSwipeView));

                        // Restore state
                       // mRecyclerView.getLayoutManager().onRestoreInstanceState(recyclerViewState);


                    }
                    mPager.getAdapter().notifyDataSetChanged();

                } catch (final JSONException e) {

                    Log.d("json", String.valueOf(e));


                }


            }

            if (doafter.equals("")) {
                //todo
            }



        }

    }



}
