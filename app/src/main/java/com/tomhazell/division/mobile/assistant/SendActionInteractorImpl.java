package com.tomhazell.division.mobile.assistant;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

/**
 * Created by Tom Hazell on 17/03/2016.
 *
 * This sends a UDP message to the user entered IP. On port 54545.
 */
public class SendActionInteractorImpl implements SendActionInteractor {

    private OnFinishedListener onFinishedListener;

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
                DatagramSocket clientSocket = new DatagramSocket();
                clientSocket.setSoTimeout(1000);

                InetAddress IPAddress = InetAddress.getByName(params[1]);

                byte[] sendData;
                byte[] receiveData = new byte[1024];

                //get the parameter corresponding to the code, create and send packet
                String command = params[0];
                sendData = command.getBytes();

                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 54545);
                clientSocket.send(sendPacket);

                //attempt to receive packet (to check that the server received the command) and save it in receiveData buffer
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                try {
                    clientSocket.receive(receivePacket);
                }catch (SocketTimeoutException e){
                    clientSocket.close();
                    return "Time Out";
                }

                //trim responce to avoid padding chars being displayed.
                String response = new String(receivePacket.getData()).trim();
                clientSocket.close();

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

            onFinishedListener.onFinished(result, true);
        }
    }
}
