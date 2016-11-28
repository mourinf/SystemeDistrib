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
        DataUdp checkMessage = new DataUdp("try", -1);
        byte[] bytes = checkMessage.toByteArray();
        try {
            DatagramPacket packet = new DatagramPacket(bytes, bytes.length, InetAddress.getByName(Constantes.HOST_SERVER), Constantes.PORT_SERVER);
            client.mem.put(-1, packet);
            Timer verif = new Timer();
            verif.schedule(new TimerTask() {
                @Override
                public void run() {
                    if (client.mem.containsKey(-1)) {
                        System.out.println("Machine en panne");
                        client.socket.close();
                        client.arret = true;
                    }
                }
            }, 5000);
            if (client.arret == true) {
                client.mem.clear();
                System.exit(0);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
