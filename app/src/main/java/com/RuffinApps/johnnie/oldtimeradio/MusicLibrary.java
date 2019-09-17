package com.RuffinApps.johnnie.oldtimeradio;


import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.util.Currency;
import android.os.Environment;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.MediaDescriptionCompat;
import android.support.v4.media.MediaMetadataCompat;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;


public class MusicLibrary {

    private static final TreeMap<String, MediaMetadataCompat> music = new TreeMap<>();
    private static final TreeMap<String, MediaMetadataCompat> category = new TreeMap<>();
    private static final TreeMap<String, MediaMetadataCompat> show = new TreeMap<>();
    private static final HashMap<String, Integer> albumRes = new HashMap<>();
    private static final HashMap<String, String> musicFileName = new HashMap<>();

    private static RadioTitle radioList = RadioTitle.getInstance();
    private static String TAG = "MusicLibrary: ";

    private static String artist = "Burns And Allen";
    private static String mediaFile = "http://www.JohnnieRuffin.com/audio/baarehusbandsnecessary.mp3";
    private static String mediaTitle = "Are Husbands Necessary";
    private static int duration = 3215;
    private static String[] titles = null;
    private static String mfilePath;

    static {
        createMediaMetadataCompat(
                 mediaTitle,// CurrentArtist.getInstance().getCurrentTitle(),
                mediaTitle, //CurrentArtist.getInstance().getCurrentTitle(),
                artist, //CurrentArtist.getInstance().getCurrentArtist(),
                "Old Time Radio",
                "Old Time Radio",
                duration,
                TimeUnit.SECONDS,
                mediaFile,
                R.mipmap.burnsandallen,
                "BurnsAndAllen");
    }

    public static String getRoot() {
        return "root";
    }

    private static String getAlbumArtUri(String albumArtResName) {
        return ContentResolver.SCHEME_ANDROID_RESOURCE + "://" +
                BuildConfig.APPLICATION_ID + "/mipmap/" + albumArtResName;
    }

    public static String getMusicFilename(String mediaId) {
        return musicFileName.containsKey(mediaId) ? musicFileName.get(mediaId) : null;
    }

    private static int getAlbumRes(String mediaId) {
        return albumRes.containsKey(mediaId) ? albumRes.get(mediaId) : 0;
    }

    public static Bitmap getAlbumBitmap(Context context, String mediaId) {
        return BitmapFactory.decodeResource(context.getResources(),
                MusicLibrary.getAlbumRes(mediaId));
    }

    public static List<MediaBrowserCompat.MediaItem> getMediaItems() {
        List<MediaBrowserCompat.MediaItem> result = new ArrayList<>();
        for (MediaMetadataCompat metadata : music.values()) {
            result.add(
                    new MediaBrowserCompat.MediaItem(
                            metadata.getDescription(), MediaBrowserCompat.MediaItem.FLAG_PLAYABLE));
        }
        return result;
    }

    public static List<MediaBrowserCompat.MediaItem> getBrowseCatMediaItems() {
        List<MediaBrowserCompat.MediaItem> result = new ArrayList<>();
        for (int i = 0; i <= 3; i++) {
            MediaDescriptionCompat desc =
                    new MediaDescriptionCompat.Builder()
                            .setMediaId(radioList.category[i])
                            .setTitle(radioList.category[i])
                            .setSubtitle("")
                            .setDescription(radioList.category[i])
                            .build();
            result.add(
                    new MediaBrowserCompat.MediaItem(
                            desc, MediaBrowserCompat.MediaItem.FLAG_BROWSABLE));
        }
        return result;
    }

    public static List<MediaBrowserCompat.MediaItem> getBrowseArtistMediaItems() {
        List<MediaBrowserCompat.MediaItem> result = new ArrayList<>();
        result = getArtistResult();
        return result;
    }

