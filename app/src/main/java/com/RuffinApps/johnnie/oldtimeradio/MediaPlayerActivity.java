package com.RuffinApps.johnnie.oldtimeradio;

import android.app.Activity;
import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioAttributes;
import android.media.AudioFocusRequest;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.service.media.MediaBrowserService;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

public class MediaPlayerActivity extends AppCompatActivity {
    private MediaBrowserCompat mMediaBrowser;
    private Notification mNotificaiton;
    private ImageButton playButton;
    private Integer[] iconImage;
    private JOTRPlaybackService jService;
    private int id = 599;
    private Context mContext = this;

    // callback variables
    private IntentFilter intentFilter = new IntentFilter(AudioManager.ACTION_AUDIO_BECOMING_NOISY);
    private AudioManager.OnAudioFocusChangeListener afChangeListener;
    private BecomingNoisyReceiver myNoisyAudioStreamReceiver = new BecomingNoisyReceiver();
    // private Notification myPlayerNotification;
    private MediaSessionCompat mediaSession;
    private MediaBrowserService service;
    private MediaPlayer player;
    private AudioFocusRequest audioFocusRequest;


    private String TAG = "MediaPlayerActivity: ";

    MediaControllerCompat.Callback controllerCallback =
            new MediaControllerCompat.Callback() {
                @Override
                public void onMetadataChanged(MediaMetadataCompat metadata) {
                    Log.d(TAG, "OnMetadataChanged");
                }

                @Override
                public void onPlaybackStateChanged(PlaybackStateCompat state) {
                    Log.d(TAG, "OnPlaybackStateChanged");
                }
            };

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // ...
        // Create MediaBrowserServiceCompat
        mMediaBrowser = new MediaBrowserCompat(this,
                new ComponentName(this, JOTRPlaybackService.class),
                mConnectionCallbacks,
                null); // optional Bundle

        // image control
        ImageControl iconControl = new ImageControl();
        iconImage = iconControl.getImageButtonList();
        playButton = (ImageButton) findViewById(R.id.play_button);
        // playButton.setImageResource(R.mipmap.play50);
        jService.startForeground(id, mNotificaiton);
    }

    @Override
    public void onStart() {
        super.onStart();
        mMediaBrowser.connect();
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
        // TODO: Work on controllerCallback
        if (MediaControllerCompat.getMediaController(MediaPlayerActivity.this) != null) {
            MediaControllerCompat.getMediaController(MediaPlayerActivity.this).unregisterCallback(controllerCallback);
        }
        mMediaBrowser.disconnect();

    }

    private final MediaBrowserCompat.ConnectionCallback mConnectionCallbacks =
            new MediaBrowserCompat.ConnectionCallback() {
                @Override
                public void onConnected() {
                    try {
                        // Get the token for the MediaSession
                        MediaSessionCompat.Token token = mMediaBrowser.getSessionToken();

                        // Create a MediaControllerCompat
                        MediaControllerCompat mediaController =
                                new MediaControllerCompat(MediaPlayerActivity.this, // Context
                                        token);

                        // Save the controller
                        MediaControllerCompat.setMediaController(MediaPlayerActivity.this, mediaController);

                        // Finish building the UI
                        buildTransportControls();
                    }
                    catch (Exception e) {
                        Log.d(TAG, e.toString());
                    }
                }

                @Override
                public void onConnectionSuspended() {
                    // The Service has crashed. Disable transport controls until it automatically reconnects
                }

                @Override
                public void onConnectionFailed() {
                    // The Service has refused our connection
                }
            };

    // Build gui controls
    void buildTransportControls()
    {
        // Grab the view for the play/pause button
        playButton = findViewById(R.id.play_button);

        // Attach a listener to the button
        playButton.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View v) {
                                              // Since this is a play/pause button, you'll need to test the current state
                                              // and choose the action accordingly

                                              int pbState = MediaControllerCompat.getMediaController(MediaPlayerActivity.this).getPlaybackState().getState();
                                              if (pbState == PlaybackStateCompat.STATE_PLAYING) {
                                                  MediaControllerCompat.getMediaController(MediaPlayerActivity.this).getTransportControls().pause();
                                                  playButton.setImageResource(iconImage[MediaPlayerActivity.buttonPos.pause.ordinal()]);
                                              } else {
                                                  MediaControllerCompat.getMediaController(MediaPlayerActivity.this).getTransportControls().play();
                                                  playButton.setImageResource(iconImage[MediaPlayerActivity.buttonPos.play.ordinal()]);
                                              }
                                          }
                                      });
        MediaControllerCompat mediaController = MediaControllerCompat.getMediaController(MediaPlayerActivity.this);

        // Display the initial state
        MediaMetadataCompat metadata = mediaController.getMetadata();
        PlaybackStateCompat pbState = mediaController.getPlaybackState();

        // Register a Callback to stay in sync
        // TODO: Work on controllerCallback
        // mediaController.registerCallback(controllerCallback);
        }

    private class BecomingNoisyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (AudioManager.ACTION_AUDIO_BECOMING_NOISY.equals(intent.getAction())) {
                player.pause();
                // Pause the playback
            }
        }
    }


}
