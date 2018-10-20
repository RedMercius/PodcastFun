/*
 * Copyright 2015 © Johnnie Ruffin
 *
 * Unless required by applicable law or agreed to in writing, software is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *
 */

package com.RuffinApps.johnnie.oldtimeradio;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
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
    private ImageButton rewindButton;
    private ImageButton forwardButton;
    private SeekBar sb;
    private TextView titleLine;
    private TextView curPos;
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
    private PlayedList playList;

    public enum buttonPos
    {
        play,
        pause,
        stop,
        close,
        download,
        download0,
        info,
        delete,
        stream,
        start,
        end,
        lowVolume,
        highVolume,
        mute,
        menu,
        play2
    };

    Handler seekHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (this.mp == null) {
            this.mp = new MediaPlayer();
        }

        this.haltRun = false;

        am = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);

        ImageControl iconControl;
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        setContentView(R.layout.content_play);

        Bundle extra = getIntent().getExtras();
        mediaName = extra.getString("MediaTitle");
        artist = CurrentArtist.getInstance().getCurrentArtist();
        title = extra.getString("Title");

        playList = new PlayedList(this);

        this.mc = new MediaControl(this, mp);

        iconControl = new ImageControl();
        iconImage = iconControl.getImageButtonList();

        playPic = (ImageView) findViewById(R.id.fullscreen_content);
        playButton = (ImageButton) findViewById(R.id.play_button);
        playButton.setImageResource(iconImage[buttonPos.pause.ordinal()]);

        rewindButton = (ImageButton) findViewById(R.id.rewind_button);
        rewindButton.setImageResource(iconImage[buttonPos.start.ordinal()]);

        forwardButton = (ImageButton) findViewById(R.id.forward_button);
        forwardButton.setImageResource(iconImage[buttonPos.end.ordinal()]);

        sb = (SeekBar) findViewById(R.id.seekBar);
        titleLine = (TextView) findViewById(R.id.txtTitle);
        curPos = (TextView) findViewById(R.id.txtCurPos);
        duration = (TextView) findViewById(R.id.duration);

        playButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (mp.isPlaying()) {
                    playButton.setImageResource(iconImage[buttonPos.play.ordinal()]);
                    mp.pause();
                } else {
                    playButton.setImageResource(iconImage[buttonPos.pause.ordinal()]);
                    mp.seekTo(mp.getCurrentPosition());
                    mp.start();
                }

            }
        });

        rewindButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                mp.seekTo((mp.getCurrentPosition() - 15000));
                mp.start();
            }
        });

        forwardButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                mp.seekTo((mp.getCurrentPosition() + 15000));
                mp.start();
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
    public void onAudioFocusChange(int focusChange) {
        switch (focusChange) {
            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                Log.d(TAG, "AUDIOFOCUS_LOSS_TRANSIENT");
                playButton.setImageResource(iconImage[0]);
                mp.pause();
                // Pause
                break;
            case AudioManager.AUDIOFOCUS_GAIN:
                Log.d(TAG, "AUDIOFOCUS_GAIN");

                if (mResult != AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
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
                } catch (IllegalStateException e) {
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

    private void setPlayPic() {
        switch (artist) {
            case "Burns And Allen": {
                playPic.setImageResource(R.mipmap.burnsandallen);
                break;
            }

            case "Fibber McGee And Molly": {
                playPic.setImageResource(R.mipmap.fibber_and_molly_);
                break;
            }

            case "Martin And Lewis": {
                playPic.setImageResource(R.mipmap.lewis_and_martin);
                break;
            }

            case "The Great GilderSleeves": {
                playPic.setImageResource(R.mipmap.greatgildersleeve);
                break;
            }

            case "XMinus1": {

                playPic.setImageResource(R.mipmap.xminusone);
                break;
            }

            case "Inner Sanctum": {
                playPic.setImageResource(R.mipmap.innersanctum0);
                break;
            }

            case "Dimension X": {
                playPic.setImageResource(R.mipmap.dimension_x);
                break;
            }

            case "Night Beat": {
                playPic.setImageResource(R.mipmap.nightbeat);
                break;
            }

            case "Speed": {
                playPic.setImageResource(R.mipmap.sgimage);
                break;
            }

            case "The Whistler": {
                playPic.setImageResource(R.mipmap.thewhistler);
                break;
            }

            case "Jack Benny": {
                playPic.setImageResource(R.mipmap.jackbenny_fixed);
                break;
            }

            case "Bob Hope": {
                playPic.setImageResource(R.mipmap.bob_hope_1950_0_fixed_0);
                break;
            }

            case "Hopalong Cassidy": {
                playPic.setImageResource(R.mipmap.hopalongcassidy);
                break;
            }

            case "Fort Laramie": {
                playPic.setImageResource(R.mipmap.ftlaramie);
                break;
            }

            default: {
                playPic.setImageResource(R.mipmap.burnsandallen);
                break;
            }
        }
    }

    private void checkForMedia() {
        boolean isItInRaw = mc.checkResourceInRaw(mediaName);
        boolean doesMediaExist = mc.checkForMedia(mediaName);

        if (isItInRaw) {
            try {

                mResult = am.requestAudioFocus(PlayActivity.this, AudioManager.STREAM_MUSIC,
                        AudioManager.AUDIOFOCUS_GAIN);
                if (mResult != AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    return;
                }
                mc.callMediaFromRaw(mediaName);
            } catch (IOException e) {
                Log.e(TAG, "checkForMedia_IOException:  " + e);
            }
        }

        if (!doesMediaExist) {
            try {

                // Request focus for music stream and pass AudioManager.OnAudioFocusChangeListener
                // implementation reference

                mResult = am.requestAudioFocus(PlayActivity.this, AudioManager.STREAM_MUSIC,
                        AudioManager.AUDIOFOCUS_GAIN);
                if (mResult != AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    return;
                }
                mc.callMediaFromInternet(mediaName);

                titleLine.setText(title);
                titleLine.setVisibility(View.VISIBLE);
            } catch (IOException e) {
                Log.e(TAG, "checkForMedia_IOException:  " + e);
            } catch (IllegalArgumentException e) {
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
                titleLine.setText(title);
                titleLine.setVisibility(View.VISIBLE);
            } catch (IOException e) {
                Log.e(TAG, "checkForMedia_IOException_media does exist:  " + e);
            }
        }
    }

    public void enableProgress() {
        sb.setMax(mp.getDuration());
        int durationInMil = (mp.getDuration());
        String myDuration = "00:00:00";

        try {
            myDuration = getDurationInFormat(durationInMil);
        } catch (Exception e) {
            Log.e(TAG, "Exception_enableProgress: " + e);
        }
        duration.setText(myDuration);
    }

    Runnable run = new Runnable() {
        @Override
        public void run() {
            if (haltRun) {
                return;
            }

            try {
                runProgress();
            } catch (IllegalStateException e) {
                Log.e(TAG, "IllegalStateException_run: " + e);
            }
        }
    };

    private void runProgress() {
        // TODO: use current position and make duration constant
        int durationInMil = ((mp.getDuration() - mp.getCurrentPosition()));
        //String myDuration;
        String myCurPos;
        try {
            //myDuration = getDurationInFormat(durationInMil);
            myCurPos = getDurationInFormat(mp.getCurrentPosition());
        } catch (IllegalStateException e) {
            Log.e(TAG, "IllegalStateException_runProgress: " + e);
            return;
        }

        //duration.setText(myDuration);
        curPos.setText(myCurPos);
        sb.setProgress(mp.getCurrentPosition());
        seekHandler.postDelayed(run, 1000);
    }

    public String getDurationInFormat(int duration) {
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

    private void cleanup() {
        playList.add(0, artist, title);
        am.abandonAudioFocus(this);
        mc.stopMedia();
        haltRun = true;
        finish();
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        cleanup();
    }

    // TODO: Handle hard input button presses or joystick button presses.
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        boolean handled = false;

        switch (keyCode){
            case KeyEvent.KEYCODE_DPAD_CENTER:
            case KeyEvent.KEYCODE_BUTTON_A:
                // ... handle selections
                handled = true;
                break;
            case KeyEvent.KEYCODE_DPAD_LEFT:
                // ... handle left action
                handled = true;
                break;
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                // ... handle right action
                handled = true;
                break;
        }
        return handled || super.onKeyDown(keyCode, event);
    }

    /*@Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Log.e(TAG, "KeyCode_Back!");
            cleanup();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }*/

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}
