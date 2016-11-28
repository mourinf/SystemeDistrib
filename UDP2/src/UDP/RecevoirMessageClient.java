/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UDP;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Scanner;
import java.util.concurrent.locks.Lock;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Florian
 */
public class RecevoirMessageClient implements Runnable {

    ClientUdpWithThread client;
    DatagramSocket socket;

    public RecevoirMessageClient(ClientUdpWithThread client, DatagramSocket socket) {
        this.client = client;
        this.socket = socket;
    }

    @Override
    public void run() {
        int numSeq;

        // packet à réceptioner
        DatagramPacket dataReceived = new DatagramPacket(new byte[1024], 1024);
        try {
            socket.setSoTimeout(10000);
        } catch (SocketException ex) {

        }
        while (true) {
            try {
                socket.receive(dataReceived);
                numSeq = DataUdp.fromByteArray(dataReceived.getData()).num;
                System.out.println("Reception data : " + DataUdp.fromByteArray(dataReceived.getData()).toString());
                if (client.mem.containsKey(numSeq)) {
                    System.out.println("mem size : " + this.client.mem.size());

                    try {
                        this.client.sem.acquire();
                        this.client.mem.remove(numSeq);

                        System.out.println("Suppression du message " + numSeq + " de la mémoire");
                        System.out.println("mem size : " + this.client.mem.size());
                    } catch (Exception e) {

                    } finally {
                        this.client.sem.release();
                    }
                }
                if (client.arret == true) {
                    System.exit(0);
                }
            } catch (Exception e) {
                System.out.println(e);
            } finally {

            }
        }
    }
}
