package com.frozensparks.hellofriend.your_suggestions;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Space;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
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


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Your_Suggestions.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Your_Suggestions#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Your_Suggestions extends Fragment {

    private static Context context;
    int position = 0;
    int totalrows;
    View view;
    RecyclerView mRecyclerView;
    static int load_more =0;
    private List<FeedItem> feedsList;
    private RecycleViewAdapter_your_suggestions adapter;
    LinearLayoutManager llm;





    SliderLayout sliderShow;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Your_Suggestions() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Your_Suggestions.
     */
    // TODO: Rename and change types and number of parameters
    public static Your_Suggestions newInstance(String param1, String param2) {
        Your_Suggestions fragment = new Your_Suggestions();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) getActivity().findViewById(R.id.toolbar);
        AppBarLayout.LayoutParams params =
                (AppBarLayout.LayoutParams) toolbar.getLayoutParams();
        params.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SNAP);    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view= inflater.inflate(R.layout.fragment_your__suggestions, container, false);
/*
         sliderShow = (SliderLayout) view.findViewById(R.id.slider);

        sliderShow.setCustomIndicator((PagerIndicator) view.findViewById(R.id.custom_indicator));

        sliderShow.setPagerTransformer(false,new SlideTransformer());
        sliderShow.stopAutoCycle();
*/
        mRecyclerView = view.findViewById(R.id.recyclerView_your_suggestions);
        llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerView.setLayoutManager(llm);


        final Handler han=new Handler();
        han.postDelayed(new Runnable() {


            @Override
            public void run() {
                int firstItemPos=llm.findFirstVisibleItemPosition();
                View firstItemView=llm.findViewByPosition(firstItemPos);

                if(firstItemView !=null) {
                    setAnimation(firstItemView, (int) Math.abs(firstItemView.getY()));

                    final Toast toast = Toast.makeText(context, Float.toString(Math.abs(firstItemView.getY())), Toast.LENGTH_SHORT);
                   // toast.show();

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            toast.cancel();
                        }
                    }, 10);
                }
                han.postDelayed(this, 10);
            }
        }, 10);


        SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(mRecyclerView);


        final AsyncTask_Your_suggestions get_user = new AsyncTask_Your_suggestions(getContext());

        context = getContext();

        get_user.execute("get_users","","");

        final Handler ha=new Handler();
        ha.postDelayed(new Runnable() {


            @Override
            public void run() {

                if(load_more==1){

                    load_more = 0;
                    final AsyncTask_Your_suggestions get_user = new AsyncTask_Your_suggestions(getContext());
                    get_user.execute("get_users", "", "");
                    //Toast.makeText(getContext(), "loadingMoar", Toast.LENGTH_SHORT).show();

                }


                ha.postDelayed(this, 3000);
            }
        }, 3000);


        // Inflate the layout for this fragment
        return view;


    }


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
/*
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
*/
@Override
public void onStop() {
    if(sliderShow!=null)
    sliderShow.stopAutoCycle();
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
        Context context;
        String type;
        int result_int;


        public AsyncTask_Your_suggestions(Context activity) {


            context = activity;

        }


        @Override
        protected String doInBackground(String... params) {

            type = params[0];
            doafter = params[1];


            if (type.equals("get_users")) {
                try {
                    String URL = "http://snapchat.frozensparks.com/your_suggestions.php";


                    SharedPreferences filter = context.getSharedPreferences("filter", Context.MODE_PRIVATE);
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
                    Parcelable recyclerViewState;
                    recyclerViewState = mRecyclerView.getLayoutManager().onSaveInstanceState();

                    adapter = new RecycleViewAdapter_your_suggestions(Your_Suggestions.context, feedsList);
                    mRecyclerView.setAdapter(adapter);


                    // looping through All Contacts
                    for (int i = 0; i < toplevels.length(); i++) {
                        JSONObject c = toplevels.getJSONObject(i);


                        final String id = c.getString("id");

                        final String gender = c.getString("gender");
                        final String sc_username = c.getString("sc_username");
                        final String year = c.getString("year");
                        final String value = c.getString("value");
                        final int trigger = Integer.valueOf(c.getString("trigger"));



                        final String PB_link = "http://snapchat.frozensparks.com/user/"+id+"/pictures/1.jpg";





/*
                        final View v = getActivity().getLayoutInflater().inflate(R.layout.personslideview, null);

                        final ImageView target = (ImageView) v.findViewById(R.id.daimajia_slider_image);
                        final ImageView target_shadow = v.findViewById(R.id.imageView4);
                        //TextView description = (TextView)v.findViewById(R.id.description);
                        ImageButton button = (ImageButton) v.findViewById(R.id.button2);
                        ImageButton button3 = (ImageButton) v.findViewById(R.id.button3);
                        final RelativeLayout load_more_check = v.findViewById(R.id.load_more_check);




                        //final String PB_link = "http://snapchat.frozensparks.com/user/1/pictures/1.jpg";

                        suggestion_person_view demoSlider = new suggestion_person_view(getActivity());
                        demoSlider.images(PB_link);
                        demoSlider.image(PB_link);

                        if(trigger.equals("1")){
                            demoSlider.trigger();
                        }

                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Toast.makeText(getActivity(), sc_username, Toast.LENGTH_SHORT).show();

                            }
                        });

                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Toast.makeText(getActivity(), "2button"+sc_username, Toast.LENGTH_SHORT).show();

                            }
                        });

                        recyclerView_your_suggestions.addView(v);

                        //sliderShow.addSlider(demoSlider);

*/
                        FeedItem item = new FeedItem();
                        item.setPicture(PB_link);
                        item.setCheck(trigger);
                        item.setSC_username(sc_username);
                        feedsList.add(item);

                        // Restore state
                        mRecyclerView.getLayoutManager().onRestoreInstanceState(recyclerViewState);


                    }
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
