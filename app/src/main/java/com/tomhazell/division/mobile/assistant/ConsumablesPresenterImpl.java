package com.tomhazell.division.mobile.assistant;

import android.content.Context;
import android.content.SharedPreferences;

import com.tomhazell.division.battleassistant.R;

import java.net.InetAddress;

/**
 * Created by Tom Hazell on 17/03/2016.
 */
public class ConsumablesPresenterImpl implements ConsumablesPresenter, OnFinishedListener{

    private MainView mainView;
    private SendActionInteractor sendActionInteractor;
    private Context context;

    public ConsumablesPresenterImpl(MainView mainView, Context mcontext) {
        this.mainView = mainView;
        sendActionInteractor = new SendActionInteractorImpl();
        context = mcontext;

    }

    @Override
    public void onClick(ListItem item) {
        SharedPreferences prefs = context.getSharedPreferences("settings", context.MODE_PRIVATE);

        String Ip = prefs.getString("IP", "");
        sendActionInteractor.SendAction(this, item.Command, Ip);
    }

    @Override
    public void onResume() {

    }

    @Override
    public ListItem[] getListItems(String Type) {
        ListItem[] array = null;

        if(Type.equals("Use")){
            array  = new ListItem[6];
            array[0] = new ListItem("Incendiary Bullets", "have a chance to light target on fire for extra damage", R.drawable.incendery, "AmmoI");
            array[1] = new ListItem("Explosive Bullets", "have a chance to deal area of effect damage", R.drawable.explosive, "AmmoE");
            array[2] = new ListItem("Soda", "reduces cooldown on all skills by 30% for 30 seconds", R.drawable.soda, "DrinkS");
            array[3] = new ListItem("Water", "increases damage dealt to elite enemies by 20% for 30 seconds", R.drawable.water, "DrinkW");
            array[4] = new ListItem("Energy Bar", "instantly removes all negative status effects", R.drawable.candybar, "FoodB");
            array[5] = new ListItem("Canned Food", "increases healing effect by 40% for 30 seconds", R.drawable.cannedfood, "FoodC");
        }else if (Type.equals("Nade")){
            array  = new ListItem[6];
            array[0] = new ListItem("Fragmentation grenade", "Deal high damage and have a chance to apply Bleed.", R.drawable.nadefrag, "NadeFrag");
            array[1] = new ListItem("Flashbang grenade", "Deal Blind-Deaf, which will temporarily impair vision and hearing.", R.drawable.nadeflash, "NadeFlash");
            array[2] = new ListItem("EMP grenade", "Apply Disrupt, which will destroy any active skills caught in the blast.", R.drawable.nadeemp, "NadeEMP");
            array[3] = new ListItem("Tear gas grenade", "Apply Disoriented, which will temporarily impair the vision, as well as the accuracy.", R.drawable.nadegas, "NadeGas");
            array[4] = new ListItem("Shock grenade", "Apply Shock, which will temporarily paralyze any enemy caught in the blast and also deal damage.", R.drawable.nadeshock, "NadeShock");
            array[5] = new ListItem("Incendiary grenade", "Release fire on detonation. This fire has a chance to apply Burn.", R.drawable.nadeinc, "NadeInc");
        }else if(Type.equals("All")){
            array  = new ListItem[12];
            array[0] = new ListItem("Incendiary Bullets", "have a chance to light target on fire for extra damage", R.drawable.incendery, "AmmoI");
            array[1] = new ListItem("Explosive Bullets", "have a chance to deal area of effect damage", R.drawable.explosive, "AmmoE");
            array[2] = new ListItem("Soda", "reduces cooldown on all skills by 30% for 30 seconds", R.drawable.soda, "DrinkS");
            array[3] = new ListItem("Water", "increases damage dealt to elite enemies by 20% for 30 seconds", R.drawable.water, "DrinkW");
            array[4] = new ListItem("Energy Bar", "instantly removes all negative status effects", R.drawable.candybar, "FoodB");
            array[5] = new ListItem("Canned Food", "increases healing effect by 40% for 30 seconds", R.drawable.cannedfood, "FoodC");
            array[6] = new ListItem("Fragmentation grenade", "Deal high damage and have a chance to apply Bleed.", R.drawable.nadefrag, "NadeFrag");
            array[7] = new ListItem("Flashbang grenade", "Deal Blind-Deaf, which will temporarily impair vision and hearing.", R.drawable.nadeflash, "NadeFlash");
            array[8] = new ListItem("EMP grenade", "Apply Disrupt, which will destroy any active skills caught in the blast.", R.drawable.nadeemp, "NadeEMP");
            array[9] = new ListItem("Tear gas grenade", "Apply Disoriented, which will temporarily impair the vision, as well as the accuracy.", R.drawable.nadegas, "NadeGas");
            array[10] = new ListItem("Shock grenade", "Apply Shock, which will temporarily paralyze any enemy caught in the blast and also deal damage.", R.drawable.nadeshock, "NadeShock");
            array[11] = new ListItem("Incendiary grenade", "Release fire on detonation. This fire has a chance to apply Burn.", R.drawable.nadeinc, "NadeInc");
        }
        return array;
    }

    @Override
    public void onFinished(String message, boolean error){
        if (error){
            mainView.showToast(message);
        }
    }
}
