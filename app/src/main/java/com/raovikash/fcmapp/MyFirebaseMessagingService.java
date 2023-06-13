package com.raovikash.fcmapp;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

@SuppressLint("MissingFirebaseInstanceTokenRefresh")
public class MyFirebaseMessagingService extends FirebaseMessagingService {
    public static final String TAG = "MyFirebaseMessagingService";

    // data payload or notification payload
    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        Log.i(TAG, "From: " + message.getFrom());

        // check for data payload in the message
        if (message.getData().size() > 0) {
            Log.i(TAG, "Message Data Payload: " + message.getData());

        }

        // Check for notification payload in the message
        if (message.getNotification() != null) {
            Log.i(TAG, String.format("Message Notification Body: %s", message.getNotification().getBody()));
        }

        sendNotification(message.getNotification().getBody());
    }



    @Override
    public void onNewToken(@NonNull String token) {
        Log.d(TAG, String.format("onNewToken: %s", token));

        sendRegistrationToServer(token);
    }

//    private void scheduleJob() {
//        OneTimeWorkRequest workRequest = new OneTimeWorkRequest.Builder(MyWorker.class).build();
//        WorkManager.getInstance(this).beginWith(workRequest).enqueue();
//    }

    private void handleNow() {
        Log.v(TAG, "Short lived task is done");
    }

    private void sendRegistrationToServer(final String token) {
    }

    private void sendNotification(String messageBody) {
        Intent i = new Intent(this, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, i,
                PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_IMMUTABLE);
        String channelId = getString(R.string.default_notification_channel_id);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, channelId)
                .setSmallIcon(com.google.android.gms.base.R.drawable.common_google_signin_btn_icon_dark)
                .setContentTitle(getString(R.string.fcm_message))
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(channelId, "Human readable",
                    NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(notificationChannel);
        }
        notificationManager.notify(0, notificationBuilder.build());
    }

}
