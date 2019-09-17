package com.RuffinApps.johnnie.oldtimeradio;

import android.app.Notification;
import android.content.Intent;
import android.media.session.PlaybackState;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import android.support.v4.media.MediaBrowserCompat;
import androidx.media.MediaBrowserServiceCompat;
import android.support.v4.media.MediaDescriptionCompat;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Log;
import android.view.KeyEvent;

import java.util.ArrayList;
import java.util.List;

public class JOTRService extends MediaBrowserServiceCompat {
    private static final String TAG = JOTRService.class.getSimpleName();

    private MediaSessionCompat mSession;
    private MediaPlayerAdapter mPlayback;
    private MediaNotificationManager mMediaNotificationManager;
    private MediaSessionCallback mCallback;
    private boolean mServiceInStartedState;
    // private boolean haltRun = false;
    Handler seekHandler = new Handler();
    PlaybackStateCompat mPbState;

    boolean categoryReady = true;
    boolean ArtistReady = false;
    boolean showReady = false;
    boolean playReady = false;

    RadioTitle radioList = RadioTitle.getInstance();

    @Override
    public void onCreate() {
        super.onCreate();

        // Create a new MediaSession.
        mSession = new MediaSessionCompat(this, "JOTRService");
        mCallback = new MediaSessionCallback();
        mSession.setCallback(mCallback);

        mSession.setFlags(MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS |
                MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);

        mPbState = new PlaybackStateCompat.Builder()
                .setActions(PlaybackStateCompat.ACTION_PLAY_PAUSE)
                .setActions(PlaybackStateCompat.ACTION_FAST_FORWARD)
                 .setActions(PlaybackStateCompat.ACTION_REWIND).build();

        mSession.setPlaybackState(mPbState);

        setSessionToken(mSession.getSessionToken());

        // setTransportControls();

        mMediaNotificationManager = new MediaNotificationManager(this);

        mPlayback = new MediaPlayerAdapter(this, new MediaPlayerListener());

        // mCallback.onAddQueueItem(MusicLibrary.getMetadata(getApplicationContext(), CurrentArtist.getInstance().getCurrentTitle()).getDescription());
        Log.d(TAG, "onCreate: JOTRService creating MediaSession, and MediaNotificationManager");
        Log.d(TAG, "CurrentTitle: " + CurrentArtist.getInstance().getCurrentTitle());
    }

    private void setTransportControls()
    {
        Log.d(TAG, "setTransportControls");
    }

    public void setLastDescription()
    {
        CurrentArtist.getInstance().setLastDescription(MusicLibrary.getMetadata(getApplicationContext(), CurrentArtist.getInstance().getCurrentTitle()).getDescription());
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        stopSelf();
    }

    @Override
    public void onDestroy() {
        mMediaNotificationManager.onDestroy();
        mPlayback.stop();
        mSession.release();
        Log.d(TAG, "onDestroy: MediaPlayerAdapter stopped, and MediaSession released");
    }

    @Override
    public BrowserRoot onGetRoot(@NonNull String clientPackageName,
                                 int clientUid,
                                 Bundle rootHints) {
        return new BrowserRoot(MusicLibrary.getRoot(), null);
    }

