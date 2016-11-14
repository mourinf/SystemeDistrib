/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Udptest;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import udp.Data;

/**
 *
 * @author HP PAVILION G7
 */
public class ServerTest {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        DatagramSocket socket;
        InetAddress server;
        while (true) {
            try {
                socket = new DatagramSocket(1234);
                server = InetAddress.getByName("localhost");
                byte[] data = new byte[4];
                DatagramPacket packet = new DatagramPacket(data, data.length);
                socket.receive(packet);

                int len = 0;
                // byte[] -> int
                for (int i = 0; i < 4; ++i) {
                    len |= (data[3 - i] & 0xff) << (i << 3);
                }

                // now we know the length of the payload
                byte[] buffer = new byte[len];
                packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);

                ByteArrayInputStream bais = new ByteArrayInputStream(buffer);
                ObjectInputStream ois = new ObjectInputStream(bais);
                Data messData = (Data) ois.readObject();
                System.out.println(messData.toString());
                //renvoi de l'objet

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(baos);
                oos.writeObject(messData);
                oos.flush();
                // get the byte array of the object
                byte[] Buf = baos.toByteArray();

                int number = Buf.length;;

                // int -> byte[]
                for (int i = 0; i < 4; ++i) {
                    int shift = i << 3; // i * 8
                    data[3 - i] = (byte) ((number & (0xff << shift)) >>> shift);
                }
                packet = new DatagramPacket(data, 4, server, 1233);
                socket.send(packet);
                System.out.println("byte envoyé " + packet);
                // now send the payload
                packet = new DatagramPacket(Buf, Buf.length, server, 1233);
                socket.send(packet);

                System.out.println("Renvoi effectue");
                socket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
