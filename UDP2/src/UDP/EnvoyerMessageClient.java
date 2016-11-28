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

    public EnvoyerMessageClient(ClientUdpWithThread client, DatagramSocket socket) {
        this.client = client;
        this.socket = socket;
    }

    @Override
    public void run() {
        // Socket Udp
        //System.out.println("entree dans boucle d'envoi");
        //lock.lock();
        try {
            if (!client.mem.isEmpty()) {
                // Données à envoyer sérializer
                for (DatagramPacket p : client.mem.values()) {
                    System.out.println("Envoi data : " + DataUdp.fromByteArray(p.getData()).toString());
                    socket.send(p);
                    /*    client.memAttente.put(DataUdp.fromByteArray(p.getData()).num, p);
                            client.mem.remove(p);*/
                }
            }
            if (client.arret == true) {
                client.mem.clear();
                System.exit(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erreur dans boucle d'envoi");
        } finally {
            //lock.unlock();
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
