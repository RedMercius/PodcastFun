/*
 * Copyright 2015 © Johnnie Ruffin
 *
 * Unless required by applicable law or agreed to in writing, software is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *
 */

package com.RuffinApps.johnnie.oldtimeradiofree;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class PlayActivity extends AppCompatActivity implements MediaPlayer.OnPreparedListener,
MediaPlayer.OnCompletionListener, AudioManager.OnAudioFocusChangeListener {

    private String TAG = "PlayActivity: ";
    private Integer[] iconImage;

    // get the controls
    private ImageButton playButton;
    private SeekBar sb;
    private TextView titleLine;
    private TextView duration;
    private ImageView playPic;

    // media controls
    private MediaControl mc;
    private MediaPlayer mp;
    private String mediaName;
    private String artist;
    private boolean haltRun;
    private String title;
    private int mResult;
    private AudioManager am;

    Handler seekHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (this.mp == null) {
            this.mp = new MediaPlayer();
        }

        this.haltRun = false;

        am = (AudioManager)this.getSystemService(Context.AUDIO_SERVICE);

        ImageControl iconControl;
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        setContentView(R.layout.content_play);

        Bundle extra = getIntent().getExtras();
        mediaName = extra.getString("MediaTitle");
        artist = extra.getString("Selection");
        title = extra.getString("Title");

        this.mc = new MediaControl(this, mp, artist);

        iconControl = new ImageControl();
        iconImage = iconControl.getImageButtonList();

        playPic = (ImageView) findViewById(R.id.fullscreen_content);
        playButton = (ImageButton) findViewById(R.id.play_button);
        playButton.setImageResource(iconImage[1]);

        sb = (SeekBar) findViewById(R.id.seekBar);
        titleLine = (TextView) findViewById(R.id.txtTitle);
        duration = (TextView) findViewById(R.id.duration);

        playButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                    if (mp.isPlaying()) {
                        playButton.setImageResource(iconImage[0]);
                        mp.pause();
                    } else {
                        playButton.setImageResource(iconImage[1]);
                        mp.seekTo(mp.getCurrentPosition());
                        mp.start();
                    }

            }
        });

        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progress = 0;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progressValue, boolean fromUser) {

                progress = progressValue;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int seekPosition = progress;
                mp.seekTo(seekPosition);
            }
        });

        this.mp.setOnPreparedListener(this);
        this.mp.setOnCompletionListener(this);
        setPlayPic();
        checkForMedia();
    }

    @Override
    public void onAudioFocusChange(int focusChange)
    {
        switch(focusChange) {
            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                Log.d(TAG, "AUDIOFOCUS_LOSS_TRANSIENT");
                playButton.setImageResource(iconImage[0]);
                mp.pause();
                // Pause
                break;
            case AudioManager.AUDIOFOCUS_GAIN:
                Log.d(TAG, "AUDIOFOCUS_GAIN");

                if (mResult != AudioManager.AUDIOFOCUS_REQUEST_GRANTED)
                {
                    return;
                }
                playButton.setImageResource(iconImage[1]);
                mp.seekTo(mp.getCurrentPosition());
                mp.start();
                // Resume
                break;
            case AudioManager.AUDIOFOCUS_LOSS:
                Log.d(TAG, "AUDIOFOCUS_LOSS");
                try {
                    cleanup();
                }
                catch (IllegalStateException e)
                {
                    Log.e(TAG, "Illegal State Exception: " + e);
                }
                //cleanup();
                // Stop or pause depending on your need
            break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent i = new Intent(this, SelectActivity.class);
        i.putExtra("Selection", artist);
        this.startActivity(i);
        cleanup();
        return true;
    }

    private void setPlayPic()
    {
        switch (artist)
        {
            case "Burns And Allen":
            {
                playPic.setImageResource(R.drawable.burnsandallen1);
                break;
            }

            case "Fibber McGee And Molly":
            {
                playPic.setImageResource(R.drawable.fibber_and_molly_);
                break;
            }

            case "Martin And Lewis":
            {
                playPic.setImageResource(R.drawable.lewis_and_martin);
                break;
            }

            case "The Great GilderSleeves":
            {
                playPic.setImageResource(R.drawable.greatgildersleeve1);
                break;
            }

            case "XMinus1":
            {

                playPic.setImageResource(R.drawable.xminusone);
                break;
            }

            case "Inner Sanctum":
            {
                playPic.setImageResource(R.drawable.inner_sanctum);
                break;
            }

            case "Dimension X":
            {
                playPic.setImageResource(R.drawable.dimension_x);
                break;
            }

            case "Night Beat":
            {
                playPic.setImageResource(R.drawable.nightbeat);
                break;
            }

            case "Speed":
            {
                playPic.setImageResource(R.drawable.sgimage);
                break;
            }

            default: {
                playPic.setImageResource(R.drawable.burnsandallen1);
                break;
            }
        }
    }

    private void checkForMedia()
    {
        boolean isItInRaw = mc.checkResourceInRaw(mediaName);
        boolean doesMediaExist = mc.checkForMedia(mediaName);

        if (isItInRaw)
        {
            try {

                mResult = am.requestAudioFocus(PlayActivity.this, AudioManager.STREAM_MUSIC,
                        AudioManager.AUDIOFOCUS_GAIN);
                if (mResult != AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    return;
                }
                mc.callMediaFromRaw(mediaName);
            }
            catch (IOException e)
            {
                Log.e(TAG, "checkForMedia_IOException:  " + e);
            }
        }

        if (!doesMediaExist)
        {
            try {

                // Request focus for music stream and pass AudioManager.OnAudioFocusChangeListener
                // implementation reference

                mResult = am.requestAudioFocus(PlayActivity.this, AudioManager.STREAM_MUSIC,
                        AudioManager.AUDIOFOCUS_GAIN);
                if (mResult != AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    return;
                }
                mc.callMediaFromInternet(mediaName);
                mp.seekTo(checkMediaPos(mediaName));
                titleLine.setText(title);
                titleLine.setVisibility(View.VISIBLE);
            }
            catch (IOException e)
            {
                Log.e(TAG, "checkForMedia_IOException:  " + e);
            }
            catch (IllegalArgumentException e)
            {
                Log.e(TAG, "checkForMedia_IllegalArgumentException: " + e);
            }
        }

        if (doesMediaExist) {
            try {
                // Request focus for music stream and pass AudioManager.OnAudioFocusChangeListener
                // implementation reference

                mResult = am.requestAudioFocus(PlayActivity.this, AudioManager.STREAM_MUSIC,
                        AudioManager.AUDIOFOCUS_GAIN);
                if (mResult != AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    return;
                }
                mc.callMediaFromExternalDir(mediaName);
                mc.getMp3Info(mediaName);
                mp.seekTo(checkMediaPos(mediaName));
                titleLine.setText(title);
                titleLine.setVisibility(View.VISIBLE);
            } catch (IOException e) {
                Log.e(TAG, "checkForMedia_IOException_media does exist:  " + e);
            }
        }
    }

    private int checkMediaPos(String MediaTitle)
    {
        return 0;
    }

    private void saveMediaPos(int pos)
    {

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
            Log.e(TAG, "Exception_enableProgress: " + e);
        }
        duration.setText(myDuration);
    }

    Runnable run = new Runnable() {
        @Override
        public void run() {
            if (haltRun)
            {
                return;
            }

            try {
                runProgress();
            }
            catch (IllegalStateException e)
            {
                Log.e(TAG, "IllegalStateException_run: " + e);
            }
        }
    };

    private void runProgress() {
        int durationInMil = ((mp.getDuration() - mp.getCurrentPosition()));
        String myDuration;
        try {
            myDuration =  getDurationInFormat(durationInMil);
        } catch (IllegalStateException e)
        {
            Log.e(TAG, "IllegalStateException_runProgress: " + e);
            return;
        }

        duration.setText(myDuration);
        sb.setProgress(mp.getCurrentPosition());
        saveMediaPos(mp.getCurrentPosition());
        seekHandler.postDelayed(run, 1000);
    }

    public String getDurationInFormat (int duration)
    {
        String durationInFormat;
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
        mp.start();
        seekHandler.removeCallbacks(run);

        enableProgress();
        runProgress();
    }

    private void cleanup()
    {
        am.abandonAudioFocus(this);
        mc.stopMedia();
        haltRun = true;
        finish();
    }

    @Override
    public void onCompletion(MediaPlayer mp)
    {
        cleanup();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Log.e(TAG, "KeyCode_Back!");
            cleanup();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
}
