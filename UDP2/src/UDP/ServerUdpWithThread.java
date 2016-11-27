/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UDP;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.HashMap;

/**
 *
 * @author Florian
 */
public class ServerUdpWithThread {

    DatagramSocket socket;

    public ServerUdpWithThread() {

        try {
            this.socket = new DatagramSocket(
                    Constantes.PORT_SERVER, InetAddress
                            .getByName("localhost"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void Communiquer() {
        Thread reception = new Thread(new RecevoirMessageServeur(this.socket));
        reception.start();
    }
}
