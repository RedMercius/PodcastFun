/*
 * Copyright 2015 © Johnnie Ruffin
 *
 * Unless required by applicable law or agreed to in writing, software is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *
 */

package com.example.johnnie.podcastfun;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.DownloadManager;
import android.app.DownloadManager.Query;
import android.app.DownloadManager.Request;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.io.File;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;

//////////////////////////////////////////////////////////////////////////////////////
//
///  @class DownloadControl
//
///  @author Johnnie
//
///  @brief A class to control the Download of files
//
///  @created 10/10/2015
//
//////////////////////////////////////////////////////////////////////////////////////
public class DownloadControl extends Activity {
    private long enqueue;
    private DownloadManager dm;
    private String webPath;
    private String TAG = "Download Control: ";
    private boolean m_downloadSpaceRemaining;
    private long m_mediaFileSize;

    // TODO: check to see if we can detect an SD card
    // TODO: check to see if there is enough space to download a file.

    public boolean isExternalStorage(){
        boolean mExternalStorageAvailable = false;
        boolean mExternalStorageWriteable = false;
        String state = Environment.getExternalStorageState();

        if (Environment.MEDIA_MOUNTED.equals(state)) {
            // We can read and write the media
            mExternalStorageAvailable = true;
            mExternalStorageWriteable = true;
        } else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            // We can only read the media
            mExternalStorageAvailable = true;
            mExternalStorageWriteable = false;
        } else {
            // Something else is wrong. It may be one of many other states, but all we need
            //  to know is we can neither read nor write
            mExternalStorageAvailable = mExternalStorageWriteable = false;
        }
        return mExternalStorageAvailable;
    }

    /** Called when the activity is first created. */
    public DownloadControl(Activity context) {
        webPath = "http://www.JohnnieRuffin.com/audio/";
        Log.e("DownloadControl:", "DownloadControl -- constructor");

        m_mediaFileSize = 0;
        m_downloadSpaceRemaining = false;
    }

    public void setWebPath(String url)
    {
        webPath = url;
    }

    public void deleteMedia(String filename)
    {
        String filePath = (Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC) + "/" + filename);
        File file = new File(filePath);
        Log.d(TAG, "Delete Media: " + filePath);
        if (file.exists()) {
            boolean deleted = file.delete();
        }
    }

    public void downloadFile(String filename, Activity context) {

        String customfilePath =(webPath + filename);
        dm = (DownloadManager) context.getSystemService(DOWNLOAD_SERVICE);
        Request request = new Request(
                Uri.parse(customfilePath)).setDestinationInExternalPublicDir(Environment.DIRECTORY_MUSIC, filename);
        enqueue = dm.enqueue(request);

        Log.e(TAG, "downloadFile: " + filename);
    }
}
