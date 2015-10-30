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
import android.content.Intent;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class CustomList extends ArrayAdapter<String> {

    // This is an attempt at a view holder pattern
    static class ViewHolderItem {

        TextView txtTitle;
        TextView txtStatus;

        ImageButton playButton;
        ImageButton stopButton;
        ImageButton closeButton;
        ImageButton deleteButton;
        ImageButton downloadButton;

    }

    private final Activity context;
    private final String[] radioTitle;
    private final Integer[] imageButtonList;
    private MediaPlayer mp;
    private String artist;

    public CustomList(Activity context, String[] radioTitle, Integer[] imageButtonList, String artist) {
        super(context, R.layout.list_single, radioTitle);
        this.context = context;
        this.radioTitle = radioTitle;
        this.imageButtonList = imageButtonList;
        this.mp = new MediaPlayer();
        this.artist = artist;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        String TAG = "CustomList";
        ViewHolderItem viewHolder;

        if (view == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            viewHolder = new ViewHolderItem();

            view = inflater.inflate(R.layout.list_single, null, true);
            viewHolder.txtTitle = (TextView) view.findViewById(R.id.txt);
            viewHolder.txtStatus = (TextView) view.findViewById(R.id.txtstatus);

            viewHolder.playButton = (ImageButton) view.findViewById(R.id.playbtn);
            viewHolder.stopButton = (ImageButton) view.findViewById(R.id.stopbtn);
            viewHolder.closeButton = (ImageButton) view.findViewById(R.id.closebtn);
            viewHolder.deleteButton = (ImageButton) view.findViewById(R.id.deletebtn);
            viewHolder.downloadButton = (ImageButton) view.findViewById(R.id.downloadbtn);

            view.setTag(viewHolder);

            Log.d(TAG, "View is null");
        }
        else
        {
            viewHolder = (ViewHolderItem) view.getTag();
        }

        final String mediaTitle = radioTitle[position];

        final MediaControl mc =
                new MediaControl(context, mp);

            boolean isItInRaw = mc.checkResourceInRaw(mediaTitle);
            boolean doesMediaExist = mc.checkForMedia(mediaTitle);

            viewHolder.txtStatus.setVisibility(View.VISIBLE);
            viewHolder.deleteButton.setVisibility(View.INVISIBLE);
            viewHolder.closeButton.setVisibility(View.INVISIBLE);
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
            }

            viewHolder.playButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    final Intent i = new Intent(context, PlayActivity.class);
                    i.putExtra("MediaTitle", mediaTitle);
                    i.putExtra("class", artist);
                    context.startActivity(i);
                }
            });

            viewHolder.downloadButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {

                    mc.downloadMedia(mediaTitle);
                    Toast.makeText(context, "Download button is clicked: " + mediaTitle, Toast.LENGTH_SHORT).show();
                }
            });

        return view;
    }
}


