/*
 * Copyright 2015 © Johnnie Ruffin
 *
 * Unless required by applicable law or agreed to in writing, software is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *
 */

package com.example.johnnie.podcastfun;

import android.app.Activity;
import android.database.Cursor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
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
        MediaPlayer.OnCompletionListener {

    private final Activity context;

    private MediaPlayer mp;
    private DownloadControl dc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("MediaControl:", "onCreate Service");
    }

    public MediaControl(Activity context, MediaPlayer mp) {
        this.context = context;
        this.mp = mp;
        this.mp.setOnCompletionListener(this);
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
            if (title.equals(filename))
            {
                mediaFound = true;
                Log.d("MediaControl: ", "mediaFound=true");
                break;
            }
        }
        cursor.close();
        return mediaFound;
    }

    public void downloadMedia(String filename)
    {
        dc = new DownloadControl();
        dc.downloadFile(filename, context);
        Log.d("MediaControl: ", "downloadMedia");
    }

    public void callMediaFromRaw(String item, Activity context) throws IOException {

        int mediaId = 0;

        if (checkResourceInRaw(item)) {
            // assign the resource id so that the raw item can be identified and played.
            mediaId = (context.getResources().getIdentifier(item, "raw", context.getPackageName()));
        }
        // check to see if the resource exists in raw
        else if (!checkResourceInRaw(item))
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
        else if (mp.isPlaying())
        {
            mp.pause();
            Toast.makeText(context, "Pausing!!", Toast.LENGTH_SHORT).show();
        }
        Log.d("MediaControl: ", "callMediaFromRaw");
    }

    public void callMediaFromInternet(String filename, Activity context) throws IOException
    {
        String url = "http://www.JohnnieRuffin.com/audio/" + filename; // your URL here
        mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mp.setDataSource(url);
        mp.prepareAsync(); // might take long! (for buffering, etc)
        Log.d("MediaControl: ", ("callMediaFromInternet: " + filename));
    }

    @Override
    public void onCompletion(MediaPlayer arg0) {
        Log.d("MediaControl: ", "onCompletion called");
    }

    public void stopMedia()
    {
        releaseMediaPlayer();
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        releaseMediaPlayer();
        System.out.println("MediaControl:::OnDestroy");
    }

    public void releaseMediaPlayer()
    {
        //if mediaplayer is still holding mediaplayer
        // release the mediaplayer
        if (mp != null) {
            mp.stop();
            mp.reset();
            mp.release();
        }
    }
}
