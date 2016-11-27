/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UDP;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Florian
 */
public class LireMessage implements Runnable {

    ClientUdpWithThread client;

    public LireMessage(ClientUdpWithThread client) {
        this.client = client;
    }

    @Override
    public void run() {
        int i = 1;
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("Message :");
            String message = sc.nextLine();
            try {
                for(int j=0; j<100; j++){
                DataUdp data = new DataUdp(message, i+j);
                byte[] bytes = data.toByteArray();
                client.mem.put(i+j, new DatagramPacket(bytes, bytes.length,
                        InetAddress.getByName(Constantes.HOST_SERVER),
                        Constantes.PORT_SERVER));
                System.out.println("mis en memoire: " + client.mem.get(i+j).toString() + " nb elements: " + client.mem.size());
                }
                i+=100;
                if (client.arret==true){
                    System.exit(0);
                }
                //Thread.yield();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

}
