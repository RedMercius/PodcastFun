package com.RuffinApps.johnnie.oldtimeradio;
/*
 * Copyright 2015 © Johnnie Ruffin
 *
 * Unless required by applicable law or agreed to in writing, software is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *
 */

/////////////////////////////////////////////////////////////////////////////
//
/// @class SelectActivity
//
/// @brief SelectActivity class controls navigation for the selected item
//
/// @author Johnnie Ruffin
//
////////////////////////////////////////////////////////////////////////////

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import java.util.Arrays;

import static com.RuffinApps.johnnie.oldtimeradio.R.id.playedList;

public class SelectActivity extends AppCompatActivity {
    private String TAG = "SelectActivity: ";

    private CustomList adapter;
    private CustomList playedAdapter;
    private CustomList notPlayedAdapter;
    private CustomList queuedAdapter;
    private CustomList notQueuedAdapter;

    private Button playedListBtn;
    private Button unplayedListBtn;
    private Button allShowsBtn;
    private Button queuedShowsBtn;

    private PlayedList playList;
    private QueueList queueList;

    private ListView listview;
    private String artist;

    private String [] notPlayedTitles;
    private String [] playedTitles;
    private String [] queuedTitles;
    private String [] notQueuedTitles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        artist = CurrentArtist.getInstance().getCurrentArtist();

        // get the icon images
        ImageControl iconControl = new ImageControl();

        // get and set the instance for the played and queue list
        playList = new PlayedList(this);
        queueList = new QueueList(this);

        String[] titles = CurrentArtist.getInstance().getRadioTitles(artist);
        Log.d(TAG, "Got Radio Titles!!");
        // Log.d(TAG, "Titles: " + titles.length);
        Arrays.sort(titles);

        // Log.d(TAG, "Unplayed Length: " + playList.getUnplayedTitles(artist).length);
        // Log.d(TAG, "UnQueue Length: " + queueList.getUnqueueTitles(artist).length);

        notPlayedTitles = playList.getUnplayedTitles(artist);
        playedTitles = playList.getPlayedTitles(artist);

        queuedTitles = queueList.getQueuedTitles(artist);
        notQueuedTitles = queueList.getUnqueueTitles(artist);

        Arrays.sort(notPlayedTitles);
        Arrays.sort(playedTitles);
        Arrays.sort(queuedTitles);
        Arrays.sort(notQueuedTitles);

        listview = findViewById(R.id.listview);
        playedListBtn = findViewById(playedList);
        unplayedListBtn = findViewById(R.id.unplayedList);
        allShowsBtn = findViewById(R.id.allShowsList);

        queuedShowsBtn = findViewById(R.id.queueList);

        // set the list adapter
        adapter =
                new CustomList(this, titles,
                        iconControl.getImageButtonList());

        listview.setAdapter(adapter);

        // set the played adapter
        playedAdapter =
                new CustomList(this, playedTitles,
                        iconControl.getImageButtonList());


        // set the notPlayed adapter
        notPlayedAdapter =
                new CustomList(this, notPlayedTitles,
                        iconControl.getImageButtonList());

        // set the queued adapter
        queuedAdapter =
                new CustomList(this, queuedTitles,
                        iconControl.getImageButtonList());

        // set the not queued adapter
        notQueuedAdapter =
                new CustomList(this, notQueuedTitles,
                        iconControl.getImageButtonList());


        unplayedListBtn.setBackgroundColor(Color.TRANSPARENT);
        unplayedListBtn.setTextColor(Color.BLACK);
        allShowsBtn.setBackgroundColor(Color.BLACK);
        allShowsBtn.setTextColor(Color.WHITE);
        playedListBtn.setBackgroundColor(Color.TRANSPARENT);
        playedListBtn.setTextColor(Color.BLACK);

        queuedShowsBtn.setBackgroundColor(Color.TRANSPARENT);
        queuedShowsBtn.setTextColor(Color.BLACK);

        playedListBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                unplayedListBtn.setBackgroundColor(Color.TRANSPARENT);
                unplayedListBtn.setTextColor(Color.BLACK);
                allShowsBtn.setBackgroundColor(Color.TRANSPARENT);
                allShowsBtn.setTextColor(Color.BLACK);
                playedListBtn.setBackgroundColor(Color.BLACK);
                playedListBtn.setTextColor(Color.WHITE);
                queuedShowsBtn.setBackgroundColor(Color.TRANSPARENT);
                queuedShowsBtn.setTextColor(Color.BLACK);

