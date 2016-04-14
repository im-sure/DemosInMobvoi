package com.example.chentingshuo.randomnumber;

import android.content.Intent;
import android.util.Log;

import com.mobvoi.android.common.api.MobvoiApiClient;
import com.mobvoi.android.wearable.MessageEvent;
import com.mobvoi.android.wearable.Wearable;
import com.mobvoi.android.wearable.WearableListenerService;

/**
 * Created by chentingshuo on 16/4/9.
 */
public class RandomNumberListenerService extends WearableListenerService {

    private static final String TAG = "RandomNumbeiService";
    private static final String START_ACTIVITY_PATH = "/start-randomnumber";

    private MobvoiApiClient mMobvoiApiClient;

    @Override
    public void onCreate() {
        super.onCreate();
        mMobvoiApiClient = new MobvoiApiClient.Builder(this)
                .addApi(Wearable.API)
                .build();
        mMobvoiApiClient.connect();
    }

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        super.onMessageReceived(messageEvent);
        Log.d(TAG, "onMessageReceived");
        if (messageEvent.getPath().equals(START_ACTIVITY_PATH)) {
            Intent startIntent = new Intent(this, MainActivity.class);
            startIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(startIntent);
            Log.d(TAG, "Start New Activity");
        }
    }
}
