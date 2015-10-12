/*
 * Copyright 2015 © Johnnie Ruffin
 *
 * Unless required by applicable law or agreed to in writing, software is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *
 */

package com.example.johnnie.podcastfun;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

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

public class MediaControl extends Activity implements
        MediaPlayer.OnBufferingUpdateListener, MediaPlayer.OnCompletionListener,
        MediaPlayer.OnPreparedListener {

    private final Activity context;
    private final String[] radioTitle;
    private final Integer[] imageButtonList;
    private final Integer[] imageButtonListStop;

    private MediaPlayer mp;
    private DownloadControl dc;

    public MediaControl(Activity context, String[] radioTitle, Integer[] imageButtonList, Integer[] imageButtonListStop) {
        this.context = context;
        this.radioTitle = radioTitle;
        this.imageButtonList = imageButtonList;
        this.imageButtonListStop = imageButtonListStop;
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

        Cursor cursor = context.getContentResolver().query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                null,
                null,
                null,
                MediaStore.Audio.Media.TITLE);

        while (cursor.moveToNext()) {
            String title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
            if (title == filename)
            {
                mediaFound = true;
                break;
            }
        }

        return mediaFound;
    }


    public void downloadMedia(String filename)
    {
        dc = new DownloadControl();
        dc.downloadFile(filename);
    }

    public void callMediaFromRaw(String item, Activity context) throws IOException {

        int mediaId = 0;

        if (true == checkResourceInRaw(item)) {
            // assign the resource id so that the raw item can be identified and played.
            mediaId = (context.getResources().getIdentifier(item, "raw", context.getPackageName()));
        }
        // check to see if the resource exists in raw
        else if (false == checkResourceInRaw(item))
        {
            Toast.makeText(context, "Resource does not exist!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (mp == null)
        {
            mp = MediaPlayer.create(context, mediaId);
        }

        if (false == mp.isPlaying()) {

            mp.start();
        }
        else if (true == mp.isPlaying())
        {
            mp.pause();
            Toast.makeText(context, "Pausing!!", Toast.LENGTH_SHORT).show();
        }
    }

    private void callMediaFromContentResolver()
    {
       /* Uri myUri = ....; // initialize Uri here
        MediaPlayer mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setDataSource(getApplicationContext(), myUri);
        mediaPlayer.prepare();
        mediaPlayer.start();*/
    }

    private void callMediaFromInternet(String filename) throws IOException
    {
        String url = "http://http://www.JohnnieRuffin.com/audio/" + filename; // your URL here
        MediaPlayer mp = new MediaPlayer();
        mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mp.setDataSource(url);
        mp.prepareAsync(); // might take long! (for buffering, etc)
        mp.setOnBufferingUpdateListener(this);
        mp.setOnCompletionListener(this);
        mp.setOnPreparedListener(this);
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {
        Log.d("MediaControl: ", "onBufferingUpdate percent:" + percent);

    }

    @Override
    public void onCompletion(MediaPlayer arg0) {
        Log.d("MediaControl: ", "onCompletion called");
    }


    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        mp.start();
    }



    public void stopMedia()
    {
        releaseMediaPlayer();
    }

    public void releaseMediaPlayer()
    {
        //if mediaplayer is still holding mediaplayer
        // release the mediaplayer
        if (mp != null) {
            mp.stop();
            mp.release();
        }
    }
}
