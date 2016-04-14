package com.example.chentingshuo.mynotification;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity implements View.OnClickListener {

    private String EXTRA_EVENT_ID = "EXTRA_EVENT_ID";
    private int eventId = 1;
    private String eventTitle = "eventTitle";
    private String eventLocation = "eventLocation";
    private Button notify;

    private int notificationId = 001;
    private NotificationCompat.Builder notificationBuilder;
    private NotificationManagerCompat notificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        notify = (Button) findViewById(R.id.notify);
        notify.setOnClickListener(this);

        // Build intent for notification content
        Intent viewIntent = new Intent(this, ViewEventActivity.class);
        viewIntent.putExtra(EXTRA_EVENT_ID, eventId);
        PendingIntent viewPendingIntent =
                PendingIntent.getActivity(this, 0, viewIntent, 0);

        notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(eventTitle)
                .setContentText(eventLocation)
                .setContentIntent(viewPendingIntent);

        // Get an instance of the NotificationManager service
        notificationManager = NotificationManagerCompat.from(this);

    }


    @Override
    public void onClick(View v) {
        // Build the notification and issues it with notification manager.
        notificationManager.notify(notificationId, notificationBuilder.build());
    }
}