    public static List<MediaBrowserCompat.MediaItem> getArtistResult()
    {
        List<MediaBrowserCompat.MediaItem> result = new ArrayList<>();
        if (CurrentArtist.getInstance().getCurrentCategory().equals("Comedy")) {
            for (int i = 0; i <= radioList.comedyCat.length - 1; i++) {
                MediaDescriptionCompat desc =
                        new MediaDescriptionCompat.Builder()
                                .setMediaId(radioList.comedyCat[i])
                                .setTitle(radioList.comedyCat[i])
                                .setSubtitle("")
                                .setDescription(radioList.comedyCat[i])
                                .build();
                result.add(
                        new MediaBrowserCompat.MediaItem(
                                desc, MediaBrowserCompat.MediaItem.FLAG_BROWSABLE));
            }
        }

        if (CurrentArtist.getInstance().getCurrentCategory().equals("Thriller")) {
            for (int i = 0; i <= radioList.thrillerCat.length - 1; i++) {
                MediaDescriptionCompat desc =
                        new MediaDescriptionCompat.Builder()
                                .setMediaId(radioList.thrillerCat[i])
                                .setTitle(radioList.thrillerCat[i])
                                .setSubtitle("")
                                .setDescription(radioList.thrillerCat[i])
                                .build();
                result.add(
                        new MediaBrowserCompat.MediaItem(
                                desc, MediaBrowserCompat.MediaItem.FLAG_BROWSABLE));
            }
        }

        if (CurrentArtist.getInstance().getCurrentCategory().equals("Science Fiction")) {
            for (int i = 0; i <= radioList.scifiCat.length - 1; i++) {
                MediaDescriptionCompat desc =
                        new MediaDescriptionCompat.Builder()
                                .setMediaId(radioList.scifiCat[i])
                                .setTitle(radioList.scifiCat[i])
                                .setSubtitle("")
                                .setDescription(radioList.scifiCat[i])
                                .build();
                result.add(
                        new MediaBrowserCompat.MediaItem(
                                desc, MediaBrowserCompat.MediaItem.FLAG_BROWSABLE));
            }
        }

        if (CurrentArtist.getInstance().getCurrentCategory().equals("Western")) {
            for (int i = 0; i <= radioList.westernCat.length - 1; i++) {
                MediaDescriptionCompat desc =
                        new MediaDescriptionCompat.Builder()
                                .setMediaId(radioList.westernCat[i])
                                .setTitle(radioList.westernCat[i])
                                .setSubtitle("")
                                .setDescription(radioList.westernCat[i])
                                .build();
                result.add(
                        new MediaBrowserCompat.MediaItem(
                                desc, MediaBrowserCompat.MediaItem.FLAG_BROWSABLE));
            }
        }
        return result;
    }

    public static MediaMetadataCompat getMetadata(Context context, String mediaId) {
        MediaMetadataCompat metadataWithoutBitmap = music.get(mediaId);
        Bitmap albumArt = getAlbumBitmap(context, mediaId);

        // Since MediaMetadataCompat is immutable, we need to create a copy to set the album art.
        // We don't set it initially on all items so that they don't take unnecessary memory.
        MediaMetadataCompat.Builder builder = new MediaMetadataCompat.Builder();
        for (String key :
                new String[]{
                        MediaMetadataCompat.METADATA_KEY_MEDIA_ID,
                        MediaMetadataCompat.METADATA_KEY_ALBUM,
                        MediaMetadataCompat.METADATA_KEY_ARTIST,
                        MediaMetadataCompat.METADATA_KEY_GENRE,
                        MediaMetadataCompat.METADATA_KEY_TITLE
                }) {
            builder.putString(key, metadataWithoutBitmap.getString(key));
        }
        builder.putLong(
                MediaMetadataCompat.METADATA_KEY_DURATION,
                metadataWithoutBitmap.getLong(MediaMetadataCompat.METADATA_KEY_DURATION));
        builder.putBitmap(MediaMetadataCompat.METADATA_KEY_ALBUM_ART, albumArt);
        return builder.build();
    }

    public static boolean isMediaInStorage(String filename)
    {
        boolean isInStorage = false;
        mfilePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC).toString() + "/";

