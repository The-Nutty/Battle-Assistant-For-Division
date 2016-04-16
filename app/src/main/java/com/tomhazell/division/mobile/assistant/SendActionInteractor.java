package com.tomhazell.division.mobile.assistant;

/**
 * Created by Tom Hazell on 17/03/2016.
 */
public interface SendActionInteractor {

    void SendAction(OnFinishedListener listener, String code, String Ip);
}
