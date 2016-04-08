/*
 * Copyright 2015 © Johnnie Ruffin
 *
 * Unless required by applicable law or agreed to in writing, software is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *
 */

package com.RuffinApps.johnnie.oldtimeradiofree;

/////////////////////////////////////////////////////////////////////////////
//
/// @class ImageControl
//
/// @brief ImageControl class controls image items
//
/// @author Johnnie Ruffin
//
////////////////////////////////////////////////////////////////////////////

public class ImageControl {

    Integer[] imageButtonList = {
            R.mipmap.ic_play,
            R.mipmap.ic_pause,
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