        try {

            File file = new File(mfilePath + filename);

            if (file.exists()) {
                isInStorage = true;
            }
        }
        catch (Exception e)
        {
            Log.e(TAG, "Exception: " + e);
        }
        return isInStorage;
    }

    public static void setMediaMetaData()
    {
        Log.d(TAG, "setMediaMetadata()");
        String urlPrefix = CurrentArtist.getInstance().getArtistUrl();
        artist = CurrentArtist.getInstance().getCurrentArtist();
        mediaTitle = CurrentArtist.getInstance().getCurrentTitle();
        duration = CurrentArtist.getInstance().getCurrentDuration();
        titles = CurrentArtist.getInstance().getRadioTitles(artist);
        Log.d(TAG, "Got Radio Titles");

        if (!isMediaInStorage(CurrentArtist.getInstance().getCurrentFile())) {
            mediaFile = CurrentArtist.getInstance().getCurrentFile();
        }
        else
        {
            mediaFile = mfilePath + CurrentArtist.getInstance().getCurrentFile();
        }
        Log.d(TAG, "getCurrentFile: " + mediaFile);

        for (String title : titles)
        {
            createMediaMetadataCompat (
                    title,// CurrentArtist.getInstance().getCurrentTitle(),
                title, //CurrentArtist.getInstance().getCurrentTitle(),
                artist, //CurrentArtist.getInstance().getCurrentArtist(),
                "Old Time Radio",
                "Old Time Radio",
                duration,
                TimeUnit.SECONDS,
                mediaFile,
                CurrentArtist.getInstance().getCurrentImage(),
                artist);
        }
    }

    public static void clearLibraryItems()
    {
        Log.d(TAG, "ClearLibraryItems()");
        music.clear();
    }

    private static void createMediaMetadataCompat(
            String mediaId,
            String title,
            String artist,
            String album,
            String genre,
            long duration,
            TimeUnit durationUnit,
            String musicFilename,
            int albumArtResId,
            String albumArtResName) {
        music.put(
                mediaId,
                new MediaMetadataCompat.Builder()
                        .putString(MediaMetadataCompat.METADATA_KEY_MEDIA_ID, mediaId)
                        .putString(MediaMetadataCompat.METADATA_KEY_ALBUM, album)
                        .putString(MediaMetadataCompat.METADATA_KEY_ARTIST, artist)
                        .putLong(MediaMetadataCompat.METADATA_KEY_DURATION,
                                TimeUnit.MILLISECONDS.convert(duration, durationUnit))
                        .putString(MediaMetadataCompat.METADATA_KEY_GENRE, genre)
                        .putString(
                                MediaMetadataCompat.METADATA_KEY_ALBUM_ART_URI,
                                getAlbumArtUri(albumArtResName))
                        .putString(
                                MediaMetadataCompat.METADATA_KEY_DISPLAY_ICON_URI,
                                getAlbumArtUri(albumArtResName))
                        .putString(MediaMetadataCompat.METADATA_KEY_TITLE, title)
                        .build());
        albumRes.put(mediaId, albumArtResId);
        musicFileName.put(mediaId, musicFilename);
    }

   /* public static List<MediaBrowserCompat.MediaItem> getResult() {
        List<MediaBrowserCompat.MediaItem> result = new ArrayList<>();
        if (CurrentArtist.getInstance().getCurrentCategory().equals("Comedy")) {
            for (String show : radioList.comedyCat ){
                MediaDescriptionCompat desc =
                        new MediaDescriptionCompat.Builder()
                                .setMediaId(show)
                                .setTitle(show)
                                .setSubtitle("")
                                .setDescription(show)
                                .build();

                result.add(
                        new MediaBrowserCompat.MediaItem(
                                desc, MediaBrowserCompat.MediaItem.FLAG_BROWSABLE));
            }
        }

        if (CurrentArtist.getInstance().getCurrentCategory().equals("Science Fiction")) {
            for (String show : radioList.scifiCat ){
                MediaDescriptionCompat desc =
                        new MediaDescriptionCompat.Builder()
                                .setMediaId(show)
                                .setTitle(show)
                                .setSubtitle("")
                                .setDescription(show)
                                .build();

                result.add(
                        new MediaBrowserCompat.MediaItem(
                                desc, MediaBrowserCompat.MediaItem.FLAG_BROWSABLE));
            }
        }

        if (CurrentArtist.getInstance().getCurrentCategory().equals("Thriller")) {
            for (String show : radioList.thrillerCat ){
                MediaDescriptionCompat desc =
                        new MediaDescriptionCompat.Builder()
                                .setMediaId(show)
                                .setTitle(show)
                                .setSubtitle("")
                                .setDescription(show)
                                .build();

                result.add(
                        new MediaBrowserCompat.MediaItem(
                                desc, MediaBrowserCompat.MediaItem.FLAG_BROWSABLE));
            }
        }

        if (CurrentArtist.getInstance().getCurrentCategory().equals("Western")) {
            for (String show : radioList.westernCat ){
                MediaDescriptionCompat desc =
                        new MediaDescriptionCompat.Builder()
                                .setMediaId(show)
                                .setTitle(show)
                                .setSubtitle("")
                                .setDescription(show)
                                .build();

                result.add(
                        new MediaBrowserCompat.MediaItem(
                                desc, MediaBrowserCompat.MediaItem.FLAG_BROWSABLE));
            }
        }

        // TODO: consider adding mystery
        return result;
    }*/
}
