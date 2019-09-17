package com.RuffinApps.johnnie.oldtimeradio;
/*
 * Copyright 2015 © Johnnie Ruffin
 *
 * Unless required by applicable law or agreed to in writing, software is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *
 */

import android.content.Context;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.AudioFocusRequest;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
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
import java.util.Locale;
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
    private AudioAttributes mPlaybackAttributes;
    private AudioFocusRequest mFocusRequest;
    private PlayedList playList;
    WifiManager.WifiLock wifiLock;
    private Handler mmyHandler = new Handler();

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
    }

    Handler seekHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (this.mp == null) {
            this.mp = new MediaPlayer();
        }
        // finish playing if play is already in progress for the next song to start.
        if (mp.isPlaying()) {
            cleanup();
        }

        this.haltRun = false;

        wifiLock = ((WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE))
                .createWifiLock(WifiManager.WIFI_MODE_FULL, "mylock");

        am = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);

        ImageControl iconControl;
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        setContentView(R.layout.content_play);

        Bundle extra = getIntent().getExtras();
        mediaName = extra.getString("MediaTitle");
        mediaName = getRawFileName(mediaName);
        Log.d(TAG, "Media Title: " + mediaName);
        artist = CurrentArtist.getInstance().getCurrentArtist();
        title = extra.getString("Title");

        playList = new PlayedList(this);

        this.mc = new MediaControl(this, mp);

        iconControl = new ImageControl();
        iconImage = iconControl.getImageButtonList();

        playPic = findViewById(R.id.fullscreen_content);
        playButton = findViewById(R.id.play_button);
        playButton.setImageResource(iconImage[buttonPos.pause.ordinal()]);

        rewindButton = findViewById(R.id.rewind_button);
        rewindButton.setImageResource(iconImage[buttonPos.start.ordinal()]);

        forwardButton = findViewById(R.id.forward_button);
        forwardButton.setImageResource(iconImage[buttonPos.end.ordinal()]);

        sb = findViewById(R.id.seekBar);
        titleLine = findViewById(R.id.txtTitle);
        curPos = findViewById(R.id.txtCurPos);
        duration = findViewById(R.id.duration);

        playButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (mp.isPlaying()) {
                    playButton.setImageResource(iconImage[buttonPos.play.ordinal()]);
                    mp.pause();
                } else {
                    if(!requestAudioFocus())
                    {
                        return;
                    }
                    playButton.setImageResource(iconImage[buttonPos.pause.ordinal()]);
                    mp.seekTo(mp.getCurrentPosition());
                    mp.start();
                }
            }
        });

        rewindButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(!requestAudioFocus())
                {
                    return;
                }
                mp.seekTo((mp.getCurrentPosition() - 15000));
                mp.start();
            }
        });

        forwardButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(!requestAudioFocus())
                {
                    return;
                }
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
            case AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK:
                Log.d(TAG, "AUDIOFOCUS_LOSS_TRANSIENT or AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK");
                playButton.setImageResource(iconImage[0]);
                mp.pause();
                // wifiLock.release();
                // Pause
                break;
            case AudioManager.AUDIOFOCUS_GAIN:
                Log.d(TAG, "AUDIOFOCUS_GAIN");

                if (mResult != AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    return;
                }
                // wifiLock.acquire();
                playButton.setImageResource(iconImage[1]);
                mp.seekTo(mp.getCurrentPosition());
                mp.start();
                // Resume
                break;
            case AudioManager.AUDIOFOCUS_LOSS:
                Log.d(TAG, "AUDIOFOCUS_LOSS");
                try {
                    playButton.setImageResource(iconImage[0]);
                    mp.pause();
                } catch (IllegalStateException e) {
                    Log.e(TAG, "Illegal State Exception: " + e);
                }
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent i = new Intent(this, SelectActivity.class);
        i.putExtra("Selection", artist);
        this.startActivity(i);
        return true;
    }

    private void setPlayPic() {
        switch (artist) {
            case "Burns And Allen": {
                playPic.setImageResource(R.mipmap.burnsandallen);
                setImageId("burnsandallen");
                break;
            }

            case "Fibber McGee And Molly": {
                playPic.setImageResource(R.mipmap.fibber_and_molly_);
                setImageId("fibber_and_molly_");
                break;
            }

            case "Martin And Lewis": {
                playPic.setImageResource(R.mipmap.lewis_and_martin);
                setImageId("lewis_and_martin");
                break;
            }

            case "The Great GilderSleeves": {
                playPic.setImageResource(R.mipmap.greatgildersleeve);
                setImageId("greatgildersleeve");
                break;
            }

            case "XMinus1": {
                playPic.setImageResource(R.mipmap.xminusone);
                setImageId("xminusone");
                break;
            }

            case "Inner Sanctum": {
                playPic.setImageResource(R.mipmap.innersanctum0);
                setImageId("innersanctum0");
                break;
            }

            case "Dimension X": {
                playPic.setImageResource(R.mipmap.dimension_x);
                setImageId("dimension_x");
                break;
            }

            case "Night Beat": {
                playPic.setImageResource(R.mipmap.nightbeat);
                setImageId("nightbeat");
                break;
            }

            case "Speed Gibson": {
                playPic.setImageResource(R.mipmap.sgimage);
                setImageId("sgimage");
                break;
            }

            case "The Whistler": {
                playPic.setImageResource(R.mipmap.thewhistler);
                setImageId("thewhistler");
                break;
            }

            case "Jack Benny": {
                playPic.setImageResource(R.mipmap.jackbenny_fixed);
                setImageId("jackbenny_fixed");
                break;
            }

            case "Bob Hope": {
                playPic.setImageResource(R.mipmap.bob_hope_1950_0_fixed_0);
                setImageId("bob_hope_1950_0_fixed_0");
                break;
            }

            case "Hopalong Cassidy": {
                playPic.setImageResource(R.mipmap.hopalongcassidy);
                setImageId("hopalongcassidy");
                break;
            }

            case "Fort Laramie": {
                playPic.setImageResource(R.mipmap.ftlaramie);
                setImageId("ftlaramie");
                break;
            }

            case "Our Miss Brooks": {
                playPic.setImageResource(R.mipmap.ourmissbrooks);
                setImageId("ourmissbrooks");
                break;
            }

            case "Father Knows Best": {
                playPic.setImageResource(R.mipmap.fatherknowsbest);
                setImageId("fatherknowsbest");
                break;
            }

            case "Lone Ranger": {
                playPic.setImageResource(R.mipmap.loneranger);
                setImageId("loneranger");
                break;
            }

            case "Pat O": {
                playPic.setImageResource(R.mipmap.pato);
                setImageId("pato");
                break;
            }

            case "Ozzie And Harriet": {
                playPic.setImageResource(R.mipmap.ozzieandharriet);
                setImageId("ozzieandharriet");
                break;
            }

            case "The Life Of Riley": {
                playPic.setImageResource(R.mipmap.lifeofriley);
                setImageId("lifeofriley");
                break;
            }

            case "Flash Gordon": {
                playPic.setImageResource(R.mipmap.flashgordon);
                setImageId("flashgordon");
                break;
            }

            case "SciFi Radio": {
                playPic.setImageResource(R.mipmap.scifiradio);
                setImageId("scifiradio");
                break;
            }

            case "The Green Hornet": {
                playPic.setImageResource(R.mipmap.greenhornet);
                setImageId("greenhornet");
                break;
            }

            case "Adventures By Morse": {
                playPic.setImageResource(R.mipmap.adbm);
                setImageId("adbm");
                break;
            }

            case "Adventures of Dick Cole": {
                playPic.setImageResource(R.mipmap.adc);
                setImageId("adc");
                break;
            }

            case "Blondie": {
                playPic.setImageResource(R.mipmap.blondie);
                setImageId("blondie");
                break;
            }

            case "Bold Venture": {
                playPic.setImageResource(R.mipmap.boldventure);
                setImageId("boldventure");
                break;
            }

            case "Boston Blackie": {
                playPic.setImageResource(R.mipmap.bostonblackie);
                setImageId("bostonblackie");
                break;
            }

            case "CBS Radio Mystery Theater": {
                playPic.setImageResource(R.mipmap.cbsradio);
                setImageId("cbsradio");
                break;
            }

            case "Dangerous Assignment": {
                playPic.setImageResource(R.mipmap.dangerousassignment);
                setImageId("dangerousassignment");
                break;
            }

            case "Duffys Tavern": {
                playPic.setImageResource(R.mipmap.duffystavern);
                setImageId("duffystavern");
                break;
            }

            case "Mr And Mrs North": {
                playPic.setImageResource(R.mipmap.mrmrsnorth);
                setImageId("mrmrsnorth");
                break;
            }

            case "Quiet Please": {
                playPic.setImageResource(R.mipmap.quietplease);
                setImageId("quietplease");
                break;
            }

            case "Suspense": {
                playPic.setImageResource(R.mipmap.suspense);
                setImageId("suspense");
                break;
            }

            case "The Lives of Harry Lime": {
                playPic.setImageResource(R.mipmap.harrylime);
                setImageId("harrylime");
                break;
            }

            case "Have Gun Will Travel": {
                playPic.setImageResource(R.mipmap.havegun);
                setImageId("havegun");
                break;
            }

            default: {
                playPic.setImageResource(R.mipmap.burnsandallen);
                setImageId("burnsandallen");
                break;
            }
        }
    }

    private int setImageId(String imageName)
    {
        //to retrieve image in res/drawable and set image in ImageView
        int resID = getResources().getIdentifier(imageName, "mipmap", "com.RuffinApps.johnnie.oldtimeradio");
        CurrentArtist.getInstance().setCurrentImage(resID);
        return resID;
    }

    private boolean requestAudioFocus()
    {
        boolean granted = true;
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.P ) {
            mPlaybackAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setLegacyStreamType(AudioManager.STREAM_MUSIC)
                    .build();

            mFocusRequest = new AudioFocusRequest.Builder(AudioManager.AUDIOFOCUS_GAIN)
                    .setAudioAttributes(mPlaybackAttributes)
                    .setAcceptsDelayedFocusGain(true)
                    .setWillPauseWhenDucked(true)
                    .setOnAudioFocusChangeListener(PlayActivity.this)
                    .build();

            mResult = am.requestAudioFocus(mFocusRequest);
        }

        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {

            mResult = am.requestAudioFocus(PlayActivity.this, AudioManager.STREAM_MUSIC,
                    AudioManager.AUDIOFOCUS_GAIN);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P)
        {
            Log.d(TAG, "28 API or more.");
            mPlaybackAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setLegacyStreamType(AudioManager.STREAM_MUSIC)
                    .build();

            mFocusRequest = new AudioFocusRequest.Builder(AudioManager.AUDIOFOCUS_GAIN)
                    .setAudioAttributes(mPlaybackAttributes)
                    .setAcceptsDelayedFocusGain(true)
                    .setWillPauseWhenDucked(true)
                    .setOnAudioFocusChangeListener(PlayActivity.this)
                    .build();

            mResult = am.requestAudioFocus(mFocusRequest);
        }

        if (mResult != AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
            granted = false;
        }

        return granted;
    }

    private String getRawFileName(String mediaFile)
    {
        if (mediaFile == null)
        {
            return null;
        }
        String webAddress = CurrentArtist.getInstance().getArtistUrl();
        String modifiedName = mediaFile.replace(webAddress,"");
        Log.d(TAG, "Raw File Name: " + modifiedName);
        return modifiedName;
    }

    private void checkForMedia() {
        boolean isItInRaw = mc.checkResourceInRaw(mediaName);
        boolean doesMediaExist = mc.checkForMedia(mediaName);

        if (isItInRaw) {
            try {
                if(!requestAudioFocus())
                {
                    return;
                }
                mc.callMediaFromRaw(mediaName);
                CurrentArtist.getInstance().setCurrentFile(mediaName);
                CurrentArtist.getInstance().setCurrentTitle(title);
            } catch (IOException e) {
                Log.e(TAG, "checkForMedia_IOException:  " + e);
            }
        }

        if (!doesMediaExist) {
            try {

                // Request focus for music stream and pass AudioManager.OnAudioFocusChangeListener
                // implementation reference
                // wifiLock.acquire();
                if(!requestAudioFocus())
                {
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
                if(!requestAudioFocus())
                {
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
        CurrentArtist.getInstance().setCurrentDuration(durationInMil);
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
        durationInFormat = String.format(Locale.ENGLISH, "%02d:%02d:%02d",
                TimeUnit.MILLISECONDS.toHours(duration),
                TimeUnit.MILLISECONDS.toMinutes(duration) -
                        TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(duration)),
                TimeUnit.MILLISECONDS.toSeconds(duration) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration)));

        return durationInFormat;
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        // mp.setWakeMode(getApplicationContext(), PowerManager.PARTIAL_WAKE_LOCK);
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

  /*  @Override
    public void onDestroy() {
        super.onDestroy();
        cleanup();
    }*/


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
}
