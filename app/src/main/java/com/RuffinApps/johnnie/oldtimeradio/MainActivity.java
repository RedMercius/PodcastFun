package com.RuffinApps.johnnie.oldtimeradio;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    Button comedyButton;
    Button scifiButton;
    Button thrillerButton;
    Button westernButton;

    private final String TAG = "MainActivity: ";

    // MY_PREFS_NAME - a static String variable like:
    public static final String MY_PREFS_NAME = "jotrpref";
    private boolean pressedComedy = false;
    private boolean pressedSciFi = false;
    private boolean pressedThriller = false;
    private boolean pressedWestern = false;
    private SharedPreferences saved_values;
    private SharedPreferences.Editor editor;
    private RadioTitle rt = null;
    private ProgressDialog p;

    private boolean listinitialized = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        saved_values = getApplicationContext().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        comedyButton = findViewById(R.id.comedyButton);
        scifiButton = findViewById(R.id.scifiButton);
        thrillerButton = findViewById(R.id.thrillerButton);
        westernButton = findViewById(R.id.westernButton);
        comedyButton.setAllCaps(false);
        westernButton.setAllCaps(false);
        scifiButton.setAllCaps(false);
        thrillerButton.setAllCaps(false);

        pressedComedy = saved_values.getBoolean("comedy", false);
        pressedSciFi = saved_values.getBoolean("scifi", false);
        pressedThriller = saved_values.getBoolean("thriller", false);
        pressedWestern = saved_values.getBoolean("western", false);

        if (!listinitialized)
        {
            Log.d(TAG, "The list is being reinitialized!!");

            if (CurrentArtist.getInstance().getCurrentArtist() == null) {
                rt = com.RuffinApps.johnnie.oldtimeradio.RadioTitle.getInstance();
                rt.init(getApplicationContext());
                com.RuffinApps.johnnie.oldtimeradio.CurrentArtist.getInstance().init(getApplicationContext());
            }
            listinitialized = true;
        }

        if (pressedComedy)
        {
            comedyButton.setText(R.string.category_comedy);
        }
        else
        {
            SpannableString textComedy = new SpannableString("Comedy New!!");
            // make "Clicks" (characters 0 to 5) Red
            textComedy.setSpan(new ForegroundColorSpan(Color.BLACK), 0, 6, 0);
            // make "Here" (characters 6 to 10) Blue
            textComedy.setSpan(new ForegroundColorSpan(Color.RED), 7, 12, 0);
            // shove our styled text into the Button
            comedyButton.setText(textComedy, TextView.BufferType.SPANNABLE);
        }

        if (pressedSciFi)
        {
            scifiButton.setText(R.string.category_scifi);
        }
        else
        {
            SpannableString textScifi = new SpannableString("Science Fiction New!!");
            // make "Clicks" (characters 0 to 5) Red
            textScifi.setSpan(new ForegroundColorSpan(Color.BLACK), 0, 14, 0);
            // make "Here" (characters 6 to 10) Blue
            textScifi.setSpan(new ForegroundColorSpan(Color.RED), 15, 21, 0);
            // shove our styled text into the Button
            scifiButton.setText(textScifi, TextView.BufferType.SPANNABLE);
        }

        if (pressedThriller)
        {
            thrillerButton.setText(R.string.category_thriller);
        }
        else
        {
            SpannableString textthriller = new SpannableString("Thriller New!!");
            // make "Clicks" (characters 0 to 5) Red
            textthriller.setSpan(new ForegroundColorSpan(Color.BLACK), 0, 8, 0);
            // make "Here" (characters 6 to 10) Blue
            textthriller.setSpan(new ForegroundColorSpan(Color.RED), 9, 14, 0);
            // shove our styled text into the Button
            thrillerButton.setText(textthriller, TextView.BufferType.SPANNABLE);
        }

        if (pressedWestern)
        {
            westernButton.setText(R.string.category_western);
        }
        else
        {
            SpannableString textWestern = new SpannableString("Western New!!");
            // make "Clicks" (characters 0 to 5) Red
            textWestern.setSpan(new ForegroundColorSpan(Color.BLACK), 0, 7, 0);
            // make "Here" (characters 6 to 10) Blue
            textWestern.setSpan(new ForegroundColorSpan(Color.RED), 8, 13, 0);
            // shove our styled text into the Button
            westernButton.setText(textWestern, TextView.BufferType.SPANNABLE);
        }

        comedyButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if(!pressedComedy) {
                    editor = saved_values.edit();
                    editor.putBoolean("comedy", true);
                    editor.apply();
                }

                final Intent i = new Intent(MainActivity.this, ComedyActivity.class);
                startActivity(i);
            }
        });

        scifiButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if(!pressedSciFi) {
                    editor = saved_values.edit();
                    editor.putBoolean("scifi", true);
                    editor.apply();
                }

                final Intent i = new Intent(MainActivity.this, SciFiActivity.class);
                startActivity(i);
            }
        });

        thrillerButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if(!pressedThriller) {
                    editor = saved_values.edit();
                    editor.putBoolean("thriller", true);
                    editor.apply();
                }

                final Intent i = new Intent(MainActivity.this, ThrillerActivity.class);
                startActivity(i);
            }
        });

        westernButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if(!pressedWestern) {
                    editor = saved_values.edit();
                    editor.putBoolean("western", true);
                    editor.apply();
                }

                final Intent i = new Intent(MainActivity.this, WesternActivity.class);
                startActivity(i);
            }
        });

        comedyButton.requestFocus();
        AdapterState.getInstance().init(this);
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

            final Intent i = new Intent(MainActivity.this, MediaPlay.class);
            i.putExtra("MediaTitle", CurrentArtist.getInstance().getCurrentFile());
            i.putExtra("Selection", CurrentArtist.getInstance().getCurrentArtist());
            i.putExtra("Title", CurrentArtist.getInstance().getCurrentTitle());
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
