package com.tomhazell.division.mobile.assistant.DI;

import com.google.android.gms.analytics.Tracker;
import com.tomhazell.division.mobile.assistant.BattleApplication;
import com.tomhazell.division.mobile.assistant.ConsumablesPresenterImpl;
import com.tomhazell.division.mobile.assistant.Intro.IntroActivity;
import com.tomhazell.division.mobile.assistant.MainActivity;
import com.tomhazell.division.mobile.assistant.SendActionInteractor;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Tom Hazell on 19/04/2016.
 */
@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {
    void inject(BattleApplication application);

    void inject(MainActivity activity);

    void inject(IntroActivity activity);
}