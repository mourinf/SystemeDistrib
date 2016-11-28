/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UDP;

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
        // TODO code application logic here
        ClientUdpWithThread client1;
        client1 = new ClientUdpWithThread(
                Integer.parseInt(args[0]), InetAddress.getByName(args[1]));
        //ClientUdpWithThread client2= new ClientUdpWithThread(Constantes.PORT_CLIENT2);
        //ServerUdpWithThread serveur= new ServerUdpWithThread();
        client1.Communiquer();
        //client2.Communiquer();
        //serveur.Communiquer();

        Random rand=new Random();
        byte[] data = new byte[10];
        for(int i=0; i<500; i++){
            rand.nextBytes(data);
            client1.send(data);
        }
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