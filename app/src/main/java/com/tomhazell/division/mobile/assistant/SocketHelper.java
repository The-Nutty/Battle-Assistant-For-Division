package com.tomhazell.division.mobile.assistant;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;

/**
 * Created by Tom Hazell on 20/04/2016.
 */
public class SocketHelper {

    DatagramSocket clientSocket;
    int port = 54545;

    public SocketHelper() throws SocketException {
        clientSocket = new DatagramSocket();
        clientSocket.setSoTimeout(1000);

    }

    public void SendPacket(String command, InetAddress addr) throws IOException {
        byte[] sendData = command.getBytes();

        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, addr, port);
        clientSocket.send(sendPacket);

    }

    public String RecivePacket() throws IOException {
        byte[] receiveData = new byte[1024];
        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

        clientSocket.receive(receivePacket);
        return new String(receivePacket.getData()).trim();
    }
}
