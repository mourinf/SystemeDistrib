/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UDP;

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
        try {
            this.client.sem.acquire();
            if (!client.mem.isEmpty()) {
                // Données à envoyer sérializer
                int max = 1000;
                int i = 1;
                for (DatagramPacket p : client.mem.values()) {
                    System.out.println("Envoi data : " + DataUdp.fromByteArray(p.getData()).toString());
                    socket.send(p);
                    i++;
                    if (i > max) {
                        break;
                    }
                    /*    client.memAttente.put(DataUdp.fromByteArray(p.getData()).num, p);
                            client.mem.remove(p);*/
                }
            }
            if (client.arret == true) {
                System.exit(0);
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            this.client.sem.release();
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
    }
}