    @Override
    public void onLoadChildren(
            @NonNull final String parentMediaId,
            @NonNull final Result<List<MediaBrowserCompat.MediaItem>> result) {
        Log.d(TAG, "onLoadChildren");
        Log.d(TAG, "parentMediaId: " + parentMediaId);
        Log.d(TAG, "Result: " + result);

        if (parentMediaId.contentEquals("root"))
        {
            categoryReady = true;
            ArtistReady = false;
            showReady = false;
            playReady = false;

        }

        if (ArtistReady) {
            String genre = CurrentArtist.getInstance().getCurrentCategory();
            Log.d(TAG, "Current Artist: " + parentMediaId);

            if (genre.contentEquals("Comedy")) {

                for (String show : radioList.comedyCat) {
                    if (parentMediaId.contentEquals(show)) {
                        CurrentArtist.getInstance().setCurrentArtist(show, getApplicationContext());
                        showReady = true;
                        ArtistReady = false;
                    }
                }
            }

            if (genre.contentEquals("Science Fiction")) {

                for (String show: radioList.scifiCat) {
                    if (parentMediaId.contentEquals(show)) {
                        CurrentArtist.getInstance().setCurrentArtist(show, getApplicationContext());
                        showReady = true;
                        ArtistReady = false;
                    }

                }
            }

            if (genre.contentEquals("Thriller")) {

                for (String show : radioList.thrillerCat) {
                    if (parentMediaId.contentEquals(show)) {
                        CurrentArtist.getInstance().setCurrentArtist(show, getApplicationContext());
                        showReady = true;
                        ArtistReady = false;
                    }
                }
            }

            if (genre.contentEquals("Western")) {

                for (String show : radioList.westernCat) {
                    if (parentMediaId.contentEquals(show)) {
                        CurrentArtist.getInstance().setCurrentArtist(show, getApplicationContext());
                        showReady = true;
                        ArtistReady = false;
                    }
                }
            }
        }

        switch (parentMediaId)
        {
            case "Comedy":
                Log.d(TAG, "Comedy Selected");
                CurrentArtist.getInstance().setCurrentCategory("Comedy");
                // radioList.init(getApplicationContext());
                radioList.loadJSONComedy(getApplicationContext());
                radioList.updateComedyTitles();
                result.sendResult(MusicLibrary.getBrowseArtistMediaItems());
                ArtistReady = true;
                categoryReady = false;
                playReady = false;
                showReady = false;
                break;
            case "Science Fiction":
                Log.d(TAG, "Science Fiction Selected");
                CurrentArtist.getInstance().setCurrentCategory("Science Fiction");
                // radioList.init(getApplicationContext());
                radioList.loadJSONSciFi(getApplicationContext());
                radioList.updateSciFiTitles();
                result.sendResult(MusicLibrary.getBrowseArtistMediaItems());
                ArtistReady = true;
                categoryReady = false;
                playReady = false;
                showReady = false;
                break;

            case "Thriller":
                Log.d(TAG, "Thriller Selected");
                CurrentArtist.getInstance().setCurrentCategory("Thriller");
                // radioList.init(getApplicationContext());
                radioList.loadJSONThriller(getApplicationContext());
                radioList.updateThrillerTitles();
                result.sendResult(MusicLibrary.getBrowseArtistMediaItems());
                ArtistReady = true;
                categoryReady = false;
                playReady = false;
                showReady = false;
                break;

            case "Western":
                Log.d(TAG, "Western Selected");
                CurrentArtist.getInstance().setCurrentCategory("Western");
                // radioList.init(getApplicationContext());
                radioList.loadJSONWestern(getApplicationContext());
                radioList.updateWesternTitles();
                result.sendResult(MusicLibrary.getBrowseArtistMediaItems());
                ArtistReady = true;
                categoryReady = false;
                playReady = false;
                showReady = false;
                break;

            default:
                Log.d(TAG, "In default: " + parentMediaId);
                break;
        }

        if (showReady)
        {
            MusicLibrary.clearLibraryItems();
            MusicLibrary.setMediaMetaData();

            result.sendResult(MusicLibrary.getMediaItems());
            playReady = true;
        }

        if (categoryReady) {
            result.sendResult(MusicLibrary.getBrowseCatMediaItems());
        }
    }

    // MediaSession Callback: Transport Controls -> MediaPlayerAdapter
    public class MediaSessionCallback extends MediaSessionCompat.Callback {
        private final List<MediaSessionCompat.QueueItem> mPlaylist = new ArrayList<>();
        private int mQueueIndex = -1;
        private MediaMetadataCompat mPreparedMedia;

        // Log.d(TAG, "In MediaSession Cb!!");

        @Override
        public void onPlayFromMediaId(String mediaId, Bundle extras) {
            Log.d(TAG, "onPlayFromMediaId!!: " + mediaId);

            if (playReady)
            {
                String[] radioShows = CurrentArtist.getInstance().getRadioTitles(CurrentArtist.getInstance().getCurrentArtist());

                for (String myshow : radioShows) {
                    if (myshow.contentEquals(mediaId)) {
                        CurrentArtist.getInstance().setCurrentTitle(myshow);
                        Log.d(TAG, "Show selected: " + myshow);
                    }
                }

                // populate the show information
                String title = CurrentArtist.getInstance().getCurrentTitle();
                String file = CurrentArtist.getInstance().getMediaFile(title);
                CurrentArtist.getInstance().setCurrentFile(file);

                MusicLibrary.setMediaMetaData();

                if (CurrentArtist.getInstance().getLastTitle() == null)
                {
                    CurrentArtist.getInstance().setLastTitle(CurrentArtist.getInstance().getCurrentTitle());
                    setLastDescription();
                }

                mCallback.onAddQueueItem(MusicLibrary.getMetadata(getApplicationContext(), CurrentArtist.getInstance().getCurrentTitle()).getDescription());
                onPlay();
            }
        }

