/*
 * Copyright 2015 © Johnnie Ruffin
 *
 * Unless required by applicable law or agreed to in writing, software is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *
 */

package com.example.johnnie.podcastfun;

/////////////////////////////////////////////////////////////////////////////
//
/// @class ImageControl
//
/// @brief ImageControl class controls image items
//
/// @author Johnnie Ruffin
//
////////////////////////////////////////////////////////////////////////////

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ImageButton;

import java.io.IOException;

public class ImageControl {

    private enum actionList
    {
        play,
        pause,
        stop,
        close,
        download,
        info,
        delete,
        stream
    };

    Integer[] imageButtonList = {
            R.drawable.ic_play,
            R.drawable.ic_pause,
            R.drawable.ic_stop,
            R.drawable.ic_close,
            R.drawable.ic_download,
            R.drawable.ic_download_0,
            R.drawable.ic_info,
            R.drawable.ic_delete,
            R.drawable.ic_stream
    };

    public Integer[] getImageButtonList(){
     return imageButtonList;
    }
}
