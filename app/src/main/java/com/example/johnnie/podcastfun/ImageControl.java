package com.example.johnnie.podcastfun;

/**
 * Created by johnnie on 10/9/2015.
 */
public class ImageControl {

    Integer[] imageButtonList = {
            R.drawable.ic_play,
            R.drawable.ic_pause,
            R.drawable.ic_play_arrow_black_24dp
    };

    Integer[] imageButtonListStop= {
            R.drawable.ic_close_black_24dp,
            R.drawable.ic_close_black_24dp,
            R.drawable.ic_close_black_24dp,
    };

    public Integer[] getImageButtonList(){
     return imageButtonList;
    }

    public Integer[] getImageButtonListStop() {
        return imageButtonListStop;
    }

}
