/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UDP;

import java.net.UnknownHostException;
import java.util.Random;

/**
 *
 * @author Florian
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws UnknownHostException {
        // TODO code application logic here
        ClientUdpWithThread client1 = new ClientUdpWithThread(Constantes.PORT_CLIENT);
        //ClientUdpWithThread client2= new ClientUdpWithThread(Constantes.PORT_CLIENT2);
        //ServerUdpWithThread serveur= new ServerUdpWithThread();
        client1.Communiquer();
        //client2.Communiquer();
        //serveur.Communiquer();
        for(int i=0; i<300; i++){
            client1.send(generate(900));
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