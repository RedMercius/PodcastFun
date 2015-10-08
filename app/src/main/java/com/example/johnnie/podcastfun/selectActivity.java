package com.example.johnnie.podcastfun;

import android.app.Activity;
import android.content.Intent;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import static android.widget.Toast.makeText;


public class selectActivity extends AppCompatActivity {
    //MediaPlayer mp = MediaPlayer.create(this, R.raw.bagracieplayssadiethompson);
    ListView listview;

    String[] radioTitle = {
            "ba_1943_02_15_AreHusbandsNecessary",
            "ba_SantasWorkshop",
            "ba_1936_01_16_GraciePlaysSadieThompson"
    };

    Integer[] imageButtonList = {
            R.drawable.ic_play_arrow_black_24dp,
            R.drawable.ic_play_arrow_black_24dp,
            R.drawable.ic_play_arrow_black_24dp
    };

    Integer[] imageButtonListStop= {
         R.drawable.ic_close_black_24dp,
            R.drawable.ic_close_black_24dp,
            R.drawable.ic_close_black_24dp,
    };

private ArrayAdapter<String> listAdapter ;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_select);

            CustomList adapter = new
                    CustomList(this, radioTitle, imageButtonList, imageButtonListStop);
            listview = (ListView) findViewById(R.id.listview);
            listview.setAdapter(adapter);
            listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    final String item = (String) parent.getItemAtPosition(position);
                    try {
                        callMediaFromRaw(item, selectActivity.this);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    //Toast.makeText(selectActivity.this,"You Clicked at " + radioTitle[+position], Toast.LENGTH_SHORT).show();

                }
            });

           /* listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, final View view,
                                        int position, long id) {
                    final String item = (String) parent.getItemAtPosition(position);
                    view.animate().setDuration(2000).alpha(0)
                            .withEndAction(new Runnable() {
                                @Override
                                public void run() {
                                    //list.remove(item);
                                    //adapter.notifyDataSetChanged();
                                    //view.setAlpha(1);
                                    try {
                                        callMediaFromRaw(item);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                }

            });*/


            /*MediaPlayer mp = MediaPlayer.create(this, R.raw.bagracieplayssadiethompson);
            mp.start();*/
            // Button btn = (Button) findViewById(R.id.imageBtn2);
            // registerForContextMenu(btn);
           // addListenerOnButton();
        }

    public void callMediaFromRaw(String item, Activity context) throws IOException {
        switch(item)
        {
            case "ba_1943_02_15_AreHusbandsNecessary":
            {
                MediaPlayer mp = MediaPlayer.create(context, R.raw.bagracieplayssadiethompson);
                mp.start();
                break;
            }
            case "ba_SantasWorkshop":
            {
                MediaPlayer mp = MediaPlayer.create(selectActivity.this, R.raw.basantasworkshop);
                mp.start();
                break;
            }
            case "ba_1936_01_16_GraciePlaysSadieThompson":
            {
                // ---tbd--- ---jlr-- check to see if the file exists or error will happen.
                String url = "http://www.JohnnieRuffin.com/audio/baarehusbandsnecessary.mp3"; // initialize Uri here
                MediaPlayer mp = new MediaPlayer();
                mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mp.setDataSource(url);
                mp.prepare();
                mp.start();
                break;
            }

        }

    }

    private void callMediaFromContentResolver()
    {
       /* Uri myUri = ....; // initialize Uri here
        MediaPlayer mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setDataSource(getApplicationContext(), myUri);
        mediaPlayer.prepare();
        mediaPlayer.start();*/
    }

    private void callMediaFromInternet()
    {
        /*String url = "http://........"; // your URL here
        MediaPlayer mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setDataSource(url);
        mediaPlayer.prepare(); // might take long! (for buffering, etc)
        mediaPlayer.start();*/
    }
        /*@Override
        public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo) {
            super.onCreateContextMenu(menu, v, menuInfo);
            menu.setHeaderTitle("Context Menu");
            menu.add(0, v.getId(), 0, "Action 1");
            menu.add(0, v.getId(), 0, "Action 2");
        }*/

        @Override
        public boolean onContextItemSelected(MenuItem item) {
            if(item.getTitle().equals("Action 1")){function1(item.getItemId());}
            else if(item.getTitle().equals("Action 2")){function2(item.getItemId());}
            else {return false;}
            return true;
        }

        public void function1(int id){
            makeText(this, "function 1 called", Toast.LENGTH_SHORT).show();
        }
        public void function2(int id){
            makeText(this, "function 2 called", Toast.LENGTH_SHORT).show();
        }
    }
