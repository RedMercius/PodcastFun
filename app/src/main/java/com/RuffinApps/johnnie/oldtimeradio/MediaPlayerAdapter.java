package com.RuffinApps.johnnie.oldtimeradio;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.SystemClock;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Log;

/**
 * Exposes the functionality of the {@link MediaPlayer} and implements the {@link PlayerAdapter}
 * so that {@link MainActivity} can control music playback.
 */
public final class MediaPlayerAdapter extends PlayerAdapter {

    private final Context mContext;
    private MediaPlayer mMediaPlayer;
    private String mFilename;
    private PlaybackInfoListener mPlaybackInfoListener;
    private MediaMetadataCompat mCurrentMedia;
    private int mState;
    private boolean mCurrentMediaPlayedToCompletion;
    private PlayedList pl;

    private String TAG = "MediaPlayerAdapter: ";

    // Work-around for a MediaPlayer bug related to the behavior of MediaPlayer.seekTo()
    // while not playing.
    private int mSeekWhileNotPlaying = -1;

    public MediaPlayerAdapter(Context context, PlaybackInfoListener listener) {
        super(context);
        mContext = context.getApplicationContext();
        mPlaybackInfoListener = listener;
        Log.d(TAG, "MediaPlayerAdapter");
    }

    /**
     * Once the {@link MediaPlayer} is released, it can't be used again, and another one has to be
     * created. In the onStop() method of the {@link MainActivity} the {@link MediaPlayer} is
     * released. Then in the onStart() of the {@link MainActivity} a new {@link MediaPlayer}
     * object has to be created. That's why this method is private, and called by load(int) and
     * not the constructor.
     */
    private void initializeMediaPlayer() {
        if (mMediaPlayer == null) {
            mMediaPlayer = new MediaPlayer();
            mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    mPlaybackInfoListener.onPlaybackCompleted();
                    mCurrentMediaPlayedToCompletion = true;

                    // Set the state to "paused" because it most closely matches the state
                    // in MediaPlayer with regards to available state transitions compared
                    // to "stop".
                    // Paused allows: seekTo(), start(), pause(), stop()

                    setNewState(PlaybackStateCompat.STATE_PAUSED);
                    CurrentArtist.getInstance().setComplete(true);
                    Log.d(TAG, "Play Complete. Play Next in Queue");
                    pl = new PlayedList(mContext);
                    pl.add(0, CurrentArtist.getInstance().getCurrentArtist(), CurrentArtist.getInstance().getCurrentTitle());
                    QueueList queueList = new QueueList(mContext);
                    String [] queued = queueList.getQueuedTitles(CurrentArtist.getInstance().getCurrentArtist());
                    for (String queuedTitle : queued)
                    {
                        if (queuedTitle.contentEquals(CurrentArtist.getInstance().getCurrentTitle()))
                        {
                            queueList.remove(CurrentArtist.getInstance().getCurrentArtist(), CurrentArtist.getInstance().getCurrentTitle());
                        }
                    }

                    String [] nextQueued = queueList.getQueuedTitles(CurrentArtist.getInstance().getCurrentArtist());

                    if (!nextQueued[0].contentEquals("No queued shows."))
                    {
                        Log.d(TAG, "Playing next in queue: " + nextQueued[0]);
                        CurrentArtist.getInstance().setCurrentTitle(nextQueued[0]);
                        MusicLibrary.clearLibraryItems();
                        MusicLibrary.setMediaMetaData();
                        play();
                        // Log.d(TAG, "Playing next in queue: " + nextQueued[0]);
                    }
                }
            });

            mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {

                    MediaPlayerAdapter.super.play();
                    mp.start();
                    setNewState(PlaybackStateCompat.STATE_PLAYING);
                    CurrentArtist.getInstance().setCurrentDuration(mMediaPlayer.getDuration());
                    Log.d(TAG, "OnPrepared!! Duration: " + CurrentArtist.getInstance().getCurrentDuration());

                }
            });
        }
    }

    // Implements PlaybackControl.
    @Override
    public void playFromMedia(MediaMetadataCompat metadata) {
        Log.d(TAG, "Play from Media: " + metadata.getDescription());
        mCurrentMedia = metadata;
        final String mediaId = metadata.getDescription().getMediaId();
        playFile(MusicLibrary.getMusicFilename(mediaId));
    }

    @Override
    public MediaMetadataCompat getCurrentMedia() {
        return mCurrentMedia;
    }

    private void playFile(String filename) {
        Log.d(TAG, "PlayFile: " + filename);
        boolean mediaChanged = (mFilename == null || !filename.equals(mFilename));

        if (mCurrentMediaPlayedToCompletion) {
            // Last audio file was played to completion, the resourceId hasn't changed, but the
            // player was released, so force a reload of the media file for playback.
            mediaChanged = true;
            mCurrentMediaPlayedToCompletion = false;
        }
        if (!mediaChanged) {
            if (!isPlaying()) {
                //play();
                // resume media
                resume();
                mMediaPlayer.start();
                Log.d(TAG, "Media is not changed or playing");
            }
            Log.d(TAG, "Returning from playFile for some reason!!");
            return;
        } else if (mediaChanged && mMediaPlayer != null) {
            release();
            Log.d(TAG, "Media is changing and Media Player is released!!");
        }

        mFilename = filename;

        initializeMediaPlayer();

        try {
            mMediaPlayer.setDataSource(mFilename);
            Log.d(TAG, "Data source set");

            mMediaPlayer.prepareAsync();
            Log.d(TAG, "PrepareAsync!!");
        }catch (Exception e) {
            throw new RuntimeException("Failed to open file: " + mFilename, e);
        }
    }

    @Override
    public void onStop() {
        // Regardless of whether or not the MediaPlayer has been created / started, the state must
        // be updated, so that MediaNotificationManager can take down the notification.
        setNewState(PlaybackStateCompat.STATE_STOPPED);
        release();
        Log.d(TAG, "onStop");
    }

    private void release() {
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
            mMediaPlayer = null;
            Log.d(TAG, "relase media player");
        }
    }

    @Override
    public boolean isPlaying() {
        Log.d(TAG, "isPlaying");
        return mMediaPlayer != null && mMediaPlayer.isPlaying();
    }

    @Override
    protected void onResume() {
        mMediaPlayer.start();
        setNewState(PlaybackStateCompat.STATE_PLAYING);
        Log.d(TAG, "onResume!!");
    }

    @Override
    protected void onPlay() {
        Log.d(TAG, "onPlay");
    }

    @Override
    protected void onPause() {
        Log.d(TAG, "onPause");
        if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
            mMediaPlayer.pause();
            setNewState(PlaybackStateCompat.STATE_PAUSED);
        }
    }

    public int getCurrentPosition()
    {
        return mMediaPlayer.getCurrentPosition();
    }

    // This is the main reducer for the player state machine.
    public void setNewState(@PlaybackStateCompat.State int newPlayerState) {
        // Log.d(TAG, "setNewState: " + newPlayerState);
        mState = newPlayerState;

        // Whether playback goes to completion, or whether it is stopped, the
        // mCurrentMediaPlayedToCompletion is set to true.
        if (mState == PlaybackStateCompat.STATE_STOPPED) {
            mCurrentMediaPlayedToCompletion = true;
        }

        // Work around for MediaPlayer.getCurrentPosition() when it changes while not playing.
        final long reportPosition;
        if (mSeekWhileNotPlaying >= 0) {
            reportPosition = mSeekWhileNotPlaying;

            if (mState == PlaybackStateCompat.STATE_PLAYING) {
                mSeekWhileNotPlaying = -1;
            }
        } else {
            reportPosition = mMediaPlayer == null ? 0 : mMediaPlayer.getCurrentPosition();
        }

        // Log.d(TAG, "Report Position: " + reportPosition);

        final PlaybackStateCompat.Builder stateBuilder = new PlaybackStateCompat.Builder();
        stateBuilder.setActions(getAvailableActions());
        if (mMediaPlayer != null) {
            if (mMediaPlayer.isPlaying()) {
                stateBuilder.setBufferedPosition(mMediaPlayer.getCurrentPosition());
                if (CurrentArtist.getInstance().isAd())
                {
                    mMediaPlayer.pause();
                }
            }
        }
        stateBuilder.setState(mState,
                reportPosition,
                1.0f,
                SystemClock.elapsedRealtime());
        mPlaybackInfoListener.onPlaybackStateChange(stateBuilder.build());
    }

    /**
     * Set the current capabilities available on this session. Note: If a capability is not
     * listed in the bitmask of capabilities then the MediaSession will not handle it. For
     * example, if you don't want ACTION_STOP to be handled by the MediaSession, then don't
     * included it in the bitmask that's returned.
     */
    @PlaybackStateCompat.Actions
    private long getAvailableActions() {
        // Log.d(TAG, "getAvailableActions: State: " + mState);
        long actions = PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID
                | PlaybackStateCompat.ACTION_PLAY_FROM_SEARCH
                | PlaybackStateCompat.ACTION_SKIP_TO_NEXT
                | PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS
                | PlaybackStateCompat.ACTION_PLAY
                | PlaybackStateCompat.ACTION_PAUSE
                | PlaybackStateCompat.ACTION_REWIND
                | PlaybackStateCompat.ACTION_FAST_FORWARD
                | PlaybackStateCompat.ACTION_SEEK_TO;
        switch (mState) {
            case PlaybackStateCompat.STATE_STOPPED:
                Log.d(TAG, "State_Stopped");
                actions |= PlaybackStateCompat.ACTION_PLAY
                        | PlaybackStateCompat.ACTION_PAUSE;
                break;
            case PlaybackStateCompat.STATE_PLAYING:
                // Log.d(TAG, "State_Playing");
                actions |= PlaybackStateCompat.ACTION_PAUSE
                        | PlaybackStateCompat.ACTION_FAST_FORWARD
                        | PlaybackStateCompat.ACTION_REWIND
                        | PlaybackStateCompat.ACTION_SEEK_TO;
                break;
            case PlaybackStateCompat.STATE_PAUSED:
                Log.d(TAG, "State_Paused");
                actions |= PlaybackStateCompat.ACTION_PLAY
                        | PlaybackStateCompat.ACTION_FAST_FORWARD
                        | PlaybackStateCompat.ACTION_REWIND
                        | PlaybackStateCompat.ACTION_SEEK_TO;
                break;
            default:
                Log.d(TAG, "Default Actions: " + actions);
                actions |= PlaybackStateCompat.ACTION_PLAY
                        | PlaybackStateCompat.ACTION_PAUSE
                        | PlaybackStateCompat.ACTION_FAST_FORWARD
                        | PlaybackStateCompat.ACTION_REWIND;
        }
        return actions;
    }

    @Override
    public void seekTo(long position) {
        if (mMediaPlayer != null) {
            if (!mMediaPlayer.isPlaying()) {
                mSeekWhileNotPlaying = (int) position;
            }
            mMediaPlayer.seekTo((int) position);

            // Set the state (to the current state) because the position changed and should
            // be reported to clients.
            setNewState(mState);
        }
    }

    @Override
    public void setVolume(float volume) {
        if (mMediaPlayer != null) {
            mMediaPlayer.setVolume(volume, volume);
        }
    }
}
