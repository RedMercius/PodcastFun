package com.RuffinApps.johnnie.oldtimeradio;

import android.support.v4.media.session.PlaybackStateCompat;
import android.support.v4.media.session.MediaSessionCompat;

import com.RuffinApps.johnnie.oldtimeradio.MediaPlayerAdapter;

/**
 * Listener to provide state updates from {@link MediaPlayerAdapter} (the media player)
 * to {@link JOTRPlaybackService} (the service that holds our {@link MediaSessionCompat}.
 */
public abstract class PlaybackInfoListener {

    public abstract void onPlaybackStateChange(PlaybackStateCompat state);

    public void onPlaybackCompleted() {
    }
}