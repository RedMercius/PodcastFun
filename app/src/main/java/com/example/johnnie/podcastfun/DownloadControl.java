/*
 * Copyright 2015 © Johnnie Ruffin
 *
 * Unless required by applicable law or agreed to in writing, software is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *
 */

package com.example.johnnie.podcastfun;

import android.app.Activity;
import android.app.DownloadManager;
import android.app.DownloadManager.Request;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.util.Log;

import java.io.File;

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
    public DownloadManager dm;
    private String webPath;
    private String TAG = "Download Control: ";
    private Activity context;

    /** Called when the activity is first created. */
    public DownloadControl(Activity context) {
        this.context = context;
        webPath = "http://www.JohnnieRuffin.com/audio/";
        Log.d(TAG, "DownloadControl -- constructor");
    }

    public void setWebPath(String url)
    {
        webPath = url;
    }

    public boolean deleteMedia(String filename)
    {
        boolean deleted = false;
        String filePath = (Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC) + "/" + filename);
        File file = new File(filePath);
        if (file.exists()) {
            deleted = file.delete();
        }
        return deleted;
    }

    public long getDlId()
    {
    return enqueue;
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
