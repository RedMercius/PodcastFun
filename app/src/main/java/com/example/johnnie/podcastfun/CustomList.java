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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class CustomList extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] radioTitle;
    private final Integer[] imageButtonList;
    private MediaPlayer mp;

    public CustomList(Activity context, String[] radioTitle, Integer[] imageButtonList) {
        super(context, R.layout.list_single, radioTitle);
        this.context = context;
        this.radioTitle = radioTitle;
        this.imageButtonList = imageButtonList;
        this.mp = new MediaPlayer();
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.list_single, null, true);
        final TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);
        final TextView txtStatus = (TextView) rowView.findViewById(R.id.txtstatus);

        final MediaControl mc =
                new MediaControl(context, mp);

        final ImageButton playButton = (ImageButton) rowView.findViewById(R.id.playbtn);
        final ImageButton stopButton = (ImageButton) rowView.findViewById(R.id.stopbtn);
        final ImageButton closeButton = (ImageButton) rowView.findViewById(R.id.closebtn);
        final ImageButton deleteButton = (ImageButton) rowView.findViewById(R.id.deletebtn);
        final ImageButton downloadButton = (ImageButton) rowView.findViewById(R.id.downloadbtn);
        final String mediaTitle = radioTitle[position];

        boolean isItInRaw = mc.checkResourceInRaw(mediaTitle);
        boolean doesMediaExist = mc.checkForMedia(mediaTitle);

        txtStatus.setVisibility(View.VISIBLE);
        deleteButton.setVisibility(View.INVISIBLE);
        closeButton.setVisibility(View.INVISIBLE);
        stopButton.setVisibility(View.INVISIBLE);
        downloadButton.setVisibility(View.INVISIBLE);

        txtTitle.setText(mediaTitle);

        if (!isItInRaw && !doesMediaExist) {
            downloadButton.setImageResource(imageButtonList[4]);
            downloadButton.setVisibility(View.VISIBLE);
            playButton.setImageResource(imageButtonList[0]);
            stopButton.setImageResource(imageButtonList[8]);
            stopButton.setVisibility(View.VISIBLE);
        } else if (isItInRaw || doesMediaExist) {
            downloadButton.setVisibility(View.INVISIBLE);
            playButton.setImageResource(imageButtonList[0]);
        }

        playButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                final Intent i = new Intent(context, PlayActivity.class);
                i.putExtra("MediaTitle", mediaTitle);
                context.startActivity(i);
            }
        });

        downloadButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                mc.downloadMedia(mediaTitle);
                stopButton.setVisibility(View.INVISIBLE);

                Toast.makeText(context, "Download button is clicked: " + mediaTitle, Toast.LENGTH_SHORT).show();
            }
        });

        return rowView;
    }
}


