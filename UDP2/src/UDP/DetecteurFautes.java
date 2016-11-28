/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UDP;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author Florian
 */
public class DetecteurFautes extends TimerTask implements Runnable {

    ClientUdpWithThread client;

    public DetecteurFautes(ClientUdpWithThread client) {
        this.client = client;
    }

    @Override
    public void run() {
        DataUdp checkMessage = new DataUdp("try".getBytes(), -1);
        byte[] bytes = checkMessage.toByteArray();
        try {
            DatagramPacket packet = new DatagramPacket(bytes, bytes.length, InetAddress.getByName(Constantes.HOST_SERVER), Constantes.PORT_SERVER);
            try {
                client.sem.acquire();
                client.mem.put(-1, packet);
            } catch (Exception e) {

            } finally {
                client.sem.release();
            }
            //client.socket.send(packet);
            Thread.sleep(5000);
            try {
                this.client.sem.acquire();
                if (client.mem.containsKey(-1)) {
                    System.out.println("Machine en panne");
                    client.socket.close();
                    client.arret = true;
                }
            } catch (Exception e) {

            } finally {
                this.client.sem.release();
            }
            if (client.arret == true) {
                System.exit(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
