/*
 * Copyright 2015 © Johnnie Ruffin
 *
 * Unless required by applicable law or agreed to in writing, software is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *
 */

package com.RuffinApps.johnnie.oldtimeradiofree;

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
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

public class CustomList extends ArrayAdapter<String> {

    // This is an attempt at a view holder pattern
    static class ViewHolderItem {

        TextView txtTitle;
        TextView txtStatus;

        ImageButton playButton;
        ImageView stopButton;
        ImageButton deleteButton;
        ImageButton downloadButton;
    }

    private final Activity context;
    private final String[] radioTitle;
    private final Integer[] imageButtonList;
    private String artist;
    private MediaControl mc;
    private BroadcastReceiver receiver;
    private List<String> mRemoveList;
    private boolean mdownloadInProgress;
    private boolean mThreadRunning;
    private long mdlID;
    final String TAG = "CustomList";
    SQLiteDatabase db;

    public CustomList(Activity context, String[] radioTitle, Integer[] imageButtonList, String artist) {
        super(context, R.layout.custom_list_multi, radioTitle);

        this.context = context;
        this.radioTitle = radioTitle;
        this.imageButtonList = imageButtonList;
        this.artist = artist;

        MediaPlayer mp = new MediaPlayer();
        mRemoveList = new ArrayList<>();
        mdownloadInProgress = false;
        mdlID = 0;
        mThreadRunning = false;

        mc = new MediaControl(context, mp, artist);

        IntentFilter filter = new IntentFilter();
        filter.addAction(DownloadManager.ACTION_DOWNLOAD_COMPLETE);

        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (mdownloadInProgress) {
                    String filename;
                    Bundle extras = intent.getExtras();
                    DownloadManager.Query q = new DownloadManager.Query();
                    q.setFilterById(extras.getLong(DownloadManager.EXTRA_DOWNLOAD_ID));
                    Cursor c = mc.dc.dm.query(q);

                    if (c.moveToFirst()) {
                        int status = c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS));
                        if (status == DownloadManager.STATUS_SUCCESSFUL) {
                            String filePath = c.getString(c.getColumnIndex(DownloadManager.COLUMN_LOCAL_FILENAME));
                            filename = filePath.substring(filePath.lastIndexOf('/') + 1, filePath.length());
                            deleteFromList(filename);
                        }
                    }
                    c.close();
                    notifyDataSetChanged();
                }
            }
        };

        // register receiver
        context.registerReceiver(receiver, filter);

        // Creating database and table
        db=context.openOrCreateDatabase("downloadDB", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS download(title VARCHAR, id VARCHAR);");

        Cursor b=db.rawQuery("SELECT * FROM download", null);
        if(b.getCount()==0)
        {
            return;
        }

        while(b.moveToNext())
        {
            checkDownloadStatus(Long.parseLong(b.getString(1)), b.getString(0));
        }

        b.close();
    }

    public void deleteFromList(String filename)
    {
        mRemoveList.remove(filename);
        if (!db.isOpen())
        {
            db=context.openOrCreateDatabase("downloadDB", Context.MODE_PRIVATE, null);
        }

        db.execSQL("DELETE FROM download WHERE title='"+filename+"'");
    }

    public boolean isNetworkAvailable()
    {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(SelectActivity.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void checkDownloadStatus(final long id, final String title)
    {
        DownloadManager.Query query;
        Cursor c;
        DownloadManager downloadManager;
        downloadManager = (DownloadManager)context.getSystemService(Context.DOWNLOAD_SERVICE);
        query = new DownloadManager.Query();
        query.setFilterById(id);

        c = downloadManager.query(query);
        if(c.moveToFirst()) {
            int status = c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS));
            switch(status) {
                case DownloadManager.STATUS_PAUSED:
                    Log.d(TAG, "Download Paused!!");

                    context.runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            if (!mRemoveList.contains(title)) {
                                mRemoveList.add(title);
                            }
                            notifyDataSetChanged();
                            if (!mThreadRunning) {
                                new Thread(new delayedCheck()).start();
                                mThreadRunning = true;
                            }
                        }
                    });
                    break;
                case DownloadManager.STATUS_PENDING:
                    Log.d(TAG, "Download Pending!!");
                    context.runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            if (!mRemoveList.contains(title)) {
                                mRemoveList.add(title);
                            }
                            notifyDataSetChanged();

                            if (!mThreadRunning) {
                                new Thread(new delayedCheck()).start();
                                mThreadRunning = true;
                            }
                        }
                    });
                    break;
                case DownloadManager.STATUS_RUNNING:
                    Log.d(TAG, "Download Running!!");
                    context.runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            if (!mRemoveList.contains(title)) {
                                mRemoveList.add(title);
                            }

                            notifyDataSetChanged();
                            if (!mThreadRunning) {
                                new Thread(new delayedCheck()).start();
                                mThreadRunning = true;
                                Log.d(TAG, "New Thread_Status Running!");
                            }
                        }
                    });

                    break;
                case DownloadManager.STATUS_SUCCESSFUL:
                    Log.d(TAG, "Download Successful!!");

                    context.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mRemoveList.remove(title);
                            deleteFromList(title);
                            notifyDataSetChanged();
                        }
                    });
                    break;
                case DownloadManager.STATUS_FAILED:
                    int reason = c.getInt(c.getColumnIndex(DownloadManager.COLUMN_REASON));
                    Log.d(TAG, "Download Failed!!");
                    switch(reason)
                    {
                        case DownloadManager.ERROR_CANNOT_RESUME:
                            Log.d(TAG, "DownloadManager.ERROR_CANNOT_RESUME");
                            break;
                        case DownloadManager.ERROR_FILE_ALREADY_EXISTS:
                            Log.d(TAG, "DownloadManager.ERROR_FILE_ALREADY_EXISTS");
                            break;
                        case DownloadManager.ERROR_DEVICE_NOT_FOUND:
                            Log.d(TAG, "DownloadManager.ERROR_DEVICE_NOT_FOUND");
                            break;
                        case DownloadManager.ERROR_FILE_ERROR:
                            Log.d(TAG, "DownloadManager.ERROR_FILE_ERROR");
                            break;
                        case DownloadManager.ERROR_HTTP_DATA_ERROR:
                            Log.d(TAG, "DownloadManager.ERROR_HTTP_DATA_ERROR");
                            break;
                        case DownloadManager.ERROR_INSUFFICIENT_SPACE:
                            Log.d(TAG, "DownloadManager.ERROR_INSUFFICIENT_SPACE");
                                    mRemoveList.remove(title);
                            if (!db.isOpen())
                            {
                                db=context.openOrCreateDatabase("downloadDB", Context.MODE_PRIVATE, null);
                            }
                            db.execSQL("DELETE FROM download WHERE id='" + id + "'");
                            mc.dc.deleteMedia(title);
                            notifyDataSetChanged();
                            break;
                        case DownloadManager.ERROR_TOO_MANY_REDIRECTS:
                            Log.d(TAG, "DownloadManager.ERROR_TOO_MANY_REDIRECTS");
                            break;
                        case DownloadManager.ERROR_UNHANDLED_HTTP_CODE:
                            Log.d(TAG, "DownloadManager.ERROR_UNHANDLED_HTTP_CODE");
                            break;
                        case DownloadManager.ERROR_UNKNOWN:
                            Log.d(TAG, "DownloadManager.ERROR_UNKNOWN");
                            break;
                    }
                default:
                    context.runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            mRemoveList.remove(title);

                            if (!db.isOpen())
                            {
                                db=context.openOrCreateDatabase("downloadDB", Context.MODE_PRIVATE, null);
                            }

                            db.execSQL("DELETE FROM download WHERE id='" + id + "'");
                            mc.dc.deleteMedia(title);
                            notifyDataSetChanged();
                        }
                    });
                    break;
            }
        }
        c.close();
    }

    class delayedCheck implements Runnable
    {
        @Override
        public void run()
        {
            try {
                while (mRemoveList.size() >=1) {
                    Thread.sleep(1000);
                    Cursor b = db.rawQuery("SELECT * FROM download", null);
                    if (b.getCount() == 0) {
                        Log.d(TAG, "Download queue is empty. Deleting items from download list.");

                        if (!db.isOpen())
                        {
                            db=context.openOrCreateDatabase("downloadDB", Context.MODE_PRIVATE, null);
                        }

                        db.execSQL("delete from download");
                        mRemoveList.clear();
                        b.close();
                        return;
                    }
                    while (b.moveToNext()) {
                        checkDownloadStatus(Long.parseLong(b.getString(1)), b.getString(0));
                    }

                    b.close();
                }
            } catch (Exception e) {
                Log.e(TAG, "delayCheck_Exception: " + e);
            }
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {

        final ViewHolderItem viewHolder;

        context.setTitle(artist);
        if (view == null) {
            viewHolder = new ViewHolderItem();

            view = View.inflate( context, R.layout.custom_list_multi, null);
            viewHolder.txtTitle = (TextView) view.findViewById(R.id.txt);
            viewHolder.txtStatus = (TextView) view.findViewById(R.id.txtstatus);

            viewHolder.playButton = (ImageButton) view.findViewById(R.id.playbtn);
            viewHolder.stopButton = (ImageView) view.findViewById(R.id.stopbtn);
            viewHolder.deleteButton = (ImageButton) view.findViewById(R.id.deletebtn);
            viewHolder.downloadButton = (ImageButton) view.findViewById(R.id.downloadbtn);

            view.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolderItem) view.getTag();
        }

        final String mediaTitle = radioTitle[position];

        RadioTitle rt = new RadioTitle();

        rt.initTitles();

        String MediaFile = null;

        switch (artist) {
            case "Burns And Allen": {
                for (String mediaFile : rt.getBaMap().keySet()) {
                    if (rt.getBaMap().get(mediaFile).equals(mediaTitle)) {
                        MediaFile = mediaFile;
                    }
                }
                break;
            }

            case "Fibber McGee And Molly":
            {
                for (String mediaFile : rt.getFbMap().keySet()) {
                    if (rt.getFbMap().get(mediaFile).equals(mediaTitle)) {
                        MediaFile = mediaFile;
                    }
                }
                break;
            }

            case "Martin And Lewis":
            {
                for (String mediaFile : rt.getMlMap().keySet()) {
                    if (rt.getMlMap().get(mediaFile).equals(mediaTitle)) {
                        MediaFile = mediaFile;
                    }
                }
                break;
            }

            case "The Great GilderSleeves":
            {
                for (String mediaFile : rt.getGlMap().keySet()) {
                    if (rt.getGlMap().get(mediaFile).equals(mediaTitle)) {
                        MediaFile = mediaFile;
                    }
                }
                break;
            }

            case "XMinus1":
            {
                for (String mediaFile : rt.getXMMap().keySet()) {
                    if (rt.getXMMap().get(mediaFile).equals(mediaTitle)) {
                        MediaFile = mediaFile;
                    }
                }
                break;
            }

            case "Inner Sanctum":
            {
                for (String mediaFile : rt.getIsMap().keySet()) {
                    if (rt.getIsMap().get(mediaFile).equals(mediaTitle)) {
                        MediaFile = mediaFile;
                    }
                }
                break;
            }

            case "Dimension X":
            {
                for (String mediaFile : rt.getDxMap().keySet()) {
                    if (rt.getDxMap().get(mediaFile).equals(mediaTitle)) {
                        MediaFile = mediaFile;
                    }
                }
                break;
            }

            case "Night Beat":
            {
                for (String mediaFile : rt.getNbMap().keySet()) {
                    if (rt.getNbMap().get(mediaFile).equals(mediaTitle)) {
                        MediaFile = mediaFile;
                    }
                }
                break;
            }

            case "Speed":
            {
                for (String mediaFile : rt.getSgMap().keySet()) {
                    if (rt.getSgMap().get(mediaFile).equals(mediaTitle)) {
                        MediaFile = mediaFile;
                    }
                }
                break;
            }
        }

        final String mediaFileName = MediaFile;
        boolean isItInRaw = false;
        boolean doesMediaExist_0 = false;

        try {
            isItInRaw = mc.checkResourceInRaw(MediaFile);
            doesMediaExist_0 = mc.checkForMedia(MediaFile);
        }
        catch(NullPointerException e)
        {
            Log.e(TAG, "Null Exception: " + e);

        }
        final boolean doesMediaExist = doesMediaExist_0;

        viewHolder.txtTitle.setText(mediaTitle);

        boolean ignoreThisItem = false;

        if (mRemoveList != null && mediaFileName != null) {
            for (int i = 0; i < mRemoveList.size(); ++i) {
                if (mediaFileName.equals(mRemoveList.get(i))) {
                    ignoreThisItem = true;
                }
            }
        }

        if (!isItInRaw && !doesMediaExist && !ignoreThisItem) {
            viewHolder.downloadButton.setImageResource(imageButtonList[4]);
            viewHolder.playButton.setImageResource(imageButtonList[0]);
            viewHolder.stopButton.setImageResource(imageButtonList[8]);

            viewHolder.txtStatus.setVisibility(View.INVISIBLE);
            viewHolder.deleteButton.setVisibility(View.INVISIBLE);

            viewHolder.downloadButton.setVisibility(View.VISIBLE);
            viewHolder.txtTitle.setVisibility(View.VISIBLE);
            viewHolder.stopButton.setVisibility(View.VISIBLE);
            viewHolder.playButton.setVisibility(View.VISIBLE);
        }

        if ((isItInRaw || doesMediaExist) && !ignoreThisItem) {
            viewHolder.playButton.setImageResource(imageButtonList[0]);
            viewHolder.deleteButton.setImageResource(imageButtonList[7]);

            viewHolder.downloadButton.setVisibility(View.INVISIBLE);
            viewHolder.txtStatus.setVisibility(View.INVISIBLE);
            viewHolder.stopButton.setVisibility(View.INVISIBLE);

            viewHolder.deleteButton.setVisibility(View.VISIBLE);
            viewHolder.playButton.setVisibility(View.VISIBLE);
        }

        if (ignoreThisItem) {
            viewHolder.downloadButton.setVisibility(View.INVISIBLE);
            viewHolder.playButton.setVisibility(View.INVISIBLE);
            viewHolder.stopButton.setVisibility(View.INVISIBLE);
            viewHolder.deleteButton.setVisibility(View.INVISIBLE);
            viewHolder.txtStatus.setVisibility(View.VISIBLE);
            viewHolder.txtStatus.setText(context.getResources().getString(R.string.downloading));
        }

        viewHolder.playButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // if we need to stream this, check for internet connection.
                if (!doesMediaExist) {
                    if (!isNetworkAvailable()) {
                        Toast.makeText(context, context.getResources().getString(R.string.no_internet_stream),
                                Toast.LENGTH_SHORT).show();
                        return;
                    }
                }

                final Intent i = new Intent(context, PlayActivity.class);
                i.putExtra("MediaTitle", mediaFileName);
                i.putExtra("Selection", artist);
                i.putExtra("Title", mediaTitle);
                context.startActivity(i);
                context.finish();
            }
        });

        viewHolder.downloadButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (!mdownloadInProgress) {
                    mdownloadInProgress = true;
                }

                // if network connection is down, inform the user that we cannot download.
                if (!isNetworkAvailable()) {
                    Toast.makeText(context, context.getResources().getString(R.string.no_internet_stream),
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!isExternalStorage())
                {
                    Toast.makeText(context, context.getResources().getString(R.string.no_external_storage),
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                mc.downloadMedia(mediaFileName);
                mdlID = mc.dc.getDlId();
                db.execSQL("INSERT INTO download VALUES('" + mediaFileName + "', '" + mdlID + "');");
                checkDownloadStatus(mdlID, mediaFileName);
                Toast.makeText(context, context.getResources().getString(R.string.download_in_progress) + mediaFileName, Toast.LENGTH_SHORT).show();
            }
        });

        viewHolder.deleteButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                mc.deleteMedia(mediaFileName);
                notifyDataSetChanged();
                Toast.makeText(context, context.getResources().getString(R.string.deleting) + " " + mediaFileName, Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    public boolean isExternalStorage() {
        boolean mExternalStorageAvailable;
        boolean mExternalStorageWriteable;
        String state = Environment.getExternalStorageState();

        if (Environment.MEDIA_MOUNTED.equals(state)) {
            // We can read and write the media
            mExternalStorageAvailable = true;
            mExternalStorageWriteable = true;
        } else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            // We can only read the media
            // mExternalStorageAvailable = true;
            mExternalStorageAvailable = true;
            mExternalStorageWriteable = false;
        } else {
            // Something else is wrong. It may be one of many other states, but all we need
            //  to know is we can neither read nor write
            mExternalStorageAvailable = false;
            mExternalStorageWriteable = false;
        }

        if (!mExternalStorageWriteable) {
            mExternalStorageAvailable = false;
        }

        return mExternalStorageAvailable;
    }

    public void cleanUp(Activity context)
    {
        context.unregisterReceiver(receiver);
        db.close();
    }
}