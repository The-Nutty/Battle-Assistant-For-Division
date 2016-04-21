package com.tomhazell.division.mobile.assistant.DI;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
import com.tomhazell.division.battleassistant.R;
import com.tomhazell.division.mobile.assistant.BattleApplication;
import com.tomhazell.division.mobile.assistant.SocketHelper;

import java.net.SocketException;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Tom Hazell on 19/04/2016.
 */
@Module
public class AppModule {

    private final BattleApplication mApplication;

    public AppModule(BattleApplication application) {
        mApplication = application;
    }

    @Provides
    @Singleton
    Application provideApplication() {
        return mApplication;
    }

    @Provides
    @Singleton
    Tracker provideTracker(Application application) {
        GoogleAnalytics analytics = GoogleAnalytics.getInstance(application);

        return analytics.newTracker(R.xml.global_tracker);
    }

    @Provides
    @Singleton
    SharedPreferences provideSharedPreferences(Application application) {
        return application.getSharedPreferences("settings", 0);
    }

    @Provides
    @Singleton
    SharedPreferences.Editor provideSharedPreferencesEditor(SharedPreferences sPref) {
        return sPref.edit();
    }

}
