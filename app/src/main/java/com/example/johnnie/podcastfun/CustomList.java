/*
 * Copyright 2015 © Johnnie Ruffin
 *
 * Unless required by applicable law or agreed to in writing, software is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *
 */

package com.example.johnnie.podcastfun;

/**
 * Created by johnnie on 10/4/2015.
 */

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

public class CustomList extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] radioTitle;
    private final Integer[] imageButtonList;
    private final Integer[] imageButtonListStop;

    public CustomList(Activity context, String[] radioTitle, Integer[] imageButtonList, Integer[] imageButtonListStop) {
        super(context, R.layout.list_single, radioTitle);
        this.context = context;
        this.radioTitle = radioTitle;
        this.imageButtonList = imageButtonList;
        this.imageButtonListStop = imageButtonListStop;

    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.list_single, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);
        TextView txtStatus = (TextView) rowView.findViewById(R.id.txt2);

        final MediaControl mc =
                new MediaControl(context, radioTitle, imageButtonList, imageButtonListStop);

        final ImageButton playButton = (ImageButton) rowView.findViewById(R.id.playbtn);
        final ImageButton stopButton = (ImageButton) rowView.findViewById(R.id.stopbtn);
        final ImageButton closeButton = (ImageButton) rowView.findViewById(R.id.closebtn);
        // final ImageButton pauseButton = (ImageButton) rowView.findViewById(R.id.playbtn);
        final ImageButton downloadButton = (ImageButton) rowView.findViewById(R.id.downloadbtn);
        final ImageButton deleteButton = (ImageButton) rowView.findViewById(R.id.deletebtn);
        final boolean isItInRaw = mc.checkResourceInRaw(radioTitle[position]);
        final boolean doesMediaExist = mc.checkForMedia(radioTitle[position]);
        final String mediaTitle = radioTitle[position];

        closeButton.setVisibility(View.INVISIBLE);
        stopButton.setVisibility(View.INVISIBLE);
        downloadButton.setVisibility(View.INVISIBLE);

        txtTitle.setText(mediaTitle);

        if (false == isItInRaw && false == doesMediaExist)
        {
            downloadButton.setImageResource(imageButtonList[4]);
            playButton.setVisibility(View.INVISIBLE);
        }
        else if (true == isItInRaw || true == doesMediaExist)
        {
            playButton.setImageResource(imageButtonList[0]);
            stopButton.setVisibility(View.VISIBLE);
        }

        playButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                try {

                    mc.callMediaFromRaw(mediaTitle, context);
                    playButton.setImageResource(imageButtonList[1]);
                    stopButton.setImageResource(imageButtonList[2]);
                    stopButton.setVisibility(View.VISIBLE);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Toast.makeText(context, "play button is clicked: " + mediaTitle, Toast.LENGTH_SHORT).show();
            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                    mc.stopMedia();
                    playButton.setImageResource(imageButtonList[0]);
                    stopButton.setVisibility(View.INVISIBLE);

                Toast.makeText(context, "stop button is clicked: " + mediaTitle, Toast.LENGTH_SHORT).show();
            }
        });

        downloadButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                mc.downloadMedia(mediaTitle);
                playButton.setImageResource(imageButtonList[0]);
                stopButton.setVisibility(View.INVISIBLE);

                Toast.makeText(context, "stop button is clicked: " + mediaTitle, Toast.LENGTH_SHORT).show();
            }
        });

        return rowView;
    }
}
