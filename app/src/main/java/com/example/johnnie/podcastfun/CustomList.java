package com.example.johnnie.podcastfun;

/**
 * Created by johnnie on 10/4/2015.
 */

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

public class CustomList extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] radioTitle;
    private final Integer[] imageButtonList;
    private final Integer[] imageButtonListStop;

    public CustomList(Activity context, String[] radioTitle, Integer[] imageButtonList, Integer[] imageButtonListStop) {
        super(context, R.layout.list_single, radioTitle);
        this.context = context;
        this.radioTitle = radioTitle;
        this.imageButtonList = imageButtonList;
        this.imageButtonListStop = imageButtonListStop;

    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.list_single, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);
        final selectActivity myactivity = new selectActivity();
        final ImageButton imageButton = (ImageButton) rowView.findViewById(R.id.imgbtn);
        txtTitle.setText(radioTitle[position]);

        imageButton.setImageResource(imageButtonList[position]);
        final String myposition = radioTitle[position].toString();
        imageButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                try {
                    myactivity.callMediaFromRaw(myposition, context);
                    imageButton.setImageResource(imageButtonListStop[0]);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Toast.makeText(context, "ImageButton is clicked: " + myposition, Toast.LENGTH_SHORT).show();

            }
        });
        return rowView;
    }
}
