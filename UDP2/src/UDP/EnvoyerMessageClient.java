/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UDP;

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
    public EnvoyerMessageClient(ClientUdpWithThread client, DatagramSocket socket) {
        this.client = client;
        this.socket = socket;
    }

    /**
     *
     */
    @Override
    public void run() {
        // Socket Udp
        //System.out.println("entree dans boucle d'envoi");
        //lock.lock();
        HashMap<Integer, DatagramPacket> memCopy = null;
        try {

            this.client.sem.acquire();
            if (client.mem.isEmpty()) {
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
        for (DatagramPacket p : memCopy.values()) {
            System.out.println("Envoi data : " + DataUdp.fromByteArray(p.getData()).toString());
            try {
                if (!memCopy.isEmpty()) {
                    socket.send(p);
                    Thread.sleep(100);
                }
            } catch (Exception ex) {
            }
        }
        if (client.arret == true) {
            System.exit(0);
        }
    }
}

/*
                    DataUdp data = new DataUdp(message, i);
                    byte[] dataBytes = data.toByteArray();

                // Packet à envoyer
                DatagramPacket dataSend = new DatagramPacket(dataBytes,
                        dataBytes.length, InetAddress
                                .getByName(Constantes.HOST_SERVER),
                        Constantes.PORT_SERVER);


                System.out.println("Envoi data : " + data.toString());
                socket.send(p);
 */
