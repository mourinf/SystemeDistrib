/**
 * Leclaire Nicolas
 * Socket Udp
 */

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Client Udp
 * @author leclairn
 *
 */
public class ClientUdp
{

    /**
     * Programme
     * @param args
     */
    public static void main(String[] args)
    {
        new ClientUdp();
    }

    /**
     * Client Udp envoyant des messages
     *
     */
    public ClientUdp()
    {
        try
        {
            Scanner sc = new Scanner(System.in);

            // Socket Udp
            DatagramSocket socket = new DatagramSocket(
                    Constantes.PUERTO_DEL_CLIENTE, InetAddress
                            .getByName("localhost"));
            DatagramPacket dataReceived = new DatagramPacket(new byte[100], 100);
            
            HashMap<Integer, DatagramPacket> mem;
            mem = new HashMap<Integer, DatagramPacket>();

            int i=1;
            // Boucle infini de messages
            while(true)
            {
                System.out.println("Message : ");
                String message = sc.nextLine();
                
                // Données à envoyer sérializer
                DataUdp data = new DataUdp(message, i);
                byte[] elDatoEnBytes = data.toByteArray();

                // Packet à envoyer
                DatagramPacket dataSend = new DatagramPacket(elDatoEnBytes,
                        elDatoEnBytes.length, InetAddress
                                .getByName(Constantes.HOST_SERVIDOR),
                        Constantes.PUERTO_DEL_SERVIDOR);

                System.out.println("Envoi data : " + data.toString());
                socket.send(dataSend);
                
                mem.put(i, dataSend);
                
                socket.receive(dataReceived);
                System.out.println("Reception data : " + DataUdp.fromByteArray(dataReceived.getData()).toString());
                
                if(mem.containsKey(i)){
                    System.out.println("mem size : " + mem.size());
                    mem.remove(i);
                    System.out.println("Suppression du message " + i + " de la mémoire");
                    System.out.println("mem size : " + mem.size());
                }
                
                System.out.println();
                i++;
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
