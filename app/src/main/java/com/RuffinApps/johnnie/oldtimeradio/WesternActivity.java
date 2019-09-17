package com.RuffinApps.johnnie.oldtimeradio;
/*
 * Copyright 2015 © Johnnie Ruffin
 *
 * Unless required by applicable law or agreed to in writing, software is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *
 */

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class WesternActivity extends AppCompatActivity {

    Button hopalongBtn;
    Button ftLaramieBtn;
    Button loneRangerBtn;
    Button patOBtn;
    Button haveGunBtn;

    private final String TAG = "Western Activity: ";

    // MY_PREFS_NAME - a static String variable like:
    public static final String MY_PREFS_NAME = "jotrpref";
    private boolean pressedhg = false;

    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;

    private RadioTitle rt;

    private boolean listLoaded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_western);

        if (!listLoaded) {
            Log.d(TAG, "Western List not loaded. Loading...");
            rt = RadioTitle.getInstance();

            // Load Western Shows
            rt.loadJSONWestern(getApplicationContext());
            rt.updateWesternTitles();
            listLoaded = true;
        }

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        hopalongBtn = findViewById(R.id.hopalongBtn);
        ftLaramieBtn = findViewById(R.id.ftLaramieBtn);
        loneRangerBtn = findViewById(R.id.lrBtn);
        patOBtn = findViewById(R.id.poBtn);
        haveGunBtn = findViewById(R.id.hgBtn);

        hopalongBtn.setGravity(Gravity.END| Gravity.BOTTOM);
        ftLaramieBtn.setGravity(Gravity.END| Gravity.BOTTOM);
        loneRangerBtn.setGravity(Gravity.END| Gravity.BOTTOM);
        patOBtn.setGravity(Gravity.END| Gravity.BOTTOM);
        haveGunBtn.setGravity(Gravity.END| Gravity.BOTTOM);

        pressedhg = prefs.getBoolean("hg", false);

        if (!pressedhg)
        {
            haveGunBtn.setTextColor(Color.RED);
            haveGunBtn.setText(R.string.New_Addition);
        }

        hopalongBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                final Intent i = new Intent(WesternActivity.this, SelectActivity.class);
                CurrentArtist.getInstance().setCurrentArtist("Hopalong Cassidy", getApplicationContext());
                startActivity(i);
            }
        });

        ftLaramieBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                final Intent i = new Intent(WesternActivity.this, SelectActivity.class);
                CurrentArtist.getInstance().setCurrentArtist("Fort Laramie", getApplicationContext());
                startActivity(i);
            }
        });

        loneRangerBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                final Intent i = new Intent(WesternActivity.this, SelectActivity.class);
                CurrentArtist.getInstance().setCurrentArtist("Lone Ranger", getApplicationContext());
                startActivity(i);
            }
        });

        patOBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                final Intent i = new Intent(WesternActivity.this, SelectActivity.class);
                CurrentArtist.getInstance().setCurrentArtist("Pat O", getApplicationContext());
                startActivity(i);
            }
        });

        haveGunBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (!pressedhg) {
                    editor = getApplicationContext().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                    editor.putBoolean("hg", true);
                    editor.apply();
                    Log.d(TAG, "Have Gun Will Travel Pressed!");
                }

                final Intent i = new Intent(WesternActivity.this, SelectActivity.class);
                CurrentArtist.getInstance().setCurrentArtist("Have Gun Will Travel", getApplicationContext());
                startActivity(i);
            }
        });
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
    }
}