package com.tomhazell.division.mobile.assistant;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.tomhazell.division.mobile.assistant.DI.DaggerAppComponent;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

/**
 * Created by Tom Hazell on 17/03/2016.
 *
 * This sends a UDP message to the user entered IP. On port 54545.
 */
public class SendActionInteractorImpl implements SendActionInteractor {

    private OnFinishedListener onFinishedListener;

    SocketHelper SHelper;

    public SendActionInteractorImpl(SocketHelper socketH){
        SHelper = socketH;

    }

    @Override
    public void SendAction(OnFinishedListener listener, String code, String Ip) {
        onFinishedListener = listener;

        new AsyncTaskRunner().execute(code, Ip);
    }


    private class AsyncTaskRunner extends AsyncTask<String, String, String> {

        //expecting 2 params, the code to send to server and the Ip from savedprefs.
        @Override
        protected String doInBackground(String... params) {
            try {



                InetAddress IPAddress = InetAddress.getByName(params[1]);


                //get the parameter corresponding to the code, create and send packet
                String command = params[0];

                SHelper.SendPacket(command, IPAddress);

                //attempt to receive packet (to check that the server received the command) and save it in receiveData buffer
                String response;
                try {
                    response = SHelper.RecivePacket();
                }catch (SocketTimeoutException e){
                    return "Time Out";
                }

                if(!response.equals("0")){
                    return "unexpected result: " + response;
                }

            }catch (UnknownHostException e){
                return "Ip cant be found";
            }catch(IOException e){
                return e.toString();
            }
            //no error, so dont return a message
            return "";
        }

        @Override
        protected void onPostExecute(String result) {

            onFinishedListener.onFinished(result);
        }
    }
}
