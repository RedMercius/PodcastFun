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

public class SciFiActivity extends AppCompatActivity {

    ImageButton xm1Btn;
    ImageButton innSancBtn;
    ImageButton dxBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sci_fi);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        xm1Btn = findViewById(R.id.xm1Btn);
        innSancBtn = findViewById(R.id.innSancBtn);
        dxBtn = findViewById(R.id.dxBtn);

        xm1Btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                final Intent i = new Intent(SciFiActivity.this, SelectActivity.class);
                CurrentArtist.getInstance().setCurrentArtist("XMinus1");
                startActivity(i);
            }
        });

        innSancBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                final Intent i = new Intent(SciFiActivity.this, SelectActivity.class);
                CurrentArtist.getInstance().setCurrentArtist("Inner Sanctum");
                startActivity(i);
            }
        });

        dxBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                final Intent i = new Intent(SciFiActivity.this, SelectActivity.class);
                CurrentArtist.getInstance().setCurrentArtist("Dimension X");
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
