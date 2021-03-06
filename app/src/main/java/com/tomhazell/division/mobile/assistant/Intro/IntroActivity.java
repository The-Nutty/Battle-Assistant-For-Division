package com.tomhazell.division.mobile.assistant.Intro;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;

import com.github.paolorotolo.appintro.AppIntro2;
import com.github.paolorotolo.appintro.AppIntroFragment;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.tomhazell.division.mobile.assistant.MainActivity;
import com.tomhazell.division.battleassistant.R;
import com.tomhazell.division.mobile.assistant.BattleApplication;

import javax.inject.Inject;

/**
 * Created by Tom Hazell on 20/03/2016.
 */
public class IntroActivity extends AppIntro2 {

    @Inject
    Tracker mTracker;

    @Override
    public void init(Bundle savedInstanceState) {
        BattleApplication.from(this).getComponent().inject(this);

        addSlide(AppIntroFragment.newInstance("Download the computer companion", "First you need to download the computer companion to go with the app. This can be downloaded from TheDivisionBA.com, when you want to run the battle assistant run this.", R.drawable.downloadicon, ContextCompat.getColor(IntroActivity.this, R.color.colorPrimary)));
        addSlide(AppIntroFragment.newInstance("Preperation", "Make sure that your phone and the computer are on the same network.", R.drawable.wifiicon, ContextCompat.getColor(IntroActivity.this, R.color.colorPrimary)));
        addSlide(AppIntroFragment.newInstance("Open the companion", "Run and then open the companion from the system tray, select show IP from the menu.", R.drawable.opencompanion, ContextCompat.getColor(IntroActivity.this, R.color.colorPrimary)));
        addSlide(new IPSlide());
        addSlide(AppIntroFragment.newInstance("You may need to change the IP again", "If this stops working you should always check that the IP given by the computer has not changed, to change the ip tap \"Edit IP\"", R.drawable.editipslide2, ContextCompat.getColor(IntroActivity.this, R.color.colorPrimary)));
        setVibrate(false);

        GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);

        mTracker = analytics.newTracker(R.xml.global_tracker);
        mTracker.setScreenName("View~" + "FirstLoad");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }


    @Override
    public void onDonePressed() {
        loadMainActivity();
    }

    @Override
    public void onSlideChanged() {}

    @Override
    public void onNextPressed() {}

    private void loadMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}