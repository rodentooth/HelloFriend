package com.frozensparks.hellofriend.Tools;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

/**
 * Created by Emanuel Graf on 23.08.2017.
 */

public class AsyncTask extends android.os.AsyncTask<String, Void, String> {

    String doafter ="";
    Context context;
    String type;
    int result_int;


    public AsyncTask(Context activity) {


        context = activity;

    }


    @Override
    protected String doInBackground(String... params) {

        type = params[0];
        doafter = params[1];


        if (type.equals("")) {
            try {
                String URL = "http://makemoney.frozensparks.com/collect.php";

                String phonenr = params[3];
                String googleID = params[2];




                URL url = new URL(URL);
                HttpURLConnection httpurlconn = (HttpURLConnection) url.openConnection();
                httpurlconn.setRequestMethod("POST");
                httpurlconn.setDoOutput(true);
                httpurlconn.setDoInput(true);
                OutputStream outputStream = httpurlconn.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("googleID", "UTF-8") + "=" + URLEncoder.encode(googleID, "UTF-8") +"&"+
                        URLEncoder.encode("phnr", "UTF-8") + "=" + URLEncoder.encode(phonenr, "UTF-8");
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
            String result_spaceless = result.replace(" ", "");
            result_int = Integer.parseInt(result);
            Log.d("AsyncTask says",result);
        }

    }

}
