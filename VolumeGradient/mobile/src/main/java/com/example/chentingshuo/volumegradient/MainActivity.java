package com.example.chentingshuo.volumegradient;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends Activity {

    private static final String TAG = "VolumeGradient";
    private AudioManager audioManager;
    private int max;
    private MediaPlayer mMediaPlayer;
    private int mCurrentVolume;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        max = audioManager.getStreamMaxVolume(AudioManager.STREAM_ALARM);
        Log.d(TAG, "Max volume is " + max);
        audioManager.setStreamVolume(AudioManager.STREAM_ALARM, 0, 0);
        mMediaPlayer = new MediaPlayer();
        mMediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            public boolean onError(MediaPlayer mp, int what, int extra) {
                Log.d(TAG, "Error occurred while playing ringtone.");
                return true;
            }
        });
        try {
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_ALARM);
            mMediaPlayer.setDataSource(this, RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM));
            mMediaPlayer.setLooping(true);
        } catch (Exception ex) {
            Log.d(TAG, "Failed to play ringtone: " + ex);
        }
    }

    public void btnStart(View v) {
        try {
            mCurrentVolume = 0;
            mMediaPlayer.prepare();
            mMediaPlayer.start();
            Thread th = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        while (mMediaPlayer.isPlaying() && mCurrentVolume < max) {
                            mCurrentVolume++;
                            Log.d(TAG, "Current volume is " + mCurrentVolume);
                            audioManager.setStreamVolume(AudioManager.STREAM_ALARM, mCurrentVolume, 0);
                            Thread.sleep(200);
                        }
                    } catch (Exception ex) {
                        Log.v(TAG, "Failed to play ringtone: " + ex);
                    }
                }
            });
            th.start();
        } catch (Exception ex) {
            Log.v(TAG, "Failed to play ringtone: " + ex);
        }
    }

    public void btnStop(View v) {
        mMediaPlayer.stop();
    }
}
