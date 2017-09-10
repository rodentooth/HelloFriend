package com.frozensparks.hellofriend.likesAndDiamonds;


import android.animation.Animator;
import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Space;
import android.widget.TextView;
import android.widget.Toast;

import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.android.billingclient.api.SkuDetails;
import com.android.billingclient.api.SkuDetailsResponseListener;
import com.android.vending.billing.IInAppBillingService;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.frozensparks.hellofriend.MainActivity;
import com.frozensparks.hellofriend.NewAndHot.Hot_People_fragment;
import com.frozensparks.hellofriend.R;
import com.frozensparks.hellofriend.SignUp.SignUp;
import com.frozensparks.hellofriend.Tools.OnBackPressedListener;
import com.frozensparks.hellofriend.Tools.PackageChecker;
import com.frozensparks.hellofriend.Tools.SwipeBackLayout;
import com.frozensparks.hellofriend.Tools.util.IabHelper;
import com.frozensparks.hellofriend.Tools.util.IabResult;
import com.frozensparks.hellofriend.Tools.util.Inventory;
import com.ironsource.mediationsdk.IronSource;
import com.ironsource.mediationsdk.integration.IntegrationHelper;
import com.ironsource.mediationsdk.logger.IronSourceError;
import com.ironsource.mediationsdk.logger.IronSourceLogger;
import com.ironsource.mediationsdk.logger.LogListener;
import com.ironsource.mediationsdk.sdk.OfferwallListener;
import com.sackcentury.shinebuttonlib.ShineButton;

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
import java.util.Calendar;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.CLIPBOARD_SERVICE;
import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class DiamondFragment extends Fragment {

    SwipeRefreshLayout swipeRefreshLayout;
    int seconds;
    TextView creditamount_diamondfragment;

    private static final String TAG =
            "IAB";
    IabHelper mHelper;
    String ITEM_100 ="100diamonds";
    String ITEM_1000 ="1000diamonds";
    String ITEM_5000 ="5000diamonds";
    String ITEM ="";
    IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener;
    String dataSignature;


    public DiamondFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_diamond, container, false);

        Animation slide_up1 = AnimationUtils.loadAnimation(getContext(),
                R.anim.slide_up);
        slide_up1.setInterpolator(new FastOutSlowInInterpolator());
        slide_up1.setStartOffset(10);

        Animation slide_up2 = AnimationUtils.loadAnimation(getContext(),
                R.anim.slide_up);
        slide_up2.setInterpolator(new FastOutSlowInInterpolator());
        slide_up2.setStartOffset(50);


        Animation slide_up3 = AnimationUtils.loadAnimation(getContext(),
                R.anim.slide_up);
        slide_up3.setInterpolator(new FastOutSlowInInterpolator());
        slide_up3.setStartOffset(100);

        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);


        Animation slide_up4 = AnimationUtils.loadAnimation(getContext(),
                R.anim.slide_up);
        slide_up4.setInterpolator(new FastOutSlowInInterpolator());
        slide_up4.setStartOffset(150);
        slide_up4.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


        ImageView big_dia_logo_dias = view.findViewById(R.id.big_dia_logo_dias);
        big_dia_logo_dias.startAnimation(slide_up1);


        SharedPreferences prefs = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        int credits = (prefs.getInt("credits", 0));

        creditamount_diamondfragment = view.findViewById(R.id.creditamount_diamondfragment);
        creditamount_diamondfragment.startAnimation(slide_up2);
        creditamount_diamondfragment.setText(String.valueOf(credits));

        LinearLayout free_dias_layout = view.findViewById(R.id.free_dias_layout);
        free_dias_layout.startAnimation(slide_up3);

        LinearLayout paid_dias_layout = view.findViewById(R.id.paid_dias_layout);
        paid_dias_layout.startAnimation(slide_up4);


        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);

                final DiamondFragment.AsyncTask_hotPeople get_user = new DiamondFragment.AsyncTask_hotPeople(getContext());
                get_user.execute("get_credits", "", "");

            }
        });
        seconds = ((int) System.currentTimeMillis() / 1000);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                if ((((int) System.currentTimeMillis() / 1000) - seconds) < 7) {
                    final Handler haupload = new Handler();
                    haupload.postDelayed(new Runnable() {


                        @Override
                        public void run() {

                            swipeRefreshLayout.setRefreshing(false);


                        }
                    }, 2000);

                } else {

                    seconds = ((int) System.currentTimeMillis() / 1000);

                    final DiamondFragment.AsyncTask_hotPeople get_user = new DiamondFragment.AsyncTask_hotPeople(getContext());
                    get_user.execute("get_credits", "", "");
                }
            }
        });


        LinearLayout ig_follow_layout = view.findViewById(R.id.ig_follow_layout);

        SharedPreferences prefs2 = getActivity().getSharedPreferences("ratenlike", Context.MODE_PRIVATE);
        boolean ig = (prefs2.getBoolean("ig", false));
        if(ig){
            ig_follow_layout.setVisibility(View.GONE);
        }
        final Boolean[] tüdü = {false};

        Button follow_us_on_instagram_btn = view.findViewById(R.id.follow_us_on_instagram_btn);
        follow_us_on_instagram_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Uri uri = Uri.parse("http://instagram.com/_u/frozensparks_official/");
                Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);

                likeIng.setPackage("com.instagram.android");



                try {
                    startActivity(likeIng);
                } catch (ActivityNotFoundException e) {
                    //               startActivity(new Intent(Intent.ACTION_VIEW,
                    //                       Uri.parse("http://instagram.com/frozensparks_official")));
                    Toast.makeText(getContext(), "install instagram", Toast.LENGTH_SHORT).show();
                }
                if(!tüdü[0]){


                    final DiamondFragment.AsyncTask_hotPeople get_user = new DiamondFragment.AsyncTask_hotPeople(getContext());
                    get_user.execute("likenrate", "", "");

                }

                tüdü[0] =true;

                SharedPreferences.Editor editor1 = getActivity().getSharedPreferences("ratenlike", MODE_PRIVATE).edit();
                editor1.putBoolean("ig", true);
                editor1.apply();
            }
        });


        SharedPreferences filter = getActivity().getSharedPreferences("user", MODE_PRIVATE);
        int id = filter.getInt("userid", 0);


        IronSource.init(getActivity(), "66fe9ded", IronSource.AD_UNIT.OFFERWALL, IronSource.AD_UNIT.INTERSTITIAL, IronSource.AD_UNIT.REWARDED_VIDEO, IronSource.AD_UNIT.BANNER);

        IronSource.setUserId(String.valueOf(id));
        IntegrationHelper.validateIntegration(getActivity());



        IronSource.setLogListener(new LogListener() {
            @Override
            public void onLog(IronSourceLogger.IronSourceTag ironSourceTag, String s, int i) {

            }
        });


        IronSource.setOfferwallListener(new OfferwallListener() {
            /**
             * Invoked when there is a change in the Offerwall availability status.
             * @param - available - value will change to YES when Offerwall are available.
             * You can then show the offerwall by calling showOfferwall(). Value will *change to NO when Offerwall isn't available.
             */
            @Override
            public void onOfferwallAvailable(boolean isAvailable) {

                Log.d("irnsrcofwallavi", String.valueOf(isAvailable));

            }
            /**
             * Invoked when the Offerwall successfully loads for the user, after calling the 'showOfferwall' method
             */
            @Override
            public void onOfferwallOpened() {
            }


            /**
             * Invoked when the method 'showOfferWall' is called and the OfferWall fails to load.
             * @param error - A IronSourceError Object which represents the reason of 'showOfferwall' failure.
             */
            @Override
            public void onOfferwallShowFailed(IronSourceError error) {

                Log.d("irnsrcofwallerr", String.valueOf(error));
            }
            /**
             * Invoked each time the user completes an Offer.
             * Award the user with the credit amount corresponding to the value of the *‘credits’ parameter.
             * @param credits - The number of credits the user has earned.
             * @param totalCredits - The total number of credits ever earned by the user.
             * @param totalCreditsFlag - In some cases, we won’t be able to provide the exact
             * amount of credits since the last event (specifically if the user clears
             * the app’s data). In this case the ‘credits’ will be equal to the ‘totalCredits’, and this flag will be ‘true’.
             * @return boolean - true if you received the callback and rewarded the user, otherwise false.
             */
            @Override
            public boolean onOfferwallAdCredited(int credits, int totalCredits, boolean totalCreditsFlag) {
                return false;
            }
            /**
             * Invoked when the method 'getOfferWallCredits' fails to retrieve
             * the user's credit balance info.
             * @param error - A IronSourceError object which represents the reason of 'getOfferwallCredits' failure.
             * If using client-side callbacks to reward users, it is mandatory to return true on this event
             */
            @Override
            public void onGetOfferwallCreditsFailed(IronSourceError error) {
            }
            /**
             * Invoked when the user is about to return to the application after closing
             * the Offerwall.
             */
            @Override
            public void onOfferwallClosed() {
            }
        });


        Button offerwall_button = view.findViewById(R.id.offerwall_button);
        offerwall_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IronSource.showOfferwall();
            }
        });





        Button buy100 = view.findViewById(R.id.buy100);
        buy100.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    ITEM = ITEM_100;

                    mHelper.launchPurchaseFlow(getActivity(), ITEM_100, 10001,
                            mPurchaseFinishedListener, "mypurchasetoken");
                } catch (IabHelper.IabAsyncInProgressException e) {
                    e.printStackTrace();
                }
            }
        });
        Button buy1000 = view.findViewById(R.id.buy1000);
        buy1000.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    ITEM = ITEM_1000;
                    mHelper.launchPurchaseFlow(getActivity(), ITEM_1000, 10001,
                            mPurchaseFinishedListener, "mypurchasetoken");
                } catch (IabHelper.IabAsyncInProgressException e) {
                    e.printStackTrace();
                }
            }
        });
        Button buy5000 = view.findViewById(R.id.buy5000);
        buy5000.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    ITEM = ITEM_5000;

                    mHelper.launchPurchaseFlow(getActivity(), ITEM_5000, 10001,
                            mPurchaseFinishedListener, "mypurchasetoken");
                } catch (IabHelper.IabAsyncInProgressException e) {
                    e.printStackTrace();
                }
            }
        });

        String base64EncodedPublicKey =
                "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAiA1HKDyNlXXHeYQVvn+BbwHN8PqrsKeTU+NwvjuqSv9ghnjeGZFUMJwlmr3xbEMFk1bVBYcodMzu4Yfqbsw6D+ajLLy0LiitOFwcW3UaifAoyugvxAWDcA5a7jXj562EnNN5R38jL8HHhY212zovgghae9DPt5c+Rn3swi9GkzdWgKDeLSPunqAVd5tlJBGiUk9HchuxrmylJV7vkz655EJlyCZ/te99+9bG20dTD5sSUTIMSrcUET17iBDaJg2nY3gsx2itwTPFr1GP76iQopF6EqiNIUkq9qnsSIOjHPnDsRurScRD2bYhkpeTw6iDkHhqlUQ3u6eXbZ2XAETYvQIDAQAB";

        mHelper = new IabHelper(getActivity(), base64EncodedPublicKey);

        mHelper.startSetup(new
                                   IabHelper.OnIabSetupFinishedListener() {
                                       public void onIabSetupFinished(IabResult result)
                                       {
                                           if (!result.isSuccess()) {
                                               Log.d(TAG, "In-app Billing setup failed: " +
                                                       result);
                                           } else {
                                               Log.d(TAG, "In-app Billing is set up OK");
                                           }
                                       }
                                   });






         mPurchaseFinishedListener
                = new IabHelper.OnIabPurchaseFinishedListener() {
            public void onIabPurchaseFinished(IabResult result,
                                              com.frozensparks.hellofriend.Tools.util.Purchase purchase)
            {
                if (result.isFailure()) {
                    // Handle error
                    return;
                }
                else {
                    consumeItem();
                }

            }
        };



        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                    Intent data)
    {
        if (!mHelper.handleActivityResult(requestCode,
                resultCode, data)) {
            String purchaseData = data.getStringExtra("INAPP_PURCHASE_DATA");
             dataSignature = data.getStringExtra("INAPP_DATA_SIGNATURE");

            super.onActivityResult(requestCode, resultCode, data);

        }
    }

    public void consumeItem() {
        try {
            mHelper.queryInventoryAsync(mReceivedInventoryListener);


            final DiamondFragment.AsyncTask_hotPeople get_user = new DiamondFragment.AsyncTask_hotPeople(getContext());
            get_user.execute("validate_purchase", "", "");

        } catch (IabHelper.IabAsyncInProgressException e) {
            e.printStackTrace();
        }
    }

    IabHelper.QueryInventoryFinishedListener mReceivedInventoryListener
            = new IabHelper.QueryInventoryFinishedListener() {
        public void onQueryInventoryFinished(IabResult result,
                                             Inventory inventory) {

            if (result.isFailure()) {

                //todo
                Toast.makeText(getContext(), "failure", Toast.LENGTH_SHORT).show();
                // Handle failure
            } else {
                try {
                    //todo item variabel
                    mHelper.consumeAsync(inventory.getPurchase(ITEM),
                            mConsumeFinishedListener);
                } catch (IabHelper.IabAsyncInProgressException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    IabHelper.OnConsumeFinishedListener mConsumeFinishedListener =
            new IabHelper.OnConsumeFinishedListener() {
                public void onConsumeFinished(com.frozensparks.hellofriend.Tools.util.Purchase purchase,
                                              IabResult result) {

                    if (result.isSuccess()) {

                        //todo
                        Toast.makeText(getContext(), "consume success", Toast.LENGTH_SHORT).show();

                    } else {


                        //todo
                        Toast.makeText(getContext(), "consume error", Toast.LENGTH_SHORT).show();
                    }
                }
            };

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mHelper != null) try {
            mHelper.dispose();
        } catch (IabHelper.IabAsyncInProgressException e) {
            e.printStackTrace();
        }
        mHelper = null;
    }



    public class AsyncTask_hotPeople extends android.os.AsyncTask<String, Void, String> {

        Context context;
        String type;


        public AsyncTask_hotPeople(Context activity) {


            context = activity;

        }


        @Override
        protected String doInBackground(String... params) {

            type = params[0];


            if (type.equals("get_credits")) {
                try {

                    String URL = "http://snapchat.frozensparks.com/get_credits.php";


                    SharedPreferences filter = context.getSharedPreferences("user", MODE_PRIVATE);
                    int id = filter.getInt("userid", 0);


                    java.net.URL url = new URL(URL);
                    HttpURLConnection httpurlconn = (HttpURLConnection) url.openConnection();
                    httpurlconn.setRequestMethod("POST");
                    httpurlconn.setDoOutput(true);
                    httpurlconn.setDoInput(true);
                    OutputStream outputStream = httpurlconn.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    String post_data = URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(id), "UTF-8");
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
            if (type.equals("validate_purchase")) {
                try {

                    String URL = "http://snapchat.frozensparks.com/validate_purchase.php";


                    SharedPreferences filter = context.getSharedPreferences("user", MODE_PRIVATE);
                    int id = filter.getInt("userid", 0);


                    java.net.URL url = new URL(URL);
                    HttpURLConnection httpurlconn = (HttpURLConnection) url.openConnection();
                    httpurlconn.setRequestMethod("POST");
                    httpurlconn.setDoOutput(true);
                    httpurlconn.setDoInput(true);
                    OutputStream outputStream = httpurlconn.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    String post_data = URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(id), "UTF-8")+"&"+
                            URLEncoder.encode("dataSignature", "UTF-8") + "=" + URLEncoder.encode(dataSignature, "UTF-8");
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

            if (type.equals("likenrate")) {
                try {

                    String URL = "http://snapchat.frozensparks.com/likenrate.php";


                    SharedPreferences filter = context.getSharedPreferences("user", MODE_PRIVATE);
                    int id = filter.getInt("userid", 0);


                    java.net.URL url = new URL(URL);
                    HttpURLConnection httpurlconn = (HttpURLConnection) url.openConnection();
                    httpurlconn.setRequestMethod("POST");
                    httpurlconn.setDoOutput(true);
                    httpurlconn.setDoInput(true);
                    OutputStream outputStream = httpurlconn.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    String post_data = URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(id), "UTF-8");
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


                if (type.equals("get_credits")) {

                    creditamount_diamondfragment.setText(String.valueOf(result));

                    SharedPreferences.Editor editor1 = getActivity().getSharedPreferences("user", MODE_PRIVATE).edit();
                    editor1.putInt("credits", Integer.valueOf(result));
                    editor1.apply();

                    swipeRefreshLayout.setRefreshing(false);


                }


            }


        }


    }
}
