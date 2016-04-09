package com.tomhazell.division.mobile.assistant;

import android.graphics.Bitmap;

/**
 * Created by Tom Hazell on 18/03/2016.
 * used to store teh data put in to the recycler views about each item.
 */
public class ListItem {

    public String Title, Discription;
    public int Icon;
    public String Command;

    public ListItem(String title, String discription, int icon, String command){
        Title = title;
        Discription = discription;
        Icon = icon;
        Command = command;

    }

}
