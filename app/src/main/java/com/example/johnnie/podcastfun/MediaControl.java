/*
 * Copyright 2015 © Johnnie Ruffin
 *
 * Unless required by applicable law or agreed to in writing, software is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *
 */

package com.example.johnnie.podcastfun;

import android.app.Activity;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;

//////////////////////////////////////////////////////////////////////////////////////
//
///  @class MediaControl
//
///  @author Johnnie
//
///  @brief A class to control the Audio Media interaction
//
///  @created 10/9/2015
//
//////////////////////////////////////////////////////////////////////////////////////

public class MediaControl {

    private final Activity context;
    private final String[] radioTitle;
    private final Integer[] imageButtonList;
    private final Integer[] imageButtonListStop;

    private final MediaPlayer mp;

    public MediaControl(Activity context, String[] radioTitle, Integer[] imageButtonList, Integer[] imageButtonListStop) {
        this.context = context;
        this.radioTitle = radioTitle;
        this.imageButtonList = imageButtonList;
        this.imageButtonListStop = imageButtonListStop;
        mp = new MediaPlayer();
    }

    public void callMediaFromRaw(String item, Activity context) throws IOException {
        switch(item)
        {
            case "ba_1943_02_15_AreHusbandsNecessary.mp3":
            {
                if (false == mp.isPlaying()) {
                    mp.create(context, R.raw.bagracieplayssadiethompson);
                    mp.start();
                }
                else if (true == mp.isPlaying())
                {
                    mp.pause();
                    System.out.println("Pausing!!");
                }
                break;
            }
            case "ba_SantasWorkshop.mp3":
            {
                if (true == mp.isPlaying())
                {
                    mp.stop();
                    mp.reset();
                    mp.release();
                }
                mp.create(context, R.raw.basantasworkshop);
                mp.start();
                break;
            }
            case "baauntclarakangaroo.mp3":
            {
                if (true == mp.isPlaying())
                {
                    mp.stop();
                    mp.reset();
                    mp.release();
                }
                /*// ---tbd--- ---jlr-- check to see if the file exists or error will happen.
                String url = "http://www.JohnnieRuffin.com/audio/baarehusbandsnecessary.mp3"; // initialize Uri here
                mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mp.setDataSource(url);
                mp.prepare();
                mp.start();*/
                try {
                    callMediaFromInternet(item);
                } catch (IOException e) {
                    Log.e("tag", e.getMessage(), e);
                }
                break;
            }
            default:
            {
                Toast.makeText(context, "MP3 not Handled!", Toast.LENGTH_SHORT).show();
            }

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
        mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mp.setDataSource(url);
        mp.prepareAsync(); // might take long! (for buffering, etc)
        mp.start();
    }

    public void releaseMediaPlayer()
    {
        //if mediaplayer is still holding mediaplayer
        // release the mediaplayer
        mp.release();
    }
}
