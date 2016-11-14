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
import java.util.Scanner;
import udp.Data;

/**
 *
 * @author HP PAVILION G7
 */
public class clientTest {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int n = 1;
        DatagramSocket socket;
        InetAddress client;
        while (true) {
            Scanner sc = new Scanner(System.in);
            String message;

            System.out.println("Message:");
            message = sc.nextLine();
            Data messData = new Data(message, n);

            try {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(baos);
                oos.writeObject(messData);
                oos.flush();
                // get the byte array of the object
                byte[] Buf = baos.toByteArray();

                int number = Buf.length;
                byte[] data = new byte[4];

                // int -> byte[]
                for (int i = 0; i < 4; ++i) {
                    int shift = i << 3; // i * 8
                    data[3 - i] = (byte) ((number & (0xff << shift)) >>> shift);
                }

                socket = new DatagramSocket(1233);
                client = InetAddress.getByName("localhost");
                DatagramPacket packet = new DatagramPacket(data, 4, client, 1234);
                socket.send(packet);

                // now send the payload
                packet = new DatagramPacket(Buf, Buf.length, client, 1234);
                socket.send(packet);

                System.out.println("DONE SENDING");
                //reception du message
                 socket.receive(packet);

                int len = 0;
                // byte[] -> int
                for (int i = 0; i < 4; ++i) {
                    len |= (data[3 - i] & 0xff) << (i << 3);
                }

                // now we know the length of the payload
                byte[] buffer = new byte[len];
                packet = new DatagramPacket(Buf, Buf.length);
                socket.receive(packet);
                System.out.println("byte reçu "+ packet);

                ByteArrayInputStream bais = new ByteArrayInputStream(Buf);
                ObjectInputStream ois = new ObjectInputStream(bais);
                messData = (Data) ois.readObject();
                System.out.println("reception de: " + messData.toString());
                socket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            n=n+1;
        }
    }

}
