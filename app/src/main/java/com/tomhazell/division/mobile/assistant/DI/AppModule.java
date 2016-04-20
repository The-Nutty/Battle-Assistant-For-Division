package com.tomhazell.division.mobile.assistant.DI;

import android.content.Context;
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
    Context provideApplicationContext() {
        return mApplication;
    }

    @Provides
    @Singleton
    Tracker provideTracker() {
        GoogleAnalytics analytics = GoogleAnalytics.getInstance(mApplication.getApplicationContext());

        return analytics.newTracker(R.xml.global_tracker);
    }

}
