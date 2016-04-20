package com.tomhazell.division.mobile.assistant;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.util.Log;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.tomhazell.division.battleassistant.R;

/**
 * Created by Tom Hazell on 17/03/2016.
 */
public class MainPresenterImpl implements MainPresenter {

    private MainView mainView;
    private Context context;

    public MainPresenterImpl(MainView mainView, Context mcontext) {
        this.mainView = mainView;
        context = mcontext;

    }

    @Override
    public void onResume() {
        //check if its the first run of the app
        //  Initialize SharedPreferences
        SharedPreferences getPrefs = PreferenceManager
                .getDefaultSharedPreferences(context);

        //  Create a new boolean and preference and set it to true
        boolean isFirstStart = getPrefs.getBoolean("firstStart", true);

        Log.w("app", isFirstStart ? "yes it is" : "No its not");
        //  If the activity has never started before...
        if (isFirstStart) {

            //  Launch app intro

            mainView.navigateToFirstLanuch();

            SharedPreferences.Editor e = getPrefs.edit();
            //  Edit preference to make it false because we don't want this to run again
            e.putBoolean("firstStart", false);

            //  Apply changes
            e.apply();
        }

        //check if the app has been updated
        try {
            PackageInfo packageinfo =
                    context.getPackageManager().getPackageInfo(context.getPackageName(), 0);

            int version = packageinfo.versionCode;

            //  Create a new boolean and preference and set it to true
            int oldVersion = getPrefs.getInt("prevVersion", 1);

            if (version > oldVersion && !isFirstStart) {
                //meaning the app has been updated, show dialog prompting user to update server
                promptToUpdateerver();

                //update version stored in prefs
                SharedPreferences.Editor e = getPrefs.edit();

                //  Edit preference to make it teh current version
                e.putInt("prevVersion", version);

                //  Apply changes
                e.apply();
            }

        } catch (PackageManager.NameNotFoundException e) {
            //Should never happen because we call .getPackageInfo(context.getPackageName(), 0);
        }
    }

    private void promptToUpdateerver(){
        new MaterialDialog.Builder(context)
                .title("App Has Been Updated")
                .content("It looks like you have updated the app, you should update the PC companion otherwise new features might not work. Get an updated version from theDivisionba.com")
                .positiveText("OK")
                .neutralText("Take me to theDivisionba.com")
                .onNeutral(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        mainView.navigateToTheWebsite();
                    }
                })
                .show();
    }

}