        @Override
        public boolean onMediaButtonEvent(Intent mediaButtonIntent)
        {
            String intentAction = mediaButtonIntent.getAction();
            Log.d(TAG, "IntentAction: " + intentAction);

            KeyEvent event = mediaButtonIntent.getParcelableExtra(Intent.EXTRA_KEY_EVENT);
            if (event != null)
            {
                int keycode = event.getKeyCode();
                switch(keycode)
                {
                    case KeyEvent.KEYCODE_MEDIA_PAUSE:
                    {
                        int pbState = mSession.getController().getPlaybackState().getState();
                        if (pbState == PlaybackStateCompat.STATE_PLAYING) {
                            mSession.getController().getTransportControls().pause();
                        } else {
                            mSession.getController().getTransportControls().play();
                        }

                        Log.d(TAG, "KEYCODE_MEDIA_PAUSE");
                        break;
                    }
                    case KeyEvent.KEYCODE_MEDIA_PLAY:
                    {
                        mSession.getController().getTransportControls().play();
                        Log.d(TAG, "KEYCODE_MEDIA_PLAY");
                        break;
                    }

                    case KeyEvent.KEYCODE_MEDIA_NEXT:
                    {
                        mSession.getController().getTransportControls().skipToNext();
                        Log.d(TAG, "KEYCODE_MEDIA_NEXT");
                        break;
                    }

                    case KeyEvent.KEYCODE_MEDIA_PREVIOUS:
                    {
                        mSession.getController().getTransportControls().skipToPrevious();
                        Log.d(TAG, "KEYCODE_MEDIA_PREVIOUS");
                        break;
                    }

                    case KeyEvent.KEYCODE_MEDIA_FAST_FORWARD:
                    {
                        if (mPlayback.isPlaying()) {
                            int position = mPlayback.getCurrentPosition();
                            mSession.getController().getTransportControls().seekTo(position + 15000);
                        }
                        Log.d(TAG, "KEYCODE_MEDIA_FAST_FORWARD");
                        break;
                    }

                    case KeyEvent.KEYCODE_MEDIA_REWIND:
                    {
                        if (mPlayback.isPlaying()) {
                            int position = mPlayback.getCurrentPosition();
                            mSession.getController().getTransportControls().seekTo(position - 15000);
                        }
                        Log.d(TAG, "KEYCODE_MEDIA_FAST_FORWARD");
                        break;
                    }

                    default:
                    {
                        Log.d(TAG, "Unknown keycode: " + keycode);
                        break;
                    }
                }
            }
            return true;
        }

        @Override
        public void onAddQueueItem(MediaDescriptionCompat description) {
            MediaSessionCompat.QueueItem qt = new MediaSessionCompat.QueueItem(description, description.hashCode());
            mPlaylist.add(qt);
            mQueueIndex = (mQueueIndex == -1) ? 0 : mQueueIndex;
            mSession.setQueue(mPlaylist);

            Log.d(TAG, "Description Hashcode: " + description.hashCode());
            Log.d(TAG, "OnAddQueueItem: " + qt);
        }

        @Override
        public void onRemoveQueueItem(MediaDescriptionCompat description) {
            if (mPlaylist.isEmpty())
            {
                return;
            }
            Log.d(TAG, "Removing item: " + description);
            mPlaylist.remove(0);
            mQueueIndex = (mPlaylist.isEmpty()) ? -1 : mQueueIndex;
            mSession.setQueue(mPlaylist);
        }

        @Override
        public void onPrepare() {
            if (mQueueIndex < 0 && mPlaylist.isEmpty()) {
                // Nothing to play.
                Log.d(TAG, "onPrepare: Queue is empty. Nothing to play. Returning.");
                return;
            }

            final String mediaId = mPlaylist.get(mQueueIndex).getDescription().getMediaId();
            mPreparedMedia = MusicLibrary.getMetadata(JOTRService.this, mediaId);
            mSession.setMetadata(mPreparedMedia);

            if (!mSession.isActive()) {
                mSession.setActive(true);
                Log.d(TAG, "Media Session is active!!");
            }
            Log.d(TAG, "OnPrepare");
        }

