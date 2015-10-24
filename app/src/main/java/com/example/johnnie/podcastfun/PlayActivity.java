/*
 * Copyright 2015 © Johnnie Ruffin
 *
 * Unless required by applicable law or agreed to in writing, software is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *
 */

package com.example.johnnie.podcastfun;

import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.Formatter;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.IOException;
import java.text.DateFormatSymbols;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class PlayActivity extends AppCompatActivity implements MediaPlayer.OnPreparedListener {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mp = new MediaPlayer();
        setContentView(R.layout.activity_play);

        Bundle extra = getIntent().getExtras();
        mediaName = extra.getString("MediaTitle");

        Log.d("PlayActivity: ", "onCreate:  " + mediaName);

        iconControl = new ImageControl();
        iconImage = iconControl.getImageButtonList();

        // get the radio titles
        title = new RadioTitle();
        radioTitle = title.getBurnsAllen();

        playButton = (ImageButton) findViewById(R.id.play_button);
        playButton.setImageResource(iconImage[0]);

        sb = (SeekBar) findViewById(R.id.seekBar);
        titleLine = (TextView) findViewById(R.id.txtTitle);
        duration = (TextView) findViewById(R.id.duration);

        String myTitle;
        titleLine.setText(mediaName);
        titleLine.setVisibility(View.VISIBLE);

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
        else if (!doesMediaExist)
        {
            try {

                mc.callMediaFromInternet(mediaName, this);
            }
            catch (IOException e)
            {
                Log.d("PlayActivity: ", "checkForMedia_IOException:  " + e);
            }
        }
    }

    public void enableProgress()
    {
        sb.setMax(mp.getDuration());
        SimpleDateFormat  format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.US);
        Date mydate;
        int durationInMil = (mp.getDuration());

        String myDuration = "00:00:00";
        try {
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss", Locale.US);
            myDuration = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(durationInMil),
                    TimeUnit.MILLISECONDS.toMinutes(durationInMil) -
                            TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(durationInMil)), // The change is in this line
                    TimeUnit.MILLISECONDS.toSeconds(durationInMil) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(durationInMil)));
        }
        catch (Exception e)
        {
            Log.d("PlayActivity", "Exception: " + e);
        }
        duration.setText(myDuration);
        Log.d("PlayActivity", "Duration: " + mp.getDuration());
    }

    Runnable run = new Runnable() {
        @Override
        public void run() {
            runProgress();
        }
    };

    private void runProgress() {
        int durationInMil = ((mp.getDuration() - mp.getCurrentPosition()));
        SimpleDateFormat  format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.US);
        String myDuration = "00:00:00";
        Date mydate;
        try {
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss", Locale.US);
            myDuration = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(durationInMil),
                    TimeUnit.MILLISECONDS.toMinutes(durationInMil) -
                            TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(durationInMil)), // The change is in this line
                    TimeUnit.MILLISECONDS.toSeconds(durationInMil) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(durationInMil)));
        }
        catch (Exception e)
        {
            Log.d("PlayActivity", "Exception: " + e);
        }

        duration.setText(myDuration);
        sb.setProgress(mp.getCurrentPosition());
        seekHandler.postDelayed(run, 1000);
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        Log.d("PlayActivity: ", "onPrepared called");
        mp.start();
        enableProgress();
        runProgress();
    }


}
