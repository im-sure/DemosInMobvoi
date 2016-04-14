package com.example.chentingshuo.randomnumber;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.mobvoi.android.common.ConnectionResult;
import com.mobvoi.android.common.api.MobvoiApiClient;
import com.mobvoi.android.wearable.MessageApi;
import com.mobvoi.android.wearable.MessageEvent;
import com.mobvoi.android.wearable.Wearable;


public class MainActivity extends Activity implements MessageApi.MessageListener, MobvoiApiClient.ConnectionCallbacks,
        MobvoiApiClient.OnConnectionFailedListener {

    private static final String TAG = "ReceiveRandomNumber";
    private static final String SEND_NUMBER = "/send-number";

    private TextView mNum;

    private MobvoiApiClient mMobvoiApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mNum = (TextView) findViewById(R.id.num);

        mMobvoiApiClient = new MobvoiApiClient.Builder(this)
                .addApi(Wearable.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
        mMobvoiApiClient.connect();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
        Wearable.MessageApi.removeListener(mMobvoiApiClient, this);
        mMobvoiApiClient.disconnect();
    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.d(TAG, "onConnected");
        Wearable.MessageApi.addListener(mMobvoiApiClient, this);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.e(TAG, "onConnectionFailed(): Failed to connect, with result: " + connectionResult);
    }

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        Log.d(TAG, "onMessageReceived");
        if (messageEvent.getPath().equals(SEND_NUMBER)) {
            final Integer n = new Integer(messageEvent.getData()[0]);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mNum.setText(n.toString());
                    Log.d(TAG, "Inner Thread is " + Thread.currentThread().getId());
                }
            });
            Log.d(TAG, "Outer Thread is " + Thread.currentThread().getId());
            Log.d(TAG, "Receive Random Number");
        }
    }
}
