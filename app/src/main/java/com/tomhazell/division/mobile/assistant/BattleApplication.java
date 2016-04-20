package com.tomhazell.division.mobile.assistant;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import com.tomhazell.division.mobile.assistant.DI.AppComponent;
import com.tomhazell.division.mobile.assistant.DI.AppModule;
import com.tomhazell.division.mobile.assistant.DI.DaggerAppComponent;

/**
 * Created by Tom Hazell on 19/04/2016.
 */
public class BattleApplication extends Application {
    private AppComponent mComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        mComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
        mComponent.inject(this);
    }

    public AppComponent getComponent() {
        return mComponent;
    }


    public static BattleApplication from(@NonNull Context context) {
        return (BattleApplication) context.getApplicationContext();
    }
}