        @Override
        public void onPlay() {

            if (CurrentArtist.getInstance().getLastTitle() == null)
            {
                Log.d(TAG, "Get Last Title equals null!!");
                CurrentArtist.getInstance().setLastTitle("");
            }

            if (CurrentArtist.getInstance().getCurrentTitle() == null)
            {
                Log.d(TAG, "Get Current Title equals null!!");
                return;
            }
            if (!CurrentArtist.getInstance().getLastTitle().contentEquals(CurrentArtist.getInstance().getCurrentTitle())) {
                mCallback.onRemoveQueueItem(CurrentArtist.getInstance().getLastDescription());
                mCallback.onAddQueueItem(MusicLibrary.getMetadata(getApplicationContext(), CurrentArtist.getInstance().getCurrentTitle()).getDescription());
                setLastDescription();
                Log.d(TAG, "CurrentTitle: " + CurrentArtist.getInstance().getCurrentTitle());
                if (CurrentArtist.getInstance().getLastTitle() != null)
                {
                    Log.d(TAG, "LastTitle: " + CurrentArtist.getInstance().getLastTitle());
                }
                CurrentArtist.getInstance().setLastTitle(CurrentArtist.getInstance().getCurrentTitle());
                mPreparedMedia = null;
            }
            else
            {
                Log.d(TAG, "It's all good! Playing the same title!!");
            }

            if (!isReadyToPlay()) {
                // Nothing to play.
                Log.d(TAG, "onPlay: Not ready to play!!");
                return;
            }

            Log.d(TAG, "onPlay!!");

            if (mPreparedMedia == null) {
                onPrepare();
            }

            mPlayback.playFromMedia(mPreparedMedia);
            updateState();
        }

        @Override
        public void onPause() {

            Log.d(TAG, "OnPause");

            if (!mPlayback.isPlaying())
            {
                Log.d(TAG, "Not Playing!!");
                return;
            }

            int position = mPlayback.getCurrentPosition();
            Log.d(TAG, "Pause Playback: " + position);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                PlaybackStateCompat mPlayback = new PlaybackStateCompat.Builder()
                        .setState(PlaybackStateCompat.STATE_PAUSED, position, 1)
                        .setBufferedPosition(position).build();

                mSession.setPlaybackState(mPlayback);
            }

            mPlayback.pause();
        }

        @Override
        public void onStop() {
            // Toast.makeText(getApplicationContext(), "OnStop", Toast.LENGTH_SHORT);
            mPlayback.stop();
            mSession.setActive(false);
            Log.d(TAG, "OnStop");
        }

        @Override
        public void onSkipToNext() {
            // Toast.makeText(getApplicationContext(), "OnSkipToNext", Toast.LENGTH_SHORT);
            mQueueIndex = (++mQueueIndex % mPlaylist.size());
            mPreparedMedia = null;
            onPlay();
            Log.d(TAG, "OnSkipToNext");
        }

        @Override
        public void onSkipToPrevious() {
            // Toast.makeText(getApplicationContext(), "OnSkipToPrevious", Toast.LENGTH_SHORT);
            mQueueIndex = mQueueIndex > 0 ? mQueueIndex - 1 : mPlaylist.size() - 1;
            mPreparedMedia = null;
            onPlay();
            Log.d(TAG, "OnSkipToPrevious");
        }

        @Override
        public void onSeekTo(long pos) {
            // Toast.makeText(getApplicationContext(), "OnSeekTo", Toast.LENGTH_SHORT);
            mPlayback.seekTo(pos);
            Log.d(TAG, "OnSeekTo");
        }

