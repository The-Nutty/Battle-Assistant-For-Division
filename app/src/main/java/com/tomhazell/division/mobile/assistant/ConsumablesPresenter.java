package com.tomhazell.division.mobile.assistant;

/**
 * Created by Tom Hazell on 17/03/2016.
 */
public interface ConsumablesPresenter {

    void onClick(ListItem item);

    void onResume();

    ListItem[] getListItems(String Type);

}
