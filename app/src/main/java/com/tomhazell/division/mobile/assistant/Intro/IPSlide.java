package com.tomhazell.division.mobile.assistant.Intro;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.tomhazell.division.battleassistant.R;

/**
 * Created by Tom Hazell on 20/03/2016.
 *
 * This is a fragment to be loaded by the app intro to allow for an edit text
 */
public class IPSlide extends Fragment {

    private EditText et;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.slide_ip, container, false);
        et = (EditText) v.findViewById(R.id.IppSlideEt);
        return v;
    }


    @Override
    public void onPause() {
        //save the value of the edit text
        super.onPause();
        SharedPreferences.Editor editor = getActivity().getSharedPreferences("settings", getActivity().MODE_PRIVATE).edit();
        editor.putString("IP", et.getText().toString());
        editor.commit();

    }


}