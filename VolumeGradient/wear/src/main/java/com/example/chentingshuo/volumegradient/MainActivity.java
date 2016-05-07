package com.example.chentingshuo.volumegradient;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends Activity {

    private static final String TAG = "VolumeGradient";
    private AudioManager audioManager;
    private MediaPlayer mMediaPlayer;
    private boolean mIsFirstInTimerTask;
    private Timer mRingTimer;
    private int max;
    private int mCurrentVolume;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
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
            mMediaPlayer.prepare();
            max = audioManager.getStreamMaxVolume(AudioManager.STREAM_ALARM);
            mCurrentVolume = 0;
            mIsFirstInTimerTask = true;
            mRingTimer = new Timer(true);
            mRingTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    AudioManager audioManager =
                            (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                    if (mIsFirstInTimerTask) {
                        audioManager.setStreamVolume(AudioManager.STREAM_ALARM, 0, 0);
                        mMediaPlayer.start();
                        mIsFirstInTimerTask = false;
                    }
                    try {
                        if (mMediaPlayer.isPlaying() && mCurrentVolume < max) {
                            mCurrentVolume++;
                            Log.d(TAG, "Current volume is " + mCurrentVolume);
                            audioManager.setStreamVolume(AudioManager.STREAM_ALARM, mCurrentVolume, 0);
                        }
                    } catch (Exception ex) {
                        Log.v(TAG, "Failed to play ringtone: " + ex);
                    }
                }
            }, 5000, 200);
        } catch (Exception ex) {
            Log.v(TAG, "Failed to play ringtone: " + ex);
        }
    }

    public void btnStop(View v) {
        mMediaPlayer.stop();
        mCurrentVolume = 0;
        mIsFirstInTimerTask = false;
    }
}
