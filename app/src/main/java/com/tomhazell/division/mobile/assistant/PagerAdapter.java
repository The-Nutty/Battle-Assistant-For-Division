package com.tomhazell.division.mobile.assistant;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

/**
 * Created by Tom Hazell on 01/04/2016.
 */

public class PagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        //uses the number of tabs to parse correct instructions to ConsumableFragment
        switch(getCount()){
            case 1:
                Fragment tab = new ConsumableFragment();
                Bundle bundle = new Bundle();
                bundle.putString("Type", "All");
                tab.setArguments(bundle);
                return tab;
            case 2:
                switch (position) {
                    case 0:
                        Fragment tab1 = new ConsumableFragment();
                        Bundle bundleu = new Bundle();
                        bundleu.putString("Type", "Use");
                        tab1.setArguments(bundleu);
                        return tab1;
                    case 1:
                        Fragment tab2 = new ConsumableFragment();
                        Bundle bundlen = new Bundle();
                        bundlen.putString("Type", "Nade");
                        tab2.setArguments(bundlen);
                        return tab2;
                    default:
                        return null;
                }

                default:
                    return null;
        }

    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}