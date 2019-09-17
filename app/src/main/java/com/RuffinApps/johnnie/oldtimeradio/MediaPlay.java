package com.RuffinApps.johnnie.oldtimeradio;
/*
 * Copyright 2015 © Johnnie Ruffin
 *
 * Unless required by applicable law or agreed to in writing, software is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *
 */

import android.content.ComponentName;
import android.media.AudioManager;
import android.os.Handler;
import android.os.RemoteException;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class MediaPlay extends AppCompatActivity {
    private MediaBrowserCompat mediaBrowser;

    private String TAG = "MediaPlay: ";
    private Integer[] iconImage;
    private boolean haltRun;

    // get the button/image controls
    private ImageButton playButton;
    private SeekBar sb;

    private TextView curPos;
    private TextView duration;
    private ImageView playPic;
    ImageControl iconControl;

    Handler seekHandler = new Handler();

    // media controls
    private String artist;
    private String title;
    private MediaControllerCompat mediaController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.content_play);

        // create compat browser
        mediaBrowser = new MediaBrowserCompat(this,
                       new ComponentName(this, JOTRService.class),
                       connectionCallbacks, null);

        artist = CurrentArtist.getInstance().getCurrentArtist();
        title = CurrentArtist.getInstance().getCurrentTitle();

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        this.haltRun = false;
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "On Start");
        mediaBrowser.connect();
    }

    @Override
    public void onResume() {
        super.onResume();
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
    }

    @Override
    public void onStop() {
        super.onStop();
        // (see "stay in sync with the MediaSession")
        if (mediaController != null) {
            mediaController.unregisterCallback(controllerCallback);
        }
        mediaBrowser.disconnect();
    }

    private final MediaBrowserCompat.ConnectionCallback connectionCallbacks =
            new MediaBrowserCompat.ConnectionCallback() {
                @Override
                public void onConnected() {

                    // Get the token for the MediaSession
                    MediaSessionCompat.Token token = mediaBrowser.getSessionToken();
                    Log.d(TAG, "On Connected");

                    try {
                        // Create a MediaControllerCompat
                        mediaController =
                                new MediaControllerCompat(MediaPlay.this, token);

                        // Save the controller
                        MediaControllerCompat.setMediaController(MediaPlay.this, mediaController);
                        Log.d(TAG, "MediaController Created");

                    }
                    catch(RemoteException re)
                    {
                        Log.d(TAG, "Remote Exception: " + re);
                    }
                    // Finish building the UI
                    buildTransportControls();
                }

                @Override
                public void onConnectionSuspended() {
                    Log.d(TAG, "On Connection Suspended");
                    // The Service has crashed. Disable transport controls until it automatically reconnects
                }

                @Override
                public void onConnectionFailed() {
                    Log.d(TAG, "On Connection Failed!!");
                    // The Service has refused our connection
                }
            };

    void buildTransportControls()
    {
        ImageButton rewindButton;
        ImageButton forwardButton;
        TextView titleLine;

        iconControl = new ImageControl();
        iconImage = iconControl.getImageButtonList();

        playPic = findViewById(R.id.fullscreen_content);
        playButton = findViewById(R.id.play_button);
        playButton.setImageResource(iconImage[PlayActivity.buttonPos.pause.ordinal()]);

        rewindButton = findViewById(R.id.rewind_button);
        rewindButton.setImageResource(iconImage[PlayActivity.buttonPos.start.ordinal()]);

        forwardButton = findViewById(R.id.forward_button);
        forwardButton.setImageResource(iconImage[PlayActivity.buttonPos.end.ordinal()]);

        sb = findViewById(R.id.seekBar);
        titleLine = findViewById(R.id.txtTitle);
        curPos = findViewById(R.id.txtCurPos);
        duration = findViewById(R.id.duration);

        // Attach a listener to the button
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            // Since this is a play/pause button, you'll need to test the current state
            // and choose the action accordingly
                Log.d(TAG, "Play Button Clicked!!");
                int pbState = 0;

                if (mediaController.getPlaybackState() != null) {
                    pbState = mediaController.getPlaybackState().getState();
                }

                Log.d(TAG, "PlayState: " + pbState);

            if (pbState == PlaybackStateCompat.STATE_PLAYING) {
                mediaController.getTransportControls().pause();
            } else {
                mediaController.getTransportControls().play();
            }
            }
        });

        rewindButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.d(TAG, "rewind Button Clicked!!");
                long position = 0;
                if (mediaController.getPlaybackState() != null) {
                    if (mediaController.getPlaybackState().getState() == PlaybackStateCompat.STATE_PLAYING) {
                        position = mediaController.getPlaybackState().getBufferedPosition();
                    } else if (mediaController.getPlaybackState().getState() == PlaybackStateCompat.STATE_PAUSED) {
                        position = mediaController.getPlaybackState().getPosition();
                    }
                    mediaController.getTransportControls().seekTo(position - 15000);
                }
                else
                {
                    Log.d(TAG, "mediaController.getPlaybackState is null!");
                }

            }
        });

        forwardButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.d(TAG, "Forward Button Click!");
                long position = 0;
                if (mediaController.getPlaybackState() != null) {
                    if (mediaController.getPlaybackState().getState() == PlaybackStateCompat.STATE_PLAYING) {
                        position = mediaController.getPlaybackState().getBufferedPosition();
                    } else if (mediaController.getPlaybackState().getState() == PlaybackStateCompat.STATE_PAUSED) {
                        position = mediaController.getPlaybackState().getPosition();
                    }
                    mediaController.getTransportControls().seekTo(position + 15000);
                }
                else
                {
                    Log.d(TAG, "mediaController.getPlaybackState is null!");
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
                mediaController.getTransportControls().seekTo(seekPosition);
            }
        });

    // Register a Callback to stay in sync
    mediaController.registerCallback(controllerCallback);

    setPlayPic();
    titleLine.setText(title.toString());
    titleLine.setVisibility(View.VISIBLE);

    mediaController.getTransportControls().play();

    }

    MediaControllerCompat.Callback controllerCallback =
            new MediaControllerCompat.Callback() {
                @Override
                public void onMetadataChanged(MediaMetadataCompat metadata) {
                    Log.d(TAG, "onMetadataChanged");
                }

                @Override
                public void onPlaybackStateChanged(PlaybackStateCompat state) {
                    // Log.d(TAG, "onPlaybackStateChanged: " + state);

                    if (state.getState() == PlaybackStateCompat.STATE_PLAYING) {
                        playButton.setImageResource(iconImage[PlayActivity.buttonPos.pause.ordinal()]);
                        seekHandler.removeCallbacks(run);

                        enableProgress();
                        runProgress();
                    }

                    if (state.getState() == PlaybackStateCompat.STATE_PAUSED) {
                        playButton.setImageResource(iconImage[PlayActivity.buttonPos.play.ordinal()]);
                        if (CurrentArtist.getInstance().isComplete())
                        {
                            Log.d(TAG, "Playback Complete!!");
                            CurrentArtist.getInstance().setComplete(false);
                            playbackComplete();
                        }
                        else
                        {
                            Log.d(TAG, "Not Complete!!");
                        }
                    }

                    if (state.getState() == PlaybackStateCompat.STATE_STOPPED) {
                        Log.d(TAG, "State_Stopped!!");
                    }
                }
            };

    private void playbackComplete()
    {
        QueueList queueList = new QueueList(this);
        String [] queued = queueList.getQueuedTitles(CurrentArtist.getInstance().getCurrentArtist());
        for (String queuedTitle : queued)
        {
            if (queuedTitle.contentEquals(CurrentArtist.getInstance().getCurrentTitle()))
            {
                queueList.remove(CurrentArtist.getInstance().getCurrentArtist(), CurrentArtist.getInstance().getCurrentTitle());
            }
        }

        String [] nextQueued = queueList.getQueuedTitles(CurrentArtist.getInstance().getCurrentArtist());

        if (!nextQueued[0].contentEquals("No queued shows.")) {
            Log.d(TAG, "Queue length is: " + nextQueued.length);
            Log.d(TAG, "Queue: " + nextQueued[0]);
            this.recreate();
        }
        else
        {
            Log.d(TAG, "Nothing in queue, closing activity");
            finish();
        }
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

    private void setImageId(String imageName)
    {
        //to retrieve image in res/drawable and set image in ImageView
        int resID = getResources().getIdentifier(imageName, "mipmap", "com.RuffinApps.johnnie.oldtimeradio");
        CurrentArtist.getInstance().setCurrentImage(resID);
    }

    // progress bar functions

    public void enableProgress() {
        sb.setMax(CurrentArtist.getInstance().getCurrentDuration());
        String myDuration = "00:00:00";

        try {
            myDuration = getDurationInFormat(CurrentArtist.getInstance().getCurrentDuration());
        } catch (Exception e) {
            Log.e(TAG, "Exception_enableProgress: " + e);
        }
        duration.setText(myDuration.toString());
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

        long currentPosition = mediaController.getPlaybackState().getPosition();

        if (mediaController.getPlaybackState().getState() == PlaybackStateCompat.STATE_PLAYING) {
            currentPosition = mediaController.getPlaybackState().getBufferedPosition();
        }

        String myCurPos;
        try {
            myCurPos = getDurationInFormat((int)currentPosition);
        } catch (IllegalStateException e) {
            Log.e(TAG, "IllegalStateException_runProgress: " + e);
            return;
        }
        curPos.setText(myCurPos.toString());
        sb.setProgress((int)currentPosition);
        seekHandler.postDelayed(run, 1000);
    }

    public String getDurationInFormat(int duration) {
        String durationInFormat;
        durationInFormat = String.format(Locale.getDefault(),"%02d:%02d:%02d",
                TimeUnit.MILLISECONDS.toHours(duration),
                TimeUnit.MILLISECONDS.toMinutes(duration) -
                        TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(duration)),
                TimeUnit.MILLISECONDS.toSeconds(duration) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration)));

        return durationInFormat;
    }
}
