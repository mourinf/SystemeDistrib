/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UDP;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 *
 * @author Florian
 */
public class RecevoirMessageServeur implements Runnable {

    DatagramSocket socket;

    public RecevoirMessageServeur(DatagramSocket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            // Packet de reception
            DatagramPacket dataReceived = new DatagramPacket(new byte[100], 100);

            // Bucle infinito.
            while (true) {
                // Recvoir un packet
                socket.receive(dataReceived);
                System.out.print("Reçu data de "
                        + dataReceived.getAddress().getHostName() + " : ");

                // Deserializer le packet
                DataUdp data = DataUdp.fromByteArray(dataReceived.getData());
                System.out.println(data.toString());

                DatagramPacket dataSend = new DatagramPacket(dataReceived.getData(),
                        dataReceived.getLength(), dataReceived.getAddress(), dataReceived.getPort());

                socket.send(dataSend);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
