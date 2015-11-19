/*
 * Copyright 2015 © Johnnie Ruffin
 *
 * Unless required by applicable law or agreed to in writing, software is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *
 */

package com.example.johnnie.podcastfun;

/////////////////////////////////////////////////////////////////////////////
//
/// @class CustomList
//
/// @brief CustomList class controls the item list
//
/// @author Johnnie Ruffin
//
////////////////////////////////////////////////////////////////////////////

import android.app.Activity;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class CustomList extends ArrayAdapter<String> {

    // This is an attempt at a view holder pattern
    static class ViewHolderItem {

        TextView txtTitle;
        TextView txtStatus;

        ImageButton playButton;
        ImageView stopButton;
        ImageButton deleteButton;
        ImageButton downloadButton;
    }

    private final Activity context;
    private final String[] radioTitle;
    private final Integer[] imageButtonList;
    private String artist;
    private MediaControl mc;
    private BroadcastReceiver onComplete;
    final String TAG = "CustomList";

    public CustomList(Activity context, String[] radioTitle, Integer[] imageButtonList, String artist) {
        super(context, R.layout.custom_list_multi, radioTitle);

        MediaPlayer mp;
        this.context = context;
        this.radioTitle = radioTitle;
        this.imageButtonList = imageButtonList;
        mp = new MediaPlayer();
        this.artist = artist;

        onComplete = new BroadcastReceiver() {
            public void onReceive(Context ctxt, Intent intent) {
                Log.d(TAG, "Download Complete!!!!");
                notifyDataSetChanged();
            }
        };

        context.registerReceiver(onComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
        mc =
                new MediaControl(context, mp, artist);
    }

    public boolean isNetworkAvailable()
    {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(SelectActivity.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    /*public Boolean isAvailable() {
        try {
            Process p1 = java.lang.Runtime.getRuntime().exec("ping -c 1    www.google.com");
            int returnVal = p1.waitFor();
            boolean reachable = (returnVal==0);
            if(reachable){
                System.out.println("Internet access");
                return true;
            }
            else{
                System.out.println("No Internet access");
            }

        } catch (Exception e) {

            e.printStackTrace();
        }
        return false;
    }*/

    @Override
    public View getView(final int position, View view, ViewGroup parent) {

        final ViewHolderItem viewHolder;

        context.setTitle(artist);
        if (view == null) {
            viewHolder = new ViewHolderItem();

            view = View.inflate( context, R.layout.custom_list_multi, null);
            viewHolder.txtTitle = (TextView) view.findViewById(R.id.txt);
            viewHolder.txtStatus = (TextView) view.findViewById(R.id.txtstatus);

            viewHolder.playButton = (ImageButton) view.findViewById(R.id.playbtn);
            viewHolder.stopButton = (ImageView) view.findViewById(R.id.stopbtn);
            viewHolder.deleteButton = (ImageButton) view.findViewById(R.id.deletebtn);
            viewHolder.downloadButton = (ImageButton) view.findViewById(R.id.downloadbtn);

            view.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolderItem) view.getTag();
        }

        final String mediaTitle = radioTitle[position];

        RadioTitle rt = new RadioTitle();

        rt.initTitles();

        String MediaFile = null;

        switch (artist) {
            case "Burns And Allen": {
                for (String mediaFile : rt.getBaMap().keySet()) {
                    if (rt.getBaMap().get(mediaFile).equals(mediaTitle)) {
                        MediaFile = mediaFile;
                    }
                }
                break;
            }

            case "Fibber McGee And Molly":
            {
                for (String mediaFile : rt.getFbMap().keySet()) {
                    if (rt.getFbMap().get(mediaFile).equals(mediaTitle)) {
                        MediaFile = mediaFile;
                    }
                }
                break;
            }

            case "Martin And Lewis":
            {
                for (String mediaFile : rt.getMlMap().keySet()) {
                    if (rt.getMlMap().get(mediaFile).equals(mediaTitle)) {
                        MediaFile = mediaFile;
                    }
                }
                break;
            }

            case "The Great GilderSleeves":
            {
                for (String mediaFile : rt.getGlMap().keySet()) {
                    if (rt.getGlMap().get(mediaFile).equals(mediaTitle)) {
                        MediaFile = mediaFile;
                    }
                }
                break;
            }

            case "XMinus1":
            {
                for (String mediaFile : rt.getXMMap().keySet()) {
                    if (rt.getXMMap().get(mediaFile).equals(mediaTitle)) {
                        MediaFile = mediaFile;
                    }
                }
                break;
            }

            case "Inner Sanctum":
            {
                for (String mediaFile : rt.getIsMap().keySet()) {
                    if (rt.getIsMap().get(mediaFile).equals(mediaTitle)) {
                        MediaFile = mediaFile;
                    }
                }
                break;
            }

            case "Dimension X":
            {
                for (String mediaFile : rt.getDxMap().keySet()) {
                    if (rt.getDxMap().get(mediaFile).equals(mediaTitle)) {
                        MediaFile = mediaFile;
                    }
                }
                break;
            }

            case "Night Beat":
            {
                for (String mediaFile : rt.getNbMap().keySet()) {
                    if (rt.getNbMap().get(mediaFile).equals(mediaTitle)) {
                        MediaFile = mediaFile;
                    }
                }
                break;
            }

            case "Speed":
            {
                for (String mediaFile : rt.getSgMap().keySet()) {
                    if (rt.getSgMap().get(mediaFile).equals(mediaTitle)) {
                        MediaFile = mediaFile;
                    }
                }
                break;
            }
        }

            final String mediaFileName = MediaFile;

            boolean isItInRaw = mc.checkResourceInRaw(MediaFile);
            final boolean doesMediaExist = mc.checkForMedia(MediaFile);

            viewHolder.txtStatus.setVisibility(View.VISIBLE);
            viewHolder.deleteButton.setVisibility(View.INVISIBLE);
            viewHolder.stopButton.setVisibility(View.INVISIBLE);
            viewHolder.downloadButton.setVisibility(View.INVISIBLE);

            viewHolder.txtTitle.setText(mediaTitle);

            if (!isItInRaw && !doesMediaExist) {
                viewHolder.downloadButton.setImageResource(imageButtonList[4]);
                viewHolder.downloadButton.setVisibility(View.VISIBLE);
                viewHolder.playButton.setImageResource(imageButtonList[0]);
                viewHolder.stopButton.setImageResource(imageButtonList[8]);
                viewHolder.stopButton.setVisibility(View.VISIBLE);
            }

            if (isItInRaw || doesMediaExist) {
                viewHolder.downloadButton.setVisibility(View.INVISIBLE);
                viewHolder.playButton.setImageResource(imageButtonList[0]);

                viewHolder.deleteButton.setVisibility(View.VISIBLE);
                viewHolder.deleteButton.setImageResource(imageButtonList[7]);
            }

            viewHolder.playButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    // if we need to stream this, check for internet connection.
                    if (!doesMediaExist) {
                        if (!isNetworkAvailable()) {
                            Toast.makeText(context, "No Internet Connection Detected. Cannot Stream Media.",
                                    Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }

                    final Intent i = new Intent(context, PlayActivity.class);
                    i.putExtra("MediaTitle", mediaFileName);
                    i.putExtra("Selection", artist);
                    i.putExtra("Title", mediaTitle);
                    context.startActivity(i);
                    context.finish();
                }
            });

            viewHolder.downloadButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {

                    // if network connection is down, inform the user that we cannot download.
                    if (!isNetworkAvailable())
                    {
                        Toast.makeText(context, "No Internet Connection Detected. Cannot proceed with download.",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }
                    mc.downloadMedia(mediaFileName);
                    viewHolder.downloadButton.setVisibility(View.INVISIBLE);
                    Toast.makeText(context, "Download In Progress: " + mediaFileName, Toast.LENGTH_SHORT).show();
                }
            });

        viewHolder.deleteButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                mc.deleteMedia(mediaFileName);
                notifyDataSetChanged();
                Toast.makeText(context, "Deleting " + mediaFileName, Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    public void cleanUp(Activity context)
    {
        Log.d(TAG, "cleanup!!");
        context.unregisterReceiver(onComplete);
    }
}