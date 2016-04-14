package com.example.chentingshuo.randomnumber;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.mobvoi.android.common.ConnectionResult;
import com.mobvoi.android.common.api.MobvoiApiClient;
import com.mobvoi.android.common.api.MobvoiApiClient.ConnectionCallbacks;
import com.mobvoi.android.common.api.MobvoiApiClient.OnConnectionFailedListener;
import com.mobvoi.android.common.api.ResultCallback;
import com.mobvoi.android.wearable.MessageApi;
import com.mobvoi.android.wearable.MessageApi.MessageListener;
import com.mobvoi.android.wearable.MessageEvent;
import com.mobvoi.android.wearable.Wearable;

import java.util.Random;


public class MainActivity extends Activity implements MessageListener, ConnectionCallbacks,
        OnConnectionFailedListener {

    private static final String TAG = "RandomNumber";
    private static final String START_ACTIVITY_PATH = "/start-randomnumber";
    private static final String SEND_NUMBER = "/send-number";
    private static final String DEFAULT_NODE = "default_node";


    private Button mStartWearableActivitybtn;
    private Button mGenerateRandomNumberbtn;
    private Button mSendNumberbtn;
    private TextView mNum;
    private byte number;

    private MobvoiApiClient mMobvoiApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mStartWearableActivitybtn = (Button) findViewById(R.id.start_wearable_activity);
        mGenerateRandomNumberbtn = (Button) findViewById(R.id.generate_random_number);
        mSendNumberbtn = (Button) findViewById(R.id.send_number);
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
        mStartWearableActivitybtn.setEnabled(true);
        mGenerateRandomNumberbtn.setEnabled(true);
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d(TAG, "onConnectionSuspended");
        mStartWearableActivitybtn.setEnabled(false);
        mGenerateRandomNumberbtn.setEnabled(false);
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.e(TAG, "onConnectionFailed(): Failed to connect, with result: " + connectionResult);
        mStartWearableActivitybtn.setEnabled(false);
        mGenerateRandomNumberbtn.setEnabled(false);
        mSendNumberbtn.setEnabled(false);
    }

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {

    }

    public void onStartWearableActivityClick(View view) {
        Log.d(TAG, "onStartWearableActivityClick");
        Wearable.MessageApi.sendMessage(mMobvoiApiClient, DEFAULT_NODE, START_ACTIVITY_PATH,
                new byte[0])
                .setResultCallback(
                new ResultCallback<MessageApi.SendMessageResult>() {
                    @Override
                    public void onResult(MessageApi.SendMessageResult sendMessageResult) {
                        if (!sendMessageResult.getStatus().isSuccess()) {
                            Log.e(TAG, "Failed to send message with status code: "
                                    + sendMessageResult.getStatus().getStatusCode());
                        } else {
                            Log.d(TAG, "Success");
                        }
                    }
                }
        );
    }

    public void onGenerateRandomNumberClick(View view) {
        Log.d(TAG, "onGenerateRandomNumberClick");
        Random random = new Random();
        number = (byte) random.nextInt(128);
        Integer n = new Integer(number);
        mNum.setText(n.toString());
        mSendNumberbtn.setEnabled(true);

    }

    public void onSendNumberClick(View view) {
        Log.d(TAG, "onSendNumberClick");
        byte[] data = new byte[]{number};
        Wearable.MessageApi.sendMessage(mMobvoiApiClient, DEFAULT_NODE, SEND_NUMBER, data)
                .setResultCallback(
                        new ResultCallback<MessageApi.SendMessageResult>() {
                            @Override
                            public void onResult(MessageApi.SendMessageResult sendMessageResult) {
                                if (!sendMessageResult.getStatus().isSuccess()) {
                                    Log.e(TAG, "Failed to send message with status code: "
                                            + sendMessageResult.getStatus().getStatusCode());
                                } else {
                                    Log.d(TAG, "Success");
                                }
                            }
                        }
                );
    }


}
