package com.frozensparks.hellofriend.Tools;

import android.app.Notification;
import android.app.Service;

/**
 * Created by Emanuel Graf on 07.09.2017.
 */

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.util.Log;


import com.frozensparks.hellofriend.MainActivity;
import com.frozensparks.hellofriend.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    Handler h = new Handler();


    private static final String TAG = "MyFirebaseMsgService";
    static Boolean running;

    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    // [START receive_message]
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        sendNotification(remoteMessage);



        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }
    // [END receive_message]

    public static void activityPaused(){
        running=false;
    }


    private void sendNotification(RemoteMessage remoteMessage) {


        String messageBody= remoteMessage.getNotification().getTag();
        String newCredits = String.valueOf(remoteMessage.getNotification().getLink());

        Intent intent = new Intent();

        if(running){
            intent = new Intent(this, MainActivity.class);

        }
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);


        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        Notification.Builder notificationBuilder = new Notification.Builder(this);
        notificationBuilder.setStyle(new Notification.BigTextStyle(notificationBuilder));

        if(messageBody.equals("1")){


            notificationBuilder
                    .setSmallIcon(R.drawable.ic_core_heart)
                    .setContentTitle(getString(R.string.showsomelove))
                    .setContentText(getString(R.string.someonelikesyou))
                    .setAutoCancel(true)
                    .setSound(defaultSoundUri)
                    .setContentIntent(pendingIntent);

        }
        else if(messageBody.equals("2")){

            notificationBuilder
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle(getString(R.string.yay))
                    .setContentText(getString(R.string.someoneaddedyouonsnapchat))
                    .setAutoCancel(true)
                    .setSound(defaultSoundUri)
                    .setContentIntent(pendingIntent);

        }
        else if(messageBody.equals("3")){

            notificationBuilder
                    .setSmallIcon(R.drawable.ic_diamond_coin)
                    .setContentTitle(getString(R.string.guesswhosrichnow))
                    .setContentText(getString(R.string.yourcreditshavearrived))
                    .setAutoCancel(true)
                    .setSound(defaultSoundUri)
                    .setContentIntent(pendingIntent);


            SharedPreferences prefs = getApplicationContext().getSharedPreferences("user", Context.MODE_PRIVATE);
            int credits = (prefs.getInt("credits", 0));


            SharedPreferences.Editor editor1 = getApplicationContext().getSharedPreferences("user", MODE_PRIVATE).edit();
            editor1.putInt("credits", credits+ Integer.valueOf(newCredits));
            editor1.apply();


        }
        else {
            notificationBuilder
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle(getResources().getString(R.string.app_name))
                    .setContentText(messageBody)
                    .setAutoCancel(true)
                    .setSound(defaultSoundUri)
                    .setContentIntent(pendingIntent);

        }
        final NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (notificationManager != null) {
            notificationManager.notify(1 /* ID of notification */, notificationBuilder.build());
        }
            long delayInMilliseconds = 5000;
            h.postDelayed(new Runnable() {
                public void run() {
                    if(running) {

                        if (notificationManager != null) {
                            notificationManager.cancel(1);
                        }
                    }
                }
            }, delayInMilliseconds);

    }

    public static void activityActive() {
        running=true;
    }
}
