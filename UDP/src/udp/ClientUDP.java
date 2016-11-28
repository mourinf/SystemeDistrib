/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.*;
import java.net.*;
import java.util.Scanner;
import udp.Data;

/**
 *
 * @author mourinf
 */
public class ClientUDP {

    final static int port = 2042;
    final static int taille = 1024;
    static byte buffer[] = new byte[taille];

    public static void main(String argv[]) throws Exception {
        try {
            InetAddress serveur = InetAddress.getByName(InetAddress.getLocalHost().getHostAddress().toString());
            Scanner sc = new Scanner(System.in);
            int length;
            int numS = 1;
            while (true) {
                String message;

                System.out.println("Message:");
                message = sc.nextLine();
                length = message.length() + 4;

                Data data = new Data(message, numS);

                byte buffer[] = data.toByteArray();
                Data de = Data.fromByteArray(buffer);
                
                DatagramSocket socket = new DatagramSocket();
                DatagramPacket donneesEmises = new DatagramPacket(buffer, length, serveur, port);
                DatagramPacket donneesRecues = new DatagramPacket(new byte[taille], taille);

                socket.setSoTimeout(30000);
                socket.send(donneesEmises);
                
                System.out.println("Message : " + deserialize((byte[])donneesEmises.getData()).toString());
                System.out.println("de : " + donneesEmises.getAddress() + ":" + donneesEmises.getPort());
                
                socket.receive(donneesRecues);

                System.out.println("Message : " + deserialize((byte[])donneesRecues.getData()).toString());
                System.out.println("de : " + donneesRecues.getAddress() + ":" + donneesRecues.getPort());
            }

        } catch (SocketTimeoutException ste) {
            System.out.println("Le delai pour la reponse a expire");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static byte[] serialize(Object obj) throws IOException {
        try (ByteArrayOutputStream b = new ByteArrayOutputStream()) {
            try (ObjectOutputStream o = new ObjectOutputStream(b)) {
                o.writeObject(obj);
                o.close();
            }
            return b.toByteArray();
        }
    }

    public static Object deserialize(byte[] bytes) throws IOException, ClassNotFoundException {
        try (ByteArrayInputStream b = new ByteArrayInputStream(bytes)) {
            try (ObjectInputStream o = new ObjectInputStream(b)) {
                Object obj= o.readObject();
                o.close();
                return obj;
            }
        }
    }
}
