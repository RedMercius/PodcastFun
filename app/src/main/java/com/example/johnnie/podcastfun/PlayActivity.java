/*
 * Copyright 2015 © Johnnie Ruffin
 *
 * Unless required by applicable law or agreed to in writing, software is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *
 */

package com.example.johnnie.podcastfun;

import android.content.Intent;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class PlayActivity extends AppCompatActivity implements MediaPlayer.OnPreparedListener {

    private String TAG = "PlayActivity: ";
    private String[] radioTitle;
    private Integer[] iconImage;

    // get the controls
    private ImageButton playButton;
    private SeekBar sb;
    private TextView titleLine;
    private TextView duration;

    // get the icon images
    private ImageControl iconControl;
    private RadioTitle title;

    // media controls
    private MediaControl mc;
    private MediaPlayer mp;


    private boolean stream;
    private String mediaName;

    Handler seekHandler = new Handler();
    ExecutorService threadPoolExecutor = Executors.newSingleThreadExecutor();
    Future runProgressFuture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mp = new MediaPlayer();
        setContentView(R.layout.activity_play);

        runProgressFuture = threadPoolExecutor.submit(run);

        Bundle extra = getIntent().getExtras();
        mediaName = extra.getString("MediaTitle");

        Log.d("PlayActivity: ", "onCreate:  " + mediaName);

        iconControl = new ImageControl();
        iconImage = iconControl.getImageButtonList();

        // get the radio titles
        title = new RadioTitle();
        radioTitle = title.getBurnsAllen();

        playButton = (ImageButton) findViewById(R.id.play_button);
        playButton.setImageResource(iconImage[1]);

        sb = (SeekBar) findViewById(R.id.seekBar);
        titleLine = (TextView) findViewById(R.id.txtTitle);
        duration = (TextView) findViewById(R.id.duration);

        mc=new MediaControl(this, mp);

        playButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (mp.isPlaying()) {
                    playButton.setImageResource(iconImage[1]);
                    mp.pause();
                    Log.d("PlayActivity: ", "Pausing!");
                } else {
                    playButton.setImageResource(iconImage[0]);
                    mp.seekTo(mp.getCurrentPosition());
                    mp.start();
                    Log.d("PlayActivity: ", "Playing!");
                }
            }
        });

        this.mp.setOnPreparedListener(this);
        checkForMedia();
    }

    private void checkForMedia()
    {
        boolean isItInRaw = mc.checkResourceInRaw(mediaName);
        boolean doesMediaExist = mc.checkForMedia(mediaName);

        if (isItInRaw)
        {
            try {

                mc.callMediaFromRaw(mediaName, this);
            }
            catch (IOException e)
            {
                Log.d("PlayActivity: ", "checkForMedia_IOException:  " + e);
            }
        }

        if (!doesMediaExist)
        {
            try {

                mc.callMediaFromInternet(mediaName, this);

                MediaMetadataRetriever mediaInfo = new MediaMetadataRetriever();

                String uri = ("http://www.JohnnieRuffin.com/audio/" + mediaName);

                HashMap<String, String> hashMap = new HashMap<String, String>();
                mediaInfo.setDataSource(uri, hashMap);

                String myTitle = "DEFAULT_TITLE";
                myTitle = mediaInfo.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);

                titleLine.setText(myTitle);
                titleLine.setVisibility(View.VISIBLE);
                mediaInfo.release();
            }
            catch (IOException e)
            {
                Log.d("PlayActivity: ", "checkForMedia_IOException:  " + e);
            }
            catch (IllegalArgumentException e)
            {
                Log.d("PlayActivity: ", "checkForMedia_IllegalArgumentException: " + e);
            }
        }

        if (doesMediaExist) {
            try {

                mc.callMediaFromExternalDir(mediaName, this);
                mc.getMp3Info();
                titleLine.setText(mc.getMP3Title());
                titleLine.setVisibility(View.VISIBLE);
            } catch (IOException e) {
                Log.d("PlayActivity: ", "checkForMedia_IOException_media does exist:  " + e);
            }
        }
    }

    public void enableProgress()
    {
        sb.setMax(mp.getDuration());
        int durationInMil = (mp.getDuration());
        String myDuration = "00:00:00";

        try {
            myDuration =  getDurationInFormat(durationInMil);
        }
        catch (Exception e)
        {
            Log.d("PlayActivity", "Exception: " + e);
        }
        duration.setText(myDuration);
    }

    Runnable run = new Runnable() {
        @Override
        public void run() {
            try {
                runProgress();
            }
            catch(Exception e)
            {
                Log.d(TAG, "Exception: " + e);
            }
        }
    };

    private void runProgress() {
        int durationInMil = ((mp.getDuration() - mp.getCurrentPosition()));
        String myDuration = "00:00:00";
        try {
            myDuration =  getDurationInFormat(durationInMil);
        } catch (Exception e)
        {
            Log.d("PlayActivity", "Exception: " + e);
            return;
        }

        duration.setText(myDuration);
        sb.setProgress(mp.getCurrentPosition());
        seekHandler.postDelayed(run, 1000);
    }

    public String getDurationInFormat (int duration)
    {
        String durationInFormat = "00:00:00";
        durationInFormat = String.format("%02d:%02d:%02d",
                TimeUnit.MILLISECONDS.toHours(duration),
                TimeUnit.MILLISECONDS.toMinutes(duration) -
                        TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(duration)),
                TimeUnit.MILLISECONDS.toSeconds(duration) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration)));

        return durationInFormat;
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        Log.d("PlayActivity: ", "onPrepared called");
        mp.start();
        enableProgress();
        runProgress();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            runProgressFuture.cancel(true);
            mc.stopMedia();

            final Intent i = new Intent(PlayActivity.this, baSelectActivity.class);
            startActivity(i);
            finish();
            Log.d(TAG, "OnKeyDown");
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }


}
