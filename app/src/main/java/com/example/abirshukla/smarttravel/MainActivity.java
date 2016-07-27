package com.example.abirshukla.smarttravel;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String ad = TripInfo.getAddress();
      Uri gmmIntentUri = Uri.parse("google.navigation:q="+ad);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.cancel("RESUME",1234);

        Intent foodIntent = new Intent(MainActivity.this, Food.class);

        PendingIntent pIntentF = PendingIntent.getActivity(MainActivity.this, 0, foodIntent, 0);


        Intent gasIntent = new Intent(MainActivity.this, Gas.class);

        PendingIntent pIntentG = PendingIntent.getActivity(MainActivity.this, 0, gasIntent, 0);

        Notification mNotification = new Notification.Builder(this)

                .setContentTitle("Make A Stop")

                .setContentText("Grab a Bite, or Get Gas!")

                .setSmallIcon(R.drawable.icon)

                .setContentIntent(pIntentF)

                .addAction(R.drawable.gas, "Gas", pIntentG)

                .addAction(R.drawable.food5, "Food", pIntentF)
                .setPriority(Notification.PRIORITY_MAX)
                .build();
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify("GAS",1234, mNotification);

    }
    @Override
    protected void onDestroy() {
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.cancelAll();
        mNotificationManager.cancel("GAS",1234);
        mNotificationManager.cancel("RESUME",1234);
        super.onDestroy();
    }
}
