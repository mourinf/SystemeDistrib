/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UDP.client;

import UDP.client.ClientUdpWithThread;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Random;

/**
 *
 * @author Florian
 */
public class Main {

    /**
     * @param args the command line arguments
     * @throws java.net.UnknownHostException
     */
    public static void main(String[] args) throws UnknownHostException {
        long nb = Integer.parseInt(args[0]);
        int taille = Integer.parseInt(args[1]);
        ClientUdpWithThread client1;
        client1 = new ClientUdpWithThread(nb);

        Random rand = new Random();
        byte[] data = new byte[taille];

        for (int i = 0; i < nb; i++) {
            rand.nextBytes(data);
            client1.send(data);

        }

        client1.latence = System.currentTimeMillis();
        client1.Communiquer();
    }

    public static String generate(int length) {
        String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890"; // Tu supprimes les lettres dont tu ne veux pas
        String pass = "";
        for (int x = 0; x < length; x++) {
            int i = (int) Math.floor(Math.random() * 62); // Si tu supprimes des lettres tu diminues ce nb
            pass += chars.charAt(i);
        }
        System.out.println(pass);
        return pass;
    }
}