                listview.setAdapter(playedAdapter);
                if (playedTitles[0].contains("No played shows.")) {
                    playedAdapter.removeButtonsFromView(true);
                }
                AdapterState.getInstance().setCurrentState("played");
            }
        });

        unplayedListBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                unplayedListBtn.setBackgroundColor(Color.BLACK);
                unplayedListBtn.setTextColor(Color.WHITE);
                allShowsBtn.setBackgroundColor(Color.TRANSPARENT);
                allShowsBtn.setTextColor(Color.BLACK);
                playedListBtn.setBackgroundColor(Color.TRANSPARENT);
                playedListBtn.setTextColor(Color.BLACK);
                queuedShowsBtn.setBackgroundColor(Color.TRANSPARENT);
                queuedShowsBtn.setTextColor(Color.BLACK);

                listview.setAdapter(notPlayedAdapter);

                if (notPlayedTitles[0].contains("All shows have been played.")) {
                    notPlayedAdapter.removeButtonsFromView(true);
                }
                AdapterState.getInstance().setCurrentState("not_played");
            }
        });

        allShowsBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                unplayedListBtn.setBackgroundColor(Color.TRANSPARENT);
                unplayedListBtn.setTextColor(Color.BLACK);
                allShowsBtn.setBackgroundColor(Color.BLACK);
                allShowsBtn.setTextColor(Color.WHITE);
                playedListBtn.setBackgroundColor(Color.TRANSPARENT);
                playedListBtn.setTextColor(Color.BLACK);
                queuedShowsBtn.setBackgroundColor(Color.TRANSPARENT);
                queuedShowsBtn.setTextColor(Color.BLACK);

                listview.setAdapter(adapter);
                AdapterState.getInstance().setCurrentState("all");
            }
        });

        queuedShowsBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                unplayedListBtn.setBackgroundColor(Color.TRANSPARENT);
                unplayedListBtn.setTextColor(Color.BLACK);
                allShowsBtn.setBackgroundColor(Color.TRANSPARENT);
                allShowsBtn.setTextColor(Color.BLACK);
                playedListBtn.setBackgroundColor(Color.TRANSPARENT);
                playedListBtn.setTextColor(Color.BLACK);
                queuedShowsBtn.setBackgroundColor(Color.BLACK);
                queuedShowsBtn.setTextColor(Color.WHITE);

                listview.setAdapter(queuedAdapter);
                if (queuedTitles[0].contains("No queued shows.")) {
                    queuedAdapter.removeButtonsFromView(true);
                }
                AdapterState.getInstance().setCurrentState("queue");
            }
        });
    }

    public void updateAdapters() {
        notPlayedTitles = playList.getUnplayedTitles(artist);
        playedTitles = playList.getPlayedTitles(artist);

        notQueuedTitles = queueList.getUnqueueTitles(artist);
        queuedTitles = queueList.getQueuedTitles(artist);

        this.playedAdapter.updateRadioTitle(playedTitles);
        this.notPlayedAdapter.updateRadioTitle(notPlayedTitles);

        this.queuedAdapter.updateRadioTitle(queuedTitles);
        this.notQueuedAdapter.updateRadioTitle(notQueuedTitles);

        if (playedTitles[0].contains("No played shows.")) {
            playedAdapter.removeButtonsFromView(true);
        } else {
            playedAdapter.removeButtonsFromView(false);
        }

        if (queuedTitles[0].contains("No queued shows.")) {
            queuedAdapter.removeButtonsFromView(true);
        } else {
            queuedAdapter.removeButtonsFromView(false);
        }

        if (notPlayedTitles[0].contains("All shows have been played.")) {
            notPlayedAdapter.removeButtonsFromView(true);
        } else {
            notPlayedAdapter.removeButtonsFromView(false);
        }
        this.notPlayedAdapter.notifyDataSetInvalidated();
        this.playedAdapter.notifyDataSetInvalidated();
        this.notQueuedAdapter.notifyDataSetInvalidated();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        if (CurrentArtist.getInstance().getCurrentTitle() != null)
        {
            if (CurrentArtist.getInstance().isPlaying())
            {
                getMenuInflater().inflate(R.menu.menu_main, menu);
            }
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_favorite) {

            final Intent i = new Intent(getApplicationContext(), MediaPlay.class);
            i.putExtra("MediaTitle", CurrentArtist.getInstance().getCurrentFile());
            i.putExtra("Selection", CurrentArtist.getInstance().getCurrentArtist());
            i.putExtra("Title", CurrentArtist.getInstance().getCurrentTitle());
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume()
    {  // After a pause OR at startup
        super.onResume();
        invalidateOptionsMenu();
        Log.d(TAG, "OnResume!!");
    }

    @Override
    public void onDestroy()
    {
        // clean up any list activity.
        adapter.cleanUp(this);
        notPlayedAdapter.cleanUp(this);
        playedAdapter.cleanUp(this);
        queuedAdapter.cleanUp(this);
        notQueuedAdapter.cleanUp(this);
        playList.close();
        queueList.close();
        super.onDestroy();
    }
}
