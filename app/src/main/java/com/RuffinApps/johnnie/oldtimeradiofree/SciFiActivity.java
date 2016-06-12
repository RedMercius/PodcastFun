/*
 * Copyright 2015 © Johnnie Ruffin
 *
 * Unless required by applicable law or agreed to in writing, software is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *
 */

package com.RuffinApps.johnnie.oldtimeradiofree;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        xm1Btn = (ImageButton) findViewById(R.id.xm1Btn);
        innSancBtn = (ImageButton) findViewById(R.id.innSancBtn);
        dxBtn = (ImageButton) findViewById(R.id.dxBtn);

        xm1Btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                final Intent i = new Intent(SciFiActivity.this, SelectActivity.class);
                i.putExtra("Selection", "XMinus1");
                startActivity(i);
            }
        });

        innSancBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                final Intent i = new Intent(SciFiActivity.this, SelectActivity.class);
                i.putExtra("Selection", "Inner Sanctum");
                startActivity(i);
            }
        });

        dxBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                final Intent i = new Intent(SciFiActivity.this, SelectActivity.class);
                i.putExtra("Selection", "Dimension X");
                startActivity(i);
            }
        });

    }
}
