/*
 * Copyright 2015 © Johnnie Ruffin
 *
 * Unless required by applicable law or agreed to in writing, software is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *
 */

package com.RuffinApps.johnnie.oldtimeradio;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;

public class WesternActivity extends AppCompatActivity {

    ImageButton hopalongBtn;
    ImageButton ftLaramieBtn;
    ImageButton loneRangerBtn;
    ImageButton patOBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_western);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        hopalongBtn = findViewById(R.id.hopalongBtn);
        ftLaramieBtn = findViewById(R.id.ftLaramieBtn);
        loneRangerBtn = findViewById(R.id.lrBtn);
        patOBtn = findViewById(R.id.poBtn);

        hopalongBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                final Intent i = new Intent(WesternActivity.this, SelectActivity.class);
                CurrentArtist.getInstance().setCurrentArtist("Hopalong Cassidy");
                startActivity(i);
            }
        });

        ftLaramieBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                final Intent i = new Intent(WesternActivity.this, SelectActivity.class);
                CurrentArtist.getInstance().setCurrentArtist("Fort Laramie");
                startActivity(i);
            }
        });

        loneRangerBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                final Intent i = new Intent(WesternActivity.this, SelectActivity.class);
                CurrentArtist.getInstance().setCurrentArtist("Lone Ranger");
                startActivity(i);
            }
        });

        patOBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                final Intent i = new Intent(WesternActivity.this, SelectActivity.class);
                CurrentArtist.getInstance().setCurrentArtist("Pat O");
                startActivity(i);
            }
        });
    }

    // TODO: Handle hard input button presses or joystick button presses.
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        boolean handled = false;

        switch (keyCode){
            case KeyEvent.KEYCODE_DPAD_CENTER:
            case KeyEvent.KEYCODE_BUTTON_A:
                // ... handle selections
                handled = true;
                break;
            case KeyEvent.KEYCODE_DPAD_LEFT:
                // ... handle left action
                handled = true;
                break;
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                // ... handle right action
                handled = true;
                break;
        }
        return handled || super.onKeyDown(keyCode, event);
    }
}
