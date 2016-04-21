package com.tomhazell.division.mobile.assistant;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.PowerManager;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.RelativeLayout;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import com.afollestad.materialdialogs.MaterialDialog;
import com.tomhazell.division.mobile.assistant.Intro.IntroActivity;
import com.tomhazell.division.battleassistant.R;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity implements MainView {

    private MainPresenter presenter;

    @Inject
    Tracker mTracker;

    @Inject
    SharedPreferences sPrefs;

    @Inject
    SharedPreferences.Editor sPrefsEdit;

    PowerManager.WakeLock wakeLock;

    PagerAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BattleApplication.from(this).getComponent().inject(this);

        presenter = new MainPresenterImpl(this, MainActivity.this);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RelativeLayout layout = (RelativeLayout) findViewById(R.id.main_layout);
        layout.setBackgroundResource(R.drawable.background);


        boolean tabed = sPrefs.getBoolean("Tabed", true);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        if(tabed){
            tabLayout.addTab(tabLayout.newTab().setText("Consumables"));
            tabLayout.addTab(tabLayout.newTab().setText("Nades"));

        }else{
            //hides the tab selector bar
            tabLayout.addTab(tabLayout.newTab().setText("All"));
            tabLayout.setVisibility(View.GONE);
        }

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        adapter = new PagerAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        final ViewPager FviewPager = viewPager;
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                FviewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });

        //init admob
        AdView mAdView = (AdView) findViewById(R.id.adViewMain);
        AdRequest adRequest = new AdRequest.Builder()
        .addTestDevice("EBE891961B797AFB872D011247B32C1D").build();
        mAdView.loadAd(adRequest);

        //init wakelock
        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        wakeLock = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "Battle Assistant For The Division");

        //set wake lock if appropriate
        boolean isChecked = sPrefs.getBoolean("menu_checkbox_screen", false);
        if (isChecked){
            wakeLock.acquire();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.onResume();

        mTracker.setScreenName("View~" + "Main");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

    @Override
    public void showToast(String message) {
        Snackbar snackbar = Snackbar
                .make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    @Override
    public void navigateToFirstLanuch() {
        Intent i = new Intent(MainActivity.this, IntroActivity.class);
        startActivity(i);
    }

    @Override
    public void navigateToTheWebsite() {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://TheDivisionBA.com"));
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        //set value for screen lock
        boolean isChecked = sPrefs.getBoolean("menu_checkbox_screen", false);
        boolean tabbed = sPrefs.getBoolean("Tabed", true);

        MenuItem item = menu.findItem(R.id.action_ScreenOn);
        item.setChecked(isChecked);

        MenuItem item1 = menu.findItem(R.id.action_tabbed);
        item1.setChecked(tabbed);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_IP:
                IPdialog();
                return true;

            case R.id.action_More:
                navigateToTheWebsite();
                return true;

            case R.id.action_ScreenOn:
                //save checked state
                item.setChecked(!item.isChecked());

                sPrefsEdit.putBoolean("menu_checkbox_screen", item.isChecked());
                sPrefsEdit.commit();

                if(item.isChecked()){
                    wakeLock.acquire();
                }else{
                    wakeLock.release();
                }
                return true;

            case R.id.action_viewtype:
                viewDialog();
                return true;

            case R.id.action_tabbed:
                item.setChecked(!item.isChecked());
                sPrefsEdit.putBoolean("Tabed", item.isChecked());
                sPrefsEdit.commit();
                showToast("Restarting App");
                restartApp();
                return true;

            case R.id.action_addfree:
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.tomhazell.division.mobile.assistantpro"));
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void viewDialog() {

        String view = sPrefs.getString("view", "List");
        int selection;
        switch(view){
            case "List":
                selection = 0;
                break;
            case "Icon":
                selection = 1;
                break;
            default:
                selection = 0;
                break;
        }

        new MaterialDialog.Builder(this)
                .title("Select View type")
                .items(new String[]{"List", "Icon Only"})
                .content("Select the desired layout")
                .itemsCallbackSingleChoice(selection, new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
                        String saveText;

                        switch (which){
                            case 0:
                                saveText = "List";
                                break;
                            case 1:
                                saveText = "Icon";
                                break;

                            default:
                                saveText = "List";
                                break;
                        }
                        sPrefsEdit.putString("view", saveText);
                        sPrefsEdit.commit();

                        MainActivity.this.recreate();

                        return true;
                    }
                })
                .show();


    }

    private void IPdialog(){
        String ip = sPrefs.getString("IP", "");

        new MaterialDialog.Builder(this)
                .title("IP Address")
                .content("Enter the IP address that was given by the companion program")
                .input("IP address", ip, new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(MaterialDialog dialog, CharSequence input) {
                        sPrefsEdit.putString("IP", input.toString());
                        sPrefsEdit.commit();
                    }
                }).show();
    }

    private void restartApp(){
        Intent mStartActivity = new Intent(this, MainActivity.class);
        int mPendingIntentId = 123456;
        PendingIntent mPendingIntent = PendingIntent.getActivity(this, mPendingIntentId,    mStartActivity, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager mgr = (AlarmManager)this.getSystemService(Context.ALARM_SERVICE);
        mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 100, mPendingIntent);

    }

}