        private boolean isReadyToPlay() {
            Log.d(TAG, "isReadyToPlay");
            Log.d(TAG, "PlayList: " + mPlaylist.toString());
            return (!mPlaylist.isEmpty());
        }
    }

    Runnable run = new Runnable() {
        @Override
        public void run() {

            try {
                updateState();
            } catch (IllegalStateException e) {
                Log.e(TAG, "IllegalStateException_run: " + e);
            }
        }
    };

    private void updateState() {
        if (mSession.getController().getPlaybackState() != null) {
            if (mSession.getController().getPlaybackState().getState() == PlaybackStateCompat.STATE_PLAYING) {
                mPlayback.setNewState(PlaybackStateCompat.STATE_PLAYING);
            }
        }
        seekHandler.postDelayed(run, 1000);
    }

    // MediaPlayerAdapter Callback: MediaPlayerAdapter state -> MusicService.
    public class MediaPlayerListener extends PlaybackInfoListener {

        private final ServiceManager mServiceManager;

        MediaPlayerListener() {
            mServiceManager = new ServiceManager();
        }

        @Override
        public void onPlaybackStateChange(PlaybackStateCompat state) {
            // Report the state to the MediaSession.
            mSession.setPlaybackState(state);

           // Log.d(TAG, "State Change: " + state);
            // Manage the started state of this service.
            switch (state.getState()) {
                case PlaybackStateCompat.STATE_PLAYING:
                    mServiceManager.moveServiceToStartedState(state);
                    CurrentArtist.getInstance().setPlaying(true);
                    // Log.d(TAG, "STATE_PLAYING");
                    break;
                case PlaybackStateCompat.STATE_PAUSED:
                    mServiceManager.updateNotificationForPause(state);
                    Log.d(TAG, "STATE_PAUSED");
                    break;
                case PlaybackStateCompat.STATE_STOPPED:
                    mServiceManager.moveServiceOutOfStartedState(state);
                    CurrentArtist.getInstance().setPlaying(false);
                    Log.d(TAG, "STATE_STOPPED");
                    break;
                case PlaybackStateCompat.STATE_BUFFERING:
                    Log.d(TAG, "STATE_BUFFERING");
                    break;
                case PlaybackStateCompat.STATE_CONNECTING:
                    Log.d(TAG, "STATE_CONNECTING");
                    break;
                case PlaybackStateCompat.STATE_FAST_FORWARDING:
                    Log.d(TAG, "STATE_FAST_FORWARDING");
                    break;
                case PlaybackStateCompat.STATE_NONE:
                    Log.d(TAG, "STATE_NONE");
                    break;
                case PlaybackStateCompat.STATE_REWINDING:
                    Log.d(TAG, "STATE_REWINDING");
                    break;
                case PlaybackStateCompat.STATE_SKIPPING_TO_NEXT:
                    Log.d(TAG, "STATE_SKIPPING_TO_NEXT");
                    break;
                case PlaybackStateCompat.STATE_SKIPPING_TO_PREVIOUS:
                    Log.d(TAG, "STATE_SKIPPING_TO_PREVIOUS");
                    break;
                case PlaybackStateCompat.STATE_ERROR:
                    Log.d(TAG, "STATE_ERROR");
                    break;
                case PlaybackStateCompat.STATE_SKIPPING_TO_QUEUE_ITEM:
                    Log.d(TAG, "STATE_SKIPPING_TO_QUEUE_ITEM");
                    break;
            }
        }

        class ServiceManager {

            private void moveServiceToStartedState(PlaybackStateCompat state) {
                // Log.d(TAG, "moveServiceToStartedState!!");
                Notification notification =
                        mMediaNotificationManager.getNotification(
                                mPlayback.getCurrentMedia(), state, getSessionToken());

                if (!mServiceInStartedState) {
                    ContextCompat.startForegroundService(
                            JOTRService.this,
                            new Intent(JOTRService.this, JOTRService.class));
                    mServiceInStartedState = true;
                    Log.d(TAG, "JOTR Service is started here!!");
                }

                startForeground(MediaNotificationManager.NOTIFICATION_ID, notification);
            }

            private void updateNotificationForPause(PlaybackStateCompat state) {
                Log.d(TAG, "updateNotificationForPause!!");
                stopForeground(false);
                Notification notification =
                        mMediaNotificationManager.getNotification(
                                mPlayback.getCurrentMedia(), state, getSessionToken());
                mMediaNotificationManager.getNotificationManager()
                        .notify(MediaNotificationManager.NOTIFICATION_ID, notification);
            }

            private void moveServiceOutOfStartedState(PlaybackStateCompat state) {
                Log.d(TAG, "moveServiceOutOfStartedState!!" + state);
                stopForeground(true);
                stopSelf();
                mServiceInStartedState = false;
            }
        }
    }
}
