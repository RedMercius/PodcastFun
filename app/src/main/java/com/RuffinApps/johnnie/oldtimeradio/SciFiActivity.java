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

public class SciFiActivity extends AppCompatActivity {

    Button xm1Btn;
    Button innSancBtn;
    Button dxBtn;
    Button ghBtn;
    Button fgBtn;
    Button srBtn;

    private String TAG = "SciFi Activity: ";

    // MY_PREFS_NAME - a static String variable like:
    public static final String MY_PREFS_NAME = "jotrpref";
    private SharedPreferences.Editor editor;
    private boolean ghPressed = false;
    private boolean fgPressed = false;
    private boolean srPressed = false;

    private RadioTitle rt;
    private boolean listLoaded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sci_fi);

        if (!listLoaded) {
            Log.d(TAG, "SciFi List not loaded!! Loading...");
            rt = RadioTitle.getInstance();

            // Load Science Fiction Shows
            rt.loadJSONSciFi(getApplicationContext());
            rt.updateSciFiTitles();
            listLoaded = true;
        }

        SharedPreferences prefs;
        prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        xm1Btn = findViewById(R.id.xm1Btn);
        innSancBtn = findViewById(R.id.innSancBtn);
        dxBtn = findViewById(R.id.dxBtn);
        ghBtn = findViewById(R.id.ghBtn);
        fgBtn = findViewById(R.id.fgBtn);
        srBtn = findViewById(R.id.srBtn);

        ghPressed = prefs.getBoolean("gh", false);
        fgPressed = prefs.getBoolean("fg", false);
        srPressed = prefs.getBoolean("sr", false);

        xm1Btn.setGravity(Gravity.END|Gravity.BOTTOM);
        innSancBtn.setGravity(Gravity.END|Gravity.BOTTOM);
        dxBtn.setGravity(Gravity.END|Gravity.BOTTOM);
        ghBtn.setGravity(Gravity.END|Gravity.BOTTOM);
        fgBtn.setGravity(Gravity.END|Gravity.BOTTOM);
        srBtn.setGravity(Gravity.END|Gravity.BOTTOM);

        if (!ghPressed)
        {
            ghBtn.setTextColor(Color.RED);
            ghBtn.setText(R.string.New_Addition);
        }

        if (!fgPressed)
        {
            fgBtn.setTextColor(Color.RED);
            fgBtn.setText(R.string.New_Addition);
        }

        if (!srPressed)
        {
            srBtn.setTextColor(Color.RED);
            srBtn.setText(R.string.New_Addition);
        }

        xm1Btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                final Intent i = new Intent(SciFiActivity.this, SelectActivity.class);
                CurrentArtist.getInstance().setCurrentArtist("XMinus1", getApplicationContext());
                startActivity(i);
            }
        });

        innSancBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                final Intent i = new Intent(SciFiActivity.this, SelectActivity.class);
                CurrentArtist.getInstance().setCurrentArtist("Inner Sanctum", getApplicationContext());
                startActivity(i);
            }
        });

        dxBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                final Intent i = new Intent(SciFiActivity.this, SelectActivity.class);
                CurrentArtist.getInstance().setCurrentArtist("Dimension X", getApplicationContext());
                startActivity(i);
            }
        });

        ghBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (!ghPressed) {
                    editor = getApplicationContext().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                    editor.putBoolean("gh", true);
                    editor.apply();
                    Log.d(TAG, "The Green Hornet Pressed!");
                }
                final Intent i = new Intent(SciFiActivity.this, SelectActivity.class);
                CurrentArtist.getInstance().setCurrentArtist("The Green Hornet", getApplicationContext());
                startActivity(i);
            }
        });

        fgBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (!fgPressed) {
                    editor = getApplicationContext().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                    editor.putBoolean("fg", true);
                    editor.apply();
                    Log.d(TAG, "Flash Gordon Pressed!");
                }
                final Intent i = new Intent(SciFiActivity.this, SelectActivity.class);
                CurrentArtist.getInstance().setCurrentArtist("Flash Gordon", getApplicationContext());
                startActivity(i);
            }
        });

        srBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (!srPressed) {
                    editor = getApplicationContext().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                    editor.putBoolean("sr", true);
                    editor.apply();
                    Log.d(TAG, "SciFi Radio Pressed!");
                }
                final Intent i = new Intent(SciFiActivity.this, SelectActivity.class);
                CurrentArtist.getInstance().setCurrentArtist("SciFi Radio", getApplicationContext());
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