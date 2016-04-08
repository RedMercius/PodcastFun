/*
 * Copyright 2015 © Johnnie Ruffin
 *
 * Unless required by applicable law or agreed to in writing, software is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *
 */

package com.RuffinApps.johnnie.oldtimeradiofree;

import android.app.Activity;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

//////////////////////////////////////////////////////////////////////////////////////
//
///  @class MediaControl
//
///  @author Johnnie Ruffin
//
///  @brief A class to control the Audio Media interaction
//
///  @created 10/9/2015
//
//////////////////////////////////////////////////////////////////////////////////////

public class MediaControl {

    private String TAG = "MediaControl: ";
    private final Activity context;

    private MediaPlayer mp;
    public DownloadControl dc;
    private String url;

    private String martist;
    private String mfilePath;

    public MediaControl(Activity context, MediaPlayer mp, String artist) {
        this.context = context;
        this.mp = mp;
        this.martist = artist;

        dc = new DownloadControl(context);

        getArtistUrl();

        mfilePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC).toString() + "/";
    }

    public void getArtistUrl() {
        switch (martist) {
            case "Burns And Allen": {
                url = "http://www.JohnnieRuffin.com/audio/";
                break;
            }

            case "Fibber McGee And Molly": {
                url = "http://www.JohnnieRuffin.com/audio/FibberMcGeeandMolly1940/";
                break;
            }

            case "Martin And Lewis": {
                url = "http://www.JohnnieRuffin.com/audio/MartinAndLewis_OldTimeRadio/";
                break;
            }

            case "The Great GilderSleeves": {
                url = "http://www.JohnnieRuffin.com/audio/Otrr_The_Great_Gildersleeve_Singles/";
                break;
            }

            case "XMinus1": {
                url = "http://www.JohnnieRuffin.com/audio/XMinus1/";
                break;
            }

            case "Inner Sanctum": {
                url = "http://www.JohnnieRuffin.com/audio/InnerSanctum/";
                break;
            }

            case "Dimension X": {
                url = "http://www.JohnnieRuffin.com/audio/DimensionX/";
                break;
            }

            case "Night Beat": {
                url = "http://www.JohnnieRuffin.com/audio/NightBeat/";
                break;
            }

            case "Speed": {
                url = "http://www.JohnnieRuffin.com/audio/Speed/";
                break;
            }

            default: {
                url = null;
                break;
            }
        }
        dc.setWebPath(url);
    }

    public boolean checkResourceInRaw (String resource)
    {
        boolean resourceFound = true;
        int resourceid = (context.getResources().getIdentifier(resource, "raw", context.getPackageName()));

        if (resourceid == 0)
        {
            resourceFound = false;
        }
        return resourceFound;
    }

    public boolean checkForMedia (String filename)
    {
        boolean mediaFound = false;
        try {

            File file = new File(mfilePath + filename);

            if (file.exists()) {
                mediaFound = true;
            }
        }
        catch (Exception e)
        {
            Log.e(TAG, "Exception: " + e);
        }

        return mediaFound;
    }

    public void downloadMedia(String filename)
    {
        dc.downloadFile(filename);
    }

    public void deleteMedia(String filename)
    {
        dc.deleteMedia(filename);
    }

    public void callMediaFromRaw(String item) throws IOException {

        int mediaId = 0;

        if (checkResourceInRaw(item)) {
            // assign the resource id so that the raw item can be identified and played.
            mediaId = (context.getResources().getIdentifier(item, "raw", context.getPackageName()));
        }

        // check to see if the resource exists in raw
        if (!checkResourceInRaw(item))
        {
            Toast.makeText(context, "Resource does not exist!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (mp == null)
        {
            mp = MediaPlayer.create(context, mediaId);
        }

        if (!mp.isPlaying()) {

            mp.start();
        }

        if (mp.isPlaying())
        {
            mp.pause();
            Toast.makeText(context, "Pausing!!", Toast.LENGTH_SHORT).show();
        }
    }

    public void callMediaFromExternalDir(String filename) throws IOException
    {
        mp.setDataSource(mfilePath + filename);
        mp.prepareAsync();
    }

    public void callMediaFromInternet(String filename) throws IOException
    {
        //TODO verify file exists before playing.

        url = url + filename; // your URL here

        mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mp.setDataSource(url);
        mp.prepareAsync(); // might take long! (for buffering, etc)
    }

    public void stopMedia() { releaseMediaPlayer(); }

    // public String getMP3Artist(){ return martist; }

    private void releaseMediaPlayer()
    {
        //if mediaplayer is still holding mediaplayer
        // release the mediaplayer
        if (mp != null) {
            mp.stop();
            mp.reset();
            mp.release();
        }
    }

    public void getMp3Info(String filename)
    {
        final int BYTE_128 = 128;

        final int[] OFFSET_TAG = new int[] { 0, 3 };
        //final int[] OFFSET_TITLE = new int[] { 3, 33 };
        final int[] OFFSET_ARTIST = new int[] { 33, 63 };
        //final int[] OFFSET_YEAR = new int[] { 93, 97 };
        //final int[] OFFSET_ALBUM = new int[] { 63, 93 };

        // indexer
        final int FROM = 0;
        final int TO = 1;
        String filePath = "/";
        try {
            filePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC).toString();
        }
        catch(Exception e)
        {
            Log.e(TAG, "Exception: " + e);
        }
        File mp3 = new File(filePath + "/" + filename);

        FileInputStream fis;

        try {
            // create new file stream for parsing file in binary
            fis = new FileInputStream(mp3);

            // get file size
            int size = (int) mp3.length();

            // offset to the first byte of the last 128 bytes
            fis.skip(size - BYTE_128);

            // read chunk of 128 bytes
            byte[] chunk = new byte[BYTE_128];
            fis.read(chunk);

            // convert chunk to string
            String id3 = new String(chunk);

            // get first 3 byte
            String tag = id3.substring(OFFSET_TAG[FROM], OFFSET_TAG[TO]);

            // if equals to "TAG" meaning a valid readable one
            if (tag.equals("TAG")) {
                //mtitle = id3.substring(OFFSET_TITLE[FROM], OFFSET_TITLE[TO]);
                martist= id3.substring(OFFSET_ARTIST[FROM], OFFSET_ARTIST[TO]);
                //myear = id3.substring(OFFSET_YEAR[FROM], OFFSET_YEAR[TO]);
                //malbum = id3.substring(OFFSET_ALBUM[FROM], OFFSET_ALBUM[TO]);
            }
        }
        catch(Exception e)
        {
            Log.e(TAG, "Exception: " + e);
        }
    }

   /* private void scanSdcard(){
        String selection = MediaStore.Audio.Media.IS_MUSIC + " != 0";
        String[] projection = {
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.DISPLAY_NAME,
                MediaStore.Audio.Media.DURATION
        };
        final String sortOrder = MediaStore.Audio.AudioColumns.TITLE + " COLLATE LOCALIZED ASC";

        Cursor cursor = null;
        try {
            Uri uri = android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
            cursor = context.getContentResolver().query(uri, projection, selection, null, sortOrder);
            if( cursor != null){
                cursor.moveToFirst();
                while( !cursor.isAfterLast() ){
                    //MediaData media = new MediaData();
                    String title = cursor.getString(0);
                    String artist = cursor.getString(1);
                    String path = cursor.getString(2);
                    String displayName  = cursor.getString(3);
                    String songDuration = cursor.getString(4);
                    cursor.moveToNext();
                    //Log.d(TAG, "Title: " + title);
                    //Log.d(TAG, "artist: " + artist);
                    //Log.d(TAG, "path: " + path);
                    //Log.d(TAG, "displayName: " + displayName);
                    //Log.d(TAG, "songDuration: " + songDuration);
                }

            }

        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e);
        }finally{
            if( cursor != null){
                cursor.close();
            }
        }
    }*/
}
