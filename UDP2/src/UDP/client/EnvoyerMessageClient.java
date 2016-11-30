/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UDP.client;

import UDP.DataUdp;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.TimerTask;
import java.util.concurrent.locks.Lock;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Florian
 */
public class EnvoyerMessageClient extends TimerTask implements Runnable {

    ClientUdpWithThread client;
    DatagramSocket socket;

    /**
     *
     * @param client
     * @param socket
     */
    public EnvoyerMessageClient(ClientUdpWithThread client, 
            DatagramSocket socket) {
        this.client = client;
        this.socket = socket;
    }

    /**
     *
     */
    @Override
    public void run() {
        // Socket Udp
        HashMap<Integer, DatagramPacket> memCopy = null;
        try {
            this.client.sem.acquire();
            if (client.mem.isEmpty() && client.first == true) {
                client.latence = System.currentTimeMillis() - client.latence;
                System.out.println("latence moyenne : " +
                        client.latence/client.nbMessage);
                client.first = false;
                return;
            }
            // Données à envoyer sérializer
            memCopy = (HashMap<Integer, DatagramPacket>) client.mem.clone();
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            this.client.sem.release();

        }
        if (memCopy == null) {
            return;
        }
        DataUdp data = null;
        for (DatagramPacket p : memCopy.values()) {
            data = DataUdp.fromByteArray(p.getData());
            System.out.println("Envoi data : " + data.toString());
            try {
                if (!memCopy.isEmpty()) {
                    socket.send(p);
                    //Thread.sleep(1);
                }
            } catch (Exception ex) {
            }
        }
        if (client.arret == true) {
            System.exit(0);
        }
    }
